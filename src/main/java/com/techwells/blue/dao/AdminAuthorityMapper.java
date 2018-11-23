package com.techwells.blue.dao;

import com.techwells.blue.domain.AdminAuthority;

public interface AdminAuthorityMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(AdminAuthority record);

    int insertSelective(AdminAuthority record);

    AdminAuthority selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(AdminAuthority record);

    int updateByPrimaryKey(AdminAuthority record);
}