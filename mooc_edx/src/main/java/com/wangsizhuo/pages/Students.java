//package com.wangsizhuo.pages;
//
///**
// * 学生登录后的功能模块
// */
//
//import com.wangsizhuo.algorithm.Prediction;
//import com.wangsizhuo.util.DB;
//import com.wangsizhuo.util.MyFile;
//import com.wangsizhuo.model.Course;
//import com.wangsizhuo.model.Data;
//import com.wangsizhuo.model.Strings;
//
//import net.sf.json.JSONObject;
//
//import java.io.BufferedOutputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class Students {
//    private Connection conn;
//    private String uid;
//    Strings tableNames;
//    DB db;
//
//    Students(String uid) {
//        db = new DB();
//        conn = db.initDB();
//        this.uid = uid;
//        tableNames = new Strings();
//    }
//
//    /*********************************************所有课程**************************************************************/
////    /**
////     * 获取该生所有学习课程的列表
////     * @return      （课程名：课程信息）
////     */
////    public JSONObject getAllCourseInfo(int pageNo, int pageSize) {
////        JSONObject courseJson = new JSONObject();
////        //语句中字段和表名需要修改为pre_users
////        String sql = "SELECT * from " + tableNames.originDataTableName + " where uid = '" + uid +"' limit ?,?";
////        try {
////            PreparedStatement pst = conn.prepareStatement(sql);
////            pst.setInt(1, (pageNo-1)*pageSize);//距离这一页的第一行数据，其前面有多少行数据
////            pst.setInt(2, pageSize);//每页有多少行
////            ResultSet rs = pst.executeQuery();
////            int index = 1;
////            while (rs.next()){
////                Course c = new Course();
////                c.setCid(rs.getString("cid"));
////                c.setIdentify(rs.getString("registered"),rs.getString("viewed"),
////                        rs.getString("explored"),rs.getString("certified"));
////                c.setStartTime(rs.getString("start_time"));
////                c.setLastEventTime(rs.getString("last_time"));
////                c.setGrade(rs.getString("grade"));
////                c.setNevents(rs.getDouble("nevents"));
////                c.setNdays(rs.getDouble("ndays"));
////                c.setNplayVideos(rs.getDouble("nplay_videos"));
////                c.setNchapters(rs.getDouble("nchapters"));
////                c.setNforumPosts(rs.getDouble("nforum_posts"));
////                courseJson.put(index,c);
////                index++;
////            }
////            String path ="web/json/"+uid+"_courseList";
////            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
////            out.write(courseJson.toString().getBytes());
////            out.flush();
////            out.close();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return courseJson;
////    }
//
//    /**
//     * 导入待预测的数据
//     * @param path  文件路径和名称
//     */
//    public void importPredictionData(String path){
//        MyFile file = new MyFile(path);
//        ArrayList<Data> data = file.myFileReader();
////        db.createPredictionTable(data,0);
//        Prediction p = new Prediction(conn);
//        p.C45Tree();
//    }
//
//    public static void main(String args[]){
//        Students s = new Students("MHxPC130049215");
//        s.importPredictionData("C:\\Users\\Cathleen\\Desktop\\毕业相关\\数据集\\dateset\\test\\student\\MHxPC130049215.csv");
//        System.out.println(s.getAllCourseInfo(1,10));
//    }
//}
