package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IParentDao;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.StudentParent;

@Repository
public class IParentImpl extends BaseDAOImpl<Parent> implements IParentDao {

	@Override
	public List<Parent> getByMobile(String mobile, Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT p.* FROM parent p INNER JOIN org_user ou ON "
				+ "p.user_id=ou.user_id WHERE ou.user_mobile=:mobile AND ou.org_id=:org_id AND p.is_del=0 AND ou.is_del=0";
		Query query = session.createSQLQuery(sql.toString());
		query.setString("mobile", mobile);
		query.setInteger("org_id", org_id);
	    query.setResultTransformer(Transformers.aliasToBean(Parent.class));
	    @SuppressWarnings("unchecked")
		List<Parent> ls =query.list();
		return ls;
	}

	@Override
	public List<Parent> getParents(Integer stud_id,Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "p.*, sp.relation,"
				+ "ou.user_mobile mobile,"
				+ "ou.user_mobile_type mobile_type,"
				+ "sp.stud2parent_id "
				+ "FROM"
				+ "	student2parent sp "
				+ "LEFT JOIN student s ON sp.stud_id = s.stud_id "
				+ "LEFT JOIN parent p ON sp.parent_id = p.parent_id "
				+ "LEFT JOIN org_user ou ON p.user_id = ou.user_id "
				+ "WHERE "
				+ "sp.is_del = 0 "
				+ "AND s.is_del = 0 "
				+ "AND p.is_del = 0 "
				+ "AND ou.is_del = 0 "
				+ "AND s.stud_id = :stud_id "
				+ "AND p.org_id = :org_id";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("stud_id", stud_id);
		query.setInteger("org_id", org_id);
	    query.setResultTransformer(Transformers.aliasToBean(Parent.class));
	    @SuppressWarnings("unchecked")
		List<Parent> ls =query.list();
		return ls;
	}

	@Override
	public int getCountStudentParent(int stud_id, int parent_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT count(1) FROM student2parent cs WHERE cs.stud_id=:stud_id and cs.parent_id=:parent_id and cs.is_del=0 ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("stud_id", stud_id);
		query.setInteger("parent_id", parent_id);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public int checkParentPhone(String phone, String orgId, String parent_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT COUNT(ou.user_id) FROM org_user ou INNER JOIN parent p ON ou.user_id=p.user_id WHERE ou.identity = 0 AND ou.org_id = :org_id AND ou.user_mobile = :phone AND ou.is_del=0 AND p.is_del=0 AND p.parent_id!=:parent_id";
		Query query = session.createSQLQuery(sql);
		query.setString("org_id", orgId);
		query.setString("phone", phone);
		query.setString("parent_id", parent_id);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public StudentParent getStudentParent(Integer stud_id, Integer parent_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT * FROM student2parent sp WHERE sp.stud_id=:stud_id AND sp.parent_id=:parent_id AND sp.is_del=0";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("stud_id", stud_id);
		query.setInteger("parent_id", parent_id);
	    query.setResultTransformer(Transformers.aliasToBean(StudentParent.class));
	    @SuppressWarnings("unchecked")
		List<StudentParent> ls =query.list();
		return ls.size()>0?ls.get(0):null;
	}
	
	@Override
	public Parent getParentByUserID(int orgID, int userID) {
		String sql = "SELECT p.* FROM parent p WHERE p.org_id=? AND p.user_id=? AND p.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query q = session.createSQLQuery(sql);
		q.setInteger(0, orgID);
		q.setInteger(1, userID);
		q.setResultTransformer(Transformers.aliasToBean(Parent.class));

		@SuppressWarnings("unchecked")
		List<Parent> result = q.list();
		Parent p = null;
		if (result != null && result.size() > 0) {
			p = result.get(0);
		}
		return p;
	}

	@Override
	public List<Parent> getParentsByStudId(String stud_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT p.parent_id, p.parent_name, ou.user_mobile as mobile,ou.user_mobile_type as mobile_type, sp.relation, p.sex FROM parent p INNER JOIN student2parent sp ON p.parent_id = sp.parent_id LEFT JOIN org_user ou ON ou.user_id=p.user_id AND ou.org_id=p.org_id WHERE p.is_del = 0 AND sp.is_del = 0 AND ou.is_del = 0 AND sp.stud_id = :stud_id ";
		Query query = session.createSQLQuery(sql.toString());
		query.setString("stud_id", stud_id);
	    query.setResultTransformer(Transformers.aliasToBean(Parent.class));
	    @SuppressWarnings("unchecked")
		List<Parent> ls =query.list();
		return ls;
	}

	@Override
	public List<Parent> getParentListIncludeDeleted(int org_id) {
		String sql = "SELECT p.* FROM parent p WHERE p.org_id=?;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Parent.class));
		query.setInteger(0, org_id);
		
		return query.list();
	}



}
