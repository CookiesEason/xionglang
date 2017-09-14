package com.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.MenuTreeDao;
import com.base.generic.service.impl.GenericServiceImpl;
import com.base.one.entity.Menu;
import com.base.service.MenuTreeService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月15日 @此类作用：
 */
@Service
public class MenuTreeServiceImpl extends GenericServiceImpl<Menu> implements MenuTreeService {

	@Autowired
	private MenuTreeDao menusDao;

	@Override
	public void setGenericDao() {
		super.genericDao = menusDao;
	}

	@Override
	public void delete(Menu entity, Object... params) {
		menusDao.delete(entity.getId());
	}

}
