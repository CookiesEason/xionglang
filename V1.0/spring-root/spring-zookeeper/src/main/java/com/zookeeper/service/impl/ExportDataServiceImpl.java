package com.zookeeper.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.generic.service.impl.GenericServiceImpl;
import com.zookeeper.dao.ExportDataDao;
import com.zookeeper.entity.ExportData;
import com.zookeeper.service.ExportDataService;
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
	public void delete(ExportData entity, Object... params) {
		exportDataDao.delete(entity.getId());
	}
}
