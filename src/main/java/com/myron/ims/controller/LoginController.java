package com.myron.ims.controller;

import com.myron.ims.annotation.SystemControllerLog;
import com.myron.ims.bean.User;
import com.myron.ims.configuration.AppProperties;
import com.myron.ims.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * 登入控制器
 * @author lin.r.x
 *
 */
@Api(value = "/", tags = "系统登入接口")
@Controller
@RequestMapping("/")
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/** 用户session key */
	public static final String KEY_USER = "ims_user";
	@Autowired
	private UserService userService;
	@Autowired
	private AppProperties appProperties;
	
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(KEY_USER);
//        String apiPrefix = "/log/"; //k8s配合ingres转发地址，如果非k8s使用“/”即可
		String apiPrefix = appProperties.getApiPrefix();
        model.addAttribute("user", user);
        model.addAttribute("apiUrl", apiPrefix);
		Map<String, Object> front = new HashMap<>();
		// 前端资源版本--在<script><link>中使用变量
/*		front.put("iview_css", "http://unpkg.com/iview/dist/styles/iview.css");
		front.put("iview_js", "http://unpkg.com/iview/dist/iview.min.js");
		front.put("vue_js", "https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js");
		front.put("axios_js", "https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js");*/
		front.put("iview_css", apiPrefix + "dist/iview/style/iview.css");
		front.put("iview_js", apiPrefix + "dist/iview/iview.min.js");
		front.put("vue_js", apiPrefix + "dist/vue/vue.js");
		front.put("axios_js", apiPrefix + "dist/axios/axios.min.js");
		model.addAttribute("front", front);
	    return "main";
    }
	/**
	 * 系统登入
	 */
	@ApiOperation(value = "登入系统", notes = "登入系统", httpMethod = "POST")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//TODO 用户密码校验逻辑省略...
		user.setUserId("NO" + System.currentTimeMillis());
		this.userService.createUser(user);
		LOGGER.info("【系统】---生成测试用户[{}]", user.getUsername());
		//登入成功
		HttpSession session = request.getSession();       
		session.setAttribute(KEY_USER, user);
		LOGGER.info("【系统】---用户[{}]登入成功!", user.getUsername());
        Map<String, Object> result = new HashMap<>();
		result.put("code","success");
		result.put("msg", "login success");
		result.put("success", true);
		result.put("data", user);
		return result;
	}
	
	/**
	 * 安全退出登入
	 * @return
	 */
	@ApiOperation(value = "安全退出系统", notes = "登入系统", httpMethod = "POST")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute(KEY_USER);
		if (user != null) {
			//TODO 模拟退出登入,直接清空session
			session.removeAttribute(KEY_USER);
			LOGGER.info("用户[{}]退出系统成功!", user.getUsername());
		}
        Map<String, Object> result = new HashMap<>();
        result.put("code","success");
        result.put("msg", "logout success");
        result.put("success", true);
        return result;
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
		LOGGER.info("{} 重置密码为  {}", user.getUsername(), user.getPassword());
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
			LOGGER.error("xxx 出现错误", e.getMessage());
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
