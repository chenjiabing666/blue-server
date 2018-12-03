package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.techwells.blue.domain.Solution;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.SolutionService;
import com.techwells.blue.util.BlueConstants;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.UploadFileUtils;

/**
 * 解决方案的controller
 * @author 陈加兵
 */
@Api(description="解决方案的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class SolutionController {
	
	@Resource
	private SolutionService solutionService;
	
	private Logger logger=LoggerFactory.getLogger(SolutionController.class); //日志
	
	/**
	 * 添加解决方案
	 * @param request
	 * @return
	 */
	@PostMapping("/solution/addSolution")
	@ApiOperation(value="添加解决方案",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "solutionName", dataType="String", required = true, value = "解决方案的solutionName", defaultValue = "Tom"),
	})
	public Object addSolution(
			HttpServletRequest request,
			@RequestParam(value = "questionReasonFiles",required=false) MultipartFile[] questionReasonFiles,
			@RequestParam(value = "adviseFiles",required=false) MultipartFile[] adviseFiles,
			@RequestParam(value = "analysisFiles",required=false) MultipartFile[] analysisFiles) {
		ResultInfo resultInfo = new ResultInfo();
		String moduleId = request.getParameter("moduleId"); // 模块Id
		String industryId = request.getParameter("industryId"); // 行业Id
		String[] questionReasons = request
				.getParameterValues("questionReasons"); // 常见问题及原因
		String[] advise = request.getParameterValues("advise"); // 建议
		String[] analysis = request.getParameterValues("analysis");  //解读
		String[] types=request.getParameterValues("types");  //类型 1 高分 2 中等 3 低分
		
		String qFlag=request.getParameter("qFlag"); //上传文件的标记
		String adFlag=request.getParameter("adFlag");
		String anFlag=request.getParameter("anFlag");
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		List<Solution> solutionList=new ArrayList<Solution>();
		
		//总共有高中低三种解决方案
		for (int i = 0; i <3; i++) {
			
			if (!anFlag.contains(String.valueOf(i+1))||!qFlag.contains(String.valueOf(i+1))||!adFlag.contains(String.valueOf(i+1))) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("文件不能为空");
				return resultInfo;
			}
			
			Solution solution=new Solution(); 
			solution.setModuleId(Integer.parseInt(moduleId));
			solution.setCreatedDate(new Date());
			solution.setIndustryId(Integer.parseInt(industryId));
			solution.setAdvise(advise[i]);
			solution.setQuestionReason(questionReasons[i]);
			solution.setAnalysis(analysis[i]);
			solution.setType(Integer.parseInt(types[i]));
			//上传三个文件
			String qname=System.currentTimeMillis()+questionReasonFiles[i].getOriginalFilename();
			String path=BlueConstants.UPLOAD_PATH+"solution-image/";
			File qFile=new File(path,qname);
			String qUrl=BlueConstants.UPLOAD_URL+"solution-image/"+qname;
			try {
				UploadFileUtils.createChildFolder(qFile);  //创建文件夹
			} catch (Exception e) {
				logger.error("创建子文件夹异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("创建子文件夹异常");
				return resultInfo;
			}
			
			//上传
			try {
				questionReasonFiles[i].transferTo(qFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			
			
			String adname=System.currentTimeMillis()+adviseFiles[i].getOriginalFilename();
			File adFile=new File(path,adname);
			String adUrl=BlueConstants.UPLOAD_URL+"solution-image/"+adname;
			
			//上传
			try {
				adviseFiles[i].transferTo(adFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			
			String anname=System.currentTimeMillis()+analysisFiles[i].getOriginalFilename();
			File anFile=new File(path,anname);
			String anUrl=BlueConstants.UPLOAD_URL+"solution-image/"+anname;
			
			//上传
			try {
				analysisFiles[i].transferTo(anFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			
			
			solution.setQuestionReasonUrl(qUrl);
			solution.setAdviseUrl(adUrl);
			solution.setAnalysisUrl(anUrl);
			
			solutionList.add(solution);
		}
		
		//调用service层的方法
		try {
			Object object=solutionService.addSolutionBatch(solutionList);
			return object;
		} catch (Exception e) {
			logger.error("添加解决方案异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取解决方案详情
	 * @param request
	 * @return
	 */
	@PostMapping("/solution/getSolutionById")
	@ApiOperation(value="获取解决方案详情",response=Solution.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "solutionId", dataType="int", required = true, value = "解决方案的solutionId", defaultValue = "1"),
	})
	public Object getSolutionById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String solutionId=request.getParameter("solutionId");  //方案Id
		
		if (StringUtils.isEmpty(solutionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解决方案Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=solutionService.getSolutionById(Integer.parseInt(solutionId));
			return object;
		} catch (Exception e) {
			logger.error("获取解决方案详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改解决方案
	 * @param request
	 * @return
	 */
	@PostMapping("/solution/modifySolution")
	@ApiOperation(value="修改解决方案",response=Solution.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "solutionId", dataType="int", required = true, value = "解决方案的solutionId", defaultValue = "1"),
	})
	public Object modifySolution(HttpServletRequest request,
			@RequestParam(value = "questionReasonFile",required=false) MultipartFile questionReasonFile,
			@RequestParam(value = "adviseFile",required=false) MultipartFile adviseFile,
			@RequestParam(value = "analysisFile",required=false) MultipartFile analysisFile){
		ResultInfo resultInfo=new ResultInfo();
		
		String solutionId = request.getParameter("solutionId"); // 解决方案Id
		String questionReasons = request.getParameter("questionReasons"); // 常见问题及原因
		String advise = request.getParameter("advise"); // 建议
		String analysis = request.getParameter("analysis"); // 解读
		
		if (StringUtils.isEmpty(solutionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解决方案Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Solution solution=new Solution();
		solution.setSolutionId(Integer.parseInt(solutionId));
		solution.setQuestionReason(questionReasons);
		solution.setAdvise(advise);
		solution.setAnalysis(analysis);
		
		String path=BlueConstants.UPLOAD_PATH+"solution-image/";
		if (questionReasonFile!=null) {
			//上传三个文件
			String qname=System.currentTimeMillis()+questionReasonFile.getOriginalFilename();
			File qFile=new File(path,qname);
			String qUrl=BlueConstants.UPLOAD_URL+"solution-image/"+qname;
			try {
				UploadFileUtils.createChildFolder(qFile);  //创建文件夹
			} catch (Exception e) {
				logger.error("创建子文件夹异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("创建子文件夹异常");
				return resultInfo;
			}
			
			//上传
			try {
				questionReasonFile.transferTo(qFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			solution.setQuestionReasonUrl(qUrl);
		}
		
		if (adviseFile!=null) {
			String adname=System.currentTimeMillis()+adviseFile.getOriginalFilename();
			File adFile=new File(path,adname);
			String adUrl=BlueConstants.UPLOAD_URL+"solution-image/"+adname;
			
			//上传
			try {
				adviseFile.transferTo(adFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			solution.setAdviseUrl(adUrl);
		}
			
		
		if (analysisFile!=null) {

			String anname=System.currentTimeMillis()+analysisFile.getOriginalFilename();
			File anFile=new File(path,anname);
			String anUrl=BlueConstants.UPLOAD_URL+"solution-image/"+anname;
			
			//上传
			try {
				analysisFile.transferTo(anFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			solution.setAnalysisUrl(anUrl);
		}
		
		
		//调用service层的方法
		try {
			Object object=solutionService.modifySolutionReturnObject(solution);
			return object;
		} catch (Exception e) {
			logger.error("修改解决方案异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 根据Id删除解决方案
	 * @param request
	 * @return
	 */
	@PostMapping("/solution/deleteSolutionById")
	@ApiOperation(value="根据Id删除解决方案",response=Solution.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "solutionId", dataType="int", required = true, value = "解决方案的solutionId", defaultValue = "1"),
	})
	public Object deleteSolution(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String solutionId=request.getParameter("solutionId");
		
		if (StringUtils.isEmpty(solutionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解决方案Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=solutionService.deleteSolutionReturnObject(Integer.parseInt(solutionId));
			return object;
		} catch (Exception e) {
			logger.error("删除解决方案异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取解决方案列表
	 * @param request
	 * @return
	 */
	@PostMapping("/solution/getSolutionList")
	@ApiOperation(value="分页获取解决方案列表",response=Solution.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getSolutionList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
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
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=solutionService.getSolutionList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取解决方案列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
