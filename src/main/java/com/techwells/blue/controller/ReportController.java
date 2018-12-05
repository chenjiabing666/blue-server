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
import com.techwells.blue.domain.Report;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.ReportService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 报告的controller
 * @author 陈加兵
 */
@Api(description="报告的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class ReportController {
	
	@Resource
	private ReportService reportService;
	
	private Logger logger=LoggerFactory.getLogger(ReportController.class); //日志
	
	/**
	 * 添加报告
	 * @param request
	 * @return
	 */
	@PostMapping("/report/addReport")
	@ApiOperation(value="添加报告",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "reportName", dataType="String", required = true, value = "报告的reportName", defaultValue = "Tom"),
	})
	public Object addReport(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String reportName=request.getParameter("reportName");
		
		if (StringUtils.isEmpty(reportName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("reportName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Report report=new Report();
		report.setReportName(reportName);
		
		//调用service层的方法
		try {
			Object object=reportService.addReport(report);
			return object;
		} catch (Exception e) {
			logger.error("添加报告异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
	}
	
	/**
	 * 获取报告详情
	 * @param request
	 * @return
	 */
	@PostMapping("/report/getReportById")
	@ApiOperation(value="获取报告详情",response=Report.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "reportId", dataType="int", required = true, value = "报告的reportId", defaultValue = "1"),
	})
	public Object getReportById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String reportId=request.getParameter("reportId");
		
		if (StringUtils.isEmpty(reportId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("报告Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=reportService.getReportById(Integer.parseInt(reportId));
			return object;
		} catch (Exception e) {
			logger.error("获取报告详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
	
	/**
	 * 修改报告
	 * @param request
	 * @return
	 */
	@PostMapping("/report/modifyReport")
	@ApiOperation(value="修改报告",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "reportId", dataType="int", required = true, value = "报告的reportId", defaultValue = "1"),
	})
	public Object modifyReport(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String reportId=request.getParameter("reportId");
		
		if (StringUtils.isEmpty(reportId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("报告Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Report report=new Report();
		report.setReportId(Integer.parseInt(reportId));
		
		//调用service层的方法
		try {
			Object object=reportService.modifyReportReturnObject(report);
			return object;
		} catch (Exception e) {
			logger.error("修改报告异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id删除报告
	 * @param request
	 * @return
	 */
	@PostMapping("/report/deleteReportById")
	@ApiOperation(value="根据Id删除报告",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "reportId", dataType="int", required = true, value = "报告的reportId", defaultValue = "1"),
	})
	public Object deleteReport(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String reportId=request.getParameter("reportId");
		
		if (StringUtils.isEmpty(reportId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("报告Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=reportService.deleteReportReturnObject(Integer.parseInt(reportId));
			return object;
		} catch (Exception e) {
			logger.error("删除报告异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取报告列表
	 * @param request
	 * @return
	 */
	@PostMapping("/report/getReportList")
	@ApiOperation(value="分页获取报告列表",response=Report.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getReportList(HttpServletRequest request){
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
			Object object=reportService.getReportList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取报告列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}
