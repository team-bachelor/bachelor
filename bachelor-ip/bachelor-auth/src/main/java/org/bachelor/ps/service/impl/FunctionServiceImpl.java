/*
 * @(#)FunctionServiceImpl.java	Mar 26, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.ps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.bachelor.ps.dao.IFunctionDao;
import org.bachelor.ps.domain.Function;
import org.bachelor.ps.service.IFunctionService;

/**
 * @author user
 *
 */
@RequestMapping("func")
@Service(value="functionService")
public class FunctionServiceImpl   implements IFunctionService{

	@Autowired
	private IFunctionDao dao;

	@Override
	public void saveOrUpdate(Function func) {
		
		dao.saveOrUpdate(func);
	}

	@Override
	public void delete(Function fnc) {
		dao.delete(fnc);
	}

	@Override
	public Function findById(String id) {
		return dao.findById(id);
	}

	
	@Override
	@RequestMapping("findByUrl")
	@ResponseBody
	public Function findByUrl(String url) {
		List<Function> func_list = dao.findByUrl(url);
		Function func = new Function();
		if(func_list!=null && func_list.size()>0){
			func = func_list.get(0);
		}
		return func;
	}

	@Override
	public Function findByCode(String code) {
		List<Function> func_list = dao.findByCode(code);
		Function func = new Function();
		if(func_list!=null && func_list.size()>0){
			func = func_list.get(0);
		}
		return func;
	}

	@Override
	public void deleteById(String id) {
		
		dao.deleteById(id);
	}

	@Override
	public List<Function> findByLikeUrl(String url) {
		
		return dao.findByLikeUrl(url);
	}
	
	
}
