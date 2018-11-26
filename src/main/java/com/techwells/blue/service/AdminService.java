package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Admin;
import com.techwells.blue.util.PagingTool;

/**
 * 管理员的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface AdminService {
	
	/**
	 * 添加管理员
	 * @param 管理员
	 * @return
	 * @throws Exception
	 */
	Object addAdmin(Admin admin)throws Exception;
	
	/**
	 * 根据管理员Id获取信息
	 * @param 管理员Id
	 * @return
	 * @throws Exception
	 */
	Object getAdminById(Integer adminId)throws Exception;
	
	
	/**
	 * 修改管理员
	 * @param 管理员
	 * @return
	 * @throws Exception
	 */
	Object modifyAdminReturnObject(Admin admin)throws Exception;
	
	
	/**
	 * 修改管理员
	 * @param 管理员
	 * @return
	 * @throws Exception
	 */
	int modifyAdminReturnCount(Admin admin)throws Exception;
	
	
	/**
	 * 根据管理员Id删除数据
	 * @param 管理员Id
	 * @return
	 * @throws Exception
	 */
	Object deleteAdminReturnObject(Integer adminId)throws Exception;
	
	/**
	 * 根据管理员Id删除数据
	 * @param 管理员Id
	 * @return
	 * @throws Exception
	 */
	int deleteAdminReturnCount(Integer adminId)throws Exception;
	
	
	
	/**
	 * 分页获取管理员数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAdminList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 管理员登录
	 * @param account
	 * @param password
	 * @return
	 * @throws Exception
	 */
	Object login(String account, String password) throws Exception;
	
	
	
}
