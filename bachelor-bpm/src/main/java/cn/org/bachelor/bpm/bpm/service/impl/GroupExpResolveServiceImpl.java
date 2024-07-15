package cn.org.bachelor.bpm.bpm.service.impl;

import java.util.List;

import cn.org.bachelor.bpm.bpm.service.IGroupExpResolveService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.org.bachelor.bpm.domain.BaseBpDataEx;
import cn.org.bachelor.bpm.service.IExpressionResolver;
import cn.org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupExpResolveServiceImpl implements IGroupExpResolveService {

	@Autowired
	private IExpressionResolver resolver;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public List<? extends IBaseEntity> resolve(String groupOrgExp, BaseBpDataEx bpDataEx) {
		return resolver.resolveUsersByGroupExp(groupOrgExp);
	}
}
