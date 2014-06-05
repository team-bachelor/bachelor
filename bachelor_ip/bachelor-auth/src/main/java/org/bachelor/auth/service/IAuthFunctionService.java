package org.bachelor.auth.service;

import java.util.List;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.gv.vo.GlobalEnumTvVo;

public interface IAuthFunctionService {
	
	public void saveOrUpdate(String authFuncs);
	
	public List<AuthFunction> findAll();
	
	public AuthFunction findAuthFunctionById(String id);
	
	public List<AuthFunction> findAuthFunctionByFuncId(AuthFunction af);
	
	public void deleteAuthFunctionInfo(AuthFunction auth);
	
	public List<GlobalEnumTvVo> findTvInfo(String funcId);
	
	/**
	 * 批量保存功能权限信息
	 * @param iSQL
	 */
	public void batchSaveAuthFunction(List<AuthFunction> afs);
	
	/**
	 * 查询角色功能,根据功能ID拼合数组
	 * @param ids
	 * @return
	 */
	public List<AuthFunction> findByFuncIds(String ids);
}
