/*
 * @(#)MenuWebController.java	Mar 7, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.ps.web;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.console.common.Constant;
import org.bachelor.ps.domain.BusinessDomain;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.domain.ProjectInfo;
import org.bachelor.ps.domain.ProjectModule;
import org.bachelor.ps.domain.ProjectModuleExt;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IBusinessDomainService;
import org.bachelor.ps.service.IFunctionService;
import org.bachelor.ps.service.IProjectInfoService;
import org.bachelor.ps.service.IProjectModuleExtService;
import org.bachelor.ps.service.IProjectModuleService;
import org.bachelor.ps.service.IProjectPropertyService;
import org.bachelor.web.ztree.ZTreeTreeNode;
import org.bachelor.web.ztree.ZTreeTreeUtil;

/**
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("/ps/")
public class PSWebController {
	
	@Autowired
	private IProjectInfoService piService;
	
	@Autowired
	private IProjectPropertyService ppService;
	
	@Autowired
	private IProjectModuleService pmService;
	
	@Autowired
	private IBusinessDomainService bdService;
	
	@Autowired
	private IFunctionService funcService;
	
	@Autowired
	private IProjectModuleExtService pmeService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("ps/index");
		return mav;
	}
	
	@RequestMapping("allModule")
	@ResponseBody 
	public List<ZTreeTreeNode> getAllModule(){
		
		ProjectInfo pi = piService.findPI();
		//根节点:项目名称
		ZTreeTreeNode root = new ZTreeTreeNode();
		root.setName(pi.getPp().getName());
		root.setHasChildren(true);
		root.setNodeType(Constant.ZTREE_NODE_TYPE_PROJECT);
		
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
//		nodeList.add(root);
//		//子节点：各模块的层级信息
//		List<ProjectModule> pmList = pmService.findChildren(null);
//		List<OperamaskUITreeNode> moduleNodeList = OperamaskUITreeUtil.toTreeNode(pmList);
//		root.setChildren(moduleNodeList);
		return nodeList;
	}
	
	@RequestMapping("pp/property")
	public ModelAndView getProperty(){
		ProjectProperty  pp = ppService.get();
		if(pp.getPpbd()==null){
			pp.setPpbd(new BusinessDomain());
		}
		ModelAndView mav = new ModelAndView("ps/property", "model", pp);
		return mav;
	}
	
	@RequestMapping("pm/module")
	public ModelAndView module(@RequestParam(value="id") String id,@RequestParam("type") String type,
			@RequestParam("pidType") String pid){
		ProjectModule pm = new ProjectModule();
		pm.setParentModule(new ProjectModule());
		if(type.trim().equals("add")){
			pm.getParentModule().setId(pid);
		} else if(type.trim().equals("update")){
			pm = pmService.findByName(id);
		}
		ModelAndView mav = new ModelAndView("ps/module", "model", pm);
		return mav;
	}
	
	@RequestMapping("pm/delete")
	@ResponseBody
	public Map<String,String> deleteProjectModule(ProjectModule pm){
		pmService.deleteById(pm.getId());
 		//删除功能信息
//		ProjectModuleExt fnc = new ProjectModuleExt(); 
//		fnc.setModuleId(pm.getId());
//		pmeService.delete(fnc);
		 
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		return map;
	}
	
	@RequestMapping("pp/update")
	@ResponseBody
	public Map<String, String> updateProperty(ProjectProperty  pp){
		ppService.update(pp);
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		map.put("ppId", pp.getId());
		return map;
	}
	
	@RequestMapping("pp/delete")
	@ResponseBody
	public Map<String,String> deleteProperty(ProjectProperty pp){
		ppService.delete(pp);
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		return map;
	}

	@RequestMapping("pm/createAndUpdate")
	@ResponseBody
	public Map<String, String> createModule(ProjectModule pm){
		Map<String,String> map = new HashMap<String,String>();
	/*if(pm.getId()!=null && !pm.getId().equals("")){
			if(pm.getName()==null || pm.getName().equals("")){
				String pid = pm.getParentModule().getId().replace(",", "");
				pm = pmService.findByName(pm.getId());
				ProjectModule tempPm = new ProjectModule();
				tempPm.setId(pid);
				pm.setParentModule(tempPm);
			}
			pmService.update(pm);
		} else {
			pm.setId(null);
			pmService.save(pm);
		}*/
		map.put("resultId", "");
		if(pm!=null){
				if(StringUtils.isEmpty(pm.getId())){
					
					pm.setId(null);
				}
				/** 验证业务编码不为空 **/
				if(!StringUtils.isEmpty(pm.getCode())){
					ProjectModule projectModule = pmService.findByCode(pm.getCode());
					/** 当功能Code不为空时 **/
					if(projectModule!=null && !StringUtils.isEmpty(projectModule.getCode()) ){
						if(pm.getCode().equals(projectModule.getCode())){
							if(!StringUtils.isEmpty(pm.getId()) && pm.getId().equals(projectModule.getId())){
								projectModule.setCode(pm.getCode());
								pmService.saveOrUpdate(projectModule);
								map.put("resultCode", "0");
							} else {
								map.put("resultCode", "2");
							}
						} else {
							projectModule.setCode(pm.getCode());
							pmService.saveOrUpdate(projectModule);
							map.put("resultCode", "0");
						}
					} else {
						pmService.saveOrUpdate(pm);
						map.put("resultCode", "0");
					}
				} else {
					pmService.saveOrUpdate(pm);
					map.put("resultCode", "0");
				}
				map.put("resultId", pm.getId());
		} 
		return map;
	}
	
	@RequestMapping("pm/update")
	@ResponseBody
	public Map<String, String> updateModule(ProjectModule pm){
		
		pmService.update(pm);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		
		return map;
	}
	
	/**
	 * 功能: 查询项目树
	 * 作者 曾强 2013-4-17下午02:01:53
	 * @return
	 */
	@RequestMapping("pp/all")
	@ResponseBody
	public List<ZTreeTreeNode> allProject(){
		ProjectProperty pp = ppService.get();
		//根节点:项目名称
		ZTreeTreeNode root = new ZTreeTreeNode();
		root.setId(pp.getName());
		root.setName(pp.getTitle());
		root.setHasChildren(true);
		root.setNodeType(Constant.ZTREE_NODE_TYPE_PROJECT);
		
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		nodeList.add(root);
		//子节点：各模块的层级信息
		List<ProjectModule> pmList = pmService.findChildren(null); 
	 	List<ZTreeTreeNode> moduleNodeList = ZTreeTreeUtil.toZTreeNode(pmList, new ProjectModuleZTreeAdapter());
		root.setChildren(moduleNodeList);
		return nodeList;
	} 
	
	/**
	 * 功能: 查询业务域
	 * 作者 Team Bachelor 2013-4-17下午02:03:32
	 * @return
	 */
	@RequestMapping("bd/all")
	@ResponseBody
	public List<ZTreeTreeNode> allBd(){
		List<BusinessDomain> bdlist = bdService.findAll(); 
	 	List<ZTreeTreeNode> moduleNodeList = ZTreeTreeUtil.toZTreeNode(bdlist,new BusinessDomainZTreeAdapter()); 
		return moduleNodeList;
	}
	
	/**
	 * 功能: 查询业务域
	 * 作者 Team Bachelor 2013-4-17下午02:03:32
	 * @return
	 */
	@RequestMapping("bd/tree")
	@ResponseBody
	public List<ZTreeTreeNode> findAllBd(){
		List<BusinessDomain> bdlist = bdService.findAll(); 
		List<ZTreeTreeNode> moduleNodeList = ZTreeTreeUtil.toZTreeNode(bdlist,new BusinessDomainZTreeAdapter()); 
		return moduleNodeList;
	}
	
	@RequestMapping("bd/create")
	@ResponseBody
	public Map<String, Object> createBd(@RequestBody BusinessDomain bd){
		bdService.save(bd);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resultCode", "0");
		map.put("obj",bd);
		
		return map;
	}
	
	@RequestMapping("func/all")
	public ModelAndView funcs(@RequestParam(value="id") String id,@RequestParam("type") String type,
			@RequestParam("pidType") String pid){
		Function fnc = new Function();
		fnc.setModule(new ProjectModule());
		if(type.trim().equals("add")){
			fnc.getModule().setId(pid);
		} else if(type.trim().equals("update")){
			fnc = funcService.findById(id);
		}
		return new ModelAndView("ps/functions","model",fnc);
	}
	
	@RequestMapping("bd/delete")
	@ResponseBody
	public Map<String, String> deleteBd(@RequestParam(value="id")String id){
		bdService.deleteById(id);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		
		return map;
	}

	@RequestMapping("func/createAndUpdate")
	@ResponseBody
	public void createAndUpdateFunc(HttpServletResponse response,Function func){
		PrintWriter printWriter = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			printWriter = response.getWriter();
			if(func!=null){
				if(func.getId()==null || func.getId().equals("")){
					func.setId(null);
				}
				
				Function tempFunc = func;
				
				/**  **/
				if(!StringUtils.isEmpty(func.getId())
							&& !StringUtils.isEmpty(func.getName())){
					String pid = func.getModule().getId();
					func = funcService.findById(func.getId());
					ProjectModule pm = new ProjectModule();
					pm.setId(pid);
					func.setCode(tempFunc.getCode());
					func.setName(tempFunc.getName());
					func.setDescription(tempFunc.getDescription());
					func.setEntryPath(tempFunc.getEntryPath());
					func.setModule(pm);
				}
				
				/**  **/
				if(!StringUtils.isEmpty(func.getCode())){
					Function function = funcService.findByCode(func.getCode());
					/** 当功能Code不为空时 **/
					if(function!=null && !StringUtils.isEmpty(function.getCode()) ){
						if(func.getCode().equals(function.getCode())){
							if(!StringUtils.isEmpty(func.getId()) && func.getId().equals(function.getId())){
								function.setCode(func.getCode());
								
								funcService.saveOrUpdate(func);
								map.put("\"resultCode\"", "\"0\"");
							} else {
								map.put("\"resultCode\"", "\"2\"");
							}
						} else {
							function.setCode(func.getCode());
							funcService.saveOrUpdate(func);
							map.put("\"resultCode\"", "\"0\"");
						}
					} else {
						funcService.saveOrUpdate(func);
						map.put("\"resultCode\"", "\"0\"");
					}
				} else {
					funcService.saveOrUpdate(func);
					map.put("\"resultCode\"", "\"0\"");
				}
			}
			
			map.put("\"id\"","\""+func.getId()+"\"");
		}catch(Exception e){
			e.printStackTrace();
			map.put("\"resultCode\"", "\"1\"");
		}
		
		printWriter.write("<script>parent.funReturnMethod('"+map+"');</script>");
	}
	
	/**
	 * URL是否存在
	 * @param url
	 * @return
	 */
	@RequestMapping("isExistsUrl")
	@ResponseBody
	public Map<String,String> isExistsUrl(String url){
		Function func = funcService.findByUrl(url);
		Map<String,String> map = new HashMap<String,String>();
		if(func!=null && !StringUtils.isEmpty(func.getId())){
			map.put("resultCode", "1");
		} else {
			map.put("resultCode", "0");
		}
		return map;
	}
	
	@RequestMapping("func/delete")
	@ResponseBody
	public Map<String, String> deleteFunc(@RequestParam(value="id")String id){
		funcService.deleteById(id);
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		return map;
	}
	
	@RequestMapping("fp/delete")
	@ResponseBody
	public Map<String, String> deleteFp(@RequestParam(value="id")String id){
		/*fpService.deleteById(id);*/
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("resultCode", "0");
		
		return map;
	}
	
	@RequestMapping("pme/all")
	@ResponseBody
	public  Map<String,Object> allPme(ProjectModuleExt pme){
		List<ProjectModuleExt> pmelist = null;
		if(pme!=null && pme.getModuleId()!=null && !pme.getModuleId().equals("")){
			pmelist = pmeService.findModuleExt(pme);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", (pmelist!=null?pmelist.size():0));
		map.put("rows", (pmelist!=null?pmelist:new ArrayList<ProjectModuleExt>()));
		return map;
	}
	
	@RequestMapping("pme/update")
	@ResponseBody
	public Map<String,String> saveOrUpdate(ProjectModuleExt pme){
		Map<String,String> map = new HashMap<String,String>();
		try{
			if(pme!=null){
				if(pme.getId()!=null && pme.getId().equals("undefined")){
					pme.setId(null);
				}
				if(pme.getValue()!=null){
					pme.setValue(URLDecoder.decode(pme.getValue(),"UTF-8"));
				}
				if(pme.getDescription()!=null){
					pme.setDescription(URLDecoder.decode(pme.getDescription(),"UTF-8"));
				}
			}
			pmeService.saveOrUpdate(pme);
			map.put("result", "success");
		} catch(Exception e){
			map.put("result", "error");
		}
		return map;
	}
	
	@RequestMapping("pme/delete")
	@ResponseBody
	public Map<String,String> deletePmeInfo(ProjectModuleExt pme){
		Map<String,String> map = new HashMap<String,String>(); 
		try{
			pmeService.delete(pme);
			map.put("result", "success");
		} catch(Exception e){
			map.put("result", "error");
		}
		return map;
	}
}
