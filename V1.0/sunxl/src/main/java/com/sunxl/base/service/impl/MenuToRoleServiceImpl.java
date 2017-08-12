package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.MenuToRoleDao;
import com.sunxl.base.entity.MenuToRole;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.MenuToRoleService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月17日 @此类作用：
 */
@Service
public class MenuToRoleServiceImpl extends GenericServiceImpl<MenuToRole> implements MenuToRoleService {

	@Autowired
	private MenuToRoleDao menuToRoleDao;

	@Override
	public void setGenericDao() {
		super.genericDao = menuToRoleDao;
	}

	@Override
	@Transactional
	public MenuToRole create(MenuToRole entity) {
		return menuToRoleDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		menuToRoleDao.delete(id);
	}

	@Override
	@Transactional
	public MenuToRole update(MenuToRole entity) {
		return menuToRoleDao.update(entity);
	}

}
