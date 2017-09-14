package com.base.form;

import java.util.List;

import com.base.util.ButtonUtil;
import com.base.util.Page;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月24日 @此类的作用：
 */
public class ViewInfoForm {

	private List<FieldForm> searchParamList;// 查询的字段值和字段名

	private String baseUrl;// 返回路径

	private String formId;// 表单提交

	private Page page;// 分页规则

	private String objectInfo;// 操作对象信息

	private ButtonUtil addButton;// 添加按钮是否出现

	private ButtonUtil[] listButton;// 普通按钮

	public ViewInfoForm() {

	}

	public List<FieldForm> getSearchParamList() {
		return searchParamList;
	}

	public void setSearchParamList(List<FieldForm> searchParamList) {
		this.searchParamList = searchParamList;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getObjectInfo() {
		return objectInfo;
	}

	public void setObjectInfo(String objectInfo) {
		this.objectInfo = objectInfo;
	}

	public ButtonUtil getAddButton() {
		return addButton;
	}

	public ButtonUtil[] getListButton() {
		return listButton;
	}

	/**
	 * @param listButton
	 * @此方法的作用：普通按钮
	 */
	public void setListButton(ButtonUtil... button) {
		this.listButton = button;
	}

	/**
	 * @param searchParamList
	 * @param returnPath
	 * @param objectInfo
	 * @param buttonUtils
	 * @此类的作用 默认添加按钮存在，默认提交方式GET,默认路径+/add
	 */
	public ViewInfoForm(List<FieldForm> searchParamList, String baseUrl, String formId, String objectInfo, ButtonUtil... buttonUtils) {
		this.searchParamList = searchParamList;
		this.baseUrl = baseUrl;
		this.objectInfo = objectInfo;
		this.listButton = buttonUtils;
		this.page = Page.create();
		ButtonUtil addButton = new ButtonUtil("添加" + objectInfo, baseUrl + "/add", formId);
		this.addButton = addButton;
		this.formId = formId;
	}

	public ViewInfoForm(List<FieldForm> searchParamList, String baseUrl, String objectInfo, String formId, ButtonUtil addButton, ButtonUtil... buttonUtils) {
		this.searchParamList = searchParamList;
		this.baseUrl = baseUrl;
		this.objectInfo = objectInfo;
		this.addButton = addButton;
		this.listButton = buttonUtils;
		this.page = Page.create();
		this.formId = formId;
	}
}
