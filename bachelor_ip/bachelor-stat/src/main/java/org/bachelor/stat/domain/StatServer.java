package org.bachelor.stat.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_ufp_stat_server_ss")
public class StatServer {
	 
	@Id
	@GenericGenerator(name="uuidGen", strategy="uuid")
	@GeneratedValue(generator="uuidGen")
	private String id;
	
	@Column(name="IP_ADDR")
	private String ipAddr;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="SHUTDOWN_TIME")
	private Date shutdownTime;
	
	private Date ts;

	@Transient
	private String searchType;
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getShutdownTime() {
		return shutdownTime;
	}

	public void setShutdownTime(Date shutdownTime) {
		this.shutdownTime = shutdownTime;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
}
