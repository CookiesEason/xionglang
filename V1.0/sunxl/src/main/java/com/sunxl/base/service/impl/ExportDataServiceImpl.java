package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.ExportDataDao;
import com.sunxl.base.entity.ExportData;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ExportDataService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
 */
@Service
public class ExportDataServiceImpl extends GenericServiceImpl<ExportData> implements ExportDataService {

	@Autowired
	private ExportDataDao exportDataDao;

	@Override
	public void setGenericDao() {
		super.genericDao = exportDataDao;
	}

	@Override
	@Transactional
	public ExportData create(ExportData entity, Object... params) {
		return exportDataDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(ExportData entity, Object... params) {
		exportDataDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public ExportData update(ExportData entity, Object... params) {
		return exportDataDao.update(entity);
	}
}
