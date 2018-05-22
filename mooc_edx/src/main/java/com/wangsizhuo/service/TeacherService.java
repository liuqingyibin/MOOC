package com.wangsizhuo.service;

import com.wangsizhuo.algorithm.Prediction;
import com.wangsizhuo.model.Data;
import com.wangsizhuo.model.Strings;
import com.wangsizhuo.pages.Teacher;
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
     * 获得课程列表
     * @return  课程号：选课人数，获得证书人数
     */
    public Map<String,ArrayList<Integer>> getCourseList(){
        Map<String,ArrayList<Integer>> courseList = new HashMap<>();
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
                course.add( rs.getInt(3));
                courseList.put(rs.getString(1),course);
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
     * 获取老师上传的课程名称（有用）
     * @return 课程名列表
     */
    private ArrayList<String> getCoursesName() {
        ArrayList<String> names = new ArrayList<>();
        String sql = "select cid from " + tableNames.teacher + " where tid = '" + tid + "'";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                names.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }


    /**
     * 查询所有课程的课程总人数、考试人数、取得证书人数
     * 页面4，所有课程图表
     * @return 课程（选课总人数，考试人数，通过人数）
     */
    public Map<String,ArrayList<Integer>> getNumberOfStudents() {
        Map<String,ArrayList<Integer>> courses = new HashMap<>();
        //获得老师上传的课程名
        ArrayList<String> names = getCoursesName();
        //获得每一门课程的详细信息
        for (String cid : names) {
            ArrayList<Integer> course = new ArrayList<>();
            String sql = "select count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "'\n" +
                    "union all\n" +
                    "select count(*) from " + tableNames.originDataTableName + " where grade = 'null' and cid = '" + cid + "'\n" +
                    "union all\n" +
                    "select count(*) from  " + tableNames.originDataTableName + " where certified = 'yes' and cid = '" + cid + "';";
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    course.add(rs.getInt(1));
                }
                courses.put(cid, course);
                String path ="C:\\teacher\\allCourseNumberOfStudents.json";
                fileOut(path,courses);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }


    /**
     * 查询所有课程交互次数和人数的记录（待修改）
     *
     * @param kind 查询类型：nevents,nplay_videos,nchapters,nforum_posts
     * @return 课程（次数：人数）
     */

    public Map<String,ArrayList<int[]>> getInteractionWithNumber(String kind) {
        Map<String,ArrayList<int[]>> courses = new HashMap<>();

        ArrayList<String> names = getCoursesName();

        for (String cid : names) {
            ArrayList<int[]> course = new ArrayList<>();
            String nevents = "select " + kind + ",count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "' GROUP BY " + kind;
            try {
                PreparedStatement pst = conn.prepareStatement(nevents);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int[] item = new int[2];
                    item[0] = rs.getInt(1);
                    item[1] = rs.getInt(2);
                    course.add(item);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            courses.put(cid,course);
        }
        String path = "C:/teacher/interactionOfNumber.json";
//        fileOut(path,courses);
        return courses;
    }

    /**
     * 获得学习者地区展示
     * @return json对象(关键字, 数量)
     */
    public Map<String,Integer> getLocation() {
        String sql = "SELECT location,COUNT(*) FROM " + tableNames.originDataTableName + "  GROUP BY location ORDER BY count(*) DESC LIMIT 10 ";
        Map<String,Integer> map = new HashMap<>();
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
     * 查看某一门课程的选课学生列表
     * @param cid 课程Id
     * @return 学生信息(学号，地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书)
     */
    public Map<String,ArrayList<String>> getStudentList(String cid, int pageNo, int pageSize) {
        Map<String,ArrayList<String>> students = new HashMap<>();
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
                dataItem.add( rs.getString(3));
                dataItem.add( rs.getString(4));
                dataItem.add( rs.getString(5));
                dataItem.add(rs.getString(6));
                dataItem.add( rs.getString(7));
                dataItem.add( rs.getString(8));
                dataItem.add(rs.getString(9));
                dataItem.add( rs.getString(10));
                dataItem.add( rs.getString(11));
                dataItem.add( rs.getString(12));
                dataItem.add(rs.getString(13));
                dataItem.add( rs.getString(14));
                students.put(rs.getString(1),dataItem);
            }
            String path = "C:\\teacher\\" + cid.replace("/", "_") + "_studentList.json";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * 获得某一门课程的成绩和交互情况的关系
     * @param cid
     * @return
     */
    public Map<String, ArrayList<Double>> getInteractionAndGrade(String cid) {
        String sql = "select grade,nevents,nplay_videos,nchapters,nforum_posts from " + tableNames.originDataTableName +
                " where cid ='" + cid + "' and grade != 'null'";
        Map<String, ArrayList<Double>> attrs = new HashMap<>();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            JSONArray title = new JSONArray();
            title.add("grade");
            title.add("nevents");
            title.add("nplay_videos");
            title.add("nchapters");
            title.add("nforum_posts");
            String[] keys = new String[]{"grade","nevents","nplay_videos","nchapters","nforum_posts"};
            int index = 0;
            while (rs.next()) {
                ArrayList<Double> item = new ArrayList<>();
                item.add(rs.getDouble(1));
                item.add(rs.getDouble(2));
                item.add(rs.getDouble(3));
                item.add(rs.getDouble(4));
                item.add(rs.getDouble(5));
                attrs.put(keys[index],item);
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attrs;
    }

    /**
     * 获得某一属性和取得证书情况的关系
     *
     * @param cid  课程号
     * @param kind 查询属性：learner_level,age,gender
     * @return 属性（该属性总人数，该学历取得证书人数）
     */
    public Map<String,ArrayList<Object>> attributeWithCertified(String cid, String kind) {
        Map<String,ArrayList<Object>> attrs = new HashMap<>();
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind;
        JSONObject attributeJson = getNumberOfAttributes(attribute);
        JSONObject certifiedJson = getNumberOfAttributes(certified);

        for (Object obj : attributeJson.keySet()) {
            ArrayList<Object> attr = new ArrayList<>();
            attr.add(attributeJson.get(obj));
            attr.add(certifiedJson.get(obj));
            attrs.put((String)obj,attr);
        }
        System.out.println(attrs);
        return attrs;
    }

    /**
     * 查询某一个属性的人数
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

    private void fileOut(String path,Map<String,ArrayList<Integer>> file){
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


    public static void main(String[] args){
        TeacherService t = new TeacherService("t2");
        t.attributeWithCertified("HarvardX/PH207x/2012_Fall","gender");
    }

    public void importPredictionData(String path,String cid) {
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
        db.createPredictionTable(data,0);
        Prediction p = new Prediction(conn, cid);
        p.C45Tree();
    }
}
