package com.edx.dao.jdbc;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by HP on 2018/5/17.
 */

/**
 * 柳青创建的基础jdbc连接
 * 方便之后代码的jdbc连接使用，后续不再使用mybatis
 */
public class ConnectionLiu {

    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://123.206.205.246:3306/student";
        String username = "liuqing";
        String password = "liuqing123456";
        Connection conn = null;

        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
