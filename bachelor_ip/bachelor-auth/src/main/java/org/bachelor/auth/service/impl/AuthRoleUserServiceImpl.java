package org.bachelor.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.dao.IAuthRoleUserDao;
import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthRoleUserService;
import org.bachelor.auth.service.IRoleService;
import org.bachelor.auth.vo.AuthRoleUserVo;
import org.bachelor.org.domain.User;

@Service
public class AuthRoleUserServiceImpl implements IAuthRoleUserService {

	@Autowired
	private IAuthRoleUserDao aruDao = null;

	@Autowired
	private IRoleService roleServiceImpl = null;
	
	@Override
	public void add(AuthRoleUser aru) {
		// TODO Auto-generated method stub
		aruDao.save(aru);
	}

	@Override
	public void addOrUpdate(AuthRoleUser aru) {
		if (!StringUtils.isEmpty(aru.getId())) {

			aruDao.updateByAuthRoleUser(aru);
		} else {
			String roleIds[] = aru.getRoleIds().split(",");
			if (roleIds != null && roleIds.length > 0) {
				Role role;
				for (String roleId : roleIds) {
					role = new Role();
					role.setId(roleId);
					AuthRoleUser saveAru = new AuthRoleUser();
					saveAru.setRole(role);
					saveAru.setUser(aru.getUser());
					aruDao.save(saveAru);
				}
			}
		}
	}

	@Override
	public void delete(AuthRoleUser aru) {

		aruDao.delete(aru);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		AuthRoleUser aru = new AuthRoleUser();
		aru.setId(id);
		aruDao.delete(aru);
	}

	@Override
	public List<AuthRoleUserVo> findByExample(AuthRoleUserVo aruVo) {
		// TODO Auto-generated method stub
		return aruDao.findByExample(aruVo);
	}

	@Override
	public AuthRoleUser findById(String id) {
		// TODO Auto-generated method stub
		return aruDao.findById(id);
	}

	@Override
	public void update(AuthRoleUser aru) {
		// TODO Auto-generated method stub
		aruDao.update(aru);
	}

	@Override
	public List<AuthRoleUser> findAll() {
		// TODO Auto-generated method stub
		return aruDao.findAll();
	}

	@Override
	public List<Role> findRolesByUserId(String userId) {
		// List<Role> role_list = aruDao.findByUserId(userId);
		// //过滤AuthFunction中不可见的权限对象
		// if(role_list!=null && role_list.size()>0){
		// for(Role role : role_list){
		// if(role.getAuthFunctions()!=null &&
		// role.getAuthFunctions().size()>0){
		// List<AuthFunction> temp_list = new ArrayList<AuthFunction>();
		// for(AuthFunction af : role.getAuthFunctions()){
		// if(af.getVisible().equals("1")){
		// temp_list.add(af);
		// }
		// }
		// role.setAuthFunctions(temp_list);
		// }
		// }
		// }
		return aruDao.findByUserId(userId);
	}

	@Override
	public List<AuthRoleUser> findListByExample(AuthRoleUser aru) {

		return aruDao.findListByExample(aru);
	}

	@Override
	public void batchDelete(String info) {
		// TODO Auto-generated method stub
		String deleteInfo[] = info.split(",");
		String dSQL[] = new String[deleteInfo.length];
		int index = 0;
		for (String dInfo : deleteInfo) {
			dSQL[index] = "delete from T_bchlr_AUTH_ROLE_USER where id='" + dInfo
					+ "'";
			index++;
		}
		aruDao.batchDelete(dSQL);
	}

	/**
	 * 取得指定角色包含的用户 如有重复用户则进行过滤。
	 * 
	 * @param roleIds
	 *            角色Id数组
	 * @return 包含的用户列表
	 */
	@Override
	public List<User> findUsersByRoleIds(String... roleIds) {
		if (roleIds == null || roleIds.length <= 0) {
			return null;
		}
		StringBuffer roleQuery = new StringBuffer();
		int index = 0;
		for (String roleId : roleIds) {
			roleQuery.append("'").append(roleId).append("'");
			if (index < (roleIds.length - 1)) {
				roleQuery.append(",");
			}
			index++;
		}
		return aruDao.findUsersByRoleIds(roleQuery.toString());
	}

