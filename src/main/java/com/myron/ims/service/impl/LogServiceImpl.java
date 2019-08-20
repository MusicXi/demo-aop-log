package com.myron.ims.service.impl;

import org.springframework.stereotype.Service;

import com.myron.ims.bean.Log;
import com.myron.ims.service.LogService;

@Service("logService")
public class LogServiceImpl  implements LogService{
	

//	@Autowired
//	private LogDao logDao;
	
	@Override
	public int createLog(Log log) {
		//return this.logDao.insertSelective(log);
		System.out.println("模拟日志入库"+log);
		return 1;
	}
	
	@Override
	public int updateLog(Log log) {
		//return this.logDao.updateByPrimaryKeySelective(log);
		System.out.println("模拟日志更新"+log);
		return 1;
	}


	





}
