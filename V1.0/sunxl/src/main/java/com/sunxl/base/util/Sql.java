package com.sunxl.base.util;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：执行的是HQL语句， 必须遵循HQL语句数序SET信息>WHERE信息>GROUP BY信息>ORDER BY信息
 */
public class Sql {

	private String order;// 排序条件
	private int begin;
	private int end;
	private Class<?> clazz;
	private String group;// 分组条件
	private String[] params;// where条件
	private int offset;
	private String[] setParams;// update的set条件
	private int setParamsOffset;
	private String result;// 别名
	private boolean loaded;

	/**
	 * 默认使用的创建HQL对象
	 */
	public Sql() {
		params = new String[3];
	}

	/**
	 * 修改HQL
	 * 
	 * @param setId,需要修改的字段个数
	 */
	public Sql(int setId) {
		params = new String[3];
		setParams = new String[setId];
	}

	/**
	 * 以什么字段默认排序
	 * 
	 * @param param
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setOrder(String param) {
		return setOrder(param, "");
	}

	/**
	 * 按什么字段分组
	 * 
	 * @param groupBy
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setGroupBy(String groupBy) {
		group = " GROUP BY " + groupBy;
		return this;
	}

	/**
	 * 以什么字段按某规则排序
	 * 
	 * @param param
	 * @param by排序规则asc升序desc降序
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setOrder(String param, String by) {
		order = " ORDER BY " + param + " " + by;
		return this;
	}

	/**
	 * 开始数据
	 * 
	 * @param begin
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setBegin(int begin) {
		this.begin = begin;
		return this;
	}

	/**
	 * 结束数据
	 * 
	 * @param end
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setEnd(int end) {
		this.end = end;
		return this;
	}

	/**
	 * 生成HQL语句使用的class类
	 * 
	 * @param clazz
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setClass(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql setValueSet(String key, Object value) {
		if (setParamsOffset >= setParams.length) {
			bulkCapacity(2);
		}
		setParams[setParamsOffset++] = key + (value == null ? "" : "='" + value + "',");
		return this;
	}

	/**
	 * 添加查询条件
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql add(String key, Object value) {
		return add(key, value, null);
	}

	/**
	 * 查询条件
	 * 
	 * @param key
	 * @param value
	 * @param operator（可以为null,like,in等）
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql add(String key, Object value, String operator) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		operator = Inspector.isEmpty(operator) ? "=" : operator;
		if (operator.toUpperCase().trim().equals("LIKE")) {
			params[offset++] = key + " " + operator + " '%" + value + "%'";
		} else if (operator.toUpperCase().trim().equals("IN") || operator.toUpperCase().trim().equals("NOT IN")) {
			params[offset++] = key + " " + operator + " (" + (value == null ? "" : "'" + value + "'") + ")";
		} else {
			params[offset++] = key + " " + operator + " " + (value == null ? "" : "'" + value + "'");
		}
		return this;
	}

	/**
	 * 添加OR条件查询，值一定，键多个 第一个参数为值
	 * 
	 * @param key
	 * @param key2
	 * @param value
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql addORValue(Object value, String... params) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		StringBuilder sb = new StringBuilder();
		if (params.length > 0) {
			for (int i = 0, len = params.length; i < len; i++) {
				if (i == len - 1)
					sb.append(params[i] + " = " + value);
				else
					sb.append(params[i] + " = " + value + " OR ");
			}
		}
		params[offset++] = sb.toString();
		return this;
	}

	/**
	 * 添加OR条件查询,键一定，值多个 第一个参数为键
	 * 
	 * @param key
	 * @param params
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql addOrKey(String key, Object... params) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		StringBuilder sb = new StringBuilder();
		if (params.length > 0) {
			for (int i = 0, len = params.length; i < len; i++) {
				if (i == len - 1)
					sb.append(key + " = " + params[i]);
				else
					sb.append(key + " = " + params[i] + " OR ");
			}
		}
		params[offset++] = sb.toString();
		return this;
	}

	/**
	 * 添加非等于条件！=
	 * 
	 * @param key
	 * @param value1
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql addNot(String key, Object value1) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		params[offset++] = key + " != " + value1;
		return this;
	}

	/**
	 * 添加is null语句
	 * 
	 * @param key
	 * @param value1
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql addNull(String key, Object value1) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		params[offset++] = key + " is " + value1;
		return this;
	}

	/**
	 * 添加is not null
	 * 
	 * @param key
	 * @param value1
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql addnotNull(String key, Object value1) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		params[offset++] = key + " is not " + value1;
		return this;
	}

	/**
	 * 扩充条件数组
	 * 
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	private void bulkCapacity(int type) {
		if (type == 1) {
			String[] newArray = new String[params.length + 5];
			System.arraycopy(params, 0, newArray, 0, params.length);
			params = newArray;
		} else {
			String[] newArray = new String[setParams.length + 5];
			System.arraycopy(setParams, 0, newArray, 0, setParams.length);
			setParams = newArray;
		}
	}

	/**
	 * 总数
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql useCount() {
		result = " COUNT(o) ";
		return this;
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql useSearch() {
		result = " o ";
		return this;
	}

	/**
	 * 求和
	 * 
	 * @param column
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	public Sql useSum(String column) {
		result = " SUM(o." + column + ") ";
		return this;
	}

	/**
	 * HQL对象查询
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	@JsonIgnore
	public String getStatement() {
		result = Inspector.isEmpty(result) ? " o " : result;
		return "SELECT " + result + "FROM " + clazz.getSimpleName() + " o" + build();
	}

	/**
	 * HQL总数查询
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	@JsonIgnore
	public String getCount() {
		return "SELECT COUNT(o) FROM " + clazz.getSimpleName() + " o " + build();
	}

	@JsonIgnore
	public String getFind() {
		return "SELECT o FROM " + clazz.getSimpleName() + " o " + build();
	}

	public String getUpdate() {
		return "UPDATE " + clazz.getSimpleName() + " o " + valueSet() + build();
	}

	public String getDelete() {
		return "DELETE FROM " + clazz.getSimpleName() + " o " + build();
	}

	private String valueSet() {
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for (String s : setParams) {
			if (Inspector.isEmpty(s)) {
				continue;
			}
			if (flag) {
				flag = false;
				sb.append(" SET ");
			} else {
				sb.append(" , ");
			}
			sb.append("o.").append(s);
		}
		return sb.toString();
	}

	/**
	 * 条件信息
	 * 
	 * @return
	 * @auto：熊浪 @Time：2017年8月3日 @此方法的作用：
	 */
	private String build() {
		if (!loaded) {
			loadWhere();
			loaded = true;
		}
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for (String s : params) {
			if (Inspector.isEmpty(s)) {
				continue;
			}
			if (flag) {
				flag = false;
				sb.append(" WHERE ");
			} else {
				sb.append(" AND ");
			}
			sb.append("o.").append(s);
		}
		if (group != null) {
			sb.append(group);
		}
		if (order != null) {
			sb.append(order);
		}
		return sb.toString();
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	protected void loadWhere() {

	}

	public Sql addLike(String key, Object value) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		params[offset++] = key + " like '%" + value + "%'";
		return this;
	}

	public Sql addListStr(String key, List<String> list) {
		if (offset >= params.length) {
			bulkCapacity(1);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("'" + list.get(0) + "'");
		for (int i = 1; i < list.size(); i++) {
			sb.append("," + "'" + list.get(i) + "'");
		}
		sb.append(")");
		params[offset++] = key + " in(" + sb.toString();
		return this;
	}
}
