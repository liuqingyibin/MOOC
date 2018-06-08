package com.edx.controller.work;




import com.edx.service.StudentService;
import com.edx.service.TeacherService;
import com.edx.service.WorkService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    @RequestMapping(value = "xuren.do",method = RequestMethod.GET)
    @ResponseBody
    public Object xuren(String userId,String courseId,HttpSession session){

//        HashMap<String, ArrayList<XYZ>> map = new HashMap<String,ArrayList<XYZ>>();
        Map<String,ArrayList<Double>> map=new HashMap<String,ArrayList<Double>>();
        k_means_ablity_new k=new k_means_ablity_new(5);
        k.kmeans();
        System.out.println(k.getXyzCluster().size()+"------------------------");
        String x=null;
        String y=null;
        String z=null;
        String id=null;
        ArrayList<Double> x_arr=null;
        ArrayList<Double> y_arr=null;
        ArrayList<Double> z_arr=null;
        ArrayList<Double> id_arr=null;
        for(int i=0;i<k.getCluster().size();i++){
            x_arr=new ArrayList<Double>();
            y_arr=new ArrayList<Double>();
            z_arr=new ArrayList<Double>();
            id_arr=new ArrayList<Double>();
            x="x"+i;
            y="y"+i;
            z="z"+i;
            id="id"+i;
            for(int j=0;j<k.getCluster().get(i).size();j++){
                Data data=k.getCluster().get(i).get(j);
                x_arr.add(data.getX());
                y_arr.add(data.getY());
                z_arr.add(data.getZ());
                id_arr.add((double)data.getEdx_id());
            }

            map.put(x,x_arr);
            map.put(y,y_arr);
            map.put(z,z_arr);
            map.put(id,id_arr);
        }
//        for(int i=0;i<k.getXyzCluster().size();i++){
//            map.put("cluster"+i,k.getXyzCluster().get(i));
//        }
//        map.put("1",k.getCluster().get(0));
        JSONObject resultJson = JSONObject.fromObject(map);
        return "jsonpCallback("+resultJson.toString()+")";
    }


    /**
     *王阳代码模块
     */
    @RequestMapping(value = "wangyang.do",method = RequestMethod.GET)
    @ResponseBody
    public Object wangyang(String userId,String courseId,HttpSession session){
        Map<String,String> map = new HashMap<String,String>();
        map.put("course1","程序设计高级C语言");
        map.put("course2","Java基础篇");
        map.put("course3","Java进阶篇");
        JSONObject resultJson = JSONObject.fromObject(map);
        return "jsonpCallback("+resultJson.toString()+")";

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 学生学习效果评价模型
     * 柳青的模块，传入用户id，返回相应的处理数据
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "liuqing.do",method = RequestMethod.GET)
    @ResponseBody
    public String liuqing(String callback) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */

        JSONObject resultJson = JSONObject.fromObject(workService.getMenuWeight(edx_id));
        return "jsonpCallback("+resultJson.toString()+")";

    }

    @RequestMapping(value = "liuqingGradeAll.do",method = RequestMethod.GET)
    @ResponseBody
    public String liuqingGradeAll(String callback) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */
        JSONObject resultJson = JSONObject.fromObject(workService.getGradeAll(edx_id));

//        JSONObject resultJson = JSONObject.fromObject(workService.getGradeAll(edx_id));

        return "jsonpCallback("+resultJson.toString()+")";

    }

    @RequestMapping(value = "liuqingRank.do",method = RequestMethod.GET)
    @ResponseBody
    public String liuqingRank(String callback) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */

