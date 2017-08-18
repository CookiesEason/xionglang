package com.sunxl.privates.controller;

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

import com.sunxl.base.entity.Role;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.service.RoleService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月20日 @此类作用：管理角色
 */
@Controller
@RequestMapping(value = "/private/role")
public class PrivateRoleController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateRoleController.class);
	private static final String OBJECTINFO = "角色";
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/viewRoleJson")
	public String goRole(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("name", "菜单名"));
		searchParamList.add(new FieldForm("url", "菜单路径"));
		searchParamList.add(new FieldForm("function", "菜单对应的方法"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/role", Page.create(), "#idRoleForm", PrivateRoleController.OBJECTINFO);
			return "private.role.list";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String goRoleList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridString(request, roleService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewRole(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, roleService, "private.role.view", id, "role");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addRole(HttpServletRequest request) {
		return "private.role.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addRole(HttpServletRequest request, Role role) {
		return PrivateBaseMethodUtil.addObject(roleService, role, PrivateRoleController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteRole(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(roleService, id, PrivateRoleController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateRole(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, roleService, "private.role.update", id, "role");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateRole(HttpServletRequest request, Role role) {
		return PrivateBaseMethodUtil.updateObject(roleService, role, PrivateRoleController.OBJECTINFO);
	}
}
