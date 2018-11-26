package com.techwells.blue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.blue.domain.Admin;
import com.techwells.blue.util.PagingTool;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
   
    /**
     * 根据账号查询管理员的详细信息
     * @param account  账号             可选
     * @param activated 账号状态  可选  
     * @return
     */
    Admin selectAdminByAccount(@Param("account")String account,@Param("activated")Integer activated);
    
    /**
     * 分页获取数据
     * @param pagingTool
     * @return
     */
    List<Admin> selectAdminBatch(PagingTool pagingTool);
    
    /**
     * 获取管理员的数量
     * @return
     */
    int countTotal(PagingTool pagingTool);
    
    
}