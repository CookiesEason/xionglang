package com.sunxl.base.util;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：
 */
public class NumberUtil {

	/***
	 * 将字符串变成整形
	 * 
	 * @param value
	 *            源
	 * @param defaultInt
	 *            变成整形时若抛出异常则返回默认值
	 * @return
	 */
	public static Boolean toBoolean(String value, boolean defaultBoolean) {
		Boolean result = false;
		try {
			result = Boolean.getBoolean(value);
		} catch (NumberFormatException e) {
			result = defaultBoolean;
		}
		return result;
	}
	
	public static Byte toByte(String value, byte defaultByte) {
		Byte result = 0;
		try {
			result = Byte.parseByte(value);
		} catch (NumberFormatException e) {
			result = defaultByte;
		}
		return result;
	}
	
	public static Long toLong(String value, long defaultLong) {
		Long result = 0L;
		try {
			result = Long.parseLong(value);
		} catch (NumberFormatException e) {
			result = defaultLong;
		}
		return result;
	}
	public static Float toFloat(String value, float defaultFloat) {
		Float result = 0F;
		try {
			result = Float.parseFloat(value);
		} catch (NumberFormatException e) {
			result = defaultFloat;
		}
		return result;
	}
	public static Double toDouble(String value, double defaultDouble){
		Double result = 0D;
		try {
			result = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			result = defaultDouble;
		}
		return result;
	}
	public static Integer toInt(String value, int defaultInt) {
		Integer result = 0;
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			result = defaultInt;
		}
		return result;
	}
}
