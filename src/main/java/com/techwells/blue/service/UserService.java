package com.techwells.blue.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.EnterpriseAuth;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.Warm;
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
	
	/**
	 * 修改用户信息，返回1或者0
	 * @param user
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 修改密码
	 * @param userId  用户Id
	 * @param oldPwd  旧密码
	 * @param newPwd  新密码
	 * @return
	 * @throws Exception
	 */
	Object modifyPwd(Integer userId, String oldPwd, String newPwd)throws Exception;

	/**
	 * 企业认证
	 * @param auth
	 * @return
	 * @throws Exception
	 */
	Object enterpriseAuth(EnterpriseAuth auth)throws Exception;

	/**
	 * 获取认证列表
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAuthListBack(PagingTool pagingTool)throws Exception;

	/**
	 * 根据Id获取认证详情
	 * @param authId 认证审核Id
	 * @return
	 * @throws Exception
	 */
	Object getAuthById(Integer authId)throws Exception;

	/**
	 * 认证审核通过
	 * @param authIds  认证Id数组
	 * @param status 审核状态 1 通过 0 不通过
	 * @return
	 * @throws Exception
	 */
	Object authExaminPass(String[] authIds,Integer status)throws Exception;

	/**
	 * 绑定邮箱
	 * @param userId
	 * @param email
	 * @param code
	 * @return
	 * @throws Exception
	 */
	Object checkEmail(Integer userId, String email, String code)throws Exception;

	/**
	 * 根据Id数组批量获取用户信息
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	List<User> getUserBatchByIds(String[] userIds)throws Exception;

	/**
	 * 提交热身问题的答案
	 * @param warm
	 * @return
	 * @throws Exception
	 */
	Object addWarm(Warm warm)throws Exception;
	
	
	
	
	
	
	
	
	
}
