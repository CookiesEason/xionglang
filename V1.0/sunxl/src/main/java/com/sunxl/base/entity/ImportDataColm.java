package com.sunxl.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author：熊浪
 * @Email：xiongl@sunline.cn
 * @Time：2017年7月27日 @此类作用：导数添加的表字段信息
 */
@Entity
@Table(name = "base_import_colm")
public class ImportDataColm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "ImportDataColm", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.ImportDataColm", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportDataColm")
	private Integer id;

	private int ordeid;// 位置顺序
	
	private String coluna;// 字段名

	private String defava;// 默认值

	private String commet;// 注释

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_import_importcolm", inverseJoinColumns = { @JoinColumn(name = "iid") }, joinColumns = { @JoinColumn(name = "cid") })
	private Set<ImportDataInfo> importDataInfos;
	
	//前台注入list不需要跟数据库交互
	@Transient
	private List<ImportDataColm> importDataColm;

	public List<ImportDataColm> getImportDataColm() {
		return importDataColm;
	}

	public void setImportDataColm(List<ImportDataColm> importDataColm) {
		this.importDataColm = importDataColm;
	}
	
	public ImportDataColm() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColuna() {
		return coluna;
	}

	public void setColuna(String coluna) {
		this.coluna = coluna;
	}

	public String getDefava() {
		return defava;
	}

	public void setDefava(String defava) {
		this.defava = defava;
	}

	public String getCommet() {
		return commet;
	}

	public void setCommet(String commet) {
		this.commet = commet;
	}

	public int getOrdeid() {
		return ordeid;
	}

	public void setOrdeid(int ordeid) {
		this.ordeid = ordeid;
	}

	public Set<ImportDataInfo> getImportDataInfos() {
		return importDataInfos;
	}

	public void setImportDataInfos(Set<ImportDataInfo> importDataInfos) {
		this.importDataInfos = importDataInfos;
	}

	public ImportDataColm(int ordeid, String coluna, String defava, String commet) {
		super();
		this.ordeid = ordeid;
		this.coluna = coluna;
		this.defava = defava;
		this.commet = commet;
	}
}
