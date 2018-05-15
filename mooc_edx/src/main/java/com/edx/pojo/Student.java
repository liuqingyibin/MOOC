package com.edx.pojo;

public class Student {
    private Byte studentId;

    private String name;

    public Student(Byte studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public Student() {
        super();
    }

    public Byte getStudentId() {
        return studentId;
    }

    public void setStudentId(Byte studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}