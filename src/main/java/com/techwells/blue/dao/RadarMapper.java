package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Radar;
import com.techwells.blue.domain.rs.RadarModuleIndustryVos;
import com.techwells.blue.util.PagingTool;

public interface RadarMapper {
    int deleteByPrimaryKey(Integer radarId);

    int insert(Radar record);

    int insertSelective(Radar record);

    Radar selectByPrimaryKey(Integer radarId);

    int updateByPrimaryKeySelective(Radar record);

    int updateByPrimaryKey(Radar record);

    
	int countTotalRadarList(PagingTool pagingTool);

	List<RadarModuleIndustryVos> selectRadarList(PagingTool pagingTool);
}