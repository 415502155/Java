package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IStudentDao;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentCard;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;

/**
 * 学生DAO实现类 Title:IStudentImpl Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月26日下午2:40:38
 */
@Repository
public class IStudentImpl extends BaseDAOImpl<Student> implements IStudentDao {

	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Student> getByOrgId(Integer orgId) {
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(Student.class);
		if (null != orgId) {
			c.add(Restrictions.eq("org_id", orgId));
		}
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		@SuppressWarnings("unchecked")
		List<Student> ls = c.list();
		return ls;
	}
	
	@Override
	public List<Student> getStudentListIncludeDeleted(int org_id) {
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(Student.class);
		c.add(Restrictions.eq("org_id", org_id));
		@SuppressWarnings("unchecked")
		List<Student> ls = c.list();
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getByClassId(Integer classId) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_id,s.stud_name,s.stud_card,s.stud_note,s.stud_record,s.org_id,s.user_id,s.insert_time,s.update_time,s.del_time,s.is_del,c.clas_id,c.clas_name FROM student s"
				+ " LEFT JOIN class2student cs ON cs.stud_id=s.stud_id "
				+ " LEFT JOIN classes c ON c.clas_id=cs.clas_id AND c.org_id=s.org_id "
				+ " WHERE c.is_del=0 AND s.is_del=0 AND cs.is_del=0 AND c.clas_id=:clas_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("clas_id", classId);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		List<Student> list = new ArrayList<Student>();
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getByGradeId(Integer gradeId) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_id,s.stud_name,s.stud_card,s.stud_note,s.stud_record,s.org_id,s.user_id,s.insert_time,s.update_time,s.del_time,s.is_del,g.grade_id,g.grade_name FROM student s "
				+ " LEFT JOIN class2student cs ON cs.stud_id=s.stud_id "
				+ " LEFT JOIN grade2clas gc ON gc.clas_id=cs.clas_id "
				+ " LEFT JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=s.org_id "
				+ " WHERE s.is_del=0 AND cs.is_del=0 AND gc.is_del=0 AND g.is_del=0 " + " AND g.grade_id = :grade_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("grade_id", gradeId);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		List<Student> list = new ArrayList<Student>();
		list = query.list();
		return list;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Student> getAll() {
//		Session session = this.factory.getCurrentSession();
//		String sql = " SELECT s.stud_id,s.stud_name,s.stud_card,s.stud_note,s.stud_record,s.org_id,s.user_id,s.insert_time,s.update_time,s.del_time,s.is_del,g.grade_id,cs.clas_id,ou.user_mobile FROM student s "
//				+ " LEFT JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del=0 "
//				+ " LEFT JOIN grade2clas gc ON gc.clas_id = cs.clas_id AND gc.is_del=0 "
//				+ " LEFT JOIN grade g ON g.grade_id = gc.grade_id AND g.is_del=0 AND g.org_id = s.org_id "
//				+ " LEFT JOIN org_user ou ON ou.org_id = s.org_id AND ou.is_del=0 AND ou.org_id = g.org_id AND ou.user_id = s.user_id "
//				+ " WHERE s.is_del = 0 ";
//		Query query = session.createSQLQuery(sql);
//		query.setResultTransformer(Transformers.aliasToBean(Student.class));
//		List<Student> list = new ArrayList<Student>();
//		list = query.list();
//		return list;
//	}

	@Override
	public Integer getStudentsNumber(Integer clas_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT count(1) FROM class2student cs WHERE cs.clas_id=:clas_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("clas_id", clas_id);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public Long getTotalCount(Integer org_id, String stud_name, String parent_name, String user_mobile) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT COUNT(*) FROM (" + "SELECT " + "count(*) " + "FROM" + "	student s  inner join class2student cs on s.stud_id=cs.stud_id AND cs.is_del=0 inner join classes c on cs.clas_id = c.clas_id and c.is_graduated=0 AND c.is_del=0 "
				+ "LEFT JOIN student2parent s2p ON s.stud_id = s2p.stud_id AND s2p.is_del=0 "
				+ "LEFT JOIN parent p ON s2p.parent_id = p.parent_id AND p.is_del=0 "
				+ "LEFT JOIN org_user u ON p.user_id=u.user_id "
				+ "LEFT JOIN grade2clas gc ON c.clas_id=gc.clas_id "
				+ "LEFT JOIN grade g ON gc.grade_id=g.grade_id "
				+ "WHERE "
				+ "s.org_id = :org_id AND s.is_del=0 AND g.is_del=0 AND c.is_del=0 AND gc.is_del=0 ";

		if (stud_name != null && !"".equals(stud_name)) {
			sql += " AND s.stud_name LIKE :stud_name ";
		}
		if (parent_name != null && !"".equals(parent_name)) {
			sql += " AND p.parent_name LIKE :parent_name ";
		}
		if (user_mobile != null && !"".equals(user_mobile)) {
			sql += " AND u.user_mobile LIKE :user_mobile ";
		}
		sql += "GROUP BY s.stud_id ORDER BY s.stud_id DESC" + ") tt";

		Query query = session.createSQLQuery(sql.toString());

		query.setInteger("org_id", org_id);

		if (stud_name != null && !"".equals(stud_name)) {
			query.setString("stud_name", "%" + stud_name + "%");
		}
		if (parent_name != null && !"".equals(parent_name)) {
			query.setString("parent_name", "%" + parent_name + "%");
		}
		if (user_mobile != null && !"".equals(user_mobile)) {
			query.setString("user_mobile", "%" + user_mobile + "%");
		}

		BigInteger count = (BigInteger) query.uniqueResult();

		return count.longValue();
	}

	@Override
	public void getPaging(Integer org_id, String stud_name, String parent_name, String user_mobile, Paging<Student> pages) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "s.*,GROUP_CONCAT(p.parent_name SEPARATOR ',') parent_name,GROUP_CONCAT(u.user_mobile SEPARATOR ',') parent_mobile "
				+ "FROM  student s inner join class2student cs on s.stud_id=cs.stud_id AND cs.is_del=0 inner join classes c on cs.clas_id = c.clas_id and c.is_graduated=0 AND c.is_del=0 LEFT JOIN student2parent s2p ON s.stud_id = s2p.stud_id AND s2p.is_del=0 "
				+ "LEFT JOIN parent p ON s2p.parent_id = p.parent_id AND p.is_del=0 "
				+ "LEFT JOIN org_user u ON p.user_id=u.user_id "
				+ "LEFT JOIN grade2clas gc ON c.clas_id=gc.clas_id "
				+ "LEFT JOIN grade g ON gc.grade_id=g.grade_id "
				+ "WHERE "
				+ "s.org_id = :org_id AND s.is_del=0 AND g.is_del=0 AND gc.is_del=0 "
				+ "AND g.grade_number NOT LIKE '%9' AND g.grade_number NOT LIKE '%8' ";

		if (stud_name != null && !"".equals(stud_name)) {
			sql += " AND s.stud_name LIKE :stud_name ";
		}
		if (parent_name != null && !"".equals(parent_name)) {
			sql += " AND p.parent_name LIKE :parent_name ";
		}
		if (user_mobile != null && !"".equals(user_mobile)) {
			sql += " AND u.user_mobile LIKE :user_mobile ";
		}
		sql += "GROUP BY s.stud_id ORDER BY s.stud_id DESC";

		Query query = session.createSQLQuery(sql.toString());

		query.setInteger("org_id", org_id);

		if (stud_name != null && !"".equals(stud_name)) {
			query.setString("stud_name", "%" + stud_name + "%");
		}
		if (parent_name != null && !"".equals(parent_name)) {
			query.setString("parent_name", "%" + parent_name + "%");
		}
		if (user_mobile != null && !"".equals(user_mobile)) {
			query.setString("user_mobile", "%" + user_mobile + "%");
		}
		if (pages.getPage() > pages.getTotalPage()) {
			pages.setPage(pages.getTotalPage().intValue());
		}

		query.setFirstResult((pages.getPage() - 1) * pages.getLimit() + pages.getStart());
		query.setMaxResults(pages.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> ls = query.list();
		for (Student student : ls) {
			LSHelper.processInjection(student);
		}
		pages.setData(ls);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getStudentsForSelect(String groupId, String orgId) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT ca.cam_id,ca.cam_name,g.grade_id,g.grade_name,c.clas_id,c.clas_name,s.stud_id,s.stud_name,CASE s.sex WHEN 0 THEN '男' WHEN 1 THEN '女' ELSE '保密' END as sex,IFNULL(gm.group_member_id, 0) AS is_selected FROM student s "
				+ " INNER JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del=0 "
				+ " INNER JOIN classes c ON c.clas_id=cs.clas_id AND c.org_id = s.org_id AND c.is_del = 0 "
				+ " INNER JOIN grade2clas gc ON gc.clas_id=c.clas_id AND gc.clas_id=cs.clas_id AND gc.is_del = 0 "
				+ " INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=s.org_id AND g.org_id=c.org_id AND g.is_del = 0 "
				+ " INNER JOIN campus ca ON ca.cam_id = c.cam_id AND ca.org_id=s.org_id AND ca.org_id=c.org_id AND ca.org_id=g.org_id AND ca.is_del=0 "
				+ " LEFT JOIN group_member gm ON gm.mem_id=s.stud_id AND gm.is_del=0 AND gm.group_id=:group_id "
				+ " LEFT JOIN `group` gr ON gr.group_id=gm.group_id AND gr.group_type=4 AND gr.org_id=s.org_id AND gr.org_id=c.org_id AND gr.org_id=g.org_id AND gr.org_id=ca.org_id AND gr.is_del=0  "
				+ " WHERE s.is_del=0 AND s.org_id=:org_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("group_id", Integer.parseInt(groupId));
		query.setInteger("org_id", Integer.parseInt(orgId));
		return query.list();
	}

	@Override
	public List<Student> getStudentsByClassId(Integer clas_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT " + "s.*, cs.clas2stud_id " + "FROM class2student cs "
				+ "LEFT JOIN student s ON cs.stud_id = s.stud_id " + "WHERE " + "cs.clas_id = :clas_id " + "AND cs.is_del = 0 ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("clas_id", clas_id);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@Override
	public List<Student> getStudentsByGradeId(Integer grade_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT " + "s.*, gc.grade_id " + "FROM" + "	class2student cs "
				+ "LEFT JOIN student s ON cs.stud_id = s.stud_id " + "LEFT JOIN grade2clas gc ON cs.clas_id = gc.clas_id "
				+ "WHERE " + "cs.is_del = 0 AND gc.grade_id=:grade_id " + "GROUP BY" + "	s.stud_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("grade_id", grade_id);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@Override
	public Student getByName(Integer org_id, String stud_name,
			Set<String> mobiles) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT "
				+ "s.*"
				+ "FROM"
				+ "	student s "
				+ "LEFT JOIN student2parent spt ON s.stud_id = spt.stud_id "
				+ "LEFT JOIN parent p ON spt.parent_id=p.parent_id "
				+ "LEFT JOIN org_user ou ON p.user_id=ou.user_id "
				+ "WHERE "
				+ "s.stud_name = :stud_name AND s.org_id=:org_id AND s.is_del=0 AND ou.user_mobile IN (:mobiles) ;";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setString("stud_name", stud_name);
		query.setParameterList("mobiles", mobiles);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students.size()>0?students.get(0):null;
	}
	
	@Override
	public Student getStudentByUserID(int orgID, int userID) {
		String sql = "SELECT s.* FROM student s WHERE s.org_id=? AND s.user_id=? AND s.is_del=0;";
		
		Session s = this.factory.getCurrentSession();
		Query q = s.createSQLQuery(sql);
		q.setInteger(0, orgID);
		q.setInteger(1, userID);
		q.setResultTransformer(Transformers.aliasToBean(Student.class));
		
		@SuppressWarnings("unchecked")
		List<Student> result = q.list();
		Student student= null;
		if (result != null && result.size() > 0) {
			student = result.get(0);
			LSHelper.processInjection(student);
		}
		
		return student;
	}
	
	
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentParent> getAllStudAndParent() {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.*,ss.org_id FROM student2parent s INNER JOIN student ss ON s.stud_id=ss.stud_id INNER JOIN parent p ON s.parent_id=p.parent_id WHERE s.is_del=0 AND ss.is_del=0 AND p.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentParent.class));
		List<StudentParent> list = new ArrayList<StudentParent>();
		list = query.list();
		return list;
	}

	@Override
	public Student getStudentByOrgUser(OrgUser ou) {
		String sql = "SELECT s.stud_id,s.stud_name,s.head,s.sex,cs.clas_id,gc.grade_id FROM student s LEFT JOIN class2student cs ON s.stud_id=cs.stud_id AND cs.is_del=0 LEFT JOIN grade2clas gc ON gc.clas_id=cs.clas_id AND gc.is_del=0 LEFT JOIN classes c ON c.clas_id=cs.clas_id AND c.is_del=0 AND c.org_id=s.org_id WHERE s.org_id = ? AND s.user_id = ? AND s.is_del = 0";
		Session s = this.factory.getCurrentSession();
		Query q = s.createSQLQuery(sql);
		q.setInteger(0, ou.getOrg_id());
		q.setInteger(1, ou.getUser_id());
		q.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> result = q.list();
		Student student= new Student();
		if (null!=result && result.size() > 0) {
			student = result.get(0);
		}
		return student;
	}

	@Override
	public List<Student> getByName(Integer org_id, String stud_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "s.* "
				+ "FROM"
				+ "	student s "
				+ "LEFT JOIN class2student cs ON s.stud_id = cs.stud_id "
				+ "LEFT JOIN grade2clas gc ON cs.clas_id = gc.clas_id "
				+ "LEFT JOIN grade g ON gc.grade_id = g.grade_id "
				+ "WHERE "
				+ "s.stud_name = :stud_name "
				+ "AND s.org_id = :org_id "
				+ "AND s.is_del = 0 "
				+ "AND cs.is_del = 0 "
				+ "AND gc.is_del = 0 "
				+ "AND g.is_del = 0 "
				+ "AND g.grade_number NOT LIKE '%9';";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setString("stud_name", stud_name);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@Override
	public List<Student> getStudentByClassIdStudentName(Integer clas_id,
			String stud_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "s.* "
				+ "FROM"
				+ "	class2student cs "
				+ "LEFT JOIN student s ON cs.stud_id = s.stud_id "
				+ "WHERE "
				+ "s.stud_name = :stud_name "
				+ "AND cs.clas_id = :clas_id "
				+ "AND cs.is_del = 0 "
				+ "AND s.is_del = 0";
		Query query = session.createSQLQuery(sql);
		query.setInteger("clas_id", clas_id);
		query.setString("stud_name", stud_name);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@Override
	public List<Student> getGradStudentsByName(Integer org_id, String stud_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "s.*,cs.clas_id "
				+ "FROM"
				+ "	student s "
				+ "LEFT JOIN class2student cs ON s.stud_id = cs.stud_id "
				+ "LEFT JOIN grade2clas gc ON cs.clas_id = gc.clas_id "
				+ "LEFT JOIN grade g ON gc.grade_id = g.grade_id "
				+ "WHERE "
				+ "s.stud_name = :stud_name "
				+ "AND s.org_id = :org_id "
				+ "AND s.is_del = 0 "
				+ "AND cs.is_del = 0 "
				+ "AND gc.is_del = 0 "
				+ "AND g.is_del = 0 AND g.grade_number LIKE '%8';";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setString("stud_name", stud_name);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		@SuppressWarnings("unchecked")
		List<Student> students = query.list();
		for (Student student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getStudentsWithGradeClass(Integer orgId) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT ca.cam_id,  ca.cam_name,  g.grade_id,  g.grade_name,  c.clas_id,  c.clas_name,  s.stud_id,  s.stud_name,  CASE s.sex WHEN 0 THEN  '男' WHEN 1 THEN  '女' ELSE  '保密' END AS sex, 0 AS is_selected FROM  student s INNER JOIN class2student cs ON cs.stud_id = s.stud_id AND cs.is_del = 0 INNER JOIN classes c ON c.clas_id = cs.clas_id AND c.org_id = s.org_id AND c.is_del = 0  AND c.is_graduated=0 INNER JOIN grade2clas gc ON gc.clas_id = c.clas_id AND gc.clas_id = cs.clas_id AND gc.is_del = 0 INNER JOIN grade g ON g.grade_id = gc.grade_id AND g.org_id = s.org_id AND g.org_id = c.org_id AND g.is_del = 0 INNER JOIN campus ca ON ca.cam_id = c.cam_id AND ca.org_id = s.org_id AND ca.org_id = c.org_id AND ca.org_id = g.org_id AND ca.is_del = 0 WHERE s.is_del = 0 AND s.org_id = :org_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", orgId);
		return query.list();
	}

	@Override
	public List<StudentCard> getStudentListByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT s.*,c.clas_name,g.grade_name,GROUP_CONCAT(distinct cd.card_no SEPARATOR ',') card_no,org.org_name_cn  "
				+ "FROM"
				+ "	student s "
				+ "INNER JOIN class2student cs ON s.stud_id = cs.stud_id "
				+ "AND cs.is_del = 0 "
				+ "INNER JOIN classes c ON cs.clas_id = c.clas_id "
				+ "AND c.is_graduated = 0 "
				+ "AND c.is_del = 0 "
				+ "LEFT JOIN student2parent s2p ON s.stud_id = s2p.stud_id "
				+ "AND s2p.is_del = 0 "
				+ "LEFT JOIN parent p ON s2p.parent_id = p.parent_id "
				+ "AND p.is_del = 0 "
				+ "LEFT JOIN org_user u ON p.user_id = u.user_id "
				+ "LEFT JOIN grade2clas gc ON c.clas_id = gc.clas_id "
				+ "LEFT JOIN grade g ON gc.grade_id = g.grade_id "
				+ "LEFT JOIN card cd ON cd.stud_id = s.stud_id "
				+ "LEFT JOIN organization org ON org.org_id=s.org_id "
				+ "WHERE "
				+ "s.org_id = :org_id "
				+ "AND s.is_del = 0 "
				+ "AND g.is_del = 0 "
				+ "AND gc.is_del = 0 "
				+ "AND g.grade_number NOT LIKE '%9' "
				+ "AND g.grade_number NOT LIKE '%8' "
				+ "GROUP BY s.stud_id ORDER BY s.stud_id DESC";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(StudentCard.class));
		@SuppressWarnings("unchecked")
		List<StudentCard> students = query.list();
		for (StudentCard student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}

	@Override
	public List<StudentCard> getStudsAndClasByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT ss.stud_id,ss.stud_name,ss.user_id,c.clas_id,c1.clas_name  FROM student ss left JOIN class2student c ON ss.stud_id=c.stud_id left JOIN classes c1 ON c.clas_id=c1.clas_id WHERE ss.org_id=:org_id AND c1.is_graduated=0 AND ss.is_del=0 AND c.is_del=0 AND c1.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(StudentCard.class));
		@SuppressWarnings("unchecked")
		List<StudentCard> students = query.list();
		for (StudentCard student : students) {
			LSHelper.processInjection(student);
		}
		return students;
	}


}
