package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.ModuleMapper;
import com.techwells.blue.domain.Module;
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
	
	@PrintLog  //输出异常信息到日志文件中
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
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getModuleById(Integer moduleId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Module module=moduleMapper.selectByPrimaryKey(moduleId);
		
		if (module==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该模块不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(module);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
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
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int modifyModuleReturnCount(Module module) throws Exception {
		return moduleMapper.updateByPrimaryKeySelective(module);
	}

	@PrintLog  //输出异常信息到日志文件中
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
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int deleteModuleReturnCount(Integer moduleId) throws Exception {
		return moduleMapper.deleteByPrimaryKey(moduleId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getModuleList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
