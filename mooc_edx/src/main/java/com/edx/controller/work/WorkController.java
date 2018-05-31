package com.edx.controller.work;


import com.edx.service.WorkService;
import com.wangsizhuo.service.StudentService;
import com.wangsizhuo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("explored",0.10);
        return map;
    }


    /**
     *王阳代码模块
     */
    @RequestMapping(value = "wangyang.do",method = RequestMethod.POST)
    @ResponseBody
    public Object wangyang(String userId,String courseId,HttpSession session){
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("ndays_act",0.10);
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

    /***********************王思卓代码模块********************************************/
    private final static String tid = "jiaoshi";
    private final static String uid = "MHxPC130569306";
    private final static String cid = "HarvardX/PH278x/2013_Spring";
    /**
     * 教师获得课程列表(页面1）
     * @return                  课程列表[课程号：选课人数，获得证书人数]
     */
    @RequestMapping(value = "cList.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getCList( HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        return teacherService.getCourseList();
    }

    /**
     * 教师查看某一门课程的交互情况(页面2)   图1（四合一散点图图）
     * @return  [grade:所有的grade值,nevents:所有的nevents值,nplay_videos :所有的nplay_videos值, nchapters:所有的nchapters值, nforum_posts：所有的nforum_posts值]
     * 返回值纵向看，每一列数据属于一个人，横向看是选课人数
     */
    @RequestMapping(value = "InteractionAndGrade.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getOneCourseInteraction(HttpServletResponse response, HttpServletRequest request){
        TeacherService teacherService = new TeacherService(tid);
        return teacherService.getInteractionAndGrade(cid);
    }

    /**
     *教师查看所有学生的地区分布(页面2)  图2，柱形图
     * @return              [地区名:选课人数]
     */
    @RequestMapping(value = "location.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getOneCourseLocation(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        return teacherService.getLocation(cid);
    }

    /**
     *教师查看某一课程各个属性人数和获得证书人数的关系(页面2)      图3,4,5折柱混合图，柱形图表示总人数，折线图表示取得证书人数
     * @return             [ 属性分类：[该属性总人数，该属性取得证书人数]]
     */
    @RequestMapping(value = "awc.do/{kind}",method = RequestMethod.GET)
    @ResponseBody
    public Object getAttrAndCertified(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        String kind = request.getParameter("kind");
        return teacherService.attributeWithCertified(cid,kind);
    }

    /**
     * 查看成绩 页面2 图6  折线图
     * @return      [[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]``````]
     */
    @RequestMapping(value = "grade.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getGradeChart(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        return teacherService.getGrade(cid);
    }
    /**
     * 获得某一门课程的选课学生列表(页面3)
     * @return              学号:[地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互天数 播放视频数 观看章节数 论坛发帖数 取得证书]
     */
    @RequestMapping(value = "sList.do/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public Object getStuList(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        String pageNo = request.getParameter("pageNo");
        return teacherService.getStudentList(cid, Integer.parseInt(pageNo));
    }

    /**
     * 预测 页面4  教师用
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    @RequestMapping(value = "tp.do",method = RequestMethod.GET)
    @ResponseBody
    public Object teacherPrediction(HttpServletResponse response, HttpServletRequest request){
        TeacherService teacherService = new TeacherService(tid);
        return teacherService.importPredictionData(cid);
    }

    /**
     * 预测 页面4  学生用
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    @RequestMapping(value = "sp.do",method = RequestMethod.GET)
    @ResponseBody
    public Object studentPrediction( HttpServletResponse response, HttpServletRequest request){
        StudentService studentService = new StudentService(uid);
        return studentService.importPredictionData();
    }

    /**
     *学生获得选课列表(页面5)
     * @return              课程号:课程信息
     */
    @RequestMapping(value = "cInfo.do/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public Object getMyCourseList(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        StudentService s = new StudentService(uid);
        String pageNo = request.getParameter("pageNo");
        return s.getAllCourseInfo(Integer.parseInt(pageNo));
    }
}
