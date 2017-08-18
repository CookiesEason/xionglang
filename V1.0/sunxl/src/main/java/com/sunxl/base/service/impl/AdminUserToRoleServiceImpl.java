package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.AdminUserToRoleDao;
import com.sunxl.base.entity.AdminUserToRole;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.AdminUserToRoleService;

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
	@Transactional
	public AdminUserToRole create(AdminUserToRole entity,Object... params) {
		return adminUserToRoleDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(AdminUserToRole entity,Object... params) {
		adminUserToRoleDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public AdminUserToRole update(AdminUserToRole entity,Object... params) {
		return adminUserToRoleDao.update(entity);
	}

}
