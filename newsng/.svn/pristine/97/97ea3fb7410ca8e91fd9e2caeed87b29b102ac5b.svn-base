package sng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.Org_WXDao;
import sng.entity.OrgWXEntity;

@Repository
public class Org_WXDaoImpl extends BaseDaoImpl<OrgWXEntity> implements Org_WXDao {

	@Override
	public List<OrgWXEntity> getAllRecord() {
		Session session = this.factory.getCurrentSession();
		String sql = "select * from org_wx_tab";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(OrgWXEntity.class));
		List<OrgWXEntity> list = query.list();
		return list;
	}

}
