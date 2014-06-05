package org.bachelor.demo.risklevel.bp;

import java.io.Serializable;
import java.util.List;

import org.bachelor.bpm.domain.BaseBpDataEx;

public class PNRiskLevelBpDataEx extends BaseBpDataEx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221215949130081079L;
	
	private String needMulti;
	
	private List<String> assigneeList;

	public String getNeedMulti() {
		return needMulti;
	}

	public void setNeedMulti(String needMulti) {
		this.needMulti = needMulti;
	}

	public List<String> getAssigneeList() {
		return assigneeList;
	}

	public void setAssigneeList(List<String> multiVerifyUsers) {
		this.assigneeList = multiVerifyUsers;
	}
 
}
