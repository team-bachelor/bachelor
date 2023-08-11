package org.bachelor.ps.dao;

import java.util.List;
import org.bachelor.dao.IGenericDao;
import org.bachelor.ps.domain.ProjectModuleExt;

public interface IProjectModuleExtDao extends IGenericDao<ProjectModuleExt, String> {
	
	public List<ProjectModuleExt> findModuleExt(ProjectModuleExt pme);
}
