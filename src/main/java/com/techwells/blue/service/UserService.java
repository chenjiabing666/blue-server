package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.User;
import com.techwells.blue.util.PagingTool;

/**
 * 用户的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface UserService {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Object addUser(User user)throws Exception;
	
	/**
	 * 根据用户Id获取信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Object getUserById(Integer userId)throws Exception;
	
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Object modifyUser(User user)throws Exception;
	
	int modifyUserReturnCount(User user)throws Exception;
	
	
	/**
	 * 根据用户Id删除数据
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Object deleteUser(Integer userId)throws Exception;
	
	/**
	 * 分页获取用户数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getUserList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 根据手机号码获取用户信息
	 * @param mobile 手机号码
	 * @return
	 * @throws Exception
	 */
	User getUserByMobile(String mobile)throws Exception;
	
	
	/**
	 * 根据邀请码获取用户信息
	 * @param invitedCode  邀请码
	 * @return
	 * @throws Exception
	 */
	User getUserByInvitedCode(String invitedCode)throws Exception;
	
	/**
	 * 根据邮箱查询用户信息
	 * @param email  邮箱
	 * @return
	 * @throws Exception
	 */
	User getUserByEmail(String email)throws Exception;

	/**
	 * 解绑手机号码或者邮箱
	 * @param userId  用户Id
	 * @param type  类型 
	 * @return
	 * @throws Exception
	 */
	Object cancelBindAccount(Integer userId,Integer type)throws Exception;
	
	
	
	
	
	
	
	
	
}
