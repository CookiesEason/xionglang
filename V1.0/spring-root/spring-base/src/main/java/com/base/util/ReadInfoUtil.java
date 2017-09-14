package com.base.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年8月8日
 * @此类作用：读取数据
 */
public class ReadInfoUtil {

	/**
	 * 根据指定的编码和分隔符读取一行数据
	 * 
	 * @param message
	 * @param chartSet
	 * @param splitChar
	 * @return
	 * @throws UnsupportedEncodingException
	 * @auto：熊浪 @Time：2017年8月8日 @此方法的作用：
	 */
	public static List<String> readLine(String message, String chartSet, String splitChar) throws UnsupportedEncodingException {
		// 保存字节，便于转换为字符串
		// 注意这里最大支持4k的字符串，请根据需要修改
		ByteBuffer bf = ByteBuffer.allocate(4096);
		List<String> list = new LinkedList<String>();
		// 文件读取的字节缓冲区
		byte[] bs = new byte[1024];
		bs = message.getBytes(chartSet);
		int pointer = 0;
		byte[] tag = StringUtil.HexString2Bytes(splitChar, chartSet);
		byte[] messageSubstr = new byte[tag.length];
		int length = tag.length;
		// 读取数据
		while (pointer < bs.length) {
			if (bs.length - pointer < length) {
				length = bs.length - pointer;
			}
			System.arraycopy(bs, pointer++, messageSubstr, 0, length);
			if (Arrays.equals(messageSubstr, tag)) {
				list.add(new String(bf.array(), 0, bf.position(), chartSet));
				bf.clear();
				pointer = pointer + tag.length - 1;
			} else if (messageSubstr[0] != '\r') {
				bf.put(messageSubstr[0]);
			}
		}
		list.add(new String(bf.array(), 0, bf.position(), chartSet));
		return list;
	}

	/**
	 * 把一个字符串中的 低序位 ASCII 字符 替换成 -字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceLowOrderASCIICharacters(String message) {
		if (StringUtil.isEmpty(message))
			return "";
		StringBuilder info = new StringBuilder();
		StringBuilder note = new StringBuilder();
		boolean flag = false;
		for (int i = 0, len = message.length(); i < len; i++) {
			char c = message.charAt(i);
			int s = (int) c;
			if (((s >= 0) && (s <= 8)) || ((s >= 11) && (s <= 12)) || ((s >= 14) && (s <= 32))) {
				flag = true;
				info.append(String.format("-", s));
			} else
				info.append(c);
		}
		if (flag) {
			note.append("将不可打印字符转换为【-】，").append(info);
		} else {
			note.append(info);
		}
		return note.toString();
	}
}
