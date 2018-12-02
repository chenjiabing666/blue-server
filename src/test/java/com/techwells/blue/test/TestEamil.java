package com.techwells.blue.test;

import java.math.BigDecimal;

import org.junit.Test;

import com.techwells.blue.util.SendMailUtils;

public class TestEamil {
	
	@Test
	public void test1() throws Exception{
		BigDecimal a=new BigDecimal(300);
		BigDecimal b=new BigDecimal(400);
		System.out.println(a.compareTo(b));
	}
}
