package com.sunxl.base.form;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年8月8日 @此类作用：
 */
public class ColumnBeanForm {
	private String columnName;//表字段
	private String SqlType;//字段类型

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSqlType() {
		return SqlType;
	}

	public void setSqlType(String sqlType) {
		SqlType = sqlType;
	}

	public String toString() {
		return columnName + " " + SqlType;
	}
}
