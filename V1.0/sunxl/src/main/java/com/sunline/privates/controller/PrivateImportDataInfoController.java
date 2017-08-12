package com.sunline.privates.controller;

import java.sql.SQLException;
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
import com.sunline.base.interfaces.MethodLog;
import com.sunxl.base.entity.ImportDataColm;
import com.sunxl.base.entity.ImportDataInfo;
import com.sunxl.base.form.FieldForm;
import com.sunxl.base.form.TableInfoBeanForm;
import com.sunxl.base.service.ImportDataInfoService;
import com.sunxl.base.util.DBSqlUtil;
import com.sunxl.base.util.JSON;
import com.sunxl.base.util.Page;
import com.sunxl.base.util.PrivateBaseMethodUtil;
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
			request.setAttribute("ImportDataInfoId", id);
			return "private.importDataColm.add";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/addTableInfo/{id}", method = RequestMethod.POST)
	@MethodLog(remark = "根据表名添加表字段，默认值，表注释信息")
	public JSON addTableInfo(@PathVariable("id") int id, ImportDataColm importDataColm, HttpServletRequest request) throws ConnectionException {
		JSON json = new JSON();
		String info = "";
		try {
			if (importDataColm.getImportDataColm() != null && id != 0) {
				List<String> cids = DBSqlUtil.getObjectList(String.class, "SELECT CID FROM BASE_IMPORT_IMPORTCOLM WHERE IID =" + id);
				StringBuffer sb = new StringBuffer();
				for (int i = 0, len = cids.size(); i < len; i++) {
					if (i == cids.size() - 1)
						sb.append(cids.get(i));
					else
						sb.append(cids.get(i) + ",");
				}
				SunxlConnectionFactory.beginTransaction();
				importDataInfoService.delete("DELETE FROM BASE_IMPORT_IMPORTCOLM WHERE IID =" + id);// 先删除再添加
				if (!"".equals(sb.toString()))
					importDataInfoService.delete("DELETE FROM BASE_IMPORT_COLM WHERE ID IN(" + sb.toString() + ")");
				List<ImportDataColm> colmList = new ArrayList<ImportDataColm>();
				ImportDataColm importColm = new ImportDataColm(1, "id", "0", "主键");
				colmList.add(importColm);
				importColm = new ImportDataColm(2, "coluna", "0", "字段名");
				colmList.add(importColm);
				importColm = new ImportDataColm(3, "defava", "0", "默认值");
				colmList.add(importColm);
				importColm = new ImportDataColm(4, "commet", "0", "注释");
				colmList.add(importColm);
				importColm = new ImportDataColm(5, "ordeid", "0", "顺序");
				colmList.add(importColm);
				int maxId = importDataInfoService.getIntByNativeSQL(importDataInfoService, "SELECT VALUE FROM id_sequence WHERE ID_KEY='com.sunxl.base.entity.ImportDataColm'");
				List<ImportDataColm> list = importDataColm.getImportDataColm();
				List<List<String>> values = new ArrayList<List<String>>();
				List<String> value = null;
				cids.clear();
				for (int i = 0, len = list.size(); i < len; i++) {
					value = new ArrayList<String>();
					cids.add(maxId + "");// 保存主键，后面添加到BASE_IMPORT_IMPORTCOLM表中
					value.add((maxId++) + "");
					value.add(list.get(i).getColuna());
					value.add(list.get(i).getDefava());
					value.add(list.get(i).getCommet());
					value.add(list.get(i).getOrdeid() + "");
					values.add(value);
				}
				TableInfoBeanForm tbInfo = null;
				try {
					tbInfo = DBSqlUtil.getTableColumn("BASE_IMPORT_COLM", null);
					if (tbInfo != null && tbInfo.getColumnList().size() > 0) {
						if (PrivateBaseMethodUtil.insertIntoTable(tbInfo, values, colmList)) {
							maxId = importDataInfoService.getIntByNativeSQL(importDataInfoService, "SELECT VALUE FROM id_sequence WHERE ID_KEY='com.sunxl.base.entity.ImportToColm'");
							colmList.clear();
							importColm = new ImportDataColm(1, "id", "0", "主键");
							colmList.add(importColm);
							importColm = new ImportDataColm(1, "iid", "0", "关联的表");
							colmList.add(importColm);
							importColm = new ImportDataColm(1, "cid", "0", "关联的字段");
							colmList.add(importColm);
							values.clear();
							for (int i = 0, len = cids.size(); i < len; i++) {
								value = new ArrayList<String>();
								value.add((maxId++) + "");
								value.add(id + "");
								value.add(cids.get(i));
								values.add(value);
							}
							tbInfo = DBSqlUtil.getTableColumn("BASE_IMPORT_IMPORTCOLM", null);
							if (!PrivateBaseMethodUtil.insertIntoTable(tbInfo, values, colmList))
								throw new SQLException("BASE_IMPORT_IMPORTCOLM的批处理添加出错");
							SunxlConnectionFactory.commitTransaction();
						} else
							throw new SQLException("BASE_IMPORT_COLM的批处理添加出错");
					}
				} catch (SQLException e) {
					logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
				} finally {
					SunxlConnectionFactory.closeUserConnection();
				}
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
