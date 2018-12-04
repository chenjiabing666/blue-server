package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.Radar;

/**
 * 雷达图、模块、行业的值对象
 * @author 陈加兵 	
 * @since 2018年12月4日 上午11:28:05
 */
public class RadarModuleIndustryVos extends Radar {
	private String moduleName;  //模块名称
	private String industryName;  //行业名称
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	
}
