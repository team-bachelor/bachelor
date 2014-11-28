package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import org.bachelor.auth.dao.IAuthRoleUserDao;
import org.bachelor.auth.domain.AuthRoleUser;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.domain.RoleGroup;
import org.bachelor.auth.vo.AuthRoleUserVo;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.org.domain.Org;
import org.bachelor.org.domain.User;

@Repository
@SuppressWarnings("unchecked")
public class AuthRoleUserDaoImpl  extends GenericDaoImpl<AuthRoleUser, String>  implements IAuthRoleUserDao {

	private Log log = LogFactory.getLog(AuthRoleUserDaoImpl.class);
	
	@Override
	public List<AuthRoleUserVo> findByExample(AuthRoleUserVo aruVo) {
		StringBuilder qSQL = new StringBuilder();
		List<AuthRoleUserVo> vo_list = new ArrayList<AuthRoleUserVo>();
		qSQL.append("select ru.id,ru.user_id,ru.role_id,uu.username||'/'||ru.user_id as AuthUserName,uu.username as username,");
		qSQL.append(" (select uar.name from T_BCHLR_AUTH_ROLE uar where uar.id = ru.role_id) as rolename,");
		qSQL.append(" uo.nm as orgname,ar.memo RoleMemo");
		qSQL.append(" from T_BCHLR_AUTH_ROLE_USER ru,T_BCHLR_AUTH_ROLE ar,T_BCHLR_USER uu,org_vw uo");
		qSQL.append(" where uo.id = uu.owner_org_id and ");
		qSQL.append(" ru.user_id in (select tuu.id from t_bchlr_org uo,T_BCHLR_USER tuu ");
		qSQL.append(" where tuu.owner_org_id = uo.id and uo.parentid like (substr(");
		if(aruVo!=null){
			/** 单位查询 (二级单位)**/
			if(aruVo.getOrgId()!=null && !aruVo.getOrgId().equals("")){
				qSQL.append("'"+aruVo.getOrgId().trim()+"'"+",0,4)||'%')");
				qSQL.append(" or tuu.owner_org_id ='").append(aruVo.getOrgId().trim()).append("' ) ");
			} else {
				qSQL.append("uu.owner_org_id,0,4)||'%') or tuu.owner_org_id = uu.owner_org_id)  ");
			}
			/** 角色**/
			if(aruVo.getRoleId()!=null && !aruVo.getRoleId().equals("")){
				qSQL.append("  and ru.role_id = ar.id  and ru.role_id = '").append(aruVo.getRoleId().trim()).append("'");
			} else {
				qSQL.append(" and ru.role_id = ar.id ");	
			}
			/** 用户名称或者OA名称**/
			if(aruVo.getUserId()!=null && !aruVo.getUserId().equals("")){
				qSQL.append("  and uu.id = ru.user_id  and ru.user_id  in (select suu.id from t_bchlr_user suu where suu.id like  '%").append(aruVo.getUserId().trim()).append("%')");
			} else {
				if(aruVo.getUserName()!=null && !aruVo.getUserName().equals("")){
					qSQL.append("  and uu.id = ru.user_id  and ru.user_id  in (select suu.id from t_bchlr_user suu where suu.username like  '%").append(aruVo.getUserName().trim()).append("%')");
				} else {
					qSQL.append(" and uu.id = ru.user_id ");
				}
			}
		} else {
			qSQL.append("uu.owner_org_id,0,4)||'%')) ");
			qSQL.append("  and ru.role_id = ar.id and uu.id = ru.user_id ");
		}
		vo_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public AuthRoleUserVo mapRow(ResultSet rst, int arg1) throws SQLException {
				AuthRoleUserVo vo = new AuthRoleUserVo();
				vo.setId(rst.getString("id"));
				vo.setAuthUserName(rst.getString("AuthUserName"));
				vo.setOrgName(rst.getString("ORGNAME"));
				vo.setRoleId(rst.getString("ROLE_ID"));
				vo.setRoleName(rst.getString("ROLENAME"));
				vo.setUserId(rst.getString("USER_ID"));
				vo.setUserName(rst.getString("USERNAME"));
				vo.setRoleMemo(rst.getString("RoleMemo"));
				return vo;
			}});
		return vo_list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Role> findByUserId(String userId) {
		/*StringBuilder qSQL =new StringBuilder();
		qSQL.append("select * from T_BCHLR_AUTH_ROLE r where r.id in (select ru.role_id from T_BCHLR_AUTH_ROLE_USER ru where ru.user_id = '").append(userId).append("')");
		List<Role> roles = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
						Role role = new Role();
						RoleGroup roleGroup = new RoleGroup();
						role.setId(rs.getString("ID"));
						role.setDelFlag(rs.getString("DEL_FLAG"));
						role.setDescription(rs.getString("DESCRIPTION"));
						roleGroup.setId(rs.getString("GROUP_ID"));
						role.setGroup(roleGroup);
						role.setMemo(rs.getString("MEMO"));
						role.setName(rs.getString("NAME"));
						role.setType(rs.getString("TYPE"));
					return role; 
			}});
		return roles;*/
		StringBuilder qSQL =new StringBuilder();
		qSQL.append("from Role r  where r.id in (select ru.role.id from AuthRoleUser ru where ru.user.id = '").append(userId).append("')");
		Query query = getSession().createQuery(qSQL.toString());
		return query.list();
	}

	@Override
	public List<AuthRoleUser> findListByExample(AuthRoleUser aru) {
		StringBuilder qSQL = new StringBuilder("from AuthRoleUser aru where 1=1 ");
		if(aru!=null){
			if(!StringUtils.isEmpty(aru.getRoleIds())){
				String roleIds[] = aru.getRoleIds().split(",");
				StringBuilder ids = new StringBuilder();
				int index = 0;
				for(String id : roleIds){
					ids.append("'").append(id).append("'");
					if(index<(roleIds.length-1)){
						ids.append(",");
					}
					index++;
				}
				qSQL.append(" and aru.role.id in (").append(ids).append(")");
			}
			if(aru.getUser()!=null && aru.getUser().getId()!=null){
				qSQL.append(" and aru.user.id='").append(aru.getUser().getId()).append("'");
			}
		}
		Query query  = getSession().createQuery(qSQL.toString());
		return query.list();
	}

	@Override
	public void batchDelete(String[] dSQL) {
		
		getJdbcTemplate().batchUpdate(dSQL);
	}

	@Override
	public List<User> findUsersByRoleIds(String roleTerm) {
		StringBuffer qSQL = new StringBuffer();
		qSQL.append("select distinct * from t_bchlr_user u , (select t.user_id from T_BCHLR_AUTH_ROLE_USER t where ");
		qSQL.append("t.role_id in (").append(roleTerm).append(")) ua");
		qSQL.append(" where u.id = ua.user_id");
		List<User> users = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return getUserByResultSet(rs);
			}});
		return users;
	}

	@Override
	public List<User> findUsersByRoleIdsAndOrgIds(String roleIds, String orgIds) {
		StringBuffer qSQL = new StringBuffer();
		if(roleIds!=null && !"".equals(roleIds)){
			String roles[] = roleIds.split(",");
			String orgs[] = orgIds.split(",");
			for(int i=0;i<roles.length;i++){
				qSQL.append("select distinct t.*,");
				qSQL.append("v.orgId company_id,v.ejdw company_name,v.bm dept_name,v.bmId dept_id,");
				qSQL.append("(select ov.nm from org_vw ov where ov.id = t.owner_org_id) org_path,");
				qSQL.append("(select uo.name from t_bchlr_org uo where uo.id = t.owner_org_id) owner_org_name");
				qSQL.append(" from t_bchlr_user t, org_user_vw v");
				qSQL.append(" where t.id = v.id ");
				/** 机构条件是否为空 **/
				if(orgs.length>0 && i<orgs.length &&
						orgs[i]!=null && !"".equals(orgs[i])){
					qSQL.append("  and v.orgId = ").append(orgs[i]);
				}
				qSQL.append(" and v.id in (select distinct u.user_id from t_bchlr_auth_role_user u, t_bchlr_auth_role ar ");
				qSQL.append(" where u.role_id = ar.id and ar.id in (").append(roleIds).append("))");
				if(i<roles.length-1){
					qSQL.append(" union ");
				} 
			}
		} 
		
		List<User> users = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				 
				return getUserAndOrgByResultSet(rs);
			}});
		return users;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<User> findUsersByRoleNamesAndOrgIds(String roleNames,
			String orgIds) {
		
		StringBuffer qSQL = new StringBuffer();
		if(roleNames!=null && !"".equals(roleNames)){
			String roles[] = roleNames.split(",");
			String orgs[] = orgIds.split(",");
			for(int i=0;i<roles.length;i++){
				qSQL.append("select distinct t.*,");
				qSQL.append("v.orgId company_id,v.ejdw company_name,v.bm dept_name,v.bmId dept_id,");
				qSQL.append("(select ov.nm from org_vw ov where ov.id = t.owner_org_id) org_path,");
				qSQL.append("(select uo.name from t_bchlr_org uo where uo.id = t.owner_org_id) owner_org_name");
				qSQL.append(" from t_bchlr_user t, org_user_vw v");
				qSQL.append(" where t.id = v.id ");
				if(orgs.length>0 && i<orgs.length &&
						orgs[i]!=null && !"".equals(orgs[i])){
					qSQL.append("  and v.orgId = ").append(orgs[i]);
				}
				qSQL.append(" and v.id in (select distinct u.user_id from t_bchlr_auth_role_user u, t_bchlr_auth_role ar ");
				qSQL.append(" where u.role_id = ar.id and ar.name in (").append(roleNames).append("))");
				if(i<roles.length-1){
					qSQL.append(" union ");
				} 
			}
		} 
		 
		List<User> users = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				 
				return getUserAndOrgByResultSet(rs);
			}});
		return users;
	}

	/**
	 * 得到用户及单位信息
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public User getUserAndOrgByResultSet(ResultSet rs) throws SQLException{
		User user = new User();
		Org org = new Org();
		
		org.setCompanyName(rs.getString("company_name"));
		org.setCompanyId(rs.getString("company_id"));
		org.setNamePath(rs.getString("org_path")); 
		org.setDepartmentId(rs.getString("dept_id"));
		org.setDepartmentName(rs.getString("dept_name"));
		
		
		user.setOwnerOrg(org);
		
		user.setId(rs.getString("ID"));
		user.setOwnerOrgId(rs.getString("OWNER_ORG_ID"));
		user.setOwnerOrgName(rs.getString("owner_org_name"));
		user.setUsername(rs.getString("USERNAME"));
		user.setType(rs.getString("TYPE"));
		user.setDuty(rs.getString("DUTY"));
		user.setMemo(rs.getString("MEMO"));
		user.setPwd(rs.getString("PWD"));
		user.setGender(rs.getString("GENDER"));
		user.setLoginFlag(rs.getString("LOGIN_FLAG"));
		user.setStatusFlag(rs.getString("STATUS_FLAG"));
		return user;
	}
	
	public User getUserByResultSet(ResultSet rs) throws SQLException{
		User user = new User();
		user.setId(rs.getString("ID"));
		user.setOwnerOrgId(rs.getString("OWNER_ORG_ID"));
		user.setUsername(rs.getString("USERNAME"));
		user.setType(rs.getString("TYPE"));
		user.setDuty(rs.getString("DUTY"));
		user.setMemo(rs.getString("MEMO"));
		user.setPwd(rs.getString("PWD"));
		user.setGender(rs.getString("GENDER"));
		user.setLoginFlag(rs.getString("LOGIN_FLAG"));
		user.setStatusFlag(rs.getString("STATUS_FLAG"));
		return user;
	}

	@Override
	public List<User> findUserByOrgIdOrRoleName(String orgId, String roleName) {
		StringBuffer qSQL = new StringBuffer();
		qSQL.append("select tu.id,tu.username,tu.pwd,tu.type,tu.login_flag,tu.status_flag,(select v.nm from org_vw v where v.id = tu.owner_org_id) orgPath ");
		qSQL.append("  from t_bchlr_user tu where ");
		if(orgId!=null && !"".equals(orgId)){
			qSQL.append("  tu.id in  (select ov.id from org_user_vw ov where orgid = '"+orgId+"') ");
		}
		if(orgId!=null && !"".equals(orgId) && 
				roleName!=null && !"".equals(roleName)){
			qSQL.append(" and ");
		}
		if(roleName!=null && !"".equals(roleName)){
			qSQL.append("   tu.id in ");
			qSQL.append(" (select ru.user_id   from t_bchlr_auth_role_user ru where role_id = (select ur.id from t_bchlr_auth_role ur where ur.name =  '"+roleName+"')) ");
		}
		try{
				List<User> users = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){
		
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("ID"));
						user.setOwnerOrgName(rs.getString("orgPath"));
						user.setUsername(rs.getString("USERNAME"));
						user.setType(rs.getString("TYPE"));
						user.setPwd(rs.getString("PWD"));
						user.setLoginFlag(rs.getString("LOGIN_FLAG"));
						user.setStatusFlag(rs.getString("STATUS_FLAG"));
						return user;
					}});
				return users;
		}catch(Exception e){
			log.debug("根据二级单位或者是角色名称查询时异常.",e);
			return new ArrayList<User>();
		}
	}

	@Override
	public void batchSaveRolesPersonal(String arus[]) {
		
		getJdbcTemplate().batchUpdate(arus);
	}

	@Override
	public void updateByAuthRoleUser(AuthRoleUser aru) {
		StringBuilder iSQL = new StringBuilder();
		iSQL.append("update t_bchlr_auth_role_user set");
		iSQL.append(" USER_ID = '").append(aru.getUser().getId()).append("',");
		iSQL.append(" ROLE_ID='").append(aru.getRole().getId()).append("'");
		iSQL.append(" where id='").append(aru.getId()).append("'");
		
		getJdbcTemplate().update(iSQL.toString());
	}

	@Override
	public AuthRoleUser findByRoleIdAndUserId(String roleId, String userId) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append(" from AuthRoleUser  ");
		qSQL.append(" where USER_ID='").append(userId).append("'");
		qSQL.append(" and ROLE_ID='").append(roleId).append("'");
		List<AuthRoleUser> arus = findByHQL(qSQL.toString());
		AuthRoleUser aru = null;
		if(arus!=null && arus.size()>0){
			aru = arus.get(0);
		}
		return aru;
	}
}
