package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.SkillMapper;
import com.techwells.blue.domain.Skill;
import com.techwells.blue.service.SkillService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 技能的业务层的实现类
 * @author 陈加兵
 */
@Service
public class SkillServiceImpl implements SkillService{
	
	@Resource
	private SkillMapper skillMapper;
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addSkill(Skill skill) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=skillMapper.insertSelective(skill);
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
	public Object getSkillById(Integer skillId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Skill skill=skillMapper.selectByPrimaryKey(skillId);
		
		if (skill==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该技能不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(skill);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifySkillReturnObject(Skill skill) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=skillMapper.updateByPrimaryKeySelective(skill);
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
	public int modifySkillReturnCount(Skill skill) throws Exception {
		return skillMapper.updateByPrimaryKeySelective(skill);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteSkillReturnObject(Integer skillId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=skillMapper.deleteByPrimaryKey(skillId);
		
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
	public int deleteSkillReturnCount(Integer skillId) throws Exception {
		return skillMapper.deleteByPrimaryKey(skillId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getSkillList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=skillMapper.countTotalSkillList(pagingTool);
		
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(total);
			resultInfo.setResult(null);
			return resultInfo;
		}
		
		List<Skill> skills=skillMapper.selectSkillList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(total);
		resultInfo.setResult(skills);
		return resultInfo;
	}
}
