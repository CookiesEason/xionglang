package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.RoleDao;
import com.sunxl.base.entity.Role;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.RoleService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月16日 @此类作用：
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao rolesDao;

	@Override
	public void setGenericDao() {
		super.genericDao = rolesDao;
	}

	@Override
	@Transactional
	public Role create(Role entity) {
		return rolesDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		rolesDao.delete(id);
	}

	@Override
	@Transactional
	public Role update(Role entity) {
		return rolesDao.update(entity);
	}

}
