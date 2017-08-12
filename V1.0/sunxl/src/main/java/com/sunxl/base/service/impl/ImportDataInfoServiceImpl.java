package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.ImportDataInfoDao;
import com.sunxl.base.entity.ImportDataInfo;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ImportDataInfoService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日
 * @此类作用：
 */
@Service
public class ImportDataInfoServiceImpl extends GenericServiceImpl<ImportDataInfo> implements ImportDataInfoService {

	@Autowired
	private ImportDataInfoDao importDataInfoDao;

	@Override
	public void setGenericDao() {
		super.genericDao = importDataInfoDao;
	}

	@Override
	@Transactional
	public ImportDataInfo create(ImportDataInfo entity) {
		return importDataInfoDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		importDataInfoDao.delete(id);
	}

	@Override
	@Transactional
	public ImportDataInfo update(ImportDataInfo entity) {
		return importDataInfoDao.update(entity);
	}

}
