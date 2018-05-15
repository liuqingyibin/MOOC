package com.edx.dao;

import com.edx.pojo.PreUsers;
import com.edx.pojo.PreUsersKey;

public interface PreUsersMapper {
    int deleteByPrimaryKey(PreUsersKey key);

    int insert(PreUsers record);

    int insertSelective(PreUsers record);

    PreUsers selectByPrimaryKey(PreUsersKey key);

    int updateByPrimaryKeySelective(PreUsers record);

    int updateByPrimaryKey(PreUsers record);
}