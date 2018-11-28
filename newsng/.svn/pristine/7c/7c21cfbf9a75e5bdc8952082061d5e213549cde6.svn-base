package sng.dao;

import java.io.Serializable;
import java.util.List;

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

}
