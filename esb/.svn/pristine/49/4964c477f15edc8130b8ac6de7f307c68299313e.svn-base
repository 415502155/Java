package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.FeedbackDao;
import cn.edugate.esb.entity.Feedback;
import cn.edugate.esb.util.Paging;


/**
 * Title: FeedbackDaoImpl
 * Description:家长意见反馈 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月3日下午4:09:38
 */
@Repository
public class FeedbackDaoImpl extends BaseDAOImpl<Feedback> implements FeedbackDao{

	@Override
	public Paging<Feedback> getList(Feedback feedback, Paging<Feedback> page) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT f.feed_id, f.org_id, f.content, f.mobile, o.org_name_cn AS org_name, u.user_loginname AS login_name, f.identity, f.name, f.`status`, f.insert_time FROM shijiwxy.feedback f INNER JOIN edugate_base.organization o ON f.org_id = o.org_id INNER JOIN edugate_base.org_user u ON u.user_id=f.user_id AND u.org_id=f.org_id AND u.org_id=o.org_id AND u.identity=f.identity WHERE f.is_del = 0 ";
		String countSql = "SELECT count(1) FROM shijiwxy.feedback f INNER JOIN edugate_base.organization o ON f.org_id = o.org_id INNER JOIN edugate_base.org_user u ON u.user_id=f.user_id AND u.org_id=f.org_id AND u.org_id=o.org_id AND u.identity=f.identity WHERE f.is_del = 0 ";
		if(StringUtils.isNotBlank(feedback.getOrg_name())){
			sql += " AND f.org_name LIKE :org_name ";
			countSql += " AND f.org_name LIKE :org_name ";
		}
		if(null!=feedback.getOrg_id()){
			sql += " AND f.org_id = :org_id ";
			countSql += " AND f.org_id = :org_id ";
		}
		sql += " ORDER BY f.feed_id DESC LIMIT :pagenum,:pagesize ";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(Feedback.class));
		if(StringUtils.isNotBlank(feedback.getOrg_name())){
			query.setString("org_name", "%"+feedback.getOrg_name()+"%");
			countQuery.setString("org_name", "%"+feedback.getOrg_name()+"%");
		}
		if(null!=feedback.getOrg_id()){
			query.setInteger("org_id",feedback.getOrg_id());
			countQuery.setInteger("org_id",feedback.getOrg_id());
		}
		query.setInteger("pagenum",  (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		@SuppressWarnings("unchecked")
		List<Feedback> list = query.list();
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		page.setTotal(pageTotal.intValue());
		page.setPage((page.getTotal()%page.getLimit()==0 ? (int)(page.getTotal()/page.getLimit()) : (int)(page.getTotal()/page.getLimit()+1)));
		page.setData(list);
		page.setSuccess(true);
		return page;
	}

	@Override
	public void readed(String ids, Integer status) {
		Session session = this.factory.getCurrentSession();
		String sql = " UPDATE shijiwxy.feedback s SET s.`status` = :status WHERE s.feed_id IN ( :ids )";
		Query query = session.createSQLQuery(sql);
		query.setInteger("status", status);
		query.setParameterList("ids", ids.split(","));
		query.executeUpdate();
	}

}