	/**
	 * 判断当前用户是否具有指定的角色 如果参数是多个角色Id，那么当前用户只要具有其中一个角色就返回True，否则返回False。
	 * 
	 * @param userId
	 *            用户Id
	 * @param roleIds
	 *            角色Id的数组
	 * @return
	 */
	@Override
	public boolean hasRoles(String userId, String... roleIds) {
		List<Role> roles = aruDao.findByUserId(userId);
		if (roles == null || roles.size() <= 0 || roleIds == null
				|| roleIds.length <= 0) {
			return false;
		}
		for (Role role : roles) {
			for (String roleId : roleIds) {
				/** 判断传的roleIds中是否有相等的roleid **/
				if (role.getId().equals(roleId)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 取得指定角色和单位所包含的用户。 如有重复用户则进行过滤。
	 * 角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员.
	 * 
	 * @param roleIds
	 *            角色和单位ID的数组。
	 *            角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员
	 *            。
	 * @return 包含的用户列表
	 * 
	 * 
	 */
	@Override
	public Set<User> findUsersByRoleIdAndOrgId(String... roleOrgIds) {
		Set<User> users = new HashSet<User>();
		if (roleOrgIds != null && roleOrgIds.length > 0) {
			/** 根据角色，单位ID数组转换成查询条件 **/
			List<User> tempUsers = aruDao.findUsersByRoleIdsAndOrgIds(
					splitArrayByRoleOrOrg("roles", roleOrgIds),
					splitArrayByRoleOrOrg("orgs", roleOrgIds));
			users = new HashSet<User>(tempUsers);
		}
		return users;
	}

	/**
	 * 取得指定角色和单位所包含的用户。 如有重复用户则进行过滤。
	 * 角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员。
	 * 如果不需要分单位，则传入参数时只要传入角色Id就可以。
	 * 
	 * @param roleIds
	 *            角色和单位ID的数组。
	 *            角色和单位应该以"#"区分，例如adminRole#0608000000,表示取得海淀供电公司的具有adminRole角色的人员
	 *            。
	 * @return 包含的用户列表
	 */
	@Override
	public Set<User> findUsersByRoleNameAndOrgId(String... roleOrgIds) {
		Set<User> users = new HashSet<User>();
		if (roleOrgIds != null && roleOrgIds.length > 0) {

			/** 根据角色名称，单位ID数组转换成查询条件 **/
			List<User> tempUsers = aruDao.findUsersByRoleNamesAndOrgIds(
					splitArrayByRoleOrOrg("roles", roleOrgIds),
					splitArrayByRoleOrOrg("orgs", roleOrgIds));
			users = new HashSet<User>(tempUsers);
		}
		return users;
	}

	/**
	 * 根据角色或者单位信息，拆分成相对应查询条件
	 * 
	 * @param roleIdsOrOrgIds
	 * @return
	 */
	public String splitArrayByRoleOrOrg(String type, String... roleIdsOrOrgIds) {
		/** 角色数组 **/
		String splitArray[] = new String[roleIdsOrOrgIds.length];
		int index = 0;
		for (String ro : roleIdsOrOrgIds) {
			if ("roles".equals(type)) {
				/** 拆分角色数组 **/
				splitArray[index] = StringUtils.substringBefore(ro, "#");
			}
			if ("orgs".equals(type)) {
				/** RO字符串可能没有机构 **/
				String tempArray[] = ro.split("#");
				if (tempArray.length > 1) {
					/** 拆分单位数组 **/
					splitArray[index] = StringUtils.substringAfterLast(ro, "#");
				}
			}
			index++;
		}
		return splitRoleOrOrg(splitArray);
	}

	/**
	 * 拆分角色或者单位数组
	 */
	public String splitRoleOrOrg(String... roleIdsOrOrgIds) {
		String group = "";
		if (roleIdsOrOrgIds != null && roleIdsOrOrgIds.length > 0) {
			int index = 0;
			for (String ro : roleIdsOrOrgIds) {
				if (ro != null && !"".equals(ro)) {
					group += "'" + ro + "'";
					if (index < (roleIdsOrOrgIds.length - 1)) {
						group += ",";
					}
				} else {
					index++;
					if (index == (roleIdsOrOrgIds.length) && group.length() > 0) {
						group = group.substring(0, group.length() - 1);
					}
				}
				index++;
			}
		}
		return group;
	}

	@Override
	public List<User> findUserByOrgIdOrRoleName(String orgId, String roleName) {

		return aruDao.findUserByOrgIdOrRoleName(orgId, roleName);
	}

	@Override
	public void batchSaveRolesPersonal(List<AuthRoleUser> arus) {
		if (arus != null && arus.size() > 0) {
			Integer size = arus.size();
			/** 导入值大于1000时,对数据进行分批批量插入 **/
			if (size > 1000) {

				int countNumber = arus.size() / 1000;
				/** 分批批量进行插入 **/
				for (int i = 0; i < countNumber; i++) {
					batchSave((i * 1000), ((i + 1) * 1000), arus);
				}
				/** 把得到余数也插入到数据库中 **/
				if (arus.size() - (countNumber * 1000) > 0) {

					batchSave((countNumber * 1000), (arus.size()), arus);
				}
			} else if (size <= 1000) {// 导入值小于1000时,直接进行插入操作

				batchSave(0, arus.size(), arus);
			}
		}
	}

	/**
	 * 批量保存 startVal 开始下标值 endVal 结束下标值 afs 导入数据集合
	 */
	public void batchSave(Integer startVal, Integer endVal,
			List<AuthRoleUser> arus) {
		StringBuilder sql;
		String iSQL[] = new String[endVal];
		int index = 0;
		for (int i = startVal; i < endVal; i++) {
			AuthRoleUser aru = arus.get(i);
			sql = new StringBuilder();
			sql.append("insert into T_bchlr_AUTH_ROLE_USER(ID,ROLE_ID,USER_ID)");
			sql.append(" values(sys_guid(),'").append(aru.getRole().getId());
			sql.append("','").append(aru.getUser().getId()).append("')");
			iSQL[index] = sql.toString();
			index++;
		}
		aruDao.batchSaveRolesPersonal(iSQL);
	}

	@Override
	public boolean removeUserFormRole(String roleName, String userId) {
		boolean delFlag = true;
		Role role = roleServiceImpl.findByName(roleName);
		/** 角色对象或者角色ID为空时，返回FLASE同时跳出方法体**/
		if(role==null || StringUtils.isEmpty(role.getId())){
			
			delFlag = false;
			return delFlag;
		}
		
		AuthRoleUser aru = aruDao.findByRoleIdAndUserId(role.getId(), userId);
		/** 角色映射对象或者角色映射ID为空时，返回FLASE同时跳出方法体**/
		if(aru==null || StringUtils.isEmpty(aru.getId())){
			
			delFlag = false;
			return delFlag;
		}
		/** 删除映射关系 **/
		aruDao.delete(aru);
		return delFlag;
	}
}
