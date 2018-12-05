package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.IOTools;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Answer;
import com.techwells.blue.domain.Question;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.rs.QuestionAnswerVos;
import com.techwells.blue.domain.rs.SubmitVos;
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
	 * @param questionReasonFile  常见问题和原因的文件
	 * @param adviseFile   建议的文件
	 * @param analysisFile  解析的文件
	 * @param file        问题内容的文件
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
		@ApiImplicitParam(paramType = "query", name = "scoreLevel", dataType="String", required = true, value = "分值级别 1-5 分别对应0%， 25%，50%，75%，100%",defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "quesionReason", dataType="String", required = true, value = "常见问题和原因", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "fileType", dataType="int", required = false, value = "题目的文件类型 1 图片 2 可选", defaultValue = ""),
	})
	public Object addQuestion(
			HttpServletRequest request,
			@RequestParam(value = "questionReasonFile", required = false) MultipartFile[] questionReasonFile,
			@RequestParam(value = "adviseFile", required = false) MultipartFile[] adviseFile,
			@RequestParam(value = "analysisFile", required = false) MultipartFile[] analysisFile,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ResultInfo resultInfo = new ResultInfo();
		String content = request.getParameter("content"); // 问题内容
		String moduleId = request.getParameter("moduleId"); // 模块Id
		String fileType = request.getParameter("fileType"); // 题目的文件类型 1 图片 2 可选
		String status = request.getParameter("status"); // 1:整体 2：非整体
		
		String questionReasonFlag=request.getParameter("questionReasonFlag"); //文件上传的标记
		String adviseFileFlag=request.getParameter("adviseFileFlag"); //文件上传的标记
		String analysisFileFlag=request.getParameter("analysisFileFlag"); //文件上传的标记
		
		
		// 下面都是数组
		String[] answer = request.getParameterValues("answer"); // 答案 1-5对应 A-E
		String[] quesionReason = request.getParameterValues("quesionReason"); // 常见问题和原因
		String[] advise = request.getParameterValues("advise"); // 建议
		String[] analysis = request.getParameterValues("analysis"); // 解读
		String[] scoreLevel = request.getParameterValues("scoreLevel"); // 分值级别 1-5 分别对应0%， 25%，50%，75%，100%
		String[] answerContent=request.getParameterValues("answerContent");  //选项的内容
		
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

		if (answer == null || answer.length != 5) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("答案必须是5个");
			return resultInfo;
		}

		if (quesionReason == null || quesionReason.length != 5) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("常见问题和原因必须是5个");
			return resultInfo;
		}

		if (analysis == null || analysis.length != 5) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解析必须是5个");
			return resultInfo;
		}

		if (advise == null || advise.length != 5) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("建议必须是5个");
			return resultInfo;
		}

		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}

		if (scoreLevel == null || scoreLevel.length != 5) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分值级别必须是5个");
			return resultInfo;
		}

		// 封装数据
		Question question = new Question();
		question.setCreatedDate(new Date());
		question.setModuleId(Integer.parseInt(moduleId));
		question.setStatus(Integer.parseInt(status));
		question.setContent(content);

		// 如果题目的文件类型不为空，那么需要上传文件
		if (!StringUtils.isEmpty(fileType)) {
			if (file == null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请上传对应类型的文件");
				return resultInfo;
			}

			try {
				String url = UploadFileUtils
						.uploadFile("question-file", System.currentTimeMillis()
								+ file.getOriginalFilename(), file);
				question.setFileType(Integer.parseInt(fileType));
				question.setFile(url);
			} catch (Exception e) {
				logger.error("上传文件异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		int reasonLength=questionReasonFile.length;
		int adviseLength=adviseFile.length;
		int analysisLength=analysisFile.length;
		
		int q=0;
		int j=0;
		int k=0;
		
		
		// 封装答案的数据
		List<Answer> answerList = new ArrayList<Answer>();
		for (int i = 0; i < 5; i++) {
			Answer answer2 = new Answer(); // 新建
			answer2.setAnalysis(analysis[i]);
			answer2.setAdvise(advise[i]);
			answer2.setQuestionReason(quesionReason[i]);
			answer2.setAnswer(Integer.parseInt(answer[i]));
			answer2.setScroeLevel(Integer.parseInt(scoreLevel[i]));
			answer2.setContent(answerContent[i]);
			
			// 上传文件
			if (reasonLength>0&&questionReasonFile[q] != null&&questionReasonFlag.contains(answer[i])) {
				try {
					String url = UploadFileUtils.uploadFile(
							"question-image",
							System.currentTimeMillis()
									+ questionReasonFile[q]
											.getOriginalFilename(),
							questionReasonFile[q]);
					answer2.setQuestionReasonUrl(url);
					reasonLength--;
					q++;
				} catch (Exception e) {
					logger.error("上传文件异常", e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("上传文件异常");
					return resultInfo;
				}
			}

			if (adviseLength>0&&adviseFile[k] != null&&adviseFileFlag.contains(answer[i])) {
				try {
					String url = UploadFileUtils.uploadFile(
							"question-image",
							System.currentTimeMillis()
									+ adviseFile[k].getOriginalFilename(),
							adviseFile[k]);
					answer2.setAdviseUrl(url);
					adviseLength--;
					k++;
				} catch (Exception e) {
					logger.error("上传文件异常", e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("上传文件异常");
					return resultInfo;
				}
			}

			if (analysisLength>0&&analysisFile[j] != null&&analysisFileFlag.contains(answer[i])) {
				try {
					String url = UploadFileUtils.uploadFile(
							"question-image",
							System.currentTimeMillis()
									+ analysisFile[j].getOriginalFilename(),
							analysisFile[j]);
					answer2.setAnalysisUrl(url);
					analysisLength--;
					j++;
				} catch (Exception e) {
					logger.error("上传文件异常", e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("上传文件异常");
					return resultInfo;
				}
			}
			answerList.add(answer2); // 添加
		}
		
		
		//调用service层的方法
		try {
			Object object=questionService.addQuestion(question,answerList);
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
		String questionId=request.getParameter("questionId");  //问题Id
		
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
		String questionId=request.getParameter("questionId");  //问题Id
		 
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
	@ApiOperation(value="分页获取问题列表(后台)",response=Question.class,hidden=true)
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
	
	/**
	 * 修改指定答案的信息
	 * @param request
	 * @return
	 */
	@PostMapping("/question/modifyAnswer")
	@ApiOperation(value="修改指定答案的信息",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "answerId", dataType="int", required = true, value = "答案Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "advise", dataType="String", required = true, value = "建议", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "analysis", dataType="String", required = true, value = "解读", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "scoreLevel", dataType="String", required = true, value = "分值级别 1-5 分别对应0%， 25%，50%，75%，100%",defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "quesionReason", dataType="String", required = true, value = "常见问题和原因", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "content", dataType="String", required = true, value = "选项内容", defaultValue = ""),
	})
	public Object modifyAnswer(
			HttpServletRequest request,
			@RequestParam(value = "questionReasonFile", required = false) MultipartFile questionReasonFile,
			@RequestParam(value = "adviseFile", required = false) MultipartFile adviseFile,
			@RequestParam(value = "analysisFile", required = false) MultipartFile analysisFile) {
		ResultInfo resultInfo=new ResultInfo();
		String answerId=request.getParameter("answerId"); //答案Id
		String quesionReason = request.getParameter("quesionReason"); // 常见问题和原因
		String advise = request.getParameter("advise"); // 建议
		String analysis = request.getParameter("analysis"); // 解读
		String scoreLevel = request.getParameter("scoreLevel"); // 分值级别 1-5 分别对应0%， 25%，50%，75%，100%
		String content=request.getParameter("content"); //选项内容
		
		if (StringUtils.isEmpty(answerId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("答案Id不能为空");
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
			resultInfo.setMessage("解读不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(scoreLevel)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分值级别不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("选项内容不能为空");
			return resultInfo;
		}
		
		//封装数据
		Answer answer=new Answer();
		
		answer.setAnswerId(Integer.parseInt(answerId));
		answer.setAnalysis(analysis);
		answer.setAdvise(advise);
		answer.setQuestionReason(quesionReason);
		answer.setScroeLevel(Integer.parseInt(scoreLevel));
		answer.setContent(content);
		
		//如果文件存在，那么上传文件
		if (questionReasonFile!=null) {
			try {
				String url = UploadFileUtils.uploadFile(
						"question-image",
						System.currentTimeMillis()
								+ questionReasonFile.getOriginalFilename(),
						questionReasonFile);
				answer.setQuestionReasonUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		if (adviseFile!=null) {
			try {
				String url = UploadFileUtils.uploadFile(
						"question-image",
						System.currentTimeMillis()
								+ adviseFile.getOriginalFilename(),
								adviseFile);
				answer.setAdviseUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		
		if (analysisFile!=null) {
			try {
				String url = UploadFileUtils.uploadFile(
						"question-image",
						System.currentTimeMillis()
								+ analysisFile.getOriginalFilename(),
								analysisFile);
				answer.setAnalysisUrl(url);
			} catch (Exception e) {
				logger.error("上传文件异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
		}
		
		try {
			Object object=questionService.modifyAnswer(answer);
			return object;
		} catch (Exception e) {
			logger.error("修改答案异常", e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改答案异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取问题列表（前台出题）
	 * @param request
	 * @return
	 */
	@PostMapping("/question/getQuestionListForeground")
	@ApiOperation(value="分页获取问题列表(前台出题)",response=Question.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = false, value = "模块Id 选择单个模块的时候传值  可选", defaultValue = ""),
	})
	public Object getQuestionListForeground(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String moduleId=request.getParameter("moduleId");  //模块Id 选择单个模块的时候传值  可选
		
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

		// 构造分页数据
		PagingTool pagingTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));

		// 封装过滤条件
		Map<String, Object> params = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(moduleId)) {
			params.put("moduleId", Integer.parseInt(moduleId));
		}
		
		try {
			Object object=questionService.getQuestionListForeground(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取问题列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取问题列表异常");
			return resultInfo;
		}
	}
	
	/**
	 * 提交答案
	 * 
	 * @param answers
	 * @return
	 */
	@PostMapping("/question/submit")
	@ApiOperation(value="提交答案",response=Question.class,hidden=false)
	@ApiImplicitParams({
	})
	public Object submit(@RequestBody SubmitVos answers){
		ResultInfo resultInfo=new ResultInfo();
		try {
			Object  object=questionService.submit(answers);
			return object;
		} catch (Exception e) {
			logger.error("提交答案异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("提交答案异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
