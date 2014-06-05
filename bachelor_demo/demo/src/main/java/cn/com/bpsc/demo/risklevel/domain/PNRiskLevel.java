package org.bachelor.demo.risklevel.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_demo_pn_risk_level")
public class PNRiskLevel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3025682557233105315L;

	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name="RISK_DESC")
	private String riskDesc;
	
	@Column(name="RISK_LEVEL")
	private String riskLevel;
	
	@Column(name="VERIFY_USER_ID")
	private String verifyUserId;
	
	@Column(name="VERIFY_MSG")
	private String verifyMsg;
	
	@Column(name="VERIFY_DATE")
	private Date verifyDate;
	
	@Column(name="REVIEW1_USER_ID")
	private String review1UserId;
	
	@Column(name="REVIEW1_MSG")
	private String review1Msg;
	
	@Column(name="REVIEW1_DATE")
	private Date review1Date;
	
	@Column(name="REVIEW2_USER_ID")
	private String review2UserId;
	
	@Column(name="REVIEW2_MSG")
	private String review2Msg;
	
	@Column(name="REVIEW2_DATE")
	private Date review2Date;
	
	@Column(name="RISK_USER_ID")
	private String riskUserId;

	@Transient
	private String review2DateTime;
	
	@Transient
	private String review1DateTime;

	@Transient
	private String verifyDateTime;

	@Transient
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public String getReview2DateTime() {
		if(!StringUtils.isEmpty(review2DateTime)){
			
			return sdf.format(review2Date);
		}
		return null;
	}

	public void setReview2DateTime(String review2DateTime) {
		this.review2DateTime = review2DateTime;
	}

	public String getReview1DateTime() {
		if(!StringUtils.isEmpty(review1DateTime)){
			
			return sdf.format(review1Date);
		}
		return null; 
	}

	public void setReview1DateTime(String review1DateTime) {
		this.review1DateTime = review1DateTime;
	}

	public String getVerifyDateTime() {
		if(!StringUtils.isEmpty(verifyDateTime)){
			
			return sdf.format(verifyDate);
		}
		return null; 
	}

	public void setVerifyDateTime(String verifyDateTime) {
		this.verifyDateTime = verifyDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRiskDesc() {
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getVerifyUserId() {
		return verifyUserId;
	}

	public void setVerifyUserId(String verifyUserId) {
		this.verifyUserId = verifyUserId;
	}

	public String getVerifyMsg() {
		return verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getReview1UserId() {
		return review1UserId;
	}

	public void setReview1UserId(String review1UserId) {
		this.review1UserId = review1UserId;
	}

	public String getReview1Msg() {
		return review1Msg;
	}

	public void setReview1Msg(String review1Msg) {
		this.review1Msg = review1Msg;
	}

	public Date getReview1Date() {
		return review1Date;
	}

	public void setReview1Date(Date review1Date) {
		this.review1Date = review1Date;
	}

	public String getReview2UserId() {
		return review2UserId;
	}

	public void setReview2UserId(String review2UserId) {
		this.review2UserId = review2UserId;
	}

	public String getReview2Msg() {
		return review2Msg;
	}

	public void setReview2Msg(String review2Msg) {
		this.review2Msg = review2Msg;
	}

	public Date getReview2Date() {
		return review2Date;
	}

	public void setReview2Date(Date review2Date) {
		this.review2Date = review2Date;
	}

	public String getRiskUserId() {
		return riskUserId;
	}

	public void setRiskUserId(String riskUserId) {
		this.riskUserId = riskUserId;
	}
}
