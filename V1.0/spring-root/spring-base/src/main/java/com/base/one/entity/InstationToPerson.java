/**
 * 
 */
package com.base.one.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年8月17日
 * @此类的作用：邮件关联用户，可以前台用户跟后台用户邮件沟通
 */
@Entity
@Table(name = "base_instation_person")
public class InstationToPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "InstationToPerson", table = "ID_SEQUENCE", pkColumnName = "ID_KEY", valueColumnName = "VALUE", pkColumnValue = "com.base.one.entity.InstationToPerson", allocationSize = 100, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "InstationToPerson")
	private Integer id;
	@OneToOne
	@JoinColumn(name = "iid")
	private InstationMessage instationMessage;// 邮件
	@OneToOne
	@JoinColumn(name = "sid")
	private User sendUser;// 前台发送人
	@OneToOne
	@JoinColumn(name = "rid")
	private User recieveUser;// 前台接收人
	@OneToOne
	@JoinColumn(name = "asid")
	private AdminUser sendAdminUser;// 后台发送人
	@OneToOne
	@JoinColumn(name = "arid")
	private AdminUser recieveAdminUser;// 后台接收人
	private Timestamp createTime;// 发送时间
	private boolean sendHasDelete;// 发送人是否删除
	private boolean receiveHasDelete;// 接收人是否删除
	private boolean receiveHasRead;// 接收人是否已读
	@Transient
	private List<User> users;// 所有前台接收人的信息,不记录数据库，只是接收前台信息
	@Transient
	private List<AdminUser> adminusers;// 所有后台接收人的信息，不记录数据库，只是接收前台信息
	@Transient
	private List<InstationToPerson> instationToPersons;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InstationMessage getInstationMessage() {
		return instationMessage;
	}

	public void setInstationMessage(InstationMessage instationMessage) {
		this.instationMessage = instationMessage;
	}

	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public User getRecieveUser() {
		return recieveUser;
	}

	public void setRecieveUser(User recieveUser) {
		this.recieveUser = recieveUser;
	}

	public AdminUser getSendAdminUser() {
		return sendAdminUser;
	}

	public void setSendAdminUser(AdminUser sendAdminUser) {
		this.sendAdminUser = sendAdminUser;
	}

	public AdminUser getRecieveAdminUser() {
		return recieveAdminUser;
	}

	public void setRecieveAdminUser(AdminUser recieveAdminUser) {
		this.recieveAdminUser = recieveAdminUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public boolean isSendHasDelete() {
		return sendHasDelete;
	}

	public void setSendHasDelete(boolean sendHasDelete) {
		this.sendHasDelete = sendHasDelete;
	}

	public boolean isReceiveHasDelete() {
		return receiveHasDelete;
	}

	public void setReceiveHasDelete(boolean receiveHasDelete) {
		this.receiveHasDelete = receiveHasDelete;
	}

	public boolean isReceiveHasRead() {
		return receiveHasRead;
	}

	public void setReceiveHasRead(boolean receiveHasRead) {
		this.receiveHasRead = receiveHasRead;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<AdminUser> getAdminusers() {
		return adminusers;
	}

	public void setAdminusers(List<AdminUser> adminusers) {
		this.adminusers = adminusers;
	}

	public List<InstationToPerson> getInstationToPersons() {
		return instationToPersons;
	}

	public void setInstationToPersons(List<InstationToPerson> instationToPersons) {
		this.instationToPersons = instationToPersons;
	}
}
