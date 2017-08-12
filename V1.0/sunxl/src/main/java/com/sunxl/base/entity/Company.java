package com.sunxl.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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

import com.sunxl.base.enums.CorporationType;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @此类作用：
 */
@Entity
@Table(name = "base_company")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 */
	@Id
	@TableGenerator(name = "Company", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.sunxl.base.entity.Company", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Company")
	private Integer id;

	private String companyName;// 公司名称
	private String companyPath;// 公司地址
	
	@Enumerated(EnumType.STRING)
	private CorporationType corporationType;// 公司性质
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;// 公司法人
	private String companyScale;// 公司规模
	private String companyIntroduction;// 公司简介
	private String url;// 公司网址
	private String phone;// 公司对外电话
	private Timestamp createTime;// 公司建立时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPath() {
		return companyPath;
	}

	public void setCompanyPath(String companyPath) {
		this.companyPath = companyPath;
	}

	public CorporationType getCorporationType() {
		return corporationType;
	}

	public void setCorporationType(CorporationType corporationType) {
		this.corporationType = corporationType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
