package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Entity
@Table(name = "base_dynamic")
public class Dynamic implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@TableGenerator(name = "Dynamic", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.Dynamic", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Dynamic")
	private Integer id;

	private Timestamp addTime;

	@Column(length = 255, nullable = false)
	private String content;// 动态内容
	@Column(length = 255, nullable = false)
	private String status;// 状态 如：登陆成功
	@Column(length = 255, nullable = false)
	private String other;// 其他
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
