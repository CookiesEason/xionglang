package com.base.form;

import java.io.Serializable;

import com.base.one.entity.Menu;
import com.base.one.entity.Role;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class ZTreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int pId;

	private String name;

	private String menuPath;

	private boolean open;

	private boolean checked;

	public ZTreeNode(Menu menus) {
		this.id = menus.getId();
		this.pId = menus.getFather() == null ? 0 : menus.getFather().getId();
		this.name = menus.getName();
		this.menuPath = menus.getUrl();
	}

	/*
	 * public ZTreeNode(Menus menus,UrlLoader urlLoader) { this(menus);
	 * if(TextUtils.isText(menus.getUrl())){
	 * this.menuPath=urlLoader.getUrl(menus.getAppName(),
	 * menus.getUrl(),SpringUtils.getRequest()); } }
	 */

	public void setMenus(Menu m) {
		m.setName(this.getName());
		m.setUrl(this.getMenuPath());
	}

	public ZTreeNode(Role role) {
		this.id = role.getId();
		this.name = role.getName();
	}

	public ZTreeNode() {
		super();
	}

	public ZTreeNode(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getText() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
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
		ZTreeNode other = (ZTreeNode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
