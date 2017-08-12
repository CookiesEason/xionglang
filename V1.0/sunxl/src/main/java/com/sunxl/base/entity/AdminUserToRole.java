package com.sunxl.base.entity;

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
@Table(name = "base_adminusers_roles")
public class AdminUserToRole implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "AdminUserToRole", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.AdminUserToRole", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AdminUserToRole")
	private Integer id;
	@Column(name = "roleId")
	private Role role;

	@Column(name = "userId")
	private AdminUser adminUser;

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

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
}
