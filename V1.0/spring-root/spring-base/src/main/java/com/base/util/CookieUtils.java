package com.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日
 * @此类作用：
 */
public class CookieUtils {

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	// public static void removeAllCookies(String str) {
	// Cookie cookies[] = ServletActionContext.getRequest().getCookies();
	// if (cookies == null || cookies.length < 0) {
	// // 没有cookie
	// System.out.println("there is no any cookie ..");
	// } else {
	// System.out.println("开始删除cookies..");
	// for (Cookie c : cookies) {
	// if (c.getName().equals(str)) {
	// c.setMaxAge(0);// 设置为0
	// ServletActionContext.getResponse().addCookie(c);
	// }
	// }
	// }
	// }
}
