package cn.org.bachelor.bpm.bpm.listener;


import java.util.List;

import cn.org.bachelor.bpm.bpm.service.IBpmRuntimeService;
import cn.org.bachelor.bpm.bpm.service.IBpmRuntimeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import cn.org.bachelor.bpm.common.BpmConstant;
import cn.org.bachelor.bpm.domain.BaseBpDataEx;
import cn.org.bachelor.bpm.domain.TaskCompletedEvent;
import cn.org.bachelor.bpm.domain.TaskEx;
import cn.org.bachelor.context.service.IVLService;

@Service
public class TaskCompletedListener implements ApplicationListener<TaskCompletedEvent>{
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	@Autowired
	private IBpmRuntimeTaskService bpmRuntimeTaskService;
	
	@Override
	public void onApplicationEvent(TaskCompletedEvent event) {
		BaseBpDataEx  bpDataEx=(BaseBpDataEx) event.getSource();
		BaseBpDataEx newBpDateEx = bpmRuntimeService.getBpDataEx(bpDataEx.getPiId(), bpDataEx.getLastOptUserId());
		if(newBpDateEx!=null && newBpDateEx.getPiId()!=null){
			List<TaskEx> tl = bpmRuntimeTaskService.getActiveTask(newBpDateEx.getPiId(), null);
			TaskEx taskEx = null;
			if(tl != null && !tl.isEmpty()){
				taskEx = tl.get(0);
			}
			newBpDateEx.setTaskEx(taskEx);
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, newBpDateEx);
		}else{
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, null);
		}
	
	}
	
}
