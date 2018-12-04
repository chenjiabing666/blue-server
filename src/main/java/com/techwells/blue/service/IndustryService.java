package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Industry;
import com.techwells.blue.util.PagingTool;

/**
 * 行业的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface IndustryService {
	
	/**
	 * 添加行业
	 * @param industry
	 * @return
	 * @throws Exception
	 */
	Object addIndustry(Industry industry)throws Exception;
	
	/**
	 * 根据行业Id获取信息
	 * @param industryId
	 * @return
	 * @throws Exception
	 */
	Object getIndustryById(Integer industryId)throws Exception;
	
	
	/**
	 * 修改行业
	 * @param industry
	 * @return
	 * @throws Exception
	 */
	Object modifyIndustryReturnObject(Industry industry)throws Exception;
	
	
	/**
	 * 修改行业
	 * @param industry
	 * @return
	 * @throws Exception
	 */
	int modifyIndustryReturnCount(Industry industry)throws Exception;
	
	
	/**
	 * 根据行业Id删除数据
	 * @param industryId
	 * @return
	 * @throws Exception
	 */
	Object deleteIndustryReturnObject(Integer industryId)throws Exception;
	
	/**
	 * 根据行业Id删除数据
	 * @param industryId
	 * @return
	 * @throws Exception
	 */
	int deleteIndustryReturnCount(Integer industryId)throws Exception;
	
	
	
	/**
	 * 分页获取行业数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getIndustryList(PagingTool pagingTool)throws Exception;

	/**
	 * 根据行业名称模糊搜索
	 * @param industryName
	 * @return
	 * @throws Exception
	 */
	Object getIndustryByName(String industryName)throws Exception;
	
	
	
	
	
}
