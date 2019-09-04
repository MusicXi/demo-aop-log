package com.myron.ims.service;

import com.github.pagehelper.Page;
import com.myron.ims.bean.Log;


import java.util.List;
import java.util.Map;

/**
 * sys_log 日志表 业务接口
 * @author myron
 * @date 2019/09/03 17:17:28
 */
public interface LogService {
	/**
	 * 新增sys_log 日志表
	 * @param log sys_log 日志表
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createLog(Log log) throws Exception;
	/**
	 * 批量新增sys_log 日志表
	 * @param logList sys_log 日志表列表
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> createLog(List<Log> logList) throws Exception;
	/**
	 * 修改sys_log 日志表
	 * @param log sys_log 日志表
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> updateLog(Log log) throws Exception;

	/**
	 * 删除sys_log 日志表
	 * @param log sys_log 日志表
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> deleteLog(Log log) throws Exception;

	/**
	 * 根据logId查询sys_log 日志表
	 * @param logId sys_log 日志表参数
	 * @return sys_log 日志表对象
	 */
	Log findLogByPrimaryKey(String logId);

	/**
	 * 查询sys_log 日志表-对象列表
	 * @param log sys_log 日志表参数
	 * @return
	 */
	List<Log> findList(Log log);
	/**
	 * 查询sys_log 日志表-map属性列表
	 * @param log sys_log 日志表参数
	 * @return
	 */
	List<Map<String, Object>> findMapList(Log log);
	/**
	 * 分页查询sys_log 日志表-对象列表
	 * @param log sys_log 日志表参数
	 * @param page 分页参数
	 * @return
	 */
	Page<Log> findListByPage(Log log, Page<Log> page);
	/**
	 * 分页查询sys_log 日志表-map属性列表
	 * @param log sys_log 日志表参数
	 * @param page 分页参数
	 * @return
	 */
	Page<Map<String, Object>> findMapListByPage(Log log, Page<Map<String, Object>> page);

}
