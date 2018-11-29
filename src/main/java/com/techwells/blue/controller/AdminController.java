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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Admin;
import com.techwells.blue.domain.AdminAuthority;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.AdminService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 管理员的controller
 * @author 陈加兵
 */
@Api(description="管理员的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class AdminController {
	
	@Resource
	private AdminService adminService;
	
	private Logger logger=LoggerFactory.getLogger(AdminController.class); //日志
	
	/**
	 * 添加管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/addAdmin")
	@ApiOperation(value="添加管理员",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "adminName", dataType="String", required = true, value = "管理员姓名", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "email", dataType="String", required = true, value = "邮箱", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "password", dataType="String", required = true, value = "密码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "account", dataType="String", required = true, value = "账号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "activated", dataType="String", required = true, value = "状态 账号状态  1 启用 0 禁用", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "authoritys", dataType="String", required = true, value = "权限列表，用逗号分割", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "description", dataType="String", required = true, value = "权限描述", defaultValue = ""),
	})
	public Object addAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String adminName=request.getParameter("adminName");  //管理员姓名
		String email=request.getParameter("email");  //邮箱
		String password=request.getParameter("password");  //密码
		String account=request.getParameter("account");  //账号 
		String mobile=request.getParameter("mobile");  //手机号码
		String activated=request.getParameter("activated");    //状态 账号状态  1 启用 0 禁用
		String authoritys=request.getParameter("authoritys");  //权限列表，用逗号分割
		String description=request.getParameter("description");   //权限描述
		
		//校验参数
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(description)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("权限描述不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(authoritys)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("权限不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(adminName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员姓名不能为空");
			return resultInfo;
		}
		
		Admin admin=new Admin();
		admin.setAdminName(adminName);
		admin.setAccount(account);
		admin.setEmail(email);
		admin.setCreatedDate(new Date());
		admin.setDescription(description);
		admin.setMobile(mobile);
		admin.setActivated(Integer.parseInt(activated));
		admin.setPassword(password);
		AdminAuthority adminAuthority=new AdminAuthority();
		adminAuthority.setAuthoritys(authoritys);
		
		//调用service层的方法
		try {
			Object object=adminService.addAdmin(admin);
			return object;
		} catch (Exception e) {
			logger.error("添加管理员异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 管理员登录
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/login")
	@ApiOperation(value="管理员登录",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "password", dataType="String", required = true, value = "密码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "account", dataType="String", required = true, value = "账号", defaultValue = ""),
	})
	public Object login(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		
		//校验参数
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}
		try {
			Object object=adminService.login(account, password);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("登录异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取管理员详情
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/getAdminById")
	@ApiOperation(value="获取管理员详情",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "adminId", dataType="int", required = true, value = "管理员的adminId", defaultValue = "1"),
	})
	public Object getAdminById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String adminId=request.getParameter("adminId");
		
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=adminService.getAdminById(Integer.parseInt(adminId));
			return object;
		} catch (Exception e) {
			logger.error("获取管理员详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/modifyAdmin")
	@ApiOperation(value="修改管理员",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "adminId", dataType="int", required = true, value = "管理员的adminId", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "email", dataType="String", required = true, value = "邮箱", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "newPwd", dataType="String", required = true, value = "新密码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "account", dataType="String", required = true, value = "账号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "activated", dataType="int", required = true, value = "账号状态  1 启用 0 禁用", defaultValue = "1"),
	})
	public Object modifyAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String email=request.getParameter("email");  //邮箱
		String password=request.getParameter("newPwd");  //新密码
		String account=request.getParameter("account");  //账号
		String mobile=request.getParameter("mobile");  //手机号码
		String activated=request.getParameter("activated");  //账号状态  1 启用 0 禁用
		String adminId=request.getParameter("adminId");    //管理员Id
		
		//校验参数
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱账号不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号状态不能为空");
			return resultInfo;
		}
		
		
		//封装参数
		Admin admin=new Admin();
		admin.setAdminId(Integer.parseInt(adminId));
		admin.setEmail(email);
		
		//密码可以不修改，如果不为空，那么表示用户输入了密码，因此需要修改
		if (!StringUtils.isEmpty(password)) {
			admin.setPassword(password);
		}
		
		admin.setMobile(mobile);
		admin.setAccount(account);
		admin.setActivated(Integer.parseInt(activated));
		
		
		//调用service层的方法
		try {
			Object object=adminService.modifyAdminReturnObject(admin);
			return object;
		} catch (Exception e) {
			logger.error("修改管理员异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/deleteAdminById")
	@ApiOperation(value="根据Id删除管理员",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "adminId", dataType="int", required = true, value = "管理员的adminId", defaultValue = "1"),
	})
	public Object deleteAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String adminId=request.getParameter("adminId");
		
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=adminService.deleteAdminReturnObject(Integer.parseInt(adminId));
			return object;
		} catch (Exception e) {
			logger.error("删除管理员异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取管理员列表
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/getAdminList")
	@ApiOperation(value="分页获取管理员列表",response=Admin.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "account", dataType="String", required = true, value = "账号", defaultValue = ""),
	})
	public Object getAdminList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String account=request.getParameter("account");  //账号
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//校验参数
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
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		//如果account不为空，那么设置成过滤条件
		if (!StringUtils.isEmpty(account)) {
			params.put("account", account);
		}
		pagingTool.setParams(params);
		
		
		//调用service方法
		try {
			Object object=adminService.getAdminList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取管理员列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
