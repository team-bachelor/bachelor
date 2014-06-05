package org.bachelor.demo.risklevel.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.demo.risklevel.biz.IPNRiskLevelBiz;
import org.bachelor.demo.risklevel.dao.IPNRiskLevelDao;
import org.bachelor.demo.risklevel.domain.PNRiskLevel;

@Service
public class PNRiskLevelBizImpl implements IPNRiskLevelBiz {

	@Autowired
	private IPNRiskLevelDao riskLevelDao;
	
	@Override
	public void save(PNRiskLevel riskLevel) {
		 
		riskLevelDao.save(riskLevel);
	}

	@Override
	public void update(PNRiskLevel riskLevel) {
			
		riskLevelDao.update(riskLevel);
	}

	@Override
	public void delete(PNRiskLevel riskLevel) {
			
		riskLevelDao.delete(riskLevel);
	}

	@Override
	public PNRiskLevel findById(String id) {
		 
		return riskLevelDao.findById(id);
	}

	@Override
	public List<PNRiskLevel> findAll() {
		 
		return riskLevelDao.findAll();
	}

	@Override
	public void saveOrUpdate(PNRiskLevel riskLevel) {
		
		riskLevelDao.saveOrUpdate(riskLevel);
	}

}
