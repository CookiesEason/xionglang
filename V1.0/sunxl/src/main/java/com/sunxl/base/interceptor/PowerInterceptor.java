package com.sunxl.base.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.itextpdf.text.pdf.codec.Base64;
import com.sunxl.base.entity.User;
import com.sunxl.base.service.UserService;
import com.sunxl.base.util.UserCookieUtil;
import com.sunxl.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：前台用户登陆拦截器
 */
@SuppressWarnings("unused")
public class PowerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(PowerInterceptor.class);
	private final static String cookieDomainName = "sunxl.user";
	@Autowired
	private UserService userService;
	private static Set<String> excludeUrl = null;
	{
		excludeUrl = new HashSet<String>();
		excludeUrl.add("/personal");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(WebKeys.USER_KEY);

		String url = request.getRequestURI().toString();
		if (user == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				String cookieValue = null;
				for (int i = 0; i < cookies.length; i++) {
					if (cookieDomainName.equals(cookies[i].getName())) {
						cookieValue = cookies[i].getValue();
						break;
					}
				}
				// 如果cookieValue为空 执行其他
				if (cookieValue == null) {
					if (url.contains("personal")) {
						response.sendRedirect(request.getContextPath() + "/login");
						return false;
					}
				} else {
					// 先得到的CookieValue进行Base64解码
					String cookieValueAfterDecode = new String(Base64.decode(cookieValue), "UTF-8");
					// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
					String cookieValues[] = cookieValueAfterDecode.split(":");
					if (cookieValues.length != 3) {
						response.sendRedirect(request.getContextPath() + "/login");
						return false;
					}
					// 判断是否在有效期内,过期就删除Cookie
					long validTimeInCookie = new Long(cookieValues[1]);
					if (validTimeInCookie < System.currentTimeMillis()) {
						// 删除Cookie
						UserCookieUtil.clearCookie(response);
						response.sendRedirect(request.getContextPath() + "/login");
						return false;
					}
					// 取出cookie中的用户名,并到数据库中检查这个用户名,
					String username = cookieValues[0];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", username);
					User temp = userService.find(userService, map);
					// 如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
					if (temp != null) {
						String md5ValueInCookie = cookieValues[2];
						String md5ValueFromUser = UserCookieUtil.getMD5(temp.getUserName() + ":" + temp.getPassWord() + ":" + validTimeInCookie + ":" + cookieDomainName);
						// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
						if (md5ValueFromUser.equals(md5ValueInCookie)) {
							session.setAttribute(WebKeys.USER_KEY, temp);
							response.sendRedirect(request.getContextPath() + "/login");
							return false;
						}
					}
				}
			}
			if (url.contains("personal")) {
				response.sendRedirect(request.getContextPath() + "/login");
				return false;
			}
		} else {
			if (url.contains("personal") && url.contains("login")) {
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}
}
