package sng.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sng.dao.ShardingDBBaseDao;



@Repository
public class ShardingDBBaseDaoImpl<T> implements ShardingDBBaseDao<T> {

	static Logger logger = LoggerFactory.getLogger(ShardingDBBaseDaoImpl.class);

	@Autowired
	//@Resource(name = "sessionFactory")
	@Resource(name = "sessionFactory4Sharding")
	public SessionFactory factory;

	// @Autowired
	// private DBListenerService dbListener;

	
	/*public void setSessionFactory(SessionFactory sessionFactory) {
		this.factory = sessionFactory;
	}*/

	@Override
	public void deleteById(Class<T> clazz, Serializable... ids) {
		Session session = this.factory.getCurrentSession();

		for (Serializable id : ids) {
			Object t = session.get(clazz, id);
			session.delete(t);
			session.flush();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(T... ts) {
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.save(t);
			session.flush();
			// LSHelper.processInjection(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(T... ts) {
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.update(t);
			session.flush();
			// LSHelper.processInjection(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(T... ts) {
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.saveOrUpdate(t);
			session.flush();
			// LSHelper.processInjection(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(T... ts) {

		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.delete(t);
			session.flush();
		}
	}

	public String toSql(Criterion criterion, String alias) {
		StringBuilder sql = new StringBuilder();

		return sql.toString();
	}

	public int delete(Class<T> clazz, Criterion criterion) {
		int count = 0;
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(clazz);
		String alias = CriteriaQueryTranslator.ROOT_SQL_ALIAS;
		StringBuilder hql = new StringBuilder();
		hql.append("delete ").append(" from ").append(clazz.getSimpleName()).append(" ").append(alias);
		CriteriaQuery translator = new CriteriaQueryTranslator((SessionFactoryImplementor) factory, (CriteriaImpl) c,
				clazz.getName(), alias);

		if (criterion != null) {
			hql.append(" where ").append(criterion.toSqlString(c, translator));

		}
		Query q = session.createQuery(hql.toString());

		if (criterion != null) {
			int i = 0;
			for (TypedValue tv : criterion.getTypedValues(c, translator)) {
				q.setParameter(i++, tv.getValue(), tv.getType());
			}

		}
		count = q.executeUpdate();
		// this.dbListener.fireEvent(ChangeType.Delete, clazz, null);
		return count;
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		Session session = this.factory.getCurrentSession();
		@SuppressWarnings("unchecked")
		T t = (T) session.get(clazz, id);
		if (t != null) {
			// LSHelper.processInjection(t);
		}

		return t;
	}

}
