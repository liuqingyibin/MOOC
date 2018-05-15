package com.edx.dao;

import com.edx.pojo.XuankeOverNiceCs;

public interface XuankeOverNiceCsMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(XuankeOverNiceCs record);

    int insertSelective(XuankeOverNiceCs record);

    XuankeOverNiceCs selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(XuankeOverNiceCs record);

    int updateByPrimaryKey(XuankeOverNiceCs record);
}