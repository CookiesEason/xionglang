package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Entity
@Table(name = "base_checkcode")
public class CheckCode implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@TableGenerator(name = "CheckCode", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.CheckCode", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CheckCode")
	private Integer id;

	private String mobile; // 手机号

	private Timestamp begTime; // 创立时间

	private Timestamp endTime; // 创立时间

	private String checkCode; // 验证码

	private String ip; // ip地址

	private int expireAt; // 验证时效时间(秒)

	private int isUse; // 是否使用

	private Timestamp usingAt; // 使用时间

	private String userName; // 联系人

	private String checkCode_send;

	public String getCheckCode_send() {
		return checkCode_send;
	}

	public void setCheckCode_send(String checkCode_send) {
		this.checkCode_send = checkCode_send;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Timestamp getBegTime() {
		return begTime;
	}

	public void setBegTime(Timestamp begTime) {
		this.begTime = begTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp begTime) {
		this.endTime = new Timestamp(begTime.getTime() + expireAt * 1000);
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(int expireAt) {
		this.expireAt = expireAt;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public Timestamp getUsingAt() {
		return usingAt;
	}

	public void setUsingAt(Timestamp usingAt) {
		this.usingAt = usingAt;
	}

}
