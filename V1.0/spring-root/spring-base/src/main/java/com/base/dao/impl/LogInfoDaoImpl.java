package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.LogInfoDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.LogInfo;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月25日
 * @此类的作用：
 */
@Repository
public class LogInfoDaoImpl extends GenericOneDaoImpl<LogInfo> implements LogInfoDao {

}
