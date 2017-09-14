package com.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public enum ParamsType {

	add("添加"), to_add("去添加"), update("更新"), to_edit("去编辑"), del("删除"), search("搜索"), upload("上传"), audit("审核"), detail("详细"), list("列表"), print("打印"), assign("分配"), other("其他");
	private String text;

	private ParamsType(String text) {
		this.text = text;
	}

	public static ParamsType get(int id) {
		for (ParamsType g : values()) {
			if (g.ordinal() == id) {
				return g;
			}
		}
		return ParamsType.other;
	}

	public String getText() {
		return text;
	}

}
