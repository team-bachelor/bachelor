package org.bachelor.stat.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import org.bachelor.auth.domain.StatisticController;
import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.stat.dao.ICountMenuFunctionDao;
import org.bachelor.stat.vo.CountConditionVo;

@Repository
@SuppressWarnings(value = {"unchecked"})
public class CountMenuFunctionDaoImpl extends 
		GenericDaoImpl<StatisticController, String> implements ICountMenuFunctionDao {

	/**
	 * 统计系统运行效率
	 */
	@Override
	public List countSystemRunEfficiency(CountConditionVo cc) {
		StringBuilder qSQL = new StringBuilder();
		qSQL.append("select (select m.name from t_ufp_menu_info m  ");
		qSQL.append(" where m.function_id = up.id and rownum <2) as MenuName");
		qSQL.append(",round(avg(t.cost_time),0) CountNum from t_ufp_statistic_efficiency t,t_ufp_ps_function up ");
		qSQL.append(" where t.request_path = up.entry_path and t.type = '1' ");
		if(cc!=null){
			if(cc.getStartTime()!=null && !"".equals(cc.getStartTime()) &&  
					cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
				qSQL.append(" and t.ACCESS_DATE between to_date('").append(cc.getStartTime()).
				append("','yyyy-MM-dd')").append(" and to_date('").
				append(cc.getEndTime()).append("','yyyy-MM-dd') ");
			}else {
				if(cc.getStartTime()!=null && !"".equals(cc.getStartTime())){
					qSQL.append(" and t.ACCESS_DATE >= to_date('").append(cc.getStartTime()).
					append("','yyyy-MM-dd') ");
				}
				if(cc.getEndTime()!=null && !"".equals(cc.getEndTime())){
					qSQL.append(" and t.ACCESS_DATE <= to_date('").append(cc.getEndTime()).
					append("','yyyy-MM-dd') ");
				}
			}
		}
		qSQL.append(" group by t.request_path,up.id ");
		List sre_list = (ArrayList)getJdbcTemplate().query(qSQL.toString(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map map = new HashMap();
				map.put("label", rs.getString("MenuName"));
				map.put("value", rs.getString("CountNum"));
				return map;
			}});
		return sre_list;
	}

	@Override
	public void saveInfo(StatisticController sc) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cSQL = "insert into T_UFP_STATISTIC_EFFICIENCY(id,REQUEST_PATH,COST_TIME,TYPE,ACCESS_DATE) values(";
		cSQL += "'"+sc.getId()+"','"+sc.getRequestPath()+"',"+sc.getCostTime()+","+sc.getType()+",to_date('"+sdf.format(sc.getAccessDate())+"','yyyy-MM-dd HH24:mi:ss'))";
		getJdbcTemplate().execute(cSQL);
	}

}
