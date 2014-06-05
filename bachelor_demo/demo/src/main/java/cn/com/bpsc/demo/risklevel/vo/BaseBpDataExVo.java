package org.bachelor.demo.risklevel.vo;

import java.io.Serializable;

public class BaseBpDataExVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3975327657553829261L;

	
	/**
	 * 流程实例ID
	 */
	private String piId;
	
	/**
	 * 节点名称
	 */
	private String taskName;
	
	/**
	 * 节点开始时间
	 */
	private String taskCreateTime;

	public String getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(String taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public String getPiId() {
		return piId;
	}

	public void setPiId(String piId) {
		this.piId = piId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
}
