package com.sunxl.base.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.sunxl.base.entity.Resource;
import com.sunxl.base.entity.User;
import com.sunxl.base.generic.service.GenericService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
public interface ResourceService extends GenericService<Resource> {

	public int uploadFile(User user, String echoMethod, MultipartFile file, HttpServletRequest request);
}
