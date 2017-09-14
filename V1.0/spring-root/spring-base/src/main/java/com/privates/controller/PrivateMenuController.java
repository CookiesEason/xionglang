package com.privates.controller;

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

import com.base.form.EasyUIDataGridForm;
import com.base.form.FieldForm;
import com.base.form.ViewInfoForm;
import com.base.one.entity.Menu;
import com.base.service.MenuService;
import com.base.util.ButtonUtil;
import com.base.util.JSON;
import com.base.util.PrivateBaseMethodUtil;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月7日 @此类作用： 管理菜单
 */
@Controller
@RequestMapping(value = "/private/menu")
public class PrivateMenuController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateMenuController.class);
	private static final String OBJECTINFO = "菜单";
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/viewMenuJson")
	public String goMenu(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("name", "菜单名"));
		searchParamList.add(new FieldForm("url", "菜单路径"));
		searchParamList.add(new FieldForm("function", "菜单对应的方法"));
		try {
			ButtonUtil button = new ButtonUtil("刷新缓存和Session", "/initData/refreshData");
			ViewInfoForm viewInfoForm = new ViewInfoForm(searchParamList, "/private/menu", "idMenuForm", PrivateMenuController.OBJECTINFO);
			viewInfoForm.setListButton(button);
			PrivateBaseMethodUtil.goObjectViewList(request, viewInfoForm);
			return "private.menu.list";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goMenuList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, menuService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewMenu(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, menuService, "private.menu.view", id, "menu");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addMenu(HttpServletRequest request) {
		try {
			PrivateBaseMethodUtil.requestSetRole(request);
			PrivateBaseMethodUtil.requestSetMenu(request);
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + "强转出错" + e.getMessage());
			return "private.error";
		}
		return "private.menu.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addMenu(HttpServletRequest request, Menu menu) {
		return PrivateBaseMethodUtil.addObject(menuService, menu, PrivateMenuController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteMenu(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(menuService, id, PrivateMenuController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateMenu(@PathVariable("id") int id, HttpServletRequest request) {
		PrivateBaseMethodUtil.requestSetMenu(request);
		PrivateBaseMethodUtil.requestSetRole(request);
		return PrivateBaseMethodUtil.viewObject(request, menuService, "private.menu.update", id, "menu");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateMenu(HttpServletRequest request, Menu menu) {
		return PrivateBaseMethodUtil.updateObject(menuService, menu, PrivateMenuController.OBJECTINFO);
	}

}
