package com.zookeeper.form;
import java.util.List;

import com.base.form.TableInfoBeanForm;
import com.base.util.SunxlConnectionFactory;
import com.zookeeper.entity.ImportDataColm;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年9月1日 @此类的作用：
 */
public class InsertBatchForm {
	private TableInfoBeanForm tbInfo;
	private List<List<String>> values;
	private List<ImportDataColm> colmnList;
	private String jndiName;

	public TableInfoBeanForm getTbInfo() {
		return tbInfo;
	}

	public void setTbInfo(TableInfoBeanForm tbInfo) {
		this.tbInfo = tbInfo;
	}

	public List<List<String>> getValues() {
		return values;
	}

	public void setValues(List<List<String>> values) {
		this.values = values;
	}

	public List<ImportDataColm> getColmnList() {
		return colmnList;
	}

	public void setColmnList(List<ImportDataColm> colmnList) {
		this.colmnList = colmnList;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public InsertBatchForm(TableInfoBeanForm tbInfo, List<List<String>> values, List<ImportDataColm> colmnList) {
		super();
		this.tbInfo = tbInfo;
		this.values = values;
		this.colmnList = colmnList;
		this.jndiName = SunxlConnectionFactory.getJndiName();
	}

	public InsertBatchForm(TableInfoBeanForm tbInfo, List<List<String>> values, List<ImportDataColm> colmnList, String jndiName) {
		super();
		this.tbInfo = tbInfo;
		this.values = values;
		this.colmnList = colmnList;
		this.jndiName = jndiName;
	}

	public InsertBatchForm(List<List<String>> values, List<ImportDataColm> colmnList, String jndiName) {
		super();
		this.values = values;
		this.colmnList = colmnList;
		this.jndiName = jndiName;
	}

	public InsertBatchForm(List<List<String>> values, List<ImportDataColm> colmnList) {
		super();
		this.values = values;
		this.colmnList = colmnList;
		this.jndiName = SunxlConnectionFactory.getJndiName();
	}
}
