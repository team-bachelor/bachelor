package org.bachelor.auth.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.bachelor.auth.dao.IAuthLoginDao;
import org.bachelor.auth.domain.AuthLogin;
import org.bachelor.dao.impl.GenericDaoImpl;

@Repository
public class AuthLoginDaoImpl extends GenericDaoImpl<AuthLogin, String>  implements IAuthLoginDao {

	@Override
	public List<AuthLogin> findByExample(AuthLogin al) {
		
		return null;
	}

}
