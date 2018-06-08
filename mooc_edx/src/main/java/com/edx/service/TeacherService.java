package com.edx.service;

import com.wangsizhuo.algorithm.Kfolder;
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
import java.util.Iterator;
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
     *
     * @param tid 教师id（登录影虎id)
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
    public Map<String, ArrayList<String>> getCourseList() {
        Map<String, ArrayList<String>> courseList = new HashMap<>();
        //创建视图
        String sql = "select cid c,count(*)," +
                " (SELECT count(*) from " + tableNames.originDataTableName + " where certified='yes' and cid=c) certifiedCount " +
                " from " + tableNames.originDataTableName + " where cid in(select cid from teacher where tid = '" + tid + "' ) group by cid";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<String> cidList = new ArrayList<>();
            ArrayList<String> all = new ArrayList<>();
            ArrayList<String> certified = new ArrayList<>();

            while (rs.next()) {
                cidList.add(rs.getString(1).replace("/","_"));
                all.add(rs.getString(2));
                certified.add(rs.getString(3));
            }
            courseList.put("cid",cidList);
            courseList.put("all",all);
            courseList.put("certified",certified);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    /**
     * 获得某一门课程的成绩和交互情况的关系：页面2，图1（散点四合一图）
     *
     * @param cid 课程号
     * @return (grade : 所有的grade值
     *nevents : 所有的nevents值
     *nplay_videos : 所有的nplay_videos值
     *nchapters : 所有的nchapters值
     *nforum_posts ： 所有的nforum_posts值)
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
            attrs.put(keys[0], list1);
            attrs.put(keys[1], list2);
            attrs.put(keys[2], list3);
            attrs.put(keys[3], list4);
            attrs.put(keys[4], list5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attrs;
    }


    /**
     * 获得某一门课程学习者地区展示：页面2：图2（柱形图，展示前10个地区的人数）
     *
     * @param cid 课程号
     * @return [地区名,:选课人数]
     */
    public Map<String, ArrayList<String>> getLocation(String cid) {
        String sql = "SELECT location,COUNT(*) FROM " + tableNames.originDataTableName + " WHERE cid = '" + cid + "' and location != 'Unknown_Other' GROUP BY location ORDER BY count(*) DESC LIMIT 30 ";
        Map<String, ArrayList<String>> map = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<String> location = new ArrayList<>();
            ArrayList<String> number = new ArrayList<>();
            while (rs.next()) {
                location.add(rs.getString(1));
                number.add(rs.getString(2));
            }
            map.put("location",location);
            map.put("number",number);
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
     * @return [ 属性分类：[该属性总人数，该属性取得证书人数]]
     */
    public Map<String, ArrayList<String>> attributeWithCertified(String cid, String kind) {
        Map<String, ArrayList<String>> attrs = new HashMap<>();
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind +" order by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind +" order by " + kind;;
        Map<String,Integer> attributeJson = getNumberOfAttributes(attribute);
        Map<String,Integer> certifiedJson = getNumberOfAttributes(certified);

        ArrayList<String> kindList = new ArrayList<>();
        ArrayList<String> allList = new ArrayList<>();
        ArrayList<String> certifiedList = new ArrayList<>();
        ArrayList<String> perList = new ArrayList<>();


        for (Object obj : attributeJson.keySet()) {
            kindList.add((String) obj);
            allList.add(String.valueOf(attributeJson.get(obj)));
//            ArrayList attr = new ArrayList();
//            attr.add(attributeJson.get(obj));
            double d=0;
            if(certifiedJson.get(obj)==null){
                d=0;
            } else {
                d=certifiedJson.get(obj);
            }
            certifiedList.add(String.valueOf(d));
            double percent = d /attributeJson.get(obj);
            double d1 = percent*10000;
            int d2 = (int)d1;
            double d3 = (double)d2/100;
            String per = String.valueOf(d3);
            perList.add(per);
        }
        attrs.put("kind",kindList);
        attrs.put("all",allList);
        attrs.put("certified",certifiedList);
        attrs.put("per",perList);
        return attrs;
    }

    /**
     * 查看成绩 页面2 图6  折线图
     *
     * @param cid 课程号
     * @return [[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]``````]
     */
    public Map<String,ArrayList<Double>> getGrade(String cid) {
        Map<String,ArrayList<Double>> map = new HashMap<>();
        String sql = "select grade,count(*) from " + tableNames.originDataTableName + " where cid = '" + cid
                + "'and grade != 'null' group by grade";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<Double> grade = new ArrayList<>();
            ArrayList<Double> number = new ArrayList<>();
            while (rs.next()) {
                grade.add(rs.getDouble(1));
                number.add(rs.getDouble(2));
            }
            map.put("grade",grade);
            map.put("number",number);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 查看某一门课程的选课学生列表：页面3
     *
     * @param cid    课程Id
     * @param pageNo 页码
     * @return 学号:[地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书]
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
                location.add(rs.getString(2).replace(" ", "_"));
                learner_level.add(rs.getString(3));
                age.add(rs.getString(4));
                gender.add(rs.getString(5));
                grade.add(rs.getString(6));
                start_time.add(rs.getString(7).replace("/", "_"));
                last_time.add(rs.getString(8).replace("/", "_"));
                nevents.add(rs.getString(9));
                ndays.add(rs.getString(10));
                nplay_videos.add(rs.getString(11));
                nchapters.add(rs.getString(12));
                nforum_posts.add(rs.getString(13));
                certified.add(rs.getString(14));

            }
            students.put("uidList", uidList);
            students.put("location", location);
            students.put("learner_level", learner_level);
            students.put("age", age);
            students.put("gender", gender);
            students.put("grade", grade);
            students.put("start_time", start_time);
            students.put("last_time", last_time);
            students.put("nevents", nevents);
            students.put("ndays", ndays);
            students.put("nplay_videos", nplay_videos);
            students.put("nchapters", nchapters);
            students.put("nforum_posts", nforum_posts);
            students.put("certified", certified);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * 预测：页面4
     *
     * @param cid 课程号
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    public Map<String, ArrayList<String>> importPredictionData(String cid) {
        //以下为调用数据库实现预测算法
        /*
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data, 0);
        Prediction p = new Prediction(conn, cid);
        p.C45Tree();
        */
        //以下为十折交叉验证法调用数据库
        Kfolder kfolder = new Kfolder();
        Map<String, ArrayList<String>> map = kfolder.C45Tree();
        return map;
    }


    /**
     * 查询某一个属性的人数
     *
     * @param sql 查询语句
     * @return 属性值：人数
     */
    private Map<String, Integer> getNumberOfAttributes(String sql) {
        Map<String, Integer> map = new HashMap<>();
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

    public static void main(String[] args) {
        TeacherService t = new TeacherService("jiaoshi");
        String cid = "HarvardX/PH278x/2013_Spring";
        System.out.println(t.getCourseList());
        System.out.println(t.getLocation(cid));
        System.out.println(t.attributeWithCertified(cid,"learner_level"));
        System.out.println(t.attributeWithCertified(cid,"age"));
        System.out.println(t.attributeWithCertified(cid,"gender"));
        System.out.println(t.getStudentList(cid,25));
        System.out.println(t.getInteractionAndGrade(cid));
        System.out.println(t.getGrade(cid));
        Map<String, ArrayList<String>> map = t.importPredictionData(cid);
        for (Object o : map.keySet()) {
            String key = o.toString();
            System.out.println(key + "\t" + map.get(key));
        }
    }
}
