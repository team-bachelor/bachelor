package org.bachelor.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.auth.dao.IUserManageDao;
import org.bachelor.auth.service.IUserManageService;
import org.bachelor.org.domain.User;

@Service
public class UserManageServiceImpl implements IUserManageService {

	@Autowired
	private IUserManageDao userManageDao = null;
	
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		userManageDao.save(user);
	}

	@Override
	public void addOrUpdate(User user) {
		// TODO Auto-generated method stub
		userManageDao.saveOrUpdate(user);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		userManageDao.delete(user);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setId(id);
		userManageDao.delete(user);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userManageDao.findAll();
	}

	@Override
	public List<User> findByExample(User user) {
		// TODO Auto-generated method stub
		return userManageDao.findByExample(user);
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return userManageDao.findById(id);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		userManageDao.update(user);
	}

	@Override
	public List<User> validateById(String id) {
		// TODO Auto-generated method stub
		return userManageDao.validateById(id);
	}

	@Override
	public void batchDelete(String info) {
		String[] dInfo = info.split(",");
		String[] dSQL = new String[dInfo.length];
		int index = 0;
		for(String tempInfo:dInfo){
			dSQL[index] = "delete from T_bchlr_USER where id='"+tempInfo+"'";
			index++;
		}
		userManageDao.batchDelete(dSQL);
	}

}
