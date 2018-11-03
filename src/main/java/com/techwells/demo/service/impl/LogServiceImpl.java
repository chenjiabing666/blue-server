package com.techwells.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.demo.annotation.InsertLog;
import com.techwells.demo.domain.Log;
import com.techwells.demo.service.LogService;
@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Override
	public int addLog(Log log) throws Exception {
		return 1;
	}
	
}
