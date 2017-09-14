package com.base.one.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * @Email：xiongl@sunline.cn @Time：2017年8月2日 @此类作用：
 */
@Entity
@Table(name = "base_schedule_status")
public class ScheduleStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Roles", table = "id_sequence", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.Roles", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Roles")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "sid")
	private SysTidData systid;

	private Timestamp createDate;

	private String acctbr;

	private String bathid;
	@OneToOne
	@JoinColumn(name = "uid")
	private AdminUser adminUser;

	private int status;

	private String currdt;

	private String switdt;

	private int type;

	private String plancd;

	private String datach;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysTidData getSystid() {
		return systid;
	}

	public void setSystid(SysTidData systid) {
		this.systid = systid;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getAcctbr() {
		return acctbr;
	}

	public void setAcctbr(String acctbr) {
		this.acctbr = acctbr;
	}

	public String getBathid() {
		return bathid;
	}

	public void setBathid(String bathid) {
		this.bathid = bathid;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCurrdt() {
		return currdt;
	}

	public void setCurrdt(String currdt) {
		this.currdt = currdt;
	}

	public String getSwitdt() {
		return switdt;
	}

	public void setSwitdt(String switdt) {
		this.switdt = switdt;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPlancd() {
		return plancd;
	}

	public void setPlancd(String plancd) {
		this.plancd = plancd;
	}

	public String getDatach() {
		return datach;
	}

	public void setDatach(String datach) {
		this.datach = datach;
	}

}
