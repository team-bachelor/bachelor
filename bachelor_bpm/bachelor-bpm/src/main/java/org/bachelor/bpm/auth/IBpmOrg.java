package org.bachelor.bpm.auth;

import org.bachelor.bpm.domain.IBaseBizDomain;

public interface IBpmOrg extends IBaseBizDomain{

	public String getShortName();

	public String getDepartmentId();

	public String getDepartmentName();
	
}
