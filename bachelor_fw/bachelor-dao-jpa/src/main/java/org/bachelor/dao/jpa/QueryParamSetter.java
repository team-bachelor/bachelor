package cn.org.bachelor.dao.jpa;

import org.hibernate.Query;

public interface QueryParamSetter {
	void set(Query query);
}
