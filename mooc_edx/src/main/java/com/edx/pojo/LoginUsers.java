package com.edx.pojo;

public class LoginUsers {
    private String uid;

    private String pwd;

    private Integer identify;

    public LoginUsers(String uid, String pwd, Integer identify) {
        this.uid = uid;
        this.pwd = pwd;
        this.identify = identify;
    }

    public LoginUsers() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }
}