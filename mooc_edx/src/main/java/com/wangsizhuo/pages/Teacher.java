package com.wangsizhuo.pages;

import com.wangsizhuo.algorithm.Prediction;
import com.wangsizhuo.model.Data;
import net.sf.json.JSONArray;
import com.wangsizhuo.util.DB;
import com.wangsizhuo.util.MyFile;
import com.wangsizhuo.model.Strings;
import net.sf.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Teacher {
    private String tid;
    Strings tableNames;
    private Connection conn;
    DB db;

    public Teacher(String tid) {
        db = new DB();
        conn = db.initDB();
        tableNames = new Strings();
        this.tid = tid;
    }


    /*********************************************老师上传的课程的列表****************************************************/

    /**
     * 获得课程列表（有用）
     *
     * @return （课程索引（课程号，选课人数，证书人数））
     */
    public JSONArray getCourseList() {
        JSONArray courses = new JSONArray();
        //创建视图
        String sql = "select cid c,count(*)," +
                " (SELECT count(*) from " + tableNames.originDataTableName + " where certified='yes' and cid=c) certifiedCount " +
                " from " + tableNames.originDataTableName + " where cid in(select cid from teacher where tid = '" + tid + "' ) group by cid";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject json = new JSONObject();
                json.put("课程号", rs.getString(1));
                json.put("选课人数", rs.getInt(2));
                json.put("获得证书人数", rs.getInt(3));
                courses.add(json);
            }
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("C:\\teacher\\courseList.json"));
            outputStream.write(courses.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }


    /*******************************************所有课程*****************************************************************/

    /**
     * 获取老师上传的课程名称（有用）
     *
     * @return 课程名列表
     */
    public ArrayList<String> getCoursesName() {
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
     * 查询所有课程的课程总人数、考试人数、取得证书人数（有用）
     *
     * @return 课程（选课总人数，考试人数，通过人数）
     */
    public JSONObject getNumberOfStudents() {
        JSONObject courses = new JSONObject();
        ArrayList<String> names = getCoursesName();
        for (String cid : names) {
            JSONObject course = new JSONObject();
            String sql = "select count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "'\n" +
                    "union all\n" +
                    "select count(*) from " + tableNames.originDataTableName + " where grade = 'null' and cid = '" + cid + "'\n" +
                    "union all\n" +
                    "select count(*) from  " + tableNames.originDataTableName + " where certified = 'yes' and cid = '" + cid + "';";
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                String[] keys = {"选课总人数", "考试总人数", "取得证书人数"};
                int index = 0;
                while (rs.next()) {
                    course.put(keys[index], rs.getInt(1));
                    index++;
                }
                courses.put(cid, course);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\teacher\\allCourseNumberOfStudents.json"));
                out.write(courses.toString().getBytes());
                out.flush();
                out.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

    public void getInteractionWithNumber(String kind) {
        JSONObject json = new JSONObject();
        ArrayList<String> names = getCoursesName();
        for (String cid : names) {
            JSONArray course = new JSONArray();
            String nevents = "select " + kind + ",count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "' GROUP BY " + kind;
            try {
                PreparedStatement pst = conn.prepareStatement(nevents);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    JSONArray item = new JSONArray();
                    item.add(rs.getInt(1));
                    item.add(rs.getInt(2));
                    course.add(item);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            json.put(cid,course);
        }
        String path = "C:/teacher/interactionOfNumber.json";
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            bufferedOutputStream.write(json.toString().getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    public JSONObject getInteractionWithNumber(String kind) {
//        ArrayList<String> names = getCoursesName();
//        JSONObject courses = new JSONObject();
//        for (String cid : names) {
//            JSONObject course = new JSONObject();
//            String nevents = "select " + kind + ",count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "' group by " + kind;
//            try {
//                PreparedStatement pst = conn.prepareStatement(nevents);
//                ResultSet rs = pst.executeQuery();
//                while (rs.next()) {
//                    course.put(rs.getInt(1), rs.getInt(2));
//                }
//                courses.put(cid, course);
//                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
//                        new FileOutputStream("C:\\teacher\\interactionOfNumber.json"));
//                bufferedOutputStream.write(courses.toString().getBytes());
//                bufferedOutputStream.flush();
//                bufferedOutputStream.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return courses;
//    }

    /**
     * 查询成绩(参加考试者)和平均交互次数之间的关系（待修改）
     *
     * @param kind 查询类型：nevents,nplay_videos,nchapters,nforum_posts
     * @return (成绩 ： 平均交互次数)
     */
    public JSONObject getGradeAndAvgInteraction(String kind) {
        JSONObject json = new JSONObject();
        String sql = "select grade,avg(" + kind + ") from " + tableNames.originDataTableName + " where grade != 'null' group by " + kind + " order by grade";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                json.put(rs.getDouble(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    /****************************************每一门课程***********************************************************************/


    /**
     * 查看某一门课程的选课学生列表（有用）
     *
     * @param cid 课程Id
     * @return 学生信息
     */
    public JSONArray getStudentList(String cid, int pageNo, int pageSize) {
        JSONArray array = new JSONArray();
        String sql = "SELECT uid, location, learner_level,age, gender, grade, start_time, last_time, nevents, ndays, nplay_videos, nchapters, " +
                "nforum_posts, certified FROM " + tableNames.originDataTableName + " WHERE cid ='" + cid + "' limit ?,?";
        String newCid = cid.replace("/", "_");
        System.out.println("new cid" + newCid);
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, (pageNo - 1) * pageSize);//距离这一页的第一行数据，其前面有多少行数据
            pst.setInt(2, pageSize);//每页有多少行
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject dataItem = new JSONObject();
                dataItem.put("学号", rs.getString(1));
                dataItem.put("地区", rs.getString(2));
                dataItem.put("学历", rs.getString(3));
                dataItem.put("年龄", rs.getString(4));
                dataItem.put("性别", rs.getString(5));
                dataItem.put("成绩", rs.getString(6));
                dataItem.put("注册时间", rs.getString(7));
                dataItem.put("最后登录时间", rs.getString(8));
                dataItem.put("交互次数", rs.getString(9));
                dataItem.put("交互天数", rs.getString(10));
                dataItem.put("播放视频数", rs.getString(11));
                dataItem.put("观看章节数", rs.getString(12));
                dataItem.put("论坛发帖数", rs.getString(13));
                dataItem.put("取得证书", rs.getString(14));
                array.add(dataItem);
            }
            System.out.println(array);
            String path = "C:\\teacher\\" + cid.replace("/", "_") + "_studentList.json";
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            outputStream.write(array.toString().getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    //获取某一位学生某一门课程的信息
    public JSONArray getOneCourseOneStudent(String cid, String uid) {
        JSONArray array = new JSONArray();
        String sql = "SELECT uid,location, learner_level,age, gender, grade, start_time, last_time, nevents, ndays, nplay_videos, nchapters, " +
                "nforum_posts, certified FROM " + tableNames.originDataTableName + " WHERE cid ='" + cid + "' and uid '" + uid + "'";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject dataItem = new JSONObject();
                dataItem.put("学号", rs.getString(1));
                dataItem.put("地区", rs.getString(2));
                dataItem.put("学历", rs.getString(3));
                dataItem.put("年龄", rs.getString(4));
                dataItem.put("性别", rs.getString(5));
                dataItem.put("成绩", rs.getString(6));
                dataItem.put("注册时间", rs.getString(7));
                dataItem.put("最后登录时间", rs.getString(8));
                dataItem.put("交互次数", rs.getString(9));
                dataItem.put("交互天数", rs.getString(10));
                dataItem.put("播放视频数", rs.getString(11));
                dataItem.put("观看章节数", rs.getString(12));
                dataItem.put("论坛发帖数", rs.getString(13));
                dataItem.put("取得证书", rs.getString(14));
                array.add(dataItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }


    /**
     * 查询某一门课程某一项交互情况的次数和人数之间的关系（暂时不用）
     *
     * @param cid  课程号
     * @param kind 交互类型：nevents,nplay_videos,nchapters,nforum_posts
     * @return 次数：人数
     */
    public JSONObject getOneCourseInterAction(String cid, String kind) {
        JSONObject json = new JSONObject();
        String sql = "select " + kind + ", count(*) from " + tableNames.originDataTableName + " where cid = '" + cid + "' group by " + kind;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                json.put(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 获得某一门课程的成绩和交互情况的关系（有用）
     * @param cid
     * @return
     */
    public JSONArray getInteractionAndGrade(String cid) {
        String sql = "select grade,nevents,nplay_videos,nchapters,nforum_posts from " + tableNames.originDataTableName +
                " where cid ='" + cid + "' and grade != 'null'";
        JSONArray array = new JSONArray();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            JSONArray title = new JSONArray();
            title.add("grade");
            title.add("nevents");
            title.add("nplay_videos");
            title.add("nchapters");
            title.add("nforum_posts");
            array.add(title);
            while (rs.next()) {
                JSONArray item = new JSONArray();
                item.add(rs.getDouble(1));
                item.add(rs.getDouble(2));
                item.add(rs.getDouble(3));
                item.add(rs.getDouble(4));
                item.add(rs.getDouble(5));
                array.add(item);
            }
            String path = "C:\\teacher\\" + cid.replace("/", "_") + "_gradeWithInteraction.json";
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(array.toString().getBytes());
            out.flush();
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 获得学历和取得证书情况的关系
     *
     * @param cid  课程号
     * @param kind 查询属性：learner_level,age,gender
     * @return 学历（该学历总人数，该学历取得证书人数）
     */
    public JSONArray attributeWithCertified(String cid, String kind) {

        JSONArray array = new JSONArray();
        JSONArray title = new JSONArray();
        String attribute = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid='" + cid + "' group by " + kind;
        String certified = "select " + kind + ", count(*) from " + tableNames.classfierTableName + " where cid = '" + cid + "' and certified = 'yes' group by " + kind;
        JSONObject attributeJson = getNumberOfAttributes(attribute);
        JSONObject certifiedJson = getNumberOfAttributes(certified);
        title.add(kind);
        title.add("examer");
        title.add("certified");
        array.add(title);
        for (Object obj : attributeJson.keySet()) {
            JSONArray item = new JSONArray();
            item.add(obj);
            item.add(attributeJson.get(obj));
            item.add(certifiedJson.get(obj));
            array.add(item);
        }
        String path = "C:\\teacher\\" + cid.replace("/", "_") + "_" + kind + ".json";
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(path));
            outputStream.write(array.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
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
     * 查看某一门课程&成绩的分布（待定）
     *
     * @param cid 课程号
     * @return 成绩：人数
     */
    public JSONObject getOneCourseGrade(String cid) {
        JSONObject json = new JSONObject();
        String sql = "select grade,count(*) from " + tableNames.classfierTableName + " where grade != 'empty' and cid = '" + cid + "' group by grade";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                json.put(rs.getString(1), rs.getDouble(2));
            }
            String newCid = cid.replace("/", "_");
            String path = "C:\\teacher\\" + cid + "_grade.json";
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
     * 获得某一门课程的学习者地区展示
     *
     * @param cid 待查询课程号
     * @return json对象(关键字, 数量)
     */
    public JSONObject location(String cid) {
        String sql = "SELECT location,COUNT(*) FROM " + tableNames.originDataTableName + " WHERE cid = '" + cid + "' GROUP BY location ORDER BY count(*) DESC LIMIT 10 ";
        JSONObject json = new JSONObject();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String key = rs.getString(1);
                if (key.equals("NA")) {
                    key = "未填写";
                }
                json.put(key, rs.getInt(2));
            }
            String newCid = cid.replace("/", "_");
            String path = "C:\\teacher\\" + newCid + "_location.json";
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            bufferedOutputStream.write(json.toString().getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /************************************************预测***************************************************************/
    public void importPredictionData(String path) {
        MyFile file = new MyFile(path);
        ArrayList<Data> data = file.myFileReader();
//        db.createPredictionTable(data,0);
        Prediction p = new Prediction(conn, "HarvardX/CB22x/2013_Spring");
        p.C45Tree();
    }


    public static void main(String[] args) {
        Teacher t = new Teacher("t2");
        String cid = "HarvardX/PH207x/2012_Fall";
//        System.out.println(t.getCourseList());
//        System.out.println(t.getStudentList(cid,1,15));
//        System.out.println(t.getNumberOfStudents());
//        System.out.println(t.getInteractionAndGrade(cid));
        t.getInteractionWithNumber("nevents");
//        System.out.println(t.getGradeAndAvgInteraction("nevents"));
//        System.out.println(t.attributeWithCertified(cid, "learner_level"));
//        System.out.println(t.attributeWithCertified(cid, "age"));
//        System.out.println(t.attributeWithCertified(cid, "gender"));
//        System.out.println(t.location(cid));
//        t.importPredictionData("C:/Users/Cathleen/Desktop/毕业相关/数据集/dateset/test/teacher/HarvardX_CB22x_2013_Spring.csv");

    }

}


