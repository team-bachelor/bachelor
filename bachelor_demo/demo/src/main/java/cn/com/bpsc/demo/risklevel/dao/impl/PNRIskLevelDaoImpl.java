package org.bachelor.demo.risklevel.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.demo.risklevel.dao.IPNRiskLevelDao;
import org.bachelor.demo.risklevel.domain.PNRiskLevel;
import org.bachelor.dao.impl.GenericDaoImpl;

@Repository
public class PNRIskLevelDaoImpl extends GenericDaoImpl<PNRiskLevel, String>
		implements IPNRiskLevelDao {
 
}
