package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.auth.dao.IAuthFunctionDao;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.Role;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.gv.vo.GlobalEnumTvVo;
import org.bachelor.ps.domain.Function;

@Repository
@SuppressWarnings("unchecked")
public class AuthFunctionDaoImpl  extends GenericDaoImpl<AuthFunction, String> implements IAuthFunctionDao {

	@Override
	public void deleteAuthFunctionInfo(AuthFunction af) {
		if(af!=null){
			StringBuilder dSQL = new StringBuilder();
			dSQL.append("delete from t_ufp_auth_function where 1=1 ");
			if(af.getId()!=null && !af.getId().equals("")){
				dSQL.append(" and  id='").append(af.getId()).append("'");
			}
			if(af.getFunc()!=null && af.getFunc().getId()!=null && !af.getFunc().getId().equals("")){
				dSQL.append(" and function_id='").append(af.getFunc().getId()).append("'");
			}
			if(af.getRole()!=null && af.getRole().getId()!=null && !af.getRole().getId().equals("")){
				dSQL.append(" and role_id='").append(af.getRole().getId()).append("'");
			}
			getJdbcTemplate().update(dSQL.toString());
			/** 立即执行SQL **/
		}		
	}

	
	@SuppressWarnings({  "rawtypes" })
	public List<AuthFunction> findAllAuthFuncByFuncId(AuthFunction af) {
		List<AuthFunction> af_list = null;
		StringBuilder dSQL = new StringBuilder();
		dSQL.append("select ID,ROLE_ID,FUNCTION_ID,VISIBLE,USAGE from t_ufp_auth_function where 1=1 ");
		if(af!=null){
			if(af.getId()!=null && !af.getId().equals("")){
				dSQL.append(" and  id='").append(af.getId()).append("'");
			}
			if(af.getFunc()!=null && af.getFunc().getId()!=null && !af.getFunc().getId().equals("")){
				dSQL.append(" and function_id='").append(af.getFunc().getId()).append("'");
			}
			if(af.getRole()!=null && af.getRole().getId()!=null && !af.getRole().getId().equals("")){
				dSQL.append(" and ROLE_ID='").append(af.getRole().getId()).append("'");
			}
		}	
			af_list = getJdbcTemplate().query(dSQL.toString(), new RowMapper(){
				@Override
				public AuthFunction mapRow(ResultSet rst, int arg1)
						throws SQLException {
					AuthFunction tempAF = new AuthFunction();
					tempAF.setId(rst.getString("ID"));
					Function tempFun = new Function();
					Role tempRole = new Role();
					tempFun.setId(rst.getString("FUNCTION_ID"));
					tempRole.setId(rst.getString("ROLE_ID"));
					
					tempAF.setFunc(tempFun);
					tempAF.setRole(tempRole);
					tempAF.setUsage(rst.getString("USAGE"));
					tempAF.setVisible(rst.getString("VISIBLE"));
					return tempAF;
				}
			});
		
		return af_list;
	}
	
	@Override
	public List<GlobalEnumTvVo> findTvInfo(String funcId) {
		List<GlobalEnumTvVo> af_list = null;
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select  (select t.name from t_ufp_auth_role t where  t.id = ROLE_ID) as text,ROLE_ID value from t_ufp_auth_function where FUNCTION_ID='");
		qSQL.append(funcId).append("'");
		 
		af_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){
			@Override
			public GlobalEnumTvVo mapRow(ResultSet rst, int arg1)
					throws SQLException {
				GlobalEnumTvVo tv = new GlobalEnumTvVo();
				tv.setText(rst.getString("text"));
				tv.setValue(rst.getString("value"));
				return tv;
			}
		});
		return af_list;
	}


	@Override
	public void batchSaveAuthFunction(String[] iSQL) {
		
		getJdbcTemplate().batchUpdate(iSQL);
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<AuthFunction> findByFuncIds(String ids) {
		
		if(StringUtils.isEmpty(ids)){
			
			return null;
		}
		
		List<AuthFunction> af_list = null;
		StringBuilder dSQL = new StringBuilder();
		dSQL.append("select ID,ROLE_ID,FUNCTION_ID,VISIBLE,USAGE from t_ufp_auth_function where 1=1 ");
		if(!StringUtils.isEmpty(ids)){
			dSQL.append(" and function_id in (").append(ids).append(") ");
		}
		
		af_list = getJdbcTemplate().query(dSQL.toString(), new RowMapper(){
			@Override
			public AuthFunction mapRow(ResultSet rst, int arg1)
					throws SQLException {
				AuthFunction tempAF = new AuthFunction();
				tempAF.setId(rst.getString("ID"));
				Function tempFun = new Function();
				Role tempRole = new Role();
				tempFun.setId(rst.getString("FUNCTION_ID"));
				tempRole.setId(rst.getString("ROLE_ID"));
				
				tempAF.setFunc(tempFun);
				tempAF.setRole(tempRole);
				tempAF.setUsage(rst.getString("USAGE"));
				tempAF.setVisible(rst.getString("VISIBLE"));
				return tempAF;
			}
		});
		
		return af_list;
	}
}
