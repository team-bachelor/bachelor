package org.bachelor.stat.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.stat.dao.IStatUserTrackDao;
import org.bachelor.stat.domain.StatUserTrack;

@Repository
public class StatUserTrackDaoImpl extends GenericDaoImpl<StatUserTrack, String> implements IStatUserTrackDao {
 
}
