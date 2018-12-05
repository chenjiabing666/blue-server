package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.ReportMapper;
import com.techwells.blue.domain.Report;
import com.techwells.blue.service.ReportService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 报告的业务层的实现类
 * @author 陈加兵
 */
@Service
public class ReportServiceImpl implements ReportService{
	
	@Resource
	private ReportMapper reportMapper;
	
	
	@Override
	public Object addReport(Report report) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=reportMapper.insertSelective(report);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getReportById(Integer reportId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Report report=reportMapper.selectByPrimaryKey(reportId);
		
		if (report==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该报告不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(report);
		return resultInfo;
	}

	
	@Override
	public Object modifyReportReturnObject(Report report) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=reportMapper.updateByPrimaryKeySelective(report);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyReportReturnCount(Report report) throws Exception {
		return reportMapper.updateByPrimaryKeySelective(report);
	}

	
	@Override
	public Object deleteReportReturnObject(Integer reportId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=reportMapper.deleteByPrimaryKey(reportId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteReportReturnCount(Integer reportId) throws Exception {
		return reportMapper.deleteByPrimaryKey(reportId);
	}

	
	@Override
	public Object getReportList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}