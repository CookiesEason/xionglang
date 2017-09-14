package com.zookeeper.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.generic.service.impl.GenericServiceImpl;
import com.zookeeper.dao.ImportDataColmDao;
import com.zookeeper.entity.ImportDataColm;
import com.zookeeper.service.ImportDataColmService;

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
	public void delete(ImportDataColm entity, Object... params) {
		importDataColmDao.delete(entity.getId());
	}
}
