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
import com.techwells.blue.domain.Aaa;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.AaaService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 模板的controller
 * @author 陈加兵
 */
@Api(description="模板的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class AaaController {
	
	@Resource
	private AaaService aaaService;
	
	private Logger logger=LoggerFactory.getLogger(AaaController.class); //日志
	
	/**
	 * 添加模板
	 * @param request
	 * @return
	 */
	@PostMapping("/aaa/addAaa")
	@ApiOperation(value="添加模板",response=Aaa.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "aaaName", dataType="String", required = true, value = "模板的aaaName", defaultValue = "Tom"),
	})
	public Object addAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaName=request.getParameter("aaaName");
		
		if (StringUtils.isEmpty(aaaName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("aaaName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Aaa aaa=new Aaa();
		aaa.setAaaName(aaaName);
		
		//调用service层的方法
		try {
			Object object=aaaService.addAaa(aaa);
			return object;
		} catch (Exception e) {
			logger.error("添加模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取模板详情
	 * @param request
	 * @return
	 */
	@PostMapping("/aaa/getAaaById")
	@ApiOperation(value="获取模板详情",response=Aaa.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "aaaId", dataType="int", required = true, value = "模板的aaaId", defaultValue = "1"),
	})
	public Object getAaaById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模板Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=aaaService.getAaaById(Integer.parseInt(aaaId));
			return object;
		} catch (Exception e) {
			logger.error("获取模板详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改模板
	 * @param request
	 * @return
	 */
	@PostMapping("/aaa/modifyAaa")
	@ApiOperation(value="修改模板",response=Aaa.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "aaaId", dataType="int", required = true, value = "模板的aaaId", defaultValue = "1"),
	})
	public Object modifyAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模板Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Aaa aaa=new Aaa();
		aaa.setAaaId(Integer.parseInt(aaaId));
		
		//调用service层的方法
		try {
			Object object=aaaService.modifyAaaReturnObject(aaa);
			return object;
		} catch (Exception e) {
			logger.error("修改模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 根据Id删除模板
	 * @param request
	 * @return
	 */
	@PostMapping("/aaa/deleteAaaById")
	@ApiOperation(value="根据Id删除模板",response=Aaa.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "aaaId", dataType="int", required = true, value = "模板的aaaId", defaultValue = "1"),
	})
	public Object deleteAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模板Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=aaaService.deleteAaaReturnObject(Integer.parseInt(aaaId));
			return object;
		} catch (Exception e) {
			logger.error("删除模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取模板列表
	 * @param request
	 * @return
	 */
	@PostMapping("/aaa/getAaaList")
	@ApiOperation(value="分页获取模板列表",response=Aaa.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getAaaList(HttpServletRequest request){
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
			Object object=aaaService.getAaaList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取模板列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}
