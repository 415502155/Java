package sng.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import sng.util.Paging;


public interface BaseDao<T> {

	@Transactional(readOnly = true)
	public void queryData(Class<T> clazz, Paging<T> paging, Criterion criterion, Order... orders);

	@Transactional(readOnly = true)
	public List<T> list(Class<T> clazz, Criterion criterion, Order... orders);

	@Transactional(readOnly = true)
	public void queryData(Class<T> clazz, Paging<T> paging, Cols columns, Criterion criterion, Order... orders);

	@Transactional(readOnly = true)
	public void queryData(Class<T> clazz, String associationPath, JoinType joinType, Paging<T> paging, Cols columns,
			Criterion criterion, Order... orders);

	@Transactional(readOnly = true)
	public List<T> list(Class<T> clazz, Cols columns, Criterion criterion, Order... orders);

	@Transactional(readOnly = true)
	public List<T> list(Class<T> clazz, String associationPath, JoinType joinType, Cols columns, Criterion criterion,
			Order... orders);

	@Transactional
	public void deleteById(Class<T> clazz, Serializable... ids);

	@SuppressWarnings("unchecked")
	@Transactional
	public void delete(T... ts);

	@Transactional
	public int delete(Class<T> clazz, Criterion criterion);

	@SuppressWarnings("unchecked")
	@Transactional
	public void saveOrUpdate(T... ts);

	@SuppressWarnings("unchecked")
	@Transactional
	public void save(T... ts);

	@SuppressWarnings("unchecked")
	@Transactional
	public void update(T... ts);

	@Transactional(readOnly = true)
	public T get(Class<T> clazz, Serializable id);

	/**
	 * 获取表名
	 * 
	 * @param clazz
	 * @return
	 */
	public String tn(Class<?> clazz);

	/**
	 * 数据库实例常量
	 * 
	 * @author Jichen 2016年5月11日 下午1:59:04
	 * 
	 */
	public static class DATABASE_SCHEMA {

		public static final String BASE = "jinnan_base";

		/**
		 * 获取数据库名(用于拼接sql语句)
		 * 
		 * @author Jichen 2016年5月11日 下午1:59:21
		 * @param schema
		 * @return
		 */
		public static String getSchema(String schema) {
			if (StringUtils.hasLength(schema)) {
				return "`" + schema + "`" + ".";
			} else {
				return "";
			}
		}
	}
	
	
	
	<T> Serializable saveOne(T t);

	<T> void updateOne(T t);

	<T> T findById(Class<T> clazz, Serializable id);

	<T> Paging<T> queryPageBySql(String sql, String countSql, int pageNo, int pageSize, Class<T> clazz,
			List<Object> params);

	<T> List<T> queryListBySql(String sql, Class<T> clazz, List<Object> params);

	int countBySql(String sql, List<Object> params);

	List<Map<String, Object>> queryListBySql(String sql, List<Object> params);

	Paging<Map<String, Object>> queryPageBySql(String sql, String countSql, int pageNo, int pageSize,
			List<Object> params);
	
	List queryListByHql(String hql,List<Object> params);
	
	List queryListByHql(String hql,Map<String,Object> params);
	
	int executeSql(String sql,List<Object> params);
	
	Object queryObjectBySql(String sql,List<Object> params);
	
	<T> List<T> queryListBySql(String sql,Class<T> clazz,Map<String,Object> params);
	
	List<Map<String,Object>> queryListBySql(String sql,Map<String,Object> params);
	
	int countBySql(String sql,Map<String,Object> params);
	
	<T> Paging<T> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Class<T> clazz,Map<String,Object> params);
	
	Paging<Map<String,Object>> queryPageBySql(String sql,String countSql,int pageNo,int pageSize,Map<String,Object> params);
	
	Object queryObjectBySql(String sql,Map<String,Object> params);
	
	int executeSql(String sql,Map<String,Object> params);

}
