package org.bachelor.demo.report.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.demo.report.biz.IReportBiz;
import org.bachelor.demo.report.domain.Report;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.facade.service.ContextHelper;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.IContextService;

@Controller
@RequestMapping("report")
public class ReportController {
	
	@Autowired
	private IReportBiz reportBiz;
	
	@Autowired
	private IContextService ctxService;
	
	@Autowired
	private IBpmContextService bpmCtxService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		List<TaskEx> taskList = reportBiz.queryWaitTask(ctxService.getLoginUser().getId());
		List<TaskEx> taskList2 = reportBiz.queryMyTask(ctxService.getLoginUser().getId());
		
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("waitTasks", taskList);
		model.put("myTasks", taskList2);
		return new ModelAndView("report/index", "model", model);
	}

	
	@RequestMapping("take")
	public ModelAndView take(){
		String taskId = bpmCtxService.getBpDataEx().getTaskEx().getTask().getId();
		reportBiz.task(taskId, ctxService.getLoginUser().getId());
		return new ModelAndView("redirect:index.htm");
	}

	@RequestMapping("start")
	public ModelAndView start(){
		reportBiz.startVp();
		return new ModelAndView("redirect:index.htm");
	}
	
	@RequestMapping("write")
	public ModelAndView write(){
		Map<String,Object> map = new HashMap<String,Object>();
		String taskId = bpmCtxService.getBpDataEx().getTaskEx().getTask().getId();
		String domainId = reportBiz.getIdByTaskId(taskId);
		map.put("domainId", domainId);
		map.put("task_id", taskId);
		return new ModelAndView("report/writeReport","model",map);
	}
	
	@RequestMapping("doWrite")
	public ModelAndView doWrite(Report report, @RequestParam("task_id") String task_id){
		reportBiz.update(report,task_id,null);
		return new ModelAndView("redirect:index.htm");
	}
	
	@RequestMapping("verify")
	public ModelAndView confirm(){
		Map<String,Object> map = new HashMap<String,Object>();
		String taskId = bpmCtxService.getBpDataEx().getTaskEx().getTask().getId();
		String domainId = reportBiz.getIdByTaskId(taskId);
		Report report = reportBiz.findById(domainId);
		map.put("domain", report);
		map.put("task_id", taskId);
		return new ModelAndView("report/verify","model",map);
	}
	
	@RequestMapping("doVerify")
	public ModelAndView doVerify(Report report, @RequestParam("comment") String comment){
		String taskId = bpmCtxService.getBpDataEx().getTaskEx().getTask().getId();
		reportBiz.update(report,taskId,comment);
		
		return new ModelAndView("redirect:index.htm");
	}
}
