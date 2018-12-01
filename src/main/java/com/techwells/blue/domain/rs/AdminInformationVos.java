package com.techwells.blue.domain.rs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.techwells.blue.domain.Information;

/**
 * 管理员和资料的值对象
 * 
 * @author 陈加兵
 * @since 2018年11月26日 下午1:21:00
 */
@ApiModel(value="资料库的值对象的实体类")
public class AdminInformationVos extends Information {
	private String adminName; // 发布人姓名
	@ApiModelProperty(value = "访问文件或者视频的权限  1 有权 0 无权")
	private Integer power;  //访问文件或者视频的权限  1 有权 0 无权
	
	public String getAdminName() {
		return adminName;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
