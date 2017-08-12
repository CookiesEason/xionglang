package com.sunxl.base.service.impl;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sunxl.base.dao.ResourceDao;
import com.sunxl.base.entity.Resource;
import com.sunxl.base.entity.User;
import com.sunxl.base.generic.service.impl.GenericServiceImpl;
import com.sunxl.base.service.ResourceService;
import com.sunxl.base.util.FileMaker;
import com.sunxl.base.util.ThumbnailsUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日 @此类作用：
 */
@Service
public class ResourceServiceImpl extends GenericServiceImpl<Resource> implements ResourceService {
	private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void setGenericDao() {
		super.genericDao = resourceDao;
	}

	@Override
	@Transactional
	public void delete(Object id) {
		resourceDao.delete(id);
	}

	@Override
	@Transactional
	public Resource create(Resource r) {
		return resourceDao.create(r);
	}

	@Override
	@Transactional
	public Resource update(Resource entity) {
		return resourceDao.update(entity);
	}

	@Override
	@Transactional
	public int uploadFile(User user, String echoMethod, MultipartFile file, HttpServletRequest request) {
		Resource resource = null;
		try {
			if (echoMethod.endsWith("Excel")) {
				File file1 = FileMaker.getExcelDirectory();
				resource = ThumbnailsUtil.uploadFile(file, file1.getAbsolutePath().toString());
				resource.setFileName(file.getOriginalFilename());
				resource.setResourcePath(file1.getAbsolutePath().toString());
				resource.setUploadDate(new Date());
				resource.setOtherId(user.getId());
			} else if (echoMethod.endsWith("IMG")) {
				resource = ThumbnailsUtil.newCreateImage(file.getOriginalFilename(), file.getBytes());
			} else {
				resource = ThumbnailsUtil.newCreateImage(file.getOriginalFilename(), file.getBytes());
				resource.setModel(echoMethod);
				resource.setOtherId(user.getId());
			}
			resourceDao.create(resource);
			return resource.getId();
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"添加图片出错" + e.getMessage());
		}
		return -1;
	}

}
