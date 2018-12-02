package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.Order;

/**
 * 用户和订单的值对象
 * @author 陈加兵 	
 * @since 2018年12月2日 下午1:17:25
 */
public class UserOrderVos extends Order {
	private String mobile; //用户账号

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
