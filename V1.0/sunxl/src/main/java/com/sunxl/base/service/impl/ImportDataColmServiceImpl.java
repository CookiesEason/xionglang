package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.ImportDataColmDao;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ImportDataColmService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
 */
@Service
public class ImportDataColmServiceImpl extends GenericServiceImpl<ImportDataColm> implements ImportDataColmService {

	@Autowired
	private ImportDataColmDao importDataColmDao;

	@Override
	public void setGenericDao() {
		super.genericDao = importDataColmDao;
	}

	@Override
	@Transactional
	public ImportDataColm create(ImportDataColm entity, Object... params) {
		return importDataColmDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(ImportDataColm entity, Object... params) {
		importDataColmDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public ImportDataColm update(ImportDataColm entity, Object... params) {
		return importDataColmDao.update(entity);
	}
}
