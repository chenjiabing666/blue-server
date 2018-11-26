package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Module;
import com.techwells.blue.util.PagingTool;

/**
 * 模块的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface ModuleService {
	
	/**
	 * 添加模块
	 * @param module
	 * @return
	 * @throws Exception
	 */
	Object addModule(Module module)throws Exception;
	
	/**
	 * 根据模块Id获取信息
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	Object getModuleById(Integer moduleId)throws Exception;
	
	
	/**
	 * 修改模块
	 * @param module
	 * @return
	 * @throws Exception
	 */
	Object modifyModuleReturnObject(Module module)throws Exception;
	
	
	/**
	 * 修改模块
	 * @param module
	 * @return
	 * @throws Exception
	 */
	int modifyModuleReturnCount(Module module)throws Exception;
	
	
	/**
	 * 根据模块Id删除数据
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	Object deleteModuleReturnObject(Integer moduleId)throws Exception;
	
	/**
	 * 根据模块Id删除数据
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	int deleteModuleReturnCount(Integer moduleId)throws Exception;
	
	
	
	/**
	 * 分页获取模块数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getModuleList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
