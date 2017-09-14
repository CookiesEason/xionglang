package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.CompanyDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.Company;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月16日
 * @此类作用：
 */
@Repository
public class CompanyDaoImpl extends GenericOneDaoImpl<Company> implements CompanyDao {

}
