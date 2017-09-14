package com.zookeeper.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.generic.service.impl.GenericServiceImpl;
import com.zookeeper.dao.ImportDataDao;
import com.zookeeper.entity.ImportData;
import com.zookeeper.service.ImportDataService;

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
	public void delete(ImportData entity, Object... params) {
		importDataDao.delete(entity.getId());
	}
}
