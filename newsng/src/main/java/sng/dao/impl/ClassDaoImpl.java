package sng.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import sng.constant.Consts;
import sng.dao.ClassDao;
import sng.entity.ChargeDetail;
import sng.entity.Classes;
import sng.pojo.ClassToTeacher;
import sng.pojo.StudentRoster;
import sng.util.CommonUtils;
import sng.util.Constant;
import sng.util.MoneyUtil;
import sng.util.Paging;

/***
 * 
 *  @Company sjwy
 *  @Title: ClassDaoImpl.java 
 *  @Description: 班级管理 dao层实现
 *	@author: shy
 *  @date: 2018年10月29日 下午2:23:31
 */
@Repository
public class ClassDaoImpl extends BaseDaoImpl<Classes> implements ClassDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Classes> getList(Classes classes) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT c.*,GROUP_CONCAT(t.tech_id) AS teacher_ids FROM classes c INNER JOIN teacher_class tc ON tc.clas_id=c.clas_id AND tc.is_del=0 INNER JOIN edugate_base.teacher t ON t.tech_id=tc.tech_id AND t.org_id=c.org_id AND t.is_del=0 WHERE c.is_del=0 ";
		if(null!=classes.getClas_id()){
			sql += " AND c.clas_id=:clas_id ";
		}
		if(null!=classes.getSubject_id()){
			sql += " AND c.subject_id=:subject_id ";
		}
		if(null!=classes.getCategory_id()){
			sql += " AND c.category_id=:category_id ";
		}
		if(StringUtils.isNotBlank(classes.getTuition_fees())){
			sql += " AND c.tuition_fees=:tuition_fees ";
		}
		sql += " GROUP BY c.clas_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeDetail.class));
		if(null!=classes.getClas_id()){
			query.setInteger("clas_id", classes.getClas_id());
		}
		if(null!=classes.getSubject_id()){
			query.setInteger("subject_id", classes.getSubject_id());
		}
		if(null!=classes.getCategory_id()){
			query.setInteger("category_id", classes.getCategory_id());
		}
		if(StringUtils.isNotBlank(classes.getTuition_fees())){
			query.setString("tuition_fees", classes.getTuition_fees());
		}
		return query.list();
	}

	/***
	 * 班级列表
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Paging<ClassToTeacher> getClassList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, String camId, Integer classWeek, String techNames, Paging<ClassToTeacher> page) throws Exception {
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.plan_id, c.clas_name,c.clas_type,cam.cam_name, c.age_range, p.plan_switch, \r\n" + 
				"c.class_start_date, c.tuition_fees, IFNULL(c.size, 0) size, IFNULL(c.ss_num, 0) ss_num,\r\n" + 
				"cate.category_name, sub.subject_name ,cr.classroom_id, cr.building, cr.floor, cr.classroom_name, c.start_birthday,\r\n" + 
				"c.end_birthday, IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, c.cooperation_id, co.name,\r\n" + 
				"GROUP_CONCAT(CONCAT(t.tech_name, '_',u.user_mobile)) tech_names,\r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_name,\r\n" + 
				"GROUP_CONCAT(t.tech_id) tech_id,\r\n" + 
				"GROUP_CONCAT(t.user_id) user_id, tm.term_id, tm.term_name, tm.cur_year, \r\n" +
				"(IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) num,\r\n" + 
				"IFNULL(c.ss_money, 0) ss_money, IFNULL(c.st_money, 0) st_money,\r\n" + 
				"\r\n" + 
				"CONCAT((IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) , '/', c.size) divide \r\n" + 
				"FROM newsng.classes c \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.org_user u ON t.user_id = u.user_id AND u.is_del = 0 \r\n" + 
				"LEFT JOIN newsng.term tm ON c.term_id = tm.term_id AND tm.is_del = 0\r\n" + 
				"LEFT JOIN newsng.category cate ON c.category_id = cate.category_id AND cate.is_del = 0\r\n" + 
				"LEFT JOIN newsng.subject sub ON c.subject_id = sub.subject_id AND sub.is_del = 0\r\n" + 
				"LEFT JOIN newsng.campus cam ON c.cam_id = cam.cam_id AND cam.is_del = 0\r\n" + 
				"LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n" + 
				"LEFT JOIN newsng.cooperative co ON c.cooperation_id = co.cooperative_id AND co.is_del = 0\r\n" + 
				//"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"LEFT JOIN newsng.plan p ON p.plan_id = c.plan_id AND p.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0  \r\n");
				//"AND c.org_id = ?  ");
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != termId ) {
			sql.append(" AND c.term_id = ? ");
			params.add(termId);
		} else {
			//默认查询最晚的学期 
			//SELECT 	MAX(TERM_ID) FROM term WHERE NOW() > start_time 
			sql.append(" AND c.term_id = (SELECT term_id FROM newsng.term WHERE is_del = 0 AND  NOW() > start_time and org_id = ? ORDER BY start_time DESC LIMIT 0,1) ");
			params.add(orgId);
		}
		if(null != clasType ) {
			sql.append(" AND c.clas_type = ? ");
			params.add(clasType);
		}
		if(null != categoryId ) {
			sql.append(" AND c.category_id = ? ");
			params.add(categoryId);
		}
		if(null != subjectId ) {
			sql.append(" AND c.subject_id = ? ");
			params.add(subjectId);
		}
		if (null != camId) {
			boolean flag = camId.contains(",");
			
			if (camId.equals(Constant.ALL_CAMPUS)) {//获取全部校区
				
			} else if (!flag) {//只有一个校区
				sql.append(" AND c.cam_id = ? ");
				params.add(camId);
			} else {//多个校区
				String[] idArr = null;
				idArr = camId.split(",");
				sql.append(" AND c.cam_id in ( ");
				for (int i = 0; i < idArr.length; i++) {
					if(i == idArr.length-1)
					{
						sql.append(idArr[i]);
					} else {
						sql.append(idArr[i]);
						sql.append(",");
					}
				}
				sql.append(" )");
			}
		}
		if(null != classWeek ) {
			sql.append(" AND c.class_week = ? ");
			params.add(classWeek);
		}
		String group = " GROUP BY c.clas_id ";
		String order1 = "ORDER BY cam.cam_name , sub.subject_name, cr.classroom_name ASC";//ORDER BY cam.cam_name , sub.subject_name, cr.classroom_name ASC
		sql.append(group);
		sql.append(order1);
		String finalSql = "";
		String limit = "";
		String order2 = " ORDER BY tt.cam_name , tt.subject_name, tt.classroom_name ASC ";
		StringBuffer countSql = new StringBuffer();
		if (StringUtils.isNotBlank(techNames)) {//模糊查询
			StringBuffer like1 = new StringBuffer(" SELECT tt.* FROM ( ");
			StringBuffer like2 = new StringBuffer(" ) tt\r\n" + 
					"where  LOCATE(? , tt.tech_name )>0 OR LOCATE(? , tt.clas_name )>0 ");
			params.add(techNames);
			params.add(techNames);
			finalSql = like1.append(sql).append(like2).append(order2).toString();
			countSql.append("select count(*) from (" + finalSql + ") ts");
			int limitFrom = (page.getPage() - 1) * page.getLimit();
			limit = " limit " + limitFrom + "," + page.getLimit();
			finalSql = finalSql + limit;
		} else {
			finalSql = sql.toString();
			countSql.append("select count(*) from (" + finalSql + ") ts");
			int limitFrom = (page.getPage() - 1) * page.getLimit();
			limit = " limit " + limitFrom + "," + page.getLimit();
			finalSql = finalSql + limit;
		}
		Query query = session.createSQLQuery(finalSql);
		Query countQuery = session.createSQLQuery(countSql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
			countQuery.setParameter(i, params.get(i));
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		query.setResultTransformer(Transformers.aliasToBean(ClassToTeacher.class));
		List<ClassToTeacher> list = query.list();
		//班级列表  实收金额
		for (ClassToTeacher ct : list) {
			//根据班级id(clas_id) 查询支付项目表，获取实缴金额
			String ssMoney = ct.getSs_money();
			String stMoney = ct.getSt_money();
			BigDecimal ss = new BigDecimal(ssMoney);
		    BigDecimal st = new BigDecimal(stMoney);
			String sjsMoney = ss.subtract(st).toString();
			ct.setSs_tuition(MoneyUtil.fromFENtoYUAN(sjsMoney));
			
			//将 开始生日、结束生日和开课日期  Timestamp格式转换 为yyyy-MM-dd HH:mm:ss
			String startDate = null;
			String startBirthday = null;
			String endBirthday = null;
			startDate = CommonUtils.dateFormat(ct.getClass_start_date(), "yyyy-MM-dd");
			ct.setClass_start_date_str(startDate);
			startBirthday = CommonUtils.dateFormat(ct.getStart_birthday(), "yyyy-MM-dd");
			endBirthday = CommonUtils.dateFormat(ct.getEnd_birthday(), "yyyy-MM-dd");
			ct.setStart_birthday_str(startBirthday);
			ct.setEnd_birthday_str(endBirthday);
		}
		page.setTotal(pageTotal.intValue());
		page.setSize(page.getTotal()%page.getLimit()==0 ? (page.getTotal()/page.getLimit()) : (page.getTotal()/page.getLimit()+1));
		page.setData(list);
		page.setSuccess(true);
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassToTeacher> getClassList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, String camId, Integer classWeek, String techNames) {
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name,c.clas_type,cam.cam_name, c.age_range,\r\n" + 
				"c.class_start_date, c.tuition_fees, IFNULL(c.size, 0) size, IFNULL(c.ss_num, 0) ss_num,\r\n" + 
				"cate.category_name, sub.subject_name ,cr.classroom_id, cr.building, cr.floor, cr.classroom_name, c.start_birthday,\r\n" + 
				"c.end_birthday, IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, c.cooperation_id, co.name,\r\n" + 
				"GROUP_CONCAT(CONCAT(t.tech_name, '_', u.user_mobile)) tech_names,\r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_name,\r\n" + 
				"GROUP_CONCAT(t.tech_id) tech_id,\r\n" + 
				"GROUP_CONCAT(t.user_id) user_id, \r\n" +
				"(IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) num,\r\n" + 
				"IFNULL(c.ss_money, 0) ss_money, IFNULL(c.st_money, 0) st_money,\r\n" + 
				"\r\n" + 
				"CONCAT((IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) , '/', c.size) divide \r\n" + 
				"FROM newsng.classes c \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.org_user u ON t.user_id = u.user_id AND u.is_del = 0 \r\n" + 
				"LEFT JOIN newsng.term tm ON c.term_id = tm.term_id AND tm.is_del = 0\r\n" + 
				"LEFT JOIN newsng.category cate ON c.category_id = cate.category_id AND cate.is_del = 0\r\n" + 
				"LEFT JOIN newsng.subject sub ON c.subject_id = sub.subject_id AND sub.is_del = 0\r\n" + 
				"LEFT JOIN newsng.campus cam ON c.cam_id = cam.cam_id AND cam.is_del = 0\r\n" + 
				"LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n" + 
				"LEFT JOIN newsng.cooperative co ON c.cooperation_id = co.cooperative_id AND co.is_del = 0\r\n" + 
				//"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0  \r\n"); 
				//"AND c.org_id = ?  ");
		if(null != orgId ) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != termId ) {
			sql.append(" AND c.term_id = ? ");
			params.add(termId);
		} else {
			//默认查询最晚的学期 
			sql.append(" AND c.term_id = (SELECT term_id FROM newsng.term WHERE is_del = 0 AND  NOW() > start_time and org_id = ? ORDER BY start_time DESC LIMIT 0,1) ");
			params.add(orgId);			
		}
		if(null != clasType ) {
			sql.append(" AND c.clas_type = ? ");
			params.add(clasType);
		}
		if(null != categoryId ) {
			sql.append(" AND c.category_id = ? ");
			params.add(categoryId);
		}
		if(null != subjectId ) {
			sql.append(" AND c.subject_id = ? ");
			params.add(subjectId);
		}
		if (null != camId) {
			boolean flag = camId.contains(",");
			
			if (camId.equals(Constant.ALL_CAMPUS)) {//获取全部校区
				
			} else if (!flag) {//只有一个校区
				sql.append(" AND c.cam_id = ? ");
				params.add(camId);
			} else {//多个校区
				String[] idArr = null;
				idArr = camId.split(",");
				sql.append(" AND c.cam_id in ( ");
				for (int i = 0; i < idArr.length; i++) {
					if(i == idArr.length-1)
					{
						sql.append(idArr[i]);
					} else {
						sql.append(idArr[i]);
						sql.append(",");
					}
				}
				sql.append(" )");
			}
		}
		if(null != classWeek ) {
			sql.append(" AND c.class_week = ? ");
			params.add(classWeek);
		}
		String group = " GROUP BY c.clas_id ";
		String order1 = "ORDER BY cam.cam_name , sub.subject_name, cr.classroom_name, c.clas_id ASC";
		sql.append(group);
		sql.append(order1);
		String finalSql = "";
		String order2 = " ORDER BY tt.cam_name , tt.subject_name, tt.classroom_name, tt.clas_id ASC ";

		if (StringUtils.isNotBlank(techNames)) {//模糊查询
			StringBuffer like1 = new StringBuffer(" SELECT tt.* FROM ( ");
			StringBuffer like2 = new StringBuffer(" ) tt\r\n" + 
					"where  LOCATE(? , tt.tech_name )>0 OR LOCATE(? , tt.clas_name )>0 ");
			params.add(techNames);
			params.add(techNames);
			finalSql = like1.append(sql).append(like2).append(order2).toString();
		} else {
			finalSql = sql.toString();
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(finalSql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(ClassToTeacher.class));
		List<ClassToTeacher> list = query.list();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ClassToTeacher getClassToTeacher(Integer org_id, Integer classId) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.clas_id, c.clas_name,c.clas_type,cam.cam_name, c.age_range,\r\n");
		sql.append("c.class_start_date, c.tuition_fees, IFNULL(c.size, 0) size, IFNULL(c.ss_num, 0) ss_num,\r\n");
		sql.append("cate.category_name, sub.subject_name ,cr.classroom_id, cr.building, cr.floor, cr.classroom_name, c.start_birthday,\r\n");
		sql.append("c.end_birthday, IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n");
		sql.append("DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, c.cooperation_id, co.name,\r\n");
		sql.append("GROUP_CONCAT(CONCAT(t.tech_name, '_', u.user_mobile)) tech_names,\r\n");
		sql.append("GROUP_CONCAT(t.tech_name) tech_name,\r\n" + "GROUP_CONCAT(t.tech_id) tech_id,\r\n");
		sql.append("GROUP_CONCAT(t.user_id) user_id, \r\n" + "(IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) num,\r\n");
		sql.append("IFNULL(c.ss_money, 0) ss_money, IFNULL(c.st_money, 0) st_money,\r\n" + "\r\n");
		sql.append("CONCAT((IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) , '/', c.size) divide \r\n");
		sql.append("FROM newsng.classes c \r\n");
		sql.append("LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n");
		sql.append("LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n");
		sql.append("LEFT JOIN edugate_base.org_user u ON t.user_id = u.user_id AND u.is_del = 0 \r\n");
		sql.append("LEFT JOIN newsng.term tm ON c.term_id = tm.term_id AND tm.is_del = 0\r\n");
		sql.append("LEFT JOIN newsng.category cate ON c.category_id = cate.category_id AND cate.is_del = 0\r\n");
		sql.append("LEFT JOIN newsng.subject sub ON c.subject_id = sub.subject_id AND sub.is_del = 0\r\n");
		sql.append("LEFT JOIN newsng.campus cam ON c.cam_id = cam.cam_id AND cam.is_del = 0\r\n");
		sql.append("LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n");
		sql.append("LEFT JOIN newsng.cooperative co ON c.cooperation_id = co.cooperative_id AND co.is_del = 0\r\n");
		sql.append("WHERE\r\n" + "c.is_del = 0  \r\n");

		if (null != org_id) {
			sql.append(" AND c.org_id = ? ");
			params.add(org_id);
		}
		if (null != classId) {
			sql.append(" AND c.clas_id = ? ");
			params.add(classId);
		}

		/*String group = " GROUP BY c.clas_id ";
		String order1 = "ORDER BY cam.cam_name , c.clas_name, cr.classroom_name, c.clas_id ASC";
		sql.append(group);
		sql.append(order1);*/

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(ClassToTeacher.class));
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		List<ClassToTeacher> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getStatisticsInfo(List<Integer> ids) throws Exception {
		StringBuffer sql = new StringBuffer(" SELECT IFNULL(SUM(IFNULL(cla.ss_num, 0) - IFNULL(cla.st_num, 0)), 0) ss_num, " + 
				"IFNULL(SUM(IFNULL(cla.ss_money, 0)), 0) ss_money, IFNULL(SUM(IFNULL(cla.st_money, 0)), 0) st_money, " + 
				"COUNT(0) num FROM newsng.classes cla\r\n" + 
				//"INNER JOIN newsng.charge cha ON cha.clas_id = cla.clas_id AND cha.is_del = 0\r\n" +    GROUP_CONCAT(cla.clas_id) clas_id, 
				"WHERE cla.is_del = 0 ");
		//, \r\n" + "IFNULL(( SUM(IFNULL(cla.ss_money, 0)) - SUM(IFNULL(cla.st_money, 0)) ), 0) sjs_money
		StringBuffer classIds = new StringBuffer();
		if (ids != null) {
			sql.append("AND cla.clas_id IN ( ");
			for (int i = 0; i < ids.size(); i++) {
				if(i == ids.size()-1)
				{
					sql.append(ids.get(i));
					classIds.append(ids.get(i));
				} else {
					classIds.append(ids.get(i));
					classIds.append(",");
					sql.append(ids.get(i));
					sql.append(",");
				}
			}
			sql.append(" )");
		}
		String clasId = classIds.toString();
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> statisticsMap = query.list();
		Map map = new HashMap<>();
		if (null != statisticsMap && statisticsMap.size() > 0) {
			map = statisticsMap.get(0);
			Double ssMoney = (Double) map.get("ss_money");
			Double stMoney = (Double) map.get("st_money");
			double sjs_money = CommonUtils.jian(ssMoney, stMoney).doubleValue();
			map.put("clas_id", clasId);
			map.put("sjs_money", MoneyUtil.fromFENtoYUAN(String.valueOf(sjs_money)));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStudentList(Integer orgId, String cidList, String status) {
		String sql = " SELECT c.clas_name, s.stud_name, sc.stu_status, sc.stud_id, u.user_idnumber FROM newsng.classes c  \r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0  \r\n" + 
				"LEFT JOIN edugate_base.student s  ON sc.stud_id = s.stud_id  AND s.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.is_del = 0  \r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0  \r\n";  
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql += " AND c.org_id = ? ";
			params.add(orgId);
		}
		StringBuffer clas =  new StringBuffer("");
		if (cidList != null && cidList.length() > 0) {
			clas.append(" AND c.clas_id in ( ");
			String[] idArr = null;
			idArr = cidList.split(",");
			for (int i = 0; i < idArr.length; i++) {
				if(i == idArr.length-1)
				{
					clas.append(idArr[i]);
				} else {
					clas.append(idArr[i]);
					clas.append(",");
				}
			}
			clas.append(" )");
			sql += clas;
		}
		StringBuffer stu =  new StringBuffer("");
		if (status != null && status.length() > 0) {
			String[] statusArr = null;
			stu.append(" AND sc.stu_status in ( ");
			statusArr = status.split(",");
			for (int i = 0; i < statusArr.length; i++) {
				
				if(i == statusArr.length-1)//当循环到最后一个的时候 就不添加逗号,
				{
					stu.append(statusArr[i]);
				} else {
					stu.append(statusArr[i]);
					stu.append(",");
				}
			}
			stu.append(" )");
			sql = sql + stu;
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getClasInfoById(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer("");
		String ssql = " SELECT c.clas_id, c.org_id, c.term_id, c.plan_id, c.clas_name, c.category_id, c.subject_id, c.clas_type,\r\n" + 
				"c.cam_id, c.classroom_id, c.size, c.age_range, c.start_birthday, c.end_birthday,\r\n" + 
				"c.total_hours, c.class_start_date, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,c.tuition_fees, c.ys_num, \r\n" + 
				"c.ss_num, c.st_num, c.st_money, c.ss_money, c.ys_money, c.usable_num, c.is_del, c.insert_time, c.update_time, c.del_time, \r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_names,\r\n" + 
				"GROUP_CONCAT(t.tech_id) tech_ids,\r\n" + 
				"c.cooperation_id\r\n" + 
				"FROM newsng.classes c \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0\r\n" + 
				"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0\r\n";
				//"AND c.org_id = ?\r\n" + 
				//"AND c.clas_id = ?\r\n" + 
				//"GROUP BY c.clas_id ";
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (clasId != null) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		sql.append(" GROUP BY c.clas_id ");
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> clasList = query.list();
		Map map = new HashMap();
		if (clasList != null && clasList.size() > 0) {
			Map<String, Object> clas = clasList.get(0);
			//将 开始生日、结束生日和开课日期  Timestamp格式转换 为yyyy-MM-dd 
			Timestamp startDate =(Timestamp) clas.get("class_start_date");
			Timestamp startBirthday = (Timestamp) clas.get("start_birthday");
			Timestamp endBirthday = (Timestamp) clas.get("end_birthday");
			clas.put("class_start_date", CommonUtils.dateFormat(startDate, "yyyy-MM-dd"));
			clas.put("start_birthday", CommonUtils.dateFormat(startBirthday, "yyyy-MM-dd"));
			clas.put("end_birthday", CommonUtils.dateFormat(endBirthday, "yyyy-MM-dd"));
			map = clasList.get(0);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Classes getClassInfo(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT c.* FROM newsng.classes c  WHERE c.is_del = 0 ");
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (clasId != null) {
			sql.append("  AND c.clas_id = ?  ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		List<Classes> list = query.list();
		Classes classes = new Classes();
		if (list != null && list.size() > 0) {
			classes = list.get(0);
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTecherIds(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT t.tech_name, t.tech_id FROM  newsng.classes c\r\n" + 
				"INNER JOIN newsng.teacher_class tc ON tc.clas_id = c.clas_id AND tc.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0\r\n" + 
				"WHERE \r\n" + 
				"c.is_del = 0\r\n";
				//"AND c.org_id = ?\r\n" + 
				//"AND c.clas_id = ? ";
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (clasId != null) {
			sql.append("  AND c.clas_id = ?  ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@Override
	public Integer getClassNum(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT COUNT(1) FROM newsng.classes c\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id\r\n" + 
				"INNER JOIN edugate_base.student s  ON s.stud_id = sc.stud_id \r\n" + 
				"WHERE \r\n" + 
				"c.is_del = 0\r\n" + 
				"AND sc.is_del = 0\r\n" + 
				"AND s.is_del = 0\r\n" + 
				"AND sc.stu_status NOT IN (4,8)\r\n"; 
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (clasId != null) {
			sql.append("  AND c.clas_id = ?  ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		BigInteger stuNum = (BigInteger) query.uniqueResult();
		return stuNum.intValue();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BigDecimal getClassFees(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT c.clas_id, c.clas_name, c.clas_type, c.ss_money, c.st_money,\r\n" + 
				"IFNULL((c.ss_money - c.st_money), 0) zsjs, c.ss_num, c.st_num\r\n" + 
				"FROM newsng.classes c\r\n" + 
				//"INNER JOIN newsng.charge ch ON c.clas_id = ch.clas_id\r\n" + 
				"WHERE \r\n" + 
				"c.is_del = 0 \r\n"; 
				//"AND ch.is_del = 0\r\n"; 
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (clasId != null) {
			sql.append("  AND c.clas_id = ?  ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		BigDecimal balance = null;
		if (null != list && list.size() > 0) {
			Map feesMap = list.get(0);
			String ssMoney = (String) (feesMap.get("ss_money") ==  null ? "0" : (String) feesMap.get("ss_money"));
			String stMoney = (String) (feesMap.get("st_money") ==  null ? "0" : (String) feesMap.get("st_money"));
			BigDecimal ss = new BigDecimal(ssMoney);
			BigDecimal st = new BigDecimal(stMoney);
			balance = ss.subtract(st);
		} else {
			balance = new BigDecimal("0");
		}
		return balance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getClassListByTerm(Integer orgId, Integer termId, Integer clasType, String camIds) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT c.clas_id, c.clas_name FROM newsng.classes c WHERE\r\n" + 
				" c.is_del = 0 ";
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if (termId != null) {
			sql.append(" AND c.term_id = ? ");
			params.add(termId);
		}
		if (clasType != null) {
			sql.append(" AND c.clas_type = ? ");
			params.add(clasType);
		}
		if (null != camIds) {
			boolean flag = camIds.contains(",");
			
			if (camIds.equals(Constant.ALL_CAMPUS)) {//获取全部校区
				
			} else if (!flag) {//只有一个校区
				sql.append(" AND c.cam_id = ? ");
				params.add(camIds);
			} else {//多个校区
				String[] idArr = null;
				idArr = camIds.split(",");
				sql.append(" AND c.cam_id in ( ");
				for (int i = 0; i < idArr.length; i++) {
					if(i == idArr.length-1)
					{
						sql.append(idArr[i]);
					} else {
						sql.append(idArr[i]);
						sql.append(",");
					}
				}
				sql.append(" )");
			}
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> clasList = query.list();
		Map clasMap = new HashMap();
		if (null != clasList) {
			for (Map clas : clasList) {
				clasMap.put(clas.get("clas_name"), clas.get("clas_id"));
			}
		}
		return clasMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getClassromm(Integer camId) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCategory(Integer orgId) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getSubject(Integer orgId) {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getTeacher(Integer orgId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT t.tech_id, t.tech_name, u.user_mobile FROM edugate_base.teacher t\r\n" + 
				"INNER JOIN edugate_base.org_user u ON t.user_id = u.user_id AND u.is_del = 0\r\n" + 
				"WHERE t.is_del = 0\r\n";
				//"AND t.org_id = ? ";
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND t.org_id = ? ");
			params.add(orgId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.list();
		Map techMap = new HashMap();
		if (null != list) {
			for (Map tech : list) {
				Integer techId = (Integer) tech.get("tech_id");
				String techName = (String) tech.get("tech_name");
				String mobile = (String) tech.get("user_mobile");
				String valueStr = techName + "_" + mobile; 
				techMap.put(valueStr, techId);
			}
		}
		return techMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCampus(Integer orgId) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTechListByClasId(Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT c.clas_name, sub.subject_name, cate.category_name, c.class_week, c.class_unset_time,\r\n" + 
				"DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time, DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time,\r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_names, GROUP_CONCAT(t.tech_id) tech_ids\r\n" + 
				"FROM classes c \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  tc.clas_id = c.clas_id AND tc.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.teacher t ON t.tech_id = tc.tech_id AND t.is_del = 0 \r\n" + 
				"INNER JOIN newsng.category cate ON cate.category_id = c.category_id AND cate.is_del = 0 \r\n" + 
				"INNER JOIN newsng.subject sub ON sub.subject_id = c.subject_id AND sub.is_del = 0 \r\n" + 
				"WHERE c.is_del = 0\r\n"); 
		List<Object> params = new ArrayList<Object>();
		if (clasId != null) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		sql.append(" GROUP BY c.clas_id ");
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Map<String, Object> map = new HashMap<>();
		if (null != list && list.size() > 0) {
			map = list.get(0);
			if (null != map.get("class_unset_time")) {//不固定时间 不为空

			} else {//不固定时间 为空
				String classBeginTime = (String) map.get("class_begin_time");//ct.getClass_begin_time();
				String classOverTime = (String) map.get("class_over_time");
				String classWeek = Consts.WEEK_MAP.get(String.valueOf((Integer) map.get("class_week")));
				map.put("class_unset_time", classWeek + " " + classBeginTime + "-" + classOverTime);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getWXOpenId(Integer techId) {
		String openId = "";
		StringBuffer sql = new StringBuffer(" SELECT t.user_id, u.openid FROM edugate_base.teacher t\r\n" + 
				"INNER JOIN edugate_base.org_user u ON u.user_id = t.user_id AND u.is_del = 0\r\n" + 
				"WHERE t.is_del = 0 \r\n"); 
				//"AND t.tech_id = ? ");
		List<Object> params = new ArrayList<Object>();
		if (techId != null) {
			sql.append(" AND t.tech_id = ?  ");
			params.add(techId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			openId = (String) map.get("openid");
		}
		return openId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentRoster> getStudentRosterList(Integer orgId, Integer clasId, String status) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT s.stud_name, s.stud_id, s.user_id AS stu_userId, \r\n" + 
				"CASE WHEN  s.sex  = 0 THEN '男' WHEN   s.sex  = 1 THEN '女' ELSE '保密' END sex ,\r\n" + 
				"TIMESTAMPDIFF(\r\n" + 
				"    YEAR,\r\n" + 
				"    s.birthday,\r\n" + 
				"    NOW()\r\n" + 
				") AS age,\r\n" + 
				"p.parent_name,\r\n" + 
				"u.user_idnumber, uu.openid,\r\n" + 
				"uu.user_mobile\r\n" + 
				"FROM newsng.classes c\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student s ON s.stud_id = sc.stud_id AND s.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student2parent sp ON sp.stud_id = s.stud_id AND sp.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.parent p ON p.parent_id = sp.parent_id AND p.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.org_user u ON u.user_id = s.user_id AND u.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.org_user uu ON uu.user_id = p.user_id \r\n" +
				"WHERE c.is_del = 0\r\n"; 
				//"AND c.org_id = ?\r\n" + 
				//"AND c.clas_id = ? ";
		List<Object> params = new ArrayList<Object>();
		sql.append(ssql);
		if (null != orgId) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != clasId ) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		String[] statusArr = null;
		if (status != null) {//AND sc.stu_status IN (1,2)
			sql.append(" AND sc.stu_status in ( ");
			statusArr = status.split(",");
			for (int i = 0; i < statusArr.length; i++) {
				
				if(i == statusArr.length-1)
				{
					sql.append(statusArr[i]);
				} else {
					sql.append(statusArr[i]);
					sql.append(",");
				}
			}
			sql.append(" )");
		}
		String order = " ORDER BY sc.stu_status DESC ";
		sql.append(order);
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(StudentRoster.class));
		List<StudentRoster> list = query.list();
		return list;
	}

	@Override
	public Classes getBySCId(String stu_class_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT c.* FROM classes c INNER JOIN student_class sc ON sc.clas_id=c.clas_id AND sc.cam_id=c.cam_id AND sc.is_del=0 WHERE c.is_del=0 AND sc.stu_class_id=:stu_class_id";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		try {
			query.setInteger("stu_class_id", Integer.parseInt(stu_class_id));
			return (Classes) query.list().get(0);
		} catch (Exception e) {
			return new Classes();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStudStatusByClasId(Integer clasId, Integer studId) {
		StringBuffer sql = new StringBuffer("   SELECT s.stud_name, s.birthday, sex, s.user_id, sc.stu_status FROM edugate_base.student s \r\n" + 
				"  INNER JOIN newsng.student_class sc ON s.stud_id = sc.stud_id AND sc.is_del = 0\r\n" + 
				"  WHERE s.is_del = 0 "); 
				//"  AND sc.clas_id = ?\r\n" + 
				//"  AND s.stud_id = ? ");
		List<Object> params = new ArrayList<Object>();
		if (null != clasId) {
			sql.append(" AND sc.clas_id = ? ");
			params.add(clasId);
		}
		if(null != studId ) {
			sql.append(" AND s.stud_id = ? ");
			params.add(studId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Map<String, Object> map = new HashMap<>();
		if (null != list && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStudentInfoByClasIdAndStudId(Integer clasId, Integer studId, String status) {
		StringBuffer sql = new StringBuffer("   SELECT s.stud_name, s.birthday, sex, s.user_id, sc.stu_status FROM edugate_base.student s \r\n" + 
				"  INNER JOIN newsng.student_class sc ON s.stud_id = sc.stud_id AND sc.is_del = 0\r\n" + 
				"  WHERE s.is_del = 0 "); 
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(status)) {
			if (status.equals("noExist")) {
				sql.append(" AND sc.stu_status NOT IN (4,8) ");
			} else if (status.equals("exist")) {
				sql.append(" AND sc.stu_status IN (4,8) ");
			} else {
				
			}
		}
		if (null != clasId) {
			sql.append(" AND sc.clas_id = ? ");
			params.add(clasId);
		}
		if(null != studId ) {
			sql.append(" AND s.stud_id = ? ");
			params.add(studId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStuList(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT s.* FROM edugate_base.student s \r\n" + 
				"INNER JOIN newsng.student_class sc ON sc.stud_id = s.stud_id  AND sc.is_del = 0\r\n" + 
				"INNER JOIN newsng.classes c ON c.clas_id = sc.clas_id  AND sc.is_del = 0  \r\n" + 
				"WHERE s.is_del = 0\r\n"); 
				//"AND c.org_id = 46\r\n" + 
				//"AND c.clas_id = 1 ");
		List<Object> params = new ArrayList<Object>();
		if (null != orgId) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != clasId ) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStudentPaymentRecordsList(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT s.stud_name, o.user_mobile, o.user_idnumber, \r\n" + 
				"CASE WHEN cr.txnAmt > 0 THEN '收费' ELSE '退费' END pay_method,\r\n" + 
				"cr.txnTime, cr.txnAmt, '' AS bz   FROM newsng.classes c  \r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id  AND sc.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id  AND s.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.org_user o ON o.user_id = s.user_id  AND o.is_del = 0  \r\n" + 
				"INNER JOIN newsng.charge_detail cd ON cd.clas_id = c.clas_id AND cd.is_del = 0\r\n" + 
				"RIGHT JOIN newsng.charge_record cr ON cr.cd_id = cd.cd_id AND cr.is_del = 0\r\n" + 
				"WHERE c.is_del = 0\r\n"); 
		List<Object> params = new ArrayList<Object>();
		if (null != orgId) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != clasId ) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@Override
	public Integer getRegisterCount(Integer orgId) {
		StringBuffer sql = new StringBuffer(" SELECT count(1) FROM user_register ur where ur.is_del = 0 ");
		List<Object> params = new ArrayList<Object>();
		if (null != orgId) {
			sql.append(" AND ur.org_id = ? ");
			params.add(orgId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		BigInteger registerCount = (BigInteger) query.uniqueResult();
		return registerCount.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStudentCountAndClasCountByTermAndCampus(Integer orgId, Integer termId, String camId, Integer clasType) throws Exception {
		StringBuffer stuSql = new StringBuffer(" SELECT COUNT(1) stuCount, COUNT(DISTINCT c.clas_id) AS cla FROM newsng.classes c\r\n" + 
				"inner JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0 AND sc.stu_status = ?\r\n" + 
				"WHERE c.is_del = 0 \r\n");
		StringBuffer clasSql = new StringBuffer(" SELECT count(1) clasCount, IFNULL(SUM(IFNULL(c.st_num, 0)), 0) zstCount, IFNULL(SUM(IFNULL(c.ss_money, 0)),0) zss, IFNULL(SUM(IFNULL(c.st_money, 0)),0) zst, IFNULL(SUM(IFNULL(c.ss_money,0)) - SUM(IFNULL(c.st_money,0)), 0) zsjsMoney FROM newsng.classes c  \r\n" + 
				"WHERE c.is_del = 0 \r\n");
		List<Object> params = new ArrayList<Object>();
		List<Object> clasParams = new ArrayList<Object>();
		params.add(Consts.STUD_STATUS_5PAID);
		if (null != orgId) {
			stuSql.append(" AND c.org_id = ? ");
			clasSql.append(" AND c.org_id = ? ");
			params.add(orgId);
			clasParams.add(orgId);
		}
		if (null != termId) {
			stuSql.append(" AND c.term_id = ? ");
			clasSql.append(" AND c.term_id = ? ");
			params.add(termId);
			clasParams.add(termId);
		}
		/*if (null != camId) {
			stuSql.append(" AND c.cam_id = ? ");
			clasSql.append(" AND c.cam_id = ? ");
			params.add(camId);
			clasParams.add(camId);
		}*/
		if (null != camId) {
			boolean flag = camId.contains(",");
			
			if (camId.equals(Constant.ALL_CAMPUS)) {//获取全部校区
				
			} else if (!flag) {//只有一个校区
				stuSql.append(" AND c.cam_id = ? ");
				params.add(camId);
				clasSql.append(" AND c.cam_id = ? ");
				clasParams.add(camId);
			} else {//多个校区
				String[] idArr = null;
				idArr = camId.split(",");
				stuSql.append(" AND c.cam_id in ( ");
				clasSql.append(" AND c.cam_id in ( ");
				for (int i = 0; i < idArr.length; i++) {
					if(i == idArr.length-1)
					{
						stuSql.append(idArr[i]);
						clasSql.append(idArr[i]);
					} else {
						stuSql.append(idArr[i]);
						stuSql.append(",");
						clasSql.append(idArr[i]);
						clasSql.append(",");
					}
				}
				stuSql.append(" )");
				clasSql.append(" )");
			}
		}
		if (null != clasType) {
			stuSql.append(" AND c.clas_type = ? ");
			params.add(clasType);
		}
		Session session = this.factory.getCurrentSession();
		Query stuQuery = session.createSQLQuery(stuSql.toString());
		Query clasQuery = session.createSQLQuery(clasSql.toString());
		for (int i = 0; i < params.size(); i++) {
			stuQuery.setParameter(i, params.get(i));
		}
		for (int i = 0; i < clasParams.size(); i++) {
			clasQuery.setParameter(i, clasParams.get(i));
		}
		stuQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		clasQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> stuMap = (Map<String, Object>) stuQuery.uniqueResult();
		Map<String, Object> clasMap = (Map<String, Object>) clasQuery.uniqueResult();
		BigInteger stuCount = (BigInteger) stuMap.get("stuCount");
		BigInteger clasCount = (BigInteger) clasMap.get("clasCount");
		BigDecimal zstCount = (BigDecimal) clasMap.get("zstCount");
		Double zsjsMoney = (Double) (clasMap.get("zsjsMoney") == null ? 0.0 : (Double) clasMap.get("zsjsMoney"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (stuCount != null) {
			map.put("stuCount", stuCount.intValue());
		} else {
			map.put("stuCount", 0);
		}
		if (clasCount != null) {
			map.put("clasCount", clasCount.intValue());
		} else {
			map.put("clasCount", 0);
		}
		if (zstCount != null) {
			map.put("zstCount", zstCount.intValue());
		} else {
			map.put("zstCount", 0);
		}
		map.put("zsjsMoney", MoneyUtil.fromFENtoYUAN(String.valueOf(zsjsMoney)));
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCampusIdByClassNameAndTerm(Integer orgId, String clasName, Integer termId) {
		String sql = "   SELECT cam_id FROM classes WHERE org_id = ? AND clas_name = ? AND term_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(clasName);
		params.add(termId);
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Integer camId = null;
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			camId = (Integer) map.get("cam_id");		
		}
		return camId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCampusIdByClasId(Integer orgId, Integer clasId) {
		String sql = "   SELECT cam_id FROM classes WHERE org_id = ? AND clas_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(clasId);
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Integer camId = null;
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			camId = (Integer) map.get("cam_id");		
		}
		return camId;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Paging<Map<String, Object>> getStudentListByClasId(Integer orgId, Integer clasId, String status,
			Paging<Map> page) throws Exception {
		Paging<Map<String, Object>> paging = new Paging<Map<String, Object>>();
		
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, s.stud_id, s.stud_name, o.user_idnumber, sc.stu_status, sub.subject_name,  p.plan_switch,\r\n" + 
				"CASE WHEN IFNULL(sc.is_print, 0) = 0 THEN '未打印' ELSE '已打印' END is_print,  \r\n" + 
				"sc.voucher_no, IFNULL(ch.money, '--') money, IFNULL(ch.txnAmt, '--') txnAmt, ch.status,  \r\n" + 
				"IFNULL((ch.money - ch.txnAmt), '--') tf_money, IFNULL(ch.online_pay, 0) online_pay, IFNULL(ch.offline_pay, 0) offline_pay, ch.pay_method pay_method_id,\r\n" + 
				"CASE WHEN ch.pay_method = 1 THEN '现金' WHEN  ch.pay_method = 2 THEN '银行卡' WHEN ch.pay_method = 3 THEN '手机' ELSE '' END pay_method,\r\n" + 
				"sc.stu_class_id, ch.cd_id\r\n" + 
				"FROM newsng.classes c \r\n" + 
				"INNER JOIN newsng.subject sub ON sub.subject_id = c.subject_id AND sub.is_del = 0\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id  AND sc.is_del = 0 \r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id  AND s.is_del = 0 \r\n" + 
				"INNER JOIN edugate_base.org_user o ON o.user_id = s.user_id  AND o.is_del = 0 \r\n" + 
				"LEFT JOIN newsng.charge_detail ch ON ch.stud_id = s.stud_id  AND ch.clas_id = c.clas_id AND ch.is_del = 0 AND ch.stu_class_id = sc.stu_class_id\r\n" + 
				"LEFT JOIN plan p ON c.plan_id = p.plan_id AND p.is_del = 0 \r\n" + 
				"WHERE c.is_del = 0  ");
		/*StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, s.stud_id, s.stud_name, o.user_idnumber, sc.stu_status, sub.subject_name,  p.plan_switch, \r\n" + 
				"CASE WHEN IFNULL(sc.is_print, 0) = 0 THEN '未打印' ELSE '已打印' END is_print,   \r\n" + 
				"sc.voucher_no, IFNULL(ch.money, '--') money, IFNULL(ch.txnAmt, '--') txnAmt, ch.status,   \r\n" + 
				"IFNULL((ch.money - ch.txnAmt), '--') tf_money, IFNULL(ch.online_pay, 0) online_pay, IFNULL(ch.offline_pay, 0) offline_pay, ch.pay_method pay_method_id,\r\n" + 
				"CASE WHEN ch.pay_method = 1 THEN '现金' WHEN  ch.pay_method = 2 THEN '银行卡' WHEN ch.pay_method = 3 THEN '手机' ELSE '' END pay_method, \r\n" + 
				"sc.stu_class_id, ch.cd_id \r\n" + 
				"FROM newsng.classes c  \r\n" + 
				"INNER JOIN newsng.subject sub ON sub.subject_id = c.subject_id AND sub.is_del = 0\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id  AND sc.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id  AND s.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.org_user o ON o.user_id = s.user_id  AND o.is_del = 0  \r\n" + 
				"LEFT JOIN newsng.charge_detail ch ON ch.stud_id = s.stud_id  AND ch.clas_id = c.clas_id AND ch.is_del = 0  \r\n" + 
				"LEFT JOIN plan p ON c.plan_id = p.plan_id AND p.is_del = 0 \r\n" +
				"WHERE c.is_del = 0  ");*/
		if (null != orgId) {
			sql.append(" AND c.org_id = ? ");
			params.add(orgId);
		}
		if(null != clasId ) {
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		String[] statusArr = null;
		if (status != null) {//AND sc.stu_status IN (1,2)
			sql.append(" AND sc.stu_status in ( ");
			statusArr = status.split(",");
			for (int i = 0; i < statusArr.length; i++) {
				
				if(i == statusArr.length-1)//当循环到最后一个的时候 就不添加逗号,
				{
					sql.append(statusArr[i]);
				} else {
					sql.append(statusArr[i]);
					sql.append(",");
				}
			}
			sql.append(" )");
		}
		String order = " ORDER BY sc.stu_status DESC ";
		sql.append(order);
		String countSql="select count(*) from ("+sql.toString()+") t";
		int limitFrom = (page.getPage() - 1) * page.getLimit();
		String limit = " limit " + limitFrom + "," + page.getLimit();
		Query query = session.createSQLQuery(sql.append(limit).toString());
		Query countQuery = session.createSQLQuery(countSql + limit);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
			countQuery.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		List<Map<String, Object>> list = query.list();
		
		if (null != list && list.size() > 0 ) {
			for (Map<String, Object> m : list) {
				Integer stuStatus = (Integer) m.get("stu_status");
				if (null != stuStatus) {
					String statusName = Consts.STUD_PAY_STATUS_MAP.get(stuStatus.toString());
					m.put("stu_status_name", statusName);
				}
				Integer payStatus = (Integer) m.get("status");//状态【0未支付1已支付2申请退款3已退全款4已退部分款5已驳回 9不参与】',
				String txnAmtFen = (String) (m.get("txnAmt"));//'实际金额（单位：分）',
				if ("--".equals(txnAmtFen)) {
					
				} else {
					//String txnAmt = (String) m.get("txnAmt");
					String txnAmtYuan = MoneyUtil.fromFENtoYUAN(txnAmtFen);
					m.put("txnAmt", txnAmtYuan);
				}
				//计算退费金额
				if (payStatus == null ) {
					payStatus = 0;
				}
				if (payStatus == 0) {//未支付
					m.put("tf_money", "--");
				} else {
					String money = (String) m.get("money");//应收金额（单位：元）',
					String txnAmtYuan = MoneyUtil.fromFENtoYUAN(txnAmtFen);
					BigDecimal tf = CommonUtils.jian(money, txnAmtYuan);
					m.put("tf_money", String.valueOf(tf));
				}
			}
		}
		paging.setData(list);
		paging.setTotal(pageTotal.intValue());
		paging.setSize(page.getTotal()%page.getLimit()==0 ? (page.getTotal()/page.getLimit()) : (page.getTotal()/page.getLimit()+1));
		paging.setSuccess(true);
		paging.setLimit(page.getLimit());
		paging.setPage(page.getPage());
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassToTeacher> getListByClasId(Integer orgId, String ids) {
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, c.age_range,\r\n" + 
				"c.class_start_date, IFNULL(c.tuition_fees , 0) tuition_fees, IFNULL(c.size, 0) size,\r\n" + 
				"cr.building, cr.floor, cr.classroom_name,\r\n" + 
				"IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, GROUP_CONCAT(t.tech_id) tech_id,\r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_name\r\n" + 
				"FROM newsng.classes c \r\n" + 
				"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n" + 
				"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n" + 
				"LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0 \r\n" + 
				"AND c.org_id = ?\r\n");
		//班级ids
		String[] strArray = null; 
		if (ids != null) {
			strArray = ids.split(",");
			sql.append("AND c.clas_id  IN ( ");
			for (int i = 0; i < strArray.length; i++) {//根据班级id获取班级信息
				if(i == strArray.length-1)//当循环到最后一个的时候 就不添加逗号,
				{
					sql.append(strArray[i]);
				} else {
					sql.append(strArray[i]);
					sql.append(",");
				}
			}
			sql.append(" )");
		}
		sql.append(" GROUP BY c.clas_id ");
		sql.append(" ORDER BY c.clas_id ASC ");
		params.add(orgId);
		
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(ClassToTeacher.class));
		List<ClassToTeacher> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMyCoucher(Integer orgId, Integer studId, String userIdnumber, String techName, Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT s.stud_name, t.term_name, CONCAT(s1.subject_name,'(',c.clas_name,')') clas_name, ");
		sql.append(" CASE WHEN c.clas_type = 1 THEN '老生班' ELSE '新生班' END clas_type, ");
		sql.append(" c.total_hours, DATE_FORMAT(c.class_start_date,'%Y-%m-%d') class_start_date, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time, ");
		sql.append(" DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.tuition_fees, ");
		sql.append(" c1.cam_name, c2.classroom_name, CONCAT(c2.building,c2.floor,c2.classroom_name) classroom_names, c.size, ");
		sql.append(" CASE WHEN cd.pay_method = 1 THEN '现金' WHEN  cd.pay_method = 2 THEN '银行卡' WHEN cd.pay_method = 3 THEN '手机' ELSE '' END pay_method, ");
		sql.append(" cr.txnAmt, DATE_FORMAT(cr.txnTime,'%Y-%m-%d %H:%i:%s') txnTime, sc.voucher_no ");
		sql.append(" FROM edugate_base.student s INNER JOIN student_class sc ON s.stud_id = sc.stud_id AND sc.is_del = 0 AND sc.stu_status = ? ");//5
		sql.append(" INNER JOIN classes c ON sc.clas_id = c.clas_id AND c.is_del = 0 AND c.clas_id = ? ");//clas_id
		sql.append(" INNER JOIN campus c1 ON c.cam_id = c1.cam_id AND c1.is_del = 0 ");
		sql.append(" INNER JOIN classroom c2 ON c.classroom_id = c2.classroom_id AND c2.is_del = 0 ");
		sql.append(" INNER JOIN category c3 ON c.category_id = c3.category_id AND c3.is_del = 0 ");
		sql.append(" INNER JOIN subject s1 ON c.subject_id = s1.subject_id AND s1.is_del = 0 ");
		sql.append(" INNER JOIN term t ON c.term_id = t.term_id AND t.is_del = 0 ");
		sql.append(" INNER JOIN charge_detail cd ON s.stud_id = cd.stud_id AND cd.is_del = 0 ");
		sql.append(" INNER JOIN charge_record cr ON cd.cd_id = cr.cd_id AND cr.is_del = 0 AND cr.txnType = '01' ");//'01'
		sql.append(" WHERE s.is_del = 0 AND s.org_id = ? AND s.stud_id = ? ");//org_id  stud_id 
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(Consts.STUD_STATUS_5PAID);
		params.add(Constant.TXNTYPE_UNIONPAY_PAY);
		params.add(orgId);
		params.add(userIdnumber);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getUserRegisterList(Integer orgId, String userIdnumber) {
		String sql = " SELECT * FROM user_register ur WHERE  ur.org_id = ? AND ur.card_num = ? AND ur.is_del = 0 AND ur.auth_status = ? ";
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(userIdnumber);
		params.add(Consts.AUTH_STATUS_OK);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}
	/**
	 * 根据学员班级关系表id获取 学员id 班级id
	 * 189978 189980 189981
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getClassIdAndStudId(String studClasIds) {
		String status = Consts.STUD_STATUS_5PAID + "," + Consts.STUD_STATUS_6REFUND_TO_AUDIT + "," + Consts.STUD_STATUS_7REFUND_AUDITED + Consts.STUD_STATUS_8REFUND_FINISHED;//"2,3,5,6,7,8";
		StringBuilder sql = new StringBuilder(" SELECT sc.stud_id, sc.clas_id, sc.cam_id, sc.stu_status, sc.quota_hold, sc.pay_expired_time, sc.is_print, sc.voucher_no FROM student_class sc "); 
		sql.append(" WHERE sc.is_del = 0 AND sc.stu_status in ( ");
		String[] statusArr = status.split(",");
		for (int i = 0; i < statusArr.length; i++) {
			if(i == statusArr.length-1)
			{
				sql.append(statusArr[i]);
			} else {
				sql.append(statusArr[i]);
				sql.append(",");
			}
		}
		sql.append(" )");
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		//params.add(Consts.STUD_STATUS_5PAID);
		if (null != studClasIds) {
			boolean flag = studClasIds.contains(",");
			if (!flag) {//只有一个
				sql.append(" AND sc.stu_class_id = ? ");
				params.add(studClasIds);
			} else {//多个
				String[] idArr = null;
				idArr = studClasIds.split(",");
				sql.append(" AND sc.stu_class_id in ( ");
				for (int i = 0; i < idArr.length; i++) {
					if(i == idArr.length-1)
					{
						sql.append(idArr[i]);
					} else {
						sql.append(idArr[i]);
						sql.append(",");
					}
				}
				sql.append(" )");
			}
		}
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}
	/***
	 * 根据班级id和学员id校验班级是否存在该学员
	 */
	@Override
	public Integer getStudentCountByClasIdAndStudId(Integer clasId, Integer studId) {
		String sql = " SELECT count(1) FROM student_class WHERE is_del = 0 AND stud_id = ? AND clas_id = ?  AND stu_status NOT IN (4,8) ";//AND quota_hold = ?
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(studId);
		params.add(clasId);
		//params.add(Constant.IS_QUOTA_HOLD_YES);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		BigInteger quotaCount = (BigInteger) query.uniqueResult();
		return quotaCount.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBatchMyCoucher(Integer orgId, Integer studId, Integer clasId) {
		String txnTypes = Constant.TXNTYPE_UNIONPAY_PAY + "," + Constant.TXNTYPE_SJWY_OFFLINE_PAY + "," + Constant.TXNTYPE_SJWY_OFFLINE_REFUND_PREVIEW + "," + Constant.TXNTYPE_SJWY_ONLINE_REFUND_AGREE + "," + Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE + "," + Constant.TXNTYPE_UNIONPAY_REFUND + "," + Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE;
		String[] txnTypesArr = null;
		txnTypesArr = txnTypes.split(",");
		StringBuilder sql = new StringBuilder(" SELECT s.stud_name, t.term_name, CONCAT(s1.subject_name,'(',c.clas_name,')') clas_name, ou.user_idnumber,GROUP_CONCAT(DISTINCT t1.tech_name) AS teacher_name, ");
		sql.append(" CASE WHEN c.clas_type = 1 THEN '老生班' ELSE '新生班' END clas_type, ");
		sql.append(" c.total_hours, DATE_FORMAT(c.class_start_date,'%Y-%m-%d') class_start_date, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time, ");
		sql.append(" DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.tuition_fees, ");
		sql.append(" c1.cam_name, c2.classroom_name, c.size, ");
		sql.append(" CASE WHEN cd.pay_method = 1 THEN '现金' WHEN  cd.pay_method = 2 THEN '银行卡' WHEN cd.pay_method = 3 THEN '手机' ELSE '' END pay_method, ");
		sql.append(" cr.txnAmt, DATE_FORMAT(cr.txnTime,'%Y-%m-%d %H:%i:%s') txnTime, sc.voucher_no ");
		sql.append(" FROM edugate_base.student s INNER JOIN student_class sc ON s.stud_id = sc.stud_id AND sc.is_del = 0 AND sc.stu_status IN (5,6,7,8) ");//5
		sql.append(" INNER JOIN classes c ON sc.clas_id = c.clas_id AND c.is_del = 0 AND c.clas_id = ? ");//clas_id
		sql.append(" INNER JOIN campus c1 ON c.cam_id = c1.cam_id AND c1.is_del = 0 ");
		sql.append(" INNER JOIN classroom c2 ON c.classroom_id = c2.classroom_id AND c2.is_del = 0 ");
		sql.append(" INNER JOIN category c3 ON c.category_id = c3.category_id AND c3.is_del = 0 ");
		sql.append(" INNER JOIN subject s1 ON c.subject_id = s1.subject_id AND s1.is_del = 0 ");
		sql.append(" INNER JOIN term t ON c.term_id = t.term_id AND t.is_del = 0 ");
		sql.append(" INNER JOIN charge_detail cd ON s.stud_id = cd.stud_id AND cd.is_del = 0 ");
		sql.append(" INNER JOIN charge_record cr ON cd.cd_id = cr.cd_id AND cr.is_del = 0 ");//txnType '01'  AND cr.txnType = ?
		sql.append(" INNER JOIN edugate_base.org_user ou ON s.user_id = ou.user_id AND ou.is_del = 0 ");
		sql.append(" LEFT JOIN teacher_class tc ON c.clas_id = tc.clas_id AND tc.is_del = 0 ");
		sql.append(" LEFT JOIN edugate_base.teacher t1 ON tc.tech_id = t1.tech_id AND t1.is_del = 0 ");
		sql.append(" WHERE s.is_del = 0 AND s.org_id = ? AND s.stud_id = ? ");//org_id  stud_id
		sql.append(" AND cr.txnType in ( ");
		for (int i = 0; i < txnTypesArr.length; i++) {
			if(i == txnTypesArr.length-1)
			{
				sql.append("'"+txnTypesArr[i]+"'");
			} else {
				sql.append("'"+txnTypesArr[i]+"'");
				sql.append(",");
			}
		}
		sql.append(" )");
		
		sql.append(" GROUP BY c.clas_id ");
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		//params.add(Consts.STUD_STATUS_5PAID);
		params.add(clasId);
		//params.add(Constant.TXNTYPE_UNIONPAY_PAY);
		params.add(orgId);
		params.add(studId);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStudentInfoByUserIdnumber(Integer orgId, String userIdnumber) {
		String sql = " SELECT s.stud_name, s.stud_id, u.user_idnumber,u.user_type FROM edugate_base.org_user u INNER JOIN edugate_base.student s ON u.user_id = s.user_id WHERE u.is_del = 0 AND s.is_del = 0 AND u.org_id = ? AND u.user_idnumber = ? ";
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(userIdnumber);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStudentToParent(Integer studId) {
		String sql = " SELECT s.stud_id, s.parent_id, s.relation, s.is_main FROM edugate_base.student2parent s WHERE s.is_main = ? AND s.stud_id = ? ";//190133
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(Consts.IS_MAIN);
		params.add(studId);
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStudentListByClasIdAndStudId(Integer clasId, Integer studId, Integer status) {
		String sql = " SELECT stu_class_id, stud_id, clas_id FROM student_class WHERE is_del = 0 AND stud_id = ? AND clas_id = ? AND stu_status = ? "; 
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(studId);
		params.add(clasId);
		params.add(status);
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTermInfo(Integer orgId, Integer termId) {
		String sql = " SELECT t.term_id, t.org_id, t.term_name, t.cur_year, t.term_type FROM term t WHERE t.is_del = 0 AND t.org_id = ? AND t.term_id = ? ";
		Session session = this.factory.getCurrentSession();
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(termId);
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getPlanSwitch(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  p.plan_switch,c.clas_id FROM  classes c INNER JOIN plan p ON c.plan_id = p.plan_id AND p.is_del = 0");
		sql.append(" WHERE c.is_del = 0 AND  c.org_id = ? ");
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		if(clasId!=null){
			sql.append(" AND c.clas_id = ? ");
			params.add(clasId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		return list;
	}

}
