package com.techwells.blue.dao;

import com.techwells.blue.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据手机号码获取用户信息
     * @param mobile
     * @return
     */
    User selectUserByMobile(String mobile);
    
    /**
     * 根据邀请码获取用户信息
     * @param invitedCode
     * @return
     */
    User selectUserByInvitedCode(String invitedCode);
    
    
}