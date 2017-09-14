package com.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月4日
 * @此类作用
 */
public enum StatusType {

	DREDGE("开通", 0), USE("使用", 1), FROST("冻结", -1), START("启用", 1), STOP("不启用", 0), SUBMIT("提交", 2), EXAMINE("审核", 3), PASS("通过", 4), NOPASS("不通过", 5), CANCEL("作废", 6);

	private String typeName;
	private int type;

	public int getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setType(int type) {
		this.type = type;
	}

	private StatusType(String typeName, int type) {
		this.typeName = typeName;
		this.type = type;
	}
}
