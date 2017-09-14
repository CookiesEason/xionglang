package com.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.xfire.util.Base64;

import com.base.one.entity.User;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public class UserCookieUtil {

	// 保存cookie时的cookieName
	private final static String cookieDomainName = "sunxl.user";

	// 加密cookie时的网站自定码
	private final static String webKey = "sunxl.user";

	// 设置cookie有效期是两个星期，根据需要自定义
	private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;

	/**
	 * 保存Cookie到客户端 在CheckLogonServlet.java中被调用传递进来的user对象中封装了在登陆时填写的用户名与密码
	 */
	public static void saveCookie(User user, HttpServletResponse response) {
		// cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);
		// MD5加密用户详细信息
		String cookieValueWithMd5 = getMD5(user.getUserName() + ":" + user.getPassWord() + ":" + validTime + ":" + webKey);
		// 将要被保存的完整的Cookie值
		String cookieValue = user.getUserName() + ":" + validTime + ":" + cookieValueWithMd5;
		// 再一次对Cookie的值进行BASE64编码
		String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
		// 开始保存Cookie
		Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
		// 存两年(这个值应该大于或等于validTime)
		cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
		// cookie有效路径是网站根目录
		cookie.setPath("/");
		// 向客户端写入
		response.addCookie(cookie);
	}

	/**
	 * 用户注销时,清除Cookie,在需要时可随时调用------------------------------------------------
	 * ------------
	 */
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 获取Cookie组合字符串的MD5码的字符串--------------------------------------------------
	 * --------------------------
	 */
	public static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * 将传递进来的字节数组转换成十六进制的字符串形式并返回
	 */
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

}
