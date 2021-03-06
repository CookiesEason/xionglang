package com.privates.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.exception.SunxlRuntimeException;
import com.base.form.ClassTypeInfoForm;
import com.base.form.EasyUIDataGridForm;
import com.base.form.FieldForm;
import com.base.form.ViewInfoForm;
import com.base.interfaces.MethodLog;
import com.base.one.entity.IdSequence;
import com.base.service.IdSequenceService;
import com.base.util.ButtonUtil;
import com.base.util.DBSqlUtil;
import com.base.util.JSON;
import com.base.util.PrivateBaseMethodUtil;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年6月21日
 * @此类作用：管理索引
 */
@Controller
@RequestMapping(value = "/private/idSequence")
public class PrivateIdSequenceController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateIdSequenceController.class);
	private static final String OBJECTINFO = "索引";
	@Autowired
	private IdSequenceService idSequenceService;

	@RequestMapping(value = "/viewIdSequenceJson")
	public String goIdSequence(HttpServletRequest request) {
		List<FieldForm> searchParamList = new ArrayList<FieldForm>();
		searchParamList.add(new FieldForm("tableName", "表名"));
		searchParamList.add(new FieldForm("entityKey", "类路径"));
		searchParamList.add(new FieldForm("entityValue", "索引值"));
		searchParamList.add(new FieldForm("smrytx", "表用途"));
		try {
			ButtonUtil button = new ButtonUtil("同步" + PrivateIdSequenceController.OBJECTINFO, "/private/idSequence/updateAll");
			ViewInfoForm viewInfoForm = new ViewInfoForm(searchParamList, "/private/idSequence", "idSequenceForm", PrivateIdSequenceController.OBJECTINFO);
			viewInfoForm.setListButton(button);
			PrivateBaseMethodUtil.goObjectViewList(request, viewInfoForm);
			return "private.idSequence.list";
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			return "private.error";
		}
	}

	@RequestMapping(value = "/goViewList", params = "method=ajaxList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public EasyUIDataGridForm goIdSequenceList(HttpServletRequest request) {
		return PrivateBaseMethodUtil.getEasyUIDataGridJSON(request, idSequenceService);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String viewIdSequence(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, idSequenceService, "private.idSequence.view", id, "idSequence");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addIdSequence(HttpServletRequest request) {
		return "private.idSequence.add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON addIdSequence(HttpServletRequest request, IdSequence idSequence) {
		return PrivateBaseMethodUtil.addObject(idSequenceService, idSequence, PrivateIdSequenceController.OBJECTINFO);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSON deleteIdSequence(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.deleteObject(idSequenceService, id, PrivateIdSequenceController.OBJECTINFO);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateIdSequence(@PathVariable("id") int id, HttpServletRequest request) {
		return PrivateBaseMethodUtil.viewObject(request, idSequenceService, "private.idSequence.update", id, "idSequence");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JSON updateIdSequence(HttpServletRequest request, IdSequence idSequence) {
		return PrivateBaseMethodUtil.updateObject(idSequenceService, idSequence, PrivateIdSequenceController.OBJECTINFO);
	}

	@RequestMapping(value = "/updateAll", method = RequestMethod.POST)
	@MethodLog(remark = "同步所有的索引，entityValue值就是下次添加的值")
	@ResponseBody
	public JSON updateAllIdSequence(@RequestParam("jndiName") String jndiName) {
		JSON json = new JSON();
		try {
			refreshIdsequence(jndiName);
			json.setSuccess(true);
			json.setMsg("同步" + PrivateIdSequenceController.OBJECTINFO + "成功");
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + e.getMessage());
			json.setSuccess(false);
			json.setMsg("同步" + PrivateIdSequenceController.OBJECTINFO + "失败" + e.getMessage());
		}
		return json;
	}

	public void refreshIdsequence(String jndiName) {
		String sqlStr = "";
		try {
			List<String> rows = DBSqlUtil.getInInstance().getObjectList(new ClassTypeInfoForm(String.class, "SELECT TABLENAME FROM ID_SEQUENCE", jndiName));
			int id = 0;
			for (int i = 0, len = rows.size(); i < len; i++) {
				sqlStr = "SELECT MAX(ID) FROM " + rows.get(i);
				Object maxId = DBSqlUtil.getInInstance().getObject(new ClassTypeInfoForm(Integer.class, sqlStr, jndiName));// 获取比最大值大1的索引值
				id=maxId==null?1:Integer.parseInt(maxId.toString())+1;
				sqlStr = "UPDATE id_sequence SET VALUE =" + id + " WHERE TABLENAME='" + rows.get(i) + "'";
				DBSqlUtil.getInInstance().update(new ClassTypeInfoForm(sqlStr, jndiName));// 修改此索引值位id
			}
		} catch (Exception e) {
			logger.error("错误行号:【" + new Throwable().getStackTrace()[0].getLineNumber() + "】" + sqlStr + e.getMessage());
			throw new SunxlRuntimeException(e.getMessage());
		}
	}
}
