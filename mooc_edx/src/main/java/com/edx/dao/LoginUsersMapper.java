package com.edx.dao;

import com.edx.pojo.LoginUsers;

public interface LoginUsersMapper {
    int insert(LoginUsers record);

    int insertSelective(LoginUsers record);
}