package com.techwells.blue.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志的实体类
 * @author 陈加兵 	
 * @since 2018年11月3日 上午10:47:01
 */
public class Log {
	private String name;//姓名
	private String operation;  //操作描述
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
