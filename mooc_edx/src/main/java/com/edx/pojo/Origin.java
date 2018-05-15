package com.edx.pojo;

public class Origin extends OriginKey {
    private String registered;

    private String viewed;

    private String explored;

    private String certified;

    private String location;

    private String learnerLevel;

    private String age;

    private String gender;

    private String grade;

    private String startTime;

    private String lastTime;

    private Double ndays;

    private Double nevents;

    private Double nplayVideos;

    private Double nchapters;

    private Double nforumPosts;

    public Origin(String uid, String cid, String registered, String viewed, String explored, String certified, String location, String learnerLevel, String age, String gender, String grade, String startTime, String lastTime, Double ndays, Double nevents, Double nplayVideos, Double nchapters, Double nforumPosts) {
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
        this.startTime = startTime;
        this.lastTime = lastTime;
        this.ndays = ndays;
        this.nevents = nevents;
        this.nplayVideos = nplayVideos;
        this.nchapters = nchapters;
        this.nforumPosts = nforumPosts;
    }

    public Origin() {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime == null ? null : lastTime.trim();
    }

    public Double getNdays() {
        return ndays;
    }

    public void setNdays(Double ndays) {
        this.ndays = ndays;
    }

    public Double getNevents() {
        return nevents;
    }

    public void setNevents(Double nevents) {
        this.nevents = nevents;
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