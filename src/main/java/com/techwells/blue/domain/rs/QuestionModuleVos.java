package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.Question;

/**
 * 问题和模块的值对象
 * @author 陈加兵 	
 * @since 2018年11月30日 下午7:38:36
 */
public class QuestionModuleVos extends Question {
	private String moduleName;  //模块名称

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
}
