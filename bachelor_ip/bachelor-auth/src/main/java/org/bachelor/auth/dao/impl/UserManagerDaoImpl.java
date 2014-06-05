package org.bachelor.auth.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.auth.dao.IUserManagerDao;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.org.dao.IOrgDao;
import org.bachelor.org.domain.User;

@Repository
@SuppressWarnings("unchecked")
public class UserManagerDaoImpl extends GenericDaoImpl<User, String> implements
		IUserManagerDao {

	//TODO 
	@Autowired
	private IOrgDao orgDao;
	
	@Override
	public List<User> findByExample(User user) {
		StringBuilder qSQL = new StringBuilder();

		qSQL.append("select ID,OWNER_ORG_ID,USERNAME,MEMO,TYPE,DUTY,PWD,IDENTIFY_CODE,");
		qSQL.append("GENDER,LOGIN_FLAG,STATUS_FLAG,CREATE_TIME,UPDATE_TIME,SYNC_TIME,");
		qSQL.append("(select o.name from t_ufp_org o where o.id = OWNER_ORG_ID) as orgname ");
		qSQL.append(" from T_UFP_USER where STATUS_FLAG = '1'");
		if(user!=null){
			if(user.getOwnerOrg()!=null && user.getOwnerOrg().getId()!=null && !user.getOwnerOrg().getId().equals("")){
				qSQL.append(" and OWNER_ORG_ID='").append(user.getOwnerOrg().getId()).append("'");
			}
			if(user.getId()!=null && !user.getId().equals("")){
				qSQL.append(" and ID like '%").append(user.getId()).append("%'");
			}
			if(user.getUsername()!=null && !user.getUsername().equals("")){
				qSQL.append(" and USERNAME like '%").append(user.getUsername()).append("%'");
			}
		} 
		qSQL.append(" order by ID");
		String sql = pageSql(qSQL.toString());
		List<User> user_list = getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public User mapRow(ResultSet rst, int arg1) throws SQLException {
				User user = new User();
				user.setId(rst.getString("ID"));
				user.setOwnerOrgId(rst.getString("OWNER_ORG_ID"));
				user.setUsername(rst.getString("USERNAME"));
				user.setMemo(rst.getString("MEMO"));
				user.setType(rst.getString("TYPE"));
				user.setDuty(rst.getString("DUTY"));
				user.setPwd(rst.getString("PWD"));
				user.setIdentifyCode(rst.getString("IDENTIFY_CODE"));
				user.setGender(rst.getString("GENDER"));
				user.setLoginFlag(rst.getString("LOGIN_FLAG"));
				user.setStatusFlag(rst.getString("STATUS_FLAG"));
				user.setCreateTime(rst.getDate("CREATE_TIME"));
				user.setUpdateTime(rst.getDate("UPDATE_TIME"));
				user.setSyncTime(rst.getDate("SYNC_TIME"));
				user.setOwnerOrgName(rst.getString("orgname"));
				user.setOwnerOrg(orgDao.findById(user.getOwnerOrgId()));
				return user;
			}});
		
		return user_list;
	}
 
	@Override
	public void delete(User user) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append(" update T_UFP_USER STATUS_FLAG = '2'  where id='").append(user.getId()).append("'");
		getJdbcTemplate().update(qSQL.toString());
	}

	@Override
	public List<User> validateById(String id) {
		// TODO Auto-generated method stub
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("from User where id='").append(id).append("'");
		Query query  = getSession().createQuery(qSQL.toString());
		return query.list();
	}
	
	@Override
	public void update(User user){
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("update T_UFP_USER set  ID=''");
		qSQL.append(",OWNER_ORG_ID=''");
		qSQL.append(",USERNAME=''");
		qSQL.append(",MEMO=''");
		qSQL.append(",TYPE,DUTY,PWD,IDENTIFY_CODE,");
		qSQL.append("GENDER,LOGIN_FLAG,STATUS_FLAG,CREATE_TIME,UPDATE_TIME,SYNC_TIME,");
		qSQL.append(" from T_UFP_USER where STATUS_FLAG = '1'");
	}
}
