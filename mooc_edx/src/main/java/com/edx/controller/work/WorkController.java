package com.edx.controller.work;


import com.edx.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
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
     *王思卓代码模块
     */
    @RequestMapping(value = "wangsizhuo.do",method = RequestMethod.POST)
    @ResponseBody
    public Object wangsizhuo(String userId,String courseId,HttpSession session){
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("viewd",0.10);
        map.put("vie1",0.20);
        return map;
    }

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






}
