package org.bachelor.demo.bpdemo;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.demo.bpdemo.bp.TaskFormPropBpDataEx;
import org.bachelor.facade.service.IBpmContextService;

@Controller
@RequestMapping("bpdemo")
public class BpDemoController {

	@Autowired
	private IBpmContextService bpmCtx;
	
	@Autowired
	private FormService formService;
	
	@RequestMapping("task/form_prop/init")	
	public ModelAndView taskFormPropInit(){
		ModelAndView mav = new ModelAndView("bpdemo/task/formProp/init");
		TaskFormPropBpDataEx bpInfoEx = new TaskFormPropBpDataEx();
		String bizKey = String.valueOf(System.currentTimeMillis());
		bpInfoEx.setDomainId(bizKey);
		bpmCtx.startProcessInstanceByKey("formDemo", bpInfoEx);
		
		bpInfoEx = (TaskFormPropBpDataEx)bpmCtx.getBpDataExByBizKey(bizKey);
		TaskFormData formData = formService.getTaskFormData(bpInfoEx.getTaskEx().getTask().getId());
		List<FormProperty> formProp = formData.getFormProperties();
		
		return mav;
	}
	
	@RequestMapping("task/form_prop/show")	
	public ModelAndView taskFormPropShow(String bizKey){
		
		TaskFormPropBpDataEx bpInfoEx = (TaskFormPropBpDataEx)bpmCtx.getBpDataEx();
		TaskFormData formData = formService.getTaskFormData(bpInfoEx.getTaskEx().getTask().getId());
		List<FormProperty> formProp = formData.getFormProperties();
		
		ModelAndView mav = new ModelAndView("bpdemo/task/formProp/show", "model", formProp);
		return mav;
	}
}
