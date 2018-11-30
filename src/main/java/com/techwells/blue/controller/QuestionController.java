package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.QuestionService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.UploadFileUtils;

/**
 * 问题的controller
 * @author 陈加兵
 */
@Api(description="问题的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class QuestionController {
	
	@Resource
	private QuestionService questionService;
	
	private Logger logger=LoggerFactory.getLogger(QuestionController.class); //日志
	
	/**
	 * 添加问题
	 * @param request
	 * @return
	 */
	@PostMapping("/question/addQuestion")
	@ApiOperation(value="添加问题",response=Question.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "content", dataType="String", required = true, value = "问题内容", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="String", required = true, value = "模块Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "answer", dataType="String", required = true, value = "答案 1-5对应 A-E", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "advise", dataType="String", required = true, value = "建议", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "analysis", dataType="String", required = true, value = "解读", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="String", required = true, value = "1:整体 2：非整体", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "scoreLevel", dataType="String", required = true, value = "分值级别 1-5 分别对应0%， 25%，50%，75%，100%", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "quesionReason", dataType="String", required = true, value = "常见问题和原因", defaultValue = ""),
	})
	public Object addQuestion(
			HttpServletRequest request,
			@RequestParam(value = "questionReasonFile", required = false) MultipartFile questionReasonFile,
			@RequestParam(value = "adviseFile", required = false) MultipartFile adviseFile,
			@RequestParam(value = "analysisFile", required = false) MultipartFile analysisFile) {
		ResultInfo resultInfo=new ResultInfo();
		String content=request.getParameter("content");  //问题内容
		String moduleId=request.getParameter("moduleId");  //模块Id
		String answer=request.getParameter("answer");  //答案 1-5对应 A-E
		String quesionReason=request.getParameter("quesionReason");  //常见问题和原因
		String advise=request.getParameter("advise");  //建议
		String analysis=request.getParameter("analysis");  //解读
		String status=request.getParameter("status");  //1:整体 2：非整体
		String scoreLevel=request.getParameter("scoreLevel");  //分值级别 1-5 分别对应0%， 25%，50%，75%，100%
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("问题内容不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(answer)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("答案不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(quesionReason)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("常见问题和原因不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(advise)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("建议不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(analysis)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解析不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(scoreLevel)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分值级别不能为空");
			return resultInfo;
		}
		
		//封装数据
		Question question=new Question();
		Answer answer2=new Answer();
		question.setCreatedDate(new Date());
		question.setModuleId(Integer.parseInt(moduleId));
		question.setStatus(Integer.parseInt(status));
		answer2.setAnalysis(analysis);
		answer2.setAdvise(advise);
		answer2.setQuestionReason(quesionReason);
		answer2.setAnswer(Integer.parseInt(answer));
		question.setContent(content);
		answer2.setScroeLevel(Integer.parseInt(scoreLevel));
		//上传文件
		if (questionReasonFile!=null) {
			try {
				String url=UploadFileUtils.uploadFile("question-image", System.currentTimeMillis()+questionReasonFile.getOriginalFilename(), questionReasonFile);
				answer2.setQuestionReasonUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		if (adviseFile!=null) {
			try {
				String url=UploadFileUtils.uploadFile("question-image", System.currentTimeMillis()+adviseFile.getOriginalFilename(), adviseFile);
				answer2.setAdviseUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		
		if (analysisFile!=null) {
			try {
				String url=UploadFileUtils.uploadFile("question-image", System.currentTimeMillis()+analysisFile.getOriginalFilename(), analysisFile);
				answer2.setAnalysisUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		//调用service层的方法
		try {
			Object object=questionService.addQuestion(question,answer2);
			return object;
		} catch (Exception e) {
			logger.error("添加问题异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取问题详情
	 * @param request
	 * @return
	 */
	@PostMapping("/question/getQuestionById")
	@ApiOperation(value="获取问题详情",response=Question.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "questionId", dataType="int", required = true, value = "问题的questionId", defaultValue = "1"),
	})
	public Object getQuestionById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String questionId=request.getParameter("questionId");
		
		if (StringUtils.isEmpty(questionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("问题Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=questionService.getQuestionById(Integer.parseInt(questionId));
			return object;
		} catch (Exception e) {
			logger.error("获取问题详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改问题
	 * @param request
	 * @return
	 */
	@PostMapping("/question/modifyQuestion")
	@ApiOperation(value="修改问题",response=Question.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "questionId", dataType="int", required = true, value = "问题的questionId", defaultValue = "1"),
	})
	public Object modifyQuestion(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String questionId=request.getParameter("questionId");
		
		if (StringUtils.isEmpty(questionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("问题Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Question question=new Question();
		question.setQuestionId(Integer.parseInt(questionId));
		
		//调用service层的方法
		try {
			Object object=questionService.modifyQuestionReturnObject(question);
			return object;
		} catch (Exception e) {
			logger.error("修改问题异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除问题
	 * @param request
	 * @return
	 */
	@PostMapping("/question/deleteQuestionById")
	@ApiOperation(value="根据Id删除问题",response=Question.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "questionId", dataType="int", required = true, value = "问题的questionId", defaultValue = "1"),
	})
	public Object deleteQuestion(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String questionId=request.getParameter("questionId");
		
		if (StringUtils.isEmpty(questionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("问题Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=questionService.deleteQuestionReturnObject(Integer.parseInt(questionId));
			return object;
		} catch (Exception e) {
			logger.error("删除问题异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取问题列表(后台)
	 * @param request
	 * @return
	 */
	@PostMapping("/question/getQuestionListBack")
	@ApiOperation(value="分页获取问题列表(后台)",response=Question.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "content", dataType="String", required = false, value = "问题内容(模糊查找)", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = false, value = "模块Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = false, value = "1:整体 2：非整体", defaultValue = ""),
	})
	public Object getQuestionListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String content=request.getParameter("content"); //问题内容(模糊查找)
		String moduleId=request.getParameter("moduleId");//模块Id
		String status=request.getParameter("status"); //1:整体 2：非整体
		
		//校验数据
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(content)) {
			params.put("content", content);
		}
		
		if (!StringUtils.isEmpty(moduleId)) {
			params.put("moduleId", Integer.parseInt(moduleId));
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=questionService.getQuestionList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取问题列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
}
