package org.bachelor.stat.service;

import java.util.List;

import org.bachelor.stat.domain.StatAop;


public interface IStatAopService {

	public void saveOrUpdate(StatAop aop);
	
	public void save(StatAop aop);
	
	public void saveByList(List<StatAop> add_list);
	
	public void update(StatAop aop);
	
	public void deleteById(String id);
	
	public void delete(StatAop aop);
	
	public void deleteByList(List<StatAop> del_list);
	
	public List<StatAop> findAll(StatAop aop);
	
	public StatAop findById(String id);
	
	public StatAop findByName(String name);
	
	public void batchDelete(String info);
}
