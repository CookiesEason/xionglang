package com.sunxl.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月26日
 * @此类作用
 */
public class ListForMapUtil {
	private static final Logger logger = LoggerFactory.getLogger(ListForMapUtil.class);

	/**
	 * list对象转换为map对象
	 * 
	 * @param list
	 * @param len
	 * @return
	 */
	public static List<Map<String, Object>> listForMap(List<?> list, int len) {
		if (list == null || len == 0)
			return null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(len);
		for (int i = 0; i < len; i++) {
			listMap.add(getValueMap(list.get(i)));
		}
		return listMap;
	}

	private static Map<String, Object> getValueMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] field = obj.getClass().getDeclaredFields();
		boolean flag = false;
		for (int i = 0, len = field.length; i < len; i++) {
			String varName = field[i].getName();
			try {
				flag = field[i].isAccessible();
				field[i].setAccessible(true);
				Object o = field[i].get(obj);
				if (o != null)
					map.put(varName, o);
				field[i].setAccessible(flag);
			} catch (Exception e) {
				logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			}
		}
		return map;
	}
}
