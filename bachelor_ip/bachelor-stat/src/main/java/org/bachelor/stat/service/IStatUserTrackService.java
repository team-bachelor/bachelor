package org.bachelor.stat.service;

import java.util.List;

import org.bachelor.stat.domain.StatUserTrack;

public interface IStatUserTrackService {
	
	public void save(StatUserTrack sut);
	
	public void saveUserTrackFuncEntity(StatUserTrack sut);
	
	public StatUserTrack getLasted();
	
	public List<StatUserTrack> getLasted(int num);
	
	public StatUserTrack getPre();
}
