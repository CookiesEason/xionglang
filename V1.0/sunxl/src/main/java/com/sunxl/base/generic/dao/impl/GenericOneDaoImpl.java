package com.sunxl.base.generic.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunxl.base.generic.dao.GenericOneDao;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：指定unitName为MYSQLONE
 */
public abstract class GenericOneDaoImpl<T> extends GenericDaoImpl<T> implements GenericOneDao<T> {
	final Logger logger = LoggerFactory.getLogger(GenericOneDaoImpl.class);

	/*
	 * @PersistenceContext(unitName = "MYSQLONE") public void
	 * setEntityManager(EntityManager em) { super.em = em; }
	 */

	@PersistenceUnit(unitName = "MYSQLONE")
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		super.emf = emf;
		super.em = emf.createEntityManager();
		super.tx = em.getTransaction();
	}
}
