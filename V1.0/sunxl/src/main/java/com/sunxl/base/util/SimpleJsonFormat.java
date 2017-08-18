package com.sunxl.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.sunxl.base.interfaces.FilterField;
import com.sunxl.base.interfaces.MethodLog;

import net.sf.json.JSONObject;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：对象转换为JSON
 */
public class SimpleJsonFormat {
	private final static Logger logger = LoggerFactory.getLogger(SimpleJsonFormat.class);
	private List<String> ignoreProperties;
	private Map<Class<?>, JsonConverter> converters;
	private int deep;
	private boolean fieldAccess;
	private Object obj;
	private StringBuilder builder;
	private String dateFormat;

	public SimpleJsonFormat(Object obj) {
		super();
		this.obj = obj;
		this.deep = 2;
		this.dateFormat = "yyyy-MM-dd HH:mm:ss";
		converters = new HashMap<Class<?>, JsonConverter>();
		add(Enum.class, new JsonConverter() {
			@Override
			public void convert(Object obj, JsonWrite jw) {
				String str;
				if (TypeLabel.class.isInstance(obj)) {
					str = ((TypeLabel) obj).getText();
				} else {
					str = String.valueOf(obj);
				}
				jw.stringWrite(str);
			}

		});
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	private boolean needIgnore(String field, PropertyDescriptor pd) {
		boolean flag = false;
		if (ignoreProperties != null) {
			flag = ignoreProperties.contains(field);
		}
		if (flag) {
			return true;
		}
		if (pd != null) {
			JsonIgnore ji = pd.getReadMethod() == null ? null : pd.getReadMethod().getAnnotation(JsonIgnore.class);
			if (ji != null) {
				return true;
			}
		}
		return false;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void setIgnoreProperties(String... ignoreProperties) {
		if (ignoreProperties != null) {
			this.ignoreProperties = Arrays.asList(ignoreProperties);
		}
	}

	public void add(Class<?> key, JsonConverter jsonConverter) {
		converters.put(key, jsonConverter);
	}

	public String serialize() {
		if (obj != null) {
			builder = new StringBuilder();
			parseObject(obj, 0);
		}
		return builder == null ? "null" : builder.toString();
	}

	public JSONObject getJSONObject() {
		JSONObject json = JSONObject.fromObject(serialize());
		return json;
	}

	private void parseObject(Object o, int index) {
		if (o == null) {
			builder.append("null");
			return;
		}
		Class<?> type = o.getClass();
		if (isList(type)) {
			builder.append("[");
			exeList(o, index);
			builder.append("]");
		} else if (isBasicType(type)) {
			exeBasic(o);
		} else {
			if (index > deep) {
				builder.append("null");
				return;
			}
			builder.append("{");
			if (isMap(type)) {
				exeMap(o, index);
			} else {
				exeObj(o, index);
			}
			builder.append("}");
		}
	}

	private void exeBasic(Object o) {
		if (Date.class.isAssignableFrom(o.getClass())) {
			DateFormat df = new SimpleDateFormat(dateFormat);
			writeString(df.format((Date) o));
		} else if (o.getClass() == String.class) {
			writeString(String.valueOf(o));
		} else if (o.getClass().isEnum()) {
			if (converters.get(Enum.class) == null) {
				writeString(o.toString());
			} else {
				converters.get(Enum.class).convert(o, new JsonWrite());
			}
		} else {
			writeRaw(String.valueOf(o));
		}
	}

	private void writeString(String str) {
		if (str.indexOf("\"") >= 0) {
			str = TextUtils.replace(str, "\"", "\\\"");
		}
		builder.append("\"").append(str).append("\"");
	}

	private void writeRaw(String str) {
		builder.append(str);
	}

	private void exeObj(Object o, int index) {
		try {
			Class<?> type = o.getClass();
			JsonConverter jc = converters.get(type);
			if (jc != null) {
				jc.convert(o, new JsonWrite());
			} else {
				PropertyDescriptor[] pds = null;

				if (fieldAccess) {
					Field[] fields = type.getDeclaredFields();
					List<PropertyDescriptor> list = new ArrayList<PropertyDescriptor>();
					for (Field f : fields) {
						list.add(BeanUtils.getPropertyDescriptor(type, f.getName()));
					}
				} else {
					pds = BeanUtils.getPropertyDescriptors(type);
				}

				boolean isFirst = true;
				for (PropertyDescriptor pd : pds) {
					if (pd.getName().equals("class")) {
						continue;
					}
					// System.out.println(pd.getName());
					if (!needIgnore(pd.getName(), pd) && pd.getReadMethod() != null) {
						if (isFirst) {
							isFirst = false;
						} else {
							builder.append(",");
						}
						put(pd.getName(), pd.getReadMethod().invoke(o), index);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("error for invoke:" + e);
		}

	}

	private void exeMap(Object o, int index) {
		Map<?, ?> map = (Map<?, ?>) o;
		boolean isFirst = true;
		for (Entry<?, ?> entry : map.entrySet()) {
			if (!needIgnore(String.valueOf(entry.getKey()), null)) {
				if (isFirst) {
					isFirst = false;
				} else {
					builder.append(",");
				}
				put(String.valueOf(entry.getKey()), entry.getValue(), index);
			}
		}
	}

	private void put(String key, Object value, int index) {
		builder.append("\"").append(key).append("\"");
		builder.append(":");
		parseObject(value, index + 1);
	}

	private void exeList(Object o, int index) {
		boolean isFirst = true;
		if (o instanceof Collection) {
			for (Object ob : (Collection<?>) o) {
				if (isFirst) {
					isFirst = false;
				} else {
					builder.append(",");
				}
				parseObject(ob, index);
			}
		} else {
			for (int i = 0; i < Array.getLength(o); i++) {
				Object ob = Array.get(o, i);
				if (isFirst) {
					isFirst = false;
				} else {
					builder.append(",");
				}
				parseObject(ob, index);
			}
		}
	}

	private boolean isBasicType(Class<?> clazz) {
		if (clazz == null)
			return false;
		if (clazz.isPrimitive() || Date.class.isAssignableFrom(clazz) || clazz.equals(String.class) || Number.class.isAssignableFrom(clazz) || Character.class.equals(clazz) || Boolean.class.equals(clazz) || clazz.isEnum()) {
			return true;
		}
		return false;
	}

	private boolean isList(Class<?> type) {
		return Collection.class.isAssignableFrom(type) || type.isArray();
	}

	private boolean isMap(Class<?> type) {
		return Map.class.isAssignableFrom(type);
	}

	class JsonWrite {
		public void stringWrite(String str) {
			writeString(str);
		}

		public void rawWrite(String str) {
			writeRaw(str);
		}
	}

	public void remove(Class<?> class1) {
		converters.remove(class1);
	}

	/**
	 * @param path类路径
	 * @param methodName方法名
	 * @param className类名是否需要扫描
	 * @return
	 * @auto：熊浪
	 * @Time：2017年8月3日 @此方法的作用：解析FilterField
	 */
	@MethodLog(remark = "获取FieldValue的注解值(注解值为对象需要忽略的属性值)")
	private static List<String> getFilterFieldValues(String path, String methodName, boolean className) {
		List<String> list = new ArrayList<String>();
		Class<?> clazz = null;
		FilterField filterField = null;
		String[] values = null;
		try {
			clazz = Class.forName(path);
			if (className && clazz.isAnnotationPresent(FilterField.class)) {
				filterField = (FilterField) clazz.getAnnotation(FilterField.class);
				values = filterField.value();
				for (int i = 0, len = values.length; i < len; i++)
					list.add(values[i]);
			}
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
				if (m.getName().equals(methodName) && m.isAnnotationPresent(FilterField.class)) {
					filterField = (FilterField) m.getAnnotation(FilterField.class);
					values = filterField.value();
					for (int i = 0, len = values.length; i < len; i++)
						list.add(values[i]);
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
		return list;
	}

	/**
	 * @param rows源数据
	 * @param path注解所在的类
	 * @param methodName注解所在的方法
	 * @param className是否扫描类上的该注解
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日
	 * @此方法的作用：把循环的字段设置为null
	 */
	@MethodLog(remark = "把循环的字段设置为null")
	public static List<?> getNewListObject(List<?> rows, String path, String methodName, boolean className) {
		try {
			List<String> values = getFilterFieldValues(path, methodName, className);
			Field[] fieldsOne = null, fieldsTwo = null;
			Object obj = null;
			boolean flagOne = false, flagTwo = false;
			for (Object o : rows) {
				fieldsOne = o.getClass().getDeclaredFields();
				for (Field fieldOne : fieldsOne) {
					for (String val : values) {
						if (val.startsWith(fieldOne.getName())) {
							flagOne = fieldOne.isAccessible();
							fieldOne.setAccessible(true);
							obj = fieldOne.get(o);
							if (obj != null) {// 如果本身外键为null,就不用替换
								fieldsTwo = obj.getClass().getDeclaredFields();
								for (Field fieldTwo : fieldsTwo) {
									if (val.endsWith(fieldTwo.getName())) {
										flagTwo = fieldTwo.isAccessible();
										fieldTwo.setAccessible(true);
										fieldTwo.set(obj, null);// 把循环的值设置为null，断开循环
										fieldTwo.setAccessible(flagTwo);
									}
								}
							}
							fieldOne.setAccessible(flagOne);
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
		return rows;
	}
}
