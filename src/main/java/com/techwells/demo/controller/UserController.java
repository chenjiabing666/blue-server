package com.techwells.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.demo.domain.User;
import com.techwells.demo.service.UserService;
import com.techwells.demo.util.ResultInfo;

/**
 * 用户的控制器层
 * @author 陈加兵
 */
@Api(description="用户的api接口")   //标注说明改接口的作用
@RestController
public class UserController {
	@Resource
	private UserService userService;
	
	/**
	 * 根据用户Id获取用户详情
	 * @param request
	 * @return
	 */
	@ApiOperation(value="根据Id获取用户详情",notes="这里是个详细的描述",response=User.class)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="Integer", required = true, value = "用户的id", defaultValue = "1"),
	})
	@PostMapping("/user/getUserById")
	public Object getUserById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=userService.getUserById(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 添加用户
	 * @param request
	 * @return
	 */
	@ApiOperation(value="添加用户",notes="传入用户名和性别")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userName", dataType="String", required = false, value = "用户名", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "gender", dataType="Integer", required = true, value = "性别", defaultValue = "1"),
	})
	@PostMapping("/user/add")
	public Object addUser(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userName=request.getParameter("userName");
		String gender=request.getParameter("gender");  
		
		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户名不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(gender)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("性别不能为空");
			return resultInfo;
		}
		
		User user=new User();
		user.setUserName(userName);
		user.setGender(Integer.parseInt(gender));
		
		try {
			Object object=userService.addUser(user);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	
	
	
	
}
