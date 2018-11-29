package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Industry;
import com.techwells.blue.util.PagingTool;

public interface IndustryMapper {
    int deleteByPrimaryKey(Integer industryId);

    int insert(Industry record);

    int insertSelective(Industry record);

    Industry selectByPrimaryKey(Integer industryId);

    int updateByPrimaryKeySelective(Industry record);

    int updateByPrimaryKey(Industry record);
    
    List<Industry> selectIndustryList(PagingTool pagingTool);
    
    int countTotalIndustryList(PagingTool pagingTool);
    
}