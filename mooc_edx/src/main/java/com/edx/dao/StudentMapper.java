package com.edx.dao;

import com.edx.pojo.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Byte studentId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Byte studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}