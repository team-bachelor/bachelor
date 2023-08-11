package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthRoleUserVo;
import org.bachelor.dao.IGenericDao;
import org.bachelor.org.domain.User;

public interface IAuthRoleUserDao extends IGenericDao<AuthRoleUser, String>{
 
	public List<AuthRoleUserVo> findByExample(AuthRoleUserVo aruVo);
	
	public List<Role> findByUserId(String userId);
	
	public void batchDelete(String[] dSQL); 
	
	public List<AuthRoleUser> findListByExample(AuthRoleUser aru);
	
	public List<User> findUsersByRoleIds(String roleTerm);
	
	public List<User> findUsersByRoleIdsAndOrgIds(String roleIds,String orgIds);
	
	public List<User> findUsersByRoleNamesAndOrgIds(String roleNames,String orgIds);
	
	/**
	 * 根据二级单位ID和角色ID 查询人员信息
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	public List<User> findUserByOrgIdOrRoleName(String orgId,String roleName);
	
	/**
	 * 批量保存角色人员信息
	 * @param arus
	 */
	public void batchSaveRolesPersonal(String arus[]);
	
	/**
	 * 更新角色人员信息
	 * @param aru
	 */
	public void updateByAuthRoleUser(AuthRoleUser aru);
	
	/**
	 * 根据角色ID和用户ID查询映射关系对象
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public AuthRoleUser findByRoleIdAndUserId(String roleId,String userId);
}
