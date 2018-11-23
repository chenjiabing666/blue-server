package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Skill;
import com.techwells.blue.util.PagingTool;

/**
 * 技能的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface SkillService {
	
	/**
	 * 添加技能
	 * @param skill
	 * @return
	 * @throws Exception
	 */
	Object addSkill(Skill skill)throws Exception;
	
	/**
	 * 根据技能Id获取信息
	 * @param skillId
	 * @return
	 * @throws Exception
	 */
	Object getSkillById(Integer skillId)throws Exception;
	
	
	/**
	 * 修改技能
	 * @param skill
	 * @return
	 * @throws Exception
	 */
	Object modifySkillReturnObject(Skill skill)throws Exception;
	
	
	/**
	 * 修改技能
	 * @param skill
	 * @return
	 * @throws Exception
	 */
	int modifySkillReturnCount(Skill skill)throws Exception;
	
	
	/**
	 * 根据技能Id删除数据
	 * @param skillId
	 * @return
	 * @throws Exception
	 */
	Object deleteSkillReturnObject(Integer skillId)throws Exception;
	
	/**
	 * 根据技能Id删除数据
	 * @param skillId
	 * @return
	 * @throws Exception
	 */
	int deleteSkillReturnCount(Integer skillId)throws Exception;
	
	
	
	/**
	 * 分页获取技能数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getSkillList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
