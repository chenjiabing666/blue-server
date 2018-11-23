package com.techwells.blue.dao;


import java.util.List;

import com.techwells.blue.domain.Expert;
import com.techwells.blue.util.PagingTool;

public interface ExpertMapper {
    int deleteByPrimaryKey(Integer expertId);

    int insert(Expert record);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(Integer expertId);

    int updateByPrimaryKeySelective(Expert record);

    int updateByPrimaryKey(Expert record);
    
    /**
     * 分页获取专家后台
     * @param pagingTool
     * @return
     */
    List<Expert> selectExpertListBack(PagingTool pagingTool);
    
    /**
     * 数量
     * @param pagingTool
     * @return
     */
    int countTotalExpertListBack(PagingTool pagingTool);
    
    
    
}