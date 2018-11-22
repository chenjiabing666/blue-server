package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Aaa;
import com.techwells.blue.util.PagingTool;

/**
 * 模版的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface AaaService {
	
	/**
	 * 添加模版
	 * @param aaa
	 * @return
	 * @throws Exception
	 */
	Object addAaa(Aaa aaa)throws Exception;
	
	/**
	 * 根据模版Id获取信息
	 * @param aaaId
	 * @return
	 * @throws Exception
	 */
	Object getAaaById(Integer aaaId)throws Exception;
	
	
	/**
	 * 修改模版
	 * @param aaa
	 * @return
	 * @throws Exception
	 */
	Object modifyAaaReturnObject(Aaa aaa)throws Exception;
	
	
	/**
	 * 修改模版
	 * @param aaa
	 * @return
	 * @throws Exception
	 */
	int modifyAaaReturnCount(Aaa aaa)throws Exception;
	
	
	/**
	 * 根据模版Id删除数据
	 * @param aaaId
	 * @return
	 * @throws Exception
	 */
	Object deleteAaaReturnObject(Integer aaaId)throws Exception;
	
	/**
	 * 根据模版Id删除数据
	 * @param aaaId
	 * @return
	 * @throws Exception
	 */
	int deleteAaaReturnCount(Integer aaaId)throws Exception;
	
	
	
	/**
	 * 分页获取模版数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAaaList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
