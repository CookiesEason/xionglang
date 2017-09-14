package com.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月31日 @此类的作用：
 */
public enum LocationType {

	LEFTTOP("首页左上"), LEFTDOWN("首页左下"), RIGHTLEFTOP("首页右左上"), RIGHTRIGHTTOP("首页右右上"), RIGHTLEFDOWN("首页右左下"), RIGHTRIGHTDOWN("首页右右下");

	private String typeName;

	private LocationType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
}
