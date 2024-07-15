package cn.org.bachelor.bpm.bpm.service;

import java.util.List;

import cn.org.bachelor.bpm.domain.BaseBpDataEx;
import cn.org.bachelor.core.entity.IBaseEntity;

public interface IGroupExpResolveService {

	public List<? extends IBaseEntity> resolve(String groupOrgExp, BaseBpDataEx bpDataEx);
	
//	public String resolveGroupExp(String groupExp, BaseBpDataEx bpDataEx);
//	
//	public String resolveGroupExp(String groupExp, String piId);
}
