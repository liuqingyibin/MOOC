package com.edx.controller.work;


import com.edx.service.StudentService;
import com.edx.service.TeacherService;
import com.edx.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.xuren.*;
/**
 * 业务Controller层，四个模块在这里进行处理
 */

/**
 * Created by HP on 2018/5/15.
 */
@Controller
@RequestMapping("/work/")
public class WorkController {
/**
 * 每个人定义一个模块，方法已经给你们定义好，自己业务写一下，调用相关方法，后面还是采用
 * jdbc连接数据库，算每个人自己的代码量，用mybatis太麻烦了，代码少不值当的
 *
 * json已经给封装好了，那个注解@ResonseBody就是，只需要你们返回相应的数据就好了，类似于我
 * 给你们写好的这个map集合，然后后台会自动封装成json
 */

@Autowired
private final static WorkService workService = new WorkService();




    /**
     *徐任代码模块
     */
    @RequestMapping(value = "xuren.do",method = RequestMethod.POST)
    @ResponseBody
    public Object xuren(String userId,String courseId,HttpSession session){

        HashMap<String, ArrayList<XYZ>> map = new HashMap<String,ArrayList<XYZ>>();

        k_means_ablity_new k=new k_means_ablity_new(5);
        k.kmeans();
        System.out.println(k.getXyzCluster().size()+"------------------------");
        for(int i=0;i<k.getXyzCluster().size();i++){
            map.put("cluster"+i,k.getXyzCluster().get(i));
        }
//        map.put("1",k.getCluster().get(0));
        return map;
    }


    /**
     *王阳代码模块
     */
    @RequestMapping(value = "wangyang.do",method = RequestMethod.POST)
    @ResponseBody
    public Object wangyang(String userId,String courseId,HttpSession session){
        Map<String,String> map = new HashMap<String,String>();
        map.put("course1","程序设计高级C语言");
        map.put("course2","Java基础篇");
        map.put("course3","Java进阶篇");
        return map;
    }



    /**
     * 学生学习效果评价模型
     * 柳青的模块，传入用户id，返回相应的处理数据
     * @param userId
     * @param courseId
     * @param session
     * @return
     */
    @RequestMapping(value = "liuqing.do",method = RequestMethod.POST)
    @ResponseBody
    public Object liuqing(String userId,String courseId,HttpSession session) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */

          return workService.getMenuWeight(edx_id);

    }

    //getInteractionAndGrade
    @RequestMapping(value = "getInteractionAndGrade.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getInteractionAndGrade(String userId,String courseId,HttpSession session) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */

        return workService.getMenuWeight(edx_id);

    }
    //getStudentList
    @RequestMapping(value = "getStudentList.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getStudentList(String userId,String courseId,HttpSession session) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */

        return workService.getMenuWeight(edx_id);
    }


    /***********************王思卓代码模块********************************************/

    /**
     * 教师获得课程列表(页面1）
     * @param session           session
     * @return                  课程列表[课程号：选课人数，获得证书人数]
     */
    @RequestMapping(value = "getCourseList.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getCourseList( HttpSession session) {
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getCourseList();
    }

    /**
     * 教师查看某一门课程的交互情况(页面2)   图1（四合一散点图图）
     * @param cid               课程号
     * @param session           session
     * @return  [grade:所有的grade值,nevents:所有的nevents值,nplay_videos :所有的nplay_videos值, nchapters:所有的nchapters值, nforum_posts：所有的nforum_posts值]
     * 返回值纵向看，每一列数据属于一个人，横向看是选课人数
     */
    @RequestMapping(value = "getInteractionAndGrade.do/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getOneCourseInteraction(@PathVariable String cid, HttpSession session){
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getInteractionAndGrade(cid);
    }

    /**
     *教师查看所有学生的地区分布(页面2)  图2，柱形图
     * @param cid           课程号
     * @param session       sesssion
     * @return              [地区名:选课人数]
     */
    @RequestMapping(value = "getLocation.do/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getLocation( @PathVariable String cid, HttpSession session) {
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getLocation(cid);
    }

    /**
     *教师查看某一课程各个属性人数和获得证书人数的关系(页面2)      图3,4,5折柱混合图，柱形图表示总人数，折线图表示取得证书人数
     * @param cid       课程号
     * @param kind      待查询属性，只有三个值：learner_level,age,gender
     * @param session   session
     * @return             [ 属性分类：[该属性总人数，该属性取得证书人数]]
     *  learner_level-------{"Bachelor":[10456,474],"Doctorate":[2360,127],"empty":[670,73],"Less than Secondary":[133,3],"Master":[10562,692],"Secondary":[3314,80]}
     *  age-----{"10-20":[24,null],"20-30":[7423,300],"30-40":[13786,815],"40-50":[4101,220],"50-60":[1121,37],"<10":[20,null],">60":[428,7],"empty":[592,70]}
     *  gender----{"empty":[350,61],"female":[11525,646],"male":[15620,742]}
     */
    @RequestMapping(value = "attributeWithCertified.do/{cid}/{kid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getAttrAndCertified(@PathVariable String cid,@PathVariable String kind, HttpSession session) {
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.attributeWithCertified(cid,kind);
    }

    /**
     * 查看成绩 页面2 图6  折线图
     * @param cid       课程号
     * @param session   session
     * @return      [[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]``````]
     */
    @RequestMapping(value = "getGrade.do/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getTeacherAllInteractionChart(@PathVariable String cid,HttpSession session) {
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getGrade(cid);
    }
    /**
     * 获得某一门课程的选课学生列表(页面3)
     * @param cid           课程号
     * @param pageNo        页码
     * @param session       session
     * @return              学号:[地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书]
     * {"MHxPC130000030":["Brazil","Secondary","28","male","0","2012/7/31","2012/10/10","2","2","0","0","0","no"],
     * "MHxPC130000049":["United States","Master","45","male","0","2012/8/13","2012/8/13","1","1","0","0","0","no"],
     * "MHxPC130000301":["Egypt","Bachelor","35","male","0.03","2012/10/5","2013/6/7","316","7","80","2","0","no"]}
     */
    @RequestMapping(value = "getStudentList.do/{cid}/{pageNo}",method = RequestMethod.POST)
    @ResponseBody
    public Object getStudentList(@PathVariable String cid, @PathVariable int pageNo, HttpSession session) {
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getStudentList(cid, pageNo);
    }

    /**
     * 预测 页面4  教师学生通用
     * @param cid       课程 号
     * @param path      文件路径
     * @param session   sesssion
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    @RequestMapping(value = "prediction.do/{cid}/{path}",method = RequestMethod.POST)
    @ResponseBody
    public Object prediction(@PathVariable String cid, @PathVariable String path, HttpSession session){
        String uid = session.getAttribute("uid").toString();
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.importPredictionData(path,cid);
    }

    /**
     *学生获得选课列表(页面5)
     * @param pageNo        页码
     * @param session       session
     * @return              课程号:课程信息
     */
    @RequestMapping(value = "getAllCourseInfo.do/{pageNo}",method = RequestMethod.POST)
    @ResponseBody
    public Object getMyCourseList( @PathVariable int pageNo,HttpSession session) throws SQLException {
        String uid = session.getAttribute("uid").toString();
        StudentService s = new StudentService(uid);
        return s.getAllCourseInfo(pageNo);
    }




}
