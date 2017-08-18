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

import com.sun.mail.iap.ConnectionException;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.entity.ImportDataInfo;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.interfaces.MethodLog;
import com.sunxl.base.service.ImportDataColmService;
import com.sunxl.base.service.ImportDataInfoService;
import com.sunxl.base.util.DBSqlUtil;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;
import com.sunxl.base.util.Sql;
import com.sunxl.base.util.SunxlConnectionFactory;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月1日 @此类作用：
 */
@Controller
@RequestMapping(value = "/private/importDataInfo")
public class PrivateImportDataInfoController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateImportDataInfoController.class);
	private static final String OBJECTINFO = "导数配置";
	@Autowired
	private ImportDataInfoService importDataInfoService;
	@Autowired
	private ImportDataColmService importDataColmService;

	@RequestMapping(value = "/viewimportDataInfoJson")
	public String goImportDataInfo(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			PrivateBaseMethodUtil.goObjectViewList(request, searchParamList, "/private/importDataInfo", Page.create(), "#idImportDataInfoForm", PrivateImportDataInfoController.OBJECTINFO);
			return "private.importDataInfo.list";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String goImportDataInfoList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridString(request, importDataInfoService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewImportDataInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, importDataInfoService, "private.importDataInfo.view", id, "importDataInfo");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addImportDataInfo(HttpServletRequest request) {
		PrivateBaseMethodUtil.requestSetSysData(request, "sunxl", "type", "sunxl_type");
		PrivateBaseMethodUtil.requestSetSysTidData(request);
		return "private.importDataInfo.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addImportDataInfo(HttpServletRequest request, ImportDataInfo importDataInfo) {
		return PrivateBaseMethodUtil.addObject(importDataInfoService, importDataInfo, PrivateImportDataInfoController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteImportDataInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(importDataInfoService, id, PrivateImportDataInfoController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateImportDataInfo(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, importDataInfoService, "private.importDataInfo.update", id, "importDataInfo");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateImportDataInfo(HttpServletRequest request, ImportDataInfo importDataInfo) {
		return PrivateBaseMethodUtil.updateObject(importDataInfoService, importDataInfo, PrivateImportDataInfoController.OBJECTINFO);
	}

	@RequestMapping(value = "/addTableInfo/{id}", method = RequestMethod.GET)
	@MethodLog(remark = "根据表名添加表字段，默认值，表注释信息")
	public String viewTableInfo(@PathVariable("id") int id, HttpServletRequest request) {
		try {
			List<ImportDataColm> list = DBSqlUtil.getObjectList(ImportDataColm.class, "SELECT ordeid,coluna,defava,comment FROM base_import_colm WHERE IID=" + id + " ORDER BY ordeid ASC ");
			request.setAttribute("importDataColms", list);
			return "private.importDataColm.add";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/addTableInfo/{id}", method = RequestMethod.POST)
	@ResponseBody
	@MethodLog(remark = "根据表名添加表字段，默认值，表注释信息")
	public JSON addTableInfo(@PathVariable("id") int id, ImportDataColm importDataColm, HttpServletRequest request) throws ConnectionException {
		JSON json = new JSON();
		String info = "";
		try {
			if (importDataColm.getImportDataColm() != null && id != 0) {
				List<ImportDataColm> listColums = importDataColmService.search(importDataColmService, new Sql().addNull("importDataInfo").setOrder("ordeid"));
				List<ImportDataColm> coloumnList = importDataColm.getImportDataColm();
				List<List<String>> values = new ArrayList<List<String>>();
				int maxId = importDataColmService.getIntByNativeSQL(importDataColmService, "SELECT VALUE FROM ID_SEQUENCE WHERE TABLENAME='base_import_colm'");
				for (int i = 0, len = coloumnList.size(); i < len; i++) {
					List<String> value = new ArrayList<String>();
					value.add((maxId++) + "");
					value.add(coloumnList.get(i).getColuna());
					value.add(coloumnList.get(i).getDefava());
					value.add(coloumnList.get(i).getComment());
					value.add(coloumnList.get(i).getOrdeid() + "");
					value.add(id + "");
					values.add(value);
				}
				SunxlConnectionFactory.beginTransaction();
				importDataColmService.delete("DELETE FROM base_import_colm WHERE IID=" + id);
				if (importDataColmService.insertBatch("base_import_colm", values, listColums)) {
					importDataColmService.update("UPDATE ID_SEQUENCE SET VALUE=" + maxId + " WHERE TABLENAME='base_import_colm'");
				} else {
					info = "批处理出错";
					throw new Exception(info);
				}
				SunxlConnectionFactory.commitTransaction();
			} else {
				info = "索引为" + id + "的base_import_info表信息不存在";
				throw new Exception(info);
			}
			info = "添加成功";
			json.setSuccess(true);
		} catch (Exception e) {
			SunxlConnectionFactory.rollBack();
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			info = "addTableInfo" + e.getMessage();
			json.setSuccess(false);
		}
		json.setMsg(info);
		return json;
	}
}
