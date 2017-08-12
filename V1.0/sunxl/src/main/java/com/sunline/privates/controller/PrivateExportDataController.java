package com.sunline.privates.controller;

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

import com.sunxl.base.entity.ExportData;
import com.sunxl.base.form.EasyUIDataGridForm;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.service.ExportDataService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/exportData")
public class PrivateExportDataController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateExportDataController.class);
	private static final String OBJECTINFO = "卸数方案";
	@Autowired
	private ExportDataService exportDataService;

	@RequestMapping(value = "/viewExportDataJson")
	public String goExportData(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/exportData", Page.create(), "#idExportDataForm", PrivateExportDataController.OBJECTINFO);
			return "private.exportData.list";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goExportDataList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, exportDataService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	
	public String viewExportData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, exportDataService, "private.exportData.view", id, "exportData");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addExportData(HttpServletRequest request) {
		return "private.exportData.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addExportData(HttpServletRequest request, ExportData exportData) {
		return PrivateBaseMethodUtil.addObject(exportDataService, exportData, PrivateExportDataController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteExportData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(exportDataService, id, PrivateExportDataController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateExportData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, exportDataService, "private.exportData.update", id, "exportData");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateExportData(HttpServletRequest request, ExportData exportData) {
		return PrivateBaseMethodUtil.updateObject(exportDataService, exportData, PrivateExportDataController.OBJECTINFO);
	}
}
