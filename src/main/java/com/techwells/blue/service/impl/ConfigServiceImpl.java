package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.ConfigMapper;
import com.techwells.blue.domain.Config;
import com.techwells.blue.service.ConfigService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 系统配置的业务层的实现类
 * @author 陈加兵
 */
@Service
public class ConfigServiceImpl implements ConfigService{
	
	@Resource
	private ConfigMapper configMapper;
	
	
	@Override
	public Object addConfig(Config config) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=configMapper.insertSelective(config);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getConfigById(Integer configId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Config config=configMapper.selectByPrimaryKey(configId);
		
		if (config==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该系统配置不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(config);
		return resultInfo;
	}

	
	@Override
	public Object modifyConfigReturnObject(Config config) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=configMapper.updateByPrimaryKeySelective(config);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyConfigReturnCount(Config config) throws Exception {
		return configMapper.updateByPrimaryKeySelective(config);
	}

	
	@Override
	public Object deleteConfigReturnObject(Integer configId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=configMapper.deleteByPrimaryKey(configId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteConfigReturnCount(Integer configId) throws Exception {
		return configMapper.deleteByPrimaryKey(configId);
	}

	
	@Override
	public Object getConfigList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
