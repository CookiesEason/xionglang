package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.ExceptionInfoDao;
import com.base.generic.dao.impl.GenericTwoDaoImpl;
import com.base.two.entity.ExceptionInfo;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月25日
 * @此类的作用：
 */
@Repository
public class ExceptionInfoDaoImpl extends GenericTwoDaoImpl<ExceptionInfo> implements ExceptionInfoDao {

}
