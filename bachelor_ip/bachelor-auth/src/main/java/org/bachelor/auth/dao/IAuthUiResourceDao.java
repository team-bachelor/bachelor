package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.dao.IGenericDao;

public interface IAuthUiResourceDao  extends IGenericDao<AuthUiResource, String>{
 
	public void deleteById(String id);
	
	public void batchDelete(String[] dSQL);
	
	public List<AuthUiResourceVo> findAuthUiResourceVoInfo(String type,String funcId,String funcUrl,String id,String pid,String taskId,
			AuthUiResourceVo aur,List<String> roleIds,List<Role> roles);
	
	/**
	 * 根据功能ID，UI定义ID查询功能权限信息
	 * @param funcId
	 * @param uiId
	 * @return
	 */
	public List<AuthUiResource> findByFunctIdAndUiId(String funcId,String uiId);
}
