package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Information;
import com.techwells.blue.domain.rs.AdminInformationVos;
import com.techwells.blue.util.PagingTool;

public interface InformationMapper {
    int deleteByPrimaryKey(Integer informationId);

    int insert(Information record);

    int insertSelective(Information record);

    Information selectByPrimaryKey(Integer informationId);

    int updateByPrimaryKeySelective(Information record);

    int updateByPrimaryKeyWithBLOBs(Information record);

    int updateByPrimaryKey(Information record);
    
    AdminInformationVos selectInformationById(Integer informationId);
    
    /**
     * 后台获取资料库列表
     * @param pagingTool
     * @return
     */
    List<AdminInformationVos> selectAdminInformationListBack(PagingTool pagingTool);
    
    int countTotalAdminInformationListBack(PagingTool pagingTool);
    
    /**
     * 前台获取资料库列表
     * @param pagingTool
     * @return
     */
    List<AdminInformationVos> selectAdminInformationListForeground(PagingTool pagingTool);
    
    int countTotalAdminInformationListForeground(PagingTool pagingTool);
    
    
    
}