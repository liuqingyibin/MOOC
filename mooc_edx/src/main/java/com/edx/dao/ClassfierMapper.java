package com.edx.dao;

import com.edx.pojo.Classfier;
import com.edx.pojo.ClassfierKey;

public interface ClassfierMapper {
    int deleteByPrimaryKey(ClassfierKey key);

    int insert(Classfier record);

    int insertSelective(Classfier record);

    Classfier selectByPrimaryKey(ClassfierKey key);

    int updateByPrimaryKeySelective(Classfier record);

    int updateByPrimaryKey(Classfier record);
}