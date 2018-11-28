package cn.edugate.esb.dao.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.dao.IAdminDao;
import cn.edugate.esb.entity.Admin;

@Repository
public class IAdminImpl extends BaseDAOImpl<Admin> implements IAdminDao {

	@Override
	public Admin getUserByName(String userName) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql="SELECT u FROM Admin u WHERE u.username=:userName";
		Query query = session.createQuery(hql);
		query.setString("userName", userName);
		@SuppressWarnings("unchecked")
		List<Admin> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}


}
