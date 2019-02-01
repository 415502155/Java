package sng.dao.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import sng.constant.Consts;
import sng.dao.StudentDao;
import sng.entity.StudentClass;
import sng.pojo.Counter;
import sng.pojo.Clas2Student;
import sng.pojo.ParamObj;
import sng.pojo.StudentPojo;
import sng.pojo.base.Student;
import sng.util.Constant;
import sng.util.MoneyUtil;
import sng.util.Paging;

/**
 * @类 名： StudentDaoImpl
 * @功能描述：学员Dao实现类 @作者信息： LiuYang @创建时间： 2018年11月20日下午4:03:34
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

	@Override
	public Map<String, Object> queryList(Integer org_id, String term_id, String category_id, String subject_id, String clas_type,
			String cam_id, String stu_status, String pay_method, String is_print, String keyword,
			Integer currentPageNum, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		Paging<StudentPojo> page = new Paging<StudentPojo>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT cd.online_pay,cd.offline_pay,s.stud_id,c.clas_id,cd.cd_id,cd.cid,sc.stu_class_id,s.stud_name,u.user_idnumber,c.clas_name,GROUP_CONCAT(DISTINCT(t.tech_name)) AS tech_name,sc.stu_status,c.tuition_fees,cd.pay_method,cd.`status`,cd.money,sc.is_print,su.subject_name,ca.category_name,IFNULL(cr1.txnAmt, IFNULL(cr2.txnAmt, IFNULL(cr3.txnAmt, 0))) AS refund_sum,p.plan_switch FROM student_class sc INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id and s.is_del=0 INNER JOIN edugate_base.org_user u ON s.user_id=u.user_id AND s.org_id=u.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN classes c ON c.clas_id=sc.clas_id AND c.cam_id=sc.cam_id AND c.org_id=s.org_id AND c.org_id=u.org_id AND c.is_del=0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = c.subject_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = c.org_id AND su.is_del = 0 LEFT JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = c.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 LEFT JOIN charge_detail cd ON cd.cam_id = sc.cam_id AND cd.cam_id = c.cam_id AND cd.clas_id = sc.clas_id AND cd.clas_id = c.clas_id  AND cd.org_id = s.org_id AND cd.org_id = u.org_id AND cd.org_id = c.org_id  AND cd.stud_id = s.stud_id AND cd.stud_id = sc.stud_id AND cd.stu_class_id = sc.stu_class_id AND cd.org_id = ca.org_id AND cd.org_id = su.org_id AND cd.is_del = 0 LEFT JOIN plan p ON p.plan_id = c.plan_id AND p.term_id = c.term_id AND p.term_id = c.term_id AND p.org_id = s.org_id AND p.org_id = u.org_id AND p.org_id = c.org_id AND p.org_id = t.org_id AND p.org_id = cd.org_id AND p.org_id = ca.org_id AND p.org_id = su.org_id AND p.is_del = 0 LEFT JOIN charge_record cr1 ON cr1.cid = cd.cid AND cr1.cd_id = cd.cd_id AND cr1.txnType = 'PREVIEW' LEFT JOIN charge_record cr2 ON cr2.cid = cd.cid AND cr2.cd_id = cd.cd_id AND cr2.txnType = 'SQYLTF' LEFT JOIN charge_record cr3 ON cr3.cid = cd.cid AND cr3.cd_id = cd.cd_id AND cr3.txnType = 'SQXXTF' WHERE sc.is_del=0 ";
		// 该方法不支持名称查询，因为在countSql语句中为了快速查询没有关联category、subject、chargeRecord表，如果需要改造，需要把countSql语句先关联上数据表
		String countSql = " SELECT COUNT(1) AS num, IFNULL(SUM(a.mm),0) AS money FROM ( SELECT cd.txnAmt AS mm FROM student_class sc INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id and s.is_del=0 INNER JOIN edugate_base.org_user u ON s.user_id=u.user_id AND s.org_id=u.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN classes c ON c.clas_id=sc.clas_id AND c.cam_id=sc.cam_id AND c.org_id=s.org_id AND c.org_id=u.org_id AND c.is_del=0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = c.subject_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = c.org_id AND su.is_del = 0 LEFT JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = c.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 LEFT JOIN charge_detail cd ON cd.cam_id = sc.cam_id AND cd.cam_id = c.cam_id AND cd.clas_id = sc.clas_id AND cd.clas_id = c.clas_id  AND cd.org_id = s.org_id AND cd.org_id = u.org_id AND cd.org_id = c.org_id  AND cd.org_id = ca.org_id AND cd.org_id = su.org_id AND cd.stud_id = s.stud_id AND cd.stud_id = sc.stud_id AND cd.stu_class_id = sc.stu_class_id AND cd.is_del = 0 WHERE sc.is_del=0 ";
		if (StringUtils.isNotBlank(term_id)) {
			sql += " AND c.term_id=:term_id ";
			countSql += " AND c.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			sql += " AND c.category_id=:category_id ";
			countSql += " AND c.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			sql += " AND c.subject_id=:subject_id ";
			countSql += " AND c.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(clas_type)) {
			sql += " AND c.clas_type=:clas_type ";
			countSql += " AND c.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				sql += " AND c.cam_id in ( :cam_id ) ";
				countSql += " AND c.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				sql += " AND c.cam_id = :cam_id ";
				countSql += " AND c.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(stu_status)) {
			sql += " AND sc.stu_status=:stu_status ";
			countSql += " AND sc.stu_status=:stu_status ";
		}
		if (StringUtils.isNotBlank(pay_method)) {
			sql += " AND cd.pay_method=:pay_method ";
			countSql += " AND cd.pay_method=:pay_method ";
		}
		if (StringUtils.isNotBlank(is_print)) {
			sql += "  AND sc.is_print=:is_print ";
			countSql += "  AND sc.is_print=:is_print ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			sql += "  AND (s.stud_name LIKE :keyword OR c.clas_name LIKE :keyword)  ";
			countSql += "  AND (s.stud_name LIKE :keyword OR c.clas_name LIKE :keyword)  ";
		}
		sql += " AND u.org_id=:org_id GROUP BY sc.stu_class_id ORDER BY sc.stu_class_id DESC LIMIT :pagenum,:pagesize ";
		countSql += "  AND u.org_id=:org_id GROUP BY sc.stu_class_id  ) a";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		countQuery.setResultTransformer(Transformers.aliasToBean(Counter.class));
		if (StringUtils.isNotBlank(term_id)) {
			query.setInteger("term_id", Integer.parseInt(term_id));
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(category_id)) {
			query.setInteger("category_id", Integer.parseInt(category_id));
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			query.setInteger("subject_id", Integer.parseInt(subject_id));
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(clas_type)) {
			query.setInteger("clas_type", Integer.parseInt(clas_type));
			countQuery.setInteger("clas_type", Integer.parseInt(clas_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				query.setParameterList("cam_id", cam_id.split(","));
				countQuery.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				query.setInteger("cam_id", Integer.parseInt(cam_id));
				countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(stu_status)) {
			query.setInteger("stu_status", Integer.parseInt(stu_status));
			countQuery.setInteger("stu_status", Integer.parseInt(stu_status));
		}
		if (StringUtils.isNotBlank(pay_method)) {
			query.setInteger("pay_method", Integer.parseInt(pay_method));
			countQuery.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if (StringUtils.isNotBlank(is_print)) {
			query.setInteger("is_print", Integer.parseInt(is_print));
			countQuery.setInteger("is_print", Integer.parseInt(is_print));
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setString("keyword", "%" + keyword + "%");
			countQuery.setString("keyword", "%" + keyword + "%");
		}
		query.setInteger("org_id", org_id);
		countQuery.setInteger("org_id", org_id);
		query.setInteger("pagenum", (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		Counter counter = (Counter) countQuery.list().get(0);
		page.setTotal(counter.getNum().longValue());
		page.setSize(page.getTotal() % page.getLimit() == 0 ? (page.getTotal() / page.getLimit()): (page.getTotal() / page.getLimit() + 1));
		if(counter.getNum().intValue()!=0){
			@SuppressWarnings("unchecked")
			List<StudentPojo> list = query.list();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			for (StudentPojo s : list) {
				try {
					s.setTf_money(MoneyUtil.fromFENtoYUAN(s.getRefund_sum()));
				} catch (Exception e) {
					s.setTf_money("--");
				}
				if(Constant.CHARGE_DETAIL_STATUS_NEVER.equals(s.getStatus())){
					s.setMoney("0");
				}
			}
			page.setData(list);
			try {
				map.put("money", MoneyUtil.fromFENtoYUAN(nf.format(counter.getMoney())));
			} catch (Exception e) {
				map.put("money", "--");
			}
		}else{
			page.setData(new ArrayList<StudentPojo>());
			map.put("money", "0");
		}
		map.put("page", page);
		page.setSuccess(true);
		return map;
	}

	@Override
	public Map<String, Object> getRefundApplyList(Integer org_id,String starttime, String endtime, String category_id,
			String subject_id, String class_type, String cam_id, String term_id, String keyword, Integer currentPageNum,
			Integer pageSize) {
		Paging<StudentPojo> page = new Paging<StudentPojo>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT cr.cr_id, cr.txnType, s.stud_id, cd.cd_id, sc.stu_class_id, su.subject_name, s.stud_name, u.user_idnumber, cl.clas_name, GROUP_CONCAT(t.tech_name) AS tech_name, cl.tuition_fees, cd.pay_method, cd.txnAmt, cr.txnAmt AS tf_money, cd.txnTime, cr.insert_time, ca.category_name FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid = cd.cid AND cr.cd_id = cd.cd_id AND cd.is_del = 0 INNER JOIN charge c ON c.cid = cd.cid AND c.cid = cr.cid AND c.org_id = cd.org_id AND c.cam_id = cd.cam_id AND c.clas_id = cd.clas_id AND c.is_del = 0 INNER JOIN student_class sc ON sc.stu_class_id = cd.stu_class_id AND sc.is_del = 0 AND sc.stud_id = cd.stud_id AND sc.clas_id = c.clas_id AND sc.clas_id = cd.clas_id AND sc.cam_id = c.cam_id AND sc.cam_id = cd.cam_id INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id AND s.stud_id = cd.stud_id AND s.org_id = cd.org_id AND s.org_id = c.org_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.org_id = cd.org_id AND u.org_id = c.org_id AND u.org_id = s.org_id AND u.identity = 2 AND u.is_del = 0 INNER JOIN classes cl ON cl.clas_id = sc.clas_id AND cl.clas_id = c.clas_id AND cl.org_id = cd.org_id AND cl.org_id = c.org_id AND cl.org_id = s.org_id AND cl.org_id = u.org_id AND cl.is_del = 0 INNER JOIN category ca ON ca.category_id = cl.category_id AND ca.org_id = cd.org_id AND ca.org_id = c.org_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = cl.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = cl.subject_id AND su.org_id = cd.org_id AND su.org_id = c.org_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = cl.org_id AND su.org_id = ca.org_id AND su.is_del = 0 INNER JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = cl.clas_id AND tc.is_del = 0 INNER JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = cd.org_id AND t.org_id = c.org_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = cl.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 WHERE cr.is_show = 1 AND cr.is_del = 0 AND sc.stu_status = :stu_status AND ( cr.txnType=:txnType1 OR cr.txnType=:txnType2 ) ";
		// 该方法不支持名称查询，因为在countSql语句中为了快速查询没有关联category、subject、teacher、teacher_class、学生的org_user表，如果需要改造，需要把countSql语句先关联上数据表
		String countSql = " SELECT COUNT(1) AS num, IFNULL(SUM(money), 0) AS money FROM ( SELECT COUNT(1) AS num, IFNULL(SUM(cd.txnAmt), 0) AS money FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid = cd.cid AND cr.cd_id = cd.cd_id AND cd.is_del = 0 INNER JOIN charge c ON c.cid = cd.cid AND c.cid = cr.cid AND c.org_id = cd.org_id AND c.cam_id = cd.cam_id AND c.clas_id = cd.clas_id AND c.is_del = 0 INNER JOIN student_class sc ON sc.stu_class_id = cd.stu_class_id AND sc.is_del = 0 AND sc.stud_id = cd.stud_id AND sc.clas_id = c.clas_id AND sc.clas_id = cd.clas_id AND sc.cam_id = c.cam_id AND sc.cam_id = cd.cam_id INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id AND s.stud_id = cd.stud_id AND s.org_id = cd.org_id AND s.org_id = c.org_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.org_id = cd.org_id AND u.org_id = c.org_id AND u.org_id = s.org_id AND u.identity = 2 AND u.is_del = 0 INNER JOIN classes cl ON cl.clas_id = sc.clas_id AND cl.clas_id = c.clas_id AND cl.org_id = cd.org_id AND cl.org_id = c.org_id AND cl.org_id = s.org_id AND cl.org_id = u.org_id AND cl.is_del = 0 INNER JOIN category ca ON ca.category_id = cl.category_id AND ca.org_id = cd.org_id AND ca.org_id = c.org_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = cl.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = cl.subject_id AND su.org_id = cd.org_id AND su.org_id = c.org_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = cl.org_id AND su.org_id = ca.org_id AND su.is_del = 0 INNER JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = cl.clas_id AND tc.is_del = 0 INNER JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = cd.org_id AND t.org_id = c.org_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = cl.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 WHERE cr.is_show = 1 AND cr.is_del = 0 AND sc.stu_status = :stu_status AND ( cr.txnType=:txnType1 OR cr.txnType=:txnType2 ) ";
		if (StringUtils.isNotBlank(starttime)) {
			sql += " AND cd.txnTime>=:start_time ";
			countSql += " AND cd.txnTime>=:start_time ";
		}
		if (StringUtils.isNotBlank(endtime)) {
			sql += " AND cd.txnTime<=:end_time ";
			countSql += " AND cd.txnTime<=:end_time ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			sql += "  AND cl.category_id=:category_id ";
			countSql += "  AND cl.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			sql += "  AND cl.subject_id=:subject_id ";
			countSql += "  AND cl.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(class_type)) {
			sql += " AND cl.clas_type=:clas_type ";
			countSql += " AND cl.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				sql += " AND cl.cam_id in ( :cam_id ) ";
				countSql += " AND cl.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				sql += " AND cl.cam_id = :cam_id ";
				countSql += " AND cl.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			sql += " AND cl.term_id=:term_id ";
			countSql += " AND cl.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			sql += " AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
			countSql += "  AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
		}
		sql += " AND cd.org_id=:org_id GROUP BY cr.cr_id ORDER BY cr.insert_time DESC LIMIT :pagenum,:pagesize ";
		countSql+="  AND cd.org_id=:org_id GROUP BY cr.cr_id ) t";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		countQuery.setResultTransformer(Transformers.aliasToBean(Counter.class));
		if (StringUtils.isNotBlank(starttime)) {
			try {
				query.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
				countQuery.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -100);
				query.setDate("start_time", cal.getTime());
				countQuery.setDate("start_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(endtime)) {
			try {
				query.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
				countQuery.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 100);
				query.setDate("end_time", cal.getTime());
				countQuery.setDate("end_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(category_id)) {
			query.setInteger("category_id", Integer.parseInt(category_id));
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			query.setInteger("subject_id", Integer.parseInt(subject_id));
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(class_type)) {
			query.setInteger("clas_type", Integer.parseInt(class_type));
			countQuery.setInteger("clas_type", Integer.parseInt(class_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				query.setParameterList("cam_id", cam_id.split(","));
				countQuery.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				query.setInteger("cam_id", Integer.parseInt(cam_id));
				countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			query.setInteger("term_id", Integer.parseInt(term_id));
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setString("keyword", "%" + keyword + "%");
			countQuery.setString("keyword", "%" + keyword + "%");
		}
		query.setInteger("org_id", org_id);
		countQuery.setInteger("org_id", org_id);
		query.setInteger("stu_status", Consts.STUD_STATUS_7REFUND_AUDITED);
		countQuery.setInteger("stu_status", Consts.STUD_STATUS_7REFUND_AUDITED);
		query.setString("txnType1", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		countQuery.setString("txnType1", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		query.setString("txnType2", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		countQuery.setString("txnType2", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		query.setInteger("pagenum", (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		Counter counter = (Counter) countQuery.list().get(0);
		page.setTotal(counter.getNum().longValue());
		if(counter.getNum().intValue()!=0){
			@SuppressWarnings("unchecked")
			List<StudentPojo> list = query.list();
			for (StudentPojo s : list) {
				try {
					s.setTf_money(MoneyUtil.fromFENtoYUAN(s.getTf_money()));
				} catch (Exception e) {
					s.setTf_money("--");
				}
				try {
					s.setTxnAmt(MoneyUtil.fromFENtoYUAN(s.getTxnAmt()));
				} catch (Exception e) {
					s.setTxnAmt("0");
				}
			}
			page.setData(list);
		}
		page.setSize(page.getTotal() % page.getLimit() == 0 ? (page.getTotal() / page.getLimit())
				: (page.getTotal() / page.getLimit() + 1));
		page.setSuccess(true);
		map.put("page", page);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		try {
			map.put("money", MoneyUtil.fromFENtoYUAN(nf.format(counter.getMoney())));
		} catch (Exception e) {
			map.put("money", "--");
		}
		return map;
	}

	@Override
	public List<StudentPojo> getRefundApplyList(String ids) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_name,u.user_idnumber,cl.clas_name,GROUP_CONCAT(t.tech_name) AS tech_name,cl.tuition_fees,cd.pay_method,cd.txnAmt,cr.txnAmt AS tf_money,cd.txnTime,cr.insert_time,ca.category_name FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid=cd.cid AND cr.cd_id=cd.cd_id AND cd.is_del=0 INNER JOIN charge c ON c.cid=cd.cid AND c.cid=cr.cid AND c.org_id=cd.org_id AND c.is_del=0 INNER JOIN student_class sc ON sc.stu_class_id=cd.stu_class_id AND sc.is_del=0 AND sc.stud_id=cd.stud_id AND sc.clas_id=c.clas_id INNER JOIN edugate_base.student s ON sc.stud_id=s.stud_id AND s.stud_id=cd.stud_id AND s.org_id=cd.org_id AND s.org_id=c.org_id AND s.is_del=0 INNER JOIN edugate_base.org_user u ON u.user_id=s.user_id AND u.org_id=cd.org_id AND u.org_id=c.org_id AND u.org_id=s.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN classes cl ON cl.clas_id=sc.clas_id AND cl.clas_id=c.clas_id AND cl.org_id=cd.org_id AND cl.org_id=c.org_id AND cl.org_id=s.org_id AND cl.org_id=u.org_id AND cl.is_del=0 INNER JOIN category ca ON ca.category_id=cl.category_id AND ca.org_id=cd.org_id AND ca.org_id=c.org_id AND ca.org_id=s.org_id AND ca.org_id=u.org_id AND ca.org_id=cl.org_id AND ca.is_del=0 INNER JOIN `subject` su ON su.subject_id=cl.subject_id AND su.org_id=cd.org_id AND su.org_id=c.org_id AND su.org_id=s.org_id AND su.org_id=u.org_id AND su.org_id=cl.org_id AND su.org_id=ca.org_id AND su.is_del=0 INNER JOIN teacher_class tc ON tc.clas_id=c.clas_id AND tc.clas_id=cl.clas_id AND tc.is_del=0 INNER JOIN edugate_base.teacher t ON t.tech_id=tc.tech_id AND t.org_id=cd.org_id AND t.org_id=c.org_id AND t.org_id=s.org_id AND t.org_id=u.org_id AND t.org_id=cl.org_id AND t.org_id=ca.org_id AND t.org_id=su.org_id AND t.is_del=0 WHERE cr.is_show=1 AND cr.is_del=0  AND sc.stu_status=:stu_status AND (cr.txnType=:txnType1 OR cr.txnType=:txnType2) ";
		if (ids.contains(",")){
			sql += " AND sc.stu_class_id in ( :stu_clas_id ) ";
		}else if(StringUtils.isNotBlank(ids)) {
			sql += " AND sc.stu_class_id = :stu_clas_id ";
		}
		sql += " ORDER BY cr.insert_time DESC ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		if (ids.contains(",")){
			query.setParameterList("stu_clas_id", ids.split(","));
		}else if(StringUtils.isNotBlank(ids)) {
			query.setString("stu_clas_id", ids);
		}
		if (StringUtils.isNotBlank(ids)) {
		}
		query.setInteger("stu_status", Consts.STUD_STATUS_7REFUND_AUDITED);
		query.setString("txnType1", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		query.setString("txnType2", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		@SuppressWarnings("unchecked")
		List<StudentPojo> list = query.list();
		for (StudentPojo s : list) {
			try {
				s.setTf_money(MoneyUtil.fromFENtoYUAN(s.getTf_money()));
			} catch (Exception e) {
				s.setTf_money("--");
			}
			try {
				s.setTxnAmt(MoneyUtil.fromFENtoYUAN(s.getTxnAmt()));
			} catch (Exception e) {
				s.setTxnAmt(s.getTxnAmt()+"分");
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPojo> getRefundList(String stu_clas_ids) {
		if (StringUtils.isNotBlank(stu_clas_ids)) {
			Session session = this.factory.getCurrentSession();
			String sql = "SELECT c.cam_id,cr.cr_id,sc.stu_class_id,cd.cid,cd.cd_id,cd.stud_name,cd.txnAmt,cd.money,cr.txnAmt AS tf_money,cd.`status`,cd.online_pay,cd.offline_pay,sc.stu_status FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid = cd.cid AND cr.cd_id = cd.cd_id AND cd.is_del = 0 INNER JOIN charge c ON c.cid = cd.cid AND c.cid = cr.cid AND c.org_id = cd.org_id AND c.is_del = 0 LEFT JOIN student_class sc ON sc.stu_class_id = cd.stu_class_id AND sc.stud_id = cd.stud_id AND sc.clas_id=c.clas_id AND sc.is_del=0 WHERE cr.is_show = 1 AND cr.is_del=0 AND cr.txnType =:txnType AND sc.stu_class_id in ( :stu_class_id )";
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
			query.setParameterList("stu_class_id", stu_clas_ids.split(","));
			query.setString("txnType", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_PREVIEW);
			return query.list();
		} else {
			return new ArrayList<StudentPojo>();
		}
	}

	@Override
	public List<StudentPojo> getList(Integer org_id, String term_id, String category_id, String subject_id, String clas_type,
			String cam_id, String stu_status, String pay_method, String is_print, String keyword) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_id, c.clas_id, c.cam_id, c.class_week, c.class_unset_time, DATE_FORMAT(c.class_begin_time, '%H:%i') AS class_begin_time, DATE_FORMAT(c.class_over_time, '%H:%i') AS class_over_time, cd.cd_id, cd.cid, sc.stu_class_id, s.stud_name, u.user_idnumber, c.clas_name, GROUP_CONCAT(t.tech_name) AS tech_name, sc.stu_status, c.tuition_fees, cd.pay_method, cd.`status`, SUM(cd.txnAmt) AS ss_sum, sc.is_print, su.subject_name, ca.category_name, IFNULL(SUM(cr.txnAmt), 0) AS tf_sum FROM student_class sc INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON s.user_id = u.user_id AND s.org_id = u.org_id AND u.identity = 2 AND u.is_del = 0 INNER JOIN classes c ON c.clas_id = sc.clas_id AND c.cam_id = sc.cam_id AND c.org_id = s.org_id AND c.org_id = u.org_id AND c.is_del = 0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = c.subject_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = c.org_id AND su.is_del = 0 LEFT JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = c.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 LEFT JOIN charge_detail cd ON cd.cam_id = sc.cam_id AND cd.cam_id = c.cam_id AND cd.clas_id = sc.clas_id AND cd.clas_id = c.clas_id AND cd.org_id = s.org_id AND cd.org_id = u.org_id AND cd.org_id = c.org_id AND cd.stud_id = s.stud_id AND cd.stud_id = sc.stud_id AND cd.stu_class_id = sc.stu_class_id AND cd.org_id = ca.org_id AND cd.org_id = su.org_id AND cd.is_del = 0 LEFT JOIN plan p ON p.plan_id = c.plan_id AND p.term_id = c.term_id AND p.term_id = c.term_id AND p.org_id = s.org_id AND p.org_id = u.org_id AND p.org_id = c.org_id AND p.org_id = t.org_id AND p.org_id = cd.org_id AND p.org_id = ca.org_id AND p.org_id = su.org_id AND p.is_del = 0 LEFT JOIN charge_record cr ON cr.cid = cd.cid AND cr.cd_id = cd.cd_id AND cr.txnType = 'PREVIEW' WHERE sc.is_del = 0 ";
		if (StringUtils.isNotBlank(term_id)) {
			sql += " AND c.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			sql += " AND c.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			sql += " AND c.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(clas_type)) {
			sql += " AND c.clas_type=:clas_type ";
		}
		if (StringUtils.isNotBlank(cam_id)) {
			sql += " AND c.cam_id=:cam_id ";
		}
		if (StringUtils.isNotBlank(stu_status)) {
			sql += " AND sc.stu_status=:stu_status ";
		}
		if (StringUtils.isNotBlank(pay_method)) {
			sql += " AND cd.pay_method=:pay_method ";
		}
		if (StringUtils.isNotBlank(is_print)) {
			sql += "  AND sc.is_print=:is_print ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			sql += "  AND (s.stud_name LIKE :keyword OR c.clas_name LIKE :keyword)  ";
		}
		sql += " AND s.org_id=:org_id GROUP BY sc.stu_class_id ORDER BY sc.stu_class_id DESC LIMIT :pagenum,:pagesize ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		if (StringUtils.isNotBlank(term_id)) {
			query.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(category_id)) {
			query.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			query.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(clas_type)) {
			query.setInteger("clas_type", Integer.parseInt(clas_type));
		}
		if (StringUtils.isNotBlank(cam_id)) {
			query.setInteger("cam_id", Integer.parseInt(cam_id));
		}
		if (StringUtils.isNotBlank(stu_status)) {
			query.setInteger("stu_status", Integer.parseInt(stu_status));
		}
		if (StringUtils.isNotBlank(pay_method)) {
			query.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if (StringUtils.isNotBlank(is_print)) {
			query.setInteger("is_print", Integer.parseInt(is_print));
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setString("keyword", "%" + keyword + "%");
		}
		query.setInteger("org_id", org_id);
		@SuppressWarnings("unchecked")
		List<StudentPojo> list = query.list();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		for (StudentPojo s : list) {
			if (Constant.CHARGE_DETAIL_STATUS_REFUND_PREVIEW.equals(s.getStatus())) {
				try {
					s.setTf_money(MoneyUtil.fromFENtoYUAN(nf.format(s.getTf_sum())));
				} catch (Exception e) {
					s.setTf_money("--");
				}
			} else {
				s.setTf_money("--");
			}
			try {
				s.setMoney(MoneyUtil.fromFENtoYUAN(nf.format(s.getSs_sum())));
			} catch (Exception e) {
				s.setMoney("--");
			}
		}
		return list;
	}

	@Override
	public List<StudentPojo> getList(String ids) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_id, c.clas_id, c.cam_id, c.class_week, c.class_unset_time, DATE_FORMAT(c.class_begin_time, '%H:%i') AS class_begin_time, DATE_FORMAT(c.class_over_time, '%H:%i') AS class_over_time, c.org_id, cd.cd_id, cd.cid, sc.stu_class_id, s.stud_name, u.user_idnumber, c.clas_name, GROUP_CONCAT(t.tech_name) AS tech_name, sc.stu_status, c.tuition_fees, cd.pay_method, cd.`status`, SUM(cd.txnAmt) AS dtxnAmt, sc.is_print, su.subject_name, ca.category_name, IFNULL(SUM(cr.txnAmt), 0) AS dtf_money FROM student_class sc INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON s.user_id = u.user_id AND s.org_id = u.org_id AND u.identity = 2 AND u.is_del = 0 INNER JOIN classes c ON c.clas_id = sc.clas_id AND c.cam_id = sc.cam_id AND c.org_id = s.org_id AND c.org_id = u.org_id AND c.is_del = 0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = c.subject_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = c.org_id AND su.is_del = 0 LEFT JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = c.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 LEFT JOIN charge_detail cd ON cd.cam_id = sc.cam_id AND cd.cam_id = c.cam_id AND cd.clas_id = sc.clas_id AND cd.clas_id = c.clas_id AND cd.org_id = s.org_id AND cd.org_id = u.org_id AND cd.org_id = c.org_id AND cd.stud_id = s.stud_id AND cd.stud_id = sc.stud_id AND cd.stu_class_id = sc.stu_class_id AND cd.org_id = ca.org_id AND cd.org_id = su.org_id AND cd.is_del = 0 LEFT JOIN plan p ON p.plan_id = c.plan_id AND p.term_id = c.term_id AND p.term_id = c.term_id AND p.org_id = s.org_id AND p.org_id = u.org_id AND p.org_id = c.org_id AND p.org_id = t.org_id AND p.org_id = cd.org_id AND p.org_id = ca.org_id AND p.org_id = su.org_id AND p.is_del = 0 LEFT JOIN charge_record cr ON cr.cid = cd.cid AND cr.cd_id = cd.cd_id AND cr.txnType = 'PREVIEW' WHERE sc.is_del = 0 ";
		if(ids.contains(",")){
			sql += " AND sc.stu_class_id in ( :stu_class_ids ) ";
		}else if (StringUtils.isNotBlank(ids)){
			sql += " AND sc.stu_class_id = :stu_class_ids ";
		}
		sql += " GROUP BY sc.stu_class_id ORDER BY sc.stu_class_id DESC ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		if(ids.contains(",")){
			query.setParameterList("stu_class_ids", ids.split(","));
		}else if (StringUtils.isNotBlank(ids)){
			query.setString("stu_class_ids", ids);
		}
		@SuppressWarnings("unchecked")
		List<StudentPojo> list = query.list();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		for (StudentPojo s : list) {
			try {
				s.setTf_money(MoneyUtil.fromFENtoYUAN(nf.format(s.getDtf_money())));
			} catch (Exception e) {
				s.setTf_money("--");
			}
			try {
				s.setMoney(MoneyUtil.fromFENtoYUAN(nf.format(s.getDtxnAmt())));
			} catch (Exception e) {
				s.setMoney("--");
			}
		}
		return list;
	}

	/**
	 * @param txn_type 1缴费2退费
	 */
	@Override
	public Map<String, Object> getRecordList(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String pay_method, String txn_type, String keyword, Integer currentPageNum,
			Integer pageSize) {
		Paging<StudentPojo> page = new Paging<StudentPojo>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_name, u.user_idnumber, ca.category_name, su.subject_name, sc.stu_class_id, cl.clas_name, t.tech_name, cl.tuition_fees, cr.txnType, cd.pay_method, cd.cd_id, cr.txnAmt, cr.queryId, cr.txnTime FROM charge_record cr INNER JOIN charge_detail cd ON cd.cd_id = cr.cd_id AND cd.cid = cr.cid AND cd.is_del = 0 INNER JOIN charge c ON c.cid = cd.cid AND c.org_id = c.org_id AND c.cid = cr.cid AND c.cam_id = cd.cam_id AND c.clas_id = cd.clas_id AND c.type = 1 AND c.is_del = 0 INNER JOIN student_class sc ON sc.stud_id = cd.stud_id AND sc.stu_class_id = cd.stu_class_id AND sc.cam_id = cd.cam_id AND sc.cam_id = c.cam_id AND sc.clas_id = cd.clas_id AND sc.clas_id = c.clas_id AND sc.is_del = 0 INNER JOIN edugate_base.student s ON s.stud_id = cd.stud_id AND s.stud_id = sc.stud_id AND s.org_id = cd.org_id AND s.org_id = c.org_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.identity = 2 AND u.org_id = cd.org_id AND u.org_id = c.org_id AND u.org_id = s.org_id AND u.is_del = 0 INNER JOIN teacher_class tc ON tc.clas_id = cd.clas_id AND tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 INNER JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = cd.org_id AND t.org_id = c.org_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.is_del = 0 INNER JOIN classes cl ON cl.clas_id = cd.clas_id AND cl.clas_id = c.clas_id AND cl.clas_id = sc.clas_id AND cl.clas_id = tc.clas_id AND cl.org_id = cd.org_id AND cl.org_id = c.org_id AND cl.org_id = s.org_id AND cl.org_id = u.org_id AND cl.org_id = t.org_id AND cl.is_del = 0 INNER JOIN `subject` su ON su.subject_id = cl.subject_id AND su.category_id = cl.category_id AND su.org_id = cd.org_id AND su.org_id = c.org_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = t.org_id AND su.org_id = cl.org_id AND su.is_del = 0 INNER JOIN category ca ON ca.category_id = cl.category_id AND ca.category_id = su.category_id AND ca.org_id = cd.org_id AND ca.org_id = c.org_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = t.org_id AND ca.org_id = cl.org_id AND ca.org_id = su.org_id AND ca.is_del = 0 WHERE cr.is_del = 0 AND cr.is_show = 1 ";
		// 该方法不支持名称查询，因为在countSql语句中为了快速查询没有关联其他表，如果需要改造，需要把countSql语句先关联上数据表
		String countSql = "  SELECT COUNT(1) AS num, IFNULL(SUM(money), 0) AS money FROM ( SELECT IFNULL(cd.txnAmt, 0) AS money FROM charge_record cr INNER JOIN charge_detail cd ON cd.cd_id = cr.cd_id AND cd.cid = cr.cid AND cd.is_del = 0 INNER JOIN charge c ON c.cid = cd.cid AND c.org_id = c.org_id AND c.cid = cr.cid AND c.cam_id = cd.cam_id AND c.clas_id = cd.clas_id AND c.type = 1 AND c.is_del = 0 INNER JOIN student_class sc ON sc.stud_id = cd.stud_id AND sc.stu_class_id = cd.stu_class_id AND sc.cam_id = cd.cam_id AND sc.cam_id = c.cam_id AND sc.clas_id = cd.clas_id AND sc.clas_id = c.clas_id AND sc.is_del = 0 INNER JOIN edugate_base.student s ON s.stud_id = cd.stud_id AND s.stud_id = sc.stud_id AND s.org_id = cd.org_id AND s.org_id = c.org_id AND s.is_del = 0 INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.identity = 2 AND u.org_id = cd.org_id AND u.org_id = c.org_id AND u.org_id = s.org_id AND u.is_del = 0 INNER JOIN teacher_class tc ON tc.clas_id = cd.clas_id AND tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 INNER JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = cd.org_id AND t.org_id = c.org_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.is_del = 0 INNER JOIN classes cl ON cl.clas_id = cd.clas_id AND cl.clas_id = c.clas_id AND cl.clas_id = sc.clas_id AND cl.clas_id = tc.clas_id AND cl.org_id = cd.org_id AND cl.org_id = c.org_id AND cl.org_id = s.org_id AND cl.org_id = u.org_id AND cl.org_id = t.org_id AND cl.is_del = 0 INNER JOIN `subject` su ON su.subject_id = cl.subject_id AND su.category_id = cl.category_id AND su.org_id = cd.org_id AND su.org_id = c.org_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = t.org_id AND su.org_id = cl.org_id AND su.is_del = 0 INNER JOIN category ca ON ca.category_id = cl.category_id AND ca.category_id = su.category_id AND ca.org_id = cd.org_id AND ca.org_id = c.org_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = t.org_id AND ca.org_id = cl.org_id AND ca.org_id = su.org_id AND ca.is_del = 0 WHERE cr.is_del = 0 AND cr.is_show = 1 ";
		if (StringUtils.isNotBlank(starttime)) {
			sql += " AND cr.txnTime>=:start_time ";
			countSql += " AND cr.txnTime>=:start_time ";
		}
		if (StringUtils.isNotBlank(endtime)) {
			sql += " AND cr.txnTime<=:end_time ";
			countSql += " AND cr.txnTime<=:end_time ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			sql += "  AND cl.category_id=:category_id ";
			countSql += "  AND cl.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			sql += "  AND cl.subject_id=:subject_id ";
			countSql += "  AND cl.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(class_type)) {
			sql += " AND cl.clas_type=:clas_type ";
			countSql += " AND cl.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				sql += " AND cl.cam_id in ( :cam_id ) ";
				countSql += " AND cl.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				sql += " AND cl.cam_id = :cam_id ";
				countSql += " AND cl.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			sql += " AND cl.term_id=:term_id ";
			countSql += " AND cl.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(pay_method)) {
			sql += " AND cd.pay_method=:pay_method ";
			countSql += " AND cd.pay_method=:pay_method ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			sql += " AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
			countSql += "  AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
		}
		sql += " AND cd.org_id=:org_id AND cr.txnType in (:txnType) ORDER BY cr.insert_time DESC LIMIT :pagenum,:pagesize ";
		countSql+=" AND cd.org_id=:org_id AND cr.txnType in (:txnType) ) t";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		countQuery.setResultTransformer(Transformers.aliasToBean(Counter.class));
		if (StringUtils.isNotBlank(starttime)) {
			try {
				query.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
				countQuery.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -100);
				query.setDate("start_time", cal.getTime());
				countQuery.setDate("start_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(endtime)) {
			try {
				query.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
				countQuery.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 100);
				query.setDate("end_time", cal.getTime());
				countQuery.setDate("end_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(category_id)) {
			query.setInteger("category_id", Integer.parseInt(category_id));
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			query.setInteger("subject_id", Integer.parseInt(subject_id));
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(class_type)) {
			query.setInteger("clas_type", Integer.parseInt(class_type));
			countQuery.setInteger("clas_type", Integer.parseInt(class_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				query.setParameterList("cam_id", cam_id.split(","));
				countQuery.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				query.setInteger("cam_id", Integer.parseInt(cam_id));
				countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			query.setInteger("term_id", Integer.parseInt(term_id));
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(pay_method)) {
			query.setInteger("pay_method", Integer.parseInt(pay_method));
			countQuery.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setString("keyword", "%" + keyword + "%");
			countQuery.setString("keyword", "%" + keyword + "%");
		}
		// txn_type 1缴费2退费
		String[] txnType = null;
		if(txn_type.equals("1")){
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_PAY,Constant.TXNTYPE_UNIONPAY_PAY};
		}else if(txn_type.equals("2")){
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE, Constant.TXNTYPE_UNIONPAY_REFUND };
		}else{
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_PAY, Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE,
					Constant.TXNTYPE_UNIONPAY_PAY, Constant.TXNTYPE_UNIONPAY_REFUND };
		}
		query.setParameterList("txnType", txnType);
		countQuery.setParameterList("txnType", txnType);
		query.setInteger("org_id", org_id);
		countQuery.setInteger("org_id", org_id);
		query.setInteger("pagenum", (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		Counter counter = (Counter) countQuery.list().get(0);
		page.setTotal(counter.getNum().longValue());
		page.setSize(page.getTotal() % page.getLimit() == 0 ? (page.getTotal() / page.getLimit()): (page.getTotal() / page.getLimit() + 1));
		if(counter.getNum().intValue()!=0){
			@SuppressWarnings("unchecked")
			List<StudentPojo> list = query.list();
			for (StudentPojo sp : list) {
				try {
					sp.setMoney(MoneyUtil.fromFENtoYUAN(sp.getTxnAmt()));
				} catch (Exception e) {
					sp.setMoney("--");
				}
				sp.setTxnMethod(Consts.TXN_TYPE_MAP.get(sp.getTxnType()));
			}
			page.setData(list);
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			try {
				map.put("money", MoneyUtil.fromFENtoYUAN(nf.format(counter.getMoney())));
			} catch (Exception e) {
				map.put("money", "--");
			}
		}else{
			page.setData(new ArrayList<StudentPojo>());
			map.put("money", "--");
		}
		page.setSuccess(true);
		map.put("page", page);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPojo> getRecordList(String ids) {
		if (StringUtils.isBlank(ids)) {
			return new ArrayList<StudentPojo>();
		}
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.stud_name,u.user_idnumber,ca.category_name,su.subject_name,cl.clas_name,t.tech_name,cl.tuition_fees,cr.txnType,cd.pay_method,cr.txnAmt,cr.queryId,cr.txnTime FROM charge_record cr INNER JOIN charge_detail cd ON cd.cd_id=cr.cd_id AND cd.cid=cr.cid AND cd.is_del=0  INNER JOIN charge c ON c.cid=cd.cid AND c.org_id=c.org_id AND c.cid=cr.cid AND c.cam_id=cd.cam_id AND c.clas_id=cd.clas_id AND c.type=1 AND c.is_del=0 INNER JOIN student_class sc ON sc.stud_id=cd.stud_id AND sc.stu_class_id=cd.stu_class_id AND sc.cam_id=cd.cam_id AND sc.cam_id=c.cam_id AND sc.clas_id=cd.clas_id AND sc.clas_id=c.clas_id AND sc.is_del=0  INNER JOIN edugate_base.student s ON s.stud_id=cd.stud_id AND s.stud_id=sc.stud_id AND s.org_id=cd.org_id AND s.org_id=c.org_id AND s.is_del=0  INNER JOIN edugate_base.org_user u ON u.user_id=s.user_id AND u.identity=2 AND u.org_id=cd.org_id AND u.org_id=c.org_id AND u.org_id=s.org_id AND u.is_del=0  INNER JOIN teacher_class tc ON tc.clas_id=cd.clas_id AND tc.clas_id=c.clas_id AND tc.clas_id=sc.clas_id AND tc.is_del=0  INNER JOIN edugate_base.teacher t ON t.tech_id=tc.tech_id AND t.org_id=cd.org_id AND t.org_id=c.org_id AND t.org_id=s.org_id AND t.org_id=u.org_id AND t.is_del=0  INNER JOIN classes cl ON cl.clas_id=cd.clas_id AND cl.clas_id=c.clas_id AND cl.clas_id=sc.clas_id AND cl.clas_id=tc.clas_id AND cl.org_id=cd.org_id AND cl.org_id=c.org_id AND cl.org_id=s.org_id AND cl.org_id=u.org_id AND cl.org_id=t.org_id AND cl.is_del=0  INNER JOIN `subject` su ON su.subject_id=cl.subject_id AND su.category_id=cl.category_id AND su.org_id=cd.org_id AND su.org_id=c.org_id AND su.org_id=s.org_id AND su.org_id=u.org_id AND su.org_id=t.org_id AND su.org_id=cl.org_id AND su.is_del=0 INNER JOIN category ca ON ca.category_id=cl.category_id AND ca.category_id=su.category_id AND ca.org_id=cd.org_id AND ca.org_id=c.org_id AND ca.org_id=s.org_id AND ca.org_id=u.org_id AND ca.org_id=t.org_id AND ca.org_id=cl.org_id AND ca.org_id=su.org_id AND ca.is_del=0 WHERE cr.is_del=0 AND cr.is_show=1 AND cr.txnType IN ( :txnType ) AND sc.stu_class_id IN ( :ids ) ORDER BY cr.insert_time DESC ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentPojo.class));
		String[] txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_PAY, Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE,
				Constant.TXNTYPE_UNIONPAY_PAY, Constant.TXNTYPE_UNIONPAY_REFUND };
		query.setParameterList("txnType", txnType);
		query.setParameterList("ids", ids.split(","));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentByParentUserId(String parent_user_id) {
		if (StringUtils.isBlank(parent_user_id)) {
			return new ArrayList<Student>();
		}
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.* FROM edugate_base.student s INNER JOIN edugate_base.student2parent sp ON s.stud_id=sp.stud_id AND sp.is_del=0 INNER JOIN edugate_base.parent p ON p.parent_id=sp.parent_id AND p.org_id=s.org_id AND p.is_del=0 WHERE s.is_del=0 AND p.user_id=:user_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		query.setInteger("user_id", Integer.parseInt(parent_user_id));
		return query.list();
	}

	/*
	 * 根据班级ID,学员IDS以及org_id查询班级和学员班级关系信息 (non-Javadoc)
	 * 
	 * @see sng.dao.StudentDao#queryStuAndClassInfo(sng.pojo.ParamObj)
	 */
	@Override
	public List<Clas2Student> queryStuAndClassInfo(ParamObj obj) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select c.clas_id,c.org_id,c.term_id,c.plan_id,c.category_id,c.subject_id,c.cam_id,c.class_week,");
		sql.append("DATE_FORMAT(c.class_begin_time,'%H:%i') as class_begin_time,");
		sql.append("DATE_FORMAT(c.class_over_time,'%H:%i') as class_over_time,c.tuition_fees,");
		sql.append("s.stud_id,s.stu_status,s.stu_class_id,es.stud_name,c.class_unset_time,");
		sql.append("GROUP_CONCAT(DISTINCT(t.tech_name)) as tech_name,GROUP_CONCAT(DISTINCT(t.tech_id)) as tech_id,cd.cd_id,");
		sql.append(" s.quota_hold,s.pay_expired_time,s.is_print,s.voucher_no,s.creater,c.clas_name,ou.user_idnumber,cy.category_name,sj.subject_name,s.insert_time");
		sql.append(" from newsng.classes c");
		sql.append(" left join newsng.student_class s on c.clas_id = s.clas_id and c.cam_id = s.cam_id and s.is_del=0");
		sql.append(" left join newsng.charge_detail cd on cd.stu_class_id = s.stu_class_id and cd.cam_id=s.cam_id and ");
		sql.append(" cd.clas_id=s.clas_id and cd.stud_id=s.stud_id and cd.is_del=0");
		sql.append(" left join edugate_base.student es on s.stud_id = es.stud_id and es.is_del=0");
		sql.append(" left join edugate_base.org_user ou on es.user_id = ou.user_id and ou.is_del=0");
		sql.append(" left join newsng.teacher_class tc on c.clas_id = tc.clas_id and tc.is_del=0");
		sql.append(" left join edugate_base.teacher t on t.tech_id = tc.tech_id and t.is_del=0");
		sql.append(" left join newsng.category cy on c.category_id = cy.category_id and cy.is_del=0");
		sql.append(" left join newsng.`subject` sj on c.subject_id = sj.subject_id and sj.is_del=0");
		sql.append(" where c.is_del=0 ");
		if (obj.getOrg_id() != null) {
			sql.append(" and c.org_id=?");
			paramList.add(obj.getOrg_id());
		}


		if (obj.getClas_id() != null) {
			sql.append(" and c.clas_id=?");
			paramList.add(obj.getClas_id());
		}

		//前台传的stud_id其实是stu_clas_id
		if(StringUtils.isNotBlank(obj.getStud_id())){
			sql.append(" and s.stud_id =?");
			paramList.add(Integer.parseInt(obj.getStud_id()));
		}

		if(obj.getSut_clas_id()!=null){
			sql.append(" and s.stu_class_id =?");
			paramList.add(obj.getSut_clas_id());
		}

//		if (StringUtils.isNotBlank(obj.getStud_id())) {
//			String[] strs = obj.getStud_id().split(",");
//			if (strs != null && strs.length > 0) {
//				sql.append(" and s.stud_id in(");
//				for (int i = 0; i < strs.length; i++) {
//					if (i == strs.length - 1) {
//						sql.append("?");
//					} else {
//						sql.append("?,");
//					}
//					paramList.add(Integer.valueOf(strs[i]));
//				}
//				sql.append(")");
//			}
//		}
		sql.append(" group by c.clas_id,es.stud_id");
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		@SuppressWarnings("unchecked")
		List<Clas2Student> list = query.list();
		return list;
	}

	/*
	 * 根据不同学员不同状态获取班级信息 (non-Javadoc)
	 * 
	 * @see sng.dao.StudentDao#queryMoveClassListInfo(sng.pojo.Clas2Student)
	 */
	@Override
	public List<Clas2Student> queryMoveClassListInfo(Clas2Student cs) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select c.clas_id,c.clas_name,c.org_id,c.term_id,c.plan_id,c.category_id,c.subject_id,");
		sql.append("c.classroom_id,c.size,c.age_range,");
		sql.append("DATE_FORMAT(c.start_birthday,'%Y-%m-%d') as start_birthday,");
		sql.append("DATE_FORMAT(c.end_birthday,'%Y-%m-%d') as end_birthday,");
		sql.append("DATE_FORMAT(c.class_start_date,'%Y-%m-%d') as class_start_date,c.class_week,");
		sql.append("DATE_FORMAT(c.class_begin_time,'%H:%i') as class_begin_time,");
		sql.append("DATE_FORMAT(c.class_over_time,'%H:%i') as class_over_time,c.tuition_fees,");
		sql.append("c.cam_id,nc.cam_name,o.user_mobile,sc.stu_status,");
		sql.append("GROUP_CONCAT(DISTINCT(t.tech_id)) as tech_id,");
		sql.append("GROUP_CONCAT(DISTINCT(t.tech_name)) as tech_name,");
		sql.append("GROUP_CONCAT(DISTINCT(sc.stud_id)) as stud_ids,");
		sql.append("cy.category_name,sj.subject_name");
		sql.append("  from newsng.classes c");
		sql.append(" left join newsng.student_class sc on c.clas_id = sc.clas_id and sc.is_del=0");
		sql.append(" left join newsng.teacher_class tc on tc.clas_id = c.clas_id and tc.is_del=0 ");
		sql.append(" left join edugate_base.teacher t on t.tech_id = tc.tech_id and t.is_del=0");
		sql.append(" left join edugate_base.org_user o on t.user_id = o.user_id and o.is_del=0");
		sql.append(" left join newsng.campus nc on c.cam_id = nc.cam_id and nc.is_del=0");
		sql.append(" left join newsng.category cy on c.category_id = cy.category_id and cy.is_del=0");
		sql.append(" left join newsng.`subject` sj on c.subject_id = sj.subject_id and sj.is_del=0");
		sql.append(" where c.is_del = 0");

		//相同机构下
		if(cs.getOrg_id()!=null){
            sql.append(" and c.org_id=?");
			paramList.add(cs.getOrg_id());
		}

		// 转班的要在同一学期内转班
		if (cs.getTerm_id() != null) {
			sql.append(" and c.term_id=?");
			paramList.add(cs.getTerm_id());
		}

		// 老生代缴费 缴费已完成 都要同类目
		if (cs.getCategory_id() != null) {
			sql.append(" and c.category_id=?");
			paramList.add(cs.getCategory_id());
		}
		// 老生代缴费 缴费已完成 都要同科目
		if (cs.getSubject_id() != null) {
			sql.append(" and c.subject_id=?");
			paramList.add(cs.getSubject_id());
		}

//		//班级id,不能包含自己的班级，因为转班到自己所在的班级就没有意义了，所以不能包含自己的班级
		if(cs.getClas_id()!=null){
           sql.append(" and c.clas_id <> ?");
			paramList.add(cs.getClas_id());
		}

		// 缴费已完成
		if (Consts.STUD_STATUS_5PAID == cs.getStu_status()) {
			// 当类型为缴费已完成的学员，如果转班，前提是老师和班级关系存在则应该有下面sql
			sql.append(" and tc.clas_id is not null");
			// 同学费标准
			if (StringUtils.isNotBlank(cs.getTuition_fees())) {
				sql.append(" and c.tuition_fees=?");
				paramList.add(cs.getTuition_fees());
			}
			// 相同教师
			if (StringUtils.isNotBlank(cs.getTech_id())) {
				String techId = cs.getTech_id();
				String[] techIds = techId.split(",");
				if (techIds != null && techIds.length > 0) {
					sql.append(" and tc.tech_id in (");
					for (int i = 0; i < techIds.length; i++) {
						if (i == techIds.length - 1) {
							sql.append("?");
						} else {
							sql.append("?,");
						}
						paramList.add(Integer.valueOf(techIds[i]));
					}
					sql.append(")");

				}

			}
		}

		if (StringUtils.isNotBlank(cs.getQueryContext())) {
			sql.append(" and (");
			sql.append(" t.tech_name like ?");
			paramList.add("%" + cs.getQueryContext() + "%");
			sql.append(" or o.user_mobile like ?");
			paramList.add("%" + cs.getQueryContext() + "%");
			sql.append(" or c.clas_name like ?");
			paramList.add("%" + cs.getQueryContext() + "%");
			sql.append(")");
		}
		sql.append(" group by c.clas_id");

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}

		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		@SuppressWarnings("unchecked")
		List<Clas2Student> list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)学员管理--学员信息中点击手机按钮
	 * 
	 * @see sng.dao.StudentDao#queryStuManageTelInfo(sng.pojo.ParamObj)
	 */
	@Override
	public List<Clas2Student> queryStuManageTelInfo(ParamObj obj) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append(
				"select c.clas_id,sc.stu_class_id,s.stud_id,s.stud_name,sp.is_main,sp.relation,p.parent_name,ou.user_mobile");
		sql.append(" from newsng.classes c");
		sql.append(" left join newsng.student_class sc on c.clas_id = sc.clas_id and sc.is_del=0");
		sql.append(" left join edugate_base.student s on sc.stud_id=s.stud_id and sc.is_del=0");
		sql.append(" left join edugate_base.student2parent sp on s.stud_id = sp.stud_id and sp.is_del=0");
		sql.append(" left join edugate_base.parent p on sp.parent_id=p.parent_id and p.is_del=0");
		sql.append(" left join edugate_base.org_user ou on p.user_id = ou.user_id and ou.is_del=0");
		sql.append(" where c.is_del=0 and sc.clas_id is not null");
		if (obj.getOrg_id() != null) {
			sql.append(" and c.org_id=?");
			paramList.add(obj.getOrg_id());
		}
