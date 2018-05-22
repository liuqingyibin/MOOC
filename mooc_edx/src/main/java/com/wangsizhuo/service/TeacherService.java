package com.wangsizhuo.service;

import com.wangsizhuo.algorithm.Prediction;
import com.wangsizhuo.model.Data;
import com.wangsizhuo.model.Strings;
import com.wangsizhuo.util.DB;
import com.wangsizhuo.util.MyFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherService {
    private String tid;
    Strings tableNames;
    private Connection conn;
    DB db;
    private String path;
    private Map<String, ArrayList<Integer>> file;

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
     * @return
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
     *
     * @return json对象(关键字, 数量)
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
     * 获得某一属性和取得证书情况的关系：页面2：图3,4,5
     *
     * @param cid  课程号
     * @param kind 查询属性：learner_level,age,gender
     * @return 属性（该属性总人数，该学历取得证书人数）
     */
    public Map<String, ArrayList<Object>> attributeWithCertified(String cid, String kind) {
        Map<String, ArrayList<Object>> attrs = new HashMap<>();
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind;
        JSONObject attributeJson = getNumberOfAttributes(attribute);
        JSONObject certifiedJson = getNumberOfAttributes(certified);

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
     * 查询某一门课程交互次数和人数的记录:页面2:，图6,7,8,9（折线图，横轴次数，纵轴人数）(改成一个方法)
     *
     * @param kind 查询类型：nevents,nplay_videos,nchapters,nforum_posts
     * @return 课程（次数：人数）
     */
    public Map<String, ArrayList<Integer>> getInteractionWithNumber(String kind, String cid) {
        Map<String, ArrayList<Integer>> course = new HashMap<>();
        String nevents = "select " + kind + ",count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "' GROUP BY " + kind;
        try {
            PreparedStatement pst = conn.prepareStatement(nevents);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(rs.getInt(1));
                list.add(rs.getInt(2));
                course.put(cid, list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }


    /**
     * 查看某一门课程的选课学生列表：页面3
     *
     * @param cid 课程Id
     * @return 学生信息(学号 ， 地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书)
     */
    public Map<String, ArrayList<String>> getStudentList(String cid, int pageNo, int pageSize) {
        Map<String, ArrayList<String>> students = new HashMap<>();
        String sql = "SELECT uid, location, learner_level,age, gender, grade, start_time, last_time, nevents, ndays, nplay_videos, nchapters, " +
                "nforum_posts, certified FROM " + tableNames.originDataTableName + " WHERE cid ='" + cid + "' limit ?,?";

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
     */
    public void importPredictionData(String path, String cid) {
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data, 0);
        Prediction p = new Prediction(conn, cid);
        p.C45Tree();
    }

    /**
     * 查询某一个属性的人数
     *
     * @param sql 查询语句
     * @return 属性值：人数
     */
    private JSONObject getNumberOfAttributes(String sql) {
        JSONObject json = new JSONObject();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                json.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return json;
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
        t.getInteractionWithNumber("nevents","HarvardX/PH207x/2012_Fall");
    }
}
