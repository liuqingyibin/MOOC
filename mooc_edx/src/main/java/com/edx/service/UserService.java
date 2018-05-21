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

    public HashMap<String, String> login(String username, String password) {

        return userDao.login(username,password);
    }
}
