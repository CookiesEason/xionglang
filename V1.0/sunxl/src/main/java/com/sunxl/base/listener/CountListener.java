package com.sunxl.base.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@SuppressWarnings("unused")
public class CountListener implements HttpSessionAttributeListener, HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(CountListener.class);
	private static final int INIT_PEOPLE_COUNT = 0;

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {

	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.info(" Countlistener has work ");

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

	}
}
