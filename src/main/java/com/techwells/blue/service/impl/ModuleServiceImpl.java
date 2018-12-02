package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.IndustryMapper;
import com.techwells.blue.dao.ModuleMapper;
import com.techwells.blue.dao.SolutionMapper;
import com.techwells.blue.domain.Industry;
import com.techwells.blue.domain.Module;
import com.techwells.blue.domain.Solution;
import com.techwells.blue.service.ModuleService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 模块的业务层的实现类
 * @author 陈加兵
 */
@Service
public class ModuleServiceImpl implements ModuleService{
	
	@Resource
	private ModuleMapper moduleMapper;
	
	@Resource
	private SolutionMapper solutionMapper;
	
	@Resource
	private IndustryMapper industryMapper;
	
	
	
	@Override
	public Object addModule(Module module) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=moduleMapper.insertSelective(module);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		resultInfo.setResult(module);  //返回结果
		return resultInfo;
	}

	
	@Override
	public Object getModuleById(Integer moduleId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Module module=moduleMapper.selectByPrimaryKey(moduleId);
		
		if (module==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该模块不存在");
			return resultInfo;
		}
		
		PagingTool pagingTool=new PagingTool(1, 20);
		//获取所有的行业
		List<Industry> industries=industryMapper.selectIndustryList(pagingTool);
		
		//获取解决方案列表（根据行业区分）
		Iterator<Industry> iterator = industries.iterator();
		while(iterator.hasNext()){
			Industry industry=iterator.next();
			List<Solution> solutions=solutionMapper.selectSolutionsByIndustryIdAndModuleId(industry.getIndustryId(), moduleId);
			if (solutions.size()==0) {  //如果数量为0表示该模块并没有添加这个行业的解决方案，需要将其剔除
				iterator.remove();
				continue;
			}
			industry.setSolutions(solutions);
		}
		
		
		
		module.setIndustries(industries);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(module);
		return resultInfo;
	}

	
	@Override
	public Object modifyModuleReturnObject(Module module) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=moduleMapper.updateByPrimaryKeySelective(module);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyModuleReturnCount(Module module) throws Exception {
		return moduleMapper.updateByPrimaryKeySelective(module);
	}

	
	@Override
	public Object deleteModuleReturnObject(Integer moduleId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=moduleMapper.deleteByPrimaryKey(moduleId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteModuleReturnCount(Integer moduleId) throws Exception {
		return moduleMapper.deleteByPrimaryKey(moduleId);
	}

	
	@Override
	public Object getModuleList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=moduleMapper.countTotalModuleList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<Module> modules=moduleMapper.selectModuleList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(modules);
		resultInfo.setTotal(total);
		return resultInfo;
	}
	
	
	@Override
	public Object getModuleByName(String moduleName) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Module> modules=moduleMapper.selectModuleListByName(moduleName);
		resultInfo.setResult(modules);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	
	
	
}
