package com.techwells.blue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techwells.blue.annotation.InsertLog;

@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class BlueApplicationTests {
	
	
	@Test
	public void contextLoads() {
	}
	

}
