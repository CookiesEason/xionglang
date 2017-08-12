package com.sunxl.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.sunxl.base.dao.TestDao;
import com.sunxl.base.entity.Test;
import com.sunxl.base.generic.dao.impl.GenericTwoDaoImpl;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月27日
 * @此类作用：
 */
@Repository
public class TestDaoImpl extends GenericTwoDaoImpl<Test> implements TestDao {

}
