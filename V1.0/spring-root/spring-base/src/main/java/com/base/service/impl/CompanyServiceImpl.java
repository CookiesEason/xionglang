package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.CompanyDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.Company;
import com.base.service.CompanyService;

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
	public void delete(Company entity, Object... params) {
		companyDao.delete(entity.getId());
	}

}
