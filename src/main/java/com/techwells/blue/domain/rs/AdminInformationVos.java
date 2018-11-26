package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.Information;

/**
 * 管理员和资料的值对象
 * 
 * @author 陈加兵
 * @since 2018年11月26日 下午1:21:00
 */
public class AdminInformationVos extends Information {
	private String adminName; // 发布人姓名

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
