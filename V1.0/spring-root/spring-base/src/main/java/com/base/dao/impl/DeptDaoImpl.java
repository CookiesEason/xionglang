package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.DeptDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.Dept;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月16日
 * @此类作用：
 */
@Repository
public class DeptDaoImpl extends GenericOneDaoImpl<Dept> implements DeptDao {

}
