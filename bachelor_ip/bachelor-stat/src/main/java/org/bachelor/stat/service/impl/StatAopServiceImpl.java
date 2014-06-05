package org.bachelor.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.context.interceptor.IEnabled;
import org.bachelor.context.interceptor.PlatManagedServiceAspect;
import org.bachelor.stat.dao.IStatAopDao;
import org.bachelor.stat.domain.StatAop;
import org.bachelor.stat.service.IStatAopService;

@Service
public class StatAopServiceImpl implements IStatAopService {

	@Autowired
	private IStatAopDao statAopDao;
	
	@Override
	public void saveOrUpdate(StatAop aop) {

		statAopDao.saveOrUpdate(aop);
	}

	@Override
	public void save(StatAop aop) {

		statAopDao.save(aop);
	}

	@Override
	public void update(StatAop aop) {
		
		statAopDao.update(aop);
	}

	@Override
	public List<StatAop> findAll(StatAop aop) {
		 
		return statAopDao.findAll();
	}

	@Override
	public StatAop findById(String id) {
		 
		return statAopDao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		StatAop sa = statAopDao.findById(id);
		statAopDao.delete(sa);
	}

	@Override
	public void delete(StatAop aop) {
		
		statAopDao.delete(aop);
	}

	@Override
	public void batchDelete(String info) {
		
		statAopDao.batchDelete(info);
	}

	@Override
	public StatAop findByName(String name) {
		List<StatAop> sa_list = statAopDao.findByProperty("className", name);
		
		return (sa_list!=null?(sa_list.size()>0?sa_list.get(0):null):null);
	}

	@Override
	public void saveByList(List<StatAop> add_list) {
		if(add_list!=null && add_list.size()>0){
			for(StatAop className:add_list){
				className.setEnable("1");
				save(className);
			}
		}
	}

	@Override
	public void deleteByList(List<StatAop> del_list) {
		if(del_list!=null && del_list.size()>0){
			StringBuilder classNameBatchInfo = new StringBuilder();
			for(StatAop className:del_list){
				classNameBatchInfo.append(className.getClassName()).append(",");
			}
			batchDelete(classNameBatchInfo.toString());
		}
		
	}

}
