package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Radar;
import com.techwells.blue.util.PagingTool;

/**
 * 雷达图的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface RadarService {
	
	/**
	 * 添加雷达图
	 * @param radar
	 * @return
	 * @throws Exception
	 */
	Object addRadar(Radar radar)throws Exception;
	
	/**
	 * 根据雷达图Id获取信息
	 * @param radarId
	 * @return
	 * @throws Exception
	 */
	Object getRadarById(Integer radarId)throws Exception;
	
	
	/**
	 * 修改雷达图
	 * @param radar
	 * @return
	 * @throws Exception
	 */
	Object modifyRadarReturnObject(Radar radar)throws Exception;
	
	
	/**
	 * 修改雷达图
	 * @param radar
	 * @return
	 * @throws Exception
	 */
	int modifyRadarReturnCount(Radar radar)throws Exception;
	
	
	/**
	 * 根据雷达图Id删除数据
	 * @param radarId
	 * @return
	 * @throws Exception
	 */
	Object deleteRadarReturnObject(Integer radarId)throws Exception;
	
	/**
	 * 根据雷达图Id删除数据
	 * @param radarId
	 * @return
	 * @throws Exception
	 */
	int deleteRadarReturnCount(Integer radarId)throws Exception;
	
	
	
	/**
	 * 分页获取雷达图数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getRadarList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
