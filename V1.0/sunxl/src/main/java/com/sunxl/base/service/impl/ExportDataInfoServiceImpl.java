package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.ExportDataInfoDao;
import com.sunxl.base.entity.ExportDataInfo;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ExportDataInfoService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日
 * @此类作用：
 */
@Service
public class ExportDataInfoServiceImpl extends GenericServiceImpl<ExportDataInfo> implements ExportDataInfoService {

	@Autowired
	private ExportDataInfoDao exportDataInfoDao;

	@Override
	public void setGenericDao() {
		super.genericDao = exportDataInfoDao;
	}

	@Override
	@Transactional
	public ExportDataInfo create(ExportDataInfo entity) {
		return exportDataInfoDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		exportDataInfoDao.delete(id);
	}

	@Override
	@Transactional
	public ExportDataInfo update(ExportDataInfo entity) {
		return exportDataInfoDao.update(entity);
	}
}
