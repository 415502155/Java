package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.ITeacherRoleDao;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.util.LSHelper;

@Repository
public class ITeacherRoleImpl extends BaseDAOImpl<TeacherRole> implements ITeacherRoleDao {

	@Override
	public TeacherRole getTeachRole(Integer role_id, Integer tech_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String hql="SELECT tr FROM TeacherRole tr WHERE tr.role_id=:role_id and tr.tech_id=:tech_id";
		Query query = session.createQuery(hql);
		query.setInteger("role_id", role_id);
		query.setInteger("tech_id", tech_id);
		@SuppressWarnings("unchecked")
		List<TeacherRole> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public List<TeacherRole> getTeacherRolesOfSql() {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT u.*,t.org_id FROM tech2role u left join org_user t on u.tech_id=t.user_id  WHERE 1=1 AND u.is_del=0 ";
		Query query = session.createSQLQuery(sql);

		query.setResultTransformer(Transformers.aliasToBean(TeacherRole.class));
		@SuppressWarnings("unchecked")
		List<TeacherRole> ls = query.list();

		return ls;
	}

	@Override
	public List<TeacherRole> getTeacherRoleListIncludeDeleted(int org_id) {
		String sql = "SELECT t2r.* FROM tech2role t2r WHERE t2r.tech_id IN (SELECT ou.user_id FROM org_user ou WHERE ou.org_id=? AND (ou.identity=0 OR ou.identity=1 OR ou.identity=2));";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TeacherRole.class));
		query.setInteger(0, org_id);
		return query.list();
	}
	
	
	@Override
	public List<TeacherRole> getTeachRoles(Integer user_id) {
		String sql = "SELECT t2r.* FROM tech2role t2r WHERE t2r.tech_id=? and t2r.is_del=0;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TeacherRole.class));
		query.setInteger(0, user_id);
		return query.list();
	}

	@Override
	public List<TeacherRole> getTeacherRoleByOrg(int org_id) {
		String sql = "SELECT t2r.* FROM tech2role t2r WHERE t2r.tech_id IN (SELECT ou.user_id FROM org_user ou WHERE ou.org_id=? AND (ou.identity=1 or ou.identity=99) AND ou.is_del=0)  AND t2r.is_del=0;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TeacherRole.class));
		query.setInteger(0, org_id);
		return query.list();
	}


}
