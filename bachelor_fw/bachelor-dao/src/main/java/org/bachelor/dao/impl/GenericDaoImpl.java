/*
 * @(#)GenericDaoImpl.java	Mar 5, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.context.common.ContextConstant;
import org.bachelor.context.service.IVLService;
import org.bachelor.dao.IGenericDao;
import org.bachelor.dao.QueryParamSetter;
import org.bachelor.context.vo.PageVo;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author
 * 
 */
@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, ID extends Serializable> implements
		IGenericDao<T, ID> {

	private Log log = LogFactory.getLog(this.getClass());

	private Class<T> persistentClass;

	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

	@Autowired(required = false)
	private SessionFactory sessionFactory;

	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	private IVLService vlService;

	public GenericDaoImpl() {
		// this.persistentClass = (Class<T>) ((ParameterizedType)
		// getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		if (persistentClass == null) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return persistentClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#findById(java.io.Serializable, boolean)
	 */
	@Override
	public T findById(ID id) {
		Object obj = getSession().get(getPersistentClass(), id);
		T t = (T) obj;
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		DetachedCriteria dc = getDetachedCriteria();
		return findByCriteria(dc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#save(java.lang.Object)
	 */
	@Override
	public void save(T entity) {
		getSession().persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	protected List<T> findByHQL(String hql) {
		return findByHQL(hql, null);
	}

	protected List<T> findByHQL(String hql, QueryParamSetter setter) {
		Query query = getSession().createQuery(hql);
		if (setter != null) {
			setter.set(query);
		}
		PageVo pageVo = (PageVo) vlService
				.getRequestAttribute(ContextConstant.VL_PAGE_INFO_KEY);
		if (pageVo == null) {
			log.debug("没有分页信息，查询全部记录。");
			return query.list();
		}
		// 取得全部记录数
		int count = query.list().size();
		pageVo.setCount(count);
		// 计算全部页数
		int pageCount = count / pageVo.getPageRowNum();
		if (pageCount * pageVo.getPageRowNum() <= count) {
			pageCount++;
		}

		int startIndex = pageVo.getStartIndex();
		int endIndex = pageVo.getEndIndex();
		log.debug("分页开始位置：" + startIndex);
		log.debug("分页结束位置：" + endIndex);
		if (endIndex <= startIndex || endIndex <= 0) {
			return query.list();
		}
		// 设置全部页数
		pageVo.setPageCount(pageCount);
		// 设置当前页数
		if (startIndex <= 0) {
			pageVo.setPage(0);
		} else {
			int page = startIndex / pageVo.getPageRowNum();
			pageVo.setPage(page);
		}
		query.setFirstResult(pageVo.getStartIndex());
		query.setMaxResults(pageVo.getPageRowNum());
		return query.list();
	}

	protected List<T> findNoPageByHQL(String hql) {
		return findNoPageByHQL(hql, null);
	}

	protected List<T> findNoPageByHQL(String hql, QueryParamSetter setter) {
		Query query = getSession().createQuery(hql);
		if (setter != null) {
			setter.set(query);
		}
		return query.list();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public boolean exist(String propertyName, Object propertyValue) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq(propertyName, propertyValue));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		int count = Integer.valueOf(((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue());
		return (count > 0 ? true : false);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected DetachedCriteria getDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(getPersistentClass());
		return dc;
	}

	protected Criteria getCriteria() {
		Criteria c = getSession().createCriteria(getPersistentClass());
		return c;
	}

	@Override
	public void merge(T t) {
		getSession().merge(t);
	}

	protected List<T> findByCriteriaNoPage(DetachedCriteria dc) {
		return findByCriteriaNoPage(dc, Criteria.DISTINCT_ROOT_ENTITY);
	}

	protected List<T> findByCriteriaNoPage(DetachedCriteria dc,
			ResultTransformer transformer) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(transformer);
		criteria.setLockMode(LockMode.NONE);
		return criteria.list();
	}

	protected List<T> findByCriteria(DetachedCriteria dc) {
		return findByCriteria(dc, Criteria.DISTINCT_ROOT_ENTITY);
	}

	protected List<T> findByCriteria(DetachedCriteria dc,
			ResultTransformer transformer) {
		PageVo pageVo = (PageVo) vlService
				.getRequestAttribute(ContextConstant.VL_PAGE_INFO_KEY);
		return findByCriteria(dc, transformer, pageVo);
	}

	protected T findFirstByCriteria(DetachedCriteria dc) {
		return findFirstByCriteria(dc, Criteria.DISTINCT_ROOT_ENTITY);
	}

	protected T findFirstByCriteria(DetachedCriteria dc,
			ResultTransformer transformer) {
		PageVo pageVo = new PageVo();
		pageVo.setStartIndex(0);
		pageVo.setEndIndex(1);
		pageVo.setPageRowNum(1);
		List<T> list = findByCriteria(dc, transformer, pageVo);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	private List<T> findByCriteria(DetachedCriteria dc,
			ResultTransformer transformer, PageVo pageVo) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setCacheable(true);
		criteria.setResultTransformer(transformer);
		criteria.setLockMode(LockMode.NONE);
		if (pageVo == null) {
			log.debug("没有分页信息，查询全部记录。");
			return criteria.list();
		}

		// 取得全部记录数
		/**
		 * 为了应对横向切分的情况，修改为将所有结果相加。by 刘卓
		 */
		List counts = criteria.setProjection(Projections.rowCount()).list();
		long count = 0;
		for (Object obj : counts) {
			count = count + ((Long) obj);
		}
		criteria.setProjection(null);
		criteria.setResultTransformer(transformer);
		pageVo.setCount(count);
		// 计算全部页数
		long pageCount = count / pageVo.getPageRowNum();
		if (pageCount * pageVo.getPageRowNum() < count) {
			pageCount++;
		}

		int startIndex = pageVo.getStartIndex();
		int limitIndex = pageVo.getEndIndex();
		log.debug("分页开始位置：" + startIndex);
		log.debug("分页结束位置：" + limitIndex);
		log.debug("分页参数不正确，查询全部记录。");
		if (limitIndex <= startIndex || limitIndex <= 0) {
			return criteria.list();
		}
		// 设置全部页数
		pageVo.setPageCount(pageCount);
		// 设置当前页数
		if (startIndex <= 0) {
			pageVo.setPage(0);
		} else {
			int page = startIndex / pageVo.getPageRowNum();
			pageVo.setPage(page);
		}
		List<T> list = criteria.setFirstResult(pageVo.getStartIndex())
				.setMaxResults(pageVo.getPageRowNum()).list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bachelor.dao.IGenericDao#findByProperty(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq(propertyName, propertyValue));
		return findByCriteria(dc);
	}

	protected int executeHql(String hql) {
		int num = getSession().createQuery(hql).executeUpdate();
		return num;
	}

	protected String pageSql(String sql) {
		PageVo pageVo = (PageVo) vlService
				.getRequestAttribute(ContextConstant.VL_PAGE_INFO_KEY);
		if (pageVo == null) {
			log.debug("没有分页信息，查询全部记录。");
			return sql;
		}
		// 取得全部记录数
		String countSql = "select count(*) count from (" + sql + ")";
		List<Map<String, Object>> counts = getJdbcTemplate().queryForList(
				countSql);
		long count = 0;
		for (Object obj : counts) {
			Map<String, Object> map = (Map<String, Object>) obj;
			count = count + ((Long) map.get("count"));
		}
		pageVo.setCount(count);
		// 计算全部页数
		long pageCount = count / pageVo.getPageRowNum();
		if (pageCount * pageVo.getPageRowNum() <= count) {
			pageCount++;
		}

		int startIndex = pageVo.getStartIndex();
		int endIndex = pageVo.getEndIndex();
		log.debug("分页开始位置：" + startIndex);
		log.debug("分页结束位置：" + endIndex);
		if (endIndex <= startIndex || endIndex <= 0) {
			return sql;
		}
		// 设置全部页数
		pageVo.setPageCount(pageCount);
		// 设置当前页数
		if (startIndex <= 0) {
			pageVo.setPage(0);
		} else {
			int page = startIndex / pageVo.getPageRowNum();
			pageVo.setPage(page);
		}

		String pageSql = "select * from(select tmp_a.*, rownum rn from (" + sql
				+ ") tmp_a where rownum <= " + endIndex + ") where rn > "
				+ startIndex;
		return pageSql;
	}

	@Override
	public void evict(T entity) {
		getSession().evict(entity);
	}
}
