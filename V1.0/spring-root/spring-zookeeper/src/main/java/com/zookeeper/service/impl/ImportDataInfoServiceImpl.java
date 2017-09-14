package com.zookeeper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.generic.service.impl.GenericServiceImpl;
import com.zookeeper.dao.ImportDataInfoDao;
import com.zookeeper.entity.ImportDataInfo;
import com.zookeeper.service.ImportDataInfoService;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
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
	public void delete(ImportDataInfo id, Object... params) {
		importDataInfoDao.delete(id);
	}

}
