package com.techwells.demo;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class RabbitMqTest {
	
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	@Resource
	private RabbitAdmin rabbitAdmin;
	
	
	
	
	@Test
	public void test1(){
		Map<String, Object> map=new HashedMap<String, Object>();
		map.put("name", "陈加兵");
		rabbitTemplate.convertAndSend("amq.direct", "message_1", map);
	}
	
	
	@Test
	public void test2(){
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
