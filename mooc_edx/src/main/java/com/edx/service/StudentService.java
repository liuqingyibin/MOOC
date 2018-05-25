package com.edx.service;

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
     * {"MITx/6.00x/2012_Fall":{"grade":"0.17","identify":"晒网达人","lastEventTime":"2013/2/28","nchapters":4,"ndays":14,"nevents":662,"nforumPosts":0,"nplayVideos":47,"startTime":"2012/9/23"}}
     */
    public Map<String,Course> getAllCourseInfo(int pageNo) {
        int pageSize = 15;
        Map<String,Course> courses = new HashMap<>();
        //语句中字段和表名需要修改为pre_users
        String sql = "SELECT * from " + tableNames.originDataTableName + " where uid = '" + uid +"' limit ?,?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, (pageNo-1)*pageSize);//距离这一页的第一行数据，其前面有多少行数据
            pst.setInt(2, pageSize);//每页有多少行
            ResultSet rs = pst.executeQuery();
            int index = 1;
            while (rs.next()){
                Course c = new Course();
                c.setIdentify(rs.getString("registered"),rs.getString("viewed"),
                        rs.getString("explored"),rs.getString("certified"));
                c.setStartTime(rs.getString("start_time"));
                c.setLastEventTime(rs.getString("last_time"));
                c.setGrade(rs.getString("grade"));
                c.setNevents(rs.getDouble("nevents"));
                c.setNdays(rs.getDouble("ndays"));
                c.setNplayVideos(rs.getDouble("nplay_videos"));
                c.setNchapters(rs.getDouble("nchapters"));
                c.setNforumPosts(rs.getDouble("nforum_posts"));
                courses.put(rs.getString(1),c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * 导入待预测的数据
     * @param path  文件路径和名称
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    public Map<String, ArrayList<String>> importPredictionData(String path){
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data,0);
        Prediction p = new Prediction(conn);
        Map<String, ArrayList<String>> map = p.C45Tree();
        return  map;
    }
}
