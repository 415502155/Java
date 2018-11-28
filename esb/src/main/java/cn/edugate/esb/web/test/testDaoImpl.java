package cn.edugate.esb.web.test;

import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.imp.BaseDAOImpl;
import cn.edugate.esb.entity.OrgUser;

@Repository
public class testDaoImpl extends BaseDAOImpl<OrgUser> implements testDao {

	@Override
	public List<OrgUser> getOrgUserList(Integer orgId) {

		Session session = this.factory.getCurrentSession();
		String sql1 = "SELECT o.user_id a, o.org_id b, o.user_loginname c, o.user_loginpass d, o.user_mobile f FROM edugate_base.org_user o "
				+ "WHERE o.org_id = ? ORDER BY o.user_id DESC LIMIT 1,10";
		String sql = "SELECT o.user_id, o.org_id, o.user_loginname, o.user_loginpass, o.user_mobile FROM edugate_base.org_user o "
				+ "WHERE o.org_id = ? ORDER BY o.user_id DESC LIMIT 1,10";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0,orgId);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> list = query.list();
		return list;
	}
	
	@Override
	public List<Map> getOrgUserList1(Integer orgId) {

		Session session = this.factory.getCurrentSession();
		String sql = "SELECT o.user_id, o.org_id, o.user_loginname, o.user_loginpass, o.user_mobile FROM edugate_base.org_user o "
				+ "WHERE o.org_id = ? ORDER BY o.user_id DESC LIMIT 1,10";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0,orgId);
		
		//
		//query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		//query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}
	
	public List<OrgUser> getOrgUserList2(Integer orgId) {

		Session session = this.factory.getCurrentSession();
		Criteria criteria = session.createCriteria(OrgUser.class);
		session.createCriteria("Map");
		criteria.addOrder(Order.asc("user_id"));
		orgId = null;
		if (orgId != null) {
			criteria.add(Expression.eq("org_id", null));
		}
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		//使查询到的list与数据库字段对应
		//criteria.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		@SuppressWarnings("unchecked")
		List<OrgUser> list = criteria.list();
		
		System.out.println("SQL :" + criteria.toString());
		return list;
	}

	@Override
	public List<Map> getOrgUserList2(Integer orgId, PageInfo page) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT o.user_id, o.org_id, o.user_loginname, o.user_loginpass, o.user_mobile FROM edugate_base.org_user o "
				+ "WHERE o.org_id = ? ORDER BY o.user_id DESC";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0,orgId);
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getEnd());
		//query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		//query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map> list = query.list();
		return list;
	}

}
