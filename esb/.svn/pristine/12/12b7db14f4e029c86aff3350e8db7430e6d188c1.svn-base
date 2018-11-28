package cn.edugate.esb.dao.imp;
 
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IOrgTreeDao;
import cn.edugate.esb.entity.OrgTree;
 
/**
 * 机构树DAO实现类
 * Title:IOrgTreeImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日下午1:37:06
 */
@Repository
public class IOrgTreeImpl extends BaseDAOImpl<OrgTree> implements IOrgTreeDao {

	@Override
	public int deleteAllLowerOrg(int orgID) {
		String sql = "UPDATE edugate_base.org_tree ot SET ot.is_del=1, ot.del_time= NOW() WHERE ot.org_id=?;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);

		int result = query.executeUpdate();
		return result;
	}

	@Override
	public List<OrgTree> getAllLowerOrg(int orgID) {
		String sql = "SELECT ot.* FROM edugate_base.org_tree ot WHERE ot.org_id=? AND ot.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(OrgTree.class));
		List<OrgTree> list = query.list();
		return list;
	}

	@Override
	public int saveOrgTree(OrgTree ot) {
		ot.setInsert_time(new Date());
		ot.setUpdate_time(new Date());
		ot.setIs_del(0);
		super.save(ot);
		return ot.getOtree_id().intValue();
	}

	@Override
	public int deleteOrgTree(OrgTree ot) {
		ot.setIs_del(1);
		ot.setUpdate_time(new Date());
		ot.setDel_time(new Date());
		super.update(ot);
		return ot.getOtree_id().intValue();
	}
 
}