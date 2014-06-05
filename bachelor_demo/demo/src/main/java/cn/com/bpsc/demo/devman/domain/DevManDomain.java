package org.bachelor.demo.devman.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_DEMO_DEVMAN")
public class DevManDomain {
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	private String reason;
	private String description;
	private String verifyContent;
	private String memo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVerifyContent() {
		return verifyContent;
	}
	public void setVerifyContent(String verifyContent) {
		this.verifyContent = verifyContent;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String comment) {
		this.memo = comment;
	}
	
	
	
}
