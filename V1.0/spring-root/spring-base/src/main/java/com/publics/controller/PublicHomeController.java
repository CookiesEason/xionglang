package com.publics.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.interceptor.InitDataInterceptor;
import com.base.view.PublicMenuView;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Controller
@SuppressWarnings("unchecked")
public class PublicHomeController {
	private static final Logger logger = LoggerFactory.getLogger(PublicHomeController.class);

	/*
	 * @Autowired private UserService userService;
	 * 
	 * @Autowired private TestService testService;
	 */
	@RequestMapping(value = { "", "/" })
	public String home(HttpServletRequest request) {
		try {
			List<PublicMenuView> list = (List<PublicMenuView>) InitDataInterceptor.map.get("publicmenuService");
			request.setAttribute("publicmenu", list);
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
		}
		return "public.home";
	}
}
