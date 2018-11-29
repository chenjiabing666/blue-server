package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.SolutionMapper;
import com.techwells.blue.domain.Solution;
import com.techwells.blue.service.SolutionService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 解决方案的业务层的实现类
 * @author 陈加兵
 */
@Service
public class SolutionServiceImpl implements SolutionService{
	
	@Resource
	private SolutionMapper solutionMapper;
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addSolution(Solution solution) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=solutionMapper.insertSelective(solution);
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
	public Object getSolutionById(Integer solutionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Solution solution=solutionMapper.selectByPrimaryKey(solutionId);
		
		if (solution==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该解决方案不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(solution);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifySolutionReturnObject(Solution solution) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=solutionMapper.updateByPrimaryKeySelective(solution);
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
	public int modifySolutionReturnCount(Solution solution) throws Exception {
		return solutionMapper.updateByPrimaryKeySelective(solution);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteSolutionReturnObject(Integer solutionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=solutionMapper.deleteByPrimaryKey(solutionId);
		
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
	public int deleteSolutionReturnCount(Integer solutionId) throws Exception {
		return solutionMapper.deleteByPrimaryKey(solutionId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getSolutionList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object addSolutionBatch(List<Solution> solutionList)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		for (Solution solution : solutionList) {
			int count=solutionMapper.insertSelective(solution);
			if (count==0) {
				throw new RuntimeException();   //保证数据一次性传输成功，抛出异常
			}
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	
	
}
