package com.base.listener;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@SuppressWarnings("unused")
public class CountListener implements HttpSessionAttributeListener, HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(CountListener.class);
	private static final int INIT_PEOPLE_COUNT = 0;
	/**
	 * @param event
	 * @此方法的作用：session添加session信息时触发
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
	}
	/**
	 * @param event
	 * @此方法的作用：session移除session信息时触发
	 */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
	}
	/**
	 * @param event
	 * @此方法的作用：session替换session信息时触发
	 */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

	/**
	 * @param event
	 * @此方法的作用：创建Session触发（推荐使用上面的存储session信息）
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {

	}

	/**
	 * @param event
	 * @此方法的作用：销毁Session触发（推荐使用上面的存储session信息）
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
	}
}
