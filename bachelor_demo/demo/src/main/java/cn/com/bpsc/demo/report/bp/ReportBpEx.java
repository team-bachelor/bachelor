package org.bachelor.demo.report.bp;

import java.io.Serializable;

import org.bachelor.bpm.domain.BaseBpDataEx;



public class ReportBpEx extends BaseBpDataEx implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	
}
