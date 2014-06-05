package org.bachelor.bpm.service;

import java.util.List;
import java.util.Set;

import org.bachelor.bpm.auth.IBpmUser;
import org.bachelor.bpm.domain.BaseBpDataEx;

public interface IGroupExpResolveService {

	public List<? extends IBpmUser> resolve(String groupOrgExp, BaseBpDataEx bpDataEx);
	
	public String resolveGroupExp(String groupExp, BaseBpDataEx bpDataEx);
	
	public String resolveGroupExp(String groupExp, String piId);
}
