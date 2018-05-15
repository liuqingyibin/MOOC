package com.edx.pojo;

public class XuankeOverNiceXl {
    private Integer edxId;

    private String hcourseId;

    private String useridDi;

    private String registered;

    private String viewed;

    private String explored;

    private String certified;

    private String finalCcCnameDi;

    private String loeDi;

    private String yob;

    private String gender;

    private String grade;

    private String startTimeDi;

    private String lastEventDi;

    private String nevents;

    private String ndaysAct;

    private String nplayVideo;

    private String nchapters;

    private String nforumPosts;

    private String roles;

    private String incompleteFlag;

    public XuankeOverNiceXl(Integer edxId, String hcourseId, String useridDi, String registered, String viewed, String explored, String certified, String finalCcCnameDi, String loeDi, String yob, String gender, String grade, String startTimeDi, String lastEventDi, String nevents, String ndaysAct, String nplayVideo, String nchapters, String nforumPosts, String roles, String incompleteFlag) {
        this.edxId = edxId;
        this.hcourseId = hcourseId;
        this.useridDi = useridDi;
        this.registered = registered;
        this.viewed = viewed;
        this.explored = explored;
        this.certified = certified;
        this.finalCcCnameDi = finalCcCnameDi;
        this.loeDi = loeDi;
        this.yob = yob;
        this.gender = gender;
        this.grade = grade;
        this.startTimeDi = startTimeDi;
        this.lastEventDi = lastEventDi;
        this.nevents = nevents;
        this.ndaysAct = ndaysAct;
        this.nplayVideo = nplayVideo;
        this.nchapters = nchapters;
        this.nforumPosts = nforumPosts;
        this.roles = roles;
        this.incompleteFlag = incompleteFlag;
    }

    public XuankeOverNiceXl() {
        super();
    }

    public Integer getEdxId() {
        return edxId;
    }

    public void setEdxId(Integer edxId) {
        this.edxId = edxId;
    }

    public String getHcourseId() {
        return hcourseId;
    }

    public void setHcourseId(String hcourseId) {
        this.hcourseId = hcourseId == null ? null : hcourseId.trim();
    }

    public String getUseridDi() {
        return useridDi;
    }

    public void setUseridDi(String useridDi) {
        this.useridDi = useridDi == null ? null : useridDi.trim();
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

    public String getFinalCcCnameDi() {
        return finalCcCnameDi;
    }

    public void setFinalCcCnameDi(String finalCcCnameDi) {
        this.finalCcCnameDi = finalCcCnameDi == null ? null : finalCcCnameDi.trim();
    }

    public String getLoeDi() {
        return loeDi;
    }

    public void setLoeDi(String loeDi) {
        this.loeDi = loeDi == null ? null : loeDi.trim();
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob == null ? null : yob.trim();
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

    public String getStartTimeDi() {
        return startTimeDi;
    }

    public void setStartTimeDi(String startTimeDi) {
        this.startTimeDi = startTimeDi == null ? null : startTimeDi.trim();
    }

    public String getLastEventDi() {
        return lastEventDi;
    }

    public void setLastEventDi(String lastEventDi) {
        this.lastEventDi = lastEventDi == null ? null : lastEventDi.trim();
    }

    public String getNevents() {
        return nevents;
    }

    public void setNevents(String nevents) {
        this.nevents = nevents == null ? null : nevents.trim();
    }

    public String getNdaysAct() {
        return ndaysAct;
    }

    public void setNdaysAct(String ndaysAct) {
        this.ndaysAct = ndaysAct == null ? null : ndaysAct.trim();
    }

    public String getNplayVideo() {
        return nplayVideo;
    }

    public void setNplayVideo(String nplayVideo) {
        this.nplayVideo = nplayVideo == null ? null : nplayVideo.trim();
    }

    public String getNchapters() {
        return nchapters;
    }

    public void setNchapters(String nchapters) {
        this.nchapters = nchapters == null ? null : nchapters.trim();
    }

    public String getNforumPosts() {
        return nforumPosts;
    }

    public void setNforumPosts(String nforumPosts) {
        this.nforumPosts = nforumPosts == null ? null : nforumPosts.trim();
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    public String getIncompleteFlag() {
        return incompleteFlag;
    }

    public void setIncompleteFlag(String incompleteFlag) {
        this.incompleteFlag = incompleteFlag == null ? null : incompleteFlag.trim();
    }
}