package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.DeptDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.Dept;
import com.base.service.DeptService;

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
	public void delete(Dept entity, Object... params) {
		deptDao.delete(entity.getId());
	}

}
