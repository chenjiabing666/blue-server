package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Module;
import com.techwells.blue.util.PagingTool;

public interface ModuleMapper {
    int deleteByPrimaryKey(Integer moduleId);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer moduleId);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
    
    /**
     * 分页获取模块列表
     * @param pagingTool
     * @return
     */
    List<Module> selectModuleList(PagingTool pagingTool);
    
    int countTotalModuleList(PagingTool pagingTool);

    /**
     * 根据模块名称获取列表
     * @param moduleName
     * @return
     */
	List<Module> selectModuleListByName(String moduleName);
    
    
}