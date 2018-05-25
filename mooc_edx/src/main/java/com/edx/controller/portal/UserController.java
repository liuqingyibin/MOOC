package com.edx.controller.portal;

import com.edx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
     * 用户登录接口
     * @param username
     * @param password
     * @return
     */
     @RequestMapping(value = "login.do",method = RequestMethod.POST)
     @ResponseBody
     public Object login(String username,String password){


        return userService.login(username,password);
     }

    /**
     * 欢迎页面获取登录学生的个人信息数据
     * @param userId
     * @return
     */
    @RequestMapping(value = "getStudent.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getStudent(String userId){

        System.out.println("Controller这里进入了1");
        userId = "547";
        return userService.getStudent(userId);
    }


    @RequestMapping(value = "getCourseList.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getCourseList(String username,String password){


        return userService.login(username,password);
    }



}
