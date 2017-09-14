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
@Table(name = "base_import_importinfo")
public class ImportToImportInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "ImportToImportInfo", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.ImportToImportInfo", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportToImportInfo")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "iid")
	private ImportData importData;
	@OneToOne
	@JoinColumn(name = "fid")
	private ImportDataInfo importDataInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ImportData getImportData() {
		return importData;
	}

	public void setImportData(ImportData importData) {
		this.importData = importData;
	}

	public ImportDataInfo getImportDataInfo() {
		return importDataInfo;
	}

	public void setImportDataInfo(ImportDataInfo importDataInfo) {
		this.importDataInfo = importDataInfo;
	}
}
