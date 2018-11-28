package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.dao.IAppDao;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.util.Paging;

@Repository
public class IAppImpl extends BaseDAOImpl<App> implements IAppDao {

	@Override
	public Long getTotalCount() {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT count(*) FROM app";
		Query query = session.createSQLQuery(sql);		
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	@Override
	public void getAppWithPaging(Paging<App> pages) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT * FROM app";		
		Query query = session.createSQLQuery(sql);
		
		query.setFirstResult((pages.getPage()-1)*pages.getLimit()+pages.getStart());
		query.setMaxResults(pages.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(App.class));
		@SuppressWarnings("unchecked")
		List<App> ls = query.list();
//		for (App orgUser : ls) {
//			LSHelper.processInjection(orgUser);
//		}
		pages.setData(ls);
	}

}
