package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.dao.IGenericDao;
import org.bachelor.org.domain.User;

public interface IUserManageDao extends IGenericDao<User, String> {

	public List<User> findByExample(User user);
	
	public List<User> validateById(String id);
	 
	public void batchDelete(String[] dSQL);
}
