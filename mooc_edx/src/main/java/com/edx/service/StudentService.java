package com.edx.service;

import com.wangsizhuo.algorithm.Kfolder;
import com.wangsizhuo.algorithm.Prediction;
import com.wangsizhuo.model.Course;
import com.wangsizhuo.model.Data;
import com.wangsizhuo.model.Strings;
import com.wangsizhuo.util.DB;
import com.wangsizhuo.util.MyFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StudentService {
    private Connection conn;
    private String uid;
    Strings tableNames;
    DB db;

    /**
     * 学生
     * @param uid       学生id，登录扎昂好
     */
    public StudentService(String uid) {
        db = new DB();
        conn = db.initDB();
        this.uid = uid;
        tableNames = new Strings();
    }
    /**
     * 获取该生所有学习课程的列表
     * @param pageNo 页码
     * @return      {课程名：{课程信息}}
     */
    public Map<String,ArrayList<String>> getAllCourseInfo(int pageNo) {
        int pageSize = 15;
        Map<String,ArrayList<String>> courses = new HashMap<>();
        //语句中字段和表名需要修改为pre_users
        String sql = "SELECT * from " + tableNames.originDataTableName + " where uid = '" + uid +"' limit ?,?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, (pageNo-1)*pageSize);//距离这一页的第一行数据，其前面有多少行数据
            pst.setInt(2, pageSize);//每页有多少行
            ResultSet rs = pst.executeQuery();
            ArrayList<String> uidList = new ArrayList<>();
            ArrayList<String> identifyList = new ArrayList<>();
            ArrayList<String> startTime = new ArrayList<>();
            ArrayList<String> lastTime = new ArrayList<>();
            ArrayList<String> grade = new ArrayList<>();
            ArrayList<String> neventsList = new ArrayList<>();
            ArrayList<String> ndaysList = new ArrayList<>();
            ArrayList<String> nplayVideoList = new ArrayList<>();
            ArrayList<String> nchapterList = new ArrayList<>();
            ArrayList<String> nforumList = new ArrayList<>();
            while (rs.next()){
                Course c = new Course();
                c.setIdentify(rs.getString("registered"),rs.getString("viewed"),
                        rs.getString("explored"),rs.getString("certified"));
                c.setStartTime(rs.getString("start_time").replace("/","_"));
                c.setLastEventTime(rs.getString("last_time").replace("/","_"));
                c.setGrade(rs.getString("grade"));
                c.setNevents(rs.getDouble("nevents"));
                c.setNdays(rs.getDouble("ndays"));
                c.setNplayVideos(rs.getDouble("nplay_videos"));
                c.setNchapters(rs.getDouble("nchapters"));
                c.setNforumPosts(rs.getDouble("nforum_posts"));
                uidList.add(rs.getString(1));
                identifyList.add(c.getIdentify());
                startTime.add(c.getStartTime().replace("/","_"));
                lastTime.add(c.getLastEventTime().replace("/","_"));
                grade.add(c.getGrade());
                neventsList.add(String.valueOf(c.getNevents()));
                ndaysList.add(String.valueOf(c.getNdays()));
                nplayVideoList.add(String.valueOf(c.getNplayVideos()));
                nchapterList.add(String.valueOf(c.getNchapters()));
                nforumList.add(String.valueOf(c.getNforumPosts()));
            }
            courses.put("uid",uidList);
            courses.put("identify",identifyList);
            courses.put("startTime",startTime);
            courses.put("lastTime",lastTime);
            courses.put("grade",grade);
            courses.put("nevents",neventsList);
            courses.put("ndays",ndaysList);
            courses.put("nplayVideo",nplayVideoList);
            courses.put("nchapters",nchapterList);
            courses.put("nforumPosts",nforumList);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * 预测：页面4
     *
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
//    public Map<String, ArrayList<String>> importPredictionData() {
//        //以下为十折交叉验证法调用数据库
//        Kfolder kfolder = new Kfolder();
//        Map<String, ArrayList<String>> map = kfolder.C45Tree();
//        return map;
//    }
    public Map<String, ArrayList<String>> importPredictionData() {
        //以下为十折交叉验证法调用数据库
        Map<String, ArrayList<String>> map= new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("no");
        map.put("result",list);
        return map;
    }

    public static void main(String[] args) {
        StudentService s = new StudentService("MHxPC130569306");
        System.out.println(s.getAllCourseInfo(1));
        Map<String, ArrayList<String>> map = s.importPredictionData();
        for (Object o : map.keySet()) {
            String key = o.toString();
            System.out.println(key + "\t" + map.get(key));
        }
    }
}
