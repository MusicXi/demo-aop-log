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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myron.ims.bean.CompareResult;
import com.myron.ims.bean.Log;
import com.myron.ims.bean.User;
import com.myron.ims.mapper.LogMapper;
import com.myron.ims.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * dao日志切面
 * @author linrx
 */
@Component
@Aspect
public class DaoLogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(DaoLogAspect.class);

	@Autowired(required=false)
	private HttpServletRequest request;
	@Autowired(required=false)
	private HttpServletResponse response;
	@Autowired
	private LogMapper logMapper;


	@Around("execution(* com.myron.ims.mapper.*.*(..)) && !execution(* com.myron.ims.mapper.LogMapper.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
		Log log = null;
		try {
			log = this.recordLog(joinPoint);
		} catch (IllegalStateException e) {
			LOGGER.warn("no web request:{}", e.getMessage());
		}

		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable t) {
			LOGGER.info("method: {}, throws: {}", methodName, ExceptionUtils.getStackTrace(t));
			if (log != null) {
				log.setException(t.toString());
			}
			ExceptionUtils.getRootCause(t);
		} finally {
			long endTime = System.currentTimeMillis();
			if (log != null) {
				log.setTimeout(DateUtils.formatDateTime(endTime-startTime));
				this.logMapper.insert(log);
			}

		}
		return result;
	}

	private Log recordLog(ProceedingJoinPoint joinPoint) throws IllegalStateException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("ims_user");
		if(user == null) {
			return null;
		}
		String httpMethod  = request.getMethod();
		String methodName = ((Class)joinPoint.getTarget().getClass().getGenericInterfaces()[0]).getSimpleName() + "." + joinPoint.getSignature().getName();
		// 不记录get日志
		if ( !"GET".equals(httpMethod)) {

			BaseMapper baseMapper = (BaseMapper) joinPoint.getTarget();
			Object[] objects = joinPoint.getArgs();
			Log log = Log.builder()
					.type("dao")
					.status(1)
					.title(methodName)
					.requestId(response.getHeader("requestID"))
					.requestUri(request.getRequestURI())
					.method(request.getMethod())
					.ip(request.getRemoteAddr())
					.params(JSON.toJSONString(objects[0]))
					.loginName(user.getName())
					.operateDate(new Date())
					.build();

			// TODO 后续支持更多方法
			if (methodName.contains("updateById") || methodName.contains("deleteById")) {
				Serializable primaryKey = this.getPrimaryKey(objects[0]);
				Object result = baseMapper.selectById(primaryKey);
				if (methodName.contains("updateById")){
					try {
						List<CompareResult> compareResultList = this.compareTowObject(result, objects[0]);
						LOGGER.info(JSON.toJSONString(compareResultList, true));
						compareResultList.forEach(compareResult -> {
							System.out.println(compareResult.getFieldName() + "【" + compareResult.getFieldComment() + "】值:" + compareResult.getOldValue() + " => " + compareResult.getNewValue());
						});

					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				log.setDataSnapshot(JSON.toJSONString(result));
			}

			//				LOGGER.info("操作日志:{}", jsonObject.toString(SerializerFeature.PrettyFormat));

			return log;
		}
		return null;
	}

	private Serializable getPrimaryKey(Object et) {
		// 反射获取实体类
		Class<?> clazz = et.getClass();
		// 不含有表名的实体就默认通过
		if (!clazz.isAnnotationPresent(TableName.class)) {
			return (Serializable) et;
		}
		// 获取表名
		TableName tableName = clazz.getAnnotation(TableName.class);
		String tbName = tableName.value();
		if (StringUtils.isBlank(tbName)) {
			return null;
		}
		String pkName = null;
		String pkValue = null;
		// 获取实体所有字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 设置些属性是可以访问的
			field.setAccessible(true);
			if (field.isAnnotationPresent(TableId.class)) {
				// 获取主键
				pkName = field.getName();
				try {
					// 获取主键值
					pkValue = field.get(et).toString();
				} catch (Exception e) {
					pkValue = null;
				}

			}
		}
		return pkValue;

	}

	/**
	 * 对比两个对象
	 *
	 * @param oldObj 旧对象
	 * @param newObj 新对象
	 * @return java.util.List<com.erp4cloud.rerp.common.data.log.CompareResult>
	 * @author Tophua
	 * @date 2019/8/5
	 */
	protected List<CompareResult> compareTowObject(Object oldObj, Object newObj) throws IllegalAccessException {
		List<CompareResult> list = new ArrayList<>();
		//获取对象的class
		Class<?> clazz1 = oldObj.getClass();
		Class<?> clazz2 = newObj.getClass();
		//获取对象的属性列表
		Field[] field1 = clazz1.getDeclaredFields();
		Field[] field2 = clazz2.getDeclaredFields();
		//遍历属性列表field1
		for (int i = 0; i < field1.length; i++) {
			//遍历属性列表field2
			for (int j = 0; j < field2.length; j++) {
				//如果field1[i]属性名与field2[j]属性名内容相同
				if (field1[i].getName().equals(field2[j].getName())) {
					field1[i].setAccessible(true);
					field2[j].setAccessible(true);
					if (field2[j].get(newObj) == null) {
						continue;
					}
					//如果field1[i]属性值与field2[j]属性值内容不相同
					if (!compareTwo(field1[i].get(oldObj), field2[j].get(newObj))) {
						CompareResult r = new CompareResult();
						r.setFieldName(field1[i].getName());
						r.setOldValue(field1[i].get(oldObj));
						r.setNewValue(field2[j].get(newObj));
						ApiModelProperty apiModelProperty = field1[i].getAnnotation(ApiModelProperty.class);
						if (apiModelProperty != null) {
							r.setFieldComment(apiModelProperty.value());
						}
						// 匹配字段注释
	/*					Optional o = this.basicInfo.getFieldInfos().stream()
								.filter(f -> r.getFieldName().equals(f.getJFieldName())).findFirst();
						if (o.isPresent()) {
							r.setFieldComment(((FieldInfo) o.get()).getComment());
						}*/
						list.add(r);
					}
					break;
				}
			}
		}
		return list;
	}

	/**
	 * 对比两个数据是否内容相同
	 *
	 * @param object1,object2
	 * @return boolean类型
	 */
	private boolean compareTwo(Object object1, Object object2) {

		if (object1 == null && object2 == null) {
			return true;
		}
		if (object1 == null && object2 != null) {
			return false;
		}
		if (object1.equals(object2)) {
			return true;
		}
		return false;
	}
}