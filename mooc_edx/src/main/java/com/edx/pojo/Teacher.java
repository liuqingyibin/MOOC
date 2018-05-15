package com.edx.pojo;

public class Teacher {
    private String tid;

    private String cid;

    public Teacher(String tid, String cid) {
        this.tid = tid;
        this.cid = cid;
    }

    public Teacher() {
        super();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }
}