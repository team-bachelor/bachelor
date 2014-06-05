package org.bachelor.demo.report.biz;

import java.util.List;

import org.activiti.engine.task.Task;

import org.bachelor.demo.report.domain.Report;
import org.bachelor.bpm.domain.TaskEx;

public interface IReportBiz {

	public void save(Report report);
	
	public void update(Report report,String taskId, String comment);
	
	public void startVp();
	
	public List<TaskEx> queryWaitTask(String userId);
	
	public List<TaskEx> queryMyTask(String userId);
	
	public void task(String taskId,String userId);
	
	public String getIdByTaskId(String taskId);

	public Report findById(String domainId);
}
