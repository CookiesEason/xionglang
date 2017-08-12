package com.sunxl.base.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunxl.base.entity.AdminUser;
import com.sunxl.base.form.Token;
import com.sunxl.base.service.AdminUserService;
import com.sunxl.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：后台用户登陆拦截器
 */
public class AdminPowerInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static Map<String, int[]> locked = new HashMap<String, int[]>();

	@Autowired
	private AdminUserService adminUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {// HandlerMethod
		HttpSession session = request.getSession();
		AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
		if (adminUser == null) {
			response.sendRedirect(request.getContextPath() + "/admin/login");
			return false;
		}
		if (handler instanceof HandlerMethod) {
			Token token = (Token) session.getAttribute(WebKeys.TOKEN);
			HandlerMethod hm = (HandlerMethod) handler;
			String key = hm.getBeanType().getSimpleName() + "." + hm.getMethod().getName();
			int[] roles = locked.get(key);
			if (roles != null && !token.canAccess(roles)) {
				response.sendError(400, "no power");
				return false;
			}

		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setMap(adminUserService.initRoleMapped());
	}

	public static void setMap(Map<String, int[]> locked) {
		AdminPowerInterceptor.locked = locked;
	}

}
