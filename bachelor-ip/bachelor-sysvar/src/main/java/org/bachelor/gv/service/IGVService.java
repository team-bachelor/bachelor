package org.bachelor.gv.service;

import java.util.List;

import org.bachelor.gv.domain.GlobalVariable;


public interface IGVService {

	public void update(GlobalVariable  gv);
	
	public List<GlobalVariable> queryAll(GlobalVariable gv);

	public void save(GlobalVariable gv);
	
	public void saveOrUpdate(GlobalVariable  gv);

	public void delete(GlobalVariable gv);
	
	public void batchDeleteById(String info);
	
	public GlobalVariable findById(String id);
	
	public GlobalVariable findByName(String name);
	
	public boolean exist(String propertyName, String propertyValue);
}
