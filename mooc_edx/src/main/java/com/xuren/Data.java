package com.xuren;

public class Data {
	private double x;
	private double y;
	private double z;
	private int edx_id;
	private String course_id;
	private String userid_DI;
	private String regisitered;// 是否注册
	private String finall_cc_cname_DI;// 地理位置
	private String yoB;// 出生年
	private String gender;// 性别
	private String start_time_DI;// 注册日期
	
	private String loE_DI;// 学位12345 ok
	private String viewed;// 访问课件的人 ok
	private String explored;// 课件看了一半 ok 
	private String certified;// 拿了证书的人 ok 
	
	private double grade;// 成绩 ok(0.01-0.89)
	private double nevents;// 交互次数 最大197757(23-14668)
	private double ndays_act;// 互动天数 最大205(1-99)
	private double nplay_video;// 播放次数 最大98517(0-5128)
	private double nchapters;// 互动章节数 最大48(1-11)
	private double nforum_posts;// 帖子数 最大20(3)
	
	private String last_event_DI;// 最后互动时间，没注册空白
    
	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getEdx_id() {
		return edx_id;
	}

	public void setEdx_id(int edx_id) {
		this.edx_id = edx_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getUserid_DI() {
		return userid_DI;
	}

	public void setUserid_DI(String userid_DI) {
		this.userid_DI = userid_DI;
	}

	public String getRegisitered() {
		return regisitered;
	}

	public void setRegisitered(String regisitered) {
		this.regisitered = regisitered;
	}

	public String getFinall_cc_cname_DI() {
		return finall_cc_cname_DI;
	}

	public void setFinall_cc_cname_DI(String finall_cc_cname_DI) {
		this.finall_cc_cname_DI = finall_cc_cname_DI;
	}

	public String getYoB() {
		return yoB;
	}

	public void setYoB(String yoB) {
		this.yoB = yoB;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStart_time_DI() {
		return start_time_DI;
	}

	public void setStart_time_DI(String start_time_DI) {
		this.start_time_DI = start_time_DI;
	}

	public String getLoE_DI() {
		return loE_DI;
	}

	public void setLoE_DI(String loE_DI) {
		this.loE_DI = loE_DI;
	}

	public String getViewed() {
		return viewed;
	}

	public void setViewed(String viewed) {
		this.viewed = viewed;
	}

	public String getExplored() {
		return explored;
	}

	public void setExplored(String explored) {
		this.explored = explored;
	}

	public String getCertified() {
		return certified;
	}

	public void setCertified(String certified) {
		this.certified = certified;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public double getNevents() {
		return nevents;
	}

	public void setNevents(double nevents) {
		this.nevents = nevents;
	}

	public double getNdays_act() {
		return ndays_act;
	}

	public void setNdays_act(double ndays_act) {
		this.ndays_act = ndays_act;
	}

	public double getNplay_video() {
		return nplay_video;
	}

	public void setNplay_video(double nplay_video) {
		this.nplay_video = nplay_video;
	}

	public double getNchapters() {
		return nchapters;
	}

	public void setNchapters(double nchapters) {
		this.nchapters = nchapters;
	}

	public double getNforum_posts() {
		return nforum_posts;
	}

	public void setNforum_posts(double nforum_posts) {
		this.nforum_posts = nforum_posts;
	}

	public String getLast_event_DI() {
		return last_event_DI;
	}

	public void setLast_event_DI(String last_event_DI) {
		this.last_event_DI = last_event_DI;
	}

	

}
