package com.sunxl.base.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sunxl.base.enums.StatusType;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：导数配置信息
 */
@Entity
@Table(name = "base_import_info")
public class ImportDataInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ImportDataInfo", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.ImportDataInfo", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportDataInfo")
	private Integer id;

	private String importInfoId;// 文件名前缀

	private String importInfoName;// 导数配置名称

	@OneToOne
	@JoinColumn(name = "sid")
	private SysTidData systid;// 文件系统来源

	@Enumerated(EnumType.STRING)
	private StatusType type;// 导数配置状态

	private String certty;// 指令

	private String tableName;// 表名

	private String path;// 导数文件路径

	private String splitChar;// 导数文件分隔符

	private String chartSet;// 导数文件编码

	private String smrytx;// 摘要

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_import_importcolm", inverseJoinColumns = { @JoinColumn(name = "cid") }, joinColumns = { @JoinColumn(name = "iid") })
	private Set<ImportDataColm> importDataColms;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SysTidData getSystid() {
		return systid;
	}

	public void setSystid(SysTidData systid) {
		this.systid = systid;
	}

	public String getImportInfoId() {
		return importInfoId;
	}

	public void setImportInfoId(String importInfoId) {
		this.importInfoId = importInfoId;
	}

	public String getImportInfoName() {
		return importInfoName;
	}

	public void setImportInfoName(String importInfoName) {
		this.importInfoName = importInfoName;
	}

	public StatusType getType() {
		return type;
	}

	public void setType(StatusType type) {
		this.type = type;
	}

	public String getCertty() {
		return certty;
	}

	public void setCertty(String certty) {
		this.certty = certty;
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

	public Set<ImportDataColm> getImportDataColms() {
		return importDataColms;
	}

	public void setImportDataColms(Set<ImportDataColm> importDataColms) {
		this.importDataColms = importDataColms;
	}
}
