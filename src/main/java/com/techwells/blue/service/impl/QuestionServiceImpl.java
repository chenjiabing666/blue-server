package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.AnswerMapper;
import com.techwells.blue.dao.ModuleMapper;
import com.techwells.blue.dao.QuestionMapper;
import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Module;
import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.rs.QuestionAnswerVos;
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
	@Resource
	private ModuleMapper moduleMapper;
	
	
	
	
	@Override
	public Object addQuestion(Question question,List<Answer> answerList) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		Module module=moduleMapper.selectByPrimaryKey(question.getModuleId());
		
		if (module==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("对应的模块不存在");
			return resultInfo;
		}
		
		int count=questionMapper.insertSelective(question);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		//遍历添加答案
		for (Answer answer : answerList) {
			answer.setQuestionId(question.getQuestionId());
			int count1=answerMapper.insertSelective(answer);
			if (count1==0) {
				throw new RuntimeException();
			}
		}
		
		
		//各个模块对应的数量增加
		module.setQuestionNum(module.getQuestionNum()+1);
		int count2=moduleMapper.updateByPrimaryKeySelective(module);
		if (count2==0) {
			throw new RuntimeException();
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getQuestionById(Integer questionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Question question=questionMapper.selectByPrimaryKey(questionId);
		
		if (question==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该问题不存在");
			return resultInfo;
		}
		
		//根据问题获取答案和解析
		List<Answer> answers=answerMapper.selectAnswersByQuestionId(questionId);
		question.setAnswers(answers);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(question);
		return resultInfo;
	}

	
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
	
	
	@Override
	public int modifyQuestionReturnCount(Question question) throws Exception {
		return questionMapper.updateByPrimaryKeySelective(question);
	}

	
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
	
	
	@Override
	public int deleteQuestionReturnCount(Integer questionId) throws Exception {
		return questionMapper.deleteByPrimaryKey(questionId);
	}

	
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
	
	
	@Override
	public Object modifyAnswer(Answer answer) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int count=answerMapper.updateByPrimaryKeySelective(answer);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public Object getQuestionListForeground(PagingTool pagingTool) {
		ResultInfo resultInfo=new ResultInfo();
		int total=questionMapper.countTotalQuestionListForeground(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(total);
			resultInfo.setResult(null);
			return resultInfo;
		}
		
		List<Question> questions=questionMapper.selectQuestionListForeground(pagingTool);
		
		Iterator<Question> iterator = questions.iterator();  //获取迭代器
		while(iterator.hasNext()){
			Question question = iterator.next();
			//根据问题Id答案列表
			List<Answer> answers = answerMapper.selectAnswersByQuestionId(question.getQuestionId());
			if (answers.size()<5) {  //如果这个问题没有5个答案，那么就不完整，因此需要去除
				iterator.remove();  //删除这个模块
				total--;  //数量-1
			}else {
				question.setAnswers(answers);
			}
		}
		
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(total);
		resultInfo.setResult(questions);
		return resultInfo;
	}
	
	/**
	 * 提交答案，需要核对分数，生成报告单
	 */
	
	@Override
	public Object submit(List<QuestionAnswerVos> answers) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		return answers;
	}

	
	
	
}
