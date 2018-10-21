package com.techwells.demo.service.impl;

import javax.annotation.Resource;

import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.ResubmissionScheduler;

import org.springframework.stereotype.Service;

import com.techwells.demo.dao.UserMapper;
import com.techwells.demo.domain.User;
import com.techwells.demo.service.UserService;
import com.techwells.demo.util.ResultInfo;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserMapper userMapper;

	@Override
	public Object getUserById(Integer userId) {
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

	@Override
	public Object addUser(User user) {
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

	@Override
	public Object modifyUserById(User user) {
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

	@Override
	public Object deleteUserById(Integer userId) {
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
	
		
}
