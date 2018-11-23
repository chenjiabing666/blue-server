package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Config;
import com.techwells.blue.util.PagingTool;

/**
 * 系统配置的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface ConfigService {
	
	/**
	 * 添加系统配置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	Object addConfig(Config config)throws Exception;
	
	/**
	 * 根据系统配置Id获取信息
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	Object getConfigById(Integer configId)throws Exception;
	
	
	/**
	 * 修改系统配置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	Object modifyConfigReturnObject(Config config)throws Exception;
	
	
	/**
	 * 修改系统配置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	int modifyConfigReturnCount(Config config)throws Exception;
	
	
	/**
	 * 根据系统配置Id删除数据
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	Object deleteConfigReturnObject(Integer configId)throws Exception;
	
	/**
	 * 根据系统配置Id删除数据
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	int deleteConfigReturnCount(Integer configId)throws Exception;
	
	
	
	/**
	 * 分页获取系统配置数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getConfigList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
