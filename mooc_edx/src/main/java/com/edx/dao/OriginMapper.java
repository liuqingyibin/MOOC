package com.edx.dao;

import com.edx.pojo.Origin;
import com.edx.pojo.OriginKey;

public interface OriginMapper {
    int deleteByPrimaryKey(OriginKey key);

    int insert(Origin record);

    int insertSelective(Origin record);

    Origin selectByPrimaryKey(OriginKey key);

    int updateByPrimaryKeySelective(Origin record);

    int updateByPrimaryKey(Origin record);
}