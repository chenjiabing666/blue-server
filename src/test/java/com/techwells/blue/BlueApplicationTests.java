package com.techwells.blue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techwells.blue.annotation.InsertLog;
import com.techwells.blue.dao.SkillMapper;
import com.techwells.blue.domain.Skill;

@RunWith(SpringRunner.class)
@SpringBootTest    //springBoot测试类，可以自定义测试类，不过需要引用这两个注解
public class BlueApplicationTests {
	
	@Resource
	private SkillMapper skillMapper;
	
	@Test
	public void test1(){
		String[] ids={"1","2"};
		List<Skill> skills=skillMapper.selectListByIds(ids);
		System.out.println(skills.size());
	}
	

}
