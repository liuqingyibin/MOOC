package com.edx.service;

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
                courseList.put(rs.getString(1), course);
            }
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("C:\\teacher\\courseList.json"));
            outputStream.write(courseList.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        String sql = "SELECT location,COUNT(*) FROM " + tableNames.originDataTableName + "  where cid = '" + cid + "'GROUP BY location ORDER BY count(*) DESC LIMIT 10 ";
        Map<String, Integer> map = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String key = rs.getString(1);
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
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind;
        Map<String,Integer> attributeJson = getNumberOfAttributes(attribute);
        Map<String,Integer> certifiedJson = getNumberOfAttributes(certified);

        for (Object obj : attributeJson.keySet()) {
            ArrayList<Object> attr = new ArrayList<>();
            attr.add(attributeJson.get(obj));
            attr.add(certifiedJson.get(obj));
            attrs.put((String) obj, attr);
        }
        System.out.println(attrs);
        return attrs;
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

            String path = "web/json/teacher/" + cid.replace("/","-") + "_grade.json";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(json.toString().getBytes());
            out.flush();
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
     * {"MHxPC130000030":["Brazil","Secondary","28","male","0","2012/7/31","2012/10/10","2","2","0","0","0","no"],
     * "MHxPC130000049":["United States","Master","45","male","0","2012/8/13","2012/8/13","1","1","0","0","0","no"],
     * "MHxPC130000301":["Egypt","Bachelor","35","male","0.03","2012/10/5","2013/6/7","316","7","80","2","0","no"]}
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
            while (rs.next()) {
                ArrayList<String> dataItem = new ArrayList<>();
                dataItem.add(rs.getString(2));
                dataItem.add(rs.getString(3));
                dataItem.add(rs.getString(4));
                dataItem.add(rs.getString(5));
                dataItem.add(rs.getString(6));
                dataItem.add(rs.getString(7));
                dataItem.add(rs.getString(8));
                dataItem.add(rs.getString(9));
                dataItem.add(rs.getString(10));
                dataItem.add(rs.getString(11));
                dataItem.add(rs.getString(12));
                dataItem.add(rs.getString(13));
                dataItem.add(rs.getString(14));
                students.put(rs.getString(1), dataItem);
            }
            String path = "C:\\teacher\\" + cid.replace("/", "_") + "_studentList.json";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * 预测：页面4
     *
     * @param path 路径+文件名
     * @param cid  课程号
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    public Map<String,ArrayList<String>> importPredictionData(String path, String cid) {
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data, 0);
        Prediction p = new Prediction(conn, cid);
        Map<String,ArrayList<String>> map = p.C45Tree();
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
        TeacherService t = new TeacherService("t2");
//        System.out.println(t.getInteractionAndGrade("HarvardX/PH207x/2012_Fall"));
//        t.getInteractionWithNumber("nevents","HarvardX/PH207x/2012_Fall");
        t.attributeWithCertified("HarvardX/PH207x/2012_Fall","learner_level");
    }
}
