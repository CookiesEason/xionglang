package com.base.invactionHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月30日 @此类的作用：
 */
public class MyInvocationHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(MyInvocationHandler.class);
	private Object target;

	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	public Object getTarget() {
		return target;
	}

	/**
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 * @此方法的作用：
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ("getIntByNativeSQL".equals(method.getName())) {
			logger.error("++++++++befor:" + method.getName() + "+++++++");
			Object result = method.invoke(target, args);
			logger.error("++++++++after:" + method.getName() + "+++++++");
			return result;
		}
		return null;
	}

}
