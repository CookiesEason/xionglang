package com.base.invactionHandler;

import java.lang.reflect.Proxy;

import com.base.service.UserService;
import com.base.service.impl.UserServiceImpl;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月30日 @此类的作用：
 */
public class ProxyInvocation {

	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		MyInvocationHandler invocation = new MyInvocationHandler(userService);
		UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), invocation);
		userServiceProxy.getIntByNativeSQL(userService, "");
	}
}
