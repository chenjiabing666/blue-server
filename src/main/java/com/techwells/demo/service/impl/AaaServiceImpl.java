package com.techwells.demo.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.demo.annotation.PrintLog;
import com.techwells.demo.dao.AaaMapper;
import com.techwells.demo.domain.Aaa;
import com.techwells.demo.service.AaaService;
import com.techwells.demo.util.PagingTool;
import com.techwells.demo.util.ResultInfo;

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
	public Object modifyAaa(Aaa aaa) throws Exception {
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
	public Object deleteAaa(Integer aaaId) throws Exception {
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
	public Object getAaaList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
