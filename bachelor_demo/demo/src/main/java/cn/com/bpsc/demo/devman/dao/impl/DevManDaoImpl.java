package org.bachelor.demo.devman.dao.impl;

import org.springframework.stereotype.Repository;

import org.bachelor.demo.devman.dao.IDevManDao;
import org.bachelor.demo.devman.domain.DevManDomain;
import org.bachelor.dao.impl.GenericDaoImpl;

@Repository
public class DevManDaoImpl  extends GenericDaoImpl<DevManDomain,String> implements IDevManDao {


}
