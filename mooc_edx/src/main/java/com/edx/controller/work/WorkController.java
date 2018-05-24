package com.edx.controller.work;


import com.edx.service.WorkService;
import com.wangsizhuo.service.StudentService;
import com.wangsizhuo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    //教师获得课程列表(页面1）
    @RequestMapping(value = "getCourseList.do/{uid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getCourseList(@PathVariable("uid") String uid, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getCourseList();
    }

    //教师查看某一门课程的交互情况(页面2)
    // 图1（四合一图）
    @RequestMapping(value = "getOneCourseChart.do/{uid}/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getOneCourseInteraction(@PathVariable("uid") String uid,
                                          @PathVariable String cid, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getInteractionAndGrade(cid);
    }

    //教师查看所有学生的地区分布(页面2)
    //图2
    @RequestMapping(value = "getAllCourseChart.do/{uid}/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getLocation(@PathVariable("uid") String uid, @PathVariable String cid, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getLocation(cid);
    }

    //教师查看某一课程各个属性人数和获得证书人数的关系(页面2)
    // 属性：学历、年龄、性别
    //图3,4,5
    @RequestMapping(value = "getStudentList.do/{uid}/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public Object getAttrAndCertified(@PathVariable("uid") String uid,
                                      @PathVariable String cid, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        Map<String,Map<String,ArrayList<Object>>> map = new HashMap<>();
        map.put("gender",teacherService.attributeWithCertified(cid,"gender"));
        map.put("age",teacherService.attributeWithCertified(cid,"age"));
        map.put("level",teacherService.attributeWithCertified(cid,"level"));
        return map;
    }

    //教师查询某一课程交互情况的统计图(页面2)
    //交互：总次数，观看视频数，学习章节数，论坛发帖数
    //图6,7,8,9
    @RequestMapping(value = "getGrade.do/{uid}/{cid}/{kind}",method = RequestMethod.POST)
    @ResponseBody
    public Object getTeacherAllInteractionChart(@PathVariable String uid,@PathVariable String cid,
                                                HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getGrade(cid);
    }

    //获得某一门课程的选课学生列表(页面3)
    @RequestMapping(value = "getStudentList.do/{uid}/{cid}/{pageNo}/{pageSize}",method = RequestMethod.POST)
    @ResponseBody
    public Object getStudentList(@PathVariable("uid") String uid,
                                 @PathVariable String cid, @PathVariable int pageNo, @PathVariable int pageSize, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(uid);
        return teacherService.getStudentList(cid, pageNo, pageSize);
    }

    //预测(页面4)
    //教师学生通用
    @RequestMapping(value = "prediction.do/{uid}/{cid}/{path}",method = RequestMethod.POST)
    @ResponseBody
    public void prediction(@PathVariable("tid") String tid, @PathVariable String cid,
                           @PathVariable String path, HttpSession session) throws SQLException {
        TeacherService teacherService = new TeacherService(tid);
        teacherService.importPredictionData(path,cid);
    }

    //学生获得选课列表(页面5)
    @RequestMapping(value = "getMyCourseList.do/{uid}/{pageNo}/{pageSize}",method = RequestMethod.POST)
    @ResponseBody
    public Object getMyCourseList(@PathVariable String uid, @PathVariable int pageNo, @PathVariable int pageSize, HttpSession session) throws SQLException {
        StudentService s = new StudentService(uid);
        return s.getAllCourseInfo(pageNo,pageSize);
    }

}
