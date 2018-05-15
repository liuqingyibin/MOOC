package com.edx.dao;

import com.edx.pojo.Grade;

public interface GradeMapper {
    int deleteByPrimaryKey(Byte studentId);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Byte studentId);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
}