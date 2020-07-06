package cn.org.bachelor.dao;

import org.hibernate.Query;

public interface QueryParamSetter {
	void set(Query query);
}
