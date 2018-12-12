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

import org.apache.poi.hssf.dev.EFBiffViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Compete;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.CompeteService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 	企业竞争力和行业平均水平的图形、企业市场定位和行业平均水平图形的配置controller
 * @author 陈加兵
 */
@Api(description="企业竞争力和行业平均水平的图形、企业市场定位和行业平均水平图形的配置api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class CompeteController {
	
	@Resource
	private CompeteService competeService;
	
	private Logger logger=LoggerFactory.getLogger(CompeteController.class); //日志
	
	/**
	 * 添加竞争力
	 * @param request
	 * @return
	 */
	@PostMapping("/compete/addCompete")
	@ApiOperation(value="添加竞争力",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "quality", dataType="double", required = true, value = "质量的平均水平", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "deliver", dataType="double", required = true, value = "交付的平均水平", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "cost", dataType="double", required = true, value = "成本的平均水平", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "score", dataType="double", required = true, value = "企业的健康得分", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "compete", dataType="double", required = true, value = "竞争力平均分值", defaultValue = ""),
	})
	public Object addCompete(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryId=request.getParameter("industryId"); //行业Id
		String quality=request.getParameter("quality");  //质量的平均水平
		String deliver=request.getParameter("deliver");  //交付的平均水平
		String cost=request.getParameter("cost");        //成本的平均水平
		String score=request.getParameter("score");      //企业的健康得分
		String competeScore=request.getParameter("compete");  //竞争力平均分值
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(quality)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("质量的平均水平不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(deliver)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("交付的平均水平不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(cost)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("成本的平均水平不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(score)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("企业的健康得分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(competeScore)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("竞争力平均分值不能为空");
			return resultInfo;
		}
		
		//封装数据
		Compete compete=new Compete();
		compete.setCompete(Double.parseDouble(competeScore));
		compete.setCost(Double.parseDouble(cost));
		compete.setCreatedDate(new Date());
		compete.setDeliver(Double.parseDouble(deliver));
		compete.setIndustryId(Integer.parseInt(industryId));
		compete.setQuality(Double.parseDouble(quality));
		compete.setScore(Double.parseDouble(score));
		
		//调用service层的方法
		try {
			Object object=competeService.addCompete(compete);
			return object;
		} catch (Exception e) {
			logger.error("添加竞争力异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取竞争力详情
	 * @param request
	 * @return
	 */
	@PostMapping("/compete/getCompeteById")
	@ApiOperation(value="获取竞争力详情",response=Compete.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "competeId", dataType="int", required = true, value = "竞争力的competeId", defaultValue = "1"),
	})
	public Object getCompeteById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String competeId=request.getParameter("competeId");
		
		if (StringUtils.isEmpty(competeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("竞争力Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=competeService.getCompeteById(Integer.parseInt(competeId));
			return object;
		} catch (Exception e) {
			logger.error("获取竞争力详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改竞争力
	 * @param request
	 * @return
	 */
	@PostMapping("/compete/modifyCompete")
	@ApiOperation(value="修改竞争力",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "competeId", dataType="int", required = true, value = "竞争力的competeId", defaultValue = "1"),
	})
	public Object modifyCompete(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String competeId=request.getParameter("competeId");
		
		if (StringUtils.isEmpty(competeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("竞争力Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Compete compete=new Compete();
		compete.setCompeteId(Integer.parseInt(competeId));
		
		//调用service层的方法
		try {
			Object object=competeService.modifyCompeteReturnObject(compete);
			return object;
		} catch (Exception e) {
			logger.error("修改竞争力异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 根据Id删除竞争力
	 * @param request
	 * @return
	 */
	@PostMapping("/compete/deleteCompeteById")
	@ApiOperation(value="根据Id删除",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "competeId", dataType="int", required = true, value = "竞争力的competeId", defaultValue = "1"),
	})
	public Object deleteCompete(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String competeId=request.getParameter("competeId");
		
		if (StringUtils.isEmpty(competeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("竞争力Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=competeService.deleteCompeteReturnObject(Integer.parseInt(competeId));
			return object;
		} catch (Exception e) {
			logger.error("删除竞争力异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取配置列表
	 * @param request
	 * @return
	 */
	@PostMapping("/compete/getCompeteList")
	@ApiOperation(value="分页获取配置列表",response=Compete.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = false, value = "行业Id", defaultValue = ""),
//		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = false, value = "模块Id", defaultValue = ""),
	})
	public Object getCompeteList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String industryId=request.getParameter("industryId"); //行业Id
//		String moduleId=request.getParameter("moduleId");  //模块Id
		
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
		
		if (!StringUtils.isEmpty(industryId)) {
			params.put("industryId", Integer.parseInt(industryId));
		}
		
//		if (!StringUtils.isEmpty(moduleId)) {
//			params.put("moduleId", Integer.parseInt(moduleId));
//		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=competeService.getCompeteList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取竞争力列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}
