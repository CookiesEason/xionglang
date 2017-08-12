package com.sunxl.base.form;

/**
 * @author：熊浪
 * @Email：xiongl
 * @sunline.cn 
 * @Time：2017年7月14日 
 * @此类作用：前台easyUI查询需要用到字段名和字段值
 */
public class FieldForm {

	private String field;// 字段值
	private String name;// 字段名

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldForm(String field, String name) {
		super();
		this.field = field;
		this.name = name;
	}

	public FieldForm() {
		super();
	}
}
