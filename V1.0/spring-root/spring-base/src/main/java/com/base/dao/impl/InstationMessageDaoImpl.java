/**
 * 
 */
package com.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.base.dao.InstationMessageDao;
import com.base.generic.dao.impl.GenericOneDaoImpl;
import com.base.one.entity.InstationMessage;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日 @此类的作用：
 */
@Repository
public class InstationMessageDaoImpl extends GenericOneDaoImpl<InstationMessage> implements InstationMessageDao {

	public InstationMessageDaoImpl() {
		super.num = 1;
	}
}
