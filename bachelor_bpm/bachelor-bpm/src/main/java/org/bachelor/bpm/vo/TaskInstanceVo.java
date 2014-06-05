package org.bachelor.bpm.vo;

import java.util.List;

import org.bachelor.bpm.auth.IBpmRole;
import org.bachelor.bpm.auth.IBpmUser;

public class TaskInstanceVo {

	private String pdid;
	private String piid;
	private String executionId;
	private String id;
	private String defKey;
	private String name;
	private String desciption;
	private int priority;
	private IBpmUser owner;
	private IBpmUser assignee;
	private List<IBpmUser> assignments;
	public IBpmUser getAssignee() {
		return assignee;
	}
	public void setAssignee(IBpmUser assignee) {
		this.assignee = assignee;
	}
	public List<IBpmUser> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<IBpmUser> assignments) {
		this.assignments = assignments;
	}
	private List<IBpmRole> assignmentRole;
	private String status;
	private List<TaskActionVo> taskActions;
	
	public String getPdid() {
		return pdid;
	}
	public void setPdid(String pdid) {
		this.pdid = pdid;
	}
	public String getPiid() {
		return piid;
	}
	public void setPiid(String piid) {
		this.piid = piid;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDefKey() {
		return defKey;
	}
	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public IBpmUser getOwner() {
		return owner;
	}
	public void setOwner(IBpmUser owner) {
		this.owner = owner;
	}
	public List<IBpmRole> getAssignmentRole() {
		return assignmentRole;
	}
	public void setAssignmentRole(List<IBpmRole> assignmentRole) {
		this.assignmentRole = assignmentRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TaskActionVo> getTaskActions() {
		return taskActions;
	}
	public void setTaskActions(List<TaskActionVo> taskActions) {
		this.taskActions = taskActions;
	}
	
}
