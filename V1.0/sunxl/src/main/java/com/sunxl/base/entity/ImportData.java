package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sunxl.base.enums.StatusType;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：导数方案
 */
@Entity
@Table(name = "base_import")
public class ImportData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ImportData", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.ImportData", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportData")
	private Integer id;

	private String importId;//导数方案代号

	private String importName;//导数方案名称

	private String smrytx;//摘要

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_import_importInfo", inverseJoinColumns = { @JoinColumn(name = "iid") }, joinColumns = { @JoinColumn(name = "fid") })
	private Set<ImportDataInfo> importDataInfos;

	@Enumerated(EnumType.STRING)
	private StatusType type;//导数方法状态

	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}

	public String getSmrytx() {
		return smrytx;
	}

	public void setSmrytx(String smrytx) {
		this.smrytx = smrytx;
	}

	public Set<ImportDataInfo> getImportDataInfos() {
		return importDataInfos;
	}

	public void setImportDataInfos(Set<ImportDataInfo> importDataInfos) {
		this.importDataInfos = importDataInfos;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
