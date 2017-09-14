package com.base.enums;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
public enum Gender {

	MALE("男"), FEMALE("女");

	private String text;

	private Gender(String text) {
		this.text = text;
	}

	public static Gender get(int id) {
		for (Gender g : values()) {
			if (g.ordinal() == id) {
				return g;
			}
		}
		return Gender.MALE;
	}

	public String getText() {
		return text;
	}

}
