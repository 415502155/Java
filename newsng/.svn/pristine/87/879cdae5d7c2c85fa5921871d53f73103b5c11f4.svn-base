package sng.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import sng.dao.UserRegisterDao;
import sng.entity.UserRegister;

@Repository
public class UserRegisterDaoImpl extends BaseDaoImpl<UserRegister> implements UserRegisterDao {

	@Override
	public int getUserByOpenid(String org_id, String openid) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(ur.user_register_id) as totalNum FROM user_register ur WHERE ur.org_id=? AND ur.open_id=? AND ur.is_del=0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addScalar("totalNum", StandardBasicTypes.BIG_INTEGER);;
		query.setString(0, org_id);
		query.setString(1, openid);
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.intValue();
	}

	

}
