package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.EnterpriseAuth;

/**
 * 用户认证的值对象
 * @author 陈加兵 	
 * @since 2018年11月23日 下午4:57:17
 */
public class AuthUserVos extends EnterpriseAuth {
	private String mobile;  //用户账号

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
