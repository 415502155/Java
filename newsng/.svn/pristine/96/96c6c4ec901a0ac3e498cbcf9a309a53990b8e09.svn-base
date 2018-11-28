package sng.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.BaseSetTermManageDao;
import sng.entity.Term;
import sng.pojo.ParamObj;
import sng.util.Paging;

/**
 * @desc 基础设置模块-学期管理dao实现
 * @author magq
 * @data 2018-10-29
 * @version 1.0
 */
@Repository
public class BaseSetTermManageDaoImpl extends BaseDaoImpl<Term> implements BaseSetTermManageDao {

	/**
	 * 查询学期信息列表
	 */
	@Override
	public Paging queryTermListInfo(ParamObj paramObj) {
		Paging paging = new Paging();
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append(
				"select t.term_id,t.org_id,t.term_name,t.cur_year,t.term_type,DATE_FORMAT(t.start_time,'%Y-%m-%d') as start_time from term t where t.is_del=0");
		if (paramObj != null) {
			if (paramObj.getOrg_id() != null) {
				sql.append(" and t.org_id=?");
				paramList.add(paramObj.getOrg_id());
			}

			if (StringUtils.isNotBlank(paramObj.getTerm_name())) {
				if (paramObj.isBlurQuery()) {
					sql.append(" and t.term_name like ?");
					paramList.add("%" + paramObj.getTerm_name() + "%");
				} else {
					sql.append(" and t.term_name like ?");
					paramList.add(paramObj.getTerm_name());
				}

			}
			if (paramObj.getTerm_type() != null) {
				sql.append(" and t.term_type =?");
				paramList.add(paramObj.getTerm_type());
			}
			if (StringUtils.isNotBlank(paramObj.getCur_year())) {
				sql.append(" and t.cur_year=?");
				paramList.add(paramObj.getCur_year());
			}

			if (StringUtils.isNotBlank(paramObj.getOrderContent())) {
				sql.append(" order by ?");
				paramList.add(paramObj.getOrderContent());
				if (StringUtils.isNotBlank(paramObj.getOrderType())) {
					sql.append(" ?");
					paramList.add(paramObj.getOrderType());
				}
			} else {
				sql.append(" order by t.cur_year,t.start_time desc,t.term_name asc");
			}

		}

		Session session = this.factory.getCurrentSession();

		int count = 0;
		if (paramObj.isNeedCount()) {
			countSql.append("select count(*) from (" + sql.toString() + ") T");
			Query queryCount = session.createSQLQuery(countSql.toString());
			for (int i = 0; i < paramList.size(); i++) {
				queryCount.setParameter(i, paramList.get(i));
			}
			count = Integer.parseInt(queryCount.uniqueResult().toString());
			sql.append(" limit ?,?");
			paramList.add(paramObj.getLimit() * (paramObj.getPage() - 1));
			paramList.add(paramObj.getLimit());

		}

		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Object[]> list = query.list();
		paging.setPage(paramObj.getPage());
		paging.setLimit(paramObj.getLimit());
		paging.setSize(
				count % paramObj.getLimit() == 0 ? (count / paramObj.getLimit()) : (count / paramObj.getLimit() + 1));
		paging.setTotal(count);
		paging.setData(list);
		paging.setSuccess(true);
		return paging;
	}

	/**
	 * 删除学期列表
	 */
	@Override
	public void deleteTermByTerm(ParamObj paramObj) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<>();
		sql.append("update term set  is_del=1,update_time=? where term_id=?");
		paramList.add(new Date());
		paramList.add(paramObj.getTerm_id());

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.executeUpdate();
	}

	/*
	 * 过滤添加学期名称是否有同名字子(non-Javadoc)
	 * 
	 * @see sng.dao.BaseSetTermManageDao#filterTermName(sng.pojo.ParamObj)
	 */
	@Override
	public int filterTermName(ParamObj paramObj) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<>();
		sql.append(" select count(0) from term where is_del=0");
		if (paramObj.getOrg_id() != null) {
			sql.append(" and org_id=?");
			paramList.add(paramObj.getOrg_id());
		}
		if (StringUtils.isNotBlank(paramObj.getTerm_name())) {
			sql.append(" and term_name=?");
			paramList.add(paramObj.getTerm_name());
		}

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		int count = 0;
		count = Integer.parseInt(query.uniqueResult().toString());
		return count;
	}

	/*
	 * 更具当前学期ID是否能删除或者编辑学期(non-Javadoc)
	 * 
	 * @see sng.dao.BaseSetTermManageDao#isDelOrUpdateTerminfo(sng.pojo.ParamObj)
	 */
	@Override
	public int isDelOrUpdateTerminfo(ParamObj paramObj) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<>();
		sql.append("select count(0) from term t ");
		sql.append(" left join classes  c on t.term_id=c.term_id and t.org_id=t.org_id");
		sql.append(" where t.is_del=0 and c.is_del=0 and c.term_id is not null");
		if (paramObj.getTerm_id() != null) {
			sql.append(" and t.term_id=?");
			paramList.add(paramObj.getTerm_id());

		}
		if (paramObj.getOrg_id() != null) {
			sql.append(" and t.org_id=?");
			paramList.add(paramObj.getOrg_id());
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		int count = 0;
		count = Integer.parseInt(query.uniqueResult().toString());
		return count;
	}

}
