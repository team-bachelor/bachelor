package org.bachelor.auth.service;

import java.util.List;


import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthUiResourceVo;

public interface IAuthUiResourceService {
	
	public void add(AuthUiResource resource);
	
	public void update(AuthUiResource resource);
	
	public void addOrUpdate(AuthUiResource resource);
	
	public void delete(AuthUiResource resource);
	
	public void deleteById(String id);
	
	public List<AuthUiResource> findAll();
	
	public AuthUiResourceVo findById(String id);
	
	public List<AuthUiResourceVo> findByFuncId(String funcId);
	
	public List<AuthUiResourceVo> findByExample(AuthUiResourceVo aur);
	
	public List<AuthUiResourceVo> findByFuncUrlAndRoleIds(String funcUrl);
	
	public void batchDelete(String info);
	
}
