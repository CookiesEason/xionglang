package com.sunxl.publics.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PublicHomeController {
	private static final Logger logger = LoggerFactory.getLogger(PublicHomeController.class);
	/*@Autowired
	private UserService userService;
	@Autowired
	private TestService testService;*/

	@RequestMapping(value = { "", "/" })
	public String home(Model model, HttpServletRequest request) {
		try {

		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
		}
		return "public.home";
	}
}
