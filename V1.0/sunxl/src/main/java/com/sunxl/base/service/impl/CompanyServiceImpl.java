package com.sunxl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunxl.base.dao.CompanyDao;
import com.sunxl.base.entity.Company;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.CompanyService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月16日 @此类作用：
 */
@Service
public class CompanyServiceImpl extends GenericServiceImpl<Company> implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Override
	public void setGenericDao() {
		super.genericDao = companyDao;
	}

	@Override
	@Transactional
	public Company create(Company entity) {
		return companyDao.create(entity);
	}

	@Override
	@Transactional
	public void delete(Object id) {
		companyDao.delete(id);
	}

	@Override
	@Transactional
	public Company update(Company entity) {
		return companyDao.update(entity);
	}

}
