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
import java.util.Map;

/**
 * Created by HP on 2018/5/17.
 */
public class WorkDao {
    private final static Connection connection = new ConnectionLiu().getConn();

    public HashMap<String,String> getMenuWeight() throws SQLException {
        


        ResultSet rs = null;
        PreparedStatement pstsm = null;

        try {



            StringBuffer sql = new StringBuffer();
            sql.append("select menu1,menu2,menu3 from liuqing_menu");

            pstsm = connection.prepareStatement(sql.toString());

            rs = pstsm.executeQuery();

            ResultSetMetaData data = rs.getMetaData();

            while (rs.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    String c = data.getColumnName(i);
                    String v = rs.getString(c);

                    map.put(c, v);
                }

                pstsm.close();
                rs.close();

                 
                return map;
            }

        } catch (Exception e){

            e.printStackTrace();
        }
        pstsm.close();
        rs.close();
//        stsm.close();
         

        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","数据库操作错误");
        return map;
    }


    public Map getMenu1Weight() throws SQLException {
        

//        Statement stsm=null ;
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {


            StringBuffer sql = new StringBuffer();
            sql.append("SELECT name,menu_1_state,menu_1_weight FROM `liuqing_quanzhong` where menu_1_state !='0'");

            pstsm = connection.prepareStatement(sql.toString());

            rs = pstsm.executeQuery();

            ResultSetMetaData data = rs.getMetaData();
            String[] str1=new String[10];
            String[] str2=new String[10];
            String[] str3=new String[10];
            Map map=new HashMap();
            int j=0;
            while (rs.next()){

                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    String c = data.getColumnName(i);
                    String v = rs.getString(c);
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str1[j]=v;
                    str2[j]=c;
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str3[j]=c;
//                    System.out.println(str1[j] + ":" + str2[2] + ":"+str3[j]+"\t");
                }
                j++;
            }
//                System.out.println("str的长度为："+str1.length);
                str1[j]="null";
                str2[j]="null";
                str3[j]="null";

            map.put("str1",str1);
                map.put("str2",str2);
                map.put("str3",str3);
            pstsm.close();
            rs.close();
//            stsm.close();
             
            return map;

            }

         catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","数据库操作错误");
        pstsm.close();
        rs.close();
//        stsm.close();
         

        return map;
    }


    public Map getMenu2Weight() throws SQLException {
        

//        Statement stsm=null ;
        ResultSet rs = null;
        PreparedStatement pstsm = null;

        try {

            StringBuffer sql = new StringBuffer();
            sql.append("SELECT name,menu_2_state,menu_2_weight FROM `liuqing_quanzhong` where menu_2_state !='0'");

            pstsm = connection.prepareStatement(sql.toString());

            rs = pstsm.executeQuery();

            ResultSetMetaData data = rs.getMetaData();
            String[] str1=new String[10];
            String[] str2=new String[10];
            String[] str3=new String[10];
            Map map=new HashMap();
            int j=0;
            while (rs.next()){

                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    String c = data.getColumnName(i);
                    String v = rs.getString(c);
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str1[j]=v;
                    str2[j]=c;
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str3[j]=c;
//                    System.out.println(str1[j] + ":" + str2[2] + ":"+str3[j]+"\t");
                }
                j++;
            }
            str1[j]="null";
            str2[j]="null";
            str3[j]="null";


            map.put("str1",str1);
            map.put("str2",str2);
            map.put("str3",str3);
            pstsm.close();
            rs.close();
//            stsm.close();
             

            return map;
        }

        catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","数据库操作错误");
        pstsm.close();
        rs.close();
