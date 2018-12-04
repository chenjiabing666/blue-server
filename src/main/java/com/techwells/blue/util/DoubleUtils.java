package com.techwells.blue.util;

import java.text.DecimalFormat;

/**
 * 操作Double的工具类
 * @author 陈加兵 	
 * @since 2018年12月4日 下午4:43:35
 */
public class DoubleUtils {
	/**
	 * 对double类型的数据四舍五入
	 * @param num  需要保留的小数位数
	 * @param d
	 * @return
	 */
	public static Double round(double d,Integer num) {
		String str="#.";
		for (int i = 0; i < num; i++) {
			str+="0";
		}
		DecimalFormat df = new DecimalFormat(str);
		return Double.parseDouble(df.format(d));
	}
}
