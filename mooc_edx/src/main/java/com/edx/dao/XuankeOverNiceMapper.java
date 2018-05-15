package com.edx.dao;

import com.edx.pojo.XuankeOverNice;

public interface XuankeOverNiceMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(XuankeOverNice record);

    int insertSelective(XuankeOverNice record);

    XuankeOverNice selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(XuankeOverNice record);

    int updateByPrimaryKey(XuankeOverNice record);
}