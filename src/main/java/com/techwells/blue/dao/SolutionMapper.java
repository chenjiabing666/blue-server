package com.techwells.blue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.blue.domain.Solution;

public interface SolutionMapper {
    int deleteByPrimaryKey(Integer solutionId);

    int insert(Solution record);

    int insertSelective(Solution record);

    Solution selectByPrimaryKey(Integer solutionId);

    int updateByPrimaryKeySelective(Solution record);

    int updateByPrimaryKey(Solution record);
    
    /**
     * 根据模块Id和行业Id获取解决方案
     * @param industryId
     * @param moduleId
     * @return
     */
    List<Solution> selectSolutionsByIndustryIdAndModuleId(@Param("industryId")Integer industryId,@Param("moduleId")Integer moduleId);
    
}