package com.edx.service;

import com.edx.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by HP on 2018/5/17.
 */
@Service
public class UserService {

    private final static UserDao userDao = new UserDao();

    /**
     * 用户登录后台判断
     * @param username
     * @param password
     * @return
     */
    public HashMap<String, String> login(String username, String password) {

        return userDao.login(username,password);
    }


    /**
     * 得到用户的十项个人信息展示
     * @param
     * @param
     * @return
     */
    public HashMap<String, String> getStudent(String id) {

        return userDao.getStudent(id);
    }

}
