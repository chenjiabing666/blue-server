package com.techwells.blue.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.aliyuncs.http.HttpResponse;
import com.techwells.blue.util.BlueConstants;
import com.techwells.blue.util.CRCode;
import com.techwells.blue.util.CrcodeUtils;
import com.techwells.blue.util.SendMailUtils;
import com.techwells.blue.util.SendSmsUtil;
import com.techwells.blue.util.UploadFileUtils;
import com.techwells.blue.domain.EnterpriseAuth;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.rs.UserRecommendVos;
import com.techwells.blue.service.UserService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.DateUtil;

/**
 * 用户的controller
 * @author 陈加兵
 */
@Api(description="用户的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class UserController {
	
	@Resource
	private UserService userService;

	@Value("${bindEmailUrl}")
	private String bindEmailUrl;
	
	
	private Logger logger=LoggerFactory.getLogger(UserController.class); //日志
	
	/**
	 * 添加用户
	 * @param request
	 * @return
	 */
	@PostMapping("/user/addUser")
	@ApiOperation(value="添加用户",response=User.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userName", dataType="String", required = true, value = "用户的userName", defaultValue = "Tom"),
	})
	public Object addUser(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userName=request.getParameter("userName");
		
		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("userName不能为空");
			return resultInfo;
		}
		
		//封装数据
		User user=new User();
		user.setUserName(userName);
		
		//调用service层的方法
		try {
			Object object=userService.addUser(user);
			return object;
		} catch (Exception e) {
			logger.error("添加用户异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取用户详情
	 * @param request
	 * @return
	 */
	@PostMapping("/user/getUserById")
	@ApiOperation(value="获取用户详情",response=UserRecommendVos.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
	})
	public Object getUserById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=userService.getUserById(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			logger.error("获取用户详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改用户基本信息
	 * @param request
	 * @return
	 */
	@PostMapping("/user/modifyUser")
	@ApiOperation(value="修改用户基本信息（用户名，头像）",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id 必填", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "nickName", dataType="String", required = false, value = "用户名 选填", defaultValue = "爱撒谎的男孩"),
//		@ApiImplicitParam(paramType = "form", name = "userIcon", dataType="multipart/form-data", required =false, value = "用户头像 选填"),
	})
	public Object modifyUser(HttpServletRequest request,@RequestParam(value="userIcon",required=false)MultipartFile userIcon){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String nickName=request.getParameter("nickName");  //昵称
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		User user=new User();
		user.setUserId(Integer.parseInt(userId));
		
		//如果昵称不为空，那么需要修改
		if (!StringUtils.isEmpty(nickName)) {
			user.setNickName(nickName);
		}
		
		
		//如果头像不为空，那么需要修改头像
		if (userIcon!=null) {
			String iconName=System.currentTimeMillis()+userIcon.getOriginalFilename();
			String url=BlueConstants.UPLOAD_URL+"user-image/"+iconName;
			String path=BlueConstants.UPLOAD_PATH+"user-image/";
			File targetFile=new File(path,iconName);
			
			try {
				UploadFileUtils.createChildFolder(targetFile);
			} catch (Exception e) {
				logger.error("创建子文件夹异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("异常");
				return resultInfo;
			}
			
			try {
				userIcon.transferTo(targetFile);
			} catch (Exception e) {
				logger.error("上传文件异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("异常");
				return resultInfo;
			}
			
			user.setUserIcon(url);  //保存链接
		}
		
		
		//调用service层的方法
		try {
			Object object=userService.modifyUser(user);
			return object;
		} catch (Exception e) {
			logger.error("修改用户异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id删除用户
	 * @param request
	 * @return
	 */
	@PostMapping("/user/deleteUserById")
	@ApiOperation(value="根据Id删除用户",response=User.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="Interger", required = true, value = "用户的userId", defaultValue = "1"),
	})
	public Object deleteUser(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=userService.deleteUser(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			logger.error("删除用户异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取用户列表（后台）
	 * @param request
	 * @return
	 */
	@PostMapping("/user/getUserList")
	@ApiOperation(value="分页获取用户列表（后台）",response=User.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = false, value = "用户Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = false, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "nickName", dataType="String", required = false, value = "昵称", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "gender", dataType="int", required = false, value = "性别 1男 2 女 ", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "userType", dataType="int", required = false, value = "用户类型：1、普通用户，2、企业用户，3、vip", defaultValue = ""),
	})
	public Object getUserList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//筛选条件
		String userId=request.getParameter("userId"); //用户Id
		String mobile=request.getParameter("mobile");  //手机号码
		String nickName=request.getParameter("nickName");  //昵称
		String gender=request.getParameter("gender");  //性别 1男 2 女 
		String userType=request.getParameter("userType");  //用户类型：1、普通用户，2、企业用户，3、vip
		
		
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
		
		if (!StringUtils.isEmpty(userId)) {
			params.put("userId", Integer.parseInt(userId));
		}
		
		if (!StringUtils.isEmpty(mobile)) {
			params.put("mobile",mobile);
		}
		
		if (!StringUtils.isEmpty(nickName)) {
			params.put("nickName", nickName);
		}
		
		if (!StringUtils.isEmpty(gender)) {
			params.put("gender", Integer.parseInt(gender));
		}
		
		if (!StringUtils.isEmpty(userType)) {
			params.put("userType", Integer.parseInt(userType));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=userService.getUserList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取用户列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	@PostMapping("/user/regist")
	@ApiOperation(value="用户注册",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "password", dataType="String", required = true, value = "密码", defaultValue = "123456"),
		@ApiImplicitParam(paramType = "query", name = "invitedCode", dataType="String", required = false, value = "邀请码，选填", defaultValue = "123456"),
	})
	public Object regist(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String mobile=request.getParameter("mobile");  //手机号码
		String password=request.getParameter("password");//密码
		String invitedCode=request.getParameter("invitedCode");  //邀请码，选填
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}
		
		//根据手机号码获取用户信息
		User user=null;
		try {
			user=userService.getUserByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		if (user!=null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户已经注册");
			return resultInfo;
		}
		
		
		//如果邀请码不为空，那么需要验证
		if (!StringUtils.isEmpty(invitedCode)) {
			// 根据邀请码获取用户信息
			User user1 = null;
			try {
				user1 = userService.getUserByInvitedCode(invitedCode);
			} catch (Exception e) {
				logger.error("获取用户信息异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("异常");
				return resultInfo;
			}

			if (user1 == null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("该邀请码不存在");
				return resultInfo;
			}
			
			user.setRecommendId(user1.getUserId()); //设置推荐人Id
			
		}
		
		//用户没有注册，封装数据
		
		//封装数据
		user=new User();
		user.setUserName(mobile);
		user.setCreatedDate(new Date());
		user.setPassword(password);
		user.setMobile(mobile);
		user.setPoint(0); //设置用户积分
		user.setUserType(1);  //普通用户
		user.setInvitedCode(System.currentTimeMillis()+"");  //随机设置一个邀请码
		//调用service层的方法
		try {
			Object object=userService.addUser(user);
			return object;
		} catch (Exception e) {
			logger.error("添加用户异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @return
	 */
	@PostMapping("/user/getCode")
	@ApiOperation(value="获取验证码",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
	})
	public Object getCode(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String mobile = request.getParameter("mobile");

		// 校验参数

		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}


		String code = SendSmsUtil.getSixRadam(); // 获取六位随机的验证码

		// 验证码限制，一个小时十条
		CRCode crCode = (CRCode) request.getSession().getAttribute("code");

		// 如果session中没有信息，那么可以直接发送
		if (crCode == null) {
			try {
				SendSmsUtil.sendUserCrCode(mobile, code); // 发送
				crCode = new CRCode();
				crCode.setMobile(mobile);
				crCode.setCrCode(code);
				crCode.setNumber(1);
				crCode.setSendDate(new Date());
				request.getSession().setAttribute("code", crCode);
				resultInfo.setMessage("发送成功");
				return resultInfo;
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setMessage("验证码发送异常");
				resultInfo.setCode("-1");
				return resultInfo;
			}
		}

		// 如果session已经存在了，需要判断一个小时之内的条数

		// 判断是否间隔了一个小时了

		Long sendTime = crCode.getSendDate().getTime();

		// 如果大于一个小时了，那么可以直接发送了
		if (((System.currentTimeMillis() - sendTime) / 1000 / 3600) > 1) {
			try {
				SendSmsUtil.sendUserCrCode(mobile, code); // 发送
				crCode.setMobile(mobile);
				crCode.setCrCode(code);
				crCode.setNumber(1); // 短信的条数恢复 成一条
				crCode.setSendDate(new Date());
				request.getSession().setAttribute("code", crCode);
				resultInfo.setMessage("发送成功");
				return resultInfo;
			} catch (Exception e) {
				resultInfo.setMessage("验证码发送异常");
				resultInfo.setCode("-1");
				return resultInfo;
			}
		} else { // 如果没有超过一个小时，那么验证短信的条数

			if (crCode.getNumber() > 10) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("一个小时之内只能发送10条");
				return resultInfo;
			} else {
				try {
					SendSmsUtil.sendUserCrCode(mobile, code); // 发送
					crCode.setMobile(mobile);
					crCode.setCrCode(code);
					crCode.setNumber(crCode.getNumber() + 1); // 短信的条数恢复 成一条
					crCode.setSendDate(new Date());
					request.getSession().setAttribute("code", crCode);
					resultInfo.setMessage("发送成功");
					return resultInfo;
				} catch (Exception e) {
					resultInfo.setMessage("验证码发送异常");
					resultInfo.setCode("-1");
					return resultInfo;
				}
			}

		}

	}
	
	/**
	 * 校验验证码的正确
	 * @param request
	 * @param session
	 * @return
	 */
	@PostMapping("/user/validateCode")
	@ApiOperation(value="验证验证码",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "code", dataType="String", required = true, value = "验证码", defaultValue = "123456"),
	})
	public Object validateCode(HttpServletRequest request,HttpSession session){
		ResultInfo resultInfo=new ResultInfo();
		String code=request.getParameter("code");  //验证码
		String mobile=request.getParameter("mobile");  //手机号
		
		if (StringUtils.isEmpty(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		Object crcode=session.getAttribute("code");  //从session中获取
		
		if (crcode==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先获取验证码");
			return resultInfo;
		}
		
		//session中的验证码不为空
		CRCode crCode2=(CRCode)crcode;  //强转
		if (!crCode2.getMobile().equals(mobile)) {  //号码不一致
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先获取验证码");
			return resultInfo;
		}
		
			
		//校验验证码
		if (!crCode2.getCrCode().equals(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不正确");
			return resultInfo;
		}
		resultInfo.setMessage("验证通过");
		return resultInfo;
	}
	
	/**
	 * 用户登录
	 */
	@PostMapping("/user/login")
	@ApiOperation(value="用户注册",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = true, value = "登录的类型 1 账号登录 2 快速登录", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "password", dataType="String", required = false, value = "密码，选填", defaultValue = "123456"),
		@ApiImplicitParam(paramType = "query", name = "code", dataType="String", required = false, value = "验证码，选填", defaultValue = "123456"),
	})
	public Object login(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String type=request.getParameter("type");  //登录的类型 1 账号登录 2 快速登录
		String mobile=request.getParameter("mobile");  //号码
		String pwd=request.getParameter("password");  //密码
		String code=request.getParameter("code"); //验证码
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("登录的类型不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		//根据手机号码获取用户信息
		User user=null;
				
		try {
			user=userService.getUserByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取用户信息异常");
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}	
		
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户不存在");
			return resultInfo;
		}
		
		//用户已经注册，需要验证密码或者验证码
		
		if (type.equals("1")) {  //账号登录
			if (StringUtils.isEmpty(pwd)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("密码不能为空");
				return resultInfo;
			}
			
			if (!user.getPassword().equals(pwd)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("密码不正确");
				return resultInfo;
			}
		}else if(type.equals("2")){  //快速登录，验证玛
			
			if (StringUtils.isEmpty(code)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("验证码不能为空");
				return resultInfo;
			}
			
			CRCode crCode=(CRCode) request.getSession().getAttribute("code");
			if (crCode==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请先发送验证码");
				return resultInfo;
			}
			
			if (!crCode.getMobile().equals(mobile)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请先发送验证码");
				return resultInfo;
			}
			
			if (!code.equals(crCode.getCrCode())) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("验证码不正确");
				return resultInfo;
			}
		}else {  //没有其他的方式
			resultInfo.setCode("-1");
			resultInfo.setMessage("请填写正确的登录方式");
			return resultInfo;
		}
		//修改用户登录的信息
		user.setLastLoginDate(new Date());  //设置登录时间
		
		int count=0;
		try {
			count=userService.modifyUserReturnCount(user);
		} catch (Exception e) {
			logger.error("修改用户信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改用户信息失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("登录成功");
		resultInfo.setResult(user);
		return resultInfo;
	}
	
	/**
	 * 绑定手机号码
	 * @param request
	 * @return
	 */
	@PostMapping("/user/bindMobile")
	@ApiOperation(value="绑定手机号码",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "code", dataType="String", required = true, value = "验证码", defaultValue = ""),
	})
	public Object bindAccount(HttpServletRequest request){
		ResultInfo result=new ResultInfo();
		String userId=request.getParameter("userId"); //用户Id
		
		String mobile=request.getParameter("mobile");  //手机号码
		String code=request.getParameter("code");  //验证码 
		
		if (StringUtils.isEmpty(userId)) {
			result.setCode("-1");
			result.setMessage("用户Id不能为空");
			return result;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			result.setCode("-1");
			result.setMessage("手机号码不能为空");
			return result;
		}
		
		if (StringUtils.isEmpty(code)) {
			result.setCode("-1");
			result.setMessage("验证码不能为空");
			return result;
		}
		
		
		User user=null;
		// 绑定手机号码
		try {
			user = userService.getUserByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取用户信息异常", e);
			result.setCode("-1");
			result.setMessage("异常");
			return result;
		}

		// 绑定手机号码肯定是没有手机号码，所以不用判断是否是同一个人
		if (user != null) {
			result.setCode("-1");
			result.setMessage("该手机号已经被绑定");
			return result;
		}

		// 判断验证码是否正确
		CRCode crCode = (CRCode) request.getSession().getAttribute("code"); // 获取验证码

		if (crCode == null) {
			result.setCode("-1");
			result.setMessage("请先发送验证码");
			return result;
		}

		if (!mobile.equals(crCode.getCrCode())) {
			result.setCode("-1");
			result.setMessage("请先发送验证码");
			return result;
		}

		if (!crCode.getCrCode().equals(code)) {
			result.setCode("-1");
			result.setMessage("验证码不正确");
			return result;
		}

		// user=null
		user = new User(); // 新建
		user.setMobile(mobile);// 绑定
		user.setUserId(Integer.parseInt(userId));
		
		
		//修改
		
		int count=0;
		try {
			count=userService.modifyUserReturnCount(user);
		} catch (Exception e) {
			logger.error("修改用户信息异常", e);
			result.setCode("-1");
			result.setMessage("异常");
			return result;
		}
		
		
		if (count==0) {
			result.setCode("-1");
			result.setMessage("绑定失败");
			return result;
		}
		
		result.setMessage("绑定成功");
		return result;
	}
	
	/**
	 * 绑定邮箱（用户点击发送过去的链接）
	 * @param request
	 * @return
	 */
	@GetMapping("/user/checkEmail")
	@ApiOperation(value="绑定邮箱（用户点击发送过去的链接）",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "email", dataType="String", required = true, value = "邮箱", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "code", dataType="String", required = true, value = "随机数字", defaultValue = ""),
	})
	public Object checkEmail(HttpServletRequest request){
		ResultInfo result=new ResultInfo();
		String userId=request.getParameter("userId"); //用户Id
		String email=request.getParameter("email");  //邮箱
		String code=request.getParameter("code");  //随机数字
		
		if (StringUtils.isEmpty(userId)) {
			result.setCode("-1");
			result.setMessage("用户Id不能为空");
			return result;
		}
		
		if (StringUtils.isEmpty(email)) {
			result.setCode("-1");
			result.setMessage("邮箱不能为空");
			return result;
		}
		
		if (StringUtils.isEmpty(code)) {
			result.setCode("-1");
			result.setMessage("随机数字不能为空");
			return result;
		}
		
		
		try {
			Object object=userService.checkEmail(Integer.parseInt(userId),email,code);
			return object;
		} catch (Exception e) {
			logger.error("绑定邮箱异常",e);
			result.setCode("-1");
			result.setMessage("绑定邮箱异常");
			return result;
		}
	}
	
	/**
	 * 发送链接到指定的邮箱
	 * @param request
	 * @return
	 */
	@PostMapping("/user/sendEmailUrl")
	@ApiOperation(value="发送链接到指定的邮箱",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "email", dataType="String", required = true, value = "邮箱地址", defaultValue = "123456@163.com"),
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
	})
	public Object sendEmailUrl(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String email=request.getParameter("email"); //邮箱账号
		String userId=request.getParameter("userId");  //用户Id
		
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱地址不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		User user=null;
		try {
			user = userService.getUserByEmail(email);
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		if (user!=null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该邮箱已经被绑定了");
			return resultInfo;
		}
		
		
		String code=SendSmsUtil.getSixRadam(); //生成随机的六位数字
		user=new User();
		user.setUserId(Integer.parseInt(userId));
		user.setEmailCode(code);
		
		//更新数据
		int count=0;
		try {
			count = userService.modifyUserReturnCount(user);
		} catch (Exception e) {
			logger.error("修改用户信息异常");
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改用户信息异常");
			return resultInfo;
		}
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改用户信息失败");
			return resultInfo;
		}
		
		//修改成功，可以发送链接
		String url=bindEmailUrl+"email="+email+"&userId="+userId+"&code="+code;  //验证的链接
		
		//没有被绑定，那么需要发送验证链接
		try {
			SendMailUtils.sendEmail(email,"蓝色按钮", "点击链接绑定邮箱："+url);
		} catch (Exception e) {
			logger.error("发送邮件异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		resultInfo.setMessage("发送成功");
		return resultInfo;
	}
	
	/**
	 * 取消绑定邮箱或者手机号码
	 * @param request
	 * @return
	 */
	@PostMapping("/user/cancelBindAccount")
	@ApiOperation(value="取消绑定邮箱或者手机号码",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = true, value = "解绑类型  1 手机号码 2 邮箱", defaultValue = "1"),
	})
	public Object cancelBindAccount(HttpServletRequest request){
		ResultInfo result=new ResultInfo();
		String userId=request.getParameter("userId"); //用户Id
		String type=request.getParameter("type"); //解绑类型  1 手机号码 2 邮箱
		
		if (StringUtils.isEmpty(userId)) {
			result.setCode("-1");
			result.setMessage("用户Id不能为空");
			return result;
		}
		
		if (StringUtils.isEmpty(type)) {
			result.setCode("-1");
			result.setMessage("解绑类型不能为为空");
			return result;
		}
		
		try {
			Object object=userService.cancelBindAccount(Integer.parseInt(userId),Integer.parseInt(type));
			return object;
		} catch (Exception e) {
			logger.error("取消绑定异常",e);
			result.setCode("-1");
			result.setMessage("异常");
			return result;
		}
	}
	
	
	/**
	 * 忘记密码通过手机号码找回
	 * @param request
	 * @return
	 */
	@PostMapping("/user/forgetPwdByMobile")
	@ApiOperation(value="忘记密码通过手机号码找回",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "code", dataType="String", required = true, value = "验证码", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "newPwd", dataType="String", required = true, value = "密码", defaultValue = "123456"),
	})
	public Object forgetPwdByMobile(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String mobile=request.getParameter("mobile");  //手机号码
		String code=request.getParameter("code"); //验证码
		String newPwd=request.getParameter("newPwd"); //新密码
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(newPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("新密码不能为空");
			return resultInfo;
		}
		
		
		User user=null;
		try {
			user=userService.getUserByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取用户信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该号码未注册");
			return resultInfo;
		}
		
		boolean flag=false;
		//该用户已经注册了，判断验证码
		try {
				flag=CrcodeUtils.validate(request.getSession(), mobile, code);
		} catch (Exception e) {
			logger.error("校验验证码异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		if (flag==false) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不正确");
			return resultInfo;
		}
		
		//验证码正确，需要修改用户信息
		user.setPassword(newPwd);
		try {
			Object object=userService.modifyUser(user);
			return object;
		} catch (Exception e) {
			logger.error("修改用户信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@PostMapping("/user/modifyPwd")
	@ApiOperation(value="修改密码",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "oldPwd", dataType="String", required = true, value = "旧密码", defaultValue = "126266"),
		@ApiImplicitParam(paramType = "query", name = "newPwd", dataType="String", required = true, value = "新密码", defaultValue = "123456"),
	})
	public Object modifyPwd(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId"); //用户Id
		String oldPwd=request.getParameter("oldPwd");  //旧密码
		String newPwd=request.getParameter("newPwd");  //新密码
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(oldPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("旧密码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(newPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("新密码不能为空");
			return resultInfo;
		}
		
		
		try {
			Object object=userService.modifyPwd(Integer.parseInt(userId),oldPwd,newPwd);
			return object;
		} catch (Exception e) {
			logger.error("修改密码异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 企业认证
	 * @param request
	 * @return
	 */
	@PostMapping("/user/enterpriseAuth")
	@ApiOperation(value="企业认证",response=ResultInfo.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "company", dataType="String", required = true, value = "公司名称", defaultValue = "泰闻信息科技有限公司"),
		@ApiImplicitParam(paramType = "query", name = "organization", dataType="String", required = true, value = "组织机构", defaultValue = "法轮功"),
	})
	public Object enterpriseAuth(HttpServletRequest request,@RequestParam(value="license",required=false)MultipartFile license){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String company=request.getParameter("company");  //公司名称
		String organization=request.getParameter("organization");  //组织机构
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(company)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("公司名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(organization)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("组织机构不能为空");
			return resultInfo;
		}
		
		if (license==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("营业执照不能为空");
			return resultInfo;
		}
		
		
		//封装数据
		EnterpriseAuth auth=new EnterpriseAuth();
		auth.setUserId(Integer.parseInt(userId));
		auth.setCompanyName(company);
		auth.setCreatedDate(new Date());
		auth.setOrganization(organization);
		auth.setStatus(0);
		
		//上传营业执照
		String fileName=System.currentTimeMillis()+license.getOriginalFilename();
		String path=BlueConstants.UPLOAD_PATH+"license-image/";
		File targetFile=new File(path,fileName);
		String url=BlueConstants.UPLOAD_URL+"license-image/"+fileName;
		
		try {
			UploadFileUtils.createChildFolder(targetFile);
		} catch (Exception e) {
			logger.error("创建子文件夹异常",e);
			resultInfo.setCode("-");
			resultInfo.setMessage("创建子文件夹异常");
			return resultInfo;
		}
		
		
		try {
			license.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("上传营业执照异常",e);
			resultInfo.setCode("-");
			resultInfo.setMessage("上传营业执照异常");
			return resultInfo;
		}
		
		auth.setLicense(url);
		
		
		try {
			Object object=userService.enterpriseAuth(auth);
			return object;
		} catch (Exception e) {
			logger.error("添加异常",e);
			resultInfo.setCode("-");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 获取认证审核的列表（后台）
	 * @param request
	 * @return
	 */
	@PostMapping("/user/getAuthListBack")
	@ApiOperation(value="获取认证审核的列表（后台）",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="Interger", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="Interger", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "company", dataType="String", required = false, value = "公司名称,可选", defaultValue = "泰闻信息科技有限公司"),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = false, value = "用户账号", defaultValue = "18796327106"),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = true, value = "审核状态 1 通过 0 未通过", defaultValue = "1"),
	})
	public Object getAuthListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String mobile=request.getParameter("mobile");  //用户账号
		String company=request.getParameter("company"); //公司名称
		String status=request.getParameter("status");  //审核状态 1 通过 0 未通过
		
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
		if (!StringUtils.isEmpty(company)) {
			params.put("company", company);
		}
		
		
		if (!StringUtils.isEmpty(mobile)) {
			params.put("mobile", mobile);
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		pagingTool.setParams(params);
		
		try {
			Object object=userService.getAuthListBack(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取认证列表异常",e);
			resultInfo.setCode("-");
			resultInfo.setMessage("获取认证列表异常");
			return resultInfo;
		}
		
	}
	
	
	/**
	 * 根据Id获取审核详情（后台）
	 * @param request
	 * @return
	 */
	@PostMapping("/user/getAuthById")
	@ApiOperation(value="根据Id获取审核详情（后台）",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "authId", dataType="int", required = true, value = "认证Id", defaultValue = "1"),
	})
	public Object getAuthById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String authId=request.getParameter("authId");  //认证Id
		
		if (StringUtils.isEmpty(authId)) {
			resultInfo.setCode("-");
			resultInfo.setMessage("认证Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=userService.getAuthById(Integer.parseInt(authId));
			return object;
		} catch (Exception e) {
			logger.error("获取认证详情异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取认证详情异常");
			return resultInfo;
		}
	}

	
	/**
	 * 认证批量审核（后台）
	 * @param request
	 * @return
	 */
	@PostMapping("/user/authExamin")
	@ApiOperation(value="认证批量审核（后台）",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = true, value = "审核状态 1 通过 0 未通过", defaultValue = "1"),
	})
	public Object authExamin(HttpServletRequest request,String[] authIds){
		ResultInfo resultInfo=new ResultInfo();
		String status=request.getParameter("status");  //审核状态 1 通过 0 未通过
		
		if (authIds==null||authIds.length==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("认证Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核状态不能为空");
			return resultInfo;
		}
		
		
		try {
			Object object=userService.authExaminPass(authIds,Integer.parseInt(status));
			return object;
		} catch (Exception e) {
			logger.error("认证审核通过异常");
			resultInfo.setCode("-1");
			resultInfo.setMessage("认证审核通过异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 导出用户信息到excel中 传入的参数为：　userIds : 用户ID的数组
	 */
	@RequestMapping("/user/exportExcel")
	@ApiOperation(value="导出用户信息到excel（后台）",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "authIds", dataType="int", required = true, value = "认证Id", defaultValue = "1"),
	})
	public byte[] exportExcel(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		String filename = "用户表.xls"; // 表格的名称
		String[] userIds = request.getParameterValues("userIds"); // 获取用户Id
		if (userIds == null || userIds.length == 0) {
			return "100001,用户ID不能为空".getBytes();
		}

		List<User> users = null;

		try {
			users = userService.getUserBatchByIds(userIds);
		} catch (Exception e) {
			return "100002,获取用户信息异常".getBytes();
		}
		
		try {
			// 转换编码格式为iso-8859-1
			filename = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return "100003,编码转换异常".getBytes();
		}
		// 设置响应头 contentType,这里是下载excel
		response.setContentType("application/x-xls");
		// 设置响应头Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ filename + "\"");
		String[] titles = { "用户ID", "用户手机", "用户名称",  "注册日期", "用户类型","积分"};
		try {
			return createExcel(users, titles);
		} catch (IOException e) {
			return "100006,文件下载失败.....".getBytes();
		}
	}

	/**
	 * 创建excel表格
	 * 
	 * @param lists
	 *            用户的对象的List
	 * @param titles
	 *            表头
	 * @return
	 * @throws IOException
	 */
	public byte[] createExcel(List<User> lists, String[] titles)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个工作簿

		HSSFSheet sheet = workbook.createSheet("会员表"); // 在工作簿中创建一个工作表

		HSSFRow row = sheet.createRow(0); // 创建行 行号从0开始 第一行
		// 创建表头
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i); // 在行中创建单元格，从0开始，一行中包括多个单元格
			cell.setCellValue(titles[i]);
		}

		int count = 1; // 标记行数
		for (User user : lists) {
			HSSFRow rowCount = sheet.createRow(count); // 创建行 行号从0开始 第一行

			HSSFCell cell_0 = rowCount.createCell(0);
			cell_0.setCellValue(user.getUserId());
			
			HSSFCell cell_1 = rowCount.createCell(1);
			cell_1.setCellValue(user.getMobile());

			HSSFCell cell_2 = rowCount.createCell(2);
			cell_2.setCellValue(user.getNickName());

			HSSFCell cell_4 = rowCount.createCell(3);
			cell_4.setCellValue(DateUtil.getDateForFormat(user.getCreatedDate()));

			HSSFCell cell_5 = rowCount.createCell(4);
			if (user.getUserType() == 1) {
				cell_5.setCellValue("普通会员");
			} else if (user.getUserType() == 2) {
				cell_5.setCellValue("企业用户");
			}else if (user.getUserType() == 3) {
				cell_5.setCellValue("Vip用户");
			}
			
			HSSFCell cell_6 = rowCount.createCell(5);
			cell_6.setCellValue(user.getPoint());
			count++;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream); // 写入ByteOutputStream流中
		outputStream.close();
		return outputStream.toByteArray();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
