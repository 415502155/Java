package com.gm.dao.mysql;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface BaseDao {

	Query createQuery(String queryString) throws DataAccessException;

	void persist(final Serializable entity) throws DataAccessException;

	void merge(final Serializable entity) throws DataAccessException;

	void delete(final Serializable entity) throws DataAccessException;

	void delete(Class entityClass, Serializable id)  throws DataAccessException;

	void save(final Serializable entity) throws DataAccessException;

	void update(final Serializable entity) throws DataAccessException;

	void saveOrUpdate(final Serializable entity) throws DataAccessException;

	void batchSaveOrUpdate(Collection entities) throws DataAccessException;

	<T> T get(Class<T> entityClass, Serializable id) throws DataAccessException;

	<T> T load(Class<T> entityClass, Serializable id) throws DataAccessException;

	void excuteHQL(String hql, Object... objects) throws HibernateException;

	List findByHql(String hql) throws HibernateException;

	List findByHql(String hql, Object... objects)  throws HibernateException;

	List findByHql(String hql, boolean cacheable)  throws HibernateException;

	List findByHql(String hql, boolean cacheable, Object... objects) throws HibernateException;

	List findByLimitTotal(String hql, int resultNum, Object... args) throws HibernateException;

	List findByLimitTotal(String hql, int resultNum, boolean cacheable, Object... args) throws HibernateException;

	Object findUnique(final String hql, boolean cacheable, Object... objects) throws HibernateException;

	<T> T findUnique(Class<T> entityClass, String hql, boolean cacheable, Object... objects) throws HibernateException;

	Object findUnique(final String hql, Object... objects) throws HibernateException;

	<T> T findUnique(Class<T> entityClass, String hql, Object... objects) throws HibernateException;

	List findBySql(String sql) throws HibernateException;

	List findBySql(String sql, boolean cacheable) throws HibernateException;

	List findBySql(String sql, Object... objects) throws HibernateException;

	List findBySql(String sql, boolean cacheable, Object... objects) throws HibernateException;

	int excuteSQL(String sql, Object... objects) throws HibernateException;

	int getNum(String hql);

	SQLQuery createSqlQuery(String sql) throws DataAccessException;
	
	HibernateTemplate getHibernateTemplete();

	List<Map> getlisMaps(final String sql, final Object... objects)  throws HibernateException;
	
	void save(Object entity) throws DataAccessException;

}
