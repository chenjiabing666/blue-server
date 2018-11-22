package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.AaaMapper;
import com.techwells.blue.domain.Aaa;
import com.techwells.blue.service.AaaService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 模版的业务层的实现类
 * @author 陈加兵
 */
@Service
public class AaaServiceImpl implements AaaService{
	
	@Resource
	private AaaMapper aaaMapper;
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addAaa(Aaa aaa) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.insertSelective(aaa);
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
	public Object getAaaById(Integer aaaId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Aaa aaa=aaaMapper.selectByPrimaryKey(aaaId);
		
		if (aaa==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该模版不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(aaa);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyAaaReturnObject(Aaa aaa) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.updateByPrimaryKeySelective(aaa);
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
	public int modifyAaaReturnCount(Aaa aaa) throws Exception {
		return aaaMapper.updateByPrimaryKeySelective(aaa);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteAaaReturnObject(Integer aaaId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.deleteByPrimaryKey(aaaId);
		
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
	public int deleteAaaReturnCount(Integer aaaId) throws Exception {
		return aaaMapper.deleteByPrimaryKey(aaaId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getAaaList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
