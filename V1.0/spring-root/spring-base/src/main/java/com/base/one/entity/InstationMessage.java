/**
 * 
 */
package com.base.one.entity;

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
 * @Time 2017年8月17日
 * @此类的作用：站内信
 */
@Entity
@Table(name = "base_instation_message")
public class InstationMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "InstationMessage", table = "ID_SEQUENCE", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.InstationMessage", allocationSize = 100, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "InstationMessage")
	private Integer id;
	private String title;// 标题
	private String count;// 内容
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
