package sng.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sharding.entity.NoticeDetail;
import sng.dao.NoticeDetailDao;



@Repository
public class NoticeDetailDaoImpl extends ShardingDBBaseDaoImpl<NoticeDetail> implements NoticeDetailDao {

	static Logger logger = LoggerFactory.getLogger(NoticeDetailDaoImpl.class);

	@Autowired
	//@Resource(name = "sessionFactory")
	@Resource(name = "sessionFactory4Sharding")
	public SessionFactory factory;

	@Override
	public NoticeDetail getNoticeDetail(String org_id, String user_id, String id) {
		String sql = "SELECT nd.* FROM sng_notice_detail nd WHERE nd.org_id=? AND nd.user_id=? AND nd.id=? AND nd.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, Integer.valueOf(org_id));
		query.setInteger(1, Integer.valueOf(user_id));
		query.setBigInteger(2, new BigInteger(id));
		query.setResultTransformer(Transformers.aliasToBean(NoticeDetail.class));
		NoticeDetail detail = null;
		List<NoticeDetail> detailList = query.list();
		if (detailList != null && detailList.size() > 0) {
			detail = detailList.get(0);
		}
		
		return detail;
	}

	@Override
	public int updateNoticeDetailStatus(String org_id, String user_id, String id, int status) {
		String sql = "UPDATE sng_notice_detail nd SET nd.`status`=? , nd.update_time= NOW() WHERE nd.org_id=? AND nd.user_id=? AND nd.id=? AND nd.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, status);
		query.setInteger(1, Integer.valueOf(org_id));
		query.setInteger(2, Integer.valueOf(user_id));
		query.setBigInteger(3, new BigInteger(id));
		return query.executeUpdate();
	}

}
