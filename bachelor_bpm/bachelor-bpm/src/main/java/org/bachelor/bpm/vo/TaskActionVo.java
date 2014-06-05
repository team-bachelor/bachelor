package org.bachelor.bpm.vo;

import java.util.Date;
import java.util.List;

import org.bachelor.bpm.auth.IBpmOrg;
import org.bachelor.bpm.auth.IBpmUser;

public class TaskActionVo {

	private IBpmUser operator;
	private IBpmOrg operCompany;
	private Date startDate;
	private Date completeDate;
	private List<String> comments;
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public IBpmUser getOperator() {
		return operator;
	}
	public void setOperator(IBpmUser operator) {
		this.operator = operator;
	}
	public IBpmOrg getOperCompany() {
		return operCompany;
	}
	public void setOperCompany(IBpmOrg operCompany) {
		this.operCompany = operCompany;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
}
