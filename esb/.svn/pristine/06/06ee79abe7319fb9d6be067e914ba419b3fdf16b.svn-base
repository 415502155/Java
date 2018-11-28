package cn.edugate.esb.dao.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.dao.IActCodeDao;
import cn.edugate.esb.entity.ActCode;

@Repository
public class IActCodeImpl extends BaseDAOImpl<ActCode> implements IActCodeDao {

	@Override
	public List<ActCode> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(ActCode parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(ActCode parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExit(String mobile, String code) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
//      String hql = "select count(*) from ActCode where user_id = :user_id and UNIX_TIMESTAMP(inserttime)+expire>UNIX_TIMESTAMP(now())";
		String hql = "select count(*) from ActCode where mobile = :mobile and code = :code and UNIX_TIMESTAMP(inserttime)+expire>UNIX_TIMESTAMP(now())";
      Query query = session.createQuery(hql);
      query.setString("mobile", mobile);
      query.setString("code", code);
      Long count  = (Long)query.uniqueResult();		
		return count>0;
	}

	@Override
	public ActCode getValidCodeByMobile(String mobile) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql = "from ActCode where mobile = :mobile and UNIX_TIMESTAMP(inserttime)+expire>UNIX_TIMESTAMP(now())";
	    Query query = session.createQuery(hql);
	    query.setString("mobile", mobile);
	    @SuppressWarnings("unchecked")
		List<ActCode> ls = query.list();
	    ActCode item = null;
	    if(ls.size()>0){
	    	item = ls.get(0);
	    }
		return item;
	}



}
