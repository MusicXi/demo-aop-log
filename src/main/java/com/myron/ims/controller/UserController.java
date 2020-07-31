package com.myron.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import com.myron.ims.bean.User;
import com.myron.ims.service.UserService;
import com.myron.ims.bean.ApiResult;
import com.myron.ims.bean.PageResult;
import com.myron.ims.exception.BusinessException;


/**
 * sys_user 用户信息api接口
 * @author linrx1
 * @date 2020/02/27 16:50:05
 */
@Api(tags="sys_user 用户信息api接口")
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value="分页查询sys_user 用户信息")
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<PageResult<Page<User>>> listByPage(User user, PageResult pageResult) {
		Page<User> page = this.userService.findListByPage(user, new Page(pageResult.getPageNum(), pageResult.getPageSize()));
		return ApiResult.wrapPage(page);
	}

	@ApiOperation(value="添加sys_user 用户信息")
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult addUser(@RequestBody User user) throws BusinessException {
		return this.userService.createUser(user);
	}

	@ApiOperation(value="修改sys_user 用户信息")
	@RequestMapping(value = "/edit", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult editUser(@RequestBody User user) throws BusinessException {
		return this.userService.updateUser(user);
	}

	@ApiOperation(value="删除sys_user 用户信息")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult deleteUser(@RequestBody User user) throws BusinessException{
		return this.userService.deleteUser(user);
	}

}