//        stsm.close();
         

        return map;
    }

    public Map getMenu3Weight() throws SQLException {
        

//        Statement stsm=null ;
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {



            StringBuffer sql = new StringBuffer();
            sql.append("SELECT name,menu_3_state,menu_3_weight FROM `liuqing_quanzhong` where menu_3_state !='0'");

            pstsm = connection.prepareStatement(sql.toString());

            rs = pstsm.executeQuery();

            ResultSetMetaData data = rs.getMetaData();
            String[] str1=new String[10];
            String[] str2=new String[10];
            String[] str3= new String[10];
            Map map=new HashMap();
            int j=0;
            while (rs.next()){

                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    String c = data.getColumnName(i);
                    String v = rs.getString(c);
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str1[j]=v;
                    str2[j]=c;
                    i=i+1;
                    c=data.getColumnName(i);
                    c = rs.getString(c);
                    str3[j]=c;
//                    System.out.println(str1[j] + ":" + str2[2] + ":"+str3[j]+"\t");
                }
                j++;
            }

            str1[j]="null";
            str2[j]="null";
            str3[j]="null";

            map.put("str1",str1);
            map.put("str2",str2);
            map.put("str3",str3);
            pstsm.close();
            rs.close();
//            stsm.close();
             

            return map;
        }

        catch (Exception e){
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("msg","数据库操作错误");
        pstsm.close();
        rs.close();
//        stsm.close();
         

        return map;
    }

    /**
     * 这里是查询对应属性edx中的值
     * @param string
     * @return
     */
    public String getMenuValue(String string,String id) throws SQLException {


//        Statement stsm=null ;
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {

            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append(string);
            sql.append("  FROM edx WHERE edx_id = ?");
            pstsm = connection.prepareStatement(sql.toString());
//            pstsm.setString(1,string);
//            pstsm.setString(2,id);

            pstsm.setString(1,id);

            rs = pstsm.executeQuery();

//            while (rs.next()){
//                System.out.println("here!!!0+"+rs.getString("explored"));
//                System.out.println("here!!!0+"+rs.getString("explored"));
//                System.out.println("here!!!0+"+rs.getString("explored"));
//            }
//            ResultSetMetaData data = rs.getMetaData();
//
//            System.out.println("这里是查询返回结果！"+data.getColumnCount());
//            String str1 = rs.getString(data.getColumnName(0));
            String str1= "";
            while (rs.next()){
                str1 = rs.getString(string);
            }
            pstsm.close();
            rs.close();
            return str1;
        }

        catch (Exception e){
            e.printStackTrace();
        }
        pstsm.close();
        rs.close();
//        stsm.close();
         
        return "0";
    }


    /**
     * 以下是模型查询及算法处理
     * viewed dao查询结果实现
     * @param id
     * @return
     */

    public String getViewed(String id ){
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select viewed from edx where edx_id = ? ");
            pstsm = connection.prepareStatement(sql.toString());
            pstsm.setString(1,id);
            rs = pstsm.executeQuery();
            String resStr = null;
            while (rs.next()){
                resStr = rs.getString("viewed");
            }

            pstsm.close();
            rs.close();

            return resStr;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }


    /**
     * 成绩
     * grade 模型查询dao层实现
     * @param id
     * @return
     */
    public String getGrade(String id ){
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select grade from edx where edx_id = ? ");
            pstsm = connection.prepareStatement(sql.toString());
            pstsm.setString(1,id);
            rs = pstsm.executeQuery();
            String resStr = null;
            while (rs.next()){
                resStr = rs.getString("grade");
            }

            pstsm.close();
            rs.close();

            return resStr;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }


    /**
     * explored模型算法及dao层查询实现
     *
     * @param id
     * @return
     */
    public String getExplored(String id ){
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select explored from edx where edx_id = ? ");
            pstsm = connection.prepareStatement(sql.toString());
            pstsm.setString(1,id);
            rs = pstsm.executeQuery();
            String resStr = null;
            while (rs.next()){
                resStr = rs.getString("explored");
            }

            pstsm.close();
            rs.close();

            return resStr;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }


    public int getNchapters(String id ){
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select DISTINCT nchapters from edx where hcourse_id in( select hcourse_id from edx where edx_id =?) ");

            pstsm = connection.prepareStatement(sql.toString());
            pstsm.setString(1,id);
            rs = pstsm.executeQuery();
            rs.last();
            int num= rs.getRow();
            sql = null;

            StringBuffer sbf = new StringBuffer();
            sbf.append("select nchapters from edx where edx_id =?");
            pstsm = connection.prepareStatement(sbf.toString());
            pstsm.setString(1,id);
            rs= null;
            rs = pstsm.executeQuery();
            String resStr = null;
            while (rs.next()){
                resStr=rs.getString("nchapters");
            }

            pstsm.close();
            rs.close();
            int num2 = Integer.valueOf(resStr);
            return num2*100/(num-1);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Loe_di 模型算法
     * @param id
     * @return
     */
    public String getLoe(String id ){
        ResultSet rs = null;
        PreparedStatement pstsm = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select loe_di from edx where edx_id = ?");
            pstsm = connection.prepareStatement(sql.toString());
            pstsm.setString(1,id);
            rs = pstsm.executeQuery();
            String resStr = null;
            while (rs.next()){
                resStr = rs.getString("loe_di");
            }

            pstsm.close();
            rs.close();
//            System.out.println("这里是返回的num"+resStr);
//            System.out.println("这里是返回的num"+resStr);
//            System.out.println("这里是返回的num"+resStr);
            return resStr;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "0";
    }

}
