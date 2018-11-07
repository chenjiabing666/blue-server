package com.techwells.demo.annotation.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.techwells.demo.annotation.PrintLog;


/**
 * PrintLog注解的实现
 * @author 陈加兵 	
 * @since 2018年11月7日 下午5:53:25
 */
@Component
@Aspect
public class PrintLogAnnotationImpl {
	
	/**
	 * 定义切入点，凡是方法体上标注@PrintLog这个注解都会被增强
	 * @param printLog
	 */
	@Pointcut("@annotation(printLog)")
	public void printLog(PrintLog printLog){}
	
	/**
	 * 在程序之后执行并且在出现异常的时候才会执行
	 * @param point  JoinPoint对象，用于获取切入点的信息，比如被增加的类，被增强的方法名称、方法传入的参数等信息
	 * @param printLog  注解的接口信息，可以获取接口的信息
	 * @param message   异常信息
	 */
	@AfterThrowing(value="printLog(printLog)",throwing="message")
	public void excute(JoinPoint point,PrintLog printLog,Throwable message){
		Class targetCls=point.getTarget().getClass();  //获取目标类
		Logger logger = LoggerFactory.getLogger(targetCls);
		logger.error("异常信息：",message);
	}
}
