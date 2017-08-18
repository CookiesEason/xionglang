package com.sunxl.base.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年7月6日
 * @此类作用：字符串工具类
 */
@SuppressWarnings({ "unused", "rawtypes" })
public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 判断指定字符串是否为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空。
	 */
	public static Boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 判断指定字符串是否不为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空。
	 */
	public static Boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断指定字符串是否为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空字符串。
	 */
	public static Boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定字符串是否不为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空字符串。
	 */
	public static Boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 截取指定分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符前的字符串内容。
	 */
	public static String substringBefore(String str, String separator) {
		AssertUtil.notEmpty(str);
		AssertUtil.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取最后一个分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符前的字符串内容。
	 */
	public static String substringBeforeLast(String str, String separator) {
		AssertUtil.notNull(str);
		AssertUtil.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取指定分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符后的字符串内容。
	 */
	public static String substringAfter(String str, String separator) {
		AssertUtil.notEmpty(str);
		AssertUtil.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取最后一个分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符后的字符串内容。
	 */
	public static String substringAfterLast(String str, String separator) {
		AssertUtil.notEmpty(str);
		AssertUtil.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取两个分隔符之间的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param startSeparator
	 *            开始分隔符
	 * @param endSeparator
	 *            结束分隔符
	 * @return 返回两个分隔符之间的字符串。
	 */
	public static String substringBetween(String str, String startSeparator, String endSeparator) {
		if (str == null || startSeparator == null || endSeparator == null) {
			return null;
		}
		int start = str.indexOf(startSeparator);
		if (start != -1) {
			int end = str.indexOf(endSeparator, start + startSeparator.length());
			if (end != -1) {
				return str.substring(start + startSeparator.length(), end);
			}
		}
		return null;
	}

	/**
	 * 截取指定起始位置和截取长度的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param pos
	 *            起始位置
	 * @param len
	 *            截取长度
	 * @return 返回指定起始位置和截取长度的字符串。
	 */
	public static String mid(String str, int pos, int len) {
		AssertUtil.notEmpty(str);
		AssertUtil.isTrue(pos >= 0 && pos <= str.length());
		AssertUtil.isTrue(len >= 0);

		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * 将一个字符串数组用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串。
	 */
	public static String join(String[] strs, String separator) {
		AssertUtil.notNull(strs);
		AssertUtil.notNull(separator);

		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str + separator);
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}

	/**
	 * 将一个字符串列表用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串数组。
	 */
	public static String join(List<String> strs, String separator) {
		return join(strs.toArray(new String[] {}), separator);
	}

	/**
	 * 对字符串进行字符集转换。
	 * 
	 * @param str
	 *            字符串
	 * @param origEncoding
	 *            原字符集编码
	 * @param destEncoding
	 *            新字符集编码
	 * @return 返回转换后的字符串。
	 */
	public static String encode(String str, String origEncoding, String destEncoding) {
		try {
			return new String(str.getBytes(origEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("对字符串进行字符集转换时发生异常", e);
		}
	}

	/**
	 * 格式化指定数字。
	 * 
	 * @param width
	 *            位数
	 * @param num
	 *            数字
	 * @return 格式化之后的值。
	 */
	private static String format(int width, long num) {
		char[] chs = new char[width];
		for (int i = 0; i < width; i++) {
			chs[i] = '0';
		}
		DecimalFormat df = new DecimalFormat(new String(chs));
		return df.format(num);
	}

	/**
	 * 
	 * 从十六进制字符串到字节数组转换
	 */
	public static byte[] hexString2Bytes(String hexString, String charSet) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 给调度使用，把调度的参数转化成map
	 * 
	 * @param str
	 * @return
	 */
	public static HashMap<String, String> String2Map(String str) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] paramList = str.split(";");
		for (int i = 0; i < paramList.length; i++) {
			String[] tmp = paramList[i].split("=");
			if (tmp != null && tmp.length > 0) {
				String key = tmp[0];
				String val = tmp[1];
				map.put(key, val);
			}
		}
		return map;
	}

	public static char[] convertByteToChar(byte[] source, int srclen) {
		if (source == null) {
			return null;
		}

		int len = source.length;
		if (len > srclen) {
			len = srclen;
		}
		char[] destChar = new char[len];
		for (int i = 0; i < len; i++) {
			if (source[i] >= 0) {
				destChar[i] = (char) source[i];
			} else {
				destChar[i] = (char) (256 + source[i]);
			}
		}
		return destChar;
	}

	public static byte[] convertCharToByte(char[] source, int srclen) {
		if (source == null) {
			return null;
		}

		int len = source.length;
		if (len > srclen) {
			len = srclen;
		}
		byte[] dest = new byte[len];
		for (int i = 0; i < len; i++) {
			dest[i] = (byte) source[i];
		}
		return dest;
	}

	public static final byte[] hexToBytes(String hex) {
		if (null == hex) {
			return new byte[0];
		}
		int len = hex.length();
		byte[] bytes = new byte[len / 2];
		String stmp = null;
		try {
			for (int i = 0; i < bytes.length; i++) {
				stmp = hex.substring(i * 2, i * 2 + 2);
				bytes[i] = (byte) Integer.parseInt(stmp, 16);
			}
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + "hex=[" + hex + "]"
					+ e.getMessage());
			return new byte[0];
		}
		return bytes;
	}

	public static final String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		String stmp = "";

		for (int i = 0; i < hash.length; i++) {
			stmp = (java.lang.Integer.toHexString(hash[i] & 0XFF));
			if (stmp.length() == 1) {
				buf.append(0).append(stmp);
			} else {
				buf.append(stmp);
			}
		}
		return buf.toString();
	}

	/**
	 * @Title: isObjNullOrEmpty
	 * @Description: 判断对象是否为null，或字符串是否为空串
	 * @param obj
	 * @return
	 */
	public static boolean isObjNullOrEmpty(Object obj) {
		if (obj == null || "".equals(obj.toString())) {
			return true;
		}
		if (obj instanceof Collection) {
			if (((Collection) obj).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 十六进制转字符串
	 * 
	 * @param hexStr
	 *            byte字符串（byte之间无分隔符 如 7C7C）
	 * @return 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * 将16进制字符串转为16进制形式
	 * 
	 * @param src
	 * @param Charset
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src, String Charset) {
		byte[] ret = new byte[src.length() / 2];
		try {
			byte[] tmp = src.getBytes(Charset);
			for (int i = 0; i < tmp.length / 2; i++) {
				ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static List<String> getContainOfLine(byte[] dst, String linefeed, String chartSet) {
		byte[] spd = StringUtil.hexStr2Byte(linefeed);
		int fidx = 0;
		List<String> lists = new ArrayList<String>();
		int idx = 0;
		for (idx = 0; idx < dst.length;) {
			byte[] sudst = Arrays.copyOfRange(dst, idx, idx + spd.length);
			if (Arrays.equals(spd, sudst)) {
				try {
					lists.add(new String(Arrays.copyOfRange(dst, fidx, idx), chartSet));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				idx = idx + spd.length;
				fidx = idx;
			} else {
				idx = idx + 1;
			}
		}

		// 处理最后一行没有换行标识的情况
		if (fidx < idx && idx == dst.length) {
			try {
				lists.add(new String(Arrays.copyOfRange(dst, fidx, idx), chartSet));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return lists;
	}

	public static List<String> getContainOfLine(byte[] dst, String linefeed, String chartSet, String splitStr) {
		byte[] spd = StringUtil.hexStr2Byte(linefeed);
		int fidx = 0;
		List<String> lists = new ArrayList<String>();
		String str = null;
		int idx = 0;
		for (idx = 0; idx < dst.length;) {
			byte[] sudst = Arrays.copyOfRange(dst, idx, idx + spd.length);
			if (Arrays.equals(spd, sudst)) {
				try {
					str = new String(Arrays.copyOfRange(dst, fidx, idx), chartSet);
					while (str.contains("?" + splitStr.substring(1, splitStr.length()))) {
						str = str.replace("?" + splitStr.substring(1, splitStr.length()), splitStr);
					}
					lists.add(str);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				idx = idx + spd.length;
				fidx = idx;
			} else {
				idx = idx + 1;
			}
		}

		// 处理最后一行没有换行标识的情况
		if (fidx < idx && idx == dst.length) {
			try {
				str = new String(Arrays.copyOfRange(dst, fidx, idx), chartSet);
				while (str.contains("?" + splitStr.substring(1, splitStr.length()))) {
					str = str.replace("?" + splitStr.substring(1, splitStr.length()), splitStr);
				}
				lists.add(str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return lists;
	}

	/**
	 * 十六进制转字符串
	 * 
	 * @param hexStr
	 *            byte字符串（byte之间无分隔符 如 7C7C）
	 * @return 对应的字符串
	 */
	public static byte[] hexStr2Byte(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return bytes;
	}
	/**
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 * @此方法的作用：字符串由UTF-8转ISO8859_1
	 */
	public static String strEncodeUnUTF(String s) throws UnsupportedEncodingException {
		s = new String(s.getBytes("UTF-8"), "ISO8859_1");
		return s;
	}
	
	/**
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 * @此方法的作用：字符串由ISO8859_1转UTF-8
	 */
	public static String strEncodeUTF(String s) throws UnsupportedEncodingException {
		s = new String(s.getBytes("ISO8859_1"), "UTF-8");
		return s;
	}
}
