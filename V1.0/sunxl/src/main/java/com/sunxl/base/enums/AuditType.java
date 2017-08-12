package com.sunxl.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public enum AuditType {

	UNPUBLISH("未发布"),WAITING("等待"),FAILURE("失败"),SUCCESS("成功");
	
	private String text;

	private AuditType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
