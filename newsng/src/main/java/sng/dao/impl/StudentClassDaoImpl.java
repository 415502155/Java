package sng.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.StudentClassDao;
import sng.entity.StudentClass;

/**
 * @类 名： StudentClassDaoImpl
 * @功能描述：学生班级关系 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月20日下午2:50:17
 */
@Repository
public class StudentClassDaoImpl extends BaseDaoImpl<StudentClass> implements StudentClassDao{

	@Override
	public StudentClass getWithNames(Integer stud_id, Integer clas_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT s.stud_name,c.clas_name,sc.stu_class_id,c.tuition_fees FROM student_class sc INNER JOIN edugate_base.student s ON s.stud_id = sc.stud_id AND s.is_del = 0 INNER JOIN classes c ON sc.clas_id = c.clas_id AND c.cam_id=sc.cam_id AND c.is_del=0 AND c.org_id=s.org_id  WHERE sc.is_del=0 AND s.stud_id=:stud_id AND c.clas_id=:clas_id";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentClass.class));
		query.setInteger("stud_id", stud_id);
		query.setInteger("clas_id", clas_id);
		try {
			return (StudentClass) query.list().get(0);
		} catch (Exception e) {
			return new StudentClass();
		}
	}

	@Override
	public StudentClass getInfosById(Integer stu_clas_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT sc.*, ca.category_id, ca.category_name, s.subject_id, s.subject_name, c.class_week, c.class_unset_time, c.class_begin_time, c.class_over_time, cam.cam_name, CONCAT(cr.building,cr.floor,cr.classroom_name) as classroom_name,c.tuition_fees, GROUP_CONCAT(t.tech_id) AS teacher_ids FROM student_class sc INNER JOIN classes c ON c.cam_id = sc.cam_id AND c.clas_id = sc.clas_id AND c.is_del = 0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` s ON s.category_id = c.category_id AND s.category_id = ca.category_id AND s.org_id = ca.org_id AND s.org_id = c.org_id AND s.subject_id = c.subject_id AND s.is_del = 0 INNER JOIN campus cam ON cam.cam_id = sc.cam_id AND cam.cam_id = c.cam_id AND cam.org_id = c.org_id AND cam.org_id = ca.org_id AND cam.org_id = s.org_id AND cam.is_del = 0 INNER JOIN classroom cr ON cr.cam_id = sc.cam_id AND cr.cam_id = c.cam_id AND cr.cam_id = cam.cam_id AND cr.classroom_id = c.classroom_id AND cr.is_del = 0 INNER JOIN teacher_class tc ON tc.clas_id=sc.clas_id AND tc.clas_id=c.clas_id AND tc.is_del=0 INNER JOIN edugate_base.teacher t ON t.org_id=c.org_id AND t.org_id=ca.org_id AND t.org_id=s.org_id AND t.org_id=cam.org_id AND t.tech_id=tc.tech_id AND t.is_del=0 WHERE sc.is_del=0 AND sc.stu_class_id = :stu_class_id GROUP BY sc.stu_class_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentClass.class));
		query.setInteger("stu_clas_id", stu_clas_id);
		try {
			return (StudentClass) query.list().get(0);
		} catch (Exception e) {
			return new StudentClass();
		}
	}

}
