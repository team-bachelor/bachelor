package org.bachelor.bpm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.bpm.dao.INoticeDao;
import org.bachelor.bpm.domain.Notice;
import org.bachelor.dao.impl.GenericDaoImpl;

@Repository
@SuppressWarnings(value ={ "rawtypes","unchecked"})
public class NoticeDaoImpl extends GenericDaoImpl<Notice, String> implements INoticeDao{

	@Override
	public void batchNoticeReceiver(String[] cSQL) {
		
		getJdbcTemplate().batchUpdate(cSQL);
	}

	@Override
	public void batchNoticeRole(String[] cSQL) {
		
		getJdbcTemplate().batchUpdate(cSQL);
	}

	@Override
	public List<Notice> queryByUserId(String userId) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.TITLE,t.CONTENT,t.SENDER_ID,t.SENDER_NAME,t.URL," +
				"t.STATUS,t.CREATE_TIME,t.READ_TIME,t.FINISH_TIME,t.READ_ID,t.READ_NAME,t.DONE_ID,t.DONE_NAME ");
		qSQL.append(",r.role_desc,r.role_id ");
		qSQL.append("from t_ufp_bpm_notice t,t_ufp_bpm_notice_role r ");
		qSQL.append("where t.id = r.notice_id and ");
		qSQL.append(" r.role_id in (select tu.role_id from t_ufp_auth_role_user tu ");
		qSQL.append(" where tu.user_id='"+userId+"')");
		List<Notice> notice_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getResultObj(rs);
			}});
		return notice_list;
	}
	
	@Override
	public List<Notice> queryByRoleId(String roleId) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.TITLE,t.CONTENT,t.SENDER_ID,t.SENDER_NAME,t.URL," +
				"t.STATUS,t.CREATE_TIME,t.READ_TIME,t.FINISH_TIME,t.READ_ID,t.READ_NAME,t.DONE_ID,t.DONE_NAME ");
		qSQL.append(",r.role_desc,r.role_id ");
		qSQL.append("from t_ufp_bpm_notice t,t_ufp_bpm_notice_role r ");
		qSQL.append("where t.id = r.notice_id and ");
		qSQL.append(" r.role_id ='"+roleId+"')");
		List<Notice> notice_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getResultObj(rs);
			}});
		return notice_list;
	}

	@Override
	public List<Notice> queryByExample(Notice notice) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select t.ID,t.TITLE,t.CONTENT,t.SENDER_ID,t.SENDER_NAME,t.URL," +
				"t.STATUS,t.CREATE_TIME,t.READ_TIME,t.FINISH_TIME,t.READ_ID,t.READ_NAME,t.DONE_ID,t.DONE_NAME ");
		qSQL.append(",r.role_desc,r.role_id from ");
		qSQL.append(" t_ufp_bpm_notice t,t_ufp_bpm_notice_role r,t_ufp_bpm_notice_receiver v ");
		qSQL.append(" where t.id = r.notice_id and t.id = v.notice_id ");
		if(notice!=null){
			if(notice.getRecevierUserName()!=null && !"".equals(notice.getRecevierUserName())){
				qSQL.append(" and v.receiver_name like '%").append(notice.getRecevierUserName()).append("%' ");
			}
			if(notice.getRoleId()!=null && !"".equals(notice.getRoleId())){
				qSQL.append(" and r.role_id = '").append(notice.getRoleId()).append("' ");
			}
			if(notice.getStatus()!=null && !"".equals(notice.getStatus())){
				qSQL.append(" and t.status ='").append(notice.getStatus()).append("'");
			}
		}
		List<Notice> notice_list = getJdbcTemplate().query(qSQL.toString(), new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getResultObj(rs);
			}});
		return notice_list;
	}
	
	/**
	 * 得到结果集对象
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public Object getResultObj(ResultSet rs) throws SQLException{
		Notice notice = new Notice();
		notice.setId(rs.getString("ID"));
		notice.setTitle(rs.getString("TITLE"));
		notice.setContent(rs.getString("content"));
		notice.setSenderId(rs.getString("sender_id"));
		notice.setSenderName(rs.getString("sender_name"));
		notice.setUrl(rs.getString("url"));
		notice.setStatus(rs.getString("status"));
		notice.setCreateTime(rs.getDate("create_time"));
		notice.setReadTime(rs.getDate("read_time"));
		notice.setFinishTime(rs.getDate("finish_time"));
		notice.setReadId(rs.getString("read_id"));
		notice.setReadName(rs.getString("read_name"));
		notice.setDoneId(rs.getString("done_id"));
		notice.setDoneName(rs.getString("done_name"));
		notice.setRoleId(rs.getString("role_id"));
		notice.setRoleName(rs.getString("role_name"));
		return notice;
	}
}
