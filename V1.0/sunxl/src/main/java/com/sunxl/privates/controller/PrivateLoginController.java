package com.sunxl.privates.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunxl.base.entity.AdminUser;
import com.sunxl.base.entity.Role;
import com.sunxl.base.form.Token;
import com.sunxl.base.service.AdminUserService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/admin")
public class PrivateLoginController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateLoginController.class);

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginInfo(Locale locale, Model model) {
		return "private.login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON login(@ModelAttribute("adminUser") AdminUser adminUser, HttpServletRequest request, HttpServletResponse response) {
		JSON json = new JSON();
		String info = "";
		try {
			if (adminUser != null) {
				adminUser = adminUserService.adminLogin(adminUser);
				if (adminUser != null) {
					request.getSession().setAttribute(WebKeys.ADMINUSER_KEY, adminUser);
					json.setSuccess(true);
					Role role = null;
					if (adminUser.getRole() != null)
						role = adminUser.getRole();// 存在登陆的角色就使用默认的角色
					else {
						if (!adminUser.getRoles().isEmpty()) {// 不存在就查询所有的角色，之后使用第一个，并同步更新第一个角色为默认使用角色
							role = adminUser.getRoles().iterator().next();
							adminUser.setRole(role);
							adminUserService.update(adminUser);
						}
					}
					Token token = new Token(adminUser);
					if (role != null) {
						token = adminUserService.createLoginToken(token, role);
						request.getSession().setAttribute(WebKeys.TOKEN, token);
					} else {
						info = "用户没有分配角色，请先请管理员分配角色";
						request.getSession().setAttribute(WebKeys.TOKEN, token);// 刷新当前电脑的缓存菜单
					}
					json.setSuccess(true);
				} else {
					json.setSuccess(false);
					info = "用户名或密码错误";
				}
			} else {
				json.setSuccess(false);
				info = "请输入用户名密码";
			}
		} catch (Exception e) {
			info = e.getMessage();
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+info);
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}

	@RequestMapping(value = "/adminUser-label")
	public String loadLabel(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(WebKeys.ADMINUSER_KEY);
		if (obj != null) {
			request.setAttribute("ADMINUSER_LABEL", (AdminUser) obj);
		}
		return "adminUser.label";
	}

}
