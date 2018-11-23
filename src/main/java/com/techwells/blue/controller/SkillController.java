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
import com.techwells.blue.domain.Skill;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.SkillService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 技能的controller
 * @author 陈加兵
 */
@Api(description="技能的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class SkillController {
	
	@Resource
	private SkillService skillService;
	
	private Logger logger=LoggerFactory.getLogger(SkillController.class); //日志
	
	/**
	 * 添加技能
	 * @param request
	 * @return
	 */
	@PostMapping("/skill/addSkill")
	@ApiOperation(value="添加技能",response=Skill.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "skillName", dataType="String", required = true, value = "技能的skillName", defaultValue = "Tom"),
	})
	public Object addSkill(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String skillName=request.getParameter("skillName");
		
		if (StringUtils.isEmpty(skillName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("skillName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Skill skill=new Skill();
		
		//调用service层的方法
		try {
			Object object=skillService.addSkill(skill);
			return object;
		} catch (Exception e) {
			logger.error("添加技能异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取技能详情
	 * @param request
	 * @return
	 */
	@PostMapping("/skill/getSkillById")
	@ApiOperation(value="获取技能详情",response=Skill.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "skillId", dataType="int", required = true, value = "技能的skillId", defaultValue = "1"),
	})
	public Object getSkillById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String skillId=request.getParameter("skillId");
		
		if (StringUtils.isEmpty(skillId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("技能Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=skillService.getSkillById(Integer.parseInt(skillId));
			return object;
		} catch (Exception e) {
			logger.error("获取技能详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改技能
	 * @param request
	 * @return
	 */
	@PostMapping("/skill/modifySkill")
	@ApiOperation(value="修改技能",response=Skill.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "skillId", dataType="int", required = true, value = "技能的skillId", defaultValue = "1"),
	})
	public Object modifySkill(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String skillId=request.getParameter("skillId");
		
		if (StringUtils.isEmpty(skillId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("技能Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Skill skill=new Skill();
		skill.setSkillId(Integer.parseInt(skillId));
		
		//调用service层的方法
		try {
			Object object=skillService.modifySkillReturnObject(skill);
			return object;
		} catch (Exception e) {
			logger.error("修改技能异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id删除技能
	 * @param request
	 * @return
	 */
	@PostMapping("/skill/deleteSkillById")
	@ApiOperation(value="根据Id删除技能",response=Skill.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "skillId", dataType="int", required = true, value = "技能的skillId", defaultValue = "1"),
	})
	public Object deleteSkill(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String skillId=request.getParameter("skillId");
		
		if (StringUtils.isEmpty(skillId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("技能Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=skillService.deleteSkillReturnObject(Integer.parseInt(skillId));
			return object;
		} catch (Exception e) {
			logger.error("删除技能异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取技能列表(后台)
	 * @param request
	 * @return
	 */
	@PostMapping("/skill/getSkillList")
	@ApiOperation(value="分页获取技能列表(后台)",response=Skill.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getSkillList(HttpServletRequest request){
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
			Object object=skillService.getSkillList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取技能列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
