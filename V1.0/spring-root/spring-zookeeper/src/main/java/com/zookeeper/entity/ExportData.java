package com.zookeeper.entity;

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

import com.base.enums.StatusType;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：卸数方案
 */
@Entity
@Table(name = "base_export")
public class ExportData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ExportData", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.ExportData", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ExportData")
	private Integer id;

	private String exportId;//卸数方案代号
	
	private String exportName;//卸数方案名称

	@Enumerated(EnumType.STRING)
	private StatusType type;//卸数方案状态

	private String smrytx;//摘要

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_export_exportInfo", inverseJoinColumns = { @JoinColumn(name = "eid") }, joinColumns = { @JoinColumn(name = "fid") })
	private Set<ExportDataInfo> exportDataInfos;

	private Timestamp createTime;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public String getSmrytx() {
		return smrytx;
	}

	public void setSmrytx(String smrytx) {
		this.smrytx = smrytx;
	}

	public Set<ExportDataInfo> getExportDataInfos() {
		return exportDataInfos;
	}

	public void setExportDataInfos(Set<ExportDataInfo> exportDataInfos) {
		this.exportDataInfos = exportDataInfos;
	}

	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
}
