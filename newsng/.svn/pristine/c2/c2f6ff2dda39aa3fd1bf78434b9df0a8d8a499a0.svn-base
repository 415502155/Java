package sng.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.StudentDao;
import sng.pojo.StudentPojo;
import sng.pojo.base.Student;
import sng.util.Constant;
import sng.util.MoneyUtil;
import sng.util.Paging;

/**
 * @类 名： StudentDaoImpl
 * @功能描述：学员Dao实现类 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月20日下午4:03:34
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao{

	@Override
	public Paging<StudentPojo> queryList(String term_id,String category_id,String subject_id,String clas_type,String cam_id,String stu_status,String pay_method,String is_print, String keyword, Paging<StudentPojo> page) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_id,c.clas_id,cd.cd_id,cd.cid,sc.stu_clas_id,s.stud_name,u.user_idnumber,c.clas_name,GROUP_CONCAT(t.tech_name) AS tech_name,sc.stu_status,c.tuition_fees,cd.pay_method,cd.`status`,cd.money,cd.txnAmt FROM edugate_base.student s INNER JOIN edugate_base.org_user u ON s.user_id=u.user_id AND s.org_id=u.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN student_class sc ON sc.stud_id=s.stud_id INNER JOIN classes c ON c.clas_id=sc.clas_id AND c.cam_id=sc.cam_id AND c.org_id=s.org_id AND c.org_id=u.org_id AND c.is_del=0 INNER JOIN teacher_class tc ON tc.clas_id=c.clas_id AND tc.clas_id=sc.clas_id AND tc.is_del=0 INNER JOIN edugate_base.teacher t ON t.tech_id=tc.tech_id AND t.org_id=s.org_id AND t.org_id=u.org_id AND t.org_id=c.org_id AND t.is_del=0 INNER JOIN charge_detail cd ON cd.cam_id=sc.cam_id AND cd.cam_id=c.cam_id AND cd.clas_id=sc.clas_id AND cd.clas_id=c.clas_id AND cd.clas_id=tc.clas_id AND cd.org_id=s.org_id AND cd.org_id=u.org_id AND cd.org_id=c.org_id AND cd.org_id=t.org_id AND cd.stud_id=s.stud_id AND cd.stud_id=sc.stud_id AND cd.stu_class_id=sc.stu_class_id AND cd.is_del=0 WHERE s.is_del=0 ";
		String countSql = " SELECT COUNT(1) FROM edugate_base.student s INNER JOIN edugate_base.org_user u ON s.user_id=u.user_id AND s.org_id=u.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN student_class sc ON sc.stud_id=s.stud_id INNER JOIN classes c ON c.clas_id=sc.clas_id AND c.cam_id=sc.cam_id AND c.org_id=s.org_id AND c.org_id=u.org_id AND c.is_del=0 INNER JOIN teacher_class tc ON tc.clas_id=c.clas_id AND tc.clas_id=sc.clas_id AND tc.is_del=0 INNER JOIN edugate_base.teacher t ON t.tech_id=tc.tech_id AND t.org_id=s.org_id AND t.org_id=u.org_id AND t.org_id=c.org_id AND t.is_del=0 INNER JOIN charge_detail cd ON cd.cam_id=sc.cam_id AND cd.cam_id=c.cam_id AND cd.clas_id=sc.clas_id AND cd.clas_id=c.clas_id AND cd.clas_id=tc.clas_id AND cd.org_id=s.org_id AND cd.org_id=u.org_id AND cd.org_id=c.org_id AND cd.org_id=t.org_id AND cd.stud_id=s.stud_id AND cd.stud_id=sc.stud_id AND cd.stu_class_id=sc.stu_class_id AND cd.is_del=0 WHERE s.is_del=0 ";
		if(StringUtils.isNotBlank(term_id)){
			sql += " AND c.term_id=:term_id ";
			countSql += " AND c.term_id=:term_id ";
		}
		if(StringUtils.isNotBlank(category_id)){
			sql += " AND c.category_id=:category_id ";
			countSql += " AND c.category_id=:category_id ";
		}
		if(StringUtils.isNotBlank(subject_id)){
			sql += " AND c.subject_id=:subject_id ";
			countSql += " AND c.subject_id=:subject_id ";
		}
		if(StringUtils.isNotBlank(clas_type)){
			sql += " AND c.clas_type=:clas_type ";
			countSql += " AND c.clas_type=:clas_type ";
		}
		if(StringUtils.isNotBlank(cam_id)){
			sql += " AND c.cam_id=:cam_id ";
			countSql += " AND c.cam_id=:cam_id ";
		}
		if(StringUtils.isNotBlank(stu_status)){
			sql += " AND sc.stu_status=:stu_status ";
			countSql += " AND sc.stu_status=:stu_status ";
		}
		if(StringUtils.isNotBlank(pay_method)){
			sql += " AND cd.pay_method=:pay_method ";
			countSql += " AND cd.pay_method=:pay_method ";
		}
		if(StringUtils.isNotBlank(is_print)){
			sql += "  AND sc.is_print=:is_print ";
			countSql += "  AND sc.is_print=:is_print ";
		}
		if(StringUtils.isNotBlank(keyword)){
			sql += "  AND s.stud_name LIKE :stud_name AND c.clas_name LIKE :clas_name  ";
			countSql += "  AND s.stud_name LIKE :stud_name AND c.clas_name LIKE :clas_name  ";
		}
		sql += " GROUP BY sc.stu_class_id ORDER BY sc.stu_class_id DESC LIMIT :pagenum,:pagesize ";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		if(StringUtils.isNotBlank(term_id)){
			query.setInteger("term_id", Integer.parseInt(term_id));
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if(StringUtils.isNotBlank(category_id)){
			query.setInteger("category_id", Integer.parseInt(category_id));
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if(StringUtils.isNotBlank(subject_id)){
			query.setInteger("subject_id", Integer.parseInt(subject_id));
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if(StringUtils.isNotBlank(clas_type)){
			query.setInteger("clas_type", Integer.parseInt(clas_type));
			countQuery.setInteger("clas_type", Integer.parseInt(clas_type));
		}
		if(StringUtils.isNotBlank(cam_id)){
			query.setInteger("cam_id", Integer.parseInt(cam_id));
			countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
		}
		if(StringUtils.isNotBlank(stu_status)){
			query.setInteger("stu_status", Integer.parseInt(stu_status));
			countQuery.setInteger("stu_status", Integer.parseInt(stu_status));
		}
		if(StringUtils.isNotBlank(pay_method)){
			query.setInteger("pay_method", Integer.parseInt(pay_method));
			countQuery.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if(StringUtils.isNotBlank(is_print)){
			query.setInteger("is_print", Integer.parseInt(is_print));
			countQuery.setInteger("is_print", Integer.parseInt(is_print));
		}
		if(StringUtils.isNotBlank(keyword)){
			query.setString("keyword", "%"+keyword+"%");
			countQuery.setString("keyword", "%"+keyword+"%");
		}
		query.setInteger("pagenum",  (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		@SuppressWarnings("unchecked")
		List<StudentPojo> list = query.list();
		for (StudentPojo s : list) {
			try {
				if(s.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)){
					s.setMoney("--");
					s.setTf_money("--");
				}else if(s.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_REFUND_ALL)||s.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_REFUND_PART)){
					s.setTf_money(MoneyUtil.fromFENtoYUAN(new BigDecimal(s.getMoney()).subtract(new BigDecimal(s.getTxnAmt())).toString()));
				}else{
					s.setTf_money("--");
				}
			} catch (Exception e) {
				s.setTf_money("--");
			}
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		page.setTotal(pageTotal.intValue());
		page.setSize(page.getTotal()%page.getLimit()==0 ? (page.getTotal()/page.getLimit()) : (page.getTotal()/page.getLimit()+1));
		page.setData(list);
		page.setSuccess(true);
		return page;
	}

	@Override
	public StudentPojo getStudentInfos(Integer stu_class_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT sc.stu_class_id FROM student_class sc INNER JOIN edugate_base.student s ON s.stud_id = sc.stud_id AND s.is_del = 0 INNER JOIN classes c ON sc.clas_id = c.clas_id AND c.cam_id=sc.cam_id AND c.is_del=0 AND c.org_id=s.org_id  WHERE sc.is_del=0 AND s.stud_id=:stud_id AND c.clas_id=:clas_id AND sc.quota_hold=:quota_hold";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		query.setInteger("stu_class_id", stu_class_id);
		query.setInteger("stu_class_id", stu_class_id);
		try {
			return (StudentPojo) query.list().get(0);
		} catch (Exception e) {
			return new StudentPojo();
		}
	}

}
