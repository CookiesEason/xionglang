package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.SysDataDao;
import com.sunxl.base.entity.SysData;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.SysDataService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年8月8日 @此类作用：
 */
@Service
public class SysDataServiceImpl extends GenericServiceImpl<SysData> implements SysDataService {

	@Autowired
	private SysDataDao sysDataDao;

	@Override
	public void setGenericDao() {
		super.genericDao = sysDataDao;
	}

	@Override
	@Transactional
	public SysData create(SysData entity, Object... params) {
		return sysDataDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(SysData entity, Object... params) {
		sysDataDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public SysData update(SysData entity, Object... params) {
		return sysDataDao.update(entity);
	}

}
