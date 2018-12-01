package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Answer;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer answerId);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer answerId);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);
    
    /**
     * 根据问题Id获取答案
     * @param questionId
     * @return
     */
    List<Answer> selectAnswersByQuestionId(Integer questionId);
    
}