package org.bachelor.ex.service;

import java.util.List;
import org.bachelor.ex.domain.ExMsg;

public interface IExMsgService {
	
	public List<ExMsg> findExMsgInfo(ExMsg ex);
}
