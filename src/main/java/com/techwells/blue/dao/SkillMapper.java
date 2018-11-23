package com.techwells.blue.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.blue.domain.Skill;
import com.techwells.blue.util.PagingTool;

public interface SkillMapper {
    int deleteByPrimaryKey(Integer skillId);

    int insert(Skill record);

    int insertSelective(Skill record);

    Skill selectByPrimaryKey(Integer skillId);

    int updateByPrimaryKeySelective(Skill record);

    int updateByPrimaryKey(Skill record);
    
    /**
     * 获取技能
     * @param ids
     * @return
     */
    List<Skill> selectListByIds(@Param("ids")String[] ids);
    
    /**
     * 分页获取列表
     * @param pagingTool
     * @return
     */
    List<Skill> selectSkillList(PagingTool pagingTool);
    
    int countTotalSkillList(PagingTool pagingTool);
    
    
    
}