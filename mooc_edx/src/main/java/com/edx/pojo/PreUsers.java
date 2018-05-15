package com.edx.pojo;

public class PreUsers extends PreUsersKey {
    private String registered;

    private String viewed;

    private String explored;

    private String certified;

    private String location;

    private String learnerLevel;

    private String age;

    private String gender;

    private String grade;

    private Double timeSpan;

    private Double nevents;

    private Double ndays;

    private Double nplayVideos;

    private Double nchapters;

    private Double nforumPosts;

    public PreUsers(String uid, String cid, String registered, String viewed, String explored, String certified, String location, String learnerLevel, String age, String gender, String grade, Double timeSpan, Double nevents, Double ndays, Double nplayVideos, Double nchapters, Double nforumPosts) {
        super(uid, cid);
        this.registered = registered;
        this.viewed = viewed;
        this.explored = explored;
        this.certified = certified;
        this.location = location;
        this.learnerLevel = learnerLevel;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.timeSpan = timeSpan;
        this.nevents = nevents;
        this.ndays = ndays;
        this.nplayVideos = nplayVideos;
        this.nchapters = nchapters;
        this.nforumPosts = nforumPosts;
    }

    public PreUsers() {
        super();
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered == null ? null : registered.trim();
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed == null ? null : viewed.trim();
    }

    public String getExplored() {
        return explored;
    }

    public void setExplored(String explored) {
        this.explored = explored == null ? null : explored.trim();
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified == null ? null : certified.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getLearnerLevel() {
        return learnerLevel;
    }

    public void setLearnerLevel(String learnerLevel) {
        this.learnerLevel = learnerLevel == null ? null : learnerLevel.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Double getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Double timeSpan) {
        this.timeSpan = timeSpan;
    }

    public Double getNevents() {
        return nevents;
    }

    public void setNevents(Double nevents) {
        this.nevents = nevents;
    }

    public Double getNdays() {
        return ndays;
    }

    public void setNdays(Double ndays) {
        this.ndays = ndays;
    }

    public Double getNplayVideos() {
        return nplayVideos;
    }

    public void setNplayVideos(Double nplayVideos) {
        this.nplayVideos = nplayVideos;
    }

    public Double getNchapters() {
        return nchapters;
    }

    public void setNchapters(Double nchapters) {
        this.nchapters = nchapters;
    }

    public Double getNforumPosts() {
        return nforumPosts;
    }

    public void setNforumPosts(Double nforumPosts) {
        this.nforumPosts = nforumPosts;
    }
}