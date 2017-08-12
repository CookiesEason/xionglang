package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.TestDao;
import com.sunxl.base.entity.Test;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.TestService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月27日 @此类作用：
 */
@Service
public class TestServiceImpl extends GenericServiceImpl<Test> implements TestService {
	@Autowired
	private TestDao testDao;

	@Override
	public void setGenericDao() {
		super.genericDao = testDao;
	}

	@Override
	@Transactional
	public Test create(Test entity) {
		return testDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		testDao.delete(id);
	}

	@Override
	@Transactional
	public Test update(Test entity) {
		return testDao.update(entity);
	}

}
