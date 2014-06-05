package org.bachelor.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.StringUtils;

import org.bachelor.auth.common.AuthConstant;
import org.bachelor.auth.dao.IAuthUiResourceDao;
import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthUiResourceService;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.context.service.IVLService;

@Service
@Controller
@RequestMapping("/auth/ui/")
public class AuthUiResourceServiceImpl implements IAuthUiResourceService {

	@Autowired
	private IAuthUiResourceDao uiDao;
	
	@Autowired
	private  IVLService vlService;
	
	@Override
	public void add(AuthUiResource resource) {
		
		uiDao.save(resource);
	}

	@Override
	public void addOrUpdate(AuthUiResource resource) {
			
		uiDao.saveOrUpdate(resource);
	}

	@Override
	public void delete(AuthUiResource resource) {
		
		uiDao.delete(resource);
	}

	@Override
	public void deleteById(String id) {
		
		uiDao.deleteById(id);
	}

	@Override
	public List<AuthUiResource> findAll() {
		
		return uiDao.findAll();
	}

	/**
	 * 根据褓对象查询
	 */
	@RequestMapping("findByExample")
	@ResponseBody
	public List<AuthUiResourceVo> findByExample(AuthUiResourceVo aur) {
		
		return uiDao.findAuthUiResourceVoInfo("findByExample", null, null, null,null, null, aur, null, null);
	}

	/**
	 * 根据功能URL和权限ID(字符集合形式)查询
	 */
	@RequestMapping("findByFuncUrlAndRoleIds")
	@ResponseBody
	public List<AuthUiResourceVo> findByFuncUrlAndRoleIds(String funcUrl) {
		List<Role> role_list = (ArrayList<Role>)vlService.getSessionAttribute(AuthConstant.LOGIN_USER_ROLE);
		if(role_list!=null && role_list.size()>0){
			List<AuthUiResourceVo> list = uiDao.findAuthUiResourceVoInfo("findByFuncUrlAndRoleIds", null, funcUrl, null, null,null, null, getRoleIdsList(role_list), null);
			// BpDataEx
			return list;
		} else {
			return null;
		}
	}
	
	public List<String> getRoleIdsList(List<Role> role_list){
		List<String> temp_list = new ArrayList<String>();
		for(Role role : role_list){
			temp_list.add(role.getId());
		}
		return temp_list;
	}
 
	/**
	 * 根据ID查询
	 */
	@RequestMapping("findById")
	@ResponseBody
	public AuthUiResourceVo findById(String id) {
		List<AuthUiResourceVo> vo_list = uiDao.findAuthUiResourceVoInfo("findById", null, null, id, null,null,null, null, null);
		return (vo_list!=null?(vo_list.size()>0?(vo_list.get(0)):null):null);
	}

	@Override
	public void update(AuthUiResource resource) {
	 
		uiDao.update(resource);
	}

	@Override
	public List<AuthUiResourceVo> findByFuncId(String funcId) {
		return uiDao.findAuthUiResourceVoInfo("findByFuncId", funcId, null, null, null,null, null, null, null);
	}

	@Override
	public void batchDelete(String info) {
		String dInfo[] = info.split(",");
		String dSQL[] = new String[dInfo.length];
		int index = 0;
		for(String tempInfo:dInfo){
			dSQL[index] = "delete from T_UFP_AUTH_UI_RESOURCE where id='"+tempInfo+"'";
			index ++;
		}
		uiDao.batchDelete(dSQL);
	}
}
