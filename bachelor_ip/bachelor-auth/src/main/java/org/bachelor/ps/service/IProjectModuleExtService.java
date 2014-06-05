package org.bachelor.ps.service;

import java.util.List;
import org.bachelor.ps.domain.ProjectModuleExt;

/**
 * 
 *  <code>IProjectModuleExtService.java</code>
 *  <p>功能: 项目模块扩展信息业务处理接口
 *  
 *  <p>Copyright 华商 2013 All right reserved.
 *  @author 曾强 zengqiang_1989@126.com
 *  @version 1.0	
 *  <p>时间 2013-4-18 上午09:58:24
 *  <p>最后修改曾 强
 */
public interface IProjectModuleExtService {

	public void saveOrUpdate(ProjectModuleExt pm);
	
	public void delete(ProjectModuleExt pm);
	
	public ProjectModuleExt findByName(String id);
	
	public List<ProjectModuleExt> findModuleExt(ProjectModuleExt pme);
	
}
