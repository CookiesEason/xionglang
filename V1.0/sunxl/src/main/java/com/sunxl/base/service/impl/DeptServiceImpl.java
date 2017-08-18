package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.DeptDao;
import com.sunxl.base.entity.Dept;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.DeptService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月16日 @此类作用：
 */
@Service
public class DeptServiceImpl extends GenericServiceImpl<Dept> implements DeptService {
	
	@Autowired
	private DeptDao deptDao;

	@Override
	public void setGenericDao() {
		super.genericDao = deptDao;
	}

	@Override
	@Transactional
	public Dept create(Dept entity, Object... params) {
		return deptDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Dept entity, Object... params) {
		deptDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public Dept update(Dept entity, Object... params) {
		return deptDao.update(entity);
	}

}
