package com.base.one.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月17日 @此类作用：用户菜单关联
 */
@Entity
@Table(name = "base_users_roles")
public class UserToRole implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "UserToRole", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.UserToRole", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UserToRole")
	private Integer id;
	@Column(name = "roleId")
	private Role role;

	@Column(name = "userId")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
