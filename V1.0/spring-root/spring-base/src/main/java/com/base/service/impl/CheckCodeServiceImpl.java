/**
 * 
 */
package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.CheckCodeDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.CheckCode;
import com.base.service.CheckCodeService;

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
	public void delete(CheckCode entity, Object... params) {
		checkCodeDao.delete(entity.getId());
	}
}
