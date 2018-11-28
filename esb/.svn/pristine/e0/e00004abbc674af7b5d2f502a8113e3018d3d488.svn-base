package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IClass2StudentDao;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.util.LSHelper;

/**
 * 班级DAO实现类 Title:IClassesImpl Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月25日下午1:45:55
 */
@Repository
public class IClass2StudentImpl extends BaseDAOImpl<Clas2Student> implements IClass2StudentDao {

	@Override
	public List<Clas2Student> getClas2StudentList(int studentID, int classID) {
		String sql = "SELECT c2s.* FROM edugate_base.class2student c2s WHERE c2s.stud_id=? AND c2s.clas_id=? AND c2s.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, studentID);
		query.setInteger(1, classID);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		List<Clas2Student> ls = query.list();
		for (Clas2Student c2s : ls) {
			LSHelper.processInjection(c2s);
		}
		return ls;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<Clas2Student> getClas2StudentList() {
		String sql = "SELECT c2s.*, s.stud_name, c.org_id, c.clas_name,g.grade_id, g.grade_name, s.user_id FROM edugate_base.class2student c2s INNER JOIN classes c ON c2s.clas_id = c.clas_id AND c.is_del=0 INNER JOIN student s ON c2s.stud_id = s.stud_id AND s.org_id=c.org_id AND s.is_del=0 INNER JOIN grade2clas gc ON gc.clas_id=c.clas_id AND gc.is_del=0 INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=c.org_id AND g.org_id=s.org_id AND g.is_del=0 WHERE c2s.is_del = 0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		List<Clas2Student> ls = query.list();
		for (Clas2Student c2s : ls) {
			LSHelper.processInjection(c2s);
		}
		return ls;
	}

	@Override
	public List<Clas2Student> getClass2StudentListByStudentId(int studentId) {
		String sql = "SELECT c2s.* FROM class2student c2s WHERE c2s.stud_id=? AND c2s.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, studentId);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));

		List<Clas2Student> resultList = query.list();
		return resultList;
	}

	@Override
	public List<Clas2Student> getClass2StudentListByClassId(int classId) {
		String sql = "SELECT c2s.* FROM class2student c2s WHERE c2s.clas_id=? AND c2s.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, classId);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));

		List<Clas2Student> resultList = query.list();
		return resultList;
	}

	@Override
	public List<Clas2Student> getOldForDelete(Integer stud_id, Integer org_id) {
		String sql = " SELECT c.* FROM class2student c INNER JOIN classes c1 ON c.clas_id=c1.clas_id AND c1.org_id=? WHERE c.stud_id = ? AND c.is_del=0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setInteger(1, stud_id);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		@SuppressWarnings("unchecked")
		List<Clas2Student> resultList = query.list();
		return resultList;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<Clas2Student> getClass2StudentListIncludeDeleted(int org_id) {
		/*String sql = "SELECT c2s.* FROM class2student c2s WHERE c2s.clas_id IN (SELECT c.clas_id FROM classes c WHERE c.org_id=?) OR c2s.stud_id IN (SELECT s.stud_id FROM student s WHERE s.org_id=?);";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		query.setInteger(0, org_id);
		query.setInteger(1, org_id);
		return query.list();*/
		
		String sql = "SELECT c2s.*, s.stud_name, c.org_id, c.clas_name,g.grade_id, g.grade_name, s.user_id FROM edugate_base.class2student c2s INNER JOIN classes c ON c2s.clas_id = c.clas_id AND c.is_del=0 INNER JOIN student s ON c2s.stud_id = s.stud_id AND s.org_id=c.org_id AND s.is_del=0 INNER JOIN grade2clas gc ON gc.clas_id=c.clas_id AND gc.is_del=0 INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=c.org_id AND g.org_id=s.org_id AND g.is_del=0 WHERE c2s.is_del = 0 AND c.org_id=:org_id ";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		query.setInteger("org_id", org_id);
		return query.list();
	}

	@Override
	public List<Clas2Student> getClas2StudentByClassId(Integer clas_id) {
		String sql = "SELECT s.user_id, cs.clas2stud_id, s.stud_id, g.grade_id, g.grade_name, cl.clas_id, cl.clas_name, s.stud_name, group_concat( IF ( u.openid IS NULL OR TRIM(u.openid) = '', '未关注', '已关注' ) ) as is_bind, cs.insert_time FROM student s LEFT JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 LEFT JOIN classes cl ON cl.clas_id = cs.clas_id AND cl.org_id = s.org_id AND cl.is_del = 0 LEFT JOIN grade2clas gc ON gc.clas_id = cl.clas_id AND gc.is_del = 0 LEFT JOIN grade g ON g.grade_id = gc.grade_id AND g.is_del = 0 AND g.org_id = s.org_id AND g.org_id = cl.org_id LEFT JOIN student2parent sp ON s.stud_id = sp.stud_id AND sp.is_del = 0 LEFT JOIN parent p ON p.parent_id = sp.parent_id AND p.is_del = 0 AND p.org_id = s.org_id AND p.org_id = cl.org_id AND p.org_id = g.org_id LEFT JOIN org_user u ON p.user_id = u.user_id AND u.org_id = s.org_id AND u.org_id = cl.org_id AND u.org_id = g.org_id AND u.org_id = p.org_id AND u.is_del = 0 AND u.identity=0 WHERE s.is_del=0 AND cl.clas_id=:clas_id GROUP BY s.stud_id ";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger("clas_id", clas_id);
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		@SuppressWarnings("unchecked")
		List<Clas2Student> ls = query.list();
		return ls;
	}

}
