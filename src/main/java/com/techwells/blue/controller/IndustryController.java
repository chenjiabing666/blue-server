package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Industry;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.IndustryService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 行业的controller
 * @author 陈加兵
 */
@Api(description="行业的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class IndustryController {
	
	@Resource
	private IndustryService industryService;
	
	private Logger logger=LoggerFactory.getLogger(IndustryController.class); //日志
	
	/**
	 * 添加行业
	 * @param request
	 * @return
	 */
	@PostMapping("/industry/addIndustry")
	@ApiOperation(value="添加行业",response=Industry.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryName", dataType="String", required = true, value = "行业的industryName", defaultValue = "Tom"),
	})
	public Object addIndustry(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryName=request.getParameter("industryName");
		
		if (StringUtils.isEmpty(industryName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("industryName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Industry industry=new Industry();
		
		//调用service层的方法
		try {
			Object object=industryService.addIndustry(industry);
			return object;
		} catch (Exception e) {
			logger.error("添加行业异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}

	
	/**
	 * 获取行业详情
	 * @param request
	 * @return
	 */
	@PostMapping("/industry/getIndustryById")
	@ApiOperation(value="获取行业详情",response=Industry.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业的industryId", defaultValue = "1"),
	})
	public Object getIndustryById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryId=request.getParameter("industryId");
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=industryService.getIndustryById(Integer.parseInt(industryId));
			return object;
		} catch (Exception e) {
			logger.error("获取行业详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改行业
	 * @param request
	 * @return
	 */
	@PostMapping("/industry/modifyIndustry")
	@ApiOperation(value="修改行业",response=Industry.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业的industryId", defaultValue = "1"),
	})
	public Object modifyIndustry(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryId=request.getParameter("industryId");
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Industry industry=new Industry();
		industry.setIndustryId(Integer.parseInt(industryId));
		
		//调用service层的方法
		try {
			Object object=industryService.modifyIndustryReturnObject(industry);
			return object;
		} catch (Exception e) {
			logger.error("修改行业异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id删除行业
	 * @param request
	 * @return
	 */
	@PostMapping("/industry/deleteIndustryById")
	@ApiOperation(value="根据Id删除行业",response=Industry.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业的industryId", defaultValue = "1"),
	})
	public Object deleteIndustry(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryId=request.getParameter("industryId");
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=industryService.deleteIndustryReturnObject(Integer.parseInt(industryId));
			return object;
		} catch (Exception e) {
			logger.error("删除行业异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取行业列表
	 * @param request
	 * @return
	 */
	@PostMapping("/industry/getIndustryList")
	@ApiOperation(value="分页获取行业列表",response=Industry.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getIndustryList(HttpServletRequest request){
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
			Object object=industryService.getIndustryList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取行业列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
