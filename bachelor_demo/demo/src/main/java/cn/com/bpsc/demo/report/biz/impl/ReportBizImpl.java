package org.bachelor.demo.report.biz.impl;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.demo.report.biz.IReportBiz;
import org.bachelor.demo.report.bp.ReportBpEx;
import org.bachelor.demo.report.dao.IReportDao;
import org.bachelor.demo.report.domain.Report;
import org.bachelor.auth.common.AuthConstant;
import org.bachelor.bpm.common.Constant;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.context.service.IVLService;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.org.domain.User;

@Service
public class ReportBizImpl implements IReportBiz{

	@Autowired
	private IReportDao reportDao;
	
	@Autowired
    private IBpmContextService bpmContextService;
	
	@Autowired
	private IBpmRuntimeService runtimeService;
	
	@Autowired
	private ProcessEngineFactoryBean fac;
	
	@Autowired
	private IVLService vlService;
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void save(Report report) {
		reportDao.save(report);
	}

	@Override
	public void update(Report report,String taskId,String comment) {
		reportDao.update(report);
		if(StringUtils.isNotBlank(comment)){
			User user = (User)vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
			TaskEx taskEx=bpmContextService.getTask(taskId, user.getId(),Constant.BPM_ASSIGNMENT_TYPE_ASSIGNED);
			bpmContextService.addComment(taskId, taskEx.getTask().getProcessInstanceId(), comment);
		}
		bpmContextService.completeTask();
	}

	@Override
	public void startVp() {
		
	
		Report report = new Report();
		save(report);
		ReportBpEx bpEx = new ReportBpEx();
		bpEx.setDomainId(report.getId());
		ProcessInstance pi = bpmContextService.startProcessInstanceByKey("vocationProcess", bpEx);
	}

	@Override
	public List<TaskEx> queryWaitTask(String userId) {
		List<TaskEx> taskList =  bpmContextService.getTaskByCandidateUser("vocationProcess",userId);
		return taskList;
	}
	
	@Override
	public List<TaskEx> queryMyTask(String userId) {
		List<TaskEx> taskList = bpmContextService.getTaskByAssignee("vocationProcess",userId);
		return taskList;
	}

	@Override
	public void task(String taskId,String userId) {
		bpmContextService.setAssignee(taskId, userId);
	}

	@Override
	public String getIdByTaskId(String taskId) {
		User user = (User)vlService.getSessionAttribute(AuthConstant.LOGIN_USER);
		TaskEx taskEx=bpmContextService.getTask(taskId, user.getId(),null);
		runtimeService.getNextTaskCandidateUser();
		///
		return taskEx.getTask().getId();
	}

	@Override
	public Report findById(String domainId) {
		return this.reportDao.findById(domainId);
	}

}
