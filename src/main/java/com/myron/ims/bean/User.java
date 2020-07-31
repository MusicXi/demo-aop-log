package com.myron.ims.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * sys_user 用户信息
 * @author myron
 * @date 2020/02/27 16:31:50
 */
@Data
@Builder
@AllArgsConstructor
@TableName("sys_user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 用户ID*/
	@ApiModelProperty(value="用户ID")
	@TableId
	private String userId;


	/** 机构ID*/
	@ApiModelProperty(value="机构ID")
	private String organizationId;

	/** 用户名*/
	@ApiModelProperty(value="用户名")
	private String username;

	/** 姓名*/
	@ApiModelProperty(value="姓名")
	private String name;

	/** 密码*/
	@ApiModelProperty(value="密码")
	private String password;

	/** 性别*/
	@ApiModelProperty(value="性别")
	private String sex;

	/** 手机*/
	@ApiModelProperty(value="手机")
	private String phone;

	/** 邮件*/
	@ApiModelProperty(value="邮件")
	private String email;

	/** 创建日期*/
	@ApiModelProperty(value="创建日期")
	private Date createDate;

	/** 创建人ID*/
	@ApiModelProperty(value="创建人ID")
	private String createBy;

	/** 是否被锁定*/
	@ApiModelProperty(value="是否被锁定")
	private Integer locked;

	/** 最后登入IP*/
	@ApiModelProperty(value="最后登入IP")
	private String loginIp;

	/** 最后登入日期*/
	@ApiModelProperty(value="最后登入日期")
	private Date loginDate;

	public User() {
	}
}