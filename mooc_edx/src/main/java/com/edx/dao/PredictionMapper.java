package com.edx.dao;

import com.edx.pojo.Prediction;
import com.edx.pojo.PredictionKey;

public interface PredictionMapper {
    int deleteByPrimaryKey(PredictionKey key);

    int insert(Prediction record);

    int insertSelective(Prediction record);

    Prediction selectByPrimaryKey(PredictionKey key);

    int updateByPrimaryKeySelective(Prediction record);

    int updateByPrimaryKey(Prediction record);
}