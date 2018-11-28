package sng.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;

import sng.constant.Consts;
import sng.dao.BaseDaoEx;
import sng.dao.BaseSetCampusManageDao;
import sng.dao.BaseSetCategoryManageDao;
import sng.dao.BaseSetClassRoomManageDao;
import sng.dao.BaseSetCooperativeManageDao;
import sng.dao.BaseSetSubjectManageDao;
import sng.dao.ClassDao;
import sng.entity.Campus;
import sng.entity.Category;
import sng.entity.Classes;
import sng.entity.Cooperative;
import sng.entity.ImportRecord;
import sng.entity.StudentClass;
import sng.entity.Subject;
import sng.entity.TeacherClass;
import sng.pojo.ClassTeacher;
import sng.pojo.ClassToTeacher;
import sng.pojo.ImportClass;
import sng.pojo.ImportStudent;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.SessionInfo;
import sng.pojo.StudentRoster;
import sng.pojo.WXTemplateMessage;
import sng.pojo.base.Parent;
import sng.pojo.base.Student;
import sng.service.ChargeDetailService;
import sng.service.ChargeService;
import sng.service.ClassService;
import sng.service.StudentClassService;
import sng.service.TokenService;
import sng.util.CommonUtils;
import sng.util.Constant;
import sng.util.ESBPropertyReader;
import sng.util.EasyExcelUtil;
import sng.util.EnumLog;
import sng.util.ExcelExport;
import sng.util.ExcelUtil;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.LoggerUtil;
import sng.util.MoneyUtil;
import sng.util.Paging;
import sng.util.RabbitmqUtils;
import sng.util.SendMessageUtil;
import sng.util.ValidateObject;

/***
 * 
 *  @Company sjwy
 *  @Title: ClassServiceImpl.java 
 *  @Description: 班级管理实现类
 *	@author: shy
 *  @date: 2018年10月29日 下午1:25:49
 */
@Service
@Transactional
public class ClassServiceImpl implements ClassService{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static String ESB_REQUEST_URL = ESBPropertyReader.getProperty("esbRequestUrl");

	@Autowired 
	private ClassDao classDao;
	@Autowired
	private BaseDaoEx baseDaoEx;
	//教室查询dao
	@Autowired
	private BaseSetClassRoomManageDao classRoomDao;
	//类目查询dao
	@Autowired
	private BaseSetCategoryManageDao categoryManageDao;
	//科目查询dao
	@Autowired
	private BaseSetSubjectManageDao baseSetSubjectManageDao;
	//合作查询dao
	@Autowired
	private BaseSetCooperativeManageDao baseSetCooperativeManageDao;
	//校区查询dao
	@Autowired
	private BaseSetCampusManageDao baseSetCampusManageDao;
	@Autowired  
    private ApplicationContext ctx;
	@SuppressWarnings("unused")
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RabbitmqUtils rabbitmqUtils;
	/***
	 * 添加班级
	 * @throws ParseException 
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	@Transactional//(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void save(Map<String, Object> reqMap, SessionInfo sessionInfo) throws ParseException {
		Integer orgId = null;
		if (null != (String) reqMap.get("orgId")) {
			orgId = Integer.parseInt((String) reqMap.get("orgId"));
		} else {
			orgId = sessionInfo.getOrgId();
		}
		Integer camId = null;
		if (null != (String) reqMap.get("camId")) {
			camId = Integer.parseInt((String) reqMap.get("camId"));
		} else {
			camId = sessionInfo.getCamId();
		}
		String className = (String) reqMap.get("clasName");
		Integer clasType = Integer.parseInt((String) reqMap.get("clasType"));
		String classUnsetTime = (String) reqMap.get("classUnsetTime");
		Classes classes = new Classes();
		classes.setTerm_id(Integer.parseInt((String) reqMap.get("termId")));
		classes.setClas_name((String) reqMap.get("clasName"));
		classes.setClas_type(clasType);
		classes.setCategory_id(Integer.parseInt((String) reqMap.get("categoryId")));
		classes.setSubject_id(Integer.parseInt((String) reqMap.get("subjectId")));
		classes.setCam_id(Integer.parseInt((String) reqMap.get("camId")));
		classes.setClassroom_id(Integer.parseInt((String) reqMap.get("classroomId")));
		classes.setSize(Integer.parseInt((String) reqMap.get("size")));//size 班级容量
		classes.setAge_range((String) reqMap.get("ageRange"));//年龄范围
		classes.setStart_birthday(CommonUtils.stringToDate(reqMap.get("startBirthday").toString(), "yyyy-MM-dd"));
		
		classes.setEnd_birthday(CommonUtils.stringToDate(reqMap.get("endBirthday").toString(), "yyyy-MM-dd"));
		classes.setTotal_hours(Integer.parseInt((String) reqMap.get("totalHours")));//totalHours 总课时数
		classes.setClass_start_date(CommonUtils.stringToDate(reqMap.get("classStartDate").toString(), "yyyy-MM-dd"));//classStartDate 开课日期
		if (null == classUnsetTime) {
			classes.setClass_week(Integer.parseInt((String) reqMap.get("classWeek")));//classWeek 上课星期 (周一  周二  周三 。。。)
			String beginHours = (String) reqMap.get("classBeginTime");
			String endHours = (String) reqMap.get("classOverTime");
			StringBuffer sb1 = new StringBuffer("2000-01-01 ");
			StringBuffer sb2 = new StringBuffer("2000-01-01 ");
			sb1.append(beginHours).append(":00");
			sb2.append(endHours).append(":00");
			Date beginDate = CommonUtils.stringToDate(sb1.toString(), null);
			Date overDate = CommonUtils.stringToDate(sb2.toString(), null);
			classes.setClass_begin_time(beginDate);
			classes.setClass_over_time(overDate);
			classes.setClass_unset_time(null);
		} else {
			classes.setClass_unset_time(classUnsetTime);
			classes.setClass_week(8);//
			classes.setClass_begin_time(null);
			classes.setClass_over_time(null);
		}

		classes.setTuition_fees((String) reqMap.get("tuitionFees"));//学费标准
		classes.setCooperation_id(Integer.parseInt((String) reqMap.get("cooperationId")));//联合机构id(教师的所属机构)
		classes.setOrg_id(orgId);
		classes.setUsable_num(Integer.parseInt((String) reqMap.get("size")));//创建班级时，初始化可用名额  默认与班级容量相等
		classes.setIs_del(Constant.IS_DEL_NO);
		classes.setInsert_time(new Date());
		classes.setUpdate_time(new Date());
		//插入班级表
		Integer classId = (Integer) baseDaoEx.saveOne(classes);
		String techIds = (String) reqMap.get("techIds"); //教师id，多个以英文逗号分隔（121,145）
		String techNames = (String) reqMap.get("techNames"); //教师名称，多个以英文逗号分隔（张三,李四）
		//插入支付项目表
		// TODO Auto-generated method stub
		classes.setClas_id(classId);
		//chargeService.createChargeForNewClass(classes);
		//班级教师关系表
		if (StringUtils.isNotBlank(techIds)) {
			String[] strArray = null; 
	        strArray = techIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	        for (int i = 0; i < strArray.length; i++) {
	        	TeacherClass tc = new TeacherClass();
	        	tc.setClas_id(classId);
	        	tc.setTech_id(Integer.parseInt(strArray[i]));
	        	tc.setIs_del(Constant.IS_DEL_NO);
	        	tc.setInsert_time(new Date());
	        	baseDaoEx.saveOne(tc);
	        }
		} 
		//操作日志表
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作创建班级，操作对象是班级名称，操作内容是导入班级  班级名称  1
		String classBeginDate = "";
		if (null == classUnsetTime) {
			Integer week = Integer.parseInt((String) reqMap.get("classWeek"));//classWeek 上课星期 (周一  周二  周三 。。。)
			String beginHM = (String) reqMap.get("classBeginTime");
			String endHM = (String) reqMap.get("classOverTime");
			classBeginDate = Consts.WEEK_MAP.get(week) + " " + beginHM + " " + endHM ;
		} else {
			classBeginDate = classUnsetTime;
		}
        LoggerUtil.save(orgId, camId, classId, 1, className, EnumLog.OPERATION_CLASS_CREATE.getKey(), EnumLog.OPERATION_CLASS_CREATE.getValue() + " " + className + " " + techNames + " " + classBeginDate, userType, userTypeId, userTypeName, sessionInfo);
	}
	/***
	 * 查询班级列表
	 */
	@Override
	public Paging<ClassToTeacher> getClassList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, Integer camId, Integer classWeek, String techNames, Paging<ClassToTeacher> page) {
		return classDao.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames, page);
	}
	/***
	 * 导出班级学员列表
	 */
	@Override
	public List<ClassToTeacher> getClassIdList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, Integer camId, Integer classWeek, String techNames, String ids, Integer isAll) {
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name,c.clas_type,cam.cam_name, c.age_range,\r\n" + 
				"c.class_start_date, c.tuition_fees, IFNULL(c.size, 0) size, IFNULL(c.ss_num, 0) ss_num,\r\n" + 
				"cate.category_name, sub.subject_name ,cr.classroom_id, cr.building, cr.floor, cr.classroom_name, c.start_birthday,\r\n" + 
				"c.end_birthday, IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, c.cooperation_id, co.name,\r\n" + 
				"GROUP_CONCAT(CONCAT(t.tech_name, '_',u.user_mobile)) tech_names,\r\n" + 
				"GROUP_CONCAT(t.tech_name) tech_name,\r\n" + 
				"GROUP_CONCAT(t.tech_id) tech_id,\r\n" + 
				"GROUP_CONCAT(t.user_id) user_id, \r\n" +
				"(IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) num,\r\n" + 
				"IFNULL(ch.ss_money, 0) ss_money, IFNULL(ch.st_money, 0) st_money,\r\n" + 
				"\r\n" + 
				"CONCAT((IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) , '/', c.size) divide \r\n" + 
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
				"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0  \r\n" + 
				"AND c.org_id = ? \r\n");
		List<ClassToTeacher> list = new ArrayList<ClassToTeacher>();//返回结果集
		if (isAll != null && isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
			params.add(orgId);
			if(null != termId ) {
				sql.append(" AND c.term_id = ? ");
				params.add(termId);
			} else {
				//默认查询最晚的学期 
				sql.append(" AND c.term_id = (SELECT MAX(TERM_ID) FROM term WHERE NOW() > start_time) ");			
			}
			if(null != clasType ) {
				sql.append(" AND c.clas_type = ? ");
				params.add(clasType);
			}
			if(null != categoryId ) {
				sql.append(" c.category_id = ? ");
				params.add(categoryId);
			}
			if(null != subjectId ) {
				sql.append(" AND c.subject_id = ? ");
				params.add(subjectId);
			}
			if(null != camId ) {
				sql.append(" AND c.cam_id = ? ");
				params.add(camId);
			}
			if(null != classWeek ) {
				sql.append(" AND c.class_week = ? ");
				params.add(classWeek);
			}
			String group = " GROUP BY c.clas_id ";
			String order1 = "ORDER BY cam.cam_name , c.clas_name, cr.classroom_name ASC";
			sql.append(group);
			sql.append(order1);
			String finalSql = "";
			String order2 = " ORDER BY tt.cam_name , tt.clas_name, tt.classroom_name ASC ";
			if (StringUtils.isNotBlank(techNames)) {//模糊查询
				StringBuffer like1 = new StringBuffer(" SELECT tt.* FROM ( ");
				StringBuffer like2 = new StringBuffer(" ) tt\r\n" + 
						"where  LOCATE(? , tt.tech_names )>0 OR LOCATE(? , tt.clas_name )>0 ");
				params.add(techNames);
				params.add(techNames);
				finalSql = like1.append(sql).append(like2).append(order2).toString();
			} else {
				finalSql = sql.toString();
			}
			list = baseDaoEx.queryListBySql(finalSql, ClassToTeacher.class, params);
			//班级列表  实收金额
			for (ClassToTeacher ct : list) {
				String ssMoney = ct.getSs_money();
				String stMoney = ct.getSt_money();
				BigDecimal ss = new BigDecimal(ssMoney);
			    BigDecimal st = new BigDecimal(stMoney);
				String sjsMoney = ss.subtract(st).toString();
				ct.setSs_tuition(sjsMoney);	
			}
		} else {//选择班级id[]查询， 返回班级列表
			String ctSql = " SELECT c.clas_id, c.clas_name,c.clas_type,cam.cam_name, c.age_range,\r\n" + 
					"c.class_start_date, c.tuition_fees, IFNULL(c.size, 0) size, IFNULL(c.ss_num, 0) ss_num,\r\n" + 
					"cate.category_name, sub.subject_name ,cr.classroom_id, cr.building, cr.floor, cr.classroom_name, c.start_birthday,\r\n" + 
					"c.end_birthday, IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
					"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time, c.cooperation_id, co.name,\r\n" + 
					"GROUP_CONCAT(CONCAT(t.tech_name, '_',u.user_mobile)) tech_names,\r\n" + 
					"GROUP_CONCAT(t.tech_name) tech_name,\r\n" + 
					"GROUP_CONCAT(t.tech_id) tech_id,\r\n" + 
					"GROUP_CONCAT(t.user_id) user_id, \r\n" + 
					"(IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) num,\r\n" + 
					"IFNULL(ch.ss_money, 0) ss_money, IFNULL(ch.st_money, 0) st_money,\r\n" + 
					"\r\n" + 
					"CONCAT((IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) , '/', c.size) divide \r\n" + 
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
					"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
					"WHERE\r\n" + 
					"c.is_del = 0  \r\n" + 
					"AND c.org_id = ?\r\n" + 
					"AND c.clas_id = ?\r\n" + 
					"GROUP BY c.clas_id \r\n" +
					"ORDER BY cam.cam_name , c.clas_name, cr.classroom_name ASC " ;
			String[] strArray = null; 
	        strArray = ids.split(",");
	        for (int i = 0; i < strArray.length; i++) {
	        	List<Object> paramss = new ArrayList<>();
	        	paramss.add(orgId);
	        	paramss.add(strArray[i]);
	        	List<ClassToTeacher> cttList = baseDaoEx.queryListBySql(ctSql, ClassToTeacher.class, paramss);
	        	if (null != cttList && cttList.size() > 0) {
	        		ClassToTeacher ctt = cttList.get(0);
		        	//计算班级实际收学费
		        	String ssMoney = ctt.getSs_money();
					String stMoney = ctt.getSt_money();
					BigDecimal ss = new BigDecimal(ssMoney);
				    BigDecimal st = new BigDecimal(stMoney);
					String sjsMoney = ss.subtract(st).toString();
					ctt.setSs_tuition(sjsMoney);
					list.add(ctt);
	        	}
	        }
		}
		return list;
	}
	/***
	 * 获取当前机构下的某个班级某种学员状态的列表    
	 * status为null 查全部学员状态的学员
	 */
	@Override
	public List<Map<String, Object>> getStudentList(Integer orgId, String cidList, String status) {
		
		return classDao.getStudentList(orgId, cidList, status);
	}
	/***
	 * 获取当前机构下某班级的信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getById(Integer orgId, Integer clasId) {
		return classDao.getClasInfoById(orgId, clasId);
	}
	/***
	 * 修改班级 和 教师
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void update(Map<String, Object> reqMap, SessionInfo sessionInfo, List<Map<String, Object>> techList) {
		Integer orgId = sessionInfo.getOrgId();
		Integer clasId = Integer.parseInt((String) reqMap.get("clasId"));
		String clasName = (String) reqMap.get("clasName");
		Integer clasType = Integer.parseInt((String) reqMap.get("clasType"));
		Classes classes = new Classes();
		classes.setClas_id(clasId);
		classes.setOrg_id(orgId);
		classes.setTerm_id(Integer.parseInt((String) reqMap.get("termId")));
		classes.setPlan_id(Integer.parseInt((String) reqMap.get("planId")));
		classes.setClas_name(clasName);
		classes.setClas_type(Integer.parseInt((String) reqMap.get("clasType")));
		classes.setCategory_id(Integer.parseInt((String) reqMap.get("categoryId")));
		classes.setSubject_id(Integer.parseInt((String) reqMap.get("subjectId")));
		classes.setCam_id(Integer.parseInt((String) reqMap.get("camId")));
		classes.setClassroom_id(Integer.parseInt((String) reqMap.get("classroomId")));
		classes.setSize(Integer.parseInt((String) reqMap.get("size")));//size 班级容量
		classes.setAge_range((String) reqMap.get("ageRange"));//年龄范围
		try {
			/***
			 * 接受前段传来的时间字符串， 将字符串转为指定时间格式的时间Date
			 * 开始生日、结束生日、开课日期（yyyy-MM-dd）、添加时间、修改时间、删除时间（yyyy-MM-dd HH:mm:ss）
			 */
			Date startBirthDate = CommonUtils.stringToDate((String) reqMap.get("startBirthday"), "yyyy-MM-dd");
			Date endBirthDate = CommonUtils.stringToDate((String) reqMap.get("endBirthday"), "yyyy-MM-dd");
			classes.setStart_birthday(startBirthDate);
			classes.setEnd_birthday(endBirthDate);
			Date classSatrtDate = CommonUtils.stringToDate((String) reqMap.get("classStartDate"), "yyyy-MM-dd");
			classes.setClass_start_date(classSatrtDate);//classStartDate 开课日期
			if (null != reqMap.get("insertTime")) {
				Date insertTime =  CommonUtils.stringToDate((String) reqMap.get("insertTime"), null);
				classes.setInsert_time(insertTime);
			} else {
				classes.setInsert_time(new Date());
			}
			if (null != (String) reqMap.get("delTime")) {
				Date delTime =  CommonUtils.stringToDate((String) reqMap.get("delTime"), null);
				classes.setDel_time(delTime);
			} else {
				classes.setDel_time(null);
			}
			//Date updateTime =  CommonUtils.stringToDate((String) reqMap.get("updateTime"), null);
			classes.setUpdate_time(new Date());

		} catch (ParseException e1) {
			log.info("CommonUtils.stringToDate Ex :" + e1);
			e1.printStackTrace();
		}
		classes.setTotal_hours(Integer.parseInt((String) reqMap.get("totalHours")));//totalHours 总课时数
		classes.setClass_week(Integer.parseInt((String) reqMap.get("classWeek")));//classWeek 上课星期 (周一  周二  周三 。。。)
		
		String classUnsetTime = (String) reqMap.get("classUnsetTime");
		if (null == classUnsetTime) {
			String beginHours = (String) reqMap.get("classBeginTime");
			String endHours = (String) reqMap.get("classOverTime");
			StringBuffer sb1 = new StringBuffer("2000-01-01 ");
			StringBuffer sb2 = new StringBuffer("2000-01-01 ");
			sb1.append(beginHours).append(":00");
			sb2.append(endHours).append(":00");
			Date beginDate = null;
			Date overDate = null;;
			try {
				beginDate = CommonUtils.stringToDate(sb1.toString(), null);
				overDate = CommonUtils.stringToDate(sb2.toString(), null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			classes.setClass_begin_time(beginDate);
			classes.setClass_over_time(overDate);
			classes.setClass_unset_time(null);
		} else {
			classes.setClass_week(8);//
			classes.setClass_begin_time(null);
			classes.setClass_over_time(null);
			classes.setClass_unset_time(classUnsetTime);
		}
		
		classes.setTuition_fees((String) reqMap.get("tuitionFees"));//学费标准
		classes.setYs_num(Integer.parseInt((String) reqMap.get("ysNum")));//已缴费人数
		classes.setSs_num(Integer.parseInt((String) reqMap.get("ssNum")));//已退费人数
		classes.setUsable_num(Integer.parseInt((String) reqMap.get("usableNum")));
		classes.setIs_del(Constant.IS_DEL_NO);

		classes.setCooperation_id(Integer.parseInt((String) reqMap.get("cooperationId")));//联合机构id(教师的所属机构)
		//修改班级表
		baseDaoEx.updateOne(classes);
		String techIds = (String) reqMap.get("techIds"); //教师id，多个以英文逗号分隔（121,145）
		//  ？  支付项目表
  		// TODO Auto-generated method stub
		//班级教师关系表
		String sql = " UPDATE teacher_class SET is_del = 1 WHERE clas_id = ? ";
		if (StringUtils.isBlank(techIds)) {
			//前段返回的班级教师为空，更新该班级与之前老师的关系
			if (null != techList && techList.size() > 0) {
				for (Map map : techList) {
					List<Object> params = new ArrayList<Object>();
					Integer techId = (Integer) map.get("tech_id");
		        	params.add(techId);
		        	baseDaoEx.executeSql(sql, params);
		        }
			}
		} else {
			String[] strArray = null; 
			strArray = techIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
			List<Integer> techArrayIds = new ArrayList<>(); 
			for (int i = 0; i < strArray.length; i++) {
				techArrayIds.add(Integer.parseInt(strArray[i]));
			}
			//查询当前班级下的所有老师
			List<Integer> techListIds = new ArrayList<>(); 
			if (null != techList) {
				for (Map map : techList) {
					Integer techId = (Integer) map.get("tech_id");
					techListIds.add(techId);
				}
			}
			//listAll = new ArrayList<String>(new LinkedHashSet<>(listAll));//去重
			if (null == techListIds || techListIds.size() == 0) {//原 班级与教师 无关系
				for (int i = 0; i < techArrayIds.size(); i++) {//前段返回的教师id, 将前段返回的教师插入到关系表中
					TeacherClass tc = new TeacherClass();
					tc.setClas_id(clasId);
					tc.setTech_id(techArrayIds.get(i));
					tc.setInsert_time(new Date());
					tc.setIs_del(Constant.IS_DEL_NO);
					baseDaoEx.saveOne(tc);
				}
			} else {
				//将原 班级与教师有关系的老师  打上 删除标记
				String delSql = " UPDATE teacher_class SET is_del = 1 WHERE clas_id = ? AND tech_id = ? ";
				for (int i = 0; i < techListIds.size(); i++) {
					List<Object> delParams = new ArrayList<Object>();
					delParams.add(clasId);
					delParams.add(techListIds.get(i));
					baseDaoEx.executeSql(delSql, delParams);
				}
				//1. 根据班级id和教师id先查询打上删除标记的 班级教师关系  是否存在
				for (int i = 0; i < techArrayIds.size(); i++) {//前段返回的教师id
					String tcSql = " SELECT * FROM teacher_class WHERE is_del = 1 AND clas_id = ? AND tech_id = ? ";
					List<Object> tcParams = new ArrayList<Object>();
					tcParams.add(clasId);
					tcParams.add(techArrayIds.get(i));
					List<Map<String, Object>> tcList = baseDaoEx.queryListBySql(tcSql, tcParams);
					if (null != tcList) {
						if (tcList.size() != 1) {//该教师不在有删除标记的 教师班级关系表中
							TeacherClass tc = new TeacherClass();
							tc.setClas_id(clasId);
							tc.setTech_id(techArrayIds.get(i));
							tc.setInsert_time(new Date());
							tc.setIs_del(Constant.IS_DEL_NO);
							baseDaoEx.saveOne(tc);
						} else {// 该教师在已有删除标记的 关系表中
							//将原 班级与教师有关系的老师  打上 删除标记
							String ySql = " UPDATE teacher_class SET is_del = 0 WHERE clas_id = ? AND tech_id = ? ";
							List<Object> yParams = new ArrayList<Object>();
							yParams.add(clasId);
							yParams.add(techArrayIds.get(i));
							baseDaoEx.executeSql(ySql, yParams);
						}
					}
				}
			}
		}
		
		//操作日志表
		Integer camId = sessionInfo.getCamId();
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
        LoggerUtil.save(orgId, camId, clasId, clasType, clasName, EnumLog.OPERATION_CLASS_EDIT.getKey(), EnumLog.OPERATION_CLASS_EDIT.getValue() + clasName, userType, userTypeId, userTypeName, sessionInfo);
	}
	/***
	 * 获取当前班级下的所有老师
	 */
	@Override
	public List<Map<String, Object>> getTecherIds(Integer orgId, Integer clasId) {
		return classDao.getTecherIds(orgId, clasId);
	}
	/***
	 * 根据机构id和证件编号，返回该学员所在的班级列表
	 */
	@Override
	public List<Map<String, Object>> getStudentInfo(Integer orgId, String studCard) {
		String sql = " SELECT c.clas_id, s.stud_name, s.stud_id, u.user_idnumber,u.user_type, c.usable_num,c.class_week, \r\n" + 
				"class_begin_time, class_over_time, \r\n" + 
				"DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time1, DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time1, \r\n" + 
				"c.class_unset_time, c.start_birthday, c.end_birthday \r\n" + 
				"FROM edugate_base.org_user u\r\n" + 
				"INNER JOIN edugate_base.student s ON u.user_id = s.user_id\r\n" + 
				"INNER JOIN newsng.student_class sc ON s.stud_id = sc.stud_id\r\n" + 
				"INNER JOIN newsng.classes c ON c.clas_id = sc.clas_id\r\n" + 
				"WHERE u.is_del = 0\r\n" + 
				"AND c.is_del = 0\r\n" + 
				"AND sc.is_del = 0\r\n" + 
				"AND s.is_del = 0\r\n" + 
				"AND u.org_id = ?\r\n" + 
				"AND u.user_idnumber = ? ";
		//"DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time1, DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time1, \r\n" + 
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(studCard);
		List<Map<String, Object>> stuInfoList = baseDaoEx.queryListBySql(sql, params);
		return stuInfoList;
	}
	
	/***
	 * 插班
	 */
	@Override
	public void joinClass(Map<String, Object> reqMap, SessionInfo sessionInfo, Integer orgId, String studName, Integer studId, Integer clasId, Integer clasType, String tuitionFee, Integer insertPayType) {
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		if (clasType == null) {//根据clasId获取班级类型
			Classes classes = classDao.getClassInfo(orgId, clasId);
			clasType = classes.getClas_type();
		}
		//String clasName = (String) reqMap.get("clasName");
		StudentClass sc = new StudentClass();
		sc.setStudId(studId);
		sc.setClasId(clasId);
		sc.setCamId(sessionInfo.getCamId());
		sc.setIsPrint(0);
		sc.setIsDel(Constant.IS_DEL_NO);
		sc.setInsertTime(new Date());
		sc.setQuotaHold(Constant.IS_QUOTA_HOLD_YES);
		if (null != insertPayType) {
			if (insertPayType == 1) {//1：立即缴费插班 
				sc.setStuStatus(3);//报名待缴费
			}
			if (insertPayType == 2) {//2：老生续费插班
				sc.setStuStatus(1);//老生待续费
			}
		}
		baseDaoEx.saveOne(sc);
		//插入支付详情表   班级id 机构id 校区id 学员id 学员名称
		Integer camId = sessionInfo.getCamId();
		//...................................
		
		//插入操作日志表
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
        LoggerUtil.save(orgId, camId, studId, clasType, studName, EnumLog.OPERATION_STU_JOIN_CLASS.getKey(), EnumLog.OPERATION_STU_JOIN_CLASS.getValue() + studName, userType, userTypeId, userTypeName, sessionInfo);
		//根据班级id查询 班级的科目、类目、班级名称、上课星期及上课时间
        Map<String, Object> clasMap = classDao.getTechListByClasId(clasId);
        String clasName = (String) clasMap.get("clas_name");
        String subjectName = (String) clasMap.get("subject_name");
        String categoryName = (String) clasMap.get("category_name");
        String classUnsetTime = (String) clasMap.get("class_unset_time");
        String techIds = (String) clasMap.get("tech_ids");//教师id
        String[] techIdArr = null;
        if (techIds != null && techIds.length() > 0) {
        	techIdArr = techIds.split(",");
    		//发送插班成功信息
        	for (int i = 0; i < techIdArr.length; i++) {
        		Map<String,Object> msgMap = new HashMap<String, Object>();
        		String openId = classDao.getWXOpenId(Integer.parseInt(techIdArr[i]));
        		msgMap.put("orgId", orgId);
        		msgMap.put("studName", studName);
        		msgMap.put("tuitionFee", tuitionFee);
        		msgMap.put("clasName", clasName);
        		msgMap.put("subjectName", subjectName);
        		msgMap.put("categoryName", categoryName);
        		msgMap.put("classUnsetTime", classUnsetTime);
        		msgMap.put("open_id", openId);
        		//将插班信息发送到q
        		rabbitmqUtils.sendMessage("joinClassExchange", "joinClassQueueKey", msgMap);
        	}
        }
	}
	
	public void consumptionJoinClass(Map<String, Object> msgMap) {
		String orgId = (String) msgMap.get("orgId");
		String studName = (String) msgMap.get("studName");
		String tuitionFee = (String) msgMap.get("tuitionFee");
		String clasName = (String) msgMap.get("clasName");
		String subjectName = (String) msgMap.get("subjectName");
		String categoryName = (String) msgMap.get("categoryName");
		String classUnsetTime = (String) msgMap.get("classUnsetTime");
		String openId = (String) msgMap.get("open_id");
		
		Map<String, Object> mqMap = new HashMap<String, Object>();
		mqMap.put("first", "您的班级有学生缴费插班！");
		mqMap.put("keyword1", subjectName + " (" + clasName + ")");
		mqMap.put("keyword2", classUnsetTime);
		mqMap.put("keyword3", studName);
		mqMap.put("keyword4", tuitionFee);
		mqMap.put("remark", "你可以在学费统计中进行查看。");
		mqMap.put("open_id", openId);
		try {
			this.sendWX(Integer.parseInt(orgId),mqMap);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送插班信息
	 * @param orgId
	 * @throws Exception
	 */
	public void sendWX(Integer orgId, Map<String,Object> mqMap) throws Exception {
		try {
			String templateId = tokenService.getWxTemplateId(String.valueOf(orgId), "插班");
			mqMap.put("templateId", templateId);
			WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(mqMap);
			String access_token = tokenService.getAccessTokenByOrg_Id(orgId);
			SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("插班微信推送失败"+ex.getMessage());
		}
	}
	/***
	 * 获取当前班级下的上课时间、班级可容纳人数、起始生日等
	 */
	@Override
	public List<Map<String, Object>> getCurClassInfo(Integer orgId, Integer clasId) {
		String sql = " SELECT c.usable_num,c.class_week, \r\n" + 
				"DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time1, DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time1, \r\n" + 
				"class_begin_time, class_over_time,\r\n" +
				"c.class_unset_time, c.start_birthday, c.end_birthday\r\n" + 
				"FROM newsng.classes c\r\n" + 
				"WHERE\r\n" + 
				"c.org_id = ? \r\n" + 
				"AND c.clas_id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(clasId);
		List<Map<String, Object>> stuInfoList = baseDaoEx.queryListBySql(sql, params);
		return stuInfoList;
	}
	
	/***
	 * 删除班级
	 */
	@Override
	public void delClass(Integer orgId, Integer clasId) {
		String sql = " UPDATE classes SET is_del = 1 WHERE clas_id = ? ";
		List<Object> params = new ArrayList<Object>();
    	params.add(clasId);
    	baseDaoEx.executeSql(sql, params);
	}
	/***
	 * 查询班级的人数（除 报名已作废  退费已完成）
	 */
	@Override
	public Integer getClassNum(Integer orgId, Integer clasId) {
		return classDao.getClassNum(orgId, clasId);
	}
	/***
	 * 查询班级的实收学费
	 */
	@Override
	public BigDecimal getClassFees(Integer orgId, Integer clasId) {
		return classDao.getClassFees(orgId, clasId);
	}
	/***
	 * 当前学期下，根据前段传来的班级名称 查询数据库是否有这个名称的班级
	 */
	@Override
	public Integer getClassCount(Integer orgId, Integer termId, String clasName, Integer clasId) {
		String sql = " SELECT COUNT(1) FROM newsng.classes c WHERE\r\n" + 
				"c.is_del = 0 AND  c.org_id = ? AND c.term_id = ? AND c.clas_name = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(termId);
		params.add(clasName);
		if (null != clasId) {
			sql += "  AND clas_id NOT IN (?) ";
			params.add(clasId);
		}
		int classCount = baseDaoEx.countBySql(sql, params);
		return classCount;
	}
	/***
	 * 获取当前机构下，某一学期，某种班级类型（新生班/老生班）的班级列表
	 * clasType 班级类型为null 查询全部的
	 */
	@SuppressWarnings({ "rawtypes"})
	@Override
	public Map getClassListByTerm(Integer orgId, Integer termId, Integer clasType) {
		//1':'老生班','2':'新生班
		return classDao.getClassListByTerm(orgId, termId, clasType);
	}
	/***
	 * 根据 校区 所属教学楼 所属楼层 教师名称 判断在基础数据是否存在该教室
	 */
	@Override
	public boolean existsClassromm(Integer camId, String building, String floor, String classroomName) {
		/*ParamObj paramObj = new ParamObj();
		paramObj.setCam_id(camId);
		boolean flag = false;
		List<Classroom> classroomList = classRoomDao.queryClassRoomList(paramObj);
		if (null != classroomList) {
			for (Classroom room : classroomList) {
				String roomBuilding = room.getBuilding();
				String roomClassroomName = room.getClassroom_name();
				String roomFloor = room.getFloor();
				if (null != building && null != floor && null != classroomName) {
					if (building.equals(roomBuilding)) {
						return flag;
					}
					if (roomFloor.equals(roomFloor)) {
						return flag;
					}
					if (roomClassroomName.equals(classroomName)) {
						return flag;
					}
				} else {
					return flag;
				}
			}
		}*/
		return true;
	}
	
	/***
	 * 根据 机构 类目 判断在基础数据是否存在该类目
	 */
	@Override
	public boolean existsCategory(Integer orgId, String categoryName) {
		boolean flag = false;
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setIsDel(0); // 0:否 1:是
		List<Category> list = categoryManageDao.queryCategoryListInfo(paramObj);
		if (null != list) {
			for (Category cate : list) {
				String cateCategoryName = cate.getCategory_name();
				if (categoryName != null) {
					if (categoryName.equals(cateCategoryName)) {
						return flag;
					}
				} else {
					return flag;
				}
			}
		}
		return true;
	}
	
	/***
	 * 根据 机构id 科目 判断在基础数据是否存在该科目
	 */
	@Override
	public boolean existsSubject(Integer orgId, String subjectName) {
		boolean flag = false;
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setIsDel(0);
		List<Subject> list = baseSetSubjectManageDao.querySubjectListInfo(paramObj);
		if (null != list) {
			for (Subject sub : list) {
				String subSubject = sub.getSubject_name();
				if (null != subjectName) {
					if (subjectName.equals(subSubject)) {
						return flag;
					}
				} else {
					return flag;
				}
			}
		}
		return true;
	}
	
	/***
	 * 根据 校区获取所有教室信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getClassromm(Integer camId) {
		Map classRoomMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setCam_id(camId);
		paramObj.setNeedCount(false);
		Paging classroomPage = classRoomDao.queryCampusAndClassRoomInfo(paramObj);
		//List<Classroom> classroomList = classRoomDao.queryClassRoomList(paramObj);
		List classroomList = classroomPage.getData();
		if (null != classroomList) {
			for (int i = 0; i < classroomList.size(); i++) {
				Map classroomMap = (Map) classroomList.get(i);
				Integer clasroomId = (Integer) classroomMap.get("classroom_id");
				String roomBuilding = (String) classroomMap.get("building");
				String roomFloor = (String) classroomMap.get("floor");
				String roomClassroomName = (String) classroomMap.get("classroom_name");
				String valueStr = roomBuilding + "_" + roomFloor + "_" + roomClassroomName;
				classRoomMap.put(valueStr, clasroomId);
			}
		}
		return classRoomMap;
	}
	
	/***
	 * 根据 机构获取所有类目信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getCategory(Integer orgId) {
		Map categoryMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setIsDel(0); // 0:否 1:是
		List<Category> cateList = categoryManageDao.queryCategoryListInfo(paramObj);
		if (null != cateList) {
			for (Category cate : cateList) {
				String categoryName = cate.getCategory_name();
				Integer categoryId = cate.getCategory_id();
				categoryMap.put(categoryName, categoryId);
			}
		}
		return categoryMap;
	}
	
	/***
	 * 根据 机构获取所有科目信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getSubject(Integer orgId) {
		Map subjectMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setIsDel(0);
		List<Subject> subList = baseSetSubjectManageDao.querySubjectListInfo(paramObj);
		if (null != subList) {
			for (Subject sub : subList) {
				Integer subjectId = sub.getSubject_id();
				String subjectName = sub.getSubject_name();
				subjectMap.put(subjectName, subjectId);
	 		}
		}
		return subjectMap;
	}
	
	/***
	 * 根据 机构获取所有教师信息
	 */
	@SuppressWarnings({ "rawtypes"})
	@Override
	public Map getTeacher(Integer orgId) {
		return classDao.getTeacher(orgId);
	}
	/***
	 * 根据 机构获取所有联合机构信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getCooperative(Integer orgId) {
		Map cooMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setNeedCount(false);
		Paging<Cooperative> paging = baseSetCooperativeManageDao.queryCooperativeListInfo(paramObj);
		List<Cooperative> cooList = paging.getData();
		for (Cooperative coo : cooList) {
			cooMap.put(coo.getName(), coo.getCooperative_id());
		}
		return cooMap;
	}
	/***
	 * 根据 机构获取所有校区信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getCampus(Integer orgId) {
		Map campusMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setNeedCount(false);
		Paging<Campus> paging = baseSetCampusManageDao.queryCampusListInfo(paramObj);
		List<Campus> camList = paging.getData();
		for (Campus campus : camList) {
			campusMap.put(campus.getCam_name(), campus.getCam_id());
		}
		return campusMap;
	}
	
	
	/***
	 * 插班
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	@Transactional//(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public void saveClass(ImportClass iClass, Map allTechMap, SessionInfo sessionInfo) {
		Classes classes = iClass.getClasses();
		classes.setIs_del(Constant.IS_DEL_NO);
		classes.setInsert_time(new Date());
		classes.setUpdate_time(new Date());
		//插入班级表
		Integer classId = (Integer) baseDaoEx.saveOne(classes);
		String techNames = iClass.getTech_names(); //教师名称_手机号码，多个以英文逗号分隔（张三_18811223344,李四_15812378945）
		//插入班级教师关系表
		if (null != techNames) {//教师名称不为空，插入教师班级关系表
			String[] techNameArray = techNames.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
	        if (0 != techNameArray.length) {
	        	for (int i = 0; i < techNameArray.length; i++) {
	        		TeacherClass tech = new TeacherClass();
	        		Integer techId = (Integer) allTechMap.get(techNameArray[i]);
	        		tech.setTech_id(techId);
	        		tech.setInsert_time(new Date());
	        		tech.setIs_del(Constant.IS_DEL_NO);
			        tech.setClas_id(classId);
			        Integer tcId = (Integer) baseDaoEx.saveOne(tech);
		        }
	        }
		}
				
		 //插入支付项目表
		//获取当前班级信息实体
		classes.setClas_id(classId);
		//chargeService.createChargeForNewClass(classes);
		//操作日志表
		Integer orgId = sessionInfo.getOrgId();
		Integer camId = sessionInfo.getCamId();
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		
        LoggerUtil.save(orgId, camId, classId, classes.getClas_type(), classes.getClas_name(), EnumLog.OPERATION_CLASS_CREATE.getKey(), EnumLog.OPERATION_CLASS_CREATE.getValue() + classes.getClas_name(), userType, userTypeId, userTypeName, sessionInfo);
	}
	
	/***
	 * @Description:批量导出班级
	 * @param fin 文件流
	 * @param orgId 机构id
	 * @param termId 学期id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Map importClass(HttpSession session, SessionInfo sessionInfo, InputStream fin, Integer orgId, Integer termId, String suffix) {
		Map returnMap = new HashMap<>();
		LinkedHashMap<String, String> clasMap = new LinkedHashMap<String, String>();
		clasMap.put("班级名称", "clas_name");
		clasMap.put("班级类别", "clas_type_name");
		clasMap.put("所属类目", "category_name");
		clasMap.put("所属科目", "subject_name");
		clasMap.put("所属校区", "cam_name");
		clasMap.put("所在教学楼", "building");
		clasMap.put("所在楼层", "floor");
		clasMap.put("所在教室", "classroom_name");
		clasMap.put("班级容量", "size");
		clasMap.put("年龄要求", "age_range");
		clasMap.put("起始生日", "start_birthday");
		clasMap.put("结束生日", "end_birthday");
		clasMap.put("总课时数", "total_hours");
		clasMap.put("开课日期", "class_start_date");
		clasMap.put("上课星期", "class_week_name");
		clasMap.put("上课时间", "class_begin_time");
		clasMap.put("下课时间", "class_over_time");
		clasMap.put("不固定时间", "class_unset_time");
		clasMap.put("学费标准", "tuition_fees");
		clasMap.put("职教老师", "tech_names");
		clasMap.put("联合机构", "name");
		Integer camId = sessionInfo.getCamId();

		//校验数据  不合法添加
		List<ClassTeacher> errorList = new ArrayList<ClassTeacher>();
		//获取当前学期下的所有班级
		Integer allClasType = null;//新生班和老生班
		Map<String,Integer> allClassMap = this.getClassListByTerm(orgId, termId, allClasType);
		//获取当前机构下的所有校区
		Map<String,Integer> allCampusMap = this.getCampus(orgId);
		//获取当前校区的所有教室
		Map<String,Integer> allClassRoomMap = this.getClassromm(camId);
		//获取当前机构的所有类目
		Map<String,Integer> allCategoryMap = this.getCategory(orgId);
		//获取当前机构的所有科目
		Map<String,Integer> allSubjectMap = this.getSubject(orgId);
		//获取当前机构的所有老师
		Map<String,Integer> allTechMap = this.getTeacher(orgId);
		//获取当前机构的所有合作机构
		Map<String,Integer> cooMap = this.getCooperative(orgId);
		List<Object> objectList = new ArrayList<Object>();
		if ("xls".equals(suffix)) {
			objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLS);
		}
		if ("xlsx".equals(suffix)) {
			objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLSX);
		}
		//读取excel数据转成list
		List<ClassTeacher> ctList = (List) objectList;
		//List<ClassTeacher> ctList = ExcelImport.excelToList(fin, "", ClassTeacher.class, clasMap);
		Integer errCount = 0;
		//判断list中的数据是否合法
		if (null != ctList) {
			for (ClassTeacher ct : ctList) {
				
				StringBuffer errorBuffer = new StringBuffer("");
				//校验excel中的每一行数据是否为合法数据，默认合法
				boolean validateFlag = true;
				//校验班级名称
				String clasName = ct.getClas_name();
				if (null != clasName) {
					clasName = clasName.trim();
					boolean clasNameFlag = allClassMap.containsKey(clasName);
					if (clasNameFlag == true) {
						errorBuffer.append("当前学期下的班级名称不可重复;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("班级名称不能为空;");
					validateFlag = false;
				}
				//校区名称校验
				String campusName = ct.getCam_name();
				if (campusName != null) {
					campusName = campusName.trim();
					boolean campusFlag = allCampusMap.containsKey(campusName);
					if (campusFlag == false) {
						errorBuffer.append("校区名称不存在基础校区数据中;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("校区名称不能为空;");
					validateFlag = false;
				}
				//校验类目名称
				String categoryName = ct.getCategory_name();
				if (categoryName != null) {
					categoryName = categoryName.trim();
					boolean categoryFlag = allCategoryMap.containsKey(categoryName);
					if (categoryFlag == false) {
						errorBuffer.append("类目名称不存在基础类目数据中;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("类目名称不能为空;");
					validateFlag = false;
				}
				//校验教室名称
				String building = ct.getBuilding();
				String floor = ct.getFloor();
				String classroomName = ct.getClassroom_name();
				if (null != building && null != floor && null != classroomName) {
					building = building.trim();
					floor = floor.trim();
					classroomName = classroomName.trim();
					String bfc = building + "_" + floor + "_" + classroomName;

					boolean classroomFlag = allClassRoomMap.containsKey(bfc);
					if (classroomFlag == false) {
						errorBuffer.append("所在教室、所在教学楼、所在楼层不存在基础教室数据中;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("所在教室、所在教学楼、所在楼层不能为空;");
					validateFlag = false;
				}
				//校验科目名称
				String subjectName = ct.getSubject_name();
				if (null != subjectName) {
					subjectName = subjectName.trim();
					boolean subjectFlag = allSubjectMap.containsKey(subjectName);
					if (subjectFlag == false) {
						errorBuffer.append("科目名称不存在基础科目数据中;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("科目名称不能为空;");
					validateFlag = false;
				}

				//校验 列表中的值是否合法
				boolean legal = true;
				legal = ValidateObject.validate(ct, errorBuffer, legal);
				if (legal == false) {
					validateFlag = false;
				}
				
				//职教老师如果不在老师基础数据库存在，则建立个新教师数据，多个教师用半角“,”分割。  例如： 张三_13536251478,赵天_18812345678
				String techNames = ct.getTech_names();
				if (null != techNames) {//
					String[] techArray = techNames.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
			        if (0 != techArray.length) {
			        	for (int i = 0; i < techArray.length; i++) {
				        	//String[] nameAndPhone = techArray[i].split(",");
				        	//String techName = nameAndPhone[0];
				        	//String phone = nameAndPhone[1];
				        	//String valueStr = techName + "_" + phone; 
				        	boolean techFlag = allTechMap.containsKey(techArray[i]);
				        	if (techFlag == false) {
				        		errorBuffer.append("教师名称不存在基础教师数据中;");
				        		validateFlag = false;
				        	}
				        }
			        }

				}
				//联合机构如果不为空，必须在合作机构中名字一致。
				String cooName = ct.getName();
				if (null != cooName) {
					cooName = cooName.trim();
					boolean cooFlag = cooMap.containsKey(cooName);
					if (cooFlag == false) {
						errorBuffer.append("联合机构的名称不存在合作结构数据中;");
						validateFlag = false;
					}
				}
				
				if (validateFlag == false) {// 当前条校验不通过
					//将错误信息的当前数据添加到错误列表中
					ct.setError_msg(errorBuffer.toString());
					errorList.add(ct);
					errCount++;
				} else {// 当前条数据校验合法
					//将合法数据添加到正确列表中
					ImportClass iClass = new ImportClass();//班级+教师
					Classes clas = new Classes();//班级

					clas.setOrg_id(orgId);//添加机构id
					clas.setCam_id(camId);
					clas.setTerm_id(termId);//学期id
					clas.setClas_name(ct.getClas_name());//班级名称
					Integer clasType = null;
					if (ct.getClas_type_name().equals("老生班")) {
						clasType = 1;
					} else if (ct.getClas_type_name().equals("新生班")) {
						clasType = 2;
					}
					clas.setClas_type(clasType);//班级类型
					//根据类目名称获取类目id
					Integer categoryId = (Integer) allCategoryMap.get(ct.getCategory_name());
					clas.setCategory_id(categoryId);//类目id
					//根据科目名称获取科目id
					Integer subjectId = (Integer) allSubjectMap.get(ct.getSubject_name());
					clas.setSubject_id(subjectId);//科目id
					//根据所属教学楼+所属楼层+教室名称获取教室id
					Integer classRoomId = (Integer) allClassRoomMap.get(ct.getBuilding() + "_" + ct.getFloor() + "_" + ct.getClassroom_name());
					clas.setClassroom_id(classRoomId);//教室id
					//根据校区名称获取去校区id
					Integer campusId = (Integer) allCampusMap.get(ct.getCam_name());
					clas.setCam_id(campusId);//校区id
					clas.setSize(Integer.parseInt(ct.getSize()));//班级容量
					clas.setUsable_num(Integer.parseInt(ct.getSize()));//创建班级时，初始化可用名额  默认与班级容量相等
					clas.setAge_range(ct.getAge_range());//年龄要求
					try {
						clas.setStart_birthday(CommonUtils.stringToDate(ct.getStart_birthday(), "yyyy-MM-dd"));
						//开始生日
						clas.setEnd_birthday(CommonUtils.stringToDate(ct.getEnd_birthday(), "yyyy-MM-dd"));//结束生日
						clas.setClass_start_date(CommonUtils.stringToDate(ct.getClass_start_date(), "yyyy-MM-dd"));//开课日期
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					clas.setTotal_hours(Integer.parseInt(ct.getTotal_hours()));//总课时数
					Integer classWeek = 0;
					if (null != ct.getClass_unset_time()) {//不固定时间 不为空
						classWeek = 8;
						clas.setClass_week(classWeek);//上课星期
						clas.setClass_begin_time(null);//开始时间
						clas.setClass_over_time(null);//结束时间
						clas.setClass_unset_time(ct.getClass_unset_time());//不固定时间
					} else {//不固定时间 为空
						String classBeginTime = ct.getClass_begin_time();
						String classOverTime = ct.getClass_over_time();
						StringBuffer sb1 = new StringBuffer("2000-01-01 ");
						StringBuffer sb2 = new StringBuffer("2000-01-01 ");
						sb1.append(classBeginTime).append(":00");
						sb2.append(classOverTime).append(":00");
						Date classBeginDate = null;
						Date classOverDate = null;
						try {
							classBeginDate = CommonUtils.stringToDate(sb1.toString(), null);
							classOverDate = CommonUtils.stringToDate(sb2.toString(), null);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						clas.setClass_week(ValidateObject.getClassWeek(classWeek, ct.getClass_week_name()));
						clas.setClass_begin_time(classBeginDate);
						clas.setClass_over_time(classOverDate);
						clas.setClass_unset_time(null);
					}
					clas.setTuition_fees(ct.getTuition_fees());//学费
					clas.setCooperation_id(cooMap.get(ct.getName()));//合作机构
					iClass.setTech_names(techNames);
					iClass.setClasses(clas);
					// 手动控制事务
					HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
					TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
					//保存
					try {
						this.saveClass(iClass, allTechMap, sessionInfo);
						//导入时保存操作日志，格式为操作动作导入班级，操作对象是科目 （班级名）教师名 上课时间，操作内容是批量导入班级 班级名。
				        //LoggerUtil.save(orgId, camId, clas.getClas_id(), 2, clas.getClas_name(), EnumLog.OPERATION_CLASS_IMPORT.getKey(), "批量" + EnumLog.OPERATION_CLASS_IMPORT.getValue()+ " " + clas.getClas_name(), userType, userTypeId, userTypeName, sessionInfo);
						// 所有处理完成后，提交事务
						txManager.commit(txStatus);
					} catch (Exception e) {
						e.printStackTrace();
						ct.setError_msg("数据库服务异常;");
						errorList.add(ct);
						errCount++;
						txManager.rollback(txStatus);
					}

				}
				
			}
			session.setAttribute("errNum", errCount);
			session.setAttribute("sucNum", ctList.size()-errCount);
			session.setAttribute("total", ctList.size());
			session.setAttribute("classExcel", errorList);
			/***
			 * 将导入数据进行记录
			 */
			ImportRecord ir = new ImportRecord();
			ir.setCamId(camId);//校区
			ir.setCorrectCount(ctList.size()-errCount);
			ir.setErrorCount(errCount);
			ir.setInsertTime(new Date());
			ir.setOrgId(orgId);
			ir.setTotalCount(ctList.size());//导入总数
			ir.setIsDel(Constant.IS_DEL_NO);
			ir.setResult(1);//导入状态 1成功 0失败
			ir.setCreater("admin");//操作人
			baseDaoEx.saveOne(ir);
			returnMap.put("total", ctList.size());
			returnMap.put("errNum", errCount);
			returnMap.put("sucNum", ctList.size()-errCount);
			JSONObject json = new JSONObject();
			json.put("key", session.getAttribute("classExcel"));
			System.out.println("key ...................... :" + json);
		} else {
			returnMap.put("total", 0);
			returnMap.put("errNum", 0);
			returnMap.put("sucNum", 0);
			return returnMap;
		}
		
		return returnMap;
	}
	/***
	 * @Description:批量导入学生
	 * @param fin 文件流
	 * @param orgId 机构id
	 * @param termId 学期id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Map importStudent(HttpSession session, SessionInfo sessionInfo, InputStream fin, Integer orgId, Integer termId, String suffix) {
		Map returnMap = new HashMap();
		//班级名称、学员名称、证件号码、学员状态
		LinkedHashMap<String, String> stuMap = new LinkedHashMap<String, String>();
		stuMap.put("班级名称", "clas_name");
		stuMap.put("学员名称", "stud_name");
		stuMap.put("证件号码", "user_idnumber");
		stuMap.put("学员状态", "stu_status");
		Integer camId = sessionInfo.getCamId();
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		//获取当前学期下的所有老班级
		Integer clasType = 1;//老生班
		Map<String,Integer> allClassMap = this.getClassListByTerm(orgId, termId, clasType);
		//获取当机构下所有的学员（已认证）
		Map<String,Integer> allStuMap = this.getStudent(orgId);
		
		//校验数据  不合法添加
		List<ImportStudent> errorList = new ArrayList<ImportStudent>();
		Integer errCount = 0;
		//读取excel数据转成list
		//List<ImportStudent> ctList = ExcelImport.excelToList(fin, "", ImportStudent.class, stuMap);
		List<Object> objectList = new ArrayList<Object>();
		if ("xls".equals(suffix)) {
			objectList = EasyExcelUtil.readExcelWithModel(fin, ImportStudent.class, ExcelTypeEnum.XLS);
		}
		if ("xlsx".equals(suffix)) {
			objectList = EasyExcelUtil.readExcelWithModel(fin, ImportStudent.class, ExcelTypeEnum.XLSX);
		}
		List<ImportStudent> ctList = (List) objectList;
		if (null != ctList) {
			for (ImportStudent stu : ctList) {
				//校验excel中的每一行数据是否为合法数据，默认合法
				boolean validateFlag = true;
				String clasName = stu.getClas_name();
				String stuName = stu.getStud_name();
				String idNumber = stu.getUser_idnumber();
				String stuStatus = stu.getStu_status();
				StringBuffer errorBuffer = new StringBuffer("");
				//为空校验：班级名称、学员名称、证件号码，必须都是必填项
				//校验班级名称
				if (null != clasName) {
					clasName = clasName.trim();
					boolean clasNameFlag = allClassMap.containsKey(clasName);
					if (clasNameFlag == false) {
						errorBuffer.append("当前班级名称应为老生班,不支持新生班导入学员;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("班级名称不能为空;");
					validateFlag = false;
				}
				//校验 学员名称和证件号码  并且 根据学员名称和证件号码确认在基础数据中存在该学员并且已认证
				if (null != stuName && null != idNumber) {
					stuName = stuName.trim();
					idNumber = idNumber.trim();
					boolean stuFlag = allStuMap.containsKey(stuName + "_" + idNumber);
					if (stuFlag == false) {
						errorBuffer.append("学员名称和证件号码必须在学员基础数据库中存在并且学员是已经认证的;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("学员名称和证件号码不能为空;");
					validateFlag = false;
				}
				if (validateFlag == true) { //数据校验通过 
					StudentClass sc = new StudentClass();
					Integer clasId = allClassMap.get(clasName);//班级id
					Integer studId = allStuMap.get(stuName + "_" + idNumber);
					sc.setClasId(clasId);
					sc.setStudId(studId);
					sc.setCamId(camId);
					sc.setStuStatus(1);
					sc.setIsPrint(0);
					sc.setIsDel(Constant.IS_DEL_NO);
					sc.setInsertTime(new Date());
					sc.setUpdateTime(new Date());
					sc.setQuotaHold(Constant.IS_QUOTA_HOLD_YES);
					// 手动控制事务
					HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
					TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
					try {
						baseDaoEx.saveOne(sc);
						//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
				        LoggerUtil.save(orgId, camId, studId, 1, stuName, EnumLog.OPERATION_STU_IMPORT_CLASS.getKey(), "批量" + EnumLog.OPERATION_STU_IMPORT_CLASS.getValue() + " " + stuName + " " + idNumber, userType, userTypeId, userTypeName, sessionInfo);
				        // 所有处理完成后，提交事务
				        txManager.commit(txStatus);
					} catch (Exception e) {
						e.printStackTrace();
						//异常回滚
						txManager.rollback(txStatus);
						stu.setErr_msg("数据库服务异常;");
						errorList.add(stu);
						errCount++;
					}
				} else { //校验不通过
					stu.setErr_msg(errorBuffer.toString());
					errorList.add(stu);
					errCount++;
				}
			}
			session.setAttribute("errNum", errCount);
			session.setAttribute("sucNum", ctList.size()-errCount);
			session.setAttribute("total", ctList.size());
			session.setAttribute("studentExcel", errorList);
			JSONObject json = new JSONObject();
			json.put("key", errorList);
			System.out.println("key ................ :" + json);
			/***
			 * 将导入数据进行记录
			 */
			ImportRecord ir = new ImportRecord();
			ir.setCamId(camId);//校区
			ir.setCorrectCount(ctList.size()-errCount);
			ir.setErrorCount(errCount);
			ir.setInsertTime(new Date());
			ir.setOrgId(orgId);
			ir.setTotalCount(ctList.size());//导入总数
			ir.setIsDel(Constant.IS_DEL_NO);
			ir.setResult(1);//导入状态 1成功 0失败
			ir.setCreater(userTypeName);
			baseDaoEx.saveOne(ir);
			returnMap.put("total", ctList.size());
			returnMap.put("errNum", errCount);
			returnMap.put("sucNum", ctList.size()-errCount);
		} else {
			returnMap.put("total", 0);
			returnMap.put("errNum", 0);
			returnMap.put("sucNum", 0);
			return returnMap;
		}
		return returnMap;
	}
	
	/***
	 * 根据 机构获取所有学生信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Map getStudent(Integer orgId) {
		String sql = " SELECT s.stud_id, s.stud_name, u.user_idnumber, CONCAT(s.stud_name, '_', u.user_idnumber) stu_idnumber FROM edugate_base.student s\r\n" + 
				"LEFT JOIN edugate_base.org_user u ON s.user_id = u.user_id AND u.is_del = 0 AND u.identity = 2\r\n" + 
				"WHERE s.is_del = 0 \r\n" + 
				"AND s.org_id = ? ";
		Map stuMap = new HashMap();
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		
		List<Map<String, Object>> stuList = baseDaoEx.queryListBySql(sql, params);
		if (null != stuList) {
			for (Map stu : stuList) {
				Integer studId = (Integer) stu.get("stud_id");
				String stuIdnumber = (String) stu.get("stu_idnumber");
				String stuName = (String) stu.get("stud_name");
				String mobile = (String) stu.get("user_mobile");
				stuMap.put(stuIdnumber, studId);
			}
		}
		return stuMap;
	}
	
	@Override
	public void saveStudentClass(StudentClass sc) {
		baseDaoEx.saveOne(sc);
	}
	/***
 	 *  @Description: 下载导入错误的excel（班级或学员）
	 *  @param type 1:班级 2:学生
	 *  @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void downloadErrorExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer type) {
		
		List<String[]> valueList = new ArrayList<String[]>();
		Workbook wb;
		if (type == 1) {//班级
			List<ClassTeacher> errorClass = (List<ClassTeacher>) session.getAttribute("classExcel");
			JSONObject json = new JSONObject();
			json.put("downList", errorClass);
			System.out.println("session recevice json :" + json);
			if (null != errorClass && errorClass.size() > 0) {
				for (ClassTeacher clas : errorClass) {
					String[] value = new String[22];
					value[0] = clas.getClas_name();
					value[1] = clas.getClas_type_name();
					value[2] = clas.getCategory_name();
					value[3] = clas.getSubject_name();
					value[4] = clas.getCam_name();
					value[5] = clas.getBuilding();
					value[6] = clas.getFloor();
					value[7] = clas.getClassroom_name();
					value[8] = clas.getSize();
					value[9] = clas.getAge_range();
					value[10] = clas.getStart_birthday();
					value[11] = clas.getEnd_birthday();
					value[12] = clas.getTotal_hours();
					value[13] = clas.getClass_start_date();
					value[14] = clas.getClass_week_name();
					value[15] = clas.getClass_begin_time();
					value[16] = clas.getClass_over_time();
					value[17] = clas.getClass_unset_time();
					value[18] = clas.getTuition_fees();
					value[19] = clas.getTech_names();
					value[20] = clas.getName();
					value[21] = clas.getError_msg();
					valueList.add(value);
				}
				//Workbook wb;
				try {
					wb = ExcelUtil.makeWorkbook("导入错误班级信息列表", Constant.IMPORT_EXCEL_CLASS_ERROR, valueList, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "导入错误班级信息列表.xls");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//Workbook wb;
				try {
					wb = ExcelUtil.makeWorkbook("导入错误班级信息列表", Constant.IMPORT_EXCEL_CLASS_ERROR, null, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "导入错误班级信息列表.xls");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else if (type == 2) {//学员
			List<ImportStudent> errStudent = (List<ImportStudent>) session.getAttribute("studentExcel");
			if (null != errStudent && errStudent.size() > 0) {
				//List<String[]> valueList = new ArrayList<String[]>();
				for (ImportStudent stu : errStudent) {
					String[] value = new String[5];
					value[0] = stu.getClas_name();
					value[1] = stu.getStud_name();
					value[2] = stu.getUser_idnumber();
					value[3] = stu.getStu_status();
					value[4] = stu.getErr_msg();
					valueList.add(value);
				}
				//Workbook wb;
				try {
					wb = ExcelUtil.makeWorkbook("错误学生信息列表", Constant.IMPORT_EXCEL_STUDENT_ERROR, valueList, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "错误学生信息列表.xls");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//Workbook wb;
				try {
					wb = ExcelUtil.makeWorkbook("错误学生信息列表", Constant.IMPORT_EXCEL_STUDENT_ERROR, null, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "错误学生信息列表.xls");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//session.removeAttribute(studentExcel);
		} else {
			
		}

	}
	/***
	 * 根据查询条件 获取班级列表及相关信息 不分页  
	 */
	@Override
	public List<ClassToTeacher> getClassList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, Integer camId, Integer classWeek, String techNames) {
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
				"(IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) num,\r\n" + 
				"IFNULL(ch.ss_money, 0) ss_money, IFNULL(ch.st_money, 0) st_money,\r\n" + 
				"\r\n" + 
				"CONCAT((IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) , '/', c.size) divide \r\n" + 
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
				"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"WHERE\r\n" + 
				"c.is_del = 0  \r\n" + 
				"AND c.org_id = ?  ");

		params.add(orgId);
		if(null != termId ) {
			sql.append(" AND c.term_id = ? ");
			params.add(termId);
		} else {
			//默认查询最晚的学期 
			//SELECT 	MAX(TERM_ID) FROM term WHERE NOW() > start_time 
			sql.append(" AND c.term_id = (SELECT MAX(TERM_ID) FROM term WHERE NOW() > start_time) ");			
		}
		if(null != clasType ) {
			sql.append(" AND c.clas_type = ? ");
			params.add(clasType);
		}
		if(null != categoryId ) {
			sql.append(" c.category_id = ? ");
			params.add(categoryId);
		}
		if(null != subjectId ) {
			sql.append(" AND c.subject_id = ? ");
			params.add(subjectId);
		}
		if(null != camId ) {
			sql.append(" AND c.cam_id = ? ");
			params.add(camId);
		}
		if(null != classWeek ) {
			sql.append(" AND c.class_week = ? ");
			params.add(classWeek);
		}
		String group = " GROUP BY c.clas_id ";
		String order1 = "ORDER BY cam.cam_name , c.clas_name, cr.classroom_name, c.clas_id ASC";
		sql.append(group);
		sql.append(order1);
		String finalSql = "";
		String order2 = " ORDER BY tt.cam_name , tt.clas_name, tt.classroom_name, tt.clas_id ASC ";

		if (StringUtils.isNotBlank(techNames)) {//模糊查询
			StringBuffer like1 = new StringBuffer(" SELECT tt.* FROM ( ");
			StringBuffer like2 = new StringBuffer(" ) tt\r\n" + 
					"where  LOCATE(? , tt.tech_names )>0 OR LOCATE(? , tt.clas_name )>0 ");
			params.add(techNames);
			params.add(techNames);
			finalSql = like1.append(sql).append(like2).append(order2).toString();
		} else {
			finalSql = sql.toString();
		}
		List<ClassToTeacher> list = baseDaoEx.queryListBySql(finalSql, ClassToTeacher.class, params);
		return list;
	}
	
	/***
	 * 统计班级列表：班级学员数量和学费
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getStatisticsInfo(List<Integer> ids) {
		return classDao.getStatisticsInfo(ids);
	}
	
	/***
	 * 查询班级列表
	 */

	@SuppressWarnings("rawtypes")
	@Override
	public Paging<Map<String, Object>> getStudentListByClasId(Integer orgId, Integer clasId,  String status, Paging<Map> page) {
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, s.stud_id, s.stud_name, o.user_idnumber, sc.stu_status, sc.is_print, \r\n" + 
				"IFNULL(sc.voucher_no, '未打印') voucher_no, IFNULL(ch.money, '--') money, IFNULL(ch.txnAmt, '--') txnAmt , \r\n" + 
				"IFNULL((ch.txnAmt - ch.money), '--') tf_money, IFNULL(ch.online_pay, 0) online_pay, IFNULL(ch.offline_pay, 0) offline_pay\r\n" + 
				"FROM newsng.classes c \r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id  AND sc.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id  AND s.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.org_user o ON o.user_id = s.user_id  AND o.is_del = 0\r\n" + 
				"LEFT JOIN newsng.voucher v ON sc.stu_class_id = v.stu_class_id AND v.is_del = 0\r\n" + 
				"LEFT JOIN newsng.charge_detail ch ON ch.stud_id = s.stud_id AND ch.is_del = 0\r\n" + 
				"WHERE c.is_del = 0  ");
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
		Paging<Map<String, Object>> paging = baseDaoEx.queryPageBySql(sql.toString(), countSql, page.getPage(), page.getLimit(), params);
		List<Map<String, Object>> list = paging.getData();
		if (null != list && list.size() > 0 ) {
			for (Map<String, Object> m : list) {
				Integer stuStatus = (Integer) m.get("stu_status");
				if (null != stuStatus) {
					String statusName = Consts.STUD_PAY_STATUS_MAP.get(stuStatus.toString());
					m.put("stu_status", statusName);
				}
				//tf_money   txnAmt  将分转元
				String tfMoney = (String) m.get("tf_money");//退费金额
				String txnAmt = (String) m.get("txnAmt");//应缴金额
				try {
					if (StringUtils.isNotBlank(tfMoney) && !"--".equals(tfMoney)) {
						m.put("tf_money", MoneyUtil.fromFENtoYUAN(tfMoney));
					}
					if (StringUtils.isNotBlank(txnAmt) && !"--".equals(txnAmt)) {
						m.put("txnAmt", MoneyUtil.fromFENtoYUAN(txnAmt));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return paging;
	}
	/***
	 * 根据教师id获取教师所有职教的班级信息
	 */
	@Override
	public List<ClassToTeacher> getTechClassInfoList(Integer orgId, Integer techId, Integer termId) {
		String sql = " SELECT c.clas_id, c.clas_name,c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,\r\n" + 
				"t.tech_name, (IFNULL(c.ys_num, 0) - IFNULL(c.ss_num, 0)) num,\r\n" + 
				"IFNULL(ch.ss_money, 0) ss_money, IFNULL(ch.st_money, 0) st_money\r\n" + 
				"FROM edugate_base.teacher t\r\n" + 
				"INNER JOIN newsng.teacher_class tc ON tc.tech_id = t.tech_id AND tc.is_del = 0\r\n" + 
				"INNER JOIN newsng.classes c ON c.clas_id = tc.clas_id AND c.is_del = 0\r\n" + 
				"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"WHERE t.is_del = 0\r\n" + 
				"AND t.org_id = ?\r\n" + 
				"AND c.term_id = ?\r\n" + 
				"AND t.tech_id = ? ";
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(termId);
		params.add(techId);
		List<ClassToTeacher> list = baseDaoEx.queryListBySql(sql, ClassToTeacher.class, params);
		return list;
	}
	/***
	 * 开课统计表 ：统计当前班级的老师所有开课的班级开课时间、学员人数、学费等信息, 以教师分组统计班级上课及学费总额
	 */
	@Override
	public void downloadStatistics(HttpServletRequest request, HttpServletResponse response, List<ClassToTeacher> returnList) {
		List<String[]> valueList = new ArrayList<String[]>();

		for (ClassToTeacher ct : returnList) {
			String[] value = new String[5];
			value[0] = ct.getTech_name();
			value[1] = ct.getClas_name();
			value[2] = ct.getNum().toString();
			value[3] = ct.getClass_unset_time();
			value[4] = ct.getSs_tuition();
			valueList.add(value);
		}
		Workbook wb;
		try {
			wb = ExcelUtil.makeWorkbook("开课统计信息列表", Constant.IMPORT_EXCEL_TECHER_STATISTICS, valueList, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "开课统计信息列表.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * 学员缴费记录表 ： 
	 */
	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	@Override
	public void downloadPaymentRecords(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, Integer camId, Integer classWeek,
			String techNames, Integer isAll, String ids) {
		/*OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String classIds = "";
		List<Object> params = new ArrayList<>();
		List<List<String>> clasInfoList = new ArrayList<List<String>>();//班级信息列表
		List<ClassToTeacher> list = null;//按照c.clas_id升序
		if (isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
			StringBuffer sb = new StringBuffer("");//班級id
			list = this.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if(i == list.size()-1)//当循环到最后一个的时候 就不添加逗号,
					{
						sb.append(list.get(i));
					} else {
						sb.append(list.get(i));
						sb.append(",");
					}
					List<String> clasInfo = new ArrayList<String>();

					String clasName = list.get(i).getClas_name();//班级名称
					String ageRange = list.get(i).getAge_range();//年龄范围
					String size = list.get(i).getSize().toString();//容量
					String classroom = list.get(i).getClassroom_name();//教室名称
					String techName = list.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(list.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = list.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(list.get(i).getClass_week()) + " (" + list.get(i).getClass_begin_time().toString() + "-" + list.get(i).getClass_over_time().toString() + ")";
					}
					String tuitionFees = list.get(i).getTuition_fees();//学费标准
					clasInfo.add(clasName);
					clasInfo.add(ageRange);
					clasInfo.add(size);
					clasInfo.add(classroom);
					clasInfo.add(techName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(tuitionFees);
					clasInfoList.add(clasInfo);
				}
			}
			classIds = sb.toString();
		} else {
			//班级ids
			classIds = ids;
			StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, c.age_range,c.org_id ,\r\n" + 
					"c.class_start_date, IFNULL(c.tuition_fees , 0) tuition_fees, IFNULL(c.size, 0) size,\r\n" + 
					"cr.building, cr.floor, cr.classroom_name,\r\n" + 
					"IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
					"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,\r\n" + 
					"GROUP_CONCAT(t.tech_name) tech_name\r\n" + 
					"FROM newsng.classes c \r\n" + 
					"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n" + 
					"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n" + 
					"LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n" + 
					"WHERE\r\n" + 
					"c.is_del = 0 \r\n" + 
					"AND c.org_id = ?\r\n");
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
			List<ClassToTeacher> ctList = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);
			if (null != ctList && ctList.size() > 0) {
				for (ClassToTeacher ct : ctList) {
					String clasName = ct.getClas_name();//班级名称
					String ageRange = ct.getAge_range();//年龄范围
					String size = ct.getSize().toString();//容量
					String classroom = ct.getClassroom_name();//教室名称
					String techName = ct.getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(ct.getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = ct.getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(ct.getClass_week()) + " (" + ct.getClass_begin_time().toString() + "-" + ct.getClass_over_time().toString() + ")";
					}
					String tuitionFees = ct.getTuition_fees();//学费标准
					List<String> clasInfo = new ArrayList<String>();
					clasInfo.add(clasName);
					clasInfo.add(ageRange);
					clasInfo.add(size);
					clasInfo.add(classroom);
					clasInfo.add(techName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(tuitionFees);
					clasInfoList.add(clasInfo);
				}
			}
			
		}
		/***
		 * 根据班级ids 获取所有班级id的学员   classIds(多个班级的id)
		 */
		// TODO Auto-generated catch block
		List<List<String>> dataList = new ArrayList<List<String>>();//學員缴费列表
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 0; i < clasInfoList.size(); i++) {
			try {
				ExcelExport.exportExcel(workbook, i, clasInfoList.get(i).get(0), Constant.IMPORT_EXCEL_PAYMENT_RECORDS, dataList, clasInfoList.get(i));
				//ExcelExport.exportExcel(workbook, 1, "深圳", Constant.IMPORT_EXCEL_PAYMENT_RECORDS, data, clasInfo);
				//ExcelExport.exportExcel(workbook, 2, "广州", Constant.IMPORT_EXCEL_PAYMENT_RECORDS, data, clasInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员缴费记录.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	/***
	 * 学员考勤表
	 * 1 查询班级列表，循环班级，获取每个班级的信息，根据班级id获取班级下的学员  写入excel
	 * 
	 */
	@Override
	public void downloadStudentsAttendance(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, Integer camId, Integer classWeek,
			String techNames, Integer isAll, String ids) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<Object> params = new ArrayList<>();
		if (isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
			List<ClassToTeacher> list = this.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames);//按照c.clas_id升序
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					//班级列表
					List<String> clasInfo = new ArrayList<String>();
					String clasName = list.get(i).getClas_name();//班级名称
					String classroom = list.get(i).getClassroom_name();//教室名称
					String techName = list.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(list.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = list.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(String.valueOf(list.get(i).getClass_week())) + " (" + String.valueOf(list.get(i).getClass_begin_time()) + "-" + String.valueOf(list.get(i).getClass_over_time()) + ")";
					}
					clasInfo.add(clasName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(techName);
					clasInfo.add(classroom);
					//clasInfoList.add(clasInfo);
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					List<Student> stuList = this.getStudentList(orgId, list.get(i).getClas_id());
					if (null != stuList && stuList.size() > 0) {
						for (int j = 0; j < stuList.size(); j++ ) {
							List<String> stuInfo = new ArrayList<String>();
							stuInfo.add(j + 1 + "");
							stuInfo.add(stuList.get(j).getStud_name());
							stuInfo.add("");//1
							stuInfo.add("");//2
							stuInfo.add("");//3
							stuInfo.add("");//4
							stuInfo.add("");//5
							stuInfo.add("");//6
							stuInfo.add("");//7
							stuInfo.add("");//8
							stuInfo.add("");//9
							stuInfo.add("");//10
							stuInfo.add("");//11
							stuInfo.add("");//12
							stuInfo.add("");//13
							stuInfo.add("");//14
							stuInfo.add("");//15
							stuInfo.add("");//16
							stuInfo.add("");//17
							stuInfo.add("");//18
							stuInfo.add("");//19
							stuInfo.add("");//20
							stuInfo.add("");//21
							stuInfo.add("");//22
							stuInfo.add("");//23
							stuInfo.add("");//24
							stuInfo.add("");//25
							stuInfo.add("");//26
							stuInfo.add("");//27
							stuInfo.add("");//28
							stuInfo.add("");//29
							stuInfo.add("");//30
							stuInfo.add("");//31
							stuInfoList.add(stuInfo);
						}
					}
					try {
						ExcelExport.exportExcelStuCheck(workbook, i, clasInfo.get(0), Constant.IMPORT_EXCEL_STU_CHECK, stuInfoList, clasInfo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员考勤表.xls");
				} catch (Exception e) {
					log.info("makeAndOutHSSFExcelFile Ex :" + e);
					e.printStackTrace();
				}
			}
		} else {
			//班级ids
			StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, c.age_range,\r\n" + 
					"c.class_start_date, IFNULL(c.tuition_fees , 0) tuition_fees, IFNULL(c.size, 0) size,\r\n" + 
					"cr.building, cr.floor, cr.classroom_name,\r\n" + 
					"IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
					"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,\r\n" + 
					"GROUP_CONCAT(t.tech_name) tech_name\r\n" + 
					"FROM newsng.classes c \r\n" + 
					"LEFT JOIN newsng.teacher_class tc ON  c.clas_id = tc.clas_id AND tc.is_del = 0 \r\n" + 
					"LEFT JOIN edugate_base.teacher t ON tc.tech_id = t.tech_id AND t.is_del = 0 \r\n" + 
					"LEFT JOIN newsng.classroom cr ON c.classroom_id = cr.classroom_id AND cr.is_del = 0\r\n" + 
					"WHERE\r\n" + 
					"c.is_del = 0 \r\n" + 
					"AND c.org_id = ?\r\n");
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
			List<ClassToTeacher> ctList = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);
				
			if (null != ctList && ctList.size() > 0) {
				for (int i = 0; i < ctList.size(); i++) {
					//班级列表
					List<String> clasInfo = new ArrayList<String>();
					String clasName = ctList.get(i).getClas_name();//班级名称
					String classroom = ctList.get(i).getClassroom_name();//教室名称
					String techName = ctList.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(ctList.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = ctList.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(String.valueOf(ctList.get(i).getClass_week())) + " (" + String.valueOf(ctList.get(i).getClass_begin_time()) + "-" + String.valueOf(ctList.get(i).getClass_over_time()) + ")";
					}
					clasInfo.add(clasName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(techName);
					clasInfo.add(classroom);
					
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					List<Student> stuList = this.getStudentList(orgId, ctList.get(i).getClas_id());
					if (null != stuList && stuList.size() > 0) {
						for (int j = 0; j < stuList.size(); j++ ) {
							List<String> stuInfo = new ArrayList<String>();
							stuInfo.add(j + 1 + "");
							stuInfo.add(stuList.get(j).getStud_name());
							stuInfo.add("");//1
							stuInfo.add("");//2
							stuInfo.add("");//3
							stuInfo.add("");//4
							stuInfo.add("");//5
							stuInfo.add("");//6
							stuInfo.add("");//7
							stuInfo.add("");//8
							stuInfo.add("");//9
							stuInfo.add("");//10
							stuInfo.add("");//11
							stuInfo.add("");//12
							stuInfo.add("");//13
							stuInfo.add("");//14
							stuInfo.add("");//15
							stuInfo.add("");//16
							stuInfo.add("");//17
							stuInfo.add("");//18
							stuInfo.add("");//19
							stuInfo.add("");//20
							stuInfo.add("");//21
							stuInfo.add("");//22
							stuInfo.add("");//23
							stuInfo.add("");//24
							stuInfo.add("");//25
							stuInfo.add("");//26
							stuInfo.add("");//27
							stuInfo.add("");//28
							stuInfo.add("");//29
							stuInfo.add("");//30
							stuInfo.add("");//31
							stuInfoList.add(stuInfo);
						}
					}
					try {
						ExcelExport.exportExcelStuCheck(workbook, i, clasInfo.get(i), Constant.IMPORT_EXCEL_STU_CHECK, stuInfoList, clasInfo);
					} catch (Exception e) {
						log.info("exportExcelStuCheck Ex :" + e);
						e.printStackTrace();
					}
				}
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员考勤表.xls");
				} catch (Exception e) {
					log.info("makeAndOutHSSFExcelFile Ex :" + e);
					e.printStackTrace();
				}
			}
		}
		
	}
	/***
	 * 根据班级id获取班级中的学员信息
	 * 学员名称
	 */
	@Override
	public List<Student> getStudentList(Integer orgId, Integer clasId) {
		StringBuffer sql = new StringBuffer(" SELECT s.stud_name FROM newsng.classes c \r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id AND s.is_del = 0\r\n" + 
				"WHERE c.is_del = 0\r\n" + 
				"AND c.org_id = ?\r\n" +
				"AND c.clas_id = ?\r\n" + 
				"AND sc.stu_status = 5\r\n" +
				"ORDER BY c.clas_id ASC ");
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(clasId);
		List<Student> list = baseDaoEx.queryListBySql(sql.toString(), Student.class, params);
		return list;
	}
	
	
	/***
	 * 根据班级id获取班级中的学员信息
	 * 学员名称、性别、年龄、家长名称、学员身份证号码、手机号码
	 */
	@Override
	public List<StudentRoster> getStudentRosterList(Integer orgId, Integer clasId) {
		String sql = " SELECT s.stud_name,\r\n" + 
				"CASE WHEN  s.sex  = 0 THEN '男' WHEN   s.sex  = 1 THEN '女' ELSE '保密' END sex ,\r\n" + 
				"TIMESTAMPDIFF(\r\n" + 
				"    YEAR,\r\n" + 
				"    s.birthday,\r\n" + 
				"    NOW()\r\n" + 
				") AS age,\r\n" + 
				"p.parent_name,\r\n" + 
				"u.user_idnumber,\r\n" + 
				"u.user_mobile\r\n" + 
				"FROM newsng.classes c\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student s ON s.stud_id = sc.stud_id AND s.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.student2parent sp ON sp.stud_id = s.stud_id AND sp.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.parent p ON p.parent_id = sp.parent_id AND p.is_del = 0\r\n" + 
				"INNER JOIN edugate_base.org_user u ON u.user_id = p.user_id AND u.is_del = 0\r\n" + 
				"WHERE c.is_del = 0\r\n" + 
				"AND c.org_id = ?\r\n" + 
				"AND c.clas_id = ? ";
		List<Object> params = new ArrayList<>();
		params.add(orgId);
		params.add(clasId);
		
		return baseDaoEx.queryListBySql(sql, StudentRoster.class, params);
	}
	/***
	 * 学员花名册
	 */
	@Override
	public void downloadStudentRoster(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, Integer camId, Integer classWeek,
			String techNames, Integer isAll, String ids) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		if (isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
			List<ClassToTeacher> list = this.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					//获取当前班级的信息
					//班级列表
					List<String> clasInfo = new ArrayList<String>();
					String clasName = list.get(i).getClas_name();//班级名称
					String ageRange = list.get(i).getAge_range();//年龄范围
					String size = list.get(i).getSize().toString();//容量
					String classroom = list.get(i).getClassroom_name();//教室名称
					String techName = list.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(list.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = list.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(String.valueOf(list.get(i).getClass_week())) + " (" + String.valueOf(list.get(i).getClass_begin_time()) + "-" + String.valueOf(list.get(i).getClass_over_time()) + ")";
					}
					String tuitionFees = list.get(i).getTuition_fees();//学费标准
					clasInfo.add(clasName);
					clasInfo.add(ageRange);
					clasInfo.add(size);
					clasInfo.add(classroom);
					clasInfo.add(techName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(tuitionFees);
					//根据clasId获取班级学员的详细信息
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					List<StudentRoster> srList = this.getStudentRosterList(orgId, list.get(i).getClas_id());
					if (null != srList && srList.size() > 0) {
						for (int j = 0; j < srList.size(); j++) {
							List<String> stuInfo = new ArrayList<>();
							//序号 学生名称	性别	年龄	学生身份证号码  家长姓名  联系电话  备注
							stuInfo.add(String.valueOf(j+1));//序号
							stuInfo.add(srList.get(j).getStud_name());//学生名称
							stuInfo.add(srList.get(j).getSex());//性别
							stuInfo.add(srList.get(j).getAge() == null ? "" : String.valueOf(srList.get(j).getAge()));//年龄
							stuInfo.add(srList.get(j).getUser_idnumber() == null ? "" : srList.get(j).getUser_idnumber());//学生身份证号
							stuInfo.add(srList.get(j).getParent_name());//家长姓名
							stuInfo.add(srList.get(j).getUser_mobile());//联系电话
							stuInfo.add("");//备注
							stuInfoList.add(stuInfo);//将每条
						}
					}
					//ExcelExport.exportStudentRosterExcel(workbook, i, sheetTitle, headers, result, clasInfo);
					try {
						ExcelExport.exportStudentRosterExcel(workbook, i, clasInfo.get(0), Constant.IMPORT_EXCEL_STU_ROSTER, stuInfoList, clasInfo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.info("exportExcelStuCheck Ex :" + e);
						e.printStackTrace();
					}
				}
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员花名册表.xls");
				} catch (Exception e) {
					log.info("makeAndOutHSSFExcelFile Ex :" + e);
					e.printStackTrace();
				}
			}
		} else {
			List<Object> params = new ArrayList<>();
			StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, c.age_range,\r\n" + 
					"c.class_start_date, IFNULL(c.tuition_fees , 0) tuition_fees, IFNULL(c.size, 0) size,\r\n" + 
					"cr.building, cr.floor, cr.classroom_name,\r\n" + 
					"IFNULL(c.total_hours, 0) total_hours, c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
					"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,\r\n" + 
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
			List<ClassToTeacher> ctList = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);
			if (null != ctList && ctList.size() > 0) {
				for (int i = 0; i < ctList.size(); i++) {
					
					String clasName = ctList.get(i).getClas_name();//班级名称
					String ageRange = ctList.get(i).getAge_range();//年龄范围
					String size = ctList.get(i).getSize().toString();//容量
					String classroom = ctList.get(i).getClassroom_name();//教室名称
					String techName = ctList.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(ctList.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = ctList.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(String.valueOf(ctList.get(i).getClass_week())) + " (" + String.valueOf(ctList.get(i).getClass_begin_time()) + "-" + String.valueOf(ctList.get(i).getClass_over_time()) + ")";
					}
					String tuitionFees = ctList.get(i).getTuition_fees();//学费标准
					List<String> clasInfo = new ArrayList<String>();
					clasInfo.add(clasName);
					clasInfo.add(ageRange);
					clasInfo.add(size);
					clasInfo.add(classroom);
					clasInfo.add(techName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(tuitionFees);
					
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					List<StudentRoster> srList = this.getStudentRosterList(orgId, ctList.get(i).getClas_id());
					if (null != srList && srList.size() > 0) {
						for (int j = 0; j < srList.size(); j++) {
							List<String> stuInfo = new ArrayList<>();
							//序号 学生名称	性别	年龄	学生身份证号码  家长姓名  联系电话  备注
							stuInfo.add(String.valueOf(j+1));//序号
							stuInfo.add(srList.get(j).getStud_name());//学生名称
							stuInfo.add(srList.get(j).getSex());//性别
							stuInfo.add(srList.get(j).getAge() == null ? "" : String.valueOf(srList.get(j).getAge()));//年龄
							stuInfo.add(srList.get(j).getUser_idnumber() == null ? "" : srList.get(j).getUser_idnumber());//学生身份证号
							stuInfo.add(srList.get(j).getParent_name());//家长姓名
							stuInfo.add(srList.get(j).getUser_mobile());//联系电话
							stuInfo.add("");//备注
							stuInfoList.add(stuInfo);//将每条
						}
					}
					try {
						ExcelExport.exportStudentRosterExcel(workbook, i, clasInfo.get(0), Constant.IMPORT_EXCEL_STU_ROSTER, stuInfoList, clasInfo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.info("exportExcelStuCheck Ex :" + e);
						e.printStackTrace();
					}
					
				}
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员花名册表.xls");
				} catch (Exception e) {
					log.info("makeAndOutHSSFExcelFile Ex :" + e);
					e.printStackTrace();
				}
			}
		}
		
	}
	/***
	 * 根据机构id和班级id和quotaHold=1（占有名额的）的 获取班级容量  已占用的班级名额  可容纳人数  返回 当前班级的可容纳人数
	 */
	@Override
	public Integer getCurClassUsableNum(Integer orgId, Integer clasId, Integer quotaHold, Integer size) {
		Integer usableNum = 0;
		String sql = " SELECT COUNT(1) rs FROM newsng.classes c \r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id AND sc.is_del = 0 AND sc.quota_hold = ? \r\n" + 
				"WHERE c.is_del = 0\r\n" + 
				"AND c.org_id = ? \r\n" + 
				"AND c.clas_id = ?\r\n" + 
				"GROUP BY c.clas_id \r\n";
		List<Object> params = new ArrayList<>();
		params.add(quotaHold);
		params.add(orgId);
		params.add(clasId);
		List<Map<String, Object>> list = baseDaoEx.queryListBySql(sql, params);
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			//Integer size = map.get("size") == null ? 0 : (Integer) map.get("size");
			Integer rs = map.get("rs") == null ? 0 : (Integer) map.get("rs");
			usableNum = size - rs;
			if (usableNum < 0) {
				usableNum = 0;
			}
		} else {
			usableNum = size;
		}
		return usableNum;
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public void updateClasssAndTeacher(Map<String, Object> reqMap, SessionInfo sessionInfo,
			List<Map<String, Object>> techList) {
		Integer orgId = sessionInfo.getOrgId();
		Integer clasId = Integer.parseInt((String) reqMap.get("clasId"));
		//获取修改班的信息
		Classes curClasses = classDao.getClassInfo(orgId, clasId);
		//判断前段传来的班级属性是否为空，不为空set
		Integer termId = Integer.parseInt((String) reqMap.get("termId"));//学期
		if (termId != null) {
			curClasses.setTerm_id(termId);
		}
		String clasName = (String) reqMap.get("clasName");//班级名称
		if (StringUtils.isNotBlank(clasName)) {
			curClasses.setClas_name(clasName);
		}
		Integer clasType = Integer.parseInt((String) reqMap.get("clasType"));//班级类型
		if (null != clasType) {
			curClasses.setClas_type(clasType);
		}
		Integer categoryId = Integer.parseInt((String) reqMap.get("categoryId"));//类目
		if (null != categoryId) {
			curClasses.setCategory_id(categoryId);
		}
		Integer subjectId = Integer.parseInt((String) reqMap.get("subjectId"));//科目
		if (null != subjectId) {
			curClasses.setSubject_id(subjectId);
		}
		Integer camId = Integer.parseInt((String) reqMap.get("camId"));//校区
		if (null != camId) {
			curClasses.setCam_id(camId);
		}
		Integer classroomId = Integer.parseInt((String) reqMap.get("classroomId"));//教室
		if (null != classroomId) {
			curClasses.setClassroom_id(classroomId);
		}
		Integer size = Integer.parseInt((String) reqMap.get("size"));//班级容量
		if (null != size) {
			curClasses.setSize(size);
		}
		String ageRange = (String) reqMap.get("ageRange");//年龄要求
		if (StringUtils.isNotBlank(ageRange)) {
			curClasses.setAge_range(ageRange);
		}
		try {
			/***
			 * 接受前段传来的时间字符串， 将字符串转为指定时间格式的时间Date
			 * 开始生日、结束生日、开课日期（yyyy-MM-dd）、添加时间、修改时间、删除时间（yyyy-MM-dd HH:mm:ss）
			 */
			if (StringUtils.isNotBlank((String) reqMap.get("startBirthday"))) {
				Date startBirthDate = CommonUtils.stringToDate((String) reqMap.get("startBirthday"), "yyyy-MM-dd");
				curClasses.setStart_birthday(startBirthDate);
			}
			if (StringUtils.isNotBlank((String) reqMap.get("endBirthday"))) {
				Date endBirthDate = CommonUtils.stringToDate((String) reqMap.get("endBirthday"), "yyyy-MM-dd");
				curClasses.setEnd_birthday(endBirthDate);
			}
			if (StringUtils.isNotBlank((String) reqMap.get("classStartDate"))) {
				Date classSatrtDate = CommonUtils.stringToDate((String) reqMap.get("classStartDate"), "yyyy-MM-dd");
				curClasses.setClass_start_date(classSatrtDate);
			}
		} catch (ParseException e1) {
			log.info("CommonUtils.stringToDate Ex :" + e1);
			e1.printStackTrace();
		}
		Integer totalHours = Integer.parseInt((String) reqMap.get("totalHours"));//总课时数
		if (null != totalHours) {
			curClasses.setTotal_hours(totalHours);
		}
		String classUnsetTime = (String) reqMap.get("classUnsetTime");//不固定时间
		Integer classWeek = (String) reqMap.get("classWeek") == null ? 8 : Integer.parseInt((String) reqMap.get("classWeek"));//上课星期 (周一  周二  周三 。。。)
		if (StringUtils.isNotBlank(classUnsetTime)) {
			curClasses.setClass_unset_time(classUnsetTime);
			curClasses.setClass_week(8);
			curClasses.setClass_begin_time(null);
			curClasses.setClass_over_time(null);
		} else {
			String beginHours = (String) reqMap.get("classBeginTime");
			String endHours = (String) reqMap.get("classOverTime");
			StringBuffer sb1 = new StringBuffer("2000-01-01 ");
			StringBuffer sb2 = new StringBuffer("2000-01-01 ");
			sb1.append(beginHours).append(":00");
			sb2.append(endHours).append(":00");
			Date beginDate = null;
			Date overDate = null;;
			try {
				beginDate = CommonUtils.stringToDate(sb1.toString(), null);
				overDate = CommonUtils.stringToDate(sb2.toString(), null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null != classWeek) {
				curClasses.setClass_week(classWeek);
			}
			if (null != beginHours) {
				curClasses.setClass_begin_time(beginDate);
			}
			if (null != endHours) {
				curClasses.setClass_over_time(overDate);
			}
			curClasses.setClass_unset_time(null);
		}
		String tuitionFees = (String) reqMap.get("tuitionFees");//学费标准
		if (StringUtils.isNotBlank(tuitionFees)) {
			curClasses.setTuition_fees(tuitionFees);
		}
		Integer cooperationId = Integer.parseInt((String) reqMap.get("cooperationId"));//联合机构id(教师的所属机构)
		if (null != cooperationId) {
			curClasses.setCooperation_id(cooperationId);
		}
		//修改班级表
		baseDaoEx.updateOne(curClasses);
		String techIds = (String) reqMap.get("techIds"); //教师id，多个以英文逗号分隔（121,145）
		//  ？  支付项目表
  		// TODO Auto-generated method stub
		//班级教师关系表
		String sql = " UPDATE teacher_class SET is_del = 1 WHERE clas_id = ? ";
		if (StringUtils.isBlank(techIds)) {
			//前段返回的班级教师为空，更新该班级与之前老师的关系
			if (null != techList && techList.size() > 0) {
				for (Map map : techList) {
					List<Object> params = new ArrayList<Object>();
					Integer techId = (Integer) map.get("tech_id");
		        	params.add(techId);
		        	baseDaoEx.executeSql(sql, params);
		        }
			}
		} else {
			String[] strArray = null; 
			strArray = techIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
			List<Integer> techArrayIds = new ArrayList<>(); 
			for (int i = 0; i < strArray.length; i++) {
				techArrayIds.add(Integer.parseInt(strArray[i]));
			}
			//查询当前班级下的所有老师
			List<Integer> techListIds = new ArrayList<>(); 
			if (null != techList) {
				for (Map<String, Object> map : techList) {
					Integer techId = (Integer) map.get("tech_id");
					techListIds.add(techId);
				}
			}
			//listAll = new ArrayList<String>(new LinkedHashSet<>(listAll));//去重
			if (null == techListIds || techListIds.size() == 0) {//原 班级与教师 无关系
				for (int i = 0; i < techArrayIds.size(); i++) {//前段返回的教师id, 将前段返回的教师插入到关系表中
					TeacherClass tc = new TeacherClass();
					tc.setClas_id(clasId);
					tc.setTech_id(techArrayIds.get(i));
					tc.setInsert_time(new Date());
					tc.setIs_del(Constant.IS_DEL_NO);
					baseDaoEx.saveOne(tc);
				}
			} else {
				//将原 班级与教师有关系的老师  打上 删除标记
				String delSql = " UPDATE teacher_class SET is_del = 1 WHERE clas_id = ? AND tech_id = ? ";
				for (int i = 0; i < techListIds.size(); i++) {
					List<Object> delParams = new ArrayList<Object>();
					delParams.add(clasId);
					delParams.add(techListIds.get(i));
					baseDaoEx.executeSql(delSql, delParams);
				}
				//1. 根据班级id和教师id先查询打上删除标记的 班级教师关系  是否存在
				for (int i = 0; i < techArrayIds.size(); i++) {//前段返回的教师id
					String tcSql = " SELECT * FROM teacher_class WHERE is_del = 1 AND clas_id = ? AND tech_id = ? ";
					List<Object> tcParams = new ArrayList<Object>();
					tcParams.add(clasId);
					tcParams.add(techArrayIds.get(i));
					List<Map<String, Object>> tcList = baseDaoEx.queryListBySql(tcSql, tcParams);
					if (null != tcList) {
						if (tcList.size() != 1) {//该教师不在有删除标记的 教师班级关系表中
							TeacherClass tc = new TeacherClass();
							tc.setClas_id(clasId);
							tc.setTech_id(techArrayIds.get(i));
							tc.setInsert_time(new Date());
							tc.setIs_del(Constant.IS_DEL_NO);
							baseDaoEx.saveOne(tc);
						} else {// 该教师在已有删除标记的 关系表中
							//将原 班级与教师有关系的老师  打上 删除标记
							String ySql = " UPDATE teacher_class SET is_del = 0 WHERE clas_id = ? AND tech_id = ? ";
							List<Object> yParams = new ArrayList<Object>();
							yParams.add(clasId);
							yParams.add(techArrayIds.get(i));
							baseDaoEx.executeSql(ySql, yParams);
						}
					}
				}
			}
		}
		
		//操作日志表
		Integer userType = sessionInfo.getIdentity();
		String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
        LoggerUtil.save(orgId, camId, clasId, clasType, clasName, EnumLog.OPERATION_CLASS_EDIT.getKey(), EnumLog.OPERATION_CLASS_EDIT.getValue() + clasName, userType, userTypeId, userTypeName, sessionInfo);
	
	}
	
	@Override
	public List<Classes> queryListForExchangeClass(Integer stu_class_id) {
		StudentClass sc = studentClassService.getInfosById(stu_class_id);
		List<Classes> list = new ArrayList<Classes>();
		Classes classes = new Classes();
		classes.setCategory_id(sc.getCategory_id());
		classes.setSubject_id(sc.getSubject_id());
		String[] teacherIds = sc.getTeacher_ids().split("\\,");
		if(sc.getStuStatus().equals(Consts.STUD_STATUS_1OLD_TO_PAY)){
			list = classDao.getList(classes);
		}else if(sc.getStuStatus().equals(Consts.STUD_STATUS_5PAID)){
			classes.setTuition_fees(sc.getTuition_fees());
			list = classDao.getList(classes);
			for (Classes c : list) {
				try {
					if(!Arrays.equals(c.getTeacher_ids().split("\\,"),teacherIds)){
						list.remove(c);
					}
				} catch (Exception e) {
					if(c.getTeacher_ids().equals(sc.getTeacher_ids())){
						list.remove(c);
					}
				}
			}
		}
		return list;
	}

}
