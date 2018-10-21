package com.techwells.demo.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.demo.domain.User;

@Transactional   //事务管理
public interface UserService {
	
	Object getUserById(Integer userId);
	
	Object addUser(User user);
	
	Object modifyUserById(User user);
	
	Object deleteUserById(Integer userId);
}
