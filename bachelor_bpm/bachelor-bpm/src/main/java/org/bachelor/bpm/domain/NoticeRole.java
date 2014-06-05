package org.bachelor.bpm.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_ufp_bpm_notice_role")
public class NoticeRole {

	@Id
	@GenericGenerator(name="uuidGen" , strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="notice_id")
	private Notice notice;
	
	@Column(name="ROLE_ID")
	private String roleId;
	@Column(name="ROLE_DESC")
	private String roleDesc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	
	
}
