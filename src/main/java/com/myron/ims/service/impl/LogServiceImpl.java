package com.myron.ims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myron.ims.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myron.ims.bean.Log;
import com.myron.ims.service.LogService;

import java.util.List;
import java.util.Map;

@Service("logService")
public class LogServiceImpl  implements LogService{

	@Autowired
	private LogMapper logMapper;

	@Override
	public Map<String, Object> createLog(Log log) throws Exception {
		this.logMapper.insert(log);
		return null;
	}

	@Override
	public Map<String, Object> createLog(List<Log> logList) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> updateLog(Log log) throws Exception {
		this.logMapper.updateById(log);
		return null;
	}

	@Override
	public Map<String, Object> deleteLog(Log log) throws Exception {
		return null;
	}

	@Override
	public Log findLogByPrimaryKey(String logId) {
		return null;
	}

	@Override
	public List<Log> findList(Log log) {
		return null;
	}

	@Override
	public List<Map<String, Object>> findMapList(Log log) {
		return null;
	}

	@Override
	public Page<Log> findListByPage(Log log, Page<Log> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.logMapper.selectList(new QueryWrapper<Log>().orderByDesc("operate_date"));
		return page;
	}

	@Override
	public Page<Map<String, Object>> findMapListByPage(Log log, Page<Map<String, Object>> page) {
		return null;
	}
}
