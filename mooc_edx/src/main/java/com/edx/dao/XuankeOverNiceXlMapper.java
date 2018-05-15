package com.edx.dao;

import com.edx.pojo.XuankeOverNiceXl;

public interface XuankeOverNiceXlMapper {
    int deleteByPrimaryKey(Integer edxId);

    int insert(XuankeOverNiceXl record);

    int insertSelective(XuankeOverNiceXl record);

    XuankeOverNiceXl selectByPrimaryKey(Integer edxId);

    int updateByPrimaryKeySelective(XuankeOverNiceXl record);

    int updateByPrimaryKey(XuankeOverNiceXl record);
}