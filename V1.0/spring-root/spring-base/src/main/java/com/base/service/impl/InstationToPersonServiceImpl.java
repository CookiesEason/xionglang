/**
 * 
 */
package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.InstationToPersonDao;
import com.base.generic.service.GenericService;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.InstationToPerson;
import com.base.service.InstationToPersonService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日 @此类的作用：
 */
@Service
public class InstationToPersonServiceImpl extends GenericServiceImpl<InstationToPerson> implements InstationToPersonService {

	@Autowired
	private InstationToPersonDao instationToPersonDao;

	@Override
	public void setGenericDao() {
		super.genericDao = instationToPersonDao;
	}

	@Override
	public void delete(InstationToPerson entity, Object... params) {
		if (entity.getInstationToPersons() != null) {
			for (int i = 0, len = entity.getInstationToPersons().size(); i < len; i++) {
				instationToPersonDao.delete(entity.getInstationToPersons().get(i).getId());
			}
		}
	}

	@Override
	public InstationToPerson create(GenericService<InstationToPerson> genericService, InstationToPerson entity, Object... params) {
		for (int i = 0, len = entity.getUsers().size(); i < len; i++) {
			entity.setSendUser(entity.getUsers().get(i));
			instationToPersonDao.create(entity);
		}
		for (int i = 0, len = entity.getAdminusers().size(); i < len; i++) {
			entity.setSendAdminUser(entity.getAdminusers().get(i));
			instationToPersonDao.create(entity);
		}
		return entity;
	}

	@Override
	public InstationToPerson update(GenericService<InstationToPerson> genericService, InstationToPerson entity, Object... params) {
		InstationToPerson instationToPerson = null;
		if (entity.getInstationToPersons() != null) {
			for (int i = 0, len = entity.getInstationToPersons().size(); i < len; i++) {
				instationToPerson = entity.getInstationToPersons().get(i);
				instationToPerson = instationToPersonDao.find(instationToPerson.getId());
				if (params != null) {
					if ("0".equals(params[0]))
						instationToPerson.setSendHasDelete(true);
					if ("1".equals(params[0]))
						instationToPerson.setReceiveHasDelete(true);
					instationToPerson = instationToPersonDao.update(instationToPerson);
				}
			}
		}
		return instationToPerson;
	}
}
