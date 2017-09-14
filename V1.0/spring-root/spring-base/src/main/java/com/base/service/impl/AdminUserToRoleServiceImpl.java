package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.AdminUserToRoleDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.AdminUserToRole;
import com.base.service.AdminUserToRoleService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
 */
@Service
public class AdminUserToRoleServiceImpl extends GenericServiceImpl<AdminUserToRole> implements AdminUserToRoleService {

	@Autowired
	private AdminUserToRoleDao adminUserToRoleDao;

	@Override
	public void setGenericDao() {
		super.genericDao = adminUserToRoleDao;
	}

	@Override
	public void delete(AdminUserToRole entity, Object... params) {
		adminUserToRoleDao.delete(entity.getId());
	}
}
