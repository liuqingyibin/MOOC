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

}
