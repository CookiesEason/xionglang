package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.IdSequenceDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.IdSequence;
import com.base.service.IdSequenceService;

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
	public void setGenericDao() {
		super.genericDao = idSequenceDao;
	}

	@Override
	public void delete(IdSequence entity, Object... params) {
		idSequenceDao.delete(entity.getId());
	}
}
