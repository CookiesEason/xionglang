package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.RoleDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.Role;
import com.base.service.RoleService;

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
	public void delete(Role entity, Object... params) {
		rolesDao.delete(entity.getId());
	}

}
