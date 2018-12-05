package com.techwells.blue.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.techwells.blue.util.DoubleUtils;
import com.techwells.blue.util.SendMailUtils;

public class TestEamil {
	
	@Test
	public void test1() throws Exception{
		Map<Integer,Double> map=new HashMap<Integer, Double>();
		System.out.println(map.get(1));
	}
	
	public static String formatDouble4(double d) {
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(d);
	}
}
