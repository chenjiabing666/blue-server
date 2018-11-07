package com.techwells.demo.annotation;
import java.lang.annotation.*;

/**
 * 输出日志信息到日志文件的注解
 * @author 陈加兵 	
 * @since 2018年11月7日 下午5:52:55
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  //作用在方法上面
@Retention(RetentionPolicy.RUNTIME)   //程序运行  
@Documented
public @interface PrintLog {
}
