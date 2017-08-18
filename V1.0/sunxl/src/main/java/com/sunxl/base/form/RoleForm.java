package com.sunxl.base.form;

import java.util.HashSet;
import java.util.Set;

import com.sunxl.base.dao.MenuTreeDao;
import com.sunxl.base.entity.Menu;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class RoleForm {

	private int id;
	private String name;
	private int[] treeIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getTreeIds() {
		return treeIds;
	}

	public void setTreeIds(int[] treeIds) {
		this.treeIds = treeIds;
	}

	public Set<Menu> getMenus(MenuTreeDao menusDao) {
		Set<Menu> menus = new HashSet<Menu>(this.treeIds.length);

		for (int i : treeIds) {
			menus.add(menusDao.find(i));
		}

		return menus;
	}

}
