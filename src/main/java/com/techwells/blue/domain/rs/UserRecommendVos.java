package com.techwells.blue.domain.rs;

import com.techwells.blue.domain.User;

/**
 * 用户和邀请人的值对象
 * @author 陈加兵 	
 * @since 2018年11月23日 下午6:05:55
 */
public class UserRecommendVos extends User {
	private String recommendMobile;  //推荐人的账号

	public String getRecommendMobile() {
		return recommendMobile;
	}

	public void setRecommendMobile(String recommendMobile) {
		this.recommendMobile = recommendMobile;
	}
	
}
