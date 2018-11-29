package com.techwells.blue.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Solution;
import com.techwells.blue.util.PagingTool;

/**
 * 解决方案的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface SolutionService {
	
	/**
	 * 添加解决方案
	 * @param solution
	 * @return
	 * @throws Exception
	 */
	Object addSolution(Solution solution)throws Exception;
	
	/**
	 * 根据解决方案Id获取信息
	 * @param solutionId
	 * @return
	 * @throws Exception
	 */
	Object getSolutionById(Integer solutionId)throws Exception;
	
	
	/**
	 * 修改解决方案
	 * @param solution
	 * @return
	 * @throws Exception
	 */
	Object modifySolutionReturnObject(Solution solution)throws Exception;
	
	
	/**
	 * 修改解决方案
	 * @param solution
	 * @return
	 * @throws Exception
	 */
	int modifySolutionReturnCount(Solution solution)throws Exception;
	
	
	/**
	 * 根据解决方案Id删除数据
	 * @param solutionId
	 * @return
	 * @throws Exception
	 */
	Object deleteSolutionReturnObject(Integer solutionId)throws Exception;
	
	/**
	 * 根据解决方案Id删除数据
	 * @param solutionId
	 * @return
	 * @throws Exception
	 */
	int deleteSolutionReturnCount(Integer solutionId)throws Exception;
	
	
	
	/**
	 * 分页获取解决方案数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getSolutionList(PagingTool pagingTool)throws Exception;

	/**
	 * 批量添加解决方案
	 * @param solutionList
	 * @return
	 * @throws Exception
	 */
	Object addSolutionBatch(List<Solution> solutionList)throws Exception;
	
	
	
	
	
}
