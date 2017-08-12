package com.sunxl.base.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.sunxl.base.enums.StatusType;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：卸数配置信息
 */
@Entity
@Table(name = "base_export_info")
public class ExportDataInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ExportDataInfo", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.ExportDataInfo", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ExportDataInfo")
	private Integer id;

	private String exportInfoId;// 卸数配置代号

	private String exportInfoName;// 卸数配置名称

	@OneToOne
	@JoinColumn(name = "sid")
	private SysTidData systid;// 文件系统来源

	private String sqlstr;// 卸数SQL

	private int insrtg;// 是否增量

	private String filesz;// 文件分割大小
	
	private String smrytx;//摘要

	@Enumerated(EnumType.STRING)
	private StatusType type;// 卸数信息状态

	private String fileex;// 文件扩展名默认txt

	private String path;// 文件卸数路径

	private String splitChar;// 文件分隔符

	private String chartSet;// 文件编码

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSmrytx() {
		return smrytx;
	}

	public void setSmrytx(String smrytx) {
		this.smrytx = smrytx;
	}

	public String getExportInfoId() {
		return exportInfoId;
	}

	public void setExportInfoId(String exportInfoId) {
		this.exportInfoId = exportInfoId;
	}

	public String getExportInfoName() {
		return exportInfoName;
	}

	public void setExportInfoName(String exportInfoName) {
		this.exportInfoName = exportInfoName;
	}

	public SysTidData getSystid() {
		return systid;
	}

	public void setSystid(SysTidData systid) {
		this.systid = systid;
	}

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}

	public int getInsrtg() {
		return insrtg;
	}

	public void setInsrtg(int insrtg) {
		this.insrtg = insrtg;
	}

	public String getFilesz() {
		return filesz;
	}

	public void setFilesz(String filesz) {
		this.filesz = filesz;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public String getFileex() {
		return fileex;
	}

	public void setFileex(String fileex) {
		this.fileex = fileex;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSplitChar() {
		return splitChar;
	}

	public void setSplitChar(String splitChar) {
		this.splitChar = splitChar;
	}

	public String getChartSet() {
		return chartSet;
	}

	public void setChartSet(String chartSet) {
		this.chartSet = chartSet;
	}
}
