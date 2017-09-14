package com.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class Inspector {
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.length() < 1) {
			return true;
		} else {
			return str.trim().length() < 1;
		}
	}

	// 使用了此正则表达式 ^[0-9a-zA-Z\_\.]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$
	public static boolean isEmailAddress(String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z\\_\\.]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static String escapeHtml(String string) {
		if (isEmpty(string))
			return string;
		string = StringUtils.replace(string, "<", "&lt;");
		string = StringUtils.replace(string, ">", "&gt;");
		return string;
	}

	public static String escapeScript(String string) {
		if (isEmpty(string))
			return string;
		string = StringUtils.replace(string, "<script", "&lt;script");
		return null;
	}
}
