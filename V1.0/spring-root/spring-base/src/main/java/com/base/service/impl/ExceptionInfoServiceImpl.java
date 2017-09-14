package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.ExceptionInfoDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.service.ExceptionInfoService;
import com.base.two.entity.ExceptionInfo;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月25日 @此类的作用：
 */
@Service
public class ExceptionInfoServiceImpl extends GenericServiceImpl<ExceptionInfo> implements ExceptionInfoService {
	@Autowired
	private ExceptionInfoDao exceptionInfoDao;

	/**
	 * 
	 * @此方法的作用：
	 */
	@Override
	public void setGenericDao() {
		super.genericDao = exceptionInfoDao;
	}

	/**
	 * @param entity
	 * @param params
	 * @此方法的作用：
	 */
	@Override
	public void delete(ExceptionInfo entity, Object... params) {
		exceptionInfoDao.delete(entity.getId());
	}
}
