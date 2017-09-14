package com.dubbo.custom.controlller;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dubbo.server.service.ServerService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月14日
 * @此类的作用：客户端调用接口
 */
@SuppressWarnings("resource")
public class Consumer {
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "META-INF/dubbo-consumer.xml" });
			context.start();
			ServerService serverService = (ServerService) context.getBean("serverService");
			String say = serverService.sayHello("World");
			System.out.println(say);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
