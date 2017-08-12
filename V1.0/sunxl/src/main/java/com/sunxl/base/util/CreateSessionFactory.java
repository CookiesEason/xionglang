package com.sunxl.base.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：
 */
public class CreateSessionFactory {
	private Configuration config = null;
	private SessionFactory sessionFactory = null;
	private Session session = null;

	/**
	 * 创建session和sessionFactory
	 */
	@SuppressWarnings("deprecation")
	public CreateSessionFactory() {
		this.config = new Configuration().configure();
		this.sessionFactory = config.buildSessionFactory();
		this.session = sessionFactory.getCurrentSession();
	}

	/**
	 * 获取sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 获取session
	 */
	public Session getSession() {
		return session;
	}
}
