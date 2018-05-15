package com.edx.dao;

import com.edx.pojo.Test;

public interface TestMapper {
    int insert(Test record);

    int insertSelective(Test record);
}