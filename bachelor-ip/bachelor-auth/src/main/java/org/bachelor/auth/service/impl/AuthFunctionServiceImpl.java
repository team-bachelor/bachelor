package org.bachelor.auth.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.dao.IAuthFunctionDao;
import org.bachelor.auth.domain.AuthFunction;
import org.bachelor.auth.domain.Role;
import org.bachelor.auth.service.IAuthFunctionService;
import org.bachelor.gv.vo.GlobalEnumTvVo;
import org.bachelor.ps.domain.Function;

@Service 
public class AuthFunctionServiceImpl   implements IAuthFunctionService {
 
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IAuthFunctionDao authFunctionDao = null;
	
	public void saveOrUpdate(String authFuncs){
		/** 保存数据**/
		if(!StringUtils.isEmpty(authFuncs)){
				StringBuilder dSQL = new StringBuilder();
				StringBuilder aSQL = new StringBuilder();
				if(authFuncs!=null && !authFuncs.equals("")){
					String authArray[] = authFuncs.split(";");
					for(String authFunc : authArray){
						if(!StringUtils.isEmpty(authFunc)){
							String tempAuth[] = authFunc.split(",");
						 
							if("del".equals(tempAuth[4])){
								dSQL.append(authFunc).append(";");
							} else if("add".equals(tempAuth[4])){
								aSQL.append(authFunc).append(";");
							}
						}
					}
				}
				log.info("---------------角色功能删除：要角色功能的功能ID是"+(dSQL.toString())+"---------------");
				/** 先删除所有功能权限信息 **/
				batchDelAuthFunctions(dSQL.toString());
				if(!StringUtils.isEmpty(aSQL.toString())){
					log.info("---------------角色功能新增：要新增的角色功能是"+( aSQL.toString().split(";").length)+"条---------------");
					log.info("---------------角色功能新增：要新增的角色功能的SQL："+(aSQL.toString())+"条---------------");
					String addInfos[] = aSQL.toString().split(";");
					String addSQL[] = new String[addInfos.length];
					int index = 0;
					for(String info : addInfos){
						String infos[] = info.split(",");
						addSQL[index] = " insert into t_bchlr_auth_function(ID,ROLE_ID,FUNCTION_ID,VISIBLE,USAGE) values('"+UUID.randomUUID().toString().replace("-", "").replace("-", "")+
								"','"+infos[1]+"','"+infos[0]+"','"+infos[2]+"','"+infos[3]+"')";
						index++;
					}
					authFunctionDao.batchSaveAuthFunction(addSQL);
				}
		}
	}
	 
	@Override
	public List<AuthFunction> findAll() {
		 
		return authFunctionDao.findAll();
	}

	@Override
	public AuthFunction findAuthFunctionById(String id) {
		 
		return authFunctionDao.findById(id);
	}

	@Override
	public void deleteAuthFunctionInfo(AuthFunction auth) {
		 
		authFunctionDao.delete(auth);
	}

	@Override
	public List<AuthFunction> findAuthFunctionByFuncId(AuthFunction af) {
		
		return authFunctionDao.findAllAuthFuncByFuncId(af);
	}

	@Override
	public List<GlobalEnumTvVo> findTvInfo(String funcId) {
		 
		return authFunctionDao.findTvInfo(funcId);
	}

	/**
	 * 该方法已经弃用
	 */
	@Override
	public void batchSaveAuthFunction(List<AuthFunction> afs) {
		if(afs!=null && afs.size()>0){
			String iSQL[] = new String[afs.size()];
			StringBuilder sql ;
			int index = 0;
			for(AuthFunction af : afs){
				sql = new StringBuilder();
				sql.append("insert into t_bchlr_auth_function(ID,ROLE_ID,FUNCTION_ID,VISIBLE,USAGE)");
				sql.append(" values(sys_guid(),'").append(af.getRole().getId());
				sql.append("','").append(af.getFunc().getId()).append("',");
				sql.append("'1',").append("'1')");
				iSQL[index] = sql.toString();
				index++;
			}
			authFunctionDao.batchSaveAuthFunction(iSQL);
		}
	}

	public void batchDelAuthFunctions(String batchInfo) {
		if(!StringUtils.isEmpty(batchInfo)){
			String batchInfos[] = batchInfo.split(";");
			String dSQL[] = new String[batchInfos.length];
			log.info("---------------角色功能删除：要删除的角色功能是"+(batchInfos.length)+"条---------------");
			log.info("---------------角色功能删除：要删除的角色功能的SQL："+(batchInfo)+"条---------------");
			int index = 0;
			for(String delInfo : batchInfos){
				String delInfos[] = delInfo.split(",");
				dSQL[index] = " delete from t_bchlr_auth_function where ROLE_ID='"+delInfos[1]+"' and FUNCTION_ID='"+delInfos[0]+"'";
				index++;
			} 
			authFunctionDao.batchSaveAuthFunction(dSQL);
		}
		
	}

	@Override
	public List<AuthFunction> findByFuncIds(String ids) {
		
		return authFunctionDao.findByFuncIds(ids);
	}
}
