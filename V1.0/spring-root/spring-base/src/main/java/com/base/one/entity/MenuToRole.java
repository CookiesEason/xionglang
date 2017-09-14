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

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月17日 @此类作用：角色菜单关联表
 */
@Entity
@Table(name = "base_menus_roles")
public class MenuToRole implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "MenuToRole", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.MenuToRole", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MenuToRole")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "menuId")
	private Menu menu;
	@OneToOne
	@JoinColumn(name = "roleId")
	private Role role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
