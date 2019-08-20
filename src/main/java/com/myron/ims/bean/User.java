package com.myron.ims.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户类
 * @author lin.r.x
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id; // 工号
	private String username; // 账号
	private String name; // 姓名
	private String password; // 密码
	private String sex; // 性别
	private String email; // 邮箱
	private String phone; // 手机
	private Boolean locked; // 是否被锁定
	private String organizationId;// 部门ID仅用户接受参数
	



	private String loginIp; // 最后登入IP
	private String loginDate; // 最后登入日期
	private String photo; // 头像

	private String oldLoginIp; // 上次登入IP
	private String oldLoginDate;// 上次登入日期
	
	private String createBy;	//创建者ID
	private String updateBy;	//更新者ID
	private Date createDate;	// 创建日期

	private Date updateDate;	// 更新日期

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId; 


	}
	
	

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getOldLoginIp() {
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	public String getOldLoginDate() {
		return oldLoginDate;
	}

	public void setOldLoginDate(String oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}
	
	@Override
	public String toString() {
		return "user:" + id;
	}


}
