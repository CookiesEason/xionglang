package com.sunxl.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.sunxl.base.enums.ResourceType;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月11日
 * @此类作用：
 */
@Entity
@Table(name="base_resource")
public class Resource implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="Resource",table="id_sequence",pkColumnName="ID_KEY",valueColumnName="VALUE",pkColumnValue="com.sunxl.base.entity.Resource",allocationSize=1,initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="Resource")
	private Integer id;
	
	@Column(nullable=false)
	private String resourcePath;
	
	private String original;
	private String thumbnail;
	
	@Column(length=500)
	private String fileName;
	
	private int ordinal;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	@Enumerated(EnumType.STRING)
	private ResourceType resourceType;

	@Column(length=100)
	private String model;
	
	private int otherId;
	
	@Version
	private int version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getOtherId() {
		return otherId;
	}

	public void setOtherId(int otherId) {
		this.otherId = otherId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}
