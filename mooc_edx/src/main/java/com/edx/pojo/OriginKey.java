package com.edx.pojo;

public class OriginKey {
    private String uid;

    private String cid;

    public OriginKey(String uid, String cid) {
        this.uid = uid;
        this.cid = cid;
    }

    public OriginKey() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }
}