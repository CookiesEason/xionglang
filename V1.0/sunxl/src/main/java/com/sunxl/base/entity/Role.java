package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import javax.persistence.Entity;
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
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Entity
@Table(name = "base_roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "Roles", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.Roles", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Roles")
	private Integer id;

	@Version
	private int version;

	private String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_menus_roles", inverseJoinColumns = { @JoinColumn(name = "menuId") }, joinColumns = { @JoinColumn(name = "roleId") })
	private Set<Menu> menus;

	@Transient
	private int[] menusId;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_adminusers_roles", inverseJoinColumns = { @JoinColumn(name = "userId") }, joinColumns = { @JoinColumn(name = "roleId") })
	private Set<AdminUser> adminUsers;
	@Transient
	private int[] adminUsersId;

	private int rank;// 获取当前用户默认角色的级别，只能赋予比当前角色级别低的角色,级别从小到大排列，0级别最高（开发者），1次之(管理员)

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_users_roles", inverseJoinColumns = { @JoinColumn(name = "userId") }, joinColumns = { @JoinColumn(name = "roleId") })
	private Set<User> users;
	@Transient
	private int[] usersId;

	private Timestamp createTime;

	@OneToOne
	@JoinColumn(name = "sid")
	private SysTidData systid;// 所属系统

	private String smrytx;// 角色信息简介

	public SysTidData getSystid() {
		return systid;
	}

	public void setSystid(SysTidData systid) {
		this.systid = systid;
	}

	public String getSmrytx() {
		return smrytx;
	}

	public void setSmrytx(String smrytx) {
		this.smrytx = smrytx;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getRank() {
		return rank;
	}

	public int[] getMenusId() {
		return menusId;
	}

	public void setMenusId(int[] menusId) {
		this.menusId = menusId;
	}

	public int[] getAdminUsersId() {
		return adminUsersId;
	}

	public void setAdminUsersId(int[] adminUsersId) {
		this.adminUsersId = adminUsersId;
	}

	public int[] getUsersId() {
		return usersId;
	}

	public void setUsersId(int[] usersId) {
		this.usersId = usersId;
	}

	public Set<AdminUser> getAdminUsers() {
		return adminUsers;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@JsonIgnore
	public Set<AdminUser> setAdminUsers() {
		return adminUsers;
	}

	public void setAdminUsers(Set<AdminUser> adminUsers) {
		this.adminUsers = adminUsers;
	}

	@JsonIgnore
	public Set<User> setUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * 返回排序后的菜单
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年7月26日 @此方法的作用：
	 */
	public LinkedList<Menu> getLinkedListMenu() {
		LinkedList<Menu> list = null;
		if (menus != null) {
			list = new LinkedList<Menu>(menus);
			Collections.sort(list);
		}
		return list;
	}
}
