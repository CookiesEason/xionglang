package com.zookeeper.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

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
	@TableGenerator(name = "ImportDataColm", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.ImportDataColm", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ImportDataColm")
	private Integer id;

	private int ordeid;// 位置顺序

	private String coluna;// 字段名

	private String defava;// 默认值

	private String comment;// 注释

	@ManyToOne
	@JoinColumn(name = "iid")
	private ImportDataInfo importDataInfo;// 所属表

	@Transient
	private List<ImportDataColm> importDataColm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOrdeid() {
		return ordeid;
	}

	public void setOrdeid(int ordeid) {
		this.ordeid = ordeid;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ImportDataInfo getImportDataInfo() {
		return importDataInfo;
	}

	public void setImportDataInfo(ImportDataInfo importDataInfo) {
		this.importDataInfo = importDataInfo;
	}

	public List<ImportDataColm> getImportDataColm() {
		return importDataColm;
	}

	public void setImportDataColm(List<ImportDataColm> importDataColm) {
		this.importDataColm = importDataColm;
	}

	public ImportDataColm() {
		super();
	}

	public ImportDataColm(int ordeid, String coluna, String defava, String comment, ImportDataInfo importDataInfo) {
		super();
		this.ordeid = ordeid;
		this.coluna = coluna;
		this.defava = defava;
		this.comment = comment;
		this.importDataInfo = importDataInfo;
	}

}
