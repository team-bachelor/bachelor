package org.bachelor.demo.devman.biz;

import java.util.List;

import org.bachelor.demo.devman.domain.DevManDomain;
import org.bachelor.bpm.domain.TaskEx;

public interface IDevManBiz {

	public void startBp();
	
	public void endBp();

	public List<TaskEx> getWaitTask();

	public List<TaskEx> getClaimedTask();

	public void apply(DevManDomain devMan);

	public DevManDomain findById(String id);

	public void verify(DevManDomain devMan);
}
