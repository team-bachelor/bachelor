package org.bachelor.auth.service;

import java.util.List;
import java.util.Set;

import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthRoleUserVo;
import org.bachelor.org.domain.User;

public interface IAuthRoleUserService {
	
	public void add(AuthRoleUser aru);
	
	public void update(AuthRoleUser aru);
	
	public void addOrUpdate(AuthRoleUser aru);
	
	public void delete(AuthRoleUser aru);
	
	public void deleteById(String id);
	
	public void batchDelete(String info);
	
	public List<AuthRoleUserVo> findByExample(AuthRoleUserVo aruVo);
	
	public AuthRoleUser findById(String id);
	
	public List<AuthRoleUser> findListByExample(AuthRoleUser aru);
	
	public List<AuthRoleUser> findAll();
	
	public List<Role> findRolesByUserId(String userId);
	
	/**
	 * 根据二级单位ID和角色ID 查询人员信息
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	public List<User> findUserByOrgIdOrRoleName(String orgId,String roleName);
	
	/**
	 * 取得指定角色包含的用户
	 * 如有重复用户则进行过滤。
	 * 
	 * @param roleIds 角色Id数组
	 * @return 包含的用户列表
	 */
	public List<User> findUsersByRoleIds(String... roleIds);
	
	/**
	 * 判断当前用户是否具有指定的角色
	 * 如果参数是多个角色Id，那么当前用户只要具有其中一个角色就返回True，否则返回False。
	 * 
	 * @param userId 用户Id
	 * @param roleIds 角色Id的数组
	 * @return
	 */
	public boolean hasRoles(String userId, String... roleIds);
	
	/**
	 * 取得指定角色和单位所包含的用户。
	 * 如有重复用户则进行过滤。
	 * 角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员。
	 * 如果不需要分单位，则传入参数时只要传入角色Id就可以。
	 * 
	 * @param roleIds 角色和单位ID的数组。
	 * 				     角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员。
	 * @return 包含的用户列表
	 */
	public Set<User> findUsersByRoleIdAndOrgId(String... roleOrgIds);
	
	public Set<User> findUsersByRoleNameAndOrgId(String... roleOrgIds);
	
	/**
	 * 批量保存角色人员信息
	 * @param arus
	 */
	public void batchSaveRolesPersonal(List<AuthRoleUser> arus);
	
	/**
	 * 根据角色名称和用户ID删除角色人员映射关系
	 * @param roleName
	 * @param userId
	 */
	public boolean removeUserFormRole(String roleName, String userId);
}
