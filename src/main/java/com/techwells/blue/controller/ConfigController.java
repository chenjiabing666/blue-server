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
import com.techwells.blue.domain.Config;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.ConfigService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 系统配置的controller
 * @author 陈加兵
 */
@Api(description="系统配置的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class ConfigController {
	
	@Resource
	private ConfigService configService;
	
	private Logger logger=LoggerFactory.getLogger(ConfigController.class); //日志
	
	/**
	 * 添加系统配置
	 * @param request
	 * @return
	 */
	@PostMapping("/config/addConfig")
	@ApiOperation(value="添加系统配置",response=Config.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "configName", dataType="String", required = true, value = "系统配置的configName", defaultValue = "Tom"),
	})
	public Object addConfig(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String configName=request.getParameter("configName");
		
		if (StringUtils.isEmpty(configName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("configName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Config config=new Config();
		
		//调用service层的方法
		try {
			Object object=configService.addConfig(config);
			return object;
		} catch (Exception e) {
			logger.error("添加系统配置异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取系统配置详情
	 * @param request
	 * @return
	 */
	@PostMapping("/config/getConfigById")
	@ApiOperation(value="获取系统配置详情",response=Config.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "configId", dataType="int", required = true, value = "系统配置的configId", defaultValue = "1"),
	})
	public Object getConfigById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String configId=request.getParameter("configId");
		
		if (StringUtils.isEmpty(configId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("系统配置Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=configService.getConfigById(Integer.parseInt(configId));
			return object;
		} catch (Exception e) {
			logger.error("获取系统配置详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改系统配置
	 * @param request
	 * @return
	 */
	@PostMapping("/config/modifyConfig")
	@ApiOperation(value="修改系统配置",response=Config.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "configId", dataType="int", required = true, value = "系统配置的configId", defaultValue = "1"),
	})
	public Object modifyConfig(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String configId=request.getParameter("configId");
		
		if (StringUtils.isEmpty(configId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("系统配置Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Config config=new Config();
		config.setConfigId(Integer.parseInt(configId));
		
		//调用service层的方法
		try {
			Object object=configService.modifyConfigReturnObject(config);
			return object;
		} catch (Exception e) {
			logger.error("修改系统配置异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 根据Id删除系统配置
	 * @param request
	 * @return
	 */
	@PostMapping("/config/deleteConfigById")
	@ApiOperation(value="根据Id删除系统配置",response=Config.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "configId", dataType="int", required = true, value = "系统配置的configId", defaultValue = "1"),
	})
	public Object deleteConfig(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String configId=request.getParameter("configId");
		
		if (StringUtils.isEmpty(configId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("系统配置Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=configService.deleteConfigReturnObject(Integer.parseInt(configId));
			return object;
		} catch (Exception e) {
			logger.error("删除系统配置异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取系统配置列表
	 * @param request
	 * @return
	 */
	@PostMapping("/config/getConfigList")
	@ApiOperation(value="分页获取系统配置列表",response=Config.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getConfigList(HttpServletRequest request){
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
			Object object=configService.getConfigList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取系统配置列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
