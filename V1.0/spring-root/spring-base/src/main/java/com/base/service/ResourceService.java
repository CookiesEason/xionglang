package com.base.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.base.generic.service.GenericService;
import com.base.one.entity.Resource;
import com.base.one.entity.User;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
public interface ResourceService extends GenericService<Resource> {

	public int uploadFile(User user, String echoMethod, MultipartFile file, HttpServletRequest request);
}
