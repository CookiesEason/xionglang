/**
 * 
 */
package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.InstationMessageDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.InstationMessage;
import com.base.service.InstationMessageService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日 @此类的作用：
 */
@Service
public class InstationMessageServiceImpl extends GenericServiceImpl<InstationMessage> implements InstationMessageService {

	@Autowired
	private InstationMessageDao instationMessageDao;

	@Override
	public void setGenericDao() {
		super.genericDao = instationMessageDao;
	}

	@Override
	public void delete(InstationMessage entity, Object... params) {
		instationMessageDao.delete(entity.getId());
	}

}
