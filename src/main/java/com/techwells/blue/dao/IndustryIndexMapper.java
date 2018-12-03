package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.IndustryIndex;
import com.techwells.blue.util.PagingTool;

public interface IndustryIndexMapper {
    int deleteByPrimaryKey(Integer industryIndexId);

    int insert(IndustryIndex record);

    int insertSelective(IndustryIndex record);

    IndustryIndex selectByPrimaryKey(Integer industryIndexId);

    int updateByPrimaryKeySelective(IndustryIndex record);

    int updateByPrimaryKey(IndustryIndex record);

	List<IndustryIndex> selectIndustryIndexList(PagingTool pagingTool);

	int countTotalIndustryIndexList(PagingTool pagingTool);
}