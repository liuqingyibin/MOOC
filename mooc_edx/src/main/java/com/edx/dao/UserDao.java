package com.edx.dao;

import com.edx.dao.jdbc.ConnectionLiu;
import com.mysql.jdbc.Connection;

import com.mysql.jdbc.Statement;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by HP on 2018/5/17.
 */
public class UserDao {

    private Connection connection = new ConnectionLiu().getConn();

    private Statement stsm;
    private ResultSet rs;
    private PreparedStatement pstsm;

    public HashMap<String,String> login(String username,String password){
        try {

            StringBuffer sql = new StringBuffer();
            sql.append("select * from edx_user where username = ? and password = ?");

            pstsm = connection.prepareStatement(sql.toString());

            pstsm.setString(1,username);
            pstsm.setString(2,password);

            rs = pstsm.executeQuery();

            ResultSetMetaData data = rs.getMetaData();

            while (rs.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始

                    String c = data.getColumnName(i);
                    String v = rs.getString(c);
                    System.out.println(c + ":" + v + "\t");
                    map.put(c, v);
                }
                /**
                 * 0代表查询成功！
                 */
                map.put("status","0");
                return map;
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
//            JdbcUtils.closeConnection(connection);
//            JdbcUtils.closeResultSet(rs);
//            JdbcUtils.closeStatement(pstsm);
        }
//        System.out.println("这里进入了Dao end");
//        System.out.println("这里进入了Dao end");
//        System.out.println("这里进入了Dao end");
        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","用户名或密码错误！");
        return map;
    }




    public HashMap<String,String> getStudent(String id){
        try {

            StringBuffer sql = new StringBuffer();
//            sql.append("select  grade 成绩,certified 是否获得证书,viewed 查看了课件以外学习资料,explored 是否完成章节一半学习," +
//                    "final_cc_cname_di 最高学历,start_time_di 开始学习时间,last_event_di 最后学习时间," +
//                    "nevents 与课程交互次数,ndays_act 与课程交互天数,nplay_video 观看视频数量,nchapters 学生与之交互章节数," +
//                    "nforum_posts 讨论论坛帖子数量 from edx where edx_id = ?");
            sql.append("select grade ,certified ,viewed,explored ," +
                    "final_cc_cname_di ,start_time_di ,last_event_di ," +
                    "nevents ,ndays_act ,nplay_video ,nchapters ," +
                    "nforum_posts from edx where edx_id = ?");
            pstsm = connection.prepareStatement(sql.toString());

            pstsm.setString(1,id);
//            pstsm.setString(2,password);

            rs = pstsm.executeQuery();
            System.out.println("Dao这里进入了1");
            ResultSetMetaData data = rs.getMetaData();
            System.out.println("Dao这里进入了2");
            while (rs.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    System.out.println("Dao这里进入了3+   "+i);
                    String c = data.getColumnName(i);
                    String v = rs.getString(c);
                    System.out.println(c + ":" + v + "\t");
                    map.put(c, v);
                }
                /**
                 * 0代表查询成功！
                 */

                return map;
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
//            JdbcUtils.closeConnection(connection);
//            JdbcUtils.closeResultSet(rs);
//            JdbcUtils.closeStatement(pstsm);
        }
//        System.out.println("这里进入了Dao end");
//        System.out.println("这里进入了Dao end");
//        System.out.println("这里进入了Dao end");
        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","操作错误，获取用户信息失败！");
        return map;
    }




}
