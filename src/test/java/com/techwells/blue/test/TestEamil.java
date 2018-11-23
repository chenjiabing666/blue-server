package com.techwells.blue.test;

import org.junit.Test;

import com.techwells.blue.util.SendMailUtils;

public class TestEamil {
	
	@Test
	public void test1() throws Exception{
		java.net.URL classUrl = this.getClass().getResource("com.sun.mail.util.TraceInputStream");
		System.out.println(classUrl);
//		String url="http://localhost:8080/blue-server/user/bindEmail?uid=1&email=18796327106@163.com";
//		SendMailUtils.sendTextEmail("蓝色按钮", url, "1655378771@qq.com");
	}
}
