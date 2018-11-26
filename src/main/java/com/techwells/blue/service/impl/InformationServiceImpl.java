package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.configuration2.PropertiesConfiguration.IOFactory;
import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.InformationMapper;
import com.techwells.blue.domain.Information;
import com.techwells.blue.domain.rs.AdminInformationVos;
import com.techwells.blue.service.InformationService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 资料库的业务层的实现类
 * @author 陈加兵
 */
@Service
public class InformationServiceImpl implements InformationService{
	
	@Resource
	private InformationMapper informationMapper;
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addInformation(Information information) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=informationMapper.insertSelective(information);
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
	public Object getInformationById(Integer informationId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		AdminInformationVos information=informationMapper.selectInformationById(informationId);
		
		if (information==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该资料库不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(information);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyInformationReturnObject(Information information) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=informationMapper.updateByPrimaryKeySelective(information);
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
	public int modifyInformationReturnCount(Information information) throws Exception {
		return informationMapper.updateByPrimaryKeySelective(information);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteInformationReturnObject(Integer informationId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=informationMapper.deleteByPrimaryKey(informationId);
		
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
	public int deleteInformationReturnCount(Integer informationId) throws Exception {
		return informationMapper.deleteByPrimaryKey(informationId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getInformationList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getInformationListBack(PagingTool pagingTool)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=informationMapper.countTotalAdminInformationListBack(pagingTool);
		
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(total);
			resultInfo.setResult(null);
			return resultInfo;
		}
		List<AdminInformationVos> adminInformationVos=informationMapper.selectAdminInformationListBack(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(total);
		resultInfo.setResult(adminInformationVos);
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getInformationListForeground(PagingTool pagingTool)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=informationMapper.countTotalAdminInformationListForeground(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		List<AdminInformationVos> informationVos=informationMapper.selectAdminInformationListForeground(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(informationVos);
		resultInfo.setTotal(total);
		return resultInfo;
		
	}
}
