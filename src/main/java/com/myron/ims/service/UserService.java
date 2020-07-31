package com.myron.ims.service;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.Page;
import com.myron.ims.bean.ApiResult;
import com.myron.ims.exception.BusinessException;
import com.myron.ims.bean.User;
/**
 * sys_user 用户信息 业务接口
 * @author myron
 * @date 2020/02/27 16:50:55
 */
public interface UserService {
	/**
	 * 新增sys_user 用户信息
	 * @param user sys_user 用户信息
	 * @return
	 * @throws BusinessException
	 */
	ApiResult createUser(User user) throws BusinessException;
	/**
	 * 批量新增sys_user 用户信息
	 * @param userList sys_user 用户信息列表
	 * @return
	 * @throws BusinessException
	 */
	ApiResult createUser(List<User> userList) throws BusinessException;
	/**
	 * 修改sys_user 用户信息
	 * @param user sys_user 用户信息
	 * @return
	 * @throws BusinessException
	 */
	ApiResult updateUser(User user) throws BusinessException;
	/**
	 * 批量修改sys_user 用户信息
	 * @param userList sys_user 用户信息列表
	 * @return
	 * @throws BusinessException
	 */
	ApiResult updateUser(List<User> userList) throws BusinessException;
	/**
	 * 删除sys_user 用户信息
	 * @param user sys_user 用户信息
	 * @return
	 * @throws BusinessException
	 */
	ApiResult deleteUser(User user) throws BusinessException;
	/**
	 * 批量删除sys_user 用户信息
	 * @param userList sys_user 用户信息列表
	 * @return
	 * @throws BusinessException
	 */
	ApiResult deleteUser(List<User> userList) throws BusinessException;
	/**
	 * 根据userId查询sys_user 用户信息
	 * @param userId sys_user 用户信息参数
	 * @return sys_user 用户信息对象
	 */
	User findUserByPrimaryKey(String userId);

	/**
	 * 查询sys_user 用户信息-对象列表
	 * @param user sys_user 用户信息参数
	 * @return
	 */
	List<User> findList(User user);

	/**
	 * 分页查询sys_user 用户信息-对象列表
	 * @param user sys_user 用户信息参数
	 * @param page 分页参数
	 * @return
	 */
	Page<User> findListByPage(User user, Page<User> page);

}
