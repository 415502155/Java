package sng.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.CriteriaSpecification;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import sng.dao.BaseDao;
import sng.dao.Cols;
import sng.util.Paging;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	public SessionFactory factory;

	private Configuration cfg;

	// @Autowired
	// private DBListenerService dbListener;

	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.factory = sessionFactory;

	}

	@Autowired
	@Qualifier("sessionFactory")
	public void setLocalSessionFactoryBean(LocalSessionFactoryBean b) {
		if (b != null)
			this.cfg = b.getConfiguration();
	}

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
		for (T t : data) {
			if (t.getClass().isArray()) {
				for (Object o : (Object[]) t) {
					if (clazz.isInstance(o)) {
						@SuppressWarnings("unchecked")
						T o2 = (T) o;
						r.add(o2);
						// LSHelper.processInjection(o2);
					}
				}
			} else {
				// LSHelper.processInjection(t);
				r.add(t);
			}
		}
		paging.setData(r);
	}

	@Override
	public List<T> list(Class<T> clazz, Criterion criterion, Order... orders) {
		return this.list(clazz, null, criterion, orders);
	}

	@Override
	public List<T> list(Class<T> clazz, Cols columns, Criterion criterion, Order... orders) {

		return this.list(clazz, null, null, null, criterion, orders);
	}

	@SuppressWarnings("unused")
	@Override
	public List<T> list(Class<T> clazz, String associationPath, JoinType joinType, Cols columns, Criterion criterion,
			Order... orders) {

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
		for (T t : data) {
			// LSHelper.processInjection(t);
		}
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
	public String tn(Class<?> clazz) {
		return this.cfg.getClassMapping(clazz.getName()).getTable().getName();
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
	
	
	
	
	
	
	
	
	
	@Override
	public <T> Serializable saveOne(T t) {
		Session session = this.factory.getCurrentSession();
		Serializable serializable=session.save(t);
		session.flush();
		return serializable;
	}

	@Override
	public <T> void updateOne(T t) {
		Session session = this.factory.getCurrentSession();
		session.update(t);
		session.flush();
	}

	@Override
	public <T> T findById(Class<T> clazz,Serializable id) {
		Session session = this.factory.getCurrentSession();
		T t=(T)session.get(clazz, id);
		return t;
	}
	
	
	public List queryListByHql(String hql,List<Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createQuery(hql);
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		List list=query.list();
		return list;
	}
	
	public List queryListByHql(String hql,Map<String,Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createQuery(hql);
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List list=query.list();
		return list;
	}
	
	@Override
	public <T> Paging<T> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Class<T> clazz,List<Object> params){
		int maxPageSize=this.countBySql(countSql, params);
		List<T> list=this.queryListSql(sql, pageNo, pageSize, clazz, params);
		Paging<T> page = new Paging<T>();
		page.setPage(pageNo);//页号
		page.setLimit(pageSize);//每页记录数
		page.setSize(this.getMaxPageNo(maxPageSize, pageSize));//总页数
		page.setTotal(maxPageSize);//总记录数
		page.setStart((pageNo-1)*pageSize);//起始记录数
		page.setData(list);
		return page;
	}
	
	@Override
	public <T> List<T> queryListBySql(String sql,Class<T> clazz,List<Object> params) {
		List<T> list=this.queryListSql(sql, null, null, clazz, params);
		return list;
	}
	
	@Override
	public int countBySql(String sql,List<Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	@Override
	public Object queryObjectBySql(String sql,List<Object> params) {
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.uniqueResult();
	}
	
	@Override
	public List<Map<String,Object>> queryListBySql(String sql,List<Object> params){
		List<Map<String,Object>> list=this.queryListSql(sql, null, null, params);
		return list;
	}
	
	@Override
	public Paging<Map<String,Object>> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,List<Object> params){
		int maxPageSize=this.countBySql(countSql, params);
		List<Map<String,Object>> list=this.queryListSql(sql, pageNo, pageSize, params);
		Paging<Map<String,Object>> page = new Paging<Map<String,Object>>();
		page.setPage(pageNo);//页号
		page.setLimit(pageSize);//每页记录数
		page.setSize(this.getMaxPageNo(maxPageSize, pageSize));//总页数
		page.setTotal(maxPageSize);//总记录数
		page.setStart((pageNo-1)*pageSize);//起始记录数
		page.setData(list);
		return page;
	}
	
	@Override
	public int executeSql(String sql,List<Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		return query.executeUpdate();
	}
	
	
	private <T> List<T> queryListSql(String sql,Integer pageNo,Integer pageSize,Class<T> clazz,List<Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<T> list=query.list();
		return list;
	}
	
	private List<Map<String, Object>> queryListSql(String sql,Integer pageNo,Integer pageSize,List<Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				query.setParameter(i, params.get(i));
			}
		}
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<Map<String, Object>> list=query.list();
		return list;
	}
	
	/**
	 * 计算总页数
	 * @param maxPageSize 总记录数
	 * @param pageSize 每页显示记录数
	 * @return
	 */
	private int getMaxPageNo(int maxPageSize,int pageSize){
		int maxPageNo=0;
		if(maxPageSize%pageSize==0){
			maxPageNo=maxPageSize/pageSize;
		}else{
			maxPageNo=maxPageSize/pageSize+1;
		}
		return maxPageNo;
	}
	
	@Override
	public <T> List<T> queryListBySql(String sql,Class<T> clazz,Map<String,Object> params){
		return this.queryListSql(sql, null, null, clazz, params);
	}
	
	@Override
	public List<Map<String,Object>> queryListBySql(String sql,Map<String,Object> params){
		return this.queryListSql(sql, null, null, params);
	}
	
	@Override
	public <T> Paging<T> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Class<T> clazz,Map<String,Object> params){
		int maxPageSize=this.countBySql(countSql, params);
		List<T> list=this.queryListSql(sql, pageNo, pageSize, clazz, params);
		Paging<T> page = new Paging<T>();
		page.setPage(pageNo);//页号
		page.setLimit(pageSize);//每页记录数
		page.setSize(this.getMaxPageNo(maxPageSize, pageSize));//总页数
		page.setTotal(maxPageSize);//总记录数
		page.setStart((pageNo-1)*pageSize);//起始记录数
		page.setData(list);
		return page;
	}
	
	@Override
	public Paging<Map<String,Object>> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Map<String,Object> params){
		int maxPageSize=this.countBySql(countSql, params);
		List<Map<String,Object>> list=this.queryListSql(sql, pageNo, pageSize, params);
		Paging<Map<String,Object>> page = new Paging<Map<String,Object>>();
		page.setPage(pageNo);//页号
		page.setLimit(pageSize);//每页记录数
		page.setSize(this.getMaxPageNo(maxPageSize, pageSize));//总页数
		page.setTotal(maxPageSize);//总记录数
		page.setStart((pageNo-1)*pageSize);//起始记录数
		page.setData(list);
		return page;
	}
	
	@Override
	public int countBySql(String sql,Map<String,Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	@Override
	public Object queryObjectBySql(String sql,Map<String,Object> params) {
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.uniqueResult();
	}
	
	@Override
	public int executeSql(String sql,Map<String,Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}
	
	private <T> List<T> queryListSql(String sql,Integer pageNo,Integer pageSize,Class<T> clazz,Map<String,Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<T> list=query.list();
		return list;
	}
	
	private List<Map<String, Object>> queryListSql(String sql,Integer pageNo,Integer pageSize,Map<String,Object> params){
		Session session = this.factory.getCurrentSession();
		Query query=session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if(params!=null && params.size()>0){
			for(Map.Entry<String, Object> entry : params.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<Map<String, Object>> list=query.list();
		return list;
	}

}
