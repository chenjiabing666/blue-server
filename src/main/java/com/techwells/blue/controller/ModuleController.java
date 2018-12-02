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
import com.techwells.blue.domain.Module;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.ModuleService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 模块的controller
 * @author 陈加兵
 */
@Api(description="模块的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class ModuleController {
	
	@Resource
	private ModuleService moduleService;
	
	private Logger logger=LoggerFactory.getLogger(ModuleController.class); //日志
	
	/**
	 * 添加模块
	 * @param request
	 * @return
	 */
	@PostMapping("/module/addModule")
	@ApiOperation(value="添加模块",response=Module.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleName", dataType="String", required = true, value = "模块的名称"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType="String", required = true, value = "模块描述"),
		@ApiImplicitParam(paramType = "query", name = "highScoreMax", dataType="String", required = true, value = "高分的最高分"),
		@ApiImplicitParam(paramType = "query", name = "highScoreMin", dataType="String", required = true, value = "高分的最低分"),
		@ApiImplicitParam(paramType = "query", name = "mediumScoreMax", dataType="String", required = true, value = "中等的最高分"),
		@ApiImplicitParam(paramType = "query", name = "mediumScoreMin", dataType="String", required = true, value = "中等的最高分"),
		@ApiImplicitParam(paramType = "query", name = "lowScoreMax", dataType="String", required = true, value = "低分的最高分"),
		@ApiImplicitParam(paramType = "query", name = "lowScoreMin", dataType="String", required = true, value = "低分的最低分"),
	})
	public Object addModule(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String moduleName=request.getParameter("moduleName");  //模块名称
		String description=request.getParameter("description"); //模块描述
		String highScoreMax=request.getParameter("highScoreMax"); //高分的最高分
		String highScoreMin=request.getParameter("highScoreMin"); //高分的最低分
		String mediumScoreMax=request.getParameter("mediumScoreMax"); //中等的最高分
		String mediumScoreMin=request.getParameter("mediumScoreMin"); //中等的最高分
		String lowScoreMax=request.getParameter("lowScoreMax"); //低分的最高分
		String lowScoreMin=request.getParameter("lowScoreMin"); //低分的最低分
		
		if (StringUtils.isEmpty(moduleName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(description)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块描述不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(highScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("高分的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(highScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("高分的最低分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mediumScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("中等的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mediumScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("中等的最低分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(lowScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("低分的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(lowScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("低分的最低分不能为空");
			return resultInfo;
		}
		
		//封装数据
		Module module=new Module();
		module.setModuleName(moduleName);
		module.setCreatedDate(new Date());
		module.setHighScoreMax(Integer.parseInt(highScoreMax));
		module.setDescription(description);
		module.setHighScoreMin(Integer.parseInt(highScoreMin));
		module.setMediumScoreMax(Integer.parseInt(mediumScoreMax));
		module.setMediumScoreMin(Integer.parseInt(mediumScoreMin));
		module.setLowScoreMax(Integer.parseInt(lowScoreMax));
		module.setLowScoreMin(Integer.parseInt(lowScoreMin));
		
		//调用service层的方法
		try {
			Object object=moduleService.addModule(module);
			return object;
		} catch (Exception e) {
			logger.error("添加模块异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取模块详情
	 * @param request
	 * @return
	 */
	@PostMapping("/module/getModuleById")
	@ApiOperation(value="获取模块详情",response=Module.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = true, value = "模块的moduleId", defaultValue = "1"),
	})
	public Object getModuleById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String moduleId=request.getParameter("moduleId");  //模块Id
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=moduleService.getModuleById(Integer.parseInt(moduleId));
			return object;
		} catch (Exception e) {
			logger.error("获取模块详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改模块
	 * @param request
	 * @return
	 */
	@PostMapping("/module/modifyModule")
	@ApiOperation(value="修改模块",response=Module.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = true, value = "模块的Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "moduleName", dataType="String", required = true, value = "模块的名称"),
		@ApiImplicitParam(paramType = "query", name = "description", dataType="String", required = true, value = "模块描述"),
		@ApiImplicitParam(paramType = "query", name = "highScoreMax", dataType="String", required = true, value = "高分的最高分"),
		@ApiImplicitParam(paramType = "query", name = "highScoreMin", dataType="String", required = true, value = "高分的最低分"),
		@ApiImplicitParam(paramType = "query", name = "mediumScoreMax", dataType="String", required = true, value = "中等的最高分"),
		@ApiImplicitParam(paramType = "query", name = "mediumScoreMin", dataType="String", required = true, value = "中等的最高分"),
		@ApiImplicitParam(paramType = "query", name = "lowScoreMax", dataType="String", required = true, value = "低分的最高分"),
		@ApiImplicitParam(paramType = "query", name = "lowScoreMin", dataType="String", required = true, value = "低分的最低分"),
	})
	public Object modifyModule(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String moduleId=request.getParameter("moduleId");  //模块Id
		String moduleName=request.getParameter("moduleName");  //模块名称
		String description=request.getParameter("description"); //模块描述
		String highScoreMax=request.getParameter("highScoreMax"); //高分的最高分
		String highScoreMin=request.getParameter("highScoreMin"); //高分的最低分
		String mediumScoreMax=request.getParameter("mediumScoreMax"); //中等的最高分
		String mediumScoreMin=request.getParameter("mediumScoreMin"); //中等的最高分
		String lowScoreMax=request.getParameter("lowScoreMax"); //低分的最高分
		String lowScoreMin=request.getParameter("lowScoreMin"); //低分的最低分
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(moduleName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(description)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块描述不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(highScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("高分的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(highScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("高分的最低分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mediumScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("中等的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mediumScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("中等的最低分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(lowScoreMax)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("低分的最高分不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(lowScoreMin)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("低分的最低分不能为空");
			return resultInfo;
		}
		
		//封装数据
		Module module=new Module();
		module.setModuleId(Integer.parseInt(moduleId));
		module.setModuleName(moduleName);
		module.setCreatedDate(new Date());
		module.setHighScoreMax(Integer.parseInt(highScoreMax));
		module.setDescription(description);
		module.setHighScoreMin(Integer.parseInt(highScoreMin));
		module.setMediumScoreMax(Integer.parseInt(mediumScoreMax));
		module.setMediumScoreMin(Integer.parseInt(mediumScoreMin));
		module.setLowScoreMax(Integer.parseInt(lowScoreMax));
		module.setLowScoreMin(Integer.parseInt(lowScoreMin));
		
		//调用service层的方法
		try {
			Object object=moduleService.modifyModuleReturnObject(module);
			return object;
		} catch (Exception e) {
			logger.error("修改模块异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除模块
	 * @param request
	 * @return
	 */
	@PostMapping("/module/deleteModuleById")
	@ApiOperation(value="根据Id删除模块",response=Module.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = true, value = "模块的moduleId", defaultValue = "1"),
	})
	public Object deleteModule(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String moduleId=request.getParameter("moduleId");
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=moduleService.deleteModuleReturnObject(Integer.parseInt(moduleId));
			return object;
		} catch (Exception e) {
			logger.error("删除模块异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取模块列表
	 * @param request
	 * @return
	 */
	@PostMapping("/module/getModuleList")
	@ApiOperation(value="分页获取模块列表",response=Module.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getModuleList(HttpServletRequest request){
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
			Object object=moduleService.getModuleList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取模块列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据模块名称获取模块（模糊查找）
	 * @param request
	 * @return
	 */
	@PostMapping("/module/getModuleListByName")
	@ApiOperation(value="根据模块名称获取模块",response=Module.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "moduleName", dataType="String", required = true, value = "模块名称", defaultValue = ""),
	})
	public Object getModuleListByName(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String moduleName=request.getParameter("moduleName");  //模块名称
		
		if (StringUtils.isEmpty(moduleName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块名称不能为空");
			return resultInfo;
		}
		
		
		//调用service层的方法
		try {
			Object object=moduleService.getModuleByName(moduleName);
			return object;
		} catch (Exception e) {
			logger.error("获取模块列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取模块列表异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
