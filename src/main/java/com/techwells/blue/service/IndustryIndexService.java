package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.IndustryIndex;
import com.techwells.blue.util.PagingTool;

/**
 * 行业数据指标的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface IndustryIndexService {
	
	/**
	 * 添加行业数据指标
	 * @param industryIndex
	 * @return
	 * @throws Exception
	 */
	Object addIndustryIndex(IndustryIndex industryIndex)throws Exception;
	
	/**
	 * 根据行业数据指标Id获取信息
	 * @param industryIndexId
	 * @return
	 * @throws Exception
	 */
	Object getIndustryIndexById(Integer industryIndexId)throws Exception;
	
	
	/**
	 * 修改行业数据指标
	 * @param industryIndex
	 * @return
	 * @throws Exception
	 */
	Object modifyIndustryIndexReturnObject(IndustryIndex industryIndex)throws Exception;
	
	
	/**
	 * 修改行业数据指标
	 * @param industryIndex
	 * @return
	 * @throws Exception
	 */
	int modifyIndustryIndexReturnCount(IndustryIndex industryIndex)throws Exception;
	
	
	/**
	 * 根据行业数据指标Id删除数据
	 * @param industryIndexId
	 * @return
	 * @throws Exception
	 */
	Object deleteIndustryIndexReturnObject(Integer industryIndexId)throws Exception;
	
	/**
	 * 根据行业数据指标Id删除数据
	 * @param industryIndexId
	 * @return
	 * @throws Exception
	 */
	int deleteIndustryIndexReturnCount(Integer industryIndexId)throws Exception;
	
	
	
	/**
	 * 分页获取行业数据指标数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getIndustryIndexList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
