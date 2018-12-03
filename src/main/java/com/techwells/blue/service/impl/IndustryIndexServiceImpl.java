package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.IndustryIndexMapper;
import com.techwells.blue.domain.IndustryIndex;
import com.techwells.blue.service.IndustryIndexService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 行业数据指标的业务层的实现类
 * @author 陈加兵
 */
@Service
public class IndustryIndexServiceImpl implements IndustryIndexService{
	
	@Resource
	private IndustryIndexMapper industryIndexMapper;
	
	
	@Override
	public Object addIndustryIndex(IndustryIndex industryIndex) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryIndexMapper.insertSelective(industryIndex);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getIndustryIndexById(Integer industryIndexId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		IndustryIndex industryIndex=industryIndexMapper.selectByPrimaryKey(industryIndexId);
		
		if (industryIndex==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该行业数据指标不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(industryIndex);
		return resultInfo;
	}

	
	@Override
	public Object modifyIndustryIndexReturnObject(IndustryIndex industryIndex) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryIndexMapper.updateByPrimaryKeySelective(industryIndex);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyIndustryIndexReturnCount(IndustryIndex industryIndex) throws Exception {
		return industryIndexMapper.updateByPrimaryKeySelective(industryIndex);
	}

	
	@Override
	public Object deleteIndustryIndexReturnObject(Integer industryIndexId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryIndexMapper.deleteByPrimaryKey(industryIndexId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteIndustryIndexReturnCount(Integer industryIndexId) throws Exception {
		return industryIndexMapper.deleteByPrimaryKey(industryIndexId);
	}

	
	@Override
	public Object getIndustryIndexList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=industryIndexMapper.countTotalIndustryIndexList(pagingTool);
		if(total==0){
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		List<IndustryIndex> industryIndexs=industryIndexMapper.selectIndustryIndexList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(industryIndexs);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	
}