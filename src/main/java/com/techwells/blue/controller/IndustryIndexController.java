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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.IndustryIndex;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.IndustryIndexService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 行业数据指标的controller
 * @author 陈加兵
 */
@Api(description="行业数据指标的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class IndustryIndexController {
	
	@Resource
	private IndustryIndexService industryIndexService;
	
	private Logger logger=LoggerFactory.getLogger(IndustryIndexController.class); //日志
	
	/**
	 * 添加行业数据指标
	 * @param request
	 * @return
	 */
	@PostMapping("/industryIndex/addIndustryIndex")
	@ApiOperation(value="添加行业数据指标",response=IndustryIndex.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "name", dataType="String", required = true, value = "标杆企业名称", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "pureRate", dataType="String", required = true, value = "销售净利率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "impureRate", dataType="String", required = true, value = "毛利率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "flowRate", dataType="String", required = true, value = "流动比率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "depositRate", dataType="String", required = true, value = "存货周转率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "accountRate", dataType="String", required = true, value = "应收账款周转率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "businessRate", dataType="String", required = true, value = "营业收入增长率", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "profitRate", dataType="String", required = true, value = "净利润增长率", defaultValue = ""),
	})
	public Object addIndustryIndex(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryId=request.getParameter("industryId"); //行业Id
		String industryIndexName=request.getParameter("name"); //标杆企业名称
		String pureRate=request.getParameter("pureRate"); //销售净利率
		String impureRate=request.getParameter("impureRate");  //毛利率
		String flowRate=request.getParameter("flowRate"); //流动比率
		String depositRate=request.getParameter("depositRate"); //存货周转率
		String accountRate=request.getParameter("accountRate"); //应收账款周转率
		String businessRate=request.getParameter("businessRate"); //营业收入增长率
		String profitRate=request.getParameter("profitRate"); //净利润增长率
		
		if (StringUtils.isEmpty(industryIndexName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标杆企业名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(industryId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pureRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("销售净利率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(impureRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("毛利率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(flowRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("流动比率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(depositRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("存货周转率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(accountRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应收账款周转率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(businessRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("营业收入增长率不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(profitRate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("净利润增长率不能为空");
			return resultInfo;
		}
		
		//封装数据
		IndustryIndex industryIndex=new IndustryIndex();
		industryIndex.setName(industryIndexName);
		industryIndex.setCreatedDate(new Date());
		industryIndex.setBusinessRate(businessRate);
		industryIndex.setDepositRate(depositRate);
		industryIndex.setFlowRate(flowRate);
		industryIndex.setImpureRate(impureRate);
		industryIndex.setProfitRate(profitRate);
		industryIndex.setPureRate(pureRate);
		industryIndex.setAccountRate(accountRate);
		industryIndex.setIndustryId(Integer.parseInt(industryId));
		
		//调用service层的方法
		try {
			Object object=industryIndexService.addIndustryIndex(industryIndex);
			return object;
		} catch (Exception e) {
			logger.error("添加行业数据指标异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
	}
	
	/**
	 * 获取行业数据指标详情
	 * @param request
	 * @return
	 */
	@PostMapping("/industryIndex/getIndustryIndexById")
	@ApiOperation(value="获取行业数据指标详情",response=IndustryIndex.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryIndexId", dataType="int", required = true, value = "行业数据指标的industryIndexId", defaultValue = "1"),
	})
	public Object getIndustryIndexById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryIndexId=request.getParameter("industryIndexId");
		
		if (StringUtils.isEmpty(industryIndexId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业数据指标Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=industryIndexService.getIndustryIndexById(Integer.parseInt(industryIndexId));
			return object;
		} catch (Exception e) {
			logger.error("获取行业数据指标详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
	
	/**
	 * 修改行业数据指标
	 * @param request
	 * @return
	 */
	@PostMapping("/industryIndex/modifyIndustryIndex")
	@ApiOperation(value="修改行业数据指标",response=IndustryIndex.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryIndexId", dataType="int", required = true, value = "行业数据指标的industryIndexId", defaultValue = "1"),
	})
	public Object modifyIndustryIndex(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryIndexId=request.getParameter("industryIndexId");
		
		if (StringUtils.isEmpty(industryIndexId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业数据指标Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		IndustryIndex industryIndex=new IndustryIndex();
		industryIndex.setIndustryIndexId(Integer.parseInt(industryIndexId));
		
		//调用service层的方法
		try {
			Object object=industryIndexService.modifyIndustryIndexReturnObject(industryIndex);
			return object;
		} catch (Exception e) {
			logger.error("修改行业数据指标异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除行业数据指标
	 * @param request
	 * @return
	 */
	@PostMapping("/industryIndex/deleteIndustryIndexById")
	@ApiOperation(value="根据Id删除行业数据指标",response=IndustryIndex.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "industryIndexId", dataType="int", required = true, value = "行业数据指标的industryIndexId", defaultValue = "1"),
	})
	public Object deleteIndustryIndex(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String industryIndexId=request.getParameter("industryIndexId");
		
		if (StringUtils.isEmpty(industryIndexId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业数据指标Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=industryIndexService.deleteIndustryIndexReturnObject(Integer.parseInt(industryIndexId));
			return object;
		} catch (Exception e) {
			logger.error("删除行业数据指标异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取行业数据指标列表
	 * @param request
	 * @return
	 */
	@PostMapping("/industryIndex/getIndustryIndexList")
	@ApiOperation(value="分页获取行业数据指标列表",response=IndustryIndex.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "name", dataType="String", required = false, value = "每页显示的数量", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = false, value = "行业Id", defaultValue = ""),
	})
	public Object getIndustryIndexList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String name=request.getParameter("name"); //标杆企业名称
		String industryId=request.getParameter("industryId"); //行业Id
		
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
		
		if (!StringUtils.isEmpty(name)) {
			params.put("name", name);
		}
		
		if (!StringUtils.isEmpty(industryId)) {
			params.put("industryId", Integer.parseInt(industryId));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=industryIndexService.getIndustryIndexList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取行业数据指标列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}
