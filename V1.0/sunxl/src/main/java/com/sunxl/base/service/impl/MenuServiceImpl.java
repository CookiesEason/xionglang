package com.sunxl.base.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.MenuToRoleDao;
import com.sunxl.base.dao.MenusDao;
import com.sunxl.base.dao.RoleDao;
import com.sunxl.base.entity.Menu;
import com.sunxl.base.entity.MenuToRole;
import com.sunxl.base.entity.Role;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.interfaces.MethodLog;
import com.sunxl.base.service.MenuService;
import com.sunxl.base.util.Sql;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月7日 @此类作用：
 */
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu> implements MenuService {
	@Autowired
	private MenusDao menusDao;
	@Autowired
	private MenuToRoleDao menuToRoleDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public void setGenericDao() {
		super.genericDao = menusDao;
	}

	@Override
	@Transactional
	public Menu create(Menu entity, Object... params) {
		if (entity.getFather().getId() == -1)
			entity.setFather(null);
		Menu menu = menusDao.create(entity);
		menu.setRolesId(entity.getRolesId());
		createMenuToRole(menu);
		return menusDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Menu entity, Object... params) {
		if (entity.getRoles() != null) {
			Iterator<Role> it = entity.getRoles().iterator();
			while (it.hasNext()) {
				menuToRoleDao.delete(it.next().getId());
			}
		}
		menusDao.delete(entity.getId());
	}

	@Override
	@Transactional
	@MethodLog(remark = "修改菜单需要先查出角色信息，父级菜单，同时修改菜单角色关联表")
	public Menu update(Menu entity, Object... params) {
		Menu menu = menusDao.find(entity.getId());
		menu.setName(entity.getName());
		menu.setUrl(entity.getUrl());
		menu.setFunction(entity.getFunction());
		if (entity.getFather().getId() != -1)
			menu.setFather(entity.getFather());
		else
			menu.setFather(null);
		menu.setRolesId(entity.getRolesId());
		if (menu.getRolesId()[0] != 0)// 没有选择角色不用添加菜单角色信息
			createMenuToRole(menu);
		menu.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return menusDao.update(menu);
	}

	/**
	 * 添加或修改菜单时同时修改角色信息表
	 * 
	 * @param menu
	 * @auto：熊浪
	 * @Time：2017年8月2日 @此方法的作用：先添加，再删除所有不被选中的菜单信息
	 */
	private void createMenuToRole(Menu menu) {
		Map<String, Object> map = null;
		MenuToRole menuToRole = null;
		Sql sql = new Sql();
		sql.add("menuId", menu.getId());
		for (int roleId : menu.getRolesId()) {
			map = new HashMap<String, Object>();
			map.put("menu", menu.getId());
			map.put("role", roleId);
			menuToRole = menuToRoleDao.find(map);
			if (menuToRole == null) {// 没有这条数据就添加，有这条数据就修改
				menuToRole = new MenuToRole();
				menuToRole.setMenu(menu);
				menuToRole.setRole(roleDao.find(roleId));
				menuToRoleDao.create(menuToRole);
			}
			sql.add("roleId", roleId, "NOT IN ");
		}
		menuToRoleDao.deleteByNativeSql(sql);
	}
}
