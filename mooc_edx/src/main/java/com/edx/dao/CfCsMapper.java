package com.edx.dao;

import com.edx.pojo.CfCs;

public interface CfCsMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(CfCs record);

    int insertSelective(CfCs record);

    CfCs selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(CfCs record);

    int updateByPrimaryKey(CfCs record);
}