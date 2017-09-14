package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.UserDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.User;
import com.base.service.UserService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void setGenericDao() {
		super.genericDao = userDao;
	}

	@Override
	public void delete(User entity,Object... params) {
		userDao.delete(entity.getId());
	}
}
