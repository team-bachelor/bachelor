package org.bachelor.bpm.dao;

import java.util.List;

import org.bachelor.bpm.domain.Notice;
import org.bachelor.dao.IGenericDao;

public interface INoticeDao extends IGenericDao<Notice, String>{

	/**
	 * 批量添加接收信息
	 * @param cSQL
	 */
	public void batchNoticeReceiver(String cSQL[]);
	
	/**
	 * 批量添加用户信息
	 * @param cSQL
	 */
	public void batchNoticeRole(String cSQL[]);
	
	/**
	 * 查询指定用户Id以及所具有角色相关的通知
	 * 
	 * @param userId 用户Id
	 * @return 通知实体List
	 */
	public List<Notice> queryByUserId(String userId);
	
	/**
	 * 查询指定角色Id相关的通知
	 * 
	 * @param roleId 角色Id
	 * @return 通知实体List
	 */
	public List<Notice> queryByRoleId(String roleId);
	
	/**
	 * 根据Notice实体查询Notice信息集合
	 * @param notice
	 * @return
	 */
	public List<Notice> queryByExample(Notice notice);
}
