package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.dao.IGenericDao;
import org.bachelor.gv.vo.GlobalEnumTvVo;

public interface IAuthFunctionDao extends IGenericDao<AuthFunction, String>{
	
	public void deleteAuthFunctionInfo(AuthFunction af);
	
	public List<AuthFunction> findAllAuthFuncByFuncId(AuthFunction af);
	
	public List<GlobalEnumTvVo> findTvInfo(String funcId);
	
	/**
	 * 批量保存功能权限信息
	 * @param iSQL
	 */
	public void batchSaveAuthFunction(String iSQL[]);
	
	/**
	 * 查询角色功能,根据功能ID拼合数组
	 * @param ids
	 * @return
	 */
	public List<AuthFunction> findByFuncIds(String ids);
}
