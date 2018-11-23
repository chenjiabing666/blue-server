package com.techwells.blue.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.ExpertMapper;
import com.techwells.blue.dao.SkillMapper;
import com.techwells.blue.domain.Expert;
import com.techwells.blue.domain.Skill;
import com.techwells.blue.service.ExpertService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 专家的业务层的实现类
 * @author 陈加兵
 */
@Service
public class ExpertServiceImpl implements ExpertService{
	
	@Resource
	private ExpertMapper expertMapper;
	
	@Resource
	private SkillMapper skillMapper;
	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addExpert(Expert expert) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=expertMapper.insertSelective(expert);
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
	public Object getExpertById(Integer expertId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Expert expert=expertMapper.selectByPrimaryKey(expertId);
		
		if (expert==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该专家不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(expert);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyExpertReturnObject(Expert expert) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=expertMapper.updateByPrimaryKeySelective(expert);
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
	public int modifyExpertReturnCount(Expert expert) throws Exception {
		return expertMapper.updateByPrimaryKeySelective(expert);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteExpertReturnObject(Integer expertId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=expertMapper.deleteByPrimaryKey(expertId);
		
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
	public int deleteExpertReturnCount(Integer expertId) throws Exception {
		return expertMapper.deleteByPrimaryKey(expertId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getExpertList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=expertMapper.countTotalExpertListBack(pagingTool);
		if (total==0) {
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			resultInfo.setMessage("获取成功");
			return resultInfo;
		}
		
		List<Expert> experts=expertMapper.selectExpertListBack(pagingTool);
		resultInfo.setResult(experts);
		resultInfo.setTotal(total);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getExpertListForeground(PagingTool pagingTool)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=expertMapper.countTotalExpertListBack(pagingTool);
		if (total==0) {
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			resultInfo.setMessage("获取成功");
			return resultInfo;
		}
		
		List<Expert> experts=expertMapper.selectExpertListBack(pagingTool);
		
		//循环遍历获取技能
		for (Expert expert : experts) {
			String[] skillIds=expert.getExpertSkill().split(",");  //分离出技能id
			List<Skill> skills=skillMapper.selectListByIds(skillIds);
			expert.setSkillList(skills);
		}
		
		resultInfo.setResult(experts);
		resultInfo.setTotal(total);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	
	
	
}
