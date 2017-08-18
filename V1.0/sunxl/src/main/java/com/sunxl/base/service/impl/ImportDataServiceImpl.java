package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.ImportDataDao;
import com.sunxl.base.entity.ImportData;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ImportDataService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
 */
@Service
public class ImportDataServiceImpl extends GenericServiceImpl<ImportData> implements ImportDataService {

	@Autowired
	private ImportDataDao importDataDao;

	@Override
	public void setGenericDao() {
		super.genericDao = importDataDao;
	}

	@Override
	@Transactional
	public ImportData create(ImportData entity, Object... params) {
		return importDataDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(ImportData entity, Object... params) {
		importDataDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public ImportData update(ImportData entity, Object... params) {
		return importDataDao.update(entity);
	}

}
