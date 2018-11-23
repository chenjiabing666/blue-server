package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.EnterpriseAuthMapper;
import com.techwells.blue.dao.UserMapper;
import com.techwells.blue.domain.EnterpriseAuth;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.rs.AuthUserVos;
import com.techwells.blue.domain.rs.UserRecommendVos;
import com.techwells.blue.service.UserService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 用户的业务层的实现类
 * @author 陈加兵
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private EnterpriseAuthMapper authMapper;
	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addUser(User user) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=userMapper.insertSelective(user);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getUserById(Integer userId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		UserRecommendVos user=userMapper.selectRecommenUserByUserId(userId);
		
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(user);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyUser(User user) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=userMapper.updateByPrimaryKeySelective(user);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteUser(Integer userId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=userMapper.deleteByPrimaryKey(userId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getUserList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=userMapper.countTotalUserList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		List<User> users=userMapper.selectUserList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(users);
		resultInfo.setTotal(total);
		return resultInfo;
	}
	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public User getUserByMobile(String mobile) throws Exception {
		return userMapper.selectUserByMobile(mobile);
	}

	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public User getUserByInvitedCode(String invitedCode) throws Exception {
		return userMapper.selectUserByInvitedCode(invitedCode);
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int modifyUserReturnCount(User user) throws Exception {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public User getUserByEmail(String email) throws Exception {
		return null;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object cancelBindAccount(Integer userId, Integer type)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		User user=userMapper.selectByPrimaryKey(userId);
		
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户不存在");
			return resultInfo;
		}
		
		if (type==1) { //手机
			user.setMobile(null);
		}else if (type==2) {
			user.setEmail(null);
		}
		
		
		int count=userMapper.updateByPrimaryKey(user);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("解绑失败");
			return resultInfo;
		}
		resultInfo.setMessage("解绑成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyPwd(Integer userId, String oldPwd, String newPwd)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		User user=userMapper.selectByPrimaryKey(userId);
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户不存在");
			return resultInfo;
		}
		
		//用户存在，验证密码
		if (!user.getPassword().equals(oldPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不正确");
			return resultInfo;
		}
		
		//密码正确
		user.setPassword(newPwd);
		int count=userMapper.updateByPrimaryKeySelective(user);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object enterpriseAuth(EnterpriseAuth auth) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=authMapper.insertSelective(auth);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getAuthListBack(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=authMapper.countTotalAuthUserVoList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<AuthUserVos> authUserVos=authMapper.selectAuthUserVoList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(authUserVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}
	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getAuthById(Integer authId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		AuthUserVos authUserVos=authMapper.selectAuthById(authId);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(authUserVos);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object authExaminPass(String[] authIds) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//审核通过
		for (String id : authIds) {
			EnterpriseAuth auth=authMapper.selectByPrimaryKey(Integer.parseInt(id));
			//如果auth为空，不用审核
			if (auth==null) {
				continue;  //直接继续
			}
			auth.setStatus(1);  //审核通过
			int count=authMapper.updateByPrimaryKeySelective(auth);
			if (count==0) {
				throw new RuntimeException();  //直接抛出异常回滚数据
			}
			
			//更新用户的状态为企业用户
			User user=new User();
			user.setUserId(auth.getUserId());
			user.setUserType(2);  //企业用户
			int count1=userMapper.updateByPrimaryKeySelective(user);
			if (count1==0) {
				throw new RuntimeException();  //直接抛出异常回滚数据
			}
		}
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}
	
}
