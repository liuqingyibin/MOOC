package com.edx.service.impl;

import com.edx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HP on 2018/5/15.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Override
    public Object login(String username, String password) {
        return null;
    }
}
