package com.sunxl.base.entity;

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
@Table(name = "base_import_importcolm")
public class ImportToColm implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "ImportToColm", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.ImportToColm", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportToColm")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "iid")
	private ImportDataInfo importDataInfo;
	@OneToOne
	@JoinColumn(name = "cid")
	private ImportDataColm importDataColm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ImportDataColm getImportDataColm() {
		return importDataColm;
	}

	public void setImportDataColm(ImportDataColm importDataColm) {
		this.importDataColm = importDataColm;
	}

	public ImportDataInfo getImportDataInfo() {
		return importDataInfo;
	}

	public void setImportDataInfo(ImportDataInfo importDataInfo) {
		this.importDataInfo = importDataInfo;
	}
}
