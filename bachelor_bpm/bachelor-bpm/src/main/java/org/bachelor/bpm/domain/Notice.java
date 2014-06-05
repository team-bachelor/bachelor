package org.bachelor.bpm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_ufp_bpm_notice")
public class Notice {
	
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	private String title;
	
	private String content;
	
	@Column(name="sender_id")
	private String senderId;
	
	@Column(name="sender_name")
	private String senderName;
	
	private String url;
	
	//通知状态 0：新建 1：已阅 3：已办
	private String status;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="read_time")
	private Date readTime;
	
	@Column(name="finish_time")
	private Date finishTime;
	
	@Column(name="read_id")
	private String readId;
	
	@Column(name="read_name")
	private String readName;
	
	@Column(name="done_id")
	private String doneId;
	
	@Column(name="done_name")
	private String doneName;
	
	@Transient
	private String roleId;
	
	@Transient
	private String roleName;
	
	@Transient
	private String recevierUserId;
	
	@Transient
	private String recevierUserName;
	
	public String getRecevierUserId() {
		return recevierUserId;
	}
	public void setRecevierUserId(String recevierUserId) {
		this.recevierUserId = recevierUserId;
	}
	public String getRecevierUserName() {
		return recevierUserName;
	}
	public void setRecevierUserName(String recevierUserName) {
		this.recevierUserName = recevierUserName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getReadId() {
		return readId;
	}
	public void setReadId(String readId) {
		this.readId = readId;
	}
	public String getReadName() {
		return readName;
	}
	public void setReadName(String readName) {
		this.readName = readName;
	}
	public String getDoneId() {
		return doneId;
	}
	public void setDoneId(String doneId) {
		this.doneId = doneId;
	}
	public String getDoneName() {
		return doneName;
	}
	public void setDoneName(String doneName) {
		this.doneName = doneName;
	}
}
