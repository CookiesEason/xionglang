package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.MenuToRoleDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.MenuToRole;
import com.base.service.MenuToRoleService;

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
	public void delete(MenuToRole entity, Object... params) {
		menuToRoleDao.delete(entity.getId());
	}
}
