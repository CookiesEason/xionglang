package com.base.jsoup.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.form.JsoupForm;
import com.base.interfaces.MethodLog;
import com.base.util.JSON;
import com.base.util.JsoupUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月2日
 * @此类的作用：JSOUP爬虫的实现
 */
@Controller
@RequestMapping(value = "/jsoup")
public class BaseJsoupController {

	@RequestMapping(value = "/url", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	@MethodLog(remark = "直接访问url路径")
	public JSON goJsoupForUrl(HttpServletRequest request, JsoupForm jsoupForm) {
		Document doc = null;
		try {
			if (jsoupForm.getUrl() != null && !"".equals(jsoupForm.getUrl())) {
				if (jsoupForm.getFilePath() != null && !"".equals(jsoupForm.getFilePath())) {
					Jsoup.parse(new File(jsoupForm.getFilePath()), "UTF-8", jsoupForm.getUrl());
				} else {
					Connection conn = Jsoup.connect(request.getParameter("url"));
					if (request.getAttribute("userAgent") != null && !"".equals(request.getAttribute("userAgent")))
						conn.userAgent(request.getParameter("userAgent"));
					conn.timeout(jsoupForm.getTimeOut() == 0 ? 3000 : jsoupForm.getTimeOut());
					if (jsoupForm.getMethod() == null || "".equals(jsoupForm.getMethod()))
						doc = conn.get();
					else
						doc = conn.post();
				}
				request.setAttribute("elementString", JsoupUtil.analysisDocument(doc));
			}
		} catch (IOException e) {
		}
		return null;
	}
}
