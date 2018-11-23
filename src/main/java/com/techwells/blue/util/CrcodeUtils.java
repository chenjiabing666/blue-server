package com.techwells.blue.util;

import javax.servlet.http.HttpSession;

/**
 * 验证码的工具类
 * @author 陈加兵 	
 * @since 2018年11月23日 上午11:09:59
 */
public class CrcodeUtils {
	private CrcodeUtils(){}  
	
	/**
	 * 验证码的校验
	 * @param session
	 * @param code
	 * @return
	 */
	public static boolean validate(HttpSession session,String mobile,String code)throws Exception{
		CRCode crCode=(CRCode) session.getAttribute("code");
		if (crCode==null) {  //未发送验证码码
			return false;
		}
		
		if (!crCode.getMobile().equals(mobile)) {
			return false;
		}
		
		if (!crCode.getCrCode().equals(code)) {
			return false;
		}
		
		return true;
	}
	
}
