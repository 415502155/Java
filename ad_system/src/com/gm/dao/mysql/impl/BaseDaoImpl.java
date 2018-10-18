package com.gm.dao.mysql.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gm.dao.mysql.BaseDao;


/**
 * 声明此类为数据持久层的类
 * 
 * @author jizhq
 * 
 */

@Repository
@Transactional
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Query createQuery(final String queryString) throws DataAccessException{
		return this.getHibernateTemplate().execute(new HibernateCallback<Query>() {

			@Override
			public Query doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				return arg0.createQuery(queryString);
			}
		});
	}
	
	public SQLQuery createSqlQuery(final String sql)  throws DataAccessException{
		return this.getHibernateTemplate().execute(new HibernateCallback<SQLQuery>() {

			@Override
			public SQLQuery doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				return arg0.createSQLQuery(sql);
			}
		});
	}

	public void persist(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().persist(entity);
	}

	public void merge(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().merge(entity);
	}

	public void delete(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().delete(entity);
	}

	public void delete(Class entityClass, Serializable id) throws DataAccessException{
		this.getHibernateTemplate().delete(load(entityClass, id));
	}

	public void save(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().save(entity);
	}

	public void update(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(Serializable entity) throws DataAccessException{
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public void batchSaveOrUpdate(Collection entities) throws DataAccessException{
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException{
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public <T> T load(Class<T> entityClass, Serializable id) throws DataAccessException{
		return this.getHibernateTemplate().load(entityClass, id);
	}

	public void excuteHQL(String hql, Object... objects) throws HibernateException{
		Query query = createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	public List findByHql(String hql) throws HibernateException {
		Query query = createQuery(hql);
		return query.list();
	}

	public List findByHql(String hql, Object... objects) throws HibernateException{
		Query query = createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	public List findByHql(String hql, boolean cacheable)  throws HibernateException{
		Query query = createQuery(hql);
		query.setCacheable(cacheable);
		return query.list();
	}

	public List findByHql(String hql, boolean cacheable, Object... objects)  throws HibernateException{
		Query query = createQuery(hql);
		query.setCacheable(cacheable);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	public List findByLimitTotal(String hql, int resultNum, Object... args)  throws HibernateException{
		Query query = createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.setFirstResult(0).setMaxResults(resultNum).list();
	}

	public List findByLimitTotal(String hql, int resultNum, boolean cacheable, Object... args)  throws HibernateException{
		Query query = createQuery(hql);
		query.setCacheable(true);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.setFirstResult(0).setMaxResults(resultNum).list();
	}

	public Object findUnique(String hql, Object... objects) throws HibernateException{

		Query query = createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.uniqueResult();
	}

	public <T> T findUnique(Class<T> entityClass, String hql, Object... objects)  throws HibernateException{
		Query query = createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return (T) query.uniqueResult();
	}

	public Object findUnique(String hql, boolean cacheable, Object... objects)  throws HibernateException{
		Query query = createQuery(hql);
		query.setCacheable(true);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query.uniqueResult();
	}

	public <T> T findUnique(Class<T> entityClass, String hql,
			boolean cacheable, Object... objects)  throws HibernateException{
		Query query = createQuery(hql);
		query.setCacheable(true);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return (T) query.uniqueResult();
	}

	public List findBySql(final String sql)  throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	public List findBySql(final String sql, final Object... objects)  throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < objects.length; i++) {
					query.setParameter(i, objects[i]);
				}
				return query.list();
			}
		});
	}

	public List findBySql(final String sql, final boolean cacheable)  throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setCacheable(cacheable);
				return query.list();
			}
		});
	}

	public List findBySql(final String sql, final boolean cacheable, final Object... objects)  throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

			@Override
			public List doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < objects.length; i++) {
					query.setParameter(i, objects[i]);
				}
				query.setCacheable(cacheable);
				return query.list();
			}
		});
	}

	public String getCountSql(String hql) {
		int fromIndex = hql.toLowerCase().indexOf("from");
		String counthql = hql.substring(fromIndex);
		int groupIndex = counthql.indexOf("order by");
		if (groupIndex > 0) {
			counthql = counthql.substring(0, groupIndex - 1);
		}
		return counthql = "select count(*) " + counthql;
	}

	public int excuteSQL(final String sql, final Object... objects) throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < objects.length; i++) {
					query.setParameter(i, objects[i]);
				}
				return query.executeUpdate();
			}
		});
	}

	public int getNum(String hql)  throws HibernateException{
		return new Integer(createQuery(hql).uniqueResult().toString()).intValue();
	}

	public HibernateTemplate getHibernateTemplete() {
		return this.getHibernateTemplate();
	}

	public List<Map> getlisMaps(final String sql, final Object... objects)  throws HibernateException{
		return this.getHibernateTemplate().execute(new HibernateCallback<List<Map>>() {
			@Override
			public List<Map> doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery sqlquery = session.createSQLQuery(sql.toString());
				for (int i = 0; i < objects.length; i++) {
					sqlquery.setParameter(i, objects[i]);
				}
				sqlquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return sqlquery.list();
			}
		});
	}

	@Override
	public void save(Object entity) throws DataAccessException {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(entity);
	}

}
