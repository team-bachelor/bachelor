package org.bachelor.stat.service;

import java.util.List;

import org.bachelor.stat.domain.StatServer;


public interface IStatServerService {
	
	public void saveOrUpdate(StatServer ss);
	
	public List<StatServer> findByExample(StatServer ss);
}
