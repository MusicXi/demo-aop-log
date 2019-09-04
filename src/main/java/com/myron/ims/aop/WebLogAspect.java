/*
 * @(#)DnaLogAspect.java 2018年10月30日
 *
 * Copyright 2000-2018 by ChinaNetCenter Corporation.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ChinaNetCenter Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinaNetCenter.
 */
package com.myron.ims.aop;


import com.alibaba.fastjson.JSON;
import com.myron.ims.bean.Log;
import com.myron.ims.bean.User;
import com.myron.ims.mapper.LogMapper;
import com.myron.ims.util.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * web日志记录
 * @author linrx
 */
@Component
@Aspect
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
    private static final ThreadLocal<Log> logThreadLocal = new NamedThreadLocal<Log>("ThreadLocal log");
    private static final ThreadLocal<User> currentUser=new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required = false)
    private HttpServletRequest request;
    @Autowired(required = false)
    private HttpServletResponse response;
    @Autowired
    private LogMapper logMapper;

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);
        //debug模式下 显式打印开始时间用于调试
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }

        //读取session中的用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("ims_user");
        currentUser.set(user);
        beginTimeThreadLocal.set(new Date());
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        long beginTime = beginTimeThreadLocal.get().getTime();
        long endTime = System.currentTimeMillis();
        User user = this.getCurrentUser();
        if (user == null) {
            return;
        }
        Log log = Log.builder()
                .type("http")
                .status(1)
                .title(getControllerMethodDescription2(joinPoint))
                .requestId(response.getHeader("requestId"))
                .requestUri(request.getRequestURI())
                .method(request.getMethod())
                .ip(request.getRemoteAddr())
                .params(this.buildRequestParams(request.getParameterMap(), joinPoint.getArgs()))
                .loginName(user.getUsername())
                .operateDate(beginTimeThreadLocal.get())
                .timeout(DateUtils.formatDateTime(endTime-beginTime))
                .build();

        String timestamp = request.getHeader("timestamp");
        if (!StringUtils.isEmpty(timestamp)) {
            log.setRequestTimestamp(Long.parseLong(timestamp));
        }
        this.logMapper.insert(log);
        logThreadLocal.set(log);
    }

    /**
     *  异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = logThreadLocal.get();
        if(log != null){
            log.setStatus(0);
            log.setException(e.toString());
            this.logMapper.updateById(log);
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation = method
                .getAnnotation(ApiOperation.class);
        String discription = apiOperation.value();
        return discription;
    }

    private String buildRequestParams(Map<String, String[]> paramMap, Object[] args) {
        StringBuilder params = new StringBuilder();
        // post 请求体json参数
        if (CollectionUtils.isEmpty(paramMap)) {
            for (Object obj : args) {
                if (!(obj instanceof Serializable)) {
                    continue;
                }
                params.append(JSON.toJSONString(obj));
            }
            // get 请求参数
        } else {
            for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
                params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
                String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
                params.append(paramValue);
            }
        }
        return params.toString();
    }

    private User getCurrentUser() {
        User user = currentUser.get();
        //登入login操作 前置通知时用户未校验 所以session中不存在用户信息
        if(user == null){
            HttpSession session = request.getSession();
            user = (User) session.getAttribute("ims_user");
        }
        return user;
    }


}
