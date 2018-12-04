package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.RadarMapper;
import com.techwells.blue.domain.Radar;
import com.techwells.blue.domain.rs.RadarModuleIndustryVos;
import com.techwells.blue.service.RadarService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 雷达图的业务层的实现类
 * @author 陈加兵
 */
@Service
public class RadarServiceImpl implements RadarService{
	
	@Resource
	private RadarMapper radarMapper;
	
	
	@Override
	public Object addRadar(Radar radar) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=radarMapper.insertSelective(radar);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getRadarById(Integer radarId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Radar radar=radarMapper.selectByPrimaryKey(radarId);
		
		if (radar==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该雷达图不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(radar);
		return resultInfo;
	}

	
	@Override
	public Object modifyRadarReturnObject(Radar radar) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=radarMapper.updateByPrimaryKeySelective(radar);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyRadarReturnCount(Radar radar) throws Exception {
		return radarMapper.updateByPrimaryKeySelective(radar);
	}

	
	@Override
	public Object deleteRadarReturnObject(Integer radarId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=radarMapper.deleteByPrimaryKey(radarId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteRadarReturnCount(Integer radarId) throws Exception {
		return radarMapper.deleteByPrimaryKey(radarId);
	}

	
	@Override
	public Object getRadarList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=radarMapper.countTotalRadarList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<RadarModuleIndustryVos> radarModuleIndustryVos=radarMapper.selectRadarList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(radarModuleIndustryVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	
}