package org.bachelor.stat.service;

import java.util.List;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.stat.vo.CountConditionVo;

@SuppressWarnings("unchecked")
public interface ICountMenuFunctionService {

	public List countSystemRunEfficiency(CountConditionVo cc);
	
	public void save(StatisticController sc);
}
