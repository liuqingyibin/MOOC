package com.edx.dao;

import com.edx.pojo.Cf;

public interface CfMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(Cf record);

    int insertSelective(Cf record);

    Cf selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(Cf record);

    int updateByPrimaryKey(Cf record);
}