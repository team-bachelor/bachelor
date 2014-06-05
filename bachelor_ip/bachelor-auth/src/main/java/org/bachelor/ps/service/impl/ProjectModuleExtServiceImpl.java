package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.ps.dao.IProjectModuleExtDao;
import org.bachelor.ps.domain.ProjectModuleExt;
import org.bachelor.ps.service.IProjectModuleExtService;
/**
 * 
 *  <code>IProjectModuleExtService.java</code>
 *  <p>功能: 项目模块扩展信息业务处理实现类
 *  
 *  <p>Copyright 华商 2013 All right reserved.
 *  @author 曾强 zengqiang_1989@126.com
 *  @version 1.0	
 *  <p>时间 2013-4-18 上午09:58:24
 *  <p>最后修改曾 强
 */
@Service
public class ProjectModuleExtServiceImpl implements IProjectModuleExtService {

	@Autowired
	private IProjectModuleExtDao dao = null;
	
	@Override
	public void delete(ProjectModuleExt pm) {
		 
		dao.delete(pm);
	}

	@Override
	public ProjectModuleExt findByName(String id) {
		 
		return dao.findById(id);
	}

	@Override
	public List<ProjectModuleExt> findModuleExt(ProjectModuleExt pme) {
		 
		return dao.findModuleExt(pme);
	}

	@Override
	public void saveOrUpdate(ProjectModuleExt pm) {
		
		dao.saveOrUpdate(pm);
	}
}
