package com.zookeeper.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月17日 @此类作用：角色菜单关联表
 */
@Entity
@Table(name = "base_export_exportInfo")
public class ExportToExportInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "ExportToExportInfo", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.ExportToExportInfo", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ExportToExportInfo")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "eid")
	private ExportData exportData;
	@OneToOne
	@JoinColumn(name = "fid")
	private ExportDataInfo exportDataInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ExportData getExportData() {
		return exportData;
	}

	public void setExportData(ExportData exportData) {
		this.exportData = exportData;
	}

	public ExportDataInfo getExportDataInfo() {
		return exportDataInfo;
	}

	public void setExportDataInfo(ExportDataInfo exportDataInfo) {
		this.exportDataInfo = exportDataInfo;
	}

}
