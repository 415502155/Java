package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IUserAccountDao;
import cn.edugate.esb.entity.UserAccount;

@Repository
public class IUserAccountImpl extends BaseDAOImpl<UserAccount> implements IUserAccountDao {

	@Override
	public UserAccount getUcUserAccount(Integer uc_id, Integer version) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql="SELECT ua FROM UserAccount ua WHERE ua.uc_id=:uc_id and ua.version=:version";
		Query query = session.createQuery(hql);
		query.setInteger("uc_id", uc_id);
		query.setInteger("version", version);
		@SuppressWarnings("unchecked")
		List<UserAccount> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public UserAccount getOrgUserAccount(Integer user_id, Integer version) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql="SELECT ua FROM UserAccount ua WHERE ua.user_id=:user_id and ua.version=:version";
		Query query = session.createQuery(hql);
		query.setInteger("user_id", user_id);
		query.setInteger("version", version);
		@SuppressWarnings("unchecked")
		List<UserAccount> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public UserAccount getUserAccount(String targetid, Integer version,
			int type) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql="SELECT ua FROM UserAccount ua WHERE ua.target_id=:targetid and ua.version=:version and ua.type=:type";
		Query query = session.createQuery(hql);
		query.setString("targetid", targetid);
		query.setInteger("version", version);
		query.setInteger("type", type);
		@SuppressWarnings("unchecked")
		List<UserAccount> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}



}
