package cn.edugate.esb.dao.imp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.mapping.Table;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import cn.edugate.esb.dao.BaseDAO;
import cn.edugate.esb.dao.Cols;
import cn.edugate.esb.dao.DBListener.ChangeType;
import cn.edugate.esb.dao.DBListenerService;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Stopwatch;

public class BaseDAOImpl<T> implements BaseDAO<T> {

	static Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

	public SessionFactory factory;

	private Configuration cfg;

	private DBListenerService dbListener;

	@Autowired
	public void setDbListener(DBListenerService dbListener) {
		this.dbListener = dbListener;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.factory = sessionFactory;

	}

	@Autowired
	public void setLocalSessionFactoryBean(LocalSessionFactoryBean b) {
		if (b != null)
			this.cfg = b.getConfiguration();
	}

	@Override
	public void deleteById(Class<T> clazz, Serializable... ids) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.save(t);
			session.flush();
			LSHelper.processInjection(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(T... ts) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.update(t);
			session.flush();
			LSHelper.processInjection(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(T... ts) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		for (T t : ts) {
			session.saveOrUpdate(t);
			session.flush();
			LSHelper.processInjection(t);
		}
	}

	public void queryData(Class<T> clazz, Paging<T> paging, Criterion criterion, Order... orders) {
		this.queryData(clazz, paging, null, criterion, orders);
	}

	@Override
	public void queryData(Class<T> clazz, Paging<T> paging, Cols columns, Criterion criterion, Order... orders) {
		this.queryData(clazz, null, null, paging, null, criterion, orders);
	}

	@Override
	public void queryData(Class<T> clazz, String associationPath, JoinType joinType, Paging<T> paging, Cols columns,
			Criterion criterion, Order... orders) {
		// TODO Auto-generated method stub
		Stopwatch sw = Stopwatch.begin();
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(clazz);
		if (joinType != null) {
			c.createAlias(associationPath, associationPath, joinType);
			c.setFetchMode(associationPath, FetchMode.JOIN);
		}
		if (criterion != null)
			c.add(criterion);
		Long total = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		paging.setTotal(total);

		// 处理请求页超出数据范围
		if (paging.getStart() >= total) {
			paging.setPage(1);
			paging.setStart(0);
		}
		c.setProjection(null);
		this.handleColumns(c, columns, clazz);
		for (Order order : orders) {
			if (order != null)
				c.addOrder(order);
		}
		c.setFirstResult(paging.getStart());
		c.setMaxResults(paging.getLimit());
		@SuppressWarnings("unchecked")
		List<T> data = (List<T>) c.list();
		List<T> r = new ArrayList<T>();
		sw.log("before processInjection");
		for (T t : data) {
			if (t.getClass().isArray()) {
				for (Object o : (Object[]) t) {
					if (clazz.isInstance(o)) {
						@SuppressWarnings("unchecked")
						T o2 = (T) o;
						r.add(o2);
						LSHelper.processInjection(o2);
					}
				}
			} else {
				LSHelper.processInjection(t);
				r.add(t);
			}
		}
		paging.setData(r);
		sw.log("queryData");
	}

	@Override
	public List<T> list(Class<T> clazz, Criterion criterion, Order... orders) {
		return this.list(clazz, null, criterion, orders);
	}

	@Override
	public List<T> list(Class<T> clazz, Cols columns, Criterion criterion, Order... orders) {
		// TODO Auto-generated method stub
		return this.list(clazz, null, null, null, criterion, orders);
	}

	@Override
	public List<T> list(Class<T> clazz, String associationPath, JoinType joinType, Cols columns, Criterion criterion,
			Order... orders) {
		// TODO Auto-generated method stub
		Stopwatch sw = Stopwatch.begin();
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(clazz);
		if (joinType != null) {
			c.createAlias(associationPath, associationPath, joinType);
			c.setFetchMode(associationPath, FetchMode.JOIN);
		}
		if (criterion != null)
			c.add(criterion);
		this.handleColumns(c, columns, clazz);
		for (Order order : orders) {
			if (order != null)
				c.addOrder(order);
		}

		@SuppressWarnings("unchecked")
		List<T> data = (List<T>) c.list();
		sw.log("before processInjection");
		for (T t : data) {
			LSHelper.processInjection(t);
		}
		sw.log("list");
		return data;
	}

	private void handleColumns(Criteria c, Cols columns, Class<T> clazz) {
		if (columns != null && !columns.getColums().isEmpty()) {

			Set<String> cols = new HashSet<String>();
			if (columns.include()) {
				cols.addAll(columns.getColums());

			} else {
				Table tab = this.cfg.getClassMapping(clazz.getName()).getTable();
				List<String> ignores = columns.getColums();
				for (int i = 0; i <= tab.getColumnSpan(); i++) {
					String col = tab.getColumn(i).getName();
					if (!ignores.contains(col)) {
						cols.add(col);
					}
				}
			}
			ProjectionList pl = Projections.projectionList();
			for (String col : cols) {
				pl.add(Projections.property(col), col);
			}
			c.setProjection(pl);
			c.setResultTransformer(Transformers.aliasToBean(clazz));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(T... ts) {
		// TODO Auto-generated method stub
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
		this.dbListener.fireEvent(ChangeType.Delete, clazz, null);
		return count;
	}

	@Override
	public String tn(Class<?> clazz) {
		// TODO Auto-generated method stub

		return this.cfg.getClassMapping(clazz.getName()).getTable().getName();
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		@SuppressWarnings("unchecked")
		T t = (T) session.get(clazz, id);
		if (t != null) {
			LSHelper.processInjection(t);
		}

		return t;
	}

}
