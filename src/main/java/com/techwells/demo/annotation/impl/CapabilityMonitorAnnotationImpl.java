package com.techwells.demo.annotation.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.techwells.demo.annotation.CapabilityMonitor;

/**
 * 性能监控注解的实现类
 * @author 陈加兵 	
 * @since 2018年11月3日 下午1:09:40
 */
@Aspect    //切面
@Component   //注入
public class CapabilityMonitorAnnotationImpl {
	private Logger logger=LoggerFactory.getLogger(CapabilityMonitorAnnotationImpl.class);  //selfj的日志信息
	
	/**
	 * 定义切入点，只要方法体上有这个注解
	 * @param capabilityMonitor  注解的对象
	 */
	@Pointcut("@annotation(capabilityMonitor)")
	public void capabilityMonitor(CapabilityMonitor capabilityMonitor){}
	
	/**
	 * 环绕通知，在方法执行之前和之后都执行
	 * capabilityMonitor(capabilityMonitor)：这里的参数一定要和切入点（`@Pointcut("@annotation(capabilityMonitor)")`）的参数相同
	 * @param point
	 * @param capabilityMonitor  注解的对象
	 * @throws Throwable 
	 */
	@Around("capabilityMonitor(capabilityMonitor)")
	public void execute(ProceedingJoinPoint point,CapabilityMonitor capabilityMonitor) throws Throwable{
		Long startTime=System.currentTimeMillis();  //开始时间
		Object[] args=point.getArgs(); //获取目标方法执行的参数数组
		Object returnValues=point.proceed(args);   //执行目标方法
		Long endTime=System.currentTimeMillis();  //结束时间
		logger.info("程序执行的时间："+((endTime-startTime)/1000.0));   //输出程序执行的时间，秒位单位
	}
}
