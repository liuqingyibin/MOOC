package com.wangsizhuo.service;

import com.wangsizhuo.algorithm.Prediction;
import com.wangsizhuo.model.Data;
import com.wangsizhuo.model.Strings;
import com.wangsizhuo.util.DB;
import com.wangsizhuo.util.MyFile;
import net.sf.json.JSONArray;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class TeacherService {
    //教师id
    private String tid;
    //数据库表名
    private Strings tableNames;
    //数据库连接
    private Connection conn;
    //数据库
    private DB db;

    /***********************************************以下一行为修改内容************************************************/
    String path = "mooc_edx/src/main/java/com/wangsizhuo/dataset/tp.csv";

    /**
     * 教师
     * @param tid   教师id（登录影虎id)
     */
    public TeacherService(String tid) {
        db = new DB();
        conn = db.initDB();
        tableNames = new Strings();
        this.tid = tid;
    }

    /**
     * 获得课程列表:页面1
     *
     * @return 课程号：选课人数，获得证书人数
     */
    public Map<String, ArrayList<Integer>> getCourseList() {
        Map<String, ArrayList<Integer>> courseList = new HashMap<>();
        //创建视图
        String sql = "select cid c,count(*)," +
                " (SELECT count(*) from " + tableNames.originDataTableName + " where certified='yes' and cid=c) certifiedCount " +
                " from " + tableNames.originDataTableName + " where cid in(select cid from teacher where tid = '" + tid + "' ) group by cid";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ArrayList<Integer> course = new ArrayList<>();
                course.add(rs.getInt(2));
                course.add(rs.getInt(3));
                courseList.put(rs.getString(1).replace("/","_"), course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    /**
     * 获得某一门课程的成绩和交互情况的关系：页面2，图1（散点四合一图）
     *
     * @param cid 课程号
     * @return  (grade:所有的grade值
     *             nevents:所有的nevents值
     *             nplay_videos :所有的nplay_videos值
     *             nchapters:所有的nchapters值
     *             nforum_posts：所有的nforum_posts值)
     * 返回值纵向看，每一列数据属于一个人，横向看是选课人数
     */
    public Map<String, ArrayList<Double>> getInteractionAndGrade(String cid) {
        String sql = "select grade,nevents,nplay_videos,nchapters,nforum_posts from " + tableNames.originDataTableName +
                " where cid ='" + cid + "' and grade != 'null'";
        Map<String, ArrayList<Double>> attrs = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            String[] keys = new String[]{"grade", "nevents", "nplay_videos", "nchapters", "nforum_posts"};
            ArrayList<Double> list1 = new ArrayList<>();
            ArrayList<Double> list2 = new ArrayList<>();
            ArrayList<Double> list3 = new ArrayList<>();
            ArrayList<Double> list4 = new ArrayList<>();
            ArrayList<Double> list5 = new ArrayList<>();
            while (rs.next()) {
                list1.add(rs.getDouble(1));
                list2.add(rs.getDouble(2));
                list3.add(rs.getDouble(3));
                list4.add(rs.getDouble(4));
                list5.add(rs.getDouble(5));
            }
            attrs.put(keys[0],list1);
            attrs.put(keys[1],list2);
            attrs.put(keys[2],list3);
            attrs.put(keys[3],list4);
            attrs.put(keys[4],list5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attrs;
    }


    /**
     * 获得某一门课程学习者地区展示：页面2：图2（柱形图，展示前10个地区的人数）
     * @param cid 课程号
     * @return [地区名,:选课人数]
     */
    public Map<String, Integer> getLocation(String cid) {
        String sql = "SELECT location,COUNT(*) FROM " + tableNames.originDataTableName + " WHERE cid = '" + cid + "' and location != 'Unknown_Other' GROUP BY location ORDER BY count(*) DESC LIMIT 30 ";
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String key = rs.getString(1).replace(" ","_");
                if (key.equals("NA")) {
                    key = "未填写";
                }
                map.put(key, rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 教师查看某一课程各个属性人数和获得证书人数的关系(页面2)      图3,4,5折柱混合图，柱形图表示总人数，折线图表示取得证书人数
     *
     * @param cid  课程号
     * @param kind 待查询属性，只有三个值：learner_level,age,gender
     * @return     [ 属性分类：[该属性总人数，该属性取得证书人数]]
     *  learner_level-------{"Bachelor":[10456,474],"Doctorate":[2360,127],"empty":[670,73],"Less than Secondary":[133,3],"Master":[10562,692],"Secondary":[3314,80]}
     *  age-----{"10-20":[24,null],"20-30":[7423,300],"30-40":[13786,815],"40-50":[4101,220],"50-60":[1121,37],"<10":[20,null],">60":[428,7],"empty":[592,70]}
     *  gender----{"empty":[350,61],"female":[11525,646],"male":[15620,742]}
     */
    public Map<String, ArrayList<Object>> attributeWithCertified(String cid, String kind) {
        Map<String, ArrayList<Object>> attrs = new HashMap<>();
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind +" order by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind +" order by " + kind;;
        Map<String,Integer> attributeJson = getNumberOfAttributes(attribute);
        Map<String,Integer> certifiedJson = getNumberOfAttributes(certified);

        for (Object obj : attributeJson.keySet()) {
            ArrayList attr = new ArrayList();
            attr.add(attributeJson.get(obj));
            if(certifiedJson.get(obj)==null){
                attr.add("0");
            } else {
                attr.add(certifiedJson.get(obj));
            }
            double percent = Double.parseDouble(String.valueOf(attr.get(1))) / Double.parseDouble(String.valueOf(attr.get(0)));
            double d1 = percent*10000;
            int d2 = (int)d1;
            double d3 = (double)d2/100;
            String per = String.valueOf(d3);
            attr.add(per);
            attrs.put(toMyString(obj), attr);
        }
        return attrs;
    }
    public String toMyString(Object obj){
        String str = (String)obj;
        String result="";
        switch (str){
            case "female":result="男";break;
            case "male" : result="女";break;
            case "0Less than Secondary":result="中学以下";break;
            case "1Secondary":result="中学";break;
            case "2Bachelor":result="本科";break;
            case "3Master":result="硕士";break;
            case "4Doctorate":result="博士";break;
            case "5empty":
            case "empty":
                result="未填写";break;
            default:result=str;
        }
        return result;
    }

    /**
     * 查看成绩 页面2 图6  折线图
     * @param cid  课程号
     * @return  [[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]``````]
     */
    public JSONArray getGrade(String cid){
        JSONArray json = null;
        String sql = "select grade,count(*) from " + tableNames.originDataTableName + " where cid = '" + cid
                +"'and grade != 'null' group by grade";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<double[]> grade = new ArrayList<>();
            while (rs.next()){
                double[] d = new double[2];
                d[0] = rs.getDouble(1);
                d[1] = rs.getInt(2);
                grade.add(d);
            }
            json = JSONArray.fromObject(grade);
            Map<String,Integer> map = new HashMap<>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 查看某一门课程的选课学生列表：页面3
     *
     * @param cid    课程Id
     * @param pageNo 页码
     * @return       学号:[地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书]
     */
    public Map<String, ArrayList<String>> getStudentList(String cid, int pageNo) {
        Map<String, ArrayList<String>> students = new HashMap<>();
        String sql = "SELECT uid, location, learner_level,age, gender, grade, start_time, last_time, nevents, ndays, nplay_videos, nchapters, " +
                "nforum_posts, certified FROM " + tableNames.originDataTableName + " WHERE cid ='" + cid + "' limit ?,?";
        int pageSize = 15;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, (pageNo - 1) * pageSize);//距离这一页的第一行数据，其前面有多少行数据
            pst.setInt(2, pageSize);//每页有多少行
            ResultSet rs = pst.executeQuery();
            ArrayList<String> uidList = new ArrayList<>();
            ArrayList<String> location = new ArrayList<>();
            ArrayList<String> learner_level = new ArrayList<>();
            ArrayList<String> age = new ArrayList<>();
            ArrayList<String> gender = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> start_time = new ArrayList<>();
            ArrayList<String> last_time = new ArrayList<>();
            ArrayList<String> nevents = new ArrayList<>();
            ArrayList<String> ndays = new ArrayList<>();
            ArrayList<String> nplay_videos = new ArrayList<>();
            ArrayList<String> nchapters = new ArrayList<>();
            ArrayList<String> nforum_posts = new ArrayList<>();
            ArrayList<String> certified = new ArrayList<>();

            while (rs.next()) {
                uidList.add(rs.getString(1));
                location.add(rs.getString(2).replace(" ","_"));
                learner_level.add(rs.getString(3));
                age.add(rs.getString(4));
                gender.add(rs.getString(5));
                grade.add(rs.getString(6));
                start_time.add(rs.getString(7).replace("/","_"));
                last_time.add(rs.getString(8).replace("/","_"));
                nevents.add(rs.getString(9));
                ndays.add(rs.getString(10));
                nplay_videos.add(rs.getString(11));
                nchapters.add(rs.getString(12));
                nforum_posts.add(rs.getString(13));
                certified.add(rs.getString(14));

            }
            students.put("uidList",uidList);
            students.put("location",location);
            students.put("learner_level",learner_level);
            students.put("age",age);
            students.put("gender",gender);
            students.put("grade",grade);
            students.put("start_time",start_time);
            students.put("last_time",last_time);
            students.put("nevents",nevents);
            students.put("ndays",ndays);
            students.put("nplay_videos",nplay_videos);
            students.put("nchapters",nchapters);
            students.put("nforum_posts",nforum_posts);
            students.put("certified",certified);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    /******************************************以下为修改被修改***********************************************/
    /**
     * 预测：页面4
     *
     * @param cid  课程号
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    public Map<Integer,ArrayList<String>> importPredictionData(String cid) {
        //以下为可运行代码
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data, 0);
        Prediction p = new Prediction(conn, cid);
        p.C45Tree();
        Map<Integer,ArrayList<String>> map = showPredictionList(cid);
        return map;
    }

    /**
     * 获取预测数据
     * @return  [uid, cid,yse/no]
     */
    private Map<Integer,ArrayList<String>> showPredictionList(String cid) {
        Map<Integer,ArrayList<String>> map = new HashMap<>();
        String sql = "select uid,cid,certified from " + tableNames.predictionTableName + " where cid = '"+cid+"'";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int index=1;
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add(rs.getString(1));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                list.add("这回替换成一句话");
                map.put(index,list);
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询某一个属性的人数
     *
     * @param sql 查询语句
     * @return 属性值：人数
     */
    private Map<String,Integer> getNumberOfAttributes(String sql) {
        Map<String,Integer> map = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return map;
    }

    /**
     * 输出文件
     *
     * @param path 路径+文件名
     * @param file 待输出文件数据
     */
    private void fileOut(String path, Map<String, ArrayList<Object>> file) {
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(file.toString().getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TeacherService t = new TeacherService("jiaoshi");
        String cid = "HarvardX/PH278x/2013_Spring";
//        System.out.println(t.getCourseList());
//        System.out.println(t.getLocation(cid));
//        System.out.println(t.attributeWithCertified(cid,"learner_level"));
//        System.out.println(t.attributeWithCertified(cid,"age"));
//        System.out.println(t.attributeWithCertified(cid,"gender"));
//
        System.out.println(t.getStudentList(cid,25));
//        System.out.println(t.getInteractionAndGrade(cid));
//
//        System.out.println(t.getGrade(cid));
//        Map<Integer,ArrayList<String>> map = t.importPredictionData(cid);
//        System.out.println();
    }
}