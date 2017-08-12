package com.sunxl.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sunline.base.interfaces.MethodLog;
import com.sunxl.base.entity.User;
import com.sunxl.base.service.ResourceService;
import com.sunxl.base.util.WebKeys;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn @Time：2017年7月13日 @此类作用：
 */
@Controller
public class UploadFyController {
	private static final Logger logger = LoggerFactory.getLogger(UploadFyController.class);
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "/show/upload")
	public String showUpload(@RequestParam("echoMethod") String echoMethod, Model model) {
		model.addAttribute("echoMethod", echoMethod);
		return "show.upload";
	}

	@RequestMapping(value = "/newUpload")
	@MethodLog(remark = "当文件上传成功后，返回上传成功的id")
	@ResponseBody
	public String upload(@RequestParam("echoMethod") String echoMethod, MultipartFile file, HttpSession session, HttpServletRequest request) {
		try {
			Integer index = 0;
			User user = (User) session.getAttribute(WebKeys.USER_KEY);
			index = resourceService.uploadFile(user, echoMethod, file, request);
			return index.toString();
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.toString());
		}
		return "0";
	}
}
