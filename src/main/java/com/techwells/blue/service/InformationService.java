package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Information;
import com.techwells.blue.util.PagingTool;

/**
 * 资料库的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface InformationService {
	
	/**
	 * 添加资料库
	 * @param information
	 * @return
	 * @throws Exception
	 */
	Object addInformation(Information information)throws Exception;
	
	/**
	 * 根据资料库Id获取信息
	 * @param informationId
	 * @return
	 * @throws Exception
	 */
	Object getInformationById(Integer informationId)throws Exception;
	
	
	/**
	 * 修改资料库
	 * @param information
	 * @return
	 * @throws Exception
	 */
	Object modifyInformationReturnObject(Information information)throws Exception;
	
	
	/**
	 * 修改资料库
	 * @param information
	 * @return
	 * @throws Exception
	 */
	int modifyInformationReturnCount(Information information)throws Exception;
	
	
	/**
	 * 根据资料库Id删除数据
	 * @param informationId
	 * @return
	 * @throws Exception
	 */
	Object deleteInformationReturnObject(Integer informationId)throws Exception;
	
	/**
	 * 根据资料库Id删除数据
	 * @param informationId
	 * @return
	 * @throws Exception
	 */
	int deleteInformationReturnCount(Integer informationId)throws Exception;
	
	
	
	/**
	 * 分页获取资料库数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getInformationList(PagingTool pagingTool)throws Exception;

	/**
	 * 分页获取资料库数据（后台）
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getInformationListBack(PagingTool pagingTool)throws Exception;

	/**
	 * 分页获取资料库数据(前台)
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getInformationListForeground(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
