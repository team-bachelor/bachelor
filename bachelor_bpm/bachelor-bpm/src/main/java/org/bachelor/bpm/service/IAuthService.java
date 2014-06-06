package org.bachelor.bpm.service;

import java.util.List;

import org.bachelor.core.entity.IBaseEntity;


/**
 * 用于在流程中获取用户、权限等授权信息，需要由用户实现。
 * @author liuzhuo
 *
 */
public interface IAuthService {
	
	/**
	 * 通过用户id找到用户
	 * @param userId
	 * @return
	 */
	public IBaseEntity findUserById(String userId);
	
	/**
	 * 通过角色id找到角色
	 * @param roleId
	 * @return
	 */
	public IBaseEntity findRoleById(String roleId);
	
	/**
	 * 通过id找到相应的组织
	 * @param orgId
	 * @return
	 */
	public IBaseEntity findOrgById(String orgId);
	
	/**
	 * 通过用户id找到与用户相对应的权限
	 * @param userId
	 * @return
	 */
	public List<? extends IBaseEntity> findRolesByUserId(String userId);

	/**
	 * 
	 * @param roleOrgIds
	 * @return
	 */
	public List<? extends IBaseEntity> findUsersByRoleNameAndOrgId(
			String... roleOrgIds);
}
