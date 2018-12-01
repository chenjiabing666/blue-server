package com.techwells.blue.dao;

import org.apache.ibatis.annotations.Param;

import com.techwells.blue.domain.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userInfoId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userInfoId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    /**
     * 根据资料Id和用户Id查看信息
     * @param userId
     * @param infoId
     * @return
     */
    UserInfo selectUserInfoByUserIdAndInfoId(@Param("userId")Integer userId,@Param("infoId")Integer infoId);
}