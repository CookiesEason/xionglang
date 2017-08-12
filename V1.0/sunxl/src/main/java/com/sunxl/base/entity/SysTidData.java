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
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：数据来源
 */
@Entity
@Table(name = "base_systid")
public class SysTidData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "SysTidData", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.SysTidData", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SysTidData")
	private Integer id;
	
	private String name;
	
	private String url;
	
	private Timestamp createTime;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
