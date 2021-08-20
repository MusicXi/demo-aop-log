package com.myron.ims.controller;

import com.github.pagehelper.Page;
import com.myron.ims.bean.ApiResult;
import com.myron.ims.bean.Log;
import com.myron.ims.bean.PageResult;
import com.myron.ims.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * sys_log 日志表api接口
 * @author myron
 * @date 2019/09/03 17:16:31
 */
//@CrossOrigin(origins={"*"})
@Controller
@RequestMapping("log")
public class LogController {

	
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/listByPage", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<PageResult<List<Log>>> listByPage(Log log, Page<Log> page) {
		page = this.logService.findListByPage(log, page);
		return ApiResult.wrapPage(page);
	}
	
	
	
	
	
}
