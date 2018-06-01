package com.wangsizhuo.util;
/**
 * 生成数据库：原始数据数据库，分类数据数据库
 * 用于分析数据
 */

import com.wangsizhuo.model.Data;
import java.util.ArrayList;

public class GenerateTable {
    public static void main(String [] args) {
        DB db = new DB();
        String coursePath = "C:\\Users\\Cathleen\\Desktop\\测试生成数据库.csv";
        MyFile file = new MyFile(coursePath);
        ArrayList<Data> data = file.myFileReader();
        db.initDB();
        db.createTable(data);

    }
}
