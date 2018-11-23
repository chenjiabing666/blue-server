package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.EnterpriseAuth;
import com.techwells.blue.domain.rs.AuthUserVos;
import com.techwells.blue.util.PagingTool;

public interface EnterpriseAuthMapper {
    int deleteByPrimaryKey(Integer enterpriseAuthId);

    int insert(EnterpriseAuth record);

    int insertSelective(EnterpriseAuth record);

    EnterpriseAuth selectByPrimaryKey(Integer enterpriseAuthId);

    int updateByPrimaryKeySelective(EnterpriseAuth record);

    int updateByPrimaryKey(EnterpriseAuth record);
    
    /**
     * 获取企业认证的列表
     * @param pagingTool
     * @return
     */
    List<AuthUserVos> selectAuthUserVoList(PagingTool pagingTool);
    
    int countTotalAuthUserVoList(PagingTool pagingTool);
    
    
    /**
     * 根据Id获取认证审核的详情
     * @param authId
     * @return
     */
    AuthUserVos selectAuthById(Integer authId);
    
    
}