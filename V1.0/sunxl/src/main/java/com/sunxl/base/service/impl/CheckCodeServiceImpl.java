/**
 * 
 */
package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.CheckCodeDao;
import com.sunxl.base.entity.CheckCode;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.CheckCodeService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Service
public class CheckCodeServiceImpl extends GenericServiceImpl<CheckCode> implements CheckCodeService {

	@Autowired
	private CheckCodeDao checkCodeDao;

	@Override
	public void setGenericDao() {
		super.genericDao = checkCodeDao;
	}

	@Override
	@Transactional
	public CheckCode create(CheckCode entity, Object... params) {
		return checkCodeDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(CheckCode entity, Object... params) {
		checkCodeDao.delete(entity.getId());
	}

	@Override
	@Transactional
	public CheckCode update(CheckCode entity, Object... params) {
		return checkCodeDao.update(entity);
	}

}
