package com.sunxl.privates.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Controller
@RequestMapping(value = "/private")
public class PrivateHomeController {

	@RequestMapping(value = { "", "/" })
	public String home(Model model, HttpServletRequest request) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "private.home";
	}
}
