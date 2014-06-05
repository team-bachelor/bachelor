package org.bachelor.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.stat.dao.IStatServerDao;
import org.bachelor.stat.domain.StatServer;
import org.bachelor.stat.service.IStatServerService;

@Service
public class StatServerServiceImpl implements IStatServerService {

	@Autowired
	private IStatServerDao statServerDao = null;
	
	@Override
	public void saveOrUpdate(StatServer ss) {
		 
		statServerDao.saveOrUpdate(ss);
	}

	@Override
	public List<StatServer> findByExample(StatServer ss) {
		 
		
		return statServerDao.findByExample(ss);
	}

}
