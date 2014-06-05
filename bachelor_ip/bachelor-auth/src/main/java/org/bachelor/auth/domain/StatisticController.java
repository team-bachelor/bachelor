package org.bachelor.auth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_UFP_STATISTIC_EFFICIENCY")
public class StatisticController {
	
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name="REQUEST_PATH")
	private String requestPath;
	
	@Column(name="ACCESS_DATE")
	private Date accessDate;
	
	@Column(name="COMPLETE_DATE")
	private Date completeDate;
	
	@Column(name="COST_TIME")
	private double costTime;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="COMPANY_ID")
	private String companyId;
	
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	private String type;
	
	@Column(name="ACCESS_CLASS")
	private String accessClass;
	
	@Column(name="ACCESS_METHOD")
	private String accessMethod;
	
	@Column(name = "SERVER_IP")
	private String serverIp;
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccessClass() {
		return accessClass;
	}

	public void setAccessClass(String accessClass) {
		this.accessClass = accessClass;
	}

	public String getAccessMethod() {
		return accessMethod;
	}

	public void setAccessMethod(String accessMethod) {
		this.accessMethod = accessMethod;
	}

	private Date ts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public double getCostTime() {
		return costTime;
	}

	public void setCostTime(double costTime) {
		this.costTime = costTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
}
