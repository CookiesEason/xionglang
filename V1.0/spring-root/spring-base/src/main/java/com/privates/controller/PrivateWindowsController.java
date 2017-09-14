package com.privates.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.form.Token;
import com.base.interfaces.MethodLog;
import com.base.one.entity.AdminUser;
import com.base.one.entity.Role;
import com.base.one.entity.User;
import com.base.service.AdminUserService;
import com.base.service.RoleService;
import com.base.service.UserService;
import com.base.util.EncryptionUtil;
import com.base.util.JSON;
import com.base.util.WebKeys;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月18日 @此类作用：修改前台后台用户密码
 */
@Controller
public class PrivateWindowsController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateWindowsController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/password/{id}", method = RequestMethod.GET)
	@MethodLog(remark = "id为1，表示后台用户修改自己的密码，id为2，表示前台用户修改自己的密码，id为3，表示管理员修改前台用户密码，id为4，表示管理员修改后台用户密码")
	public String updatePassWord(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("passWordId", id);
		HttpSession session = request.getSession();
		if (id == 1 || id == 3 || id == 4) {
			AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
			if (adminUser == null) {// 检测是否登陆后台，如果没有，跳转到后台登陆页面
				response.sendRedirect(request.getContextPath() + "/admin/login");
			}
		} else {
			User user = (User) session.getAttribute(WebKeys.USER_KEY);
			if (user == null) {// 检测是否登陆前台，如果没有，跳转到前台登陆页面
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
		return "private.password";
	}

	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	@MethodLog(remark = "id为1，表示后台用户切换角色，id为2，表示前台用户切换角色")
	public String updateRole(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("roleId", id);
		HttpSession session = request.getSession();
		if (id == 1) {
			AdminUser adminUser = (AdminUser) session.getAttribute(WebKeys.ADMINUSER_KEY);
			if (adminUser == null) {// 检测是否登陆后台，如果没有，跳转到后台登陆页面
				response.sendRedirect(request.getContextPath() + "/admin/login");
			}
			request.setAttribute("adminUser", adminUser);
		} else {
			User user = (User) session.getAttribute(WebKeys.USER_KEY);
			if (user == null) {// 检测是否登陆前台，如果没有，跳转到前台登陆页面
				response.sendRedirect(request.getContextPath() + "/login");
			}
			request.setAttribute("user", user);
		}
		return "private.role";
	}

	@RequestMapping(value = "/private/update/password/{passWordId}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@MethodLog(remark = "修改密码")
	@ResponseBody
	public JSON updatePassWord(HttpServletRequest request, @PathVariable("passWordId") int passWordId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpSession httpSession) {
		JSON json = new JSON();
		String info = "";
		try {
			if (passWordId == 3 || passWordId == 1) {
				AdminUser adminUser = null;
				if (passWordId == 3) {
					adminUser = adminUserService.find(adminUserService, Integer.parseInt(request.getParameter("id")));
					if (adminUser != null) {
						adminUser.setPassWord(EncryptionUtil.byMD5(newPassword).toString());
						adminUserService.update(adminUserService, adminUser);
						json.setSuccess(true);
						info = "管理员修改后台用户：" + adminUser.getUserName() + "密码成功，新密码为：" + newPassword;
					} else {
						json.setSuccess(false);
						info = "修改失败，此ID的后台用户不存在";
					}
				}
				if (passWordId == 1) {
					adminUser = (AdminUser) httpSession.getAttribute(WebKeys.ADMINUSER_KEY);
					if (adminUser != null) {
						if (oldPassword != null && EncryptionUtil.byMD5(oldPassword).toString().equals(adminUser.getPassWord())) {
							adminUser.setPassWord(EncryptionUtil.byMD5(newPassword).toString());
							adminUserService.update(adminUserService, adminUser);
							json.setSuccess(true);
							info = "密码修改成功，新密码为：" + newPassword;
						} else {
							json.setSuccess(false);
							info = "修改失败，旧密码不正确";
						}
					} else {
						json.setSuccess(false);
						info = "Session已过期，请重新登录";
					}
				}
			}
			if (passWordId == 2 || passWordId == 4) {
				User user = null;
				if (passWordId == 4) {
					user = userService.find(userService, Integer.parseInt(request.getParameter("id")));
					if (user != null) {
						user.setPassWord(EncryptionUtil.bySHA1(newPassword));
						userService.update(userService, user);
						json.setSuccess(true);
						info = "管理员修改前台用户：" + user.getUserName() + "密码成功，新密码为：" + newPassword;
					} else {
						json.setSuccess(false);
						info = "修改失败，此ID的后台用户不存在";
					}
				}
				if (passWordId == 2) {
					user = (User) httpSession.getAttribute(WebKeys.USER_KEY);
					if (user != null) {
						if (EncryptionUtil.bySHA1(oldPassword).toString().equals(user.getPassWord())) {
							user.setPassWord(EncryptionUtil.bySHA1(newPassword));
							userService.update(userService, user);
							json.setSuccess(true);
							info = "密码修改成功，新密码为：" + newPassword;
						} else {
							json.setSuccess(false);
							info = "修改失败，旧密码不正确";
						}
					} else {
						json.setSuccess(false);
						info = "Session已过期，请重新登录";
					}
				}
			}
		} catch (Exception e) {
			info = e.getMessage();
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + info);
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}

	@RequestMapping(value = "/private/update/role/{roleId}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@MethodLog(remark = "修改角色")
	@ResponseBody
	public JSON updateRole(HttpServletRequest request, HttpServletResponse response, @PathVariable("roleId") int roleId, HttpSession httpSession) {
		JSON json = new JSON();
		String info = "";
		try {
			if (roleId == 1) {
				AdminUser adminUser = (AdminUser) httpSession.getAttribute(WebKeys.ADMINUSER_KEY);
				Role role = roleService.find(roleService, Integer.parseInt(request.getParameter("id")));
				adminUser.setRole(role);
				adminUserService.update(adminUserService, adminUser);
				Token token = new Token(adminUser);
				token = adminUserService.createLoginToken(token, role);
				request.getSession().setAttribute(WebKeys.TOKEN, token);
				info = "切换角色成功";
			} else {
				User user = (User) httpSession.getAttribute(WebKeys.USER_KEY);
				Role role = roleService.find(roleService, Integer.parseInt(request.getParameter("id")));
				user.setRole(role);
				userService.update(userService, user);
				info = "切换角色成功";
			}
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			info = e.getMessage();
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + info);
		}
		json.setMsg(info);
		return json;
	}

	@RequestMapping(value = "/window/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@MethodLog(remark = "1、前台锁定窗口2、前台退出登录；3、后台锁定窗口4、后台退出登录")
	@ResponseBody
	public JSON lockOrQuitWindows(HttpServletRequest request, HttpSession session, @PathVariable("id") int id) {
		JSON json = new JSON();
		try {
			if (id == 1 || id == 2)
				session.removeAttribute(WebKeys.USER_KEY);
			if (id == 3 || id == 4)
				session.removeAttribute(WebKeys.ADMINUSER_KEY);
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}

	@RequestMapping(value = "/window/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@MethodLog(remark = "1、前台用户信息2、后台用户信息")
	public String lockWindows(HttpServletRequest request, HttpSession session, @PathVariable("id") int id) {
		String path = "";
		try {
			if (id == 1) {
				request.setAttribute("user", session.getAttribute(WebKeys.USER_KEY));
				path = "public.user.info";
			}
			if (id == 2) {
				request.setAttribute("adminUser", session.getAttribute(WebKeys.ADMINUSER_KEY));
				path = "private.adminUser.info";
			}
			if (id == 5) {
				path = "private.lock.window";
			}
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
		return path;
	}
}