//        JSONObject resultJson = JSONObject.fromObject(workService.liuqingRank(edx_id));

        JSONObject resultJson = JSONObject.fromObject(workService.getRank(edx_id));
        return "jsonpCallback("+resultJson.toString()+")";

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    //getStudentList
    @RequestMapping(value = "getStudentList.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getStudentList(String userId,String courseId,HttpSession session) throws SQLException {
        String edx_id = "547";

        /**
         *预先定义四个menu，之后动态获取判断
         */
        JSONObject resultJson = JSONObject.fromObject(workService.getMenuWeight(edx_id));
        return "jsonpCallback("+resultJson.toString()+")";
//        return workService.getMenuWeight(edx_id);
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
    public Object getCList( HttpSession session) {
        TeacherService teacherService = new TeacherService(tid);
//        return teacherService.getCourseList();
        JSONObject resultJson = JSONObject.fromObject(teacherService.getCourseList());
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     * 教师查看某一门课程的交互情况(页面2)   图1（四合一散点图图）
     * @return  [grade:所有的grade值,nevents:所有的nevents值,nplay_videos :所有的nplay_videos值, nchapters:所有的nchapters值, nforum_posts：所有的nforum_posts值]
     * 返回值纵向看，每一列数据属于一个人，横向看是选课人数
     */
    @RequestMapping(value = "InteractionAndGrade.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getOneCourseInteraction(HttpSession session){
        TeacherService teacherService = new TeacherService(tid);
//        return teacherService.getInteractionAndGrade(cid);
        JSONObject resultJson = JSONObject.fromObject(teacherService.getInteractionAndGrade(cid));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     *教师查看所有学生的地区分布(页面2)  图2，柱形图
     * @return              [地区名:选课人数]
     */
    @RequestMapping(value = "location.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getOneCourseLocation(HttpSession session) {
        TeacherService teacherService = new TeacherService(tid);
//        return teacherService.getLocation(cid);
        JSONObject resultJson = JSONObject.fromObject(teacherService.getLocation(cid));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     *教师查看某一课程各个属性人数和获得证书人数的关系(页面2)      图3,4,5折柱混合图，柱形图表示总人数，折线图表示取得证书人数
     * @return             [ 属性分类：[该属性总人数，该属性取得证书人数]]
     */
    @RequestMapping(value = "awc.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getAttrAndCertified(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
//        return teacherService.attributeWithCertified(cid,kind);
        String kind = request.getParameter("kind");
        JSONObject resultJson = JSONObject.fromObject(teacherService.attributeWithCertified(cid,kind));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     * 查看成绩 页面2 图6  折线图
     * @param session   session
     * @return      [[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]，[成绩：人数]``````]
     */
    @RequestMapping(value = "grade.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getGradeChart(HttpSession session) {
        TeacherService teacherService = new TeacherService(tid);
        JSONArray resultJson = JSONArray.fromObject(teacherService.getGrade(cid));
        return "jsonpCallback("+resultJson.toString()+")";
    }
    /**
     * 获得某一门课程的选课学生列表(页面3)
     * @return              学号:[地区 学历 性别 成绩 注册时间 最后登录时间 交互次数 交互;天数 播放视频数 观看章节数 论坛发帖数 取得证书]
     */
    @RequestMapping(value = "sList.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getStuList(HttpServletResponse response, HttpServletRequest request) {
        TeacherService teacherService = new TeacherService(tid);
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));
        JSONObject resultJson = JSONObject.fromObject(teacherService.getStudentList(cid, pageNo));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     * 预测 页面4  教师用

     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    @RequestMapping(value = "tp.do",method = RequestMethod.GET)
    @ResponseBody
    public Object teacherPrediction(HttpServletResponse response, HttpServletRequest request){
        TeacherService teacherService = new TeacherService(uid);
        JSONObject resultJson = JSONObject.fromObject(teacherService.importPredictionData(cid));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     * 预测 页面4  学生用
     * @return [学号：[课程号，能否通过],学号：[课程号，能否通过]``````]
     */
    @RequestMapping(value = "sp.do",method = RequestMethod.GET)
    @ResponseBody
    public Object studentPrediction(HttpServletResponse response, HttpServletRequest request){
        StudentService studentService = new StudentService(uid);
        JSONObject resultJson = JSONObject.fromObject(studentService.importPredictionData());
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     *学生获得选课列表(页面5)
     * @return              课程号:课程信息
     */
    @RequestMapping(value = "cInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getMyCourseList(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        StudentService s = new StudentService(uid);
//      int pa  return s.getAllCourseInfo(pageNo);
//        System.out.println("进入了这里");
        String string = request.getParameter("pageNo");
//        System.out.println("string"+string);
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));

        JSONObject resultJson = JSONObject.fromObject(s.getAllCourseInfo(pageNo));
        return "jsonpCallback("+resultJson.toString()+")";
    }

}
