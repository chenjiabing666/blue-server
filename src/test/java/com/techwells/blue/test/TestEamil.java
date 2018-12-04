package com.techwells.blue.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.Test;

import com.techwells.blue.util.DoubleUtils;
import com.techwells.blue.util.SendMailUtils;

public class TestEamil {
	
	@Test
	public void test1() throws Exception{
		System.out.println(DoubleUtils.round(100.0/3, 4));
	}
	
	public static String formatDouble4(double d) {
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(d);
	}
}
