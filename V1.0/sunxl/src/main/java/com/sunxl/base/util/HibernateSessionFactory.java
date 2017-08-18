package com.sunxl.base.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunxl.base.interceptor.InitDataInterceptor;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年8月10日 @此类作用：自定义的session工厂，主要用于控制复杂不易写于service层的控制事物
 */
public class HibernateSessionFactory {
	private static Logger logger = LoggerFactory.getLogger(HibernateSessionFactory.class);
	private static Map<Thread, Session> sessionMap;
	private static SessionFactory sessionFactory;

	/**
	 * 初始化一个sessionFactory
	 */
	static {
		SunXlProperties p = (SunXlProperties) InitDataInterceptor.map.get("META-INF/system.properties");
		if (p == null) {
			try {
				p = new SunXlProperties("META-INF/system.properties");
			} catch (IOException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
		sessionMap = new HashMap<Thread, Session>();
		Configuration cfg = new Configuration();
		cfg.configure(p.getProperty("sunxl.hibernate.cfg"));
		Properties properties = cfg.getProperties();
		p = (SunXlProperties) InitDataInterceptor.map.get("META-INF/dataSource.properties");
		if (p == null) {
			try {
				p = new SunXlProperties("META-INF/system.properties");
			} catch (IOException e) {
				logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			}
		}
		properties.setProperty("hibernate.connection.driver_class", p.getProperty("user.sunxl.driverClass"));
		properties.setProperty("hibernate.dialect", p.getProperty("user.sunxl.dialect"));
		properties.setProperty("hibernate.connection.url", p.getProperty("user.sunxl.jdbcurl"));
		properties.setProperty("hibernate.connection.username", p.getProperty("user.sunxl.user"));
		properties.setProperty("hibernate.connection.password", p.getProperty("user.sunxl.password"));
		cfg.setProperties(properties);
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
		sessionFactory = cfg.buildSessionFactory(sr);
	}

	/**
	 * 开启当前线程
	 * 
	 * @auto：熊浪 @Time：2017年8月10日 @此方法的作用：
	 */
	private static Session openSession() {
		Session session = sessionMap.get(Thread.currentThread());// 获取当前线程的session，如果存在，直接返回该session
		if (session == null) {// 如果不存在，开启一个session，并把当前线程的session放入sessionMap中
			session = sessionFactory.openSession();
			sessionMap.put(Thread.currentThread(), sessionFactory.openSession());
		}
		return session;
	}

	/**
	 * 获取当前线程的session
	 * 
	 * @auto：熊浪 @Time：2017年8月10日 @此方法的作用：
	 */
	public static Session getCurrentSession() {
		if (sessionMap.get(Thread.currentThread()) == null)
			return openSession();
		return sessionMap.get(Thread.currentThread());
	}

	/**
	 * 当前线程结束，需要把session关闭
	 * 
	 * @auto：熊浪
	 * @Time：2017年8月10日 @此方法的作用：关闭之前需要先移除session
	 */
	public static void closeAndRemoveSession() {
		if (sessionMap.get(Thread.currentThread()) != null) {
			Session session = sessionMap.remove(Thread.currentThread());
			if (session != null)
				session.close();
		}
	}

	public static void beginTransaction() {
		getCurrentSession().beginTransaction();
	}

	public static void commitTransaction() {
		getCurrentSession().getTransaction().commit();
	}

	public static void rollBack() {
		getCurrentSession().getTransaction().rollback();
	}

	public static void transactionTimeOut(int seconds) {
		getCurrentSession().getTransaction().setTimeout(seconds);
	}
}
