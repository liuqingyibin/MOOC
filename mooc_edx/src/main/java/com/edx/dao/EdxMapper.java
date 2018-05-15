package com.edx.dao;

import com.edx.pojo.Edx;

public interface EdxMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(Edx record);

    int insertSelective(Edx record);

    Edx selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(Edx record);

    int updateByPrimaryKey(Edx record);
}