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

import com.sunxl.base.entity.SysData;
import com.sunxl.base.form.EasyUIDataGridForm;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.service.SysDataService;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/sysData")
public class PrivateSysDataController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateSysDataController.class);
	private static final String OBJECTINFO = "数据字典";
	@Autowired
	private SysDataService sysDataService;

	@RequestMapping(value = "/viewSysDataJson")
	public String goSysData(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/sysData", Page.create(), "#idSysDataForm", PrivateSysDataController.OBJECTINFO);
			return "private.sysData.list";
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goSysDataList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, sysDataService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewSysData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, sysDataService, "private.sysData.view", id, "sysData");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addSysData(HttpServletRequest request) {
		return "private.sysData.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addSysData(HttpServletRequest request, SysData sysData) {
		return PrivateBaseMethodUtil.addObject(sysDataService, sysData, PrivateSysDataController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteSysData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(sysDataService, id, PrivateSysDataController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateSysData(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, sysDataService, "private.sysData.update", id, "sysData");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateSysData(HttpServletRequest request, SysData sysData) {
		return PrivateBaseMethodUtil.updateObject(sysDataService, sysData, PrivateSysDataController.OBJECTINFO);
	}
}
