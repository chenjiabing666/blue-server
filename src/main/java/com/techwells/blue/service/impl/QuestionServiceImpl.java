package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.AnswerMapper;
import com.techwells.blue.dao.QuestionMapper;
import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.rs.QuestionModuleVos;
import com.techwells.blue.service.QuestionService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 问题的业务层的实现类
 * @author 陈加兵
 */
@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Resource
	private QuestionMapper questionMapper;
	@Resource
	private AnswerMapper answerMapper;
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addQuestion(Question question,Answer answer) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=questionMapper.insertSelective(question);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		answer.setQuestionId(question.getQuestionId());
		
		int count1=answerMapper.insertSelective(answer);
		if (count1==0) {
			throw new RuntimeException();
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getQuestionById(Integer questionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Question question=questionMapper.selectByPrimaryKey(questionId);
		
		if (question==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该问题不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(question);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyQuestionReturnObject(Question question) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=questionMapper.updateByPrimaryKeySelective(question);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int modifyQuestionReturnCount(Question question) throws Exception {
		return questionMapper.updateByPrimaryKeySelective(question);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteQuestionReturnObject(Integer questionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=questionMapper.deleteByPrimaryKey(questionId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int deleteQuestionReturnCount(Integer questionId) throws Exception {
		return questionMapper.deleteByPrimaryKey(questionId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getQuestionList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=questionMapper.countTotalQuestionList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(total);
			resultInfo.setResult(null);
			return resultInfo;
		}
		
		List<QuestionModuleVos> questionModuleVos=questionMapper.selectQuestionList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(total);
		resultInfo.setResult(questionModuleVos);
		return resultInfo;
	}

	
	
	
}