//		if (obj.getStud_id() != null) {
//			sql.append(" and s.stud_id=?");
//			paramList.add(obj.getStud_id());
//		}
		// 前端真实传的是stu_class_id不是stud_id
		if (obj.getStud_id() != null) {
			sql.append(" and sc.stu_class_id=?");
			paramList.add(obj.getStud_id());
		}

		if (obj.getClas_id() != null) {
			sql.append(" and c.clas_id=?");
			paramList.add(obj.getClas_id());
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(Clas2Student.class));
		@SuppressWarnings("unchecked")
		List<Clas2Student> list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)学员管理--根据学院班级关系ID批量更新学院班级关系表信息
	 * 
	 * @see sng.dao.StudentDao#updateStuClassTabInfoByStuClasId(java.util.List)
	 */
	@Override
	public int updateStuClassTabInfoByStuClasId(List<StudentPojo> studentPojos) {
		int count = 0;
		if (studentPojos != null && !studentPojos.isEmpty()) {
			StringBuffer sql = new StringBuffer();
			List<Object> paramList = new ArrayList<>();
			sql.append("update student_class set is_del=1 where stu_class_id in (");
			for (int i = 0; i < studentPojos.size(); i++) {
				Integer stu_class_id = studentPojos.get(i).getStu_class_id();
				if (stu_class_id == null) {
					continue;
				}
				if (i == studentPojos.size() - 1) {
					sql.append("?");
					paramList.add(stu_class_id);
				} else {
					sql.append("?,");
					paramList.add(stu_class_id);
				}
			}
			sql.append(")");

			Session session = this.factory.getCurrentSession();
			Query query = session.createSQLQuery(sql.toString());
			for (int i = 0; i < paramList.size(); i++) {
				query.setParameter(i, paramList.get(i));
			}
			count = query.executeUpdate();
		}
		return count;
	}

	@Override
	public List<Integer> getidList(Integer org_id, String term_id, String category_id, String subject_id, String clas_type,
			String cam_id, String stu_status, String pay_method, String is_print, String keyword) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT sc.stu_class_id FROM  student_class sc INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id and s.is_del=0 INNER JOIN edugate_base.org_user u ON s.user_id=u.user_id AND s.org_id=u.org_id AND u.identity=2 AND u.is_del=0 INNER JOIN classes c ON c.clas_id=sc.clas_id AND c.cam_id=sc.cam_id AND c.org_id=s.org_id AND c.org_id=u.org_id AND c.is_del=0 INNER JOIN category ca ON ca.category_id = c.category_id AND ca.org_id = s.org_id AND ca.org_id = u.org_id AND ca.org_id = c.org_id AND ca.is_del = 0 INNER JOIN `subject` su ON su.subject_id = c.subject_id AND su.org_id = s.org_id AND su.org_id = u.org_id AND su.org_id = c.org_id AND su.is_del = 0 LEFT JOIN teacher_class tc ON tc.clas_id = c.clas_id AND tc.clas_id = sc.clas_id AND tc.is_del = 0 LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.org_id = s.org_id AND t.org_id = u.org_id AND t.org_id = c.org_id AND t.org_id = ca.org_id AND t.org_id = su.org_id AND t.is_del = 0 LEFT JOIN charge_detail cd ON cd.cam_id = sc.cam_id AND cd.cam_id = c.cam_id AND cd.clas_id = sc.clas_id AND cd.clas_id = c.clas_id  AND cd.org_id = s.org_id AND cd.org_id = u.org_id AND cd.org_id = c.org_id  AND cd.org_id = ca.org_id AND cd.org_id = su.org_id AND cd.stud_id = s.stud_id AND cd.stud_id = sc.stud_id AND cd.stu_class_id = sc.stu_class_id AND cd.is_del = 0 WHERE sc.is_del=0 ";
		if (StringUtils.isNotBlank(term_id)) {
			sql += " AND c.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			sql += " AND c.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			sql += " AND c.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(clas_type)) {
			sql += " AND c.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				sql += " AND c.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				sql += " AND c.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(stu_status)) {
			sql += " AND sc.stu_status=:stu_status ";
		}
		if (StringUtils.isNotBlank(pay_method)) {
			sql += " AND cd.pay_method=:pay_method ";
		}
		if (StringUtils.isNotBlank(is_print)) {
			sql += "  AND sc.is_print=:is_print ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			sql += "  AND (s.stud_name LIKE :keyword OR c.clas_name LIKE :keyword)  ";
		}
		sql += " AND s.org_id=:org_id GROUP BY sc.stu_class_id ";
		Query query = session.createSQLQuery(sql);
		if (StringUtils.isNotBlank(term_id)) {
			query.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(category_id)) {
			query.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			query.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(clas_type)) {
			query.setInteger("clas_type", Integer.parseInt(clas_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				query.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				query.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(stu_status)) {
			query.setInteger("stu_status", Integer.parseInt(stu_status));
		}
		if (StringUtils.isNotBlank(pay_method)) {
			query.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if (StringUtils.isNotBlank(is_print)) {
			query.setInteger("is_print", Integer.parseInt(is_print));
		}
		if (StringUtils.isNotBlank(keyword)) {
			query.setString("keyword", "%" + keyword + "%");
		}
		query.setInteger("org_id", org_id);
		@SuppressWarnings("unchecked")
		List<Integer> list = query.list();
		return list;
	}

	@Override
	public List getRefundApplyAllPageIds(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String keyword) {
		
		
		Session session = this.factory.getCurrentSession();
		// 该方法不支持名称查询，因为在countSql语句中为了快速查询没有关联category、subject、teacher、teacher_class、学生的org_user表，如果需要改造，需要把countSql语句先关联上数据表
		String countSql = " SELECT cd.cd_id,sc.stu_class_id,GROUP_CONCAT(DISTINCT(s.stud_name)) AS stud_name  FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid=cd.cid AND cr.cd_id=cd.cd_id AND cd.is_del=0 INNER JOIN charge c ON c.cid=cd.cid AND c.cid=cr.cid AND c.org_id=cd.org_id AND c.is_del=0 INNER JOIN student_class sc ON sc.stu_class_id=cd.stu_class_id AND sc.stud_id=cd.stud_id AND sc.clas_id=c.clas_id and sc.is_del=0 INNER JOIN edugate_base.student s ON sc.stud_id=s.stud_id AND s.stud_id=cd.stud_id AND s.org_id=cd.org_id AND s.org_id=c.org_id AND s.is_del=0 INNER JOIN classes cl ON cl.clas_id=sc.clas_id AND cl.clas_id=c.clas_id AND cl.org_id=cd.org_id AND cl.org_id=c.org_id AND cl.org_id=s.org_id AND cl.is_del=0 WHERE cr.is_show=1 AND cr.is_del=0 AND sc.stu_status=:stu_status AND ( cr.txnType=:txnType1 OR cr.txnType=:txnType2 ) ";
		if (StringUtils.isNotBlank(starttime)) {
			
			countSql += " AND cd.txnTime>=:start_time ";
		}
		if (StringUtils.isNotBlank(endtime)) {
			
			countSql += " AND cd.txnTime<=:end_time ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			
			countSql += "  AND cl.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			
			countSql += "  AND cl.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(class_type)) {
			
			countSql += " AND cl.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				
				countSql += " AND cl.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				
				countSql += " AND cl.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			
			countSql += " AND cl.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			countSql += "  AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
		}
		countSql += " AND cd.org_id=:org_id ";
		Query countQuery = session.createSQLQuery(countSql);
	
		countQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNotBlank(starttime)) {
			try {
				countQuery.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -100);
				countQuery.setDate("start_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(endtime)) {
			try {
				countQuery.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 100);
				countQuery.setDate("end_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(category_id)) {
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(class_type)) {
			countQuery.setInteger("clas_type", Integer.parseInt(class_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				countQuery.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(keyword)) {
			countQuery.setString("keyword", "%" + keyword + "%");
		}
		
		countQuery.setInteger("stu_status", Consts.STUD_STATUS_7REFUND_AUDITED);
		
		countQuery.setString("txnType1", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		
		countQuery.setString("txnType2", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		countQuery.setInteger("org_id", org_id);
		List list = countQuery.list();
		
		return list;
	}

	@Override
	public Student getById(Integer stu_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT s.* FROM edugate_base.student s WHERE s.stud_id=:stud_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Student.class));
		query.setInteger("stud_id", stu_id);
		return (Student) query.uniqueResult();
	}

	@Override
	public List getRecordIdsList(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String pay_method, String txn_type, String keyword,
			Integer currentPageNum, Integer pageSize) {
		
		Session session = this.factory.getCurrentSession();
		// 该方法不支持名称查询，因为在countSql语句中为了快速查询没有关联其他表，如果需要改造，需要把countSql语句先关联上数据表
		String countSql = " SELECT cd.cd_id,cd.stu_class_id,GROUP_CONCAT(DISTINCT(s.stud_name)) AS stud_name FROM charge_record cr INNER JOIN charge_detail cd ON cd.cd_id=cr.cd_id AND cd.cid=cr.cid AND cd.is_del=0 INNER JOIN charge c ON c.cid=cd.cid AND c.org_id=c.org_id AND c.cid=cr.cid AND c.cam_id=cd.cam_id AND c.clas_id=cd.clas_id AND c.type=1 AND c.is_del=0 INNER JOIN classes cl ON cl.clas_id=cd.clas_id AND cl.clas_id=c.clas_id AND cl.org_id=cd.org_id AND cl.org_id=c.org_id AND cl.is_del=0 INNER JOIN edugate_base.student s ON s.stud_id=cd.stud_id AND s.org_id=cd.org_id AND s.org_id=c.org_id AND s.org_id=cl.org_id AND s.is_del=0 WHERE cr.is_del=0 AND cr.is_show=1 AND cr.txnType IN ( :txnType ) ";
		if (StringUtils.isNotBlank(starttime)) {
			
			countSql += " AND cr.txnTime>=:start_time ";
		}
		if (StringUtils.isNotBlank(endtime)) {
			
			countSql += " AND cr.txnTime<=:end_time ";
		}
		if (StringUtils.isNotBlank(category_id)) {
			
			countSql += "  AND cl.category_id=:category_id ";
		}
		if (StringUtils.isNotBlank(subject_id)) {
			
			countSql += "  AND cl.subject_id=:subject_id ";
		}
		if (StringUtils.isNotBlank(class_type)) {
			
			countSql += " AND cl.clas_type=:clas_type ";
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				
				countSql += " AND cl.cam_id in ( :cam_id ) ";
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				
				countSql += " AND cl.cam_id = :cam_id ";
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			
			countSql += " AND cl.term_id=:term_id ";
		}
		if (StringUtils.isNotBlank(pay_method)) {
			
			countSql += " AND cd.pay_method=:pay_method ";
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			countSql += "  AND ( s.stud_name LIKE :keyword OR cl.clas_name LIKE :keyword ) ";
		}
		countSql += " AND cd.org_id=:org_id ";

		Query countQuery = session.createSQLQuery(countSql);
	
		countQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNotBlank(starttime)) {
			try {
				
				countQuery.setDate("start_time", Constant.sdf.parse(starttime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -100);
				
				countQuery.setDate("start_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(endtime)) {
			try {
				
				countQuery.setDate("end_time", Constant.sdf.parse(endtime + " 00:00:00"));
			} catch (Exception e) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 100);
				
				countQuery.setDate("end_time", cal.getTime());
			}
		}
		if (StringUtils.isNotBlank(category_id)) {
			
			countQuery.setInteger("category_id", Integer.parseInt(category_id));
		}
		if (StringUtils.isNotBlank(subject_id)) {
		
			countQuery.setInteger("subject_id", Integer.parseInt(subject_id));
		}
		if (StringUtils.isNotBlank(class_type)) {
			
			countQuery.setInteger("clas_type", Integer.parseInt(class_type));
		}
		// 校区id不会为空（三选一：Constant.ALL_CAMPUS，单个id，多个逗号连接的id）
		if (StringUtils.isNotBlank(cam_id)) {
			if (cam_id.indexOf(",") > 0) {
				
				countQuery.setParameterList("cam_id", cam_id.split(","));
			} else if (!Constant.ALL_CAMPUS.equals(cam_id)) {
				
				countQuery.setInteger("cam_id", Integer.parseInt(cam_id));
			}
		}
		if (StringUtils.isNotBlank(term_id)) {
			
			countQuery.setInteger("term_id", Integer.parseInt(term_id));
		}
		if (StringUtils.isNotBlank(pay_method)) {
			
			countQuery.setInteger("pay_method", Integer.parseInt(pay_method));
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			countQuery.setString("keyword", "%" + keyword + "%");
		}
		// txn_type 1缴费2退费
		String[] txnType = null;
		if(txn_type.equals("1")){
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_PAY,Constant.TXNTYPE_UNIONPAY_PAY};
		}else if(txn_type.equals("2")){
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE, Constant.TXNTYPE_UNIONPAY_REFUND };
		}else{
			txnType = new String[] { Constant.TXNTYPE_SJWY_OFFLINE_PAY, Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE,
					Constant.TXNTYPE_UNIONPAY_PAY, Constant.TXNTYPE_UNIONPAY_REFUND };
		}
		countQuery.setParameterList("txnType", txnType);
		countQuery.setInteger("org_id", org_id);
		List list = countQuery.list();
		
		return list;
	}


}
