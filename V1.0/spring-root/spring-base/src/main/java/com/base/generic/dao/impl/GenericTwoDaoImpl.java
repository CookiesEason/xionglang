package com.base.generic.dao.impl;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.base.generic.dao.GenericTwoDao;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：指定unitName为MYSQLTWO
 */
@SuppressWarnings("static-access")
public abstract class GenericTwoDaoImpl<T> extends GenericDaoImpl<T> implements GenericTwoDao<T> {
	/**
	 * @param emf
	 * @此方法的作用：为了多线程，设置几个连接
	 */
	@PersistenceUnit(unitName = "MYSQLTWO")
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		LinkedList<EntityManager> list = new LinkedList<EntityManager>();
		for (int i = 0; i < super.num; i++)
			list.add(emf.createEntityManager());
		super.emsMap.put(super.type, list);
		super.emfs.put(super.type, emf);
	}

}