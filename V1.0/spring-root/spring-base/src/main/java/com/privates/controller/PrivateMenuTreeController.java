package com.privates.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.form.Token;
import com.base.util.WebKeys;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日
 * @此类作用：构建菜单树
 */
@Controller
@RequestMapping(value = "/private/menutree")
public class PrivateMenuTreeController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateMenuTreeController.class);

	@RequestMapping(value = "/home")
	public String home(Model model, HttpSession session) {
		try {
			if (session.getAttribute(WebKeys.ADMINUSER_KEY) != null)
				model.addAttribute("menuTree", ((Token) session.getAttribute(WebKeys.TOKEN)).getViews());
			else
				return "redirect:/admin/login";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"请先登录");
			return "redirect:/admin/login";
		}
		return "easyUIPrivateLayout";
	}
}
