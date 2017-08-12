package com.sunline.privates.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunline.base.interfaces.FilterField;
import com.sunxl.base.entity.AdminUser;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.service.AdminUserService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/adminUser")
public class PrivateAdminUserController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateAdminUserController.class);
	private static final String OBJECTINFO = "后台用户";
	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/viewAdminUserJson")
	public String goAdminUser(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/adminUser", Page.create(), "#idAdminUserForm", PrivateAdminUserController.OBJECTINFO);
			return "private.adminUser.list";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@FilterField(value = { "role_adminUsers", "dept_company", "dept_dept", "dept_user", "roles_menus", "roles_adminUsers", "roles_users" })
	public String goAdminUserList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridString(request, adminUserService, false);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewAdminUser(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, adminUserService, "private.adminUser.view", id, "adminUser");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addAdminUser(HttpServletRequest request) {
		return "private.adminUser.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addAdminUser(HttpServletRequest request, AdminUser adminUser) {
		return PrivateBaseMethodUtil.addObject(adminUserService, adminUser, PrivateAdminUserController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteAdminUser(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(adminUserService, id, PrivateAdminUserController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateAdminUser(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, adminUserService, "private.adminUser.update", id, "adminUser");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateExportData(HttpServletRequest request, AdminUser adminUser) {
		return PrivateBaseMethodUtil.updateObject(adminUserService, adminUser, PrivateAdminUserController.OBJECTINFO);
	}
}
