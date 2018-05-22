package com.wangsizhuo.util;

import com.wangsizhuo.model.Data;

import java.util.ArrayList;

public class GenerateTable {
    public static void main(String [] args) {
        DB db = new DB();
        String course1Path = "src/dataset/train/HarvardX_CB22x_2013_Spring.csv";
        String course2Path = "src/dataset/train/HarvardX_PH207x_2012_Fall.csv";
        String course3Path = "src/dataset/train/MITx_6.00x_2012_Fall.csv";
        String course4Path = "src/dataset/train/MITx_6.00x_2013_Spring.csv";
        MyFile file1 = new MyFile(course1Path);
        MyFile file2 = new MyFile(course2Path);
        MyFile file3 = new MyFile(course3Path);
        MyFile file4 = new MyFile(course4Path);
        ArrayList<Data> data1 = file1.myFileReader();
        ArrayList<Data> data2= file2.myFileReader();
        ArrayList<Data> data3 = file3.myFileReader();
        ArrayList<Data> data4 = file4.myFileReader();
        db.createTable(data1);
        db.createTable(data2);
        db.createTable(data3);
        db.createTable(data4);
//
//        String path="C:\\Users\\Cathleen\\Desktop\\test.csv";
//        MyFile myFile = new MyFile(path);
//        ArrayList<Data> data = myFile.myFileReader();
//        db.createTable(data);
    }
}
