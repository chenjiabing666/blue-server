package com.techwells.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techwells.demo.annotation.InsertLog;
import com.techwells.demo.dao.UserMapper;
import com.techwells.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class DemoApplicationTests {
	
	@Resource
	private UserMapper userMapper;    //可以直接使用自动注入的方式，不需要使用classpathxmlContext的方式
	@Resource
	private UserService userService;
	
	
	@Test
	public void contextLoads() {
		userService.deleteUserById(11);
	}
	

}
