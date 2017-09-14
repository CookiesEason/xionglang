package com.base.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.base.form.Token;
import com.base.one.entity.AdminUser;
import com.base.service.AdminUserService;
import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：后台用户登陆拦截器
 */
public class AdminPowerInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
	private final static Logger logger = LoggerFactory.getLogger(AdminPowerInterceptor.class);
	private static Map<String, int[]> locked = new HashMap<String, int[]>();

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @此方法的作用：执行前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {// HandlerMethod
		logger.warn("访问之前拦截");
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
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.warn("上面执行成功，访问之后，但又未返回试图层执行");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		logger.warn("访问之后，并返回试图层执行");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setMap(adminUserService.initRoleMapped());
		logger.warn("在servlet初始化拦截器之后执行一次");
	}

	public static void setMap(Map<String, int[]> locked) {
		AdminPowerInterceptor.locked = locked;
	}

}
