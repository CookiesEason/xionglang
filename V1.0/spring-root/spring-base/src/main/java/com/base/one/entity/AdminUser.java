package com.base.one.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.base.enums.StatusType;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Entity
@Table(name = "base_adminuser")
public class AdminUser implements Serializable {
	private static final long serialVersionUID = 1L;
	//id设置为Integer类型是为了防止在添加对象是生出的对象主键默认值为0和主键自增之间的冲突
	@Id
	@TableGenerator(name = "AdminUser", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.AdminUser", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdminUser")
	private Integer id;

	@Column(unique = true, nullable = false, length = 45)
	private String userName;

	private String realName;

	@Column(nullable = false, length = 45)
	private String passWord;

	@Column(length = 45)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private StatusType type;//用户状态

	private Timestamp createTime;

	@Column(length = 45)
	private String mobilePhone;

	@Column(length = 1)
	private String isSuperAdmin;// 是否超级管理员 1是 其他不是
	
	@OneToOne
	@JoinColumn(name = "defaultRoleId")
	private Role role;// 默认使用的用户角色
	
	@OneToOne
	@JoinColumn(name = "deptId")
	private Dept dept;// 所属部门

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_users_roles", inverseJoinColumns = { @JoinColumn(name = "roleId") }, joinColumns = { @JoinColumn(name = "userId") })
	private Set<Role> roles;
	
	@Transient//此属性表中忽略，在修改、添加对象时把多的一边ID放入数组中
	private int[] rolesId;

	public int[] getRolesId() {
		return rolesId;
	}

	public void setRolesId(int[] rolesId) {
		this.rolesId = rolesId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public int[] roleIds() {
		Set<Role> roles = getRoles();
		int[] ids = new int[roles.size()];
		int count = 0;
		for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
			Role r = it.next();
			ids[count++] = r.getId();
		}
		return ids;
	}

	@OneToOne
		@JoinColumn(name = "sid")
		private SysTidData systid;// 文件系统来源
}
