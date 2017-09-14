package com.zookeeper.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.form.EasyUIDataGridForm;
import com.base.form.FieldForm;
import com.base.form.ViewInfoForm;
import com.base.util.JSON;
import com.base.util.PrivateBaseMethodUtil;
import com.zookeeper.entity.ImportDataColm;
import com.zookeeper.service.ImportDataColmService;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/importDataColm")
public class PrivateImportDataColmController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateImportDataColmController.class);
	private static final String OBJECTINFO = "导数表字段";
	@Autowired
	private ImportDataColmService importDataColmService;

	@RequestMapping(value = "/viewImportDataColmJson")
	public String goImportDataColm(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			ViewInfoForm viewInfoForm = new ViewInfoForm(searchParamList, "/private/importDataColm", "idImportDataColmForm", PrivateImportDataColmController.OBJECTINFO);
			PrivateBaseMethodUtil.goObjectViewList(request, viewInfoForm);
			return "private.importDataColm.list";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goImportDataColmList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, importDataColmService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewImportDataColm(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, importDataColmService, "private.importDataColm.view", id, "importDataColm");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addImportDataColm(HttpServletRequest request) {
		return "private.importDataColm.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addImportDataColm(HttpServletRequest request, ImportDataColm importDataColm) {
		return PrivateBaseMethodUtil.addObject(importDataColmService, importDataColm, PrivateImportDataColmController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteImportDataColm(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(importDataColmService, id, PrivateImportDataColmController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateImportDataColm(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, importDataColmService, "private.importDataColm.update", id, "importDataColm");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateImportDataColm(HttpServletRequest request, ImportDataColm importDataColm) {
		return PrivateBaseMethodUtil.updateObject(importDataColmService, importDataColm, PrivateImportDataColmController.OBJECTINFO);
	}
}
