package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Compete;
import com.techwells.blue.util.PagingTool;

/**
 *	竞争力的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface CompeteService {
	
	/**
	 * 添加竞争力
	 * @param compete
	 * @return
	 * @throws Exception
	 */
	Object addCompete(Compete compete)throws Exception;
	
	/**
	 * 根据竞争力Id获取信息
	 * @param competeId
	 * @return
	 * @throws Exception
	 */
	Object getCompeteById(Integer competeId)throws Exception;
	
	
	/**
	 * 修改竞争力
	 * @param compete
	 * @return
	 * @throws Exception
	 */
	Object modifyCompeteReturnObject(Compete compete)throws Exception;
	
	
	/**
	 * 修改竞争力
	 * @param compete
	 * @return
	 * @throws Exception
	 */
	int modifyCompeteReturnCount(Compete compete)throws Exception;
	
	
	/**
	 * 根据竞争力Id删除数据
	 * @param competeId
	 * @return
	 * @throws Exception
	 */
	Object deleteCompeteReturnObject(Integer competeId)throws Exception;
	
	/**
	 * 根据竞争力Id删除数据
	 * @param competeId
	 * @return
	 * @throws Exception
	 */
	int deleteCompeteReturnCount(Integer competeId)throws Exception;
	
	
	
	/**
	 * 分页获取竞争力数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getCompeteList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
