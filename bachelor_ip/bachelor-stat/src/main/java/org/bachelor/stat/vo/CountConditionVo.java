package org.bachelor.stat.vo;


import java.io.Serializable;

/**
 * 统计查询条件VO
 * @author user
 *
 */
public class CountConditionVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5872782267840627327L;
	
	private String startTime;
	private String endTime;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
