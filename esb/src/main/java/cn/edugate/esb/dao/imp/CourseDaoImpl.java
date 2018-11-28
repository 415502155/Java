package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.CourseDao;
import cn.edugate.esb.entity.Course;

@Repository
public class CourseDaoImpl extends BaseDAOImpl<Course> implements CourseDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCourseAll() {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String hql = "SELECT c.* FROM course c where c.is_del=0 order by c.cour_name";
		Query query = session.createSQLQuery(hql);

		query.setResultTransformer(Transformers.aliasToBean(Course.class));
		List<Course> list = new ArrayList<Course>();
		list = query.list();
		return list;
	}


	@Override
	public int checkName(int orgID, String courseName) {
		Session session = this.factory.getCurrentSession();

		String sql = "SELECT COUNT(c.cour_id) FROM edugate_base.course c WHERE c.org_id=? AND c.cour_name=? AND c.is_del=0;";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, orgID);
		query.setString(1, courseName);
		BigInteger count = (BigInteger) query.uniqueResult();

		return count.intValue();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCourseList(int org_id) {
		String sql = "SELECT c.* FROM course c WHERE c.org_id=? AND c.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setResultTransformer(Transformers.aliasToBean(Course.class));
		List<Course> list = new ArrayList<Course>();
		list = query.list();
		return list;
	}
	
	@Override
	public List<Course> getCourseListIncludeDeleted(int org_id) {
		String sql = "SELECT c.* FROM course c WHERE c.org_id=? AND c.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setResultTransformer(Transformers.aliasToBean(Course.class));
		List<Course> list = new ArrayList<Course>();
		list = query.list();
		return list;
	}

}
