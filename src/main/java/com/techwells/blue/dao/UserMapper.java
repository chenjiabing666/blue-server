package com.techwells.blue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.blue.domain.User;
import com.techwells.blue.domain.rs.UserRecommendVos;
import com.techwells.blue.util.PagingTool;

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
    
    /**
     * 根据邮箱查找用户信息
     * @param email  邮箱
     * @return
     */
    User selectUserByEmail(String email);
    
    
    /**
     * 分页获取用户信息
     * @param pagingTool
     * @return
     */
    List<User> selectUserList(PagingTool pagingTool);
    
    int countTotalUserList(PagingTool pagingTool);
    
    /**
     * 获取用户详情，包括推荐人
     * @param userId
     * @return
     */
    UserRecommendVos selectRecommenUserByUserId(Integer userId);
    
    /**
     * 根据数组Id批量获取用户信息
     * @param ids
     * @return
     */
    List<User> selectUserBatchByIds(@Param("ids")String[] ids);
    
    
    
    
}