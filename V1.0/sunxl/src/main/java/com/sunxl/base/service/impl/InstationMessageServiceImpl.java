/**
 * 
 */
package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.InstationMessageDao;
import com.sunxl.base.entity.InstationMessage;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.InstationMessageService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日 @此类的作用：
 */
@Service
public class InstationMessageServiceImpl extends GenericServiceImpl<InstationMessage> implements InstationMessageService {

	@Autowired
	private InstationMessageDao instationMessageDao;

	@Override
	public void setGenericDao() {
		super.genericDao = instationMessageDao;
	}

	@Override
	@Transactional
	public InstationMessage create(InstationMessage entity, Object... params) {
		return instationMessageDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(InstationMessage entity, Object... params) {
		instationMessageDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public InstationMessage update(InstationMessage entity, Object... params) {
		return instationMessageDao.update(entity);
	}

}
