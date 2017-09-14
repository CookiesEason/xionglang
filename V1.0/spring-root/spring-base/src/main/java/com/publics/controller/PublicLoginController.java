package com.publics.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月18日
 * @此类作用：
 */
@Controller
@RequestMapping(value = "/login")
public class PublicLoginController {
	private static final Logger logger = LoggerFactory.getLogger(PublicLoginController.class);

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String loginInfo(Locale locale, Model model) {
		logger.info(" goLogin has work ");
		return "public.login";
	}
}
