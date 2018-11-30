package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.rs.QuestionModuleVos;
import com.techwells.blue.util.PagingTool;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
    
    List<QuestionModuleVos> selectQuestionList(PagingTool pagingTool);
    
    int countTotalQuestionList(PagingTool pagingTool);
    
}