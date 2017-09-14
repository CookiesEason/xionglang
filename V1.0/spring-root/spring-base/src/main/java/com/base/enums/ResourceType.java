package com.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public enum ResourceType {
	
	IMAGE("image/jpeg"),ATTACHMENT("application/octet-stream"),PDF("application/pdf"),DOC("application/msword");
	private String contextType;

	private ResourceType(String contextType) {
		this.contextType = contextType;
	}

	public String getContextType() {
		return contextType;
	}

}
