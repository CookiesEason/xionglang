package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.SysTidDataDao;
import com.sunxl.base.entity.SysTidData;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.SysTidDataService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
 */
@Service
public class SysTidDataServiceImpl extends GenericServiceImpl<SysTidData> implements SysTidDataService {

	@Autowired
	private SysTidDataDao sysTidDataDao;

	@Override
	public void setGenericDao() {
		super.genericDao = sysTidDataDao;
	}

	@Override
	@Transactional
	public SysTidData create(SysTidData entity) {
		return sysTidDataDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		sysTidDataDao.delete(id);
	}

	@Override
	@Transactional
	public SysTidData update(SysTidData entity) {
		return sysTidDataDao.update(entity);
	}
}
