package com.base.one.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：存在外键的修改必须要在service方法中先查出外键
 */
@Entity
@Table(name = "base_menus")
public class Menu implements Serializable, Comparable<Menu> {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "Menus", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.Menus", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Menus")
	private Integer id;

	@Version
	private int version;// 使用了version注解，每次更新需要提交小于或等于以前version的值

	private String name;

	private String url;

	private String function;

	@ManyToOne
	@JoinColumn(name = "fid")
	private Menu father;

	@JsonIgnore
	@OneToMany(mappedBy = "father")
	private Set<Menu> children;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_menus_roles", inverseJoinColumns = { @JoinColumn(name = "roleId") }, joinColumns = { @JoinColumn(name = "menuId") })
	private Set<Role> roles;

	@Transient // 此属性表中忽略，在修改、添加对象时把多的一变ID放入数组中
	private int[] rolesId;

	@Transient
	private int pertainId;

	private int type;// 0前台，1,前台会员,2后台

	private Timestamp createTime;

	public int[] getRolesId() {
		return rolesId;
	}

	public void setRolesId(int[] rolesId) {
		this.rolesId = rolesId;
	}

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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Menu getFather() {
		return father;
	}

	public void setFather(Menu father) {
		this.father = father;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getPertainId() {
		return pertainId;
	}

	public void setPertainId(int pertainId) {
		this.pertainId = pertainId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 比较器根据id排序
	 */
	@Override
	public int compareTo(Menu menu) {
		if (menu == null) {
			return -1;
		}
		if ((getId() != null) && (menu.getId() != null)) {
			if (this.id.intValue() <= menu.getId().intValue())
				return -1;
			return 1;
		}
		if ((getId() == null) && (menu.getId() == null)) {
			return getId().compareTo(menu.getId());
		}
		if ((getId() != null) && (menu.getId() == null)) {
			return -1;
		}
		return 1;
	}
}
