package org.bachelor.ex.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ex.dao.IExMsgDao;
import org.bachelor.ex.domain.ExMsg;
import org.bachelor.ex.service.IExMsgService;

@Service
public class ExMsgServiceImpl implements IExMsgService {
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired 
	private IExMsgDao exMsgDao = null;
	/**
	 * 查询异常信息
	 */
	@Override
	public List<ExMsg> findExMsgInfo(ExMsg ex) {
		
		return exMsgDao.findExMsgInfo(ex);
	}

}
