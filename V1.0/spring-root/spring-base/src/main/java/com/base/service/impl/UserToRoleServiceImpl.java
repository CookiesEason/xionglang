package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.UserToRoleDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.UserToRole;
import com.base.service.UserToRoleService;

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
	public void delete(UserToRole entity, Object... params) {
		userToRoleDao.delete(entity.getId());
	}
}
