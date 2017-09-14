package com.base.two.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.base.enums.LocationType;
import com.base.enums.StatusType;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月30日
 * @此类的作用：新闻信息
 */
public class NewInfomation implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@TableGenerator(name = "NewInfomation", table = "ID_SEQUENCE", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.two.entity.NewInfomation", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "NewInfomation")
	private int id;

	private String title; // 标题

	@Column(length = 10000)
	private String context; // 类容

	private Timestamp createDate; // 创建日期

	private String asbstract; // 概要

	@ManyToOne
	@JoinColumn(name = "rid")
	private Resource resource; // 图片地址

	@Enumerated(EnumType.STRING)
	private LocationType type; // 1首页大图，2首页左边，3首页右边，0不在首页显示

	private String website; // 文章节选自

	private int menuId;// 所属模块

	@Transient
	private String menuName;// 所属模块名

	private int submitNameId;// 默认查后台用户名，没有再查前台用户名
	@Transient
	private String submitName;// 提交用户
	private int adminUserId;
	@Transient
	private String adminUserName;

	@Enumerated(EnumType.STRING)
	private StatusType status;// 文章状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getAsbstract() {
		return asbstract;
	}

	public void setAsbstract(String asbstract) {
		this.asbstract = asbstract;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getSubmitNameId() {
		return submitNameId;
	}

	public void setSubmitNameId(int submitNameId) {
		this.submitNameId = submitNameId;
	}

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}

	public int getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}
}
