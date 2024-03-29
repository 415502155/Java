package sng.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.LoggerDao;
import sng.entity.OperationLog;
import sng.util.Paging;

/***
 * 
 * @Company sjwy
 * @Title: LoggerDaoImpl.java
 * @Description: 日志管理
 * @author: shy
 * @date: 2018年10月23日 下午1:36:24
 */
@Repository
public class LoggerDaoImpl extends BaseDaoImpl<OperationLog> implements LoggerDao {
	/***
	 * 查询当前机构下的操作日志信息
	 */
	/***
	 * SELECT operation_id, target_name, ACTION, content, operation_time FROM
	 * operation_log WHERE is_del = 0 AND ACTION = 1 AND operation_time >=
	 * '2018-10-24 07:25:01' AND operation_time <= '2018-10-26 17:45:01' AND
	 * LOCATE('a',content )>0 LIMIT 0,2 #BETWEEN '2018-10-24 07:25:01' AND
	 * '2018-10-26 17:45:01' AND LOCATE('a',content )>0 LIMIT 0,2;
	 */

	@Override
	public Long getListCount(Integer org_id) {
		// TODO Auto-generated method stub
		return null;
	}
	/***
	 * isAll == 1
	 * 
	 */
	@Override
	public Paging<OperationLog> getListWithPaging(Integer orgId, Integer action, String startTime, String endTime,
			String content, Paging<OperationLog> page, Integer isAll) {
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(
				" SELECT operation_id, target_name, action, content, user_type_name, operation_time FROM operation_log ");
		StringBuffer where = new StringBuffer(" WHERE is_del = 0 and org_id =:org_id ");

		if (null != action) {
			where.append(" AND action=:action ");
		}
		if (StringUtils.isNotEmpty(startTime)) {
			where.append(" AND operation_time>=:start_time ");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			where.append(" AND operation_time<=:end_time ");
		}
		if (StringUtils.isNotEmpty(content)) {
			where.append(" AND LOCATE(:content , content )>0 ");
		}
		String order = " ORDER BY operation_time DESC ";
		String limit = "";//分页
		if (null == isAll || isAll != 1) {
			int limitFrom = (page.getPage() - 1) * page.getLimit();
			limit = " limit " + limitFrom + "," + page.getLimit();
		} else {
		}
		Query query = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		query.setInteger("org_id", orgId);
		if (null != action) {
			query.setInteger("action", action);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			query.setString("start_time", startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			query.setString("end_time", endTime);
		}
		if (StringUtils.isNotEmpty(content)) {
			query.setString("content", content);
		}
		query.setResultTransformer(Transformers.aliasToBean(OperationLog.class));
		@SuppressWarnings("unchecked")
		List<OperationLog> list = query.list();
		page.setData(list);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Paging<Map> getListMapWithPaging(Integer orgId, Integer action, String startTime, String endTime,
			String content, Paging<Map> page) {
		Session session = this.factory.getCurrentSession();
		StringBuffer countTotal = new StringBuffer(" SELECT count(1) FROM operation_log ");
		StringBuffer sql = new StringBuffer(
				" SELECT * FROM operation_log ");
		StringBuffer where = new StringBuffer(" WHERE is_del = 0 and org_id=:org_id ");
		if (null != action) {
			where.append(" AND action=:action ");
		}
		if (StringUtils.isNotEmpty(startTime)) {
			where.append(" AND operation_time>=:start_time ");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			where.append(" AND operation_time<=:end_time ");
		}
		if (StringUtils.isNotEmpty(content)) {
			where.append(" AND content like :content ");
		}
		String order = " ORDER BY operation_time DESC ";
		int limitFrom = (page.getPage() - 1) * page.getLimit();
		String limit = " limit " + limitFrom + "," + page.getLimit();
		Query query = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		Query countQuery = session.createSQLQuery(countTotal.append(where).toString());//countTotal.append(sql.append(" ) o")).toString()
		query.setInteger("org_id", orgId);
		countQuery.setInteger("org_id", orgId);
		if (null != action) {
			query.setInteger("action", action);
			countQuery.setInteger("action", action);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			query.setString("start_time", startTime);
			countQuery.setString("start_time", startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			query.setString("end_time", endTime);
			countQuery.setString("end_time", endTime);
		}
		if (StringUtils.isNotEmpty(content)) {
			query.setString("content", "%"+content+"%");
			countQuery.setString("content", "%"+content+"%");
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		page.setTotal(pageTotal.intValue());
		page.setSize(page.getTotal()%page.getLimit()==0 ? (page.getTotal()/page.getLimit()) : (page.getTotal()/page.getLimit()+1));
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		page.setData(list);
		return page;
	}
	/***
	 * isAll != 1
	 */
	@Override
	public OperationLog getById(Integer id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT operation_id, target_name, action, content, user_type_name, operation_time FROM operation_log where operation_id =:operation_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("operation_id", id);
		query.setResultTransformer(Transformers.aliasToBean(OperationLog.class));
		OperationLog log = (OperationLog) query.uniqueResult();
		return log;
	}

	@Override
	public List<OperationLog> getList(Integer orgId, Integer action, String startTime, String endTime, String content,
			Paging<OperationLog> page, Integer isAll) {
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(
				" SELECT operation_id, target_name, action, content, user_type_name, operation_time FROM operation_log ");
		StringBuffer where = new StringBuffer(" WHERE is_del = 0 and org_id =:org_id ");

		if (null != action) {
			where.append(" AND action=:action ");
		}
		if (StringUtils.isNotEmpty(startTime)) {
			where.append(" AND operation_time>=:start_time ");
		}
		if (StringUtils.isNotEmpty(endTime)) {
			where.append(" AND operation_time<=:end_time ");
		}
		if (StringUtils.isNotEmpty(content)) {
			where.append(" AND LOCATE(:content , content )>0 ");
		}
		String order = " ORDER BY operation_time DESC ";
		String limit = "";//分页
		if (null != isAll && isAll == 1) {
			
		} else {
			int limitFrom = (page.getPage() - 1) * page.getLimit();
			limit = " limit " + limitFrom + "," + page.getLimit();
		}
		Query query = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		query.setInteger("org_id", orgId);
		if (null != action) {
			query.setInteger("action", action);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			query.setString("start_time", startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			query.setString("end_time", endTime);
		}
		if (StringUtils.isNotEmpty(content)) {
			query.setString("content", content);
		}
		query.setResultTransformer(Transformers.aliasToBean(OperationLog.class));
		@SuppressWarnings("unchecked")
		List<OperationLog> list = query.list();
		return list;
	}
}
