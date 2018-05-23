package com.xuren;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	private String ip;
	private String database;
	private String account;
	private String password;
	private Statement sta = null;
private Connection con = null;
	public DBUtils(String ip,String database,String account,String password) {
		this.ip=ip;
		this.database=database;
		this.account=account;
		this.password=password;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str="jdbc:mysql://"
				+ ip
				+ ":3306/"
				+ database;
		try {
			System.out.println(ip);
			System.out.println(str);
			System.out.println(account);
			System.out.println(password);
			con = DriverManager.getConnection(str,account, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sta = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Statement getSta() {
		return sta;
	}
	public void close() {
		try {
			sta.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
