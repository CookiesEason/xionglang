package com.sunxl.base.util;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class TextUtils {
	public static String stringOf(Object obj) {
		if (obj == null)
			return "";
		return String.valueOf(obj);
	}

	/**
	 * 去掉所有空格字符
	 * 
	 * @param str
	 * @return
	 */
	public static String trimAll(String str) {
		if (isEmpty(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * 是否为空字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
		return (str == null || str.length() < 1);
	}

	/**
	 * 是否为文本
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isText(String str) {
		if (isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 忽略大小写 开头匹配
	 * 
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}

		if (str.length() < prefix.length()) {
			return false;
		}
		String s = str.substring(0, prefix.length()).toLowerCase();
		String p = prefix.toLowerCase();
		return p.equals(s);
	}

	/**
	 * 忽略大小写 index
	 */
	public static int indexOfWithIgnoreCase(String str, String fix) {
		if (str == null || fix == null) {
			return -1;
		}
		if (str.length() < fix.length()) {
			return -1;
		}
		int index = 0;
		int length = fix.length();
		while ((index + length) <= str.length()) {
			String temp = str.substring(index, index + length);
			if (temp.toLowerCase().equals(fix.toLowerCase()))
				return index;
			index++;
		}
		return -1;
	}

	public static String gotThingsInBag(String temp, String prefix, String suffix) {
		if (isEmpty(temp))
			return "";
		int indexPre = temp.indexOf(prefix);
		int indexSuf = temp.indexOf(suffix);
		if (indexPre >= 0 && indexSuf > 0) {
			if (indexSuf - indexPre <= 1) {
				return "";
			} else {
				String s = temp.substring(indexPre + 1, indexSuf) + ",";
				return s + gotThingsInBag(temp.substring(indexSuf + 1), prefix, suffix);
			}
		} else {
			return "";
		}
	}

	/**
	 * 忽略大小写结束匹配
	 * 
	 * @param str
	 * @param suffix
	 * @return
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}

		if (str.length() < suffix.length()) {
			return false;
		}
		String s = str.substring(str.length() - suffix.length()).toLowerCase();
		String p = suffix.toLowerCase();
		return p.equals(s);
	}

	/**
	 * 从str指定 index 开始 substring 是否匹配
	 * 
	 * @param str
	 * @param index
	 * @param substring
	 * @return
	 */
	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		if (str == null || substring == null) {
			return false;
		}
		for (int i = 0; i < substring.length(); i++) {
			int j = index + i;
			if (j >= str.length() || str.charAt(j) != substring.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符非正则替换
	 * 
	 * @param source
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static String replace(String source, String oldValue, String newValue) {
		if (isEmpty(source) || isEmpty(oldValue) || newValue == null) {
			return source;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = source.indexOf(oldValue);
		int oldLength = oldValue.length();
		while (index >= 0) {
			sb.append(source.substring(pos, index));
			sb.append(newValue);
			pos = index + oldLength;
			index = source.indexOf(oldValue, pos);
		}
		sb.append(source.substring(pos));
		return sb.toString();
	}

	/**
	 * 将 通配符 按顺序替换成 object 数组
	 * 
	 * @param source
	 * @param placeholder
	 * @param objects
	 * @return
	 */
	public static String placeholderReplace(String source, String placeholder, Object[] objects) {
		if (isEmpty(source) || isEmpty(placeholder)) {
			return source;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int index = source.indexOf(placeholder);
		int oldLen = placeholder.length();
		int counter = 0;
		while (index >= 0) {
			sb.append(source.substring(pos, index));
			sb.append(String.valueOf(objects[counter++]));
			pos = oldLen + index;
			index = source.indexOf(placeholder, pos);
		}
		sb.append(source.substring(pos));
		return sb.toString();
	}

	/**
	 * 问号通配符替换
	 * 
	 * @param source
	 * @param objects
	 * @return
	 */
	public static String questionMarkReplace(String source, Object... objects) {
		return placeholderReplace(source, "?", objects);
	}

	/**
	 * 删掉 源字符中 所以的 指定字符
	 * 
	 * @param source
	 * @param chars
	 * @return
	 */
	public static String deleteChar(String source, String chars) {
		if (isEmpty(source) || isEmpty(chars)) {
			return source;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (chars.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static String headLetter(String source, boolean capital) {
		if (isEmpty(source)) {
			return source;
		}
		StringBuilder sb = new StringBuilder();
		if (capital) {
			sb.append(Character.toUpperCase(source.charAt(0)));
		} else {
			sb.append(Character.toLowerCase(source.charAt(0)));
		}

		sb.append(source.substring(1));
		return sb.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param source
	 * @param capital
	 * @return
	 */
	public static String headLetterUpper(String source) {
		return headLetter(source, true);
	}

	/**
	 * 首字母小写
	 * 
	 * @param source
	 * @param capital
	 * @return
	 */
	public static String headLetterLower(String source) {
		return headLetter(source, false);
	}

	/**
	 * 将byte 数组 打印成十六进制 字符 用指定 链接符 链接
	 * 
	 * @param source
	 * @param capital
	 * @return
	 */
	public static String toHexString(byte[] bytes, String decollator) {
		if (bytes == null || bytes.length < 1) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < bytes.length; i++) {
			if (first) {
				first = false;
			} else {
				sb.append(decollator);
			}
			int a = bytes[i] & 0xff;
			String str = Integer.toHexString(a);
			if (str.length() > 1) {
				sb.append(str);
			} else {
				sb.append("0" + str);
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符 转化成unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnicode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append("\\u" + Integer.toHexString(str.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * 是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChineseCharacter(char c) {
		int i = (int) c;
		return (i >= 0x4e00 && i <= 0x9fa5);
	}
}
