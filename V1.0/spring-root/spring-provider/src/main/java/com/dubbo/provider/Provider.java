package com.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月14日
 * @此类的作用：服务器接口中心
 */
@SuppressWarnings("resource")
public class Provider {

	public static void main(String[] args) throws Exception {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "META-INF/dubbo-provider.xml" });
			context.start();
			System.out.println("服务器已开启");
			System.in.read(); // 按任意键退出
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
