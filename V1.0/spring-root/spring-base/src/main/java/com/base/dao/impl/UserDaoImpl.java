package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.UserDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.User;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Repository
public class UserDaoImpl extends GenericOneDaoImpl<User> implements UserDao {

}
