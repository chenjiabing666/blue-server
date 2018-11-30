package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Question;
import com.techwells.blue.util.PagingTool;

/**
 * 问题的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface QuestionService {
	
	/**
	 * 添加问题
	 * @param question
	 * @return
	 * @throws Exception
	 */
	Object addQuestion(Question question,Answer answer)throws Exception;
	
	/**
	 * 根据问题Id获取信息
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	Object getQuestionById(Integer questionId)throws Exception;
	
	
	/**
	 * 修改问题
	 * @param question
	 * @return
	 * @throws Exception
	 */
	Object modifyQuestionReturnObject(Question question)throws Exception;
	
	
	/**
	 * 修改问题
	 * @param question
	 * @return
	 * @throws Exception
	 */
	int modifyQuestionReturnCount(Question question)throws Exception;
	
	
	/**
	 * 根据问题Id删除数据
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	Object deleteQuestionReturnObject(Integer questionId)throws Exception;
	
	/**
	 * 根据问题Id删除数据
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	int deleteQuestionReturnCount(Integer questionId)throws Exception;
	
	
	
	/**
	 * 分页获取问题数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getQuestionList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
