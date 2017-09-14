package com.base.generic.dao.impl;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.base.generic.dao.GenericOneDao;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：指定unitName为MYSQLONE
 */
@SuppressWarnings("static-access")
public abstract class GenericOneDaoImpl<T> extends GenericDaoImpl<T> implements GenericOneDao<T> {
	/*
	 * @PersistenceContext(unitName = "MYSQLONE") public void
	 * setEntityManager(EntityManager em) { super.em = em; }
	 */

	@PersistenceUnit(unitName = "MYSQLONE")
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		LinkedList<EntityManager> list = new LinkedList<EntityManager>();
		for (int i = 0; i < super.num; i++)
			list.add(emf.createEntityManager());
		super.emsMap.put(super.type, list);
		super.emfs.put(super.type, emf);
	}
}
