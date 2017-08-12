package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunxl.base.dao.UserToRoleDao;
import com.sunxl.base.entity.UserToRole;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.UserToRoleService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月17日 @此类作用：
 */
@Service
public class UserToRoleServiceImpl extends GenericServiceImpl<UserToRole> implements UserToRoleService {
	@Autowired
	private UserToRoleDao userToRoleDao;

	@Override
	public void setGenericDao() {
		super.genericDao = userToRoleDao;
	}

	@Override
	public UserToRole create(UserToRole entity) {
		return null;
	}

	@Override
	public void delete(Object id) {
	}

	@Override
	public UserToRole update(UserToRole entity) {
		return null;
	}

}
