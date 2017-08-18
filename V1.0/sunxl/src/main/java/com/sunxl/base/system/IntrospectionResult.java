package com.sunxl.base.system;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class IntrospectionResult {

	private static IntrospectionResult introspectionResult = null;

	private final Map<String, IntrospectionProperty> storage = Collections.synchronizedMap(new WeakHashMap<String, IntrospectionProperty>());

	private IntrospectionResult() {

	}

	public static IntrospectionResult getInstance() {
		if (introspectionResult == null) {
			introspectionResult = new IntrospectionResult();
		}
		return introspectionResult;
	}

	public void addClass(Map<String, IntrospectionProperty> storage) {
		this.storage.putAll(storage);
	}

	public void addClass(Class<?>[] classes) {
		if (classes != null) {
			for (Class<?> clazz : classes) {
				getIntrospectionProperty(clazz);
			}
		}
	}

	public boolean contain(Class<?> clazz) {
		IntrospectionProperty ip = storage.get(clazz.getName());
		return ip != null;
	}

	public IntrospectionProperty getIntrospectionProperty(Class<?> clazz) {
		String className = clazz.getName();
		IntrospectionProperty ip = storage.get(className);
		if (ip == null) {
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
				ip = new IntrospectionProperty(beanInfo);
				storage.put(className, ip);
			} catch (Exception e) {
				throw new IllegalArgumentException("��ʡ�쳣��[" + className + "]");
			}
		}
		return ip;
	}

	public IntrospectionProperty remove(Object key) {
		return storage.remove(key);
	}

	public void clear() {
		storage.clear();
	}

	public int size() {
		return storage.size();
	}

}
