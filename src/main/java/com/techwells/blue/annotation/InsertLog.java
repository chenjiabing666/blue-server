package com.techwells.blue.annotation;

import java.lang.annotation.*;

/**
 * 定义日志的注解，作用在方法上
 * @author 陈加兵 	
 * @since 2018年11月3日 上午11:53:59
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  //作用在方法上面
@Retention(RetentionPolicy.RUNTIME)   //程序运行  
@Documented  
public @interface InsertLog {
	String name() default "";   //用户名
	String operation() default "";  //操作
}
