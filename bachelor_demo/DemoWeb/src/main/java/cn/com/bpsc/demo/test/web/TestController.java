package org.bachelor.demo.test.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.demo.risklevel.bp.PNRiskLevelBpDataEx;
import org.bachelor.demo.risklevel.vo.BaseBpDataExVo;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.facade.service.IBpmContextService;
import org.bachelor.facade.service.ITaskGraphicDataService;
import org.bachelor.org.dao.IUserDao;
import org.bachelor.org.domain.User;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private IBpmRuntimeTaskService bpmTaskRuntime;
	
	@Autowired
	private IBpmRuntimeService bpmRuntime;
	
	@Autowired
	private IBpmContextService bpmCtx;
	
	@Autowired
	private ITaskGraphicDataService taskGraphicDataService;
	
	@Autowired
	private IUserDao userDao;
	
	@RequestMapping("pd")
	@ResponseBody
	public String pd(String pdId){
		bpmTaskRuntime.getFirstTask(pdId);
		
		return "ok";
	}
	
	@RequestMapping("allUser")
	@ResponseBody	
	public List<User> allUser(){
		return userDao.findAll();
	}
	
	
	@RequestMapping("assignee")
	@ResponseBody
	public List<User> assignee(String bizKey){
		return bpmCtx.getTaskCandidateUserByBizKey(bizKey);
		
	}
	
	@RequestMapping("findGraphicByBizKey")
	@ResponseBody
	public Map<String,Object> findGraphicByBizKey(String bizKey){
		return taskGraphicDataService.findByBizKey(bizKey);
	}
	
	@RequestMapping("getBpDataEx")
	@ResponseBody
	public List<BaseBpDataExVo> getBpDataEx(String candidateUserId, String pdKey){
		Long startTime = System.currentTimeMillis();
		List<BaseBpDataEx> bpDataExs = bpmTaskRuntime.getBpDataExByCandidateUser(pdKey,candidateUserId);
		System.out.println("getBpDataExByCandidateUser方法消耗时间:"+(System.currentTimeMillis()-startTime)+"毫秒\n返回集合数值:"+bpDataExs.size());
		BaseBpDataExVo bpDataExVo ;
		List<BaseBpDataExVo> bpDataExVos = new ArrayList<BaseBpDataExVo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(BaseBpDataEx bpDataEx : bpDataExs){
			bpDataExVo = new BaseBpDataExVo();
			bpDataExVo.setPiId(bpDataEx.getPiId());
			bpDataExVo.setTaskCreateTime(sdf.format(bpDataEx.getTaskEx().getTask().getCreateTime()));
			bpDataExVo.setTaskName(bpDataEx.getTaskEx().getTask().getName());
			bpDataExVos.add(bpDataExVo);
		}
		return bpDataExVos;
	}
	
	@RequestMapping("getBpDataExByAssignee")
	@ResponseBody
	public List<BaseBpDataExVo> getBpDataExByAssignee(String assignee, String pdKey){
		Long startTime = System.currentTimeMillis();
		List<BaseBpDataEx> bpDataExs = bpmTaskRuntime.getBpDataExByAssignee(pdKey,assignee);
		System.out.println("getBpDataExByCandidateUser方法消耗时间:"+(System.currentTimeMillis()-startTime)+"毫秒\n返回集合数值:"+bpDataExs.size());
		BaseBpDataExVo bpDataExVo ;
		List<BaseBpDataExVo> bpDataExVos = new ArrayList<BaseBpDataExVo>();
		for(BaseBpDataEx bpDataEx : bpDataExs){
			bpDataExVo = new BaseBpDataExVo();
			bpDataExVo.setPiId(bpDataEx.getPiId());
			bpDataExVo.setTaskName(bpDataEx.getTaskEx().getTask().getName());
			bpDataExVos.add(bpDataExVo);
		}
		return bpDataExVos;
	}
	
	@RequestMapping("encoding/show")
	public ModelAndView encodingShow(){
		return new ModelAndView("test/encoding");
	}
	
	@RequestMapping("encoding/get")
	public ModelAndView encodingGet(String userId1,String userName1){
		
		/**
		 * 
		 <Connector port="8080" protocol="HTTP/1.1" 
               connectionTimeout="20000" 
               redirectPort="8443" URIEncoding="utf-8" useBodyEncodingForURI="true"/>
		 * 
		 * 
		 */
		
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("userName1", userName1);
		map.put("userId1", userId1);
		ModelAndView mav =  new ModelAndView("test/encoding","model1",map);
		return mav;
	}
	
	@RequestMapping("encoding/post")
	public ModelAndView encodingPost(String userId2, String userName2){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("userName2", userName2);
		map.put("userId2", userId2);
		ModelAndView mav =  new ModelAndView("test/encoding","model2",map);
		
		return mav;
	}
	
	@RequestMapping("cadidate")
	@ResponseBody
	public List<User> getTaskCandidate(String piId,String taskDefKey){
		//piId = "12101"
		//taskDefKey = "multiReview";
		List<User> users = bpmRuntime.getTaskCandidateUserByDefKey(piId, taskDefKey);
		return users;
	}
	
	@RequestMapping("setbp")
	@ResponseBody
	public String setBpDataEx(){
		PNRiskLevelBpDataEx bpdata = (PNRiskLevelBpDataEx)bpmRuntime.getBpDataEx("12101");
		ArrayList<String> list = new ArrayList<String>(1);
		list.add("test");
		bpdata.setAssigneeList(list);
		//bpmRuntime.complete();
		return "ok";
	}
	
	@RequestMapping("getbp")
	@ResponseBody
	public PNRiskLevelBpDataEx getBpDataEx(){
		PNRiskLevelBpDataEx bpdata = (PNRiskLevelBpDataEx)bpmRuntime.getBpDataEx("12101");
		
		return bpdata;
	}
}
