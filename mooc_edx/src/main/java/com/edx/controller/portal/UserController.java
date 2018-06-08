package com.edx.controller.portal;

import com.edx.service.UserService;
import com.edx.service.WorkService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Created by HP on 2018/5/15.
 */

/**
 * 用户操作信息模块
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private final static UserService userService = new UserService();

    @Autowired
    private final static WorkService workService = new WorkService();

    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
     @RequestMapping(value = "login.do",method = RequestMethod.GET)
     @ResponseBody
     public Object login(String username,String password){


//        return userService.login(username,password);
         JSONObject resultJson = JSONObject.fromObject(userService.login(username,password));
         return "jsonpCallback("+resultJson.toString()+")";
     }

    /**
     * 欢迎页面获取登录学生的个人信息数据
     * @param userId
     * @return
     */
    @RequestMapping(value = "getStudent.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getStudent(String userId){

        System.out.println("Controller这里进入了1");
        userId = "547";
//        return userService.getStudent(userId);
        JSONObject resultJson = JSONObject.fromObject(userService.getStudent(userId));
        return "jsonpCallback("+resultJson.toString()+")";
    }

    /**
     * 学生进入学习效果评价首页获取信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "liuqingOverShow.do",method = RequestMethod.GET)
    @ResponseBody
    public Object liuqingOverShow(String userId){

        System.out.println("Controller这里进入了1");
        userId = "547";
        JSONObject resultJson = null;
        try {

            resultJson = JSONObject.fromObject(workService.getMenuWeight(userId));

            return "jsonpCallback("+resultJson.toString()+")";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println();
            return "jsonpCallback("+"学生查询接口后台操作失败，请检查服务器环境！"+")";

        }


    }

    /**
     * 老师展示所有学生的个人信息数据
     * @param userId
     * @return
     */
    @RequestMapping(value = "liuqingTeacherShow.do",method = RequestMethod.GET)
    @ResponseBody
    public Object liuqingTeacherShow(String userId){
        userId = "547";
        JSONObject resultJson = JSONObject.fromObject(userService.liuqingTeacherShow(userId));
        return "jsonpCallback("+resultJson.toString()+")";
    }




    @RequestMapping(value = "getCourseList.do",method = RequestMethod.GET)
    @ResponseBody
    public Object getCourseList(String username,String password){


//        return userService.login(username,password);
        JSONObject resultJson = JSONObject.fromObject(userService.login(username,password));
        return "jsonpCallback("+resultJson.toString()+")";
    }



}
