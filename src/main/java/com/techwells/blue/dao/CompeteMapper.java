package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Compete;
import com.techwells.blue.util.PagingTool;

public interface CompeteMapper {
    int deleteByPrimaryKey(Integer competeId);

    int insert(Compete record);

    int insertSelective(Compete record);

    Compete selectByPrimaryKey(Integer competeId);

    int updateByPrimaryKeySelective(Compete record);

    int updateByPrimaryKey(Compete record);

   
	List<Compete> selectCompeteList(PagingTool pagingTool);

	int countTotalCompeteList(PagingTool pagingTool);
}