package com.base.one.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月4日
 * @此类作用初始化加载的数据
 */
@Entity
@Table(name = "base_sysdata")
public class SysData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "SysData", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.SysData", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SysData")
	private Integer id;

	private String project;// 属于那个项目

	private String type;// 属于哪里

	private String value;// 值

	private int intData;// 参数为int

	private Double doubleData;// 参数为double

	private String paraOne;// 字符串参数1大小64

	private String paraTwo;// 字符串参数2大小128

	private String paraThree;// 字符串参数3大小256

	private String paraFour;// 字符串参数4大小512

	private int orderForData;// 0不排序，1升序排序，2降序排序

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIntData() {
		return intData;
	}

	public void setIntData(int intData) {
		this.intData = intData;
	}

	public Double getDoubleData() {
		return doubleData;
	}

	public void setDoubleData(Double doubleData) {
		this.doubleData = doubleData;
	}

	public String getParaOne() {
		return paraOne;
	}

	public void setParaOne(String paraOne) {
		this.paraOne = paraOne;
	}

	public String getParaTwo() {
		return paraTwo;
	}

	public void setParaTwo(String paraTwo) {
		this.paraTwo = paraTwo;
	}

	public String getParaThree() {
		return paraThree;
	}

	public void setParaThree(String paraThree) {
		this.paraThree = paraThree;
	}

	public String getParaFour() {
		return paraFour;
	}

	public void setParaFour(String paraFour) {
		this.paraFour = paraFour;
	}

	public int getOrderForData() {
		return orderForData;
	}

	public void setOrderForData(int orderForData) {
		this.orderForData = orderForData;
	}

}
