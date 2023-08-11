package org.bachelor.stat.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.stat.dao.ICountMenuFunctionDao;
import org.bachelor.stat.service.ICountMenuFunctionService;
import org.bachelor.stat.vo.CountConditionVo;

@Service
public class CountMenuFunctionServiceImpl implements ICountMenuFunctionService{

	@Autowired
	private ICountMenuFunctionDao countMenuFunctionDao = null;
	
	@Override
	public List countSystemRunEfficiency(CountConditionVo cc) {
		 
		return countMenuFunctionDao.countSystemRunEfficiency(cc);
	}

	@Override
	public void save(StatisticController sc) {
 
		countMenuFunctionDao.saveInfo(sc);
	}

}
