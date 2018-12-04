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
import com.techwells.blue.domain.Radar;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.RadarService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 雷达图的controller
 * @author 陈加兵
 */
@Api(description="雷达图的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class RadarController {
	
	@Resource
	private RadarService radarService;
	
	private Logger logger=LoggerFactory.getLogger(RadarController.class); //日志
	
	/**
	 * 添加雷达图
	 * @param request
	 * @return
	 */
	@PostMapping("/radar/addRadar")
	@ApiOperation(value="添加雷达图",response=Radar.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = true, value = "模块Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = true, value = "行业Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "avgLevel", dataType="double", required = true, value = "行业平均水平", defaultValue = ""),
	})
	public Object addRadar(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String moduleId=request.getParameter("moduleId");  //模块Id
		String industryId=request.getParameter("industryId");  //行业Id
		String avgLevel=request.getParameter("avgLevel");  //行业平均水平
		
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
		
		if (StringUtils.isEmpty(avgLevel)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("行业平均水平不能为空");
			return resultInfo;
		}
		
		//封装数据
		Radar radar=new Radar();
		radar.setAvgLevel(Double.parseDouble(avgLevel));
		radar.setCreatedDate(new Date());
		radar.setIndustryId(Integer.parseInt(industryId));
		radar.setModuleId(Integer.parseInt(moduleId));
		
		//调用service层的方法
		try {
			Object object=radarService.addRadar(radar);
			return object;
		} catch (Exception e) {
			logger.error("添加雷达图异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取雷达图详情
	 * @param request
	 * @return
	 */
	@PostMapping("/radar/getRadarById")
	@ApiOperation(value="获取雷达图详情",response=Radar.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "radarId", dataType="int", required = true, value = "雷达图的radarId", defaultValue = "1"),
	})
	public Object getRadarById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String radarId=request.getParameter("radarId");
		
		if (StringUtils.isEmpty(radarId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("雷达图Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=radarService.getRadarById(Integer.parseInt(radarId));
			return object;
		} catch (Exception e) {
			logger.error("获取雷达图详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改雷达图
	 * @param request
	 * @return
	 */
	@PostMapping("/radar/modifyRadar")
	@ApiOperation(value="修改雷达图",response=Radar.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "radarId", dataType="int", required = true, value = "雷达图的radarId", defaultValue = "1"),
	})
	public Object modifyRadar(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String radarId=request.getParameter("radarId");
		
		if (StringUtils.isEmpty(radarId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("雷达图Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Radar radar=new Radar();
		radar.setRadarId(Integer.parseInt(radarId));
		
		//调用service层的方法
		try {
			Object object=radarService.modifyRadarReturnObject(radar);
			return object;
		} catch (Exception e) {
			logger.error("修改雷达图异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id删除雷达图
	 * @param request
	 * @return
	 */
	@PostMapping("/radar/deleteRadarById")
	@ApiOperation(value="根据Id删除雷达图",response=Radar.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "radarId", dataType="int", required = true, value = "雷达图的radarId", defaultValue = "1"),
	})
	public Object deleteRadar(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String radarId=request.getParameter("radarId");
		
		if (StringUtils.isEmpty(radarId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("雷达图Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=radarService.deleteRadarReturnObject(Integer.parseInt(radarId));
			return object;
		} catch (Exception e) {
			logger.error("删除雷达图异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取雷达图列表
	 * @param request
	 * @return
	 */
	@PostMapping("/radar/getRadarList")
	@ApiOperation(value="分页获取雷达图列表",response=Radar.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = false, value = "模块Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "industryId", dataType="int", required = false, value = "行业Id", defaultValue = ""),
	})
	public Object getRadarList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String moduleId=request.getParameter("moduleId");  //模块Id
		String industryId=request.getParameter("industryId");  //行业Id
		
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
		
		
		if (!StringUtils.isEmpty(moduleId)) {
			params.put("moduleId", Integer.parseInt(moduleId));
		}
		
		if (!StringUtils.isEmpty(industryId)) {
			params.put("industryId", Integer.parseInt(industryId));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=radarService.getRadarList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取雷达图列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}

