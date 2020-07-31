package com.myron.ims.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myron.ims.bean.ApiResult;
import com.myron.ims.bean.User;
import com.myron.ims.exception.BusinessException;
import com.myron.ims.mapper.UserMapper;
import com.myron.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * sys_user 用户信息 业务接口实现
 * @author myron
 * @date 2020/02/27 16:51:38
 */
@Service("userService")
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl  implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ApiResult createUser(User user) throws BusinessException{
		int flag = this.userMapper.insert(user);
		return new ApiResult();
	}
	
	@Override
	public ApiResult createUser(List<User> userList) throws BusinessException{

		return new ApiResult();
	}
	
	@Override
	public ApiResult updateUser(User user) throws BusinessException {
		this.userMapper.updateById(user);
		return new ApiResult();
	}
	
	@Override
	public ApiResult updateUser(List<User> userList) throws BusinessException{

		return new ApiResult();
	}

	@Override
	public ApiResult deleteUser(User user) throws BusinessException {
		this.userMapper.deleteById(user.getUserId());
		return new ApiResult();
	}

	@Override
	public ApiResult deleteUser(List<User> userList) throws BusinessException{
		return new ApiResult();
	}

	@Override
	public User findUserByPrimaryKey(String userId) {
		return this.userMapper.selectById(userId);
	}

	@Override
	public Page<User> findListByPage(User user, Page<User> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.userMapper.selectList(new QueryWrapper<>(user));
		return page;

	}

	@Override
	public List<User> findList(User user){
		return this.userMapper.selectList(new QueryWrapper<>(user));
	}

}
