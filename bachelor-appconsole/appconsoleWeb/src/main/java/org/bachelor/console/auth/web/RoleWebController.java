/*
 * @(#)RoleWebController.java	Mar 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.auth.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.auth.domain.Role;
import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.auth.service.IRoleGroupService;
import org.bachelor.auth.service.IRoleService;
import org.bachelor.auth.vo.RoleVo;
import org.bachelor.console.common.Constant;
import org.bachelor.gv.domain.GlobalVariable;
import org.bachelor.ps.domain.ProjectProperty;
import org.bachelor.ps.service.IProjectPropertyService;
import org.bachelor.web.ztree.ZTreeTreeNode;
import org.bachelor.web.ztree.ZTreeTreeUtil;

/**
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("/auth/role/")
public class RoleWebController {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IRoleService roleService = null;
	
	@Autowired
	private IRoleGroupService roleGroupService;
	
	@Autowired
	private IProjectPropertyService ppService;

	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("auth/role/index");
		return mav;
	}
	
	@RequestMapping("all.htm")
	public @ResponseBody Map<String,Object> queryAll(){
		List<Role> ruleList = roleService.findAllRoleInfo();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", (ruleList!=null?ruleList.size():0));
		map.put("rows", (ruleList!=null?ruleList:new ArrayList<GlobalVariable>()));
		return map;
	}

	/**
	 * 功能: 查询下拉框的角色信息
	 * 作者 曾强 2013-5-23下午06:47:35
	 * @return
	 */
	@RequestMapping("queryRole")
	public @ResponseBody List<RoleVo> querySelectRoleInfo(){
		List<Role> ruleList = roleService.findAllRoleInfo();
		List<RoleVo> ov_list = new ArrayList<RoleVo>();
		if(ruleList!=null && ruleList.size()>0){
			for(Role role : ruleList){
				RoleVo vo = new RoleVo();
				vo.setId(role.getId());
				vo.setDescription(role.getDescription());
				vo.setMemo(role.getMemo());
				vo.setName(role.getName());
				vo.setDescription(role.getDescription());
				ov_list.add(vo);
			}
		}
		return ov_list;
	}
	
	@RequestMapping("update.htm")
	@ResponseBody
	public Map<String, String> update(Role role) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		if(role != null){
			Role tempRole = roleService.findByName(role.getName());
			if(role.getDescription()!=null && !"".equals(role.getDescription())){
				//role.setDescription(new String(role.getDescription().getBytes("ISO-8859-1"),"UTF-8"));
			}
			if(role.getMemo()!=null && !"".equals(role.getMemo())){
				//role.setMemo(new String(role.getMemo().getBytes("ISO-8859-1"),"UTF-8"));
			}
			if(role.getId()==null || "".equals(role.getId())){
				if(tempRole!=null && !StringUtils.isEmpty(tempRole.getId())){
					map.put("ResultCode", "2");
				} else {
					roleService.saveOrUpdate(role);
					map.put("ResultCode", "0");
				}
			} else {
				if(tempRole!=null){
					tempRole.setName(role.getName());
					tempRole.setDescription(role.getDescription());
					tempRole.setMemo(role.getMemo());
					tempRole.setShowOrder(role.getShowOrder());
					tempRole.setGroup(role.getGroup());
					if(tempRole.getId().equals(role.getId())){
						roleService.saveOrUpdate(tempRole);
						map.put("ResultCode", "0");
					} else {
						map.put("ResultCode", "2");
					}
				} else {
					roleService.saveOrUpdate(role);
					map.put("ResultCode", "0");
				}
			}
			map.put("RoleId", role.getId());
		}else{
			map.put("ResultCode", "1");
		}
		return map;
	}
	
	@RequestMapping("delete.htm")
	@ResponseBody
	public Map<String, String> delete(String id){
		Map<String, String> map = new HashMap<String,String>();
		if(!StringUtils.isEmpty(id)){
			Role role  = new Role();
			role.setId(id);
			roleService.delete(role);
			map.put("ResultCode", "0");
		}else{
			map.put("ResultCode", "1");
		}
		
		return map;
	}	
	
	/**
	 * 功能: 查询项目树
	 * 作者 曾强 2013-4-17下午02:01:53
	 * @return
	 */
	@RequestMapping("tree")
	@ResponseBody
	public List<ZTreeTreeNode> allRoleGroup(){
		ProjectProperty pp = ppService.get();
		//根节点:项目名称
		ZTreeTreeNode root = new ZTreeTreeNode();
		root.setId("");
		root.setName(pp.getTitle());
		root.setHasChildren(true);
		root.setNodeType(Constant.ZTREE_NODE_TYPE_PROJECT);
		
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		nodeList.add(root);
		//子节点：各模块的层级信息
		List<RoleGroup> rgList = roleGroupService.findChildren(null);
	 	List<ZTreeTreeNode> moduleNodeList = ZTreeTreeUtil.toZTreeNode(rgList, new RoleZTreeAdapter());
		root.setChildren(moduleNodeList);
		return nodeList;
	} 
	
	@RequestMapping(value="addOrUpdateRoleGroup")
	@ResponseBody
	public Map<String,String> addOrUpdateRoleGroup(RoleGroup roleGroup){
		Map<String, String> map = new HashMap<String,String>();
		try{
			
			if(roleGroup!=null){
				if(roleGroup.getId().trim().equals("")){
					roleGroup.setId(null);
				}
				roleGroupService.saveOrUpdate(roleGroup);
				map.put("roleGroupId", roleGroup.getId());
				map.put("resultCode", "0");
			} else {
				map.put("resultCode", "1");
			}
		}catch(Exception e){
			log.info("添加角色组信息失败.",e);
			map.put("resultCode", "1");
		}
		return map;
	}
	
	/**
	 * 根据角色组ID查询角色组信息
	 * @param id
	 * @return
	 */
	@RequestMapping("createSaveRoleGroup")
	public ModelAndView findRoleGroup(String id,String type,String pid){
		RoleGroup rg = new RoleGroup();
		rg.setParentRoleGroup(new RoleGroup());
		if("add".equals(type)){
			rg.getParentRoleGroup().setId(pid);
		} else if("update".equals(type)){
			rg = roleGroupService.findById(id);
		}
		return new ModelAndView("/auth/role/roleGroup","roleGroup",rg);
	}
	
	/**
	 * 删除角色组信息
	 * @param id
	 * @return
	 */
	@RequestMapping("delRoleGroup")
	@ResponseBody
	public Map<String,String> delRoleGroup(String id){
		Map<String, String> map = new HashMap<String,String>();
		try{
			roleGroupService.deleteById(id);
			map.put("resultCode", "0");
		}catch(Exception e){
			log.info("删除角色组信息失败.",e);
			map.put("resultCode", "1");
		}
		return map;
	}
	
	/**
	 * 展示角色添加或者更新页面
	 * @param id
	 * @param type
	 * @param pid
	 * @return
	 */
	@RequestMapping("createRole")
	public ModelAndView findRole(String id,String type,@RequestParam(value="gid" ,required = false) String gid){
		Role role = new Role();
		if("add".equals(type)){
			RoleGroup roleGroup = new RoleGroup();
			roleGroup.setId(gid);
			role.setGroup(roleGroup);
		} else if("update".equals(type)){
			role = roleService.findById(id);
		}
		return new ModelAndView("/auth/role/role","model",role);
	}
	
}
