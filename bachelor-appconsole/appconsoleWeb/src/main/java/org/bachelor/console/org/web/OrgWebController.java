/*
 * @(#)OrgWebController.java	Mar 15, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.console.org.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.bachelor.ext.org.domain.OrgExt;
import org.bachelor.ext.org.domain.UserExt;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;
import org.bachelor.org.service.IOrgService;
import org.bachelor.org.service.IUserService;
import org.bachelor.org.vo.OrgVo;
import org.bachelor.web.ztree.ZTreeTreeNode;
import org.bachelor.web.ztree.ZTreeTreeUtil;

/**
 * @author Team Bachelor
 *
 */
@Controller
@RequestMapping("/org/")
public class OrgWebController { 
	
	@Autowired
	private IOrgService orgService = null;

	@Autowired
	private IUserService userService = null;
	
	@RequestMapping("index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("org/index");
		return mav;
	}
	
	@RequestMapping("list")
	public @ResponseBody List<ZTreeTreeNode> list(@RequestParam(value="parentId", required=false) String parentId){
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		if(StringUtils.isEmpty(parentId)){
			List<Org> orgList = orgService.findAllCompany();
			List<ZTreeTreeNode> companyNodeList = null;//  OperamaskUITreeUtil.toTreeNode(orgList);
			nodeList.addAll(companyNodeList);
		}
		return nodeList;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("orglist")
	public @ResponseBody List<ZTreeTreeNode> findOrgInfo(String id,String flag){
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		if(!StringUtils.isEmpty(id)){
				String ids[] = id.split(",");
				id = ids[ids.length-1];
		}
		List<Org> orgList = orgService.findOrgsById(id,flag);
		List<ZTreeTreeNode> companyNodeList = ZTreeTreeUtil.toZTreeNode(orgList,new OrgZTreeAdapter());
		nodeList.addAll(companyNodeList);
		return nodeList;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("showOrgs")
	@ResponseBody
	public List<ZTreeTreeNode> findShowOrgTreeInfo(){
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		List<Org> orgList = orgService.findShowOrgTreeInfo(null);
		List<ZTreeTreeNode> companyNodeList = ZTreeTreeUtil.toZTreeNode(orgList,new OrgZTreeAdapter());
		nodeList.addAll(companyNodeList);
		return nodeList;
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(value="id")String id){
		Org org = orgService.findAllById(id);
		ModelAndView mav = new ModelAndView("org/detail","model",org);
		return mav;
	}
	
	@RequestMapping("info/update")
	@ResponseBody
	public Map<String,Object> updateOrgInfo(Org org){
		Map<String,Object> update_map = new HashMap<String,Object>();
		try{
			Org temp_org = orgService.findAllById(org.getId());
			/**获取记录原始showType**/
			update_map.put("showType", temp_org.getFlag());
			
			temp_org.setName(org.getName());
			temp_org.setShortName(org.getShortName());
			temp_org.setFlag(org.getFlag());
			temp_org.setShowOrder(org.getShowOrder());
			temp_org.setUpdateTime(new Date());
			if(temp_org.getOrgExt()==null || temp_org.getOrgExt().getId()==null ||
					"".equals(temp_org.getOrgExt().getId())){
				OrgExt orgExt = new OrgExt();
				orgExt.setId(temp_org.getId());
				temp_org.setOrgExt(orgExt);
			}
			orgService.update(temp_org);
			
			update_map.put("resultCode", "0");
		}catch(Exception e){
			update_map.put("resultCode", "1");
			e.printStackTrace();
		}
		return update_map;
	}
	
	/**
	 * 功能: 查询所有二级单位
	 * 作者 曾强 2013-5-22下午04:47:16
	 * @param org
	 * @return
	 */
	@RequestMapping("secondUnit")
	@ResponseBody
	public List<Org> findSecondDeptInfo(@RequestParam(value = "id")String id){
		List<Org> org_list = orgService.findSubOrg(id);//"0600000000"
		
		return org_list;
	}
	
	/**
	 * 功能: 测试查询所有组织信息   -- 测试
	 * 作者 曾强 2013-5-9下午03:09:41
	 * @param org
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public Map<String,Org> findOrgInfo(Org org){
		Org tempOrg = orgService.findById("0613290000");
		List<Org> org_list = orgService.findSubOrg("0612000000");
		Org torg = orgService.findCompany("0612000000");
		Org dorg = orgService.findDepartment("0612720300");
		Map<String,Org> re_map = new HashMap<String,Org>();
		re_map.put("t_map", tempOrg);
		return re_map;
	}
	
	/**
	 * 功能: USER -- 测试
	 */
	@RequestMapping("pu")
	public void processUserInfo(){
		User user = new User();
		Org org = new Org();
		user.setId("1002201");
		org.setId("0615260300");
		 
		user.setUsername("江南才子1");
		user.setOwnerOrg(org);
		userService.saveOrUpdate(user);
		
		User tuser = userService.findById("1");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@");
	}
}
