package com.sunxl.base.system;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public class IntrospectionProperty {

	private String className;
	private final Map<String, PropertyDescriptor> properties;

	public IntrospectionProperty(BeanInfo beanInfo) {
		className = beanInfo.getBeanDescriptor().getBeanClass().getName();
		properties = new LinkedHashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			properties.put(pd.getName(), pd);
		}
	}

	public Set<Entry<String, PropertyDescriptor>> getSet() {
		return this.properties.entrySet();
	}

	public void clear() {
		properties.clear();
	}

	public int size() {
		return properties.size();
	}

	public PropertyDescriptor getPropertyDescriptor(String propertyName) {
		return properties.get(propertyName);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
