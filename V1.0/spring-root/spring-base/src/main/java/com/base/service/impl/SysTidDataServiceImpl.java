package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SysTidDataDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.SysTidData;
import com.base.service.SysTidDataService;

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
	public void delete(SysTidData entity, Object... params) {
		sysTidDataDao.delete(entity.getId());
	}
}
