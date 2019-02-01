package sng.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sharding.entity.Notice;
import sng.dao.NoticeDao;



@Repository
public class NoticeDaoImpl extends ShardingDBBaseDaoImpl<Notice> implements NoticeDao {

	static Logger logger = LoggerFactory.getLogger(NoticeDaoImpl.class);

	@Autowired
	//@Resource(name = "sessionFactory")
	@Resource(name = "sessionFactory4Sharding")
	public SessionFactory factory;

	@Override
	public Notice getNoticeRecord(String org_id, String recordId) {
		String sql = "SELECT n.* FROM sng_notice n WHERE n.org_id=? AND n.id=?  AND n.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, Integer.valueOf(org_id));
		query.setBigInteger(1, new BigInteger(recordId));
		query.setResultTransformer(Transformers.aliasToBean(Notice.class));
		
		List<Notice> list = query.list();
		Notice result = null;
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		
		return result;
	}
	
	@Override
	public BigInteger getTotalNumOfStudentNoticeSendHistory(String org_id, String userId, String beginDate, String endDate,
			String keyWord) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(n.id) AS totalNum FROM sng_notice n WHERE n.org_id=:orgId ");
		if (StringUtils.isNotBlank(userId)) {
			sql.append(" AND n.sender_id=:senderId ");
		}
		sql.append(" AND n.is_del=0 ");
		if (StringUtils.isNotBlank(beginDate)) {
			sql.append(" AND n.insert_time >= STR_TO_DATE(:beginDate,'%Y-%m-%d %H:%i:%s') ");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" AND n.insert_time <= STR_TO_DATE(:endDate,'%Y-%m-%d %H:%i:%s') ");
		}
		if (StringUtils.isNotBlank(keyWord)) {
			sql.append(" AND (n.sender_name LIKE :senderName OR n.content LIKE :content )");
		}

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString()).addScalar("totalNum", StandardBasicTypes.BIG_INTEGER);
		query.setInteger("orgId", Integer.valueOf(org_id));
		if (StringUtils.isNotBlank(userId)) {
			query.setInteger("senderId", Integer.valueOf("userId"));
		}
		if (StringUtils.isNotBlank(beginDate)) {
			beginDate = beginDate + " 00:00:00";
			query.setString("beginDate", beginDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			endDate = endDate + " 23:59:59";
			query.setString("endDate", endDate);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			query.setString("senderName", "%"+keyWord+"%");
			query.setString("content", "%"+keyWord+"%");
		}

		BigInteger bigInteger = (BigInteger) query.uniqueResult();

		return bigInteger;
	}
	

	@Override
	public List<Notice> getNoticeList4Paging(String org_id, String userId, String beginDate, String endDate, String keyWord,
			int currentPageNum, int pageSize) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT n.* FROM sng_notice n WHERE n.org_id=:orgId ");
		//  AND n.sender_id=? AND n.is_del=0 AND n.insert_time >= STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') AND n.insert_time <= STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s');
		if (StringUtils.isNotBlank(userId)) {
			sql.append(" AND n.sender_id=:senderId ");
		}
		sql.append(" AND n.is_del=0 ");
		if (StringUtils.isNotBlank(beginDate)) {
			sql.append(" AND n.insert_time >= STR_TO_DATE(:beginDate,'%Y-%m-%d %H:%i:%s') ");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" AND n.insert_time <= STR_TO_DATE(:endDate,'%Y-%m-%d %H:%i:%s') ");
		}
		if (StringUtils.isNotBlank(keyWord)) {
			sql.append(" AND (n.sender_name LIKE :senderName OR n.content LIKE :content )");
		}
		
		sql.append(" ORDER BY n.insert_time DESC ");
		sql.append(" LIMIT :offset, :len ");
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("orgId", Integer.valueOf(org_id));
		if (StringUtils.isNotBlank(userId)) {
			query.setInteger("senderId", Integer.valueOf("userId"));
		}
		if (StringUtils.isNotBlank(beginDate)) {
			beginDate = beginDate+" 00:00:00";
			query.setString("beginDate", beginDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			endDate = endDate + " 23:59:59";
			query.setString("endDate", endDate);
		}
		if (StringUtils.isNotBlank(keyWord)) {
			query.setString("senderName", "%"+keyWord+"%");
			query.setString("content", "%"+keyWord+"%");
		}
		query.setInteger("offset", (currentPageNum - 1) * pageSize);
		query.setInteger("len", pageSize);
		
		query.setResultTransformer(Transformers.aliasToBean(Notice.class));
		
		List<Notice> list = query.list();
		
		return list;
	}

	@Override
	public int updateNoticeStatus(String org_id, String recordId, int status) {
		String sql = "UPDATE sng_notice n SET n.`status`=?, n.update_time=NOW()  WHERE n.org_id=? AND n.id=? AND n.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, status);
		query.setInteger(1, Integer.valueOf(org_id));
		query.setBigInteger(2, new BigInteger(recordId));
		return query.executeUpdate();
	}

	@Override
	public Notice getNoticeRecord(String org_id, String recordId, String type) {
		String sql = "SELECT n.* FROM sng_notice n WHERE  n.org_id=? AND n.id=? AND n.type=? AND n.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, Integer.valueOf(org_id));
		query.setBigInteger(1, new BigInteger(recordId));
		query.setInteger(2, Integer.valueOf(type));
		query.setResultTransformer(Transformers.aliasToBean(Notice.class));
		
		List<Notice> list = query.list();
		Notice result = null;
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		
		return result;
	}

	@Override
	public int updateNoticetotalNum(String org_id, String recordId, int totalNum) {
		String sql = "UPDATE sng_notice n SET n.total_num=?, n.update_time=NOW()  WHERE n.org_id=? AND n.id=? AND n.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, totalNum);
		query.setInteger(1, Integer.valueOf(org_id));
		query.setBigInteger(2, new BigInteger(recordId));
		return query.executeUpdate();
	}

	@Override
	public List<Notice> getReceivingNoticeList(String org_id, String user_id, String recordId, String direction,
			String length, String type) {
		
		List<Notice> list = null;
		Session session = this.factory.getCurrentSession();
		
		if (StringUtils.isNoneBlank(recordId, direction)) {
			// 存在数据的情况，下拉刷新或上划取之前的记录 direction(0:刷新；1：上划)
			String symbol = "";
			if ("1".equals(direction)) {
				symbol = "<=";
			} else {
				symbol = ">=";
			}
			
			String sql = "SELECT n.* FROM sng_notice n INNER JOIN sng_notice_detail nd "
					//+ "WHERE nd.org_id=? AND nd.user_id=? AND nd.type=? AND nd.`status`=1 AND nd.is_del=0 AND n.org_id=? AND n.id=nd.notice_id AND n.type=nd.type "
					+ "WHERE nd.org_id=? AND nd.user_id=? AND nd.type=? AND nd.is_del=0 AND n.org_id=? AND n.id=nd.notice_id AND n.type=nd.type "
					+ "AND n.insert_time"+symbol+"(SELECT t.insert_time FROM sng_notice t WHERE t.org_id=? AND t.id=? AND t.is_del=0) AND n.id != ? AND n.is_del=0 ORDER BY n.insert_time DESC LIMIT 0, ?;";
			
			Query query = session.createSQLQuery(sql);
			query.setInteger(0, Integer.valueOf(org_id));
			query.setInteger(1, Integer.valueOf(user_id));
			query.setInteger(2, Integer.valueOf(type));
			query.setInteger(3, Integer.valueOf(org_id));
			query.setInteger(4, Integer.valueOf(org_id));
			query.setBigInteger(5, new BigInteger(recordId));
			query.setBigInteger(6, new BigInteger(recordId));
			query.setInteger(7, Integer.valueOf(length));
			query.setResultTransformer(Transformers.aliasToBean(Notice.class));
			
			list = query.list();
		} else {
			// 首次进入或进入后无数据的情况下，进行下拉刷新
			String sql = "SELECT n.* FROM sng_notice n INNER JOIN sng_notice_detail nd WHERE "
					//+ "nd.org_id=? AND nd.user_id=? AND nd.type=? AND nd.`status`=1 AND nd.is_del=0 AND n.org_id=? AND n.id=nd.notice_id AND n.type=nd.type AND n.is_del=0 "
					+ "nd.org_id=? AND nd.user_id=? AND nd.type=? AND nd.is_del=0 AND n.org_id=? AND n.id=nd.notice_id AND n.type=nd.type AND n.is_del=0 "
					+ " ORDER BY n.insert_time DESC LIMIT 0, ?;";
			
			Query query = session.createSQLQuery(sql);
			query.setInteger(0, Integer.valueOf(org_id));
			query.setInteger(1, Integer.valueOf(user_id));
			query.setInteger(2, Integer.valueOf(type));
			query.setInteger(3, Integer.valueOf(org_id));
			query.setInteger(4, Integer.valueOf(length));
			query.setResultTransformer(Transformers.aliasToBean(Notice.class));
			
			list = query.list();
		}
		
		return list;
	}

}
