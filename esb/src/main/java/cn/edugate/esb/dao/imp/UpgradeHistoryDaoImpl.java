package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.UpgradeHistoryDao;
import cn.edugate.esb.entity.UpgradeHistory;


/**
 * 
 * @author sunwei
 * @date 2018年7月24日下午2:38:48
 */
@Repository
public class UpgradeHistoryDaoImpl extends BaseDAOImpl<UpgradeHistory> implements UpgradeHistoryDao {

	@Override
	public UpgradeHistory getUpgradeHistory(int org_id, int year) {
		String sql = "SELECT uh.* FROM upgrade_history uh WHERE uh.org_id=? AND uh.`year`=?;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setInteger(1, year);
		query.setResultTransformer(Transformers.aliasToBean(UpgradeHistory.class));

		UpgradeHistory result = null;
		List<UpgradeHistory> list = query.list();
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

}
