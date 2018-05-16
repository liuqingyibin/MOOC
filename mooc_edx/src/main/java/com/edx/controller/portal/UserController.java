package com.edx.controller.portal;

import com.edx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    private IUserService iUserService;


    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
//    @RequestMapping(value = "login.do",method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<User> login(String username, String password , HttpSession session){
//        //service-->mybatis__dao
//        ServerResponse<User> response = iUserService.login(username,password);
//        if(response.isSuccess()){
//            session.setAttribute(Const.CURRENT_USER,response.getData());
//        }
//        return response;
//    }




}
