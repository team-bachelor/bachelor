package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bachelor.auth.dao.IAuthUiResourceDao;
import org.bachelor.auth.domain.AuthUiResource;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.vo.AuthUiResourceVo;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class AuthUiResourceDaoImpl  extends GenericDaoImpl<AuthUiResource, String> implements IAuthUiResourceDao {

	@Override
	public void deleteById(String id) {
		StringBuilder dSQL = new StringBuilder();
		dSQL.append("delete from T_BCHLR_AUTH_UI_RESOURCE where id='").append(id).append("'");
		getJdbcTemplate().update(dSQL.toString());
	}
  
	@Override
	public List<AuthUiResourceVo> findAuthUiResourceVoInfo(String type,String funcId,String funcUrl,String id,String pid,String taskId,
			AuthUiResourceVo aur,List<String> roleIds,List<Role> roles) {
		List<AuthUiResourceVo> ui_list = new ArrayList<AuthUiResourceVo>();
		ui_list = getJdbcTemplate().query(returnQuerySQL(type,funcId,funcUrl,id,pid,taskId,aur,roleIds,roles), new RowMapper(){
			@Override
			public AuthUiResourceVo mapRow(ResultSet rst, int arg1) throws SQLException {
				AuthUiResourceVo ui = new AuthUiResourceVo();
				ui.setId(rst.getString("ID"));
				ui.setAurFuncId(rst.getString("FUNCTION_ID"));
				ui.setRoleId(rst.getString("ROLE_ID"));
				ui.setUiResourceDesc(rst.getString("UI_RESOURCE_DESC"));
				ui.setUiResourceId(rst.getString("UI_RESOURCE_ID"));
				ui.setUiResourcePermission(rst.getString("UI_RESOURCE_PERMISSION"));
				ui.setUiTypeId(rst.getString("UI_TYPE_ID"));
				ui.setRoleName(rst.getString("RoleName"));
				ui.setFuncName(rst.getString("FuncName"));
				ui.setUiTypeName(rst.getString("UiTypeName"));
				ui.setPermissionName(rst.getString("PermissionName"));
				ui.setFlowId(rst.getString("FLOW_ID"));
				ui.setFlowVersion(rst.getString("VERSION_NAME"));
				ui.setJoinId(rst.getString("JOIN_ID"));
				ui.setJoinName(rst.getString("JOIN_NAME"));
				ui.setUiTypeSelector(rst.getString("UI_TYPE_SELECTOR"));
				ui.setFlowName(rst.getString("FLOW_NAME"));
				ui.setRoleMemo(rst.getString("RoleMemo"));
				return ui;
			}});
		return ui_list;
	}
	
	public String returnQuerySQL(String type,String funcId,String funcUrl,String id,String pid,String taskId,
			AuthUiResourceVo aur,List<String> roleIds,List<Role> roles){
			StringBuilder qSQL = new StringBuilder();
			if(type.equals("findByExample")){
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_SELECTOR,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo,");
				qSQL.append(" (select f.name from T_BCHLR_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from T_BCHLR_AUTH_UI_RESOURCE u ,t_bchlr_auth_role r");
				if(aur!=null){
					qSQL.append(" where r.id = u.role_id ");
					if(aur.getRoleId()!=null && !StringUtils.isEmpty(aur.getRoleId())){
						qSQL.append(" and ROLE_ID = '").append(aur.getRoleId()).append("' ");
					} 
					if(aur.getUiResourcePermission()!=null && !StringUtils.isEmpty(aur.getUiResourcePermission())){
						qSQL.append(" and UI_RESOURCE_PERMISSION = '").append(aur.getUiResourcePermission()).append("'");
					}
					if(aur.getFlowId()!=null && !StringUtils.isEmpty(aur.getFlowId())){
						qSQL.append(" and FLOW_ID = '").append(aur.getFlowId()).append("'");
					}
					if(aur.getJoinId()!=null && !StringUtils.isEmpty(aur.getJoinId())){
						qSQL.append(" and JOIN_ID = '").append(aur.getJoinId()).append("'");
					}
					if(aur.getAurFuncId()!=null && !StringUtils.isEmpty(aur.getAurFuncId())){
						String[] funcIds = aur.getAurFuncId().split(",");
						String temp_roles = "";
						for(String fId : funcIds){
							if(!StringUtils.isEmpty(fId)){
								temp_roles += "'" + fId + "',";
							}
						}
						temp_roles = temp_roles.substring(0, temp_roles.length()-1);
						qSQL.append(" and FUNCTION_ID in (").append(temp_roles).append(")");
					}
				}
			} else if(type.equals("findByFuncIdAndRoleIds")){
				String temp_roles = "";
				int index = 0;
				for(String role : roleIds){
					temp_roles += "'" + role + "',";
					index++;
				}
				temp_roles = temp_roles.substring(0, temp_roles.length()-1);
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_SELECTOR,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo,");
				qSQL.append(" (select f.name from T_bchlr_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from T_bchlr_AUTH_UI_RESOURCE u ,t_bchlr_auth_role r");
				qSQL.append(" where  r.id = u.role_id  and u.flow_id is null  and FUNCTION_ID =  '").append(funcId).append("' ");
				qSQL.append(" and ROLE_ID in (").append(temp_roles).append(")"); 
			}  else if(type.equals("findByFuncUrlAndRoleIds")){
				String temp_roles = "";
				int index = 0;
				for(String role : roleIds){
					temp_roles += "'" + role + "',";
					index++;
				}
				temp_roles = temp_roles.substring(0, temp_roles.length()-1);
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_SELECTOR,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo,");
				qSQL.append(" (select f.name from T_bchlr_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from T_bchlr_AUTH_UI_RESOURCE u , T_bchlr_PS_FUNCTION f,t_bchlr_auth_role r ");
				qSQL.append(" where  r.id = u.role_id  and FUNCTION_ID = f.id and f.entry_path = '").append(funcUrl).append("' ");
				qSQL.append(" and ROLE_ID in (").append(temp_roles).append(")"); 
			} else if(type.equals("findById")){
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo,");
				qSQL.append(" (select f.name from T_bchlr_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from    T_bchlr_AUTH_UI_RESOURCE u ,t_bchlr_auth_role r ");
				qSQL.append(" where r.id = u.role_id  and ID='").append(id).append("'");
			} else if(type.equals("findByFuncId")){
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_SELECTOR,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,FLOW_ID,VERSION_NAME,JOIN_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo,");
				qSQL.append(" (select f.name from T_bchlr_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from     T_bchlr_AUTH_UI_RESOURCE u,t_bchlr_auth_role r ");
				qSQL.append(" where r.id = u.role_id and FUNCTION_ID='").append(funcId).append("'");
			} else if(type.equals("findUiAuthByFuncIdAndRoleIdsAndFlowInfo")){
				String temp_roles = "";
				int index = 0;
				for(String role : roleIds){
					temp_roles += "'" + role + "',";
					index++;
				}
				temp_roles = temp_roles.substring(0, temp_roles.length()-1);
				qSQL.append("select FLOW_NAME,JOIN_NAME,FLOW_ID,JOIN_ID,VERSION_NAME,u.ID,UI_TYPE_SELECTOR,UI_TYPE_ID,UI_RESOURCE_ID,UI_RESOURCE_DESC,UI_RESOURCE_PERMISSION,FUNCTION_ID,ROLE_ID,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = UI_TYPE_ID and e.enum_name = 'bchlr_AUTH_UI_TYPE') UiTypeName,");
				qSQL.append(" (select e.field_desc from t_bchlr_gv_enum e where e.field_value = ui_resource_permission and e.enum_name = 'bchlr_AUTH_UI_PERMISSION') PermissionName,");
				qSQL.append(" r.name  RoleName, r.memo  RoleMemo, ");
				qSQL.append(" (select f.name from T_bchlr_PS_FUNCTION f where f.id = FUNCTION_ID) FuncName ");
				qSQL.append("  from T_bchlr_AUTH_UI_RESOURCE u,t_bchlr_auth_role r ");
				qSQL.append(" where   r.id = u.role_id  and FUNCTION_ID =  '").append(funcId).append("' ");
				qSQL.append(" and ROLE_ID in (").append(temp_roles).append(")");;
				qSQL.append(" and flow_id like '").append(pid).append("%' ");
				qSQL.append(" and join_id='").append(taskId).append("'   ");
			}
			System.out.println(qSQL.toString());
			return qSQL.toString();
	}

	@Override
	public void batchDelete(String[] dSQL) {
		
		getJdbcTemplate().batchUpdate(dSQL);
	}

	@Override
	public List<AuthUiResource> findByFunctIdAndUiId(String funcId,
			String uiId) {
		StringBuilder hql = new StringBuilder("from AuthUiResource ");
		hql.append(" where func.id='").append(funcId).append("'");
		hql.append(" and uiResourceId='").append(uiId).append("'");
		return findByHQL(hql.toString());
	}
}
