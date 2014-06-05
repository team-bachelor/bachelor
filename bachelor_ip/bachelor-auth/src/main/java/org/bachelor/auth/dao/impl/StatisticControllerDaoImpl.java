package org.bachelor.auth.dao.impl;

import org.springframework.stereotype.Service;

import org.bachelor.auth.dao.IStatisticControllerDao;
import org.bachelor.auth.domain.StatisticController;
import org.bachelor.dao.impl.GenericDaoImpl;

@Service
public class StatisticControllerDaoImpl  extends GenericDaoImpl<StatisticController, String> implements IStatisticControllerDao{

}
