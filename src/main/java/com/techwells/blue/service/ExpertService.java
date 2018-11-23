package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Expert;
import com.techwells.blue.util.PagingTool;

/**
 * 专家的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface ExpertService {
	
	/**
	 * 添加专家
	 * @param expert
	 * @return
	 * @throws Exception
	 */
	Object addExpert(Expert expert)throws Exception;
	
	/**
	 * 根据专家Id获取信息
	 * @param expertId
	 * @return
	 * @throws Exception
	 */
	Object getExpertById(Integer expertId)throws Exception;
	
	
	/**
	 * 修改专家
	 * @param expert
	 * @return
	 * @throws Exception
	 */
	Object modifyExpertReturnObject(Expert expert)throws Exception;
	
	
	/**
	 * 修改专家
	 * @param expert
	 * @return
	 * @throws Exception
	 */
	int modifyExpertReturnCount(Expert expert)throws Exception;
	
	
	/**
	 * 根据专家Id删除数据
	 * @param expertId
	 * @return
	 * @throws Exception
	 */
	Object deleteExpertReturnObject(Integer expertId)throws Exception;
	
	/**
	 * 根据专家Id删除数据
	 * @param expertId
	 * @return
	 * @throws Exception
	 */
	int deleteExpertReturnCount(Integer expertId)throws Exception;
	
	
	
	/**
	 * 分页获取专家数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getExpertList(PagingTool pagingTool)throws Exception;

	/**
	 * 分页获取专家数据（前台）
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getExpertListForeground(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
