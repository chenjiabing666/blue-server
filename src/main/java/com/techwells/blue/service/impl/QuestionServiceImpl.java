package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.AnswerMapper;
import com.techwells.blue.dao.ModuleMapper;
import com.techwells.blue.dao.QuestionMapper;
import com.techwells.blue.dao.RecordMapper;
import com.techwells.blue.dao.ReportMapper;
import com.techwells.blue.dao.ScoreMapper;
import com.techwells.blue.dao.WarmMapper;
import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Module;
import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.Record;
import com.techwells.blue.domain.Report;
import com.techwells.blue.domain.Score;
import com.techwells.blue.domain.Warm;
import com.techwells.blue.domain.rs.QuestionAnswerVos;
import com.techwells.blue.domain.rs.QuestionModuleVos;
import com.techwells.blue.domain.rs.SubmitVos;
import com.techwells.blue.service.QuestionService;
import com.techwells.blue.util.DoubleUtils;
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
	@Resource
	private WarmMapper warmMapper;
	@Resource
	private ReportMapper reportMapper;
	@Resource
	private ScoreMapper scoreMapper;
	@Resource
	private RecordMapper recordMapper;
	
	
	
	
	
	
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
	 * 1、总分
	 * 2、单个模块的分数
	 * 3、保存用户的答案
	 */
	@Override
	public Object submit(SubmitVos answers) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<QuestionAnswerVos> questionAnswerVos=answers.getQuestionAnswerVos();  //获取提交的问题和答案
		
		int num=questionAnswerVos.size();  //题目的总数
		String reportNum=System.currentTimeMillis()+"";  //报告单的单号
		Report report=new Report();
		report.setReportNum(reportNum);
		report.setUserId(answers.getUserId());
		report.setCreatedDate(new Date());
		report.setOrganization("蓝色按钮");
		report.setStatus(answers.getStatus());
		//根据用户Id获取热身问题的答案
		Warm warm=warmMapper.selectByUserId(answers.getUserId());
		if (warm==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先完成热身问题");
			return resultInfo;
		}
		report.setCompany(warm.getCompany());
		report.setStage(warm.getStage());
		
		Double total_score=0.0;  //总分（整体的总分，不是模块的总分）
		//如果是单个模块测试
		if (answers.getStatus().equals(0)) { 
			Score score=new Score();  //存储每个模块的分数
			int flag=0;
			Double avg_score = DoubleUtils.round(100.0 / num, 2); // 每道题目的分数
			// 遍历数据
			for (QuestionAnswerVos qa : questionAnswerVos) {
				// 获取指定答案的详细信息
				Answer answer = answerMapper.selectByPrimaryKey(qa
						.getAnswerId());
				if (answer != null) {
					// 获取权重
					if (answer.getScroeLevel().equals(1)) {// 权重0%
						total_score += 0;
					} else if (answer.getScroeLevel().equals(2)) { // 25%
						total_score += avg_score * 0.25;
					} else if (answer.getScroeLevel().equals(3)) { // 50%
						total_score += avg_score * 0.5;
					} else if (answer.getScroeLevel().equals(4)) { // 75%
						total_score += avg_score * 0.75;
					} else if (answer.getScroeLevel().equals(5)) { // 100%
						total_score += avg_score;
					}
				}
				
				if (flag==0) {  //获取模块的名称和模块的Id
					//获取模块信息
					Question question=questionMapper.selectByPrimaryKey(qa.getQuestionId());
					if (question!=null) {
						//获取模块名称
						Module module=moduleMapper.selectByPrimaryKey(question.getModuleId());
						if (module!=null) {
							report.setReportName(module.getModuleName()+"模块测试");
							score.setModuleId(module.getModuleId());
						}
					}
					flag++;
				}
			}
			report.setTotalScore(DoubleUtils.round(total_score, 1)); // 设置总分
			score.setScore(DoubleUtils.round(total_score, 1));   //设置单个模块的分数
			score.setTotalScore(100.0);  //此时的单个模块的总分就是100
			
			//添加报告
			int count1=reportMapper.insertSelective(report);  //添加报告
			
			if (count1==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("答案提交失败");
				return resultInfo;
			}
			
			score.setReportId(report.getReportId());
			
			//插入记录
			int count2=scoreMapper.insertSelective(score);
			if (count2==0) {
				throw new RuntimeException(); 
			}
			
		}else {  //如果是整体测评，需要算出总分和每个模块的总分
			Double avg_score = DoubleUtils.round(100.0 / num, 2); // 每道题目的分数
			Map<Integer,Double> map=new HashMap<Integer, Double>();  //存储模块的得分 key是moduleId，value是得分
			Map<Integer, Double> map2=new HashMap<Integer, Double>(); //存储这个模块的总分，就是这个模块所占的分数，key是moduleId，value是所占的总分
			// 遍历数据
			for (QuestionAnswerVos qa : questionAnswerVos) {
				Integer moduleId=null;
				Question question=questionMapper.selectByPrimaryKey(qa.getQuestionId());
				if (question!=null) {
					//获取模块名称
					Module module=moduleMapper.selectByPrimaryKey(question.getModuleId());
					if (module!=null) {
						moduleId=module.getModuleId();  //获取模块的Id
					}
				}
				
				map2.put(moduleId, map2.get(moduleId)!=null?map2.get(moduleId)+avg_score:0);  //计算单个模块的总分
				
				// 获取指定答案的详细信息
				Answer answer = answerMapper.selectByPrimaryKey(qa
						.getAnswerId());
				if (answer != null) {
					// 获取权重
					if (answer.getScroeLevel().equals(1)) {// 权重0%
						total_score += 0;  //总分
						map.put(moduleId, map.get(moduleId)!=null?map.get(moduleId)+0:0);  //模块的分数相加
					} else if (answer.getScroeLevel().equals(2)) { // 25% 
						total_score += avg_score * 0.25;
						map.put(moduleId, map.get(moduleId)!=null?map.get(moduleId)+avg_score * 0.25:avg_score * 0.25);
					} else if (answer.getScroeLevel().equals(3)) { // 50%
						total_score += avg_score * 0.5;
						map.put(moduleId, map.get(moduleId)!=null?map.get(moduleId)+avg_score * 0.5:avg_score * 0.5);
					} else if (answer.getScroeLevel().equals(4)) { // 75%
						total_score += avg_score * 0.75;
						map.put(moduleId, map.get(moduleId)!=null?map.get(moduleId)+avg_score * 0.75:avg_score * 0.75);
					} else if (answer.getScroeLevel().equals(5)) { // 100%
						total_score += avg_score;
						map.put(moduleId, map.get(moduleId)!=null?map.get(moduleId)+avg_score * 1:avg_score * 1);
					}
				}
			}
			
			report.setReportName("整体模块测试");  //报告的名字
			report.setTotalScore(DoubleUtils.round(total_score, 1)); // 设置总分
			
			//添加报告
			int count1=reportMapper.insertSelective(report);  //添加报告
			
			if (count1==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("答案提交失败");
				return resultInfo;
			}
			
			//遍历单个模块的分数，添加数据
			for (Integer key : map.keySet()) {
				Score score=new Score(); //设置单个模块的分数
				score.setModuleId(key);
				score.setCreatedDate(new Date());
				score.setReportId(report.getReportId());
				score.setScore(map.get(key));  //设置这个模块的得分
				score.setTotalScore(map2.get(key));  //总分
				int count2=scoreMapper.insertSelective(score);
				if (count2==0) {
					throw new RuntimeException();    //如果有一个插入失败了，那么必须回滚数据
				}
			}
		}
		
		//最后添加做题记录,记录当前用户做题的记录
		for (QuestionAnswerVos qa : questionAnswerVos) {
			Record record=new Record();  //做题的记录
			record.setAnswerId(qa.getAnswerId());
			record.setRecordId(report.getReportId());
			record.setUserId(answers.getUserId());
			record.setQuestionId(qa.getQuestionId());
			record.setCreatedDate(new Date());
			int count3=recordMapper.insertSelective(record);
			if (count3==0) {
				throw new RuntimeException();  //直接回滚数据
			}
		}
		resultInfo.setMessage("答案提交成功");
		return resultInfo;
	}

	
	
	
}
