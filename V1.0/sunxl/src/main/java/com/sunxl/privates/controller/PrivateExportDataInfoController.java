package com.sunxl.privates.controller;

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

import com.sunxl.base.entity.ExportDataInfo;
import com.sunxl.base.form.EasyUIDataGridForm;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.service.ExportDataInfoService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/exportDataInfo")
public class PrivateExportDataInfoController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateExportDataInfoController.class);
	private static final String OBJECTINFO = "卸数配置";
	@Autowired
	private ExportDataInfoService exportDataInfoService;

	@RequestMapping(value = "/viewExportDataInfoJson")
	public String goExportInfo(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/exportDataInfo", Page.create(), "#idExportDataInfoForm", PrivateExportDataInfoController.OBJECTINFO);
			return "private.exportDataInfo.list";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goExportInfoList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, exportDataInfoService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewExportInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, exportDataInfoService, "private.exportDataInfo.view", id, "exportDataInfo");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addExportInfo(HttpServletRequest request) {
		return "private.exportDataInfo.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addExportInfo(HttpServletRequest request, ExportDataInfo exportDataInfo) {
		return PrivateBaseMethodUtil.addObject(exportDataInfoService, exportDataInfo, PrivateExportDataInfoController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteexportInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(exportDataInfoService, id, PrivateExportDataInfoController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateexportInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, exportDataInfoService, "private.exportDataInfo.update", id, "exportDataInfo");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateexportInfo(HttpServletRequest request, ExportDataInfo exportDataInfo) {
		return PrivateBaseMethodUtil.updateObject(exportDataInfoService, exportDataInfo, PrivateExportDataInfoController.OBJECTINFO);
	}
}
