package org.bachelor.auth.service;

import java.util.List;

import org.bachelor.org.domain.User;

public interface IUserManageService {
	
	public void add(User user);
	
	public void addOrUpdate(User user);
	
	public void update(User user);
	
	public void delete(User user);
	
	public void deleteById(String id);
	
	public User findById(String id);
	
	public List<User> findAll();
	
	public List<User> findByExample(User user);
	
	public List<User> validateById(String id);
	
	public void batchDelete(String info);
}
