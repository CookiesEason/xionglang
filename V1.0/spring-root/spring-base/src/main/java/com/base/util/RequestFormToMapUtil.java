package com.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月17日
 * @此类作用：
 */
public class RequestFormToMapUtil {
	private static Log log = LogFactory.getLog(RequestFormToMapUtil.class);

	/**
	 * 当表单中的数据超过一条， 我们不好封装到指定的实体类中 比如客户给我们开的发票，理论上我们是需呀建立2张表，因为流水数据有2条，
	 * 这是就可以用这个方法直接接受所有的数据，之后通过反射获取所有的属性 之后在针对性的封装
	 */
	public Map<String, String[]> getParaments(HttpServletRequest request, String classpath) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			Class<?> c = Class.forName(classpath);
			Field[] attributes = c.getDeclaredFields();// 获取所有的属性
			for (Field field : attributes) {
				String name = field.getName();
				if (!"serialVersionUID".equals(name) && !"".equals(name))
					map.put(name, request.getParameterValues(name));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("获取参数出错");
		}
		return map;
	}

	/**
	 * 判断类型是否是基本类型
	 */
	public static boolean isAllBasicType(String type) {
		boolean flag = false;
		if (type != null && !type.equals("") && type.indexOf("enums") <= 0) {
			type = type.toUpperCase();
			if (type.lastIndexOf(".") == -1)
				flag = false;
			else {
				type = type.substring(type.lastIndexOf(".") + 1, type.length());
				if ("SERIALVERSIONUID,BYTE,SHORT,INT,LONG,INTEGER,BOOLEAN,FLOAT,DOUBLE,STRING,MAP,LIST,HASHMAP,TREESET,SET,ARRAYLIST,DATE,TIMESTAMP".contains(type))
					flag = false;
				else
					flag = true;
			}
		} else
			flag = false;
		return flag;
	}

	public static void main(String[] args) {
		try {
			Class<?> c = Class.forName("com.base.one.entity.User");
			Field[] attributes = c.getDeclaredFields();// 获取所有的属性
			for (Field field : attributes) {
				System.out.println(isAllBasicType(field.getType().toString()));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
