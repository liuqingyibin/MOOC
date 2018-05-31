package com.wangsizhuo.service;

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
    /****************以下一行为新增**********************************/
    String path = "mooc_edx/src/main/java/com/wangsizhuo/dataset/sp.csv";

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
            while (rs.next()){
                Course c = new Course();
                ArrayList<String> list = new ArrayList<>();
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
                list.add(c.getIdentify());
                list.add(c.getStartTime());
                list.add(c.getLastEventTime());
                list.add(c.getGrade());
                list.add(String.valueOf(c.getNevents()));
                list.add(String.valueOf(c.getNdays()));
                list.add(String.valueOf(c.getNplayVideos()));
                list.add(String.valueOf(c.getNchapters()));
                list.add(String.valueOf(c.getNforumPosts()));
                courses.put(rs.getString(1).replace("/","_"),list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

/*******************************以下一个方法被修改***********************************************************/
    /**
     * 导入待预测的数据
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    public Map<Integer, ArrayList<String>> importPredictionData(){
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data,0);
        Prediction p = new Prediction(conn);
        p.C45Tree();
        Map<Integer, ArrayList<String>> map = showPredictionList(uid);
        return  map;
    }

    /**
     * 获取预测数据
     * @return  [uid, cid,yse/no]
     */
    private Map<Integer,ArrayList<String>> showPredictionList(String uid) {
        Map<Integer,ArrayList<String>> map = new HashMap<>();
        String sql = "select uid,cid,certified from " + tableNames.predictionTableName + " where uid = '"+uid+"'";
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



    public static void main(String[] args) {
        StudentService s = new StudentService("MHxPC130569306");
        String cid = "HarvardX/PH278x/2013_Spring";
        System.out.println(s.getAllCourseInfo(1));
        Map<Integer,ArrayList<String>> map = s.importPredictionData();
        System.out.println();
    }
}
