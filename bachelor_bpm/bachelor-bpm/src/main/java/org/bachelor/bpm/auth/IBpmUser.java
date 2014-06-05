package org.bachelor.bpm.auth;

import org.bachelor.bpm.domain.IBaseBizDomain;

public interface IBpmUser extends IBaseBizDomain{
	public String getOrgId();
	public IBpmOrg getOrg();
}
