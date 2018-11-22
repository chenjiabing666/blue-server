package com.techwells.blue.annotation.impl;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.techwells.blue.annotation.InsertLog;
import com.techwells.blue.domain.Log;
import com.techwells.blue.service.LogService;

@Component   //注入
@Aspect   //切面
public class InsertLogAnnotation {
	
	@Resource
	private LogService logService;
	
	/**
	 * 1、@pointCut：定义切入点，其中支持多种表达式来匹配切入点，这里的annotation是用来匹配注解的
	 * 2、@annotation的参数必须和这个方法的参数字段相同，因为这里表示的扫描的哪个注解
	 * 3、这个切入点的 意思：只要被`@InsertLog`这个注解标注的都会被扫描到成为切入点
	 * @param log  注解的对象
	 */
	@Pointcut("@annotation(log)")
	public void insertLog(InsertLog log){}
	
	
	/**
	 * `@AfterReturning`:定义后置通知，只有程序执行成功才会调用该注解，用来添加操作的日志
	 * `insertLog(log)`: 这里的log一定要和上面定义切入点(@Pointcut)中的参数字段一样
	 * @param point JoinPoint对象，可以获取一些切面信息，比如调用的类，调用的方法名称
	 * @param log  该注解的对象，可以获取注解中参数的内容
	 * @throws Exception 
	 */
	@AfterReturning("insertLog(log)")
	public void SystemLog(JoinPoint point,InsertLog log) throws Exception{
		//获取注解中的参数内容
		String name=log.name();  //姓名
		String operation=log.operation();  //操作
		
		Log log2=new Log();
		log2.setName(name);
		log2.setOperation(operation);
		logService.addLog(log2);  //添加到日志中
		System.out.println("日志记录成功");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
