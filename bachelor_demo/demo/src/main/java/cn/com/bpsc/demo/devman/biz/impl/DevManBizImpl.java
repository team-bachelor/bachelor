package org.bachelor.demo.devman.biz.impl;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.demo.devman.biz.IDevManBiz;
import org.bachelor.demo.devman.bp.DevManBpDataEx;
import org.bachelor.demo.devman.dao.IDevManDao;
import org.bachelor.demo.devman.domain.DevManDomain;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.IContextService;
import org.bachelor.org.domain.User;

@Service
public class DevManBizImpl implements IDevManBiz{

	//private static final String PROCESS_NAME = "mantProcess";
	private static final String PROCESS_NAME = "mant2";

	@Autowired
	private IDevManDao devManDao;
	
	@Autowired
	private IBpmContextService bpmCtxService;
	
	@Autowired
	private IContextService ctx;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public List<TaskEx> getWaitTask() {
		User user = ctx.getLoginUser();
		List<TaskEx> tasks = bpmCtxService.getTaskByCandidateUser(PROCESS_NAME, user.getId());
		return tasks;
	}

	@Override
	public List<TaskEx> getClaimedTask() {
		User user = ctx.getLoginUser();
		List<TaskEx> tasks = bpmCtxService.getTaskByAssignee(PROCESS_NAME, user.getId());
		return tasks;
	}

	@Override
	public void startBp() {
		User user = ctx.getLoginUser();
		DevManDomain devMan = new DevManDomain();
		devManDao.save(devMan);
		
		DevManBpDataEx bpDataEx = new DevManBpDataEx();
		bpDataEx.setDomainId(devMan.getId());
		bpDataEx.setDwId("0664000000");//信通公司
		
		bpDataEx.setVerifyRoles("devVerifyGroup");
		
		bpmCtxService.startProcessInstanceByKey(PROCESS_NAME, bpDataEx);
	}

	@Override
	public void apply(DevManDomain devMan) {
		devManDao.update(devMan);
		bpmCtxService.complete();
	}

	@Override
	public DevManDomain findById(String id) {
		return devManDao.findById(id);
	}

	@Override
	public void verify(DevManDomain devMan) {
		devManDao.update(devMan);
		DevManBpDataEx bpDataEx = (DevManBpDataEx)bpmCtxService.getBpDataEx();
		String verifyResult = "1";
		if(devMan.getVerifyContent().equals("ok")){
			verifyResult = "0";
		}else{
			verifyResult = "1";
		}
		bpmCtxService.setPiVariable(bpDataEx.getTaskEx().getTask().getExecutionId(), "verifyResult", verifyResult);		
		bpmCtxService.completeReview(bpDataEx.getTaskEx().getTask().getId(), devMan.getVerifyContent(), "", devMan.getMemo(), verifyResult);
	}

	@Override
	public void endBp() {
		BaseBpDataEx bpDataEx = bpmCtxService.getBpDataEx();
		bpmCtxService.deleteProcessInstance(bpDataEx.getPiId(), "没有原因");
	}

}
