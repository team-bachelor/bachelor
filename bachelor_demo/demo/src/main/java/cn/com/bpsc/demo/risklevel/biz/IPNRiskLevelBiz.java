package org.bachelor.demo.risklevel.biz;

import java.util.List;

import org.bachelor.demo.risklevel.domain.PNRiskLevel;

public interface IPNRiskLevelBiz {

	/**
	 * 保存风险信息
	 * @param riskLevel
	 */
	public void save(PNRiskLevel riskLevel);
	
	/**
	 * 更新风险信息
	 * @param riskLevel
	 */
	public void update(PNRiskLevel riskLevel);
	
	/**
	 * 删除风险信息
	 * @param riskLevel
	 */
	public void delete(PNRiskLevel riskLevel);
	
	/**
	 * 根据风险ID，查询风险对象
	 * @param riskLevel
	 */
	public PNRiskLevel findById(String id);
	
	/**
	 * 根据风险对象，查询风险集合信息
	 * @param riskLevel
	 */
	public List<PNRiskLevel> findAll();
	
	/**
	 * 保存风险信息 或者 更新风险信息
	 * @param riskLevel
	 */
	public void saveOrUpdate(PNRiskLevel riskLevel);
}
