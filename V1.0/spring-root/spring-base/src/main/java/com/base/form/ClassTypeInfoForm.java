package com.base.form;

import com.base.util.SunxlConnectionFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月1日 @此类的作用：
 */
public class ClassTypeInfoForm {

	private Class<?> type;

	private String sql;

	private Object[] params;

	private String jndiName;

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public ClassTypeInfoForm(String sql, Object... params) {
		super();
		this.sql = sql;
		this.params = params;
		this.jndiName = SunxlConnectionFactory.getJndiName();
	}

	public ClassTypeInfoForm(Class<?> type, String sql, Object... params) {
		super();
		this.type = type;
		this.sql = sql;
		this.params = params;
		this.jndiName = SunxlConnectionFactory.getJndiName();
	}

	public ClassTypeInfoForm(String sql, String jndiName, Object... params) {
		super();
		this.sql = sql;
		this.params = params;
		this.jndiName = jndiName;
	}

	public ClassTypeInfoForm(Class<?> type, String sql, String jndiName, Object... params) {
		super();
		this.type = type;
		this.sql = sql;
		this.params = params;
		this.jndiName = jndiName;
	}
}
