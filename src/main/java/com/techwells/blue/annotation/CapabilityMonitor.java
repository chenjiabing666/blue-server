package com.techwells.blue.annotation;

import java.lang.annotation.*;

/**
 * 性能监控的注解
 * @author 陈加兵 	
 * @since 2018年11月3日 下午1:08:30
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  //作用在方法上面
@Retention(RetentionPolicy.RUNTIME)   //程序运行  
@Documented
public @interface CapabilityMonitor {
}
