package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月17日
 * @此类作用：
 */
@Entity
@Table(name = "id_sequence")
public class IdSequence implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@TableGenerator(name = "IdSequence", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.IdSequence", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "IdSequence")
	private Integer id;
	
	@Column(name = "ID_KEY")
	private String entityKey;// 类名
	
	@Column(name = "VALUE")
	private int entityValue;// 主键值(下次添加使用的值)
	
	private String tableName;// 表名
	
	private String smrytx;//表用途
	
	private Timestamp createTime;//表建立时间
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSmrytx() {
		return smrytx;
	}

	public void setSmrytx(String smrytx) {
		this.smrytx = smrytx;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public int getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(int entityValue) {
		this.entityValue = entityValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
