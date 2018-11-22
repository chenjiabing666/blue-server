package com.techwells.blue.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.annotation.InsertLog;
import com.techwells.blue.domain.Log;
import com.techwells.blue.service.LogService;
@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Override
	public int addLog(Log log) throws Exception {
		return 1;
	}
	
}
