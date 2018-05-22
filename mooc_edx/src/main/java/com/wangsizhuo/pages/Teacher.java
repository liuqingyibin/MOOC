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




    /*******************************************所有课程*****************************************************************/






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
//        t.getInteractionWithNumber("nevents");
//        System.out.println(t.getGradeAndAvgInteraction("nevents"));
//        System.out.println(t.attributeWithCertified(cid, "learner_level"));
//        System.out.println(t.attributeWithCertified(cid, "age"));
//        System.out.println(t.attributeWithCertified(cid, "gender"));
//        System.out.println(t.location(cid));
//        t.importPredictionData("C:/Users/Cathleen/Desktop/毕业相关/数据集/dateset/test/teacher/HarvardX_CB22x_2013_Spring.csv");

    }

}


