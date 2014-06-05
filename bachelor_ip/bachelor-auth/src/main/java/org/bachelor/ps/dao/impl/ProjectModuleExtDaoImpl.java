package org.bachelor.ps.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.bachelor.ps.dao.IProjectModuleExtDao;
import org.bachelor.ps.domain.ProjectModuleExt;

@Repository
public class ProjectModuleExtDaoImpl extends GenericDaoImpl<ProjectModuleExt, String> implements IProjectModuleExtDao {

	@Override
	public List<ProjectModuleExt> findModuleExt(ProjectModuleExt pme) {
		String hql = "from ProjectModuleExt where 1=1 ";
		
		if(!StringUtils.isEmpty(pme.getFlag())){
			hql += " and flag='"+pme.getFlag()+"'";
		} 
		if(!StringUtils.isEmpty(pme.getModuleId())){
			hql += " and moduleId='"+pme.getModuleId()+"'";
		} 
		return super.findByHQL(hql); 
	}
 
}
