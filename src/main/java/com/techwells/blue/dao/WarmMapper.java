package com.techwells.blue.dao;

import com.techwells.blue.domain.Warm;

public interface WarmMapper {
    int deleteByPrimaryKey(Integer warmId);

    int insert(Warm record);

    int insertSelective(Warm record);

    Warm selectByPrimaryKey(Integer warmId);

    int updateByPrimaryKeySelective(Warm record);

    int updateByPrimaryKey(Warm record);
}