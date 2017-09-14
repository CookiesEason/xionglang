package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SysDataDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.SysData;
import com.base.service.SysDataService;

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
	public void delete(SysData entity, Object... params) {
		sysDataDao.delete(entity.getId());
	}

}
