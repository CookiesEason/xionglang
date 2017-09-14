package com.base.util;

import java.util.ArrayList;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class URLDencode {
	public static String encode(HttpServletRequest request, String str) throws Exception {
		String sessionId = DatetimeUtil.getNow("yyyyMMdd").substring(6) + request.getRemoteAddr() + "1234567890";

		byte[] key = sessionId.substring(0, 8).getBytes();
		return StringUtil.toHex(DesUtil.decode(str.getBytes(), key));
	}

	public static String decode(HttpServletRequest request, String str) throws Exception {
		String sessionId = DatetimeUtil.getNow("yyyyMMdd").substring(6) + request.getRemoteAddr() + "1234567890";

		byte[] key = sessionId.substring(0, 8).getBytes();
		return new String(DesUtil.decode(StringUtil.hexToBytes(str), key));
	}

	public static String encodeByKey(String keyStr, String str) throws Exception {
		byte[] key = keyStr.getBytes();
		return StringUtil.toHex(DesUtil.decode(str.getBytes(), key));
	}

	public static String decodeByKey(String keyStr, String str) throws Exception {
		byte[] key = keyStr.getBytes();
		return new String(DesUtil.decode(StringUtil.hexToBytes(str), key));
	}

	public static String encodeUrl(HttpServletRequest request, String linkurl) {
		ArrayList CONFIG_PARAMS = new ArrayList();
		CONFIG_PARAMS.add("sysName=");
		CONFIG_PARAMS.add("oprID=");
		CONFIG_PARAMS.add("actions=");

		StringTokenizer st = new StringTokenizer(linkurl, "&");
		StringBuffer result = new StringBuffer("");
		int count = 1;
		while (st.hasMoreTokens()) {
			String field = st.nextToken();
			for (int i = 0; i < CONFIG_PARAMS.size(); ++i) {
				String param = (String) CONFIG_PARAMS.get(i);
				if (field.indexOf(param) >= 0) {
					try {
						field = field.substring(0, field.indexOf(param) + param.length()) + encode(request, field.substring(field.indexOf(param) + param.length()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			if (count > 1)
				result.append("&");
			result.append(field);
			++count;
		}
		return result.toString();
	}
}
