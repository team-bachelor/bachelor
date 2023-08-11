package org.bachelor.stat.dao;


import java.util.List;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.dao.IGenericDao;
import org.bachelor.stat.vo.CountConditionVo;

public interface ICountMenuFunctionDao extends IGenericDao<StatisticController, String>{

	public void saveInfo(StatisticController sc) ;
	
	public List countSystemRunEfficiency(CountConditionVo cc);
}
