package com.sunxl.test;

import org.springframework.core.JdkVersion;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn	
 * @Time 2017年9月6日 @此类的作用：
 */
public class Test {
	public static void main(String[] args) {
		System.out.println(JdkVersion.getMajorJavaVersion());
		System.out.println(JdkVersion.JAVA_15);
		System.out.println(JdkVersion.getMajorJavaVersion() < JdkVersion.JAVA_15);
	}
}
