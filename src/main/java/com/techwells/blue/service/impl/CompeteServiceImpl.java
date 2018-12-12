package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.CompeteMapper;
import com.techwells.blue.domain.Compete;
import com.techwells.blue.service.CompeteService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 竞争力的业务层的实现类
 * @author 陈加兵
 */
@Service
public class CompeteServiceImpl implements CompeteService{
	
	@Resource
	private CompeteMapper competeMapper;
	
	
	@Override
	public Object addCompete(Compete compete) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=competeMapper.insertSelective(compete);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getCompeteById(Integer competeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Compete compete=competeMapper.selectByPrimaryKey(competeId);
		
		if (compete==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该竞争力不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(compete);
		return resultInfo;
	}

	
	@Override
	public Object modifyCompeteReturnObject(Compete compete) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=competeMapper.updateByPrimaryKeySelective(compete);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyCompeteReturnCount(Compete compete) throws Exception {
		return competeMapper.updateByPrimaryKeySelective(compete);
	}

	
	@Override
	public Object deleteCompeteReturnObject(Integer competeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=competeMapper.deleteByPrimaryKey(competeId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteCompeteReturnCount(Integer competeId) throws Exception {
		return competeMapper.deleteByPrimaryKey(competeId);
	}

	
	@Override
	public Object getCompeteList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=competeMapper.countTotalCompeteList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		List<Compete> competes=competeMapper.selectCompeteList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(competes);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	
}