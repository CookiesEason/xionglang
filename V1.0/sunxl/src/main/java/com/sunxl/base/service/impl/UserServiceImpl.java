package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.UserDao;
import com.sunxl.base.entity.User;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.UserService;

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
	@Transactional
	public User create(User entity) {
		return userDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		userDao.delete(id);
	}

	@Override
	@Transactional
	public User update(User entity) {
		return userDao.update(entity);
	}
}
