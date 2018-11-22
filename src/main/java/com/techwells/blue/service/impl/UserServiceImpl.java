package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.UserMapper;
import com.techwells.blue.domain.User;
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
		User user=userMapper.selectByPrimaryKey(userId);
		
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
		// TODO Auto-generated method stub
		return null;
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
	
}
