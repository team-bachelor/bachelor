package org.bachelor.auth.dao;

import java.util.List;

import org.bachelor.auth.domain.AuthLogin;
import org.bachelor.dao.IGenericDao;

public interface IAuthLoginDao extends IGenericDao<AuthLogin, String>{

	public List<AuthLogin> findByExample(AuthLogin al);
}
