package com.wangsizhuo.Servlet;

import com.wangsizhuo.util.DB;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet implements Servlet {
    private Connection conn;
    private DB db;

    public LoginServlet() {
       db = new DB();
       conn = db.initDB();
    }

    @Override
    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
            throws ServletException, IOException {
        doPost(arg0, arg1);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        String result = "";
        // 获取用户名
        String sUserName = request.getParameter("txtUserName");
        if (sUserName == "" || sUserName == null || sUserName.length() > 20) {
            System.out.println("uid:" + sUserName);
            try {
                result = "请输入用户名（不超过20字符）！";
                request.setAttribute("ErrorUserName", result);
                response.sendRedirect("login.html");
            } catch (Exception e) {
            }
        }
        // 获取密码
        String sPassword = request.getParameter("txtPassword");
        if (sPassword == "" || sPassword == null || sPassword.length() > 20) {
            try {
                result = "请输入密码（不超过20字符）！";
                request.setAttribute("ErrorPassword", result);
                response.sendRedirect("login.jsp");
            } catch (Exception e) {
            }
        }

        // 连接参数与Access不同
        try {
            String sql = "SELECT psw,identify FROM login_users where username = '" + sUserName + "'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("identify") == 1) {
                    session.setAttribute("conn",conn);
                    session.setAttribute("tid",sUserName);
                    response.sendRedirect("/teacher1/index.jsp");
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
