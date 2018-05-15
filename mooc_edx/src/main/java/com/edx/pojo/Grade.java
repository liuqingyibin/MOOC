package com.edx.pojo;

public class Grade {
    private Byte studentId;

    private Byte grade;

    public Grade(Byte studentId, Byte grade) {
        this.studentId = studentId;
        this.grade = grade;
    }

    public Grade() {
        super();
    }

    public Byte getStudentId() {
        return studentId;
    }

    public void setStudentId(Byte studentId) {
        this.studentId = studentId;
    }

    public Byte getGrade() {
        return grade;
    }

    public void setGrade(Byte grade) {
        this.grade = grade;
    }
}