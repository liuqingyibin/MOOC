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
         //TODO 这里后续增加一些登录判断，防止密码为空什么的
         System.out.println("这里进入了user/login");
         System.out.println("这里进入了user/login");
         System.out.println("这里进入了user/login");
        return userService.login(username,password);
     }



}
