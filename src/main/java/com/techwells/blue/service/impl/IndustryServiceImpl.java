package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.IndustryMapper;
import com.techwells.blue.domain.Industry;
import com.techwells.blue.service.IndustryService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 行业的业务层的实现类
 * @author 陈加兵
 */
@Service
public class IndustryServiceImpl implements IndustryService{
	
	@Resource
	private IndustryMapper industryMapper;
	
	
	@Override
	public Object addIndustry(Industry industry) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryMapper.insertSelective(industry);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getIndustryById(Integer industryId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Industry industry=industryMapper.selectByPrimaryKey(industryId);
		
		if (industry==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该行业不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(industry);
		return resultInfo;
	}

	
	@Override
	public Object modifyIndustryReturnObject(Industry industry) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryMapper.updateByPrimaryKeySelective(industry);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyIndustryReturnCount(Industry industry) throws Exception {
		return industryMapper.updateByPrimaryKeySelective(industry);
	}

	
	@Override
	public Object deleteIndustryReturnObject(Integer industryId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=industryMapper.deleteByPrimaryKey(industryId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteIndustryReturnCount(Integer industryId) throws Exception {
		return industryMapper.deleteByPrimaryKey(industryId);
	}

	
	@Override
	public Object getIndustryList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=industryMapper.countTotalIndustryList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		List<Industry> industries=industryMapper.selectIndustryList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(industries);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	
}
