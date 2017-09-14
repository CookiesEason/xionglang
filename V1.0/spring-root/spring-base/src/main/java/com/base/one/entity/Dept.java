package com.base.one.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Entity
@Table(name = "base_dept")
public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "Dept", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.Dept", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Dept")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "CompanyId")
	private Company company;// 所属公司

	private String deptName;// 部门名称
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "deptId")
	private Dept dept;// 上级部门
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "userId")
	private User user; // 部门负责人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}