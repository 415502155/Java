package sng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.DomainOrgDao;
import sng.entity.DomainOrg;
import sng.pojo.base.Organization;

@Repository
public class DomainOrgDaoImpl extends BaseDaoImpl<DomainOrg> implements DomainOrgDao{

	@Override
	public DomainOrg getDomainOrgRecord(String domainName) {
		String sql = "SELECT t.* FROM domain_org t WHERE t.domain_name=?";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, domainName);
		query.setResultTransformer(Transformers.aliasToBean(DomainOrg.class));

		List<DomainOrg> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Organization getOrgInfo4Web(String domainName) {
		String sql = "SELECT o.* FROM domain_org t, edugate_base.organization o WHERE t.domain_name=? AND o.org_id=t.org_id AND o.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, domainName);
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));

		List<Organization> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
