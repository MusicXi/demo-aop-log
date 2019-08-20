package com.myron.ims.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myron.ims.annotation.SystemControllerLog;
import com.myron.ims.bean.User;


/**
 * 登入控制器
 * @author lin.r.x
 *
 */
@Api(value = "/", tags = "系统登入接口")
@Controller
@RequestMapping("/")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/** 登入页 */
	public static final String LOGIN_PAGE = "/index.jsp";
	/** 首页 */
	public static final String MAIN_PAGE = "/main.jsp";
	/** 用户session key */
	public static final String KEY_USER = "ims_user";
	
	
	/**
	 * 系统登入
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param verifycode
	 * @param req
	 * @return
	 * @throws WrongVerifyCodeException 
	 */
	@ApiOperation(value = "登入系统", notes = "登入系统", httpMethod = "POST")
	@SystemControllerLog(description="登入系统")
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model,User user, Boolean rememberMe, String verifycode) throws Exception{		
		//TODO 用户密码校验逻辑省略...
		user.setId("0001");
		//TODO 验证码...
		
		//登入成功
		HttpSession session = request.getSession();       
		session.setAttribute(KEY_USER, user);
		logger.info("{} 登入系统成功!", user.getUsername());
		model.addAttribute("user", user);
		return MAIN_PAGE;
	}
	
	/**
	 * 安全退出登入
	 * @return
	 */
	@SystemControllerLog(description="安全退出系统")
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute(KEY_USER);
		if (user != null) {
			//TODO 模拟退出登入,直接清空sessioni
			logger.info("{} 退出系统成功!", user.getUsername());
			session.removeAttribute(KEY_USER);			
		}
		return LOGIN_PAGE;
	}
	
	
	/**
	 * 重置密码
	 * @return
	 */
	@SystemControllerLog(description="用户重置密码")
	@RequestMapping("resetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(HttpServletRequest request, @RequestBody User user1){
		Map<String, Object> result = new HashMap<>();
		//业务逻辑省略
		User user = this.getCurrentUser(request);
		user.setPassword("abc123");
		logger.info("{} 重置密码为  {}", user.getUsername(), user.getPassword());
		result.put("success", true);
		result.put("msg", "重置密码成功");
		return result;
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@SystemControllerLog(description="Xxx操作")
	@RequestMapping("testException")
	@ResponseBody
	public Map<String, Object> testException(HttpServletRequest request) throws Exception{
		Map<String, Object> result = new HashMap<>();
		
		//模拟业务逻辑出现异常
		try {
			int number = 1 / 0;
			System.out.println(number);
		} catch (Exception e) {
			logger.error("xxx 出现错误", e.getMessage());
			throw e;
		}
		result.put("msg", "xxx-操作成功");
		return result;
	}
	
	/**
	 * 获取当前用户
	 * @param request
	 * @return
	 */
	private User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute(KEY_USER);
		return user;
	}
	

	
	
}
