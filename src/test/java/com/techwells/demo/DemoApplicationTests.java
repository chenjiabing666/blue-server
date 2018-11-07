package com.techwells.demo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techwells.demo.annotation.InsertLog;

@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class DemoApplicationTests {
	
	
	@Test
	public void contextLoads() {
	}
	

}
