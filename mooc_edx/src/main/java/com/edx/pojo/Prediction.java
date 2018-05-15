package com.edx.pojo;

public class Prediction extends PredictionKey {
    private String identify;

    private String certified;

    private String location;

    private String learnerLevel;

    private String age;

    private String gender;

    private String grade;

    private Double interaction;

    public Prediction(String uid, String cid, String identify, String certified, String location, String learnerLevel, String age, String gender, String grade, Double interaction) {
        super(uid, cid);
        this.identify = identify;
        this.certified = certified;
        this.location = location;
        this.learnerLevel = learnerLevel;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.interaction = interaction;
    }

    public Prediction() {
        super();
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify == null ? null : identify.trim();
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

    public Double getInteraction() {
        return interaction;
    }

    public void setInteraction(Double interaction) {
        this.interaction = interaction;
    }
}