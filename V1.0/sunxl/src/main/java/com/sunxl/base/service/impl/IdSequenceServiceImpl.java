package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.IdSequenceDao;
import com.sunxl.base.entity.IdSequence;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.IdSequenceService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月21日
 * @此类作用
 */
@Service
public class IdSequenceServiceImpl extends GenericServiceImpl<IdSequence> implements IdSequenceService {
	
	@Autowired
	private IdSequenceDao idSequenceDao;

	@Override
	@Transactional
	public void setGenericDao() {
		super.genericDao = idSequenceDao;
	}

	@Override
	@Transactional
	public IdSequence create(IdSequence entity, Object... params) {
		return idSequenceDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(IdSequence entity, Object... params) {
		idSequenceDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public IdSequence update(IdSequence entity, Object... params) {
		return idSequenceDao.update(entity);
	}

}
