package com.edx.dao;

import com.edx.pojo.Teacher;

public interface TeacherMapper {
    int insert(Teacher record);

    int insertSelective(Teacher record);
}