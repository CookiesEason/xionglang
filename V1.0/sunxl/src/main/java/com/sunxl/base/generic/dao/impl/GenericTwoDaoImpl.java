package com.sunxl.base.generic.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunxl.base.generic.dao.GenericTwoDao;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：指定unitName为MYSQLTWO
 */
public abstract class GenericTwoDaoImpl<T> extends GenericDaoImpl<T> implements GenericTwoDao<T> {
	final Logger logger = LoggerFactory.getLogger(GenericTwoDaoImpl.class);

	/*
	 * @PersistenceContext(unitName = "MYSQLTWO") public void
	 * setEntityManager(EntityManager em) { super.em = em; }
	 */

	@PersistenceUnit(unitName = "MYSQLTWO")
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		super.emf = emf;
		super.em = emf.createEntityManager();
	}
}