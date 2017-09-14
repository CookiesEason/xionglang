package com.zookeeper.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.generic.service.impl.GenericServiceImpl;
import com.zookeeper.dao.ExportDataInfoDao;
import com.zookeeper.entity.ExportDataInfo;
import com.zookeeper.service.ExportDataInfoService;
/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月27日 @此类作用：
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
	public void delete(ExportDataInfo entity, Object... params) {
		exportDataInfoDao.delete(entity.getId());
	}

}
