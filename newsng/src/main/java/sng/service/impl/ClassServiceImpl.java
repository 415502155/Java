package sng.service.impl;

import java.io.IOException;
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
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
import sng.entity.ChargeDetail;
import sng.entity.Classes;
import sng.entity.Cooperative;
import sng.entity.StudentClass;
import sng.entity.Subject;
import sng.entity.TeacherClass;
import sng.entity.Vouchermodel;
import sng.pojo.ClassTeacher;
import sng.pojo.ClassToTeacher;
import sng.pojo.ImportClass;
import sng.pojo.ImportStudent;
import sng.pojo.ParamObj;
import sng.pojo.SessionInfo;
import sng.pojo.StudentRoster;
import sng.pojo.WXTemplateMessage;
import sng.pojo.base.Student;
import sng.service.ChargeDetailService;
import sng.service.ChargeService;
import sng.service.ClassService;
import sng.service.MQService;
import sng.service.StudentClassService;
import sng.service.TokenService;
import sng.service.VoucherModelService;
import sng.util.CommonUtils;
import sng.util.Constant;
import sng.util.EnumLog;
import sng.util.ExcelExport;
import sng.util.ExcelUtil;
import sng.util.JsonUtil;
import sng.util.LoggerUtil;
import sng.util.MoneyConvertUtil;
import sng.util.MoneyUtil;
import sng.util.Paging;
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
	//private static String ESB_REQUEST_URL = ESBPropertyReader.getProperty("esbRequestUrl");

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
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MQService mqService;
	@Autowired
	private ApplyServiceImpl applyServiceImpl;
	@Autowired
	private VoucherModelService voucherModelService;
	/***
	 * 添加班级
	 * @throws ParseException 
	 */
	@Override
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void save(String userTypeName, Map<String, Object> reqMap, SessionInfo sessionInfo) throws ParseException {
		Integer orgId = null;
		if (null != (String) reqMap.get("orgId")) {
			orgId = Integer.parseInt((String) reqMap.get("orgId"));
		} else {
			orgId = sessionInfo.getOrgId();
		}
		Integer camId = null;
		if (null != (String) reqMap.get("camId")) {
			camId = Integer.parseInt((String) reqMap.get("camId"));
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
		if (reqMap.get("cooperationId") == null) {
			classes.setCooperation_id(null);
		} else {
			classes.setCooperation_id(Integer.parseInt((String) reqMap.get("cooperationId")));//联合机构id(教师的所属机构)
		}
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
		chargeService.createChargeForNewClass(classes);
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
		Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作创建班级，操作对象是班级名称，操作内容是导入班级  班级名称  1
		String classBeginDate = "";
		if (null == classUnsetTime) {
			Integer week = Integer.parseInt((String) reqMap.get("classWeek"));//classWeek 上课星期 (周一  周二  周三 。。。)
			String beginHM = (String) reqMap.get("classBeginTime");
			String endHM = (String) reqMap.get("classOverTime");
			classBeginDate = Consts.WEEK_MAP.get(String.valueOf(week)) + " " + beginHM + " " + endHM ;
		} else {
			classBeginDate = classUnsetTime;
		}
		if (StringUtils.isNotBlank(techNames)) {
			LoggerUtil.save(orgId, camId, classId, EnumLog.TARGET_TYPE_CLASS.getKey(), className, EnumLog.OPERATION_CLASS_CREATE.getKey(), "手动创建  " + EnumLog.OPERATION_CLASS_CREATE.getValue() + " " + className + " " + techNames + " " + classBeginDate, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
		} else {
			LoggerUtil.save(orgId, camId, classId, EnumLog.TARGET_TYPE_CLASS.getKey(), className, EnumLog.OPERATION_CLASS_CREATE.getKey(), "手动创建  " + EnumLog.OPERATION_CLASS_CREATE.getValue() + " " + className + " 教师待定 " + classBeginDate, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
		}
	}
	/***
	 * 查询班级列表
	 * @throws Exception 
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Paging<ClassToTeacher> getClassList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, String camId, Integer classWeek, String techNames, Paging<ClassToTeacher> page) throws Exception {
		return classDao.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames, page);
	}
	/***
	 * 导出班级学员列表
	 * @throws Exception 
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<ClassToTeacher> getClassIdList(Integer orgId, Integer termId, Integer clasType, Integer categoryId,
			Integer subjectId, String camId, Integer classWeek, String techNames, String ids, Integer isAll) throws Exception {
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
			String order1 = "ORDER BY cam.cam_name , c.clas_name, cr.classroom_name ASC";
			sql.append(group);
			sql.append(order1);
			String finalSql = "";
			String order2 = " ORDER BY tt.cam_name , tt.clas_name, tt.classroom_name ASC ";
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
			list = baseDaoEx.queryListBySql(finalSql, ClassToTeacher.class, params);
			//班级列表  实收金额
			for (ClassToTeacher ct : list) {
				String ssMoney = ct.getSs_money();
				String stMoney = ct.getSt_money();
				BigDecimal ss = new BigDecimal(ssMoney);
			    BigDecimal st = new BigDecimal(stMoney);
				String sjsMoney = ss.subtract(st).toString();
				ct.setSs_tuition(MoneyUtil.fromFENtoYUAN(sjsMoney));	
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
					ctt.setSs_tuition(MoneyUtil.fromFENtoYUAN(sjsMoney));
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<Map<String, Object>> getStudentList(Integer orgId, String cidList, String status) {
		
		return classDao.getStudentList(orgId, cidList, status);
	}
	/***
	 * 获取当前机构下某班级的信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map getById(Integer orgId, Integer clasId) {
		return classDao.getClasInfoById(orgId, clasId);
	}
	/***
	 * 修改班级 和 教师
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void update(Map<String, Object> reqMap, SessionInfo sessionInfo, List<Map<String, Object>> techList) throws Exception {
		Integer orgId = sessionInfo.getOrgId();
		Integer clasId = Integer.parseInt((String) reqMap.get("clasId"));
		String clasName = (String) reqMap.get("clasName");
		//Integer clasType = Integer.parseInt((String) reqMap.get("clasType"));
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
		//Integer camId = Integer.parseInt((String) reqMap.get("camId"));
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
				log.info("CommonUtils.stringToDate Ex beginDate or overDate:" + e);
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
		if (reqMap.get("cooperationId") == null) {
			classes.setCooperation_id(null);
		} else {
			classes.setCooperation_id(Integer.parseInt((String) reqMap.get("cooperationId")));//联合机构id(教师的所属机构)
		}
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
				//for (Map map : techList) {
					List<Object> params = new ArrayList<Object>();
					//Integer techId = (Integer) map.get("tech_id");
		        	params.add(clasId);
		        	baseDaoEx.executeSql(sql, params);
		        //}
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
		//String userTypeName = sessionInfo.getLoginName();
		//Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
        //LoggerUtil.save(orgId, camId, clasId, EnumLog.TARGET_TYPE_CLASS.getKey(), clasName, EnumLog.OPERATION_CLASS_EDIT.getKey(), EnumLog.OPERATION_CLASS_EDIT.getValue() + clasName, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
	}
	/***
	 * 获取当前班级下的所有老师
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<Map<String, Object>> getTecherIds(Integer orgId, Integer clasId) {
		return classDao.getTecherIds(orgId, clasId);
	}
	/***
	 * 根据机构id和证件编号，返回该学员所在的班级列表
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<Map<String, Object>> getStudentInfo(Integer orgId, String studCard, Integer clasId) {
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
				"AND u.user_idnumber = ? AND c.clas_id != ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(studCard);
		params.add(clasId);
		List<Map<String, Object>> stuInfoList = baseDaoEx.queryListBySql(sql, params);
		return stuInfoList;
	}
	
	/***
	 * 插班
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void joinClass(String userTypeName, Map<String, Object> reqMap, SessionInfo sessionInfo, Integer orgId, String studName, Integer studId, Integer clasId, Integer clasType, String tuitionFee, Integer insertPayType, String parentOpenId, Integer parentId) throws Exception {
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Date now = new Date();
		int expiredTime = applyServiceImpl.getExpiredTime(orgId);
		Date payExpiredTime =new Date(now.getTime() + expiredTime*60*60*1000);
		Classes classes = classDao.getClassInfo(orgId, clasId);
		if (clasType == null) {//根据clasId获取班级类型
			clasType = classes.getClas_type();
		}
		Integer termId = classes.getTerm_id();
		//获取学期信息
		List<Map<String, Object>> termInfo = classDao.getTermInfo(orgId, termId);
		String termName = "";
		if (termInfo != null && termInfo.size() > 0) {
			Map<String, Object> termMap = termInfo.get(0);
			termName = (String) termMap.get("term_name");
		}
		Integer camId = classes.getCam_id();
		//String clasName = (String) reqMap.get("clasName");
		/**
		 * 校验当前学员是否在学员班级表 学员状态是否为4（update） 其他insert 
		 */
		/*Integer status = Consts.STUD_STATUS_4SIGNUP_VOIDED;
		List<Map<String, Object>> fourList = classDao.getStudentListByClasIdAndStudId(clasId, studId, status);
		if (fourList != null && fourList.size() > 0) {
			
		}*/
		String hql="from StudentClass where studId=? and clasId=? and camId=? and stuStatus="+Consts.STUD_STATUS_4SIGNUP_VOIDED+" and isDel=0";
		List<Object> params = new ArrayList<Object>();
		params=new ArrayList<>();
		params.add(studId);
		params.add(clasId);
		params.add(camId);
		List<StudentClass> studentClassList = baseDaoEx.queryListByHql(hql, params);
		String insertPayTypeName = "";
        String clasName = "";
        String subjectName = "";
        String categoryName = "";
        String classUnsetTime = "";
        Integer userTypeId = sessionInfo.getUserId();
		//根据班级id查询 班级的科目、类目、班级名称、上课星期及上课时间
        Map<String, Object> clasMap = classDao.getTechListByClasId(clasId);
        clasName = (String) clasMap.get("clas_name");
        subjectName = (String) clasMap.get("subject_name");
        categoryName = (String) clasMap.get("category_name");
        classUnsetTime = (String) clasMap.get("class_unset_time");
		if (insertPayType == 1) {
			insertPayTypeName = Constant.IMMEDIATE_PAMENT_JOIN_CLASS;//"立即缴费";
		} else if (insertPayType == 2) {
			insertPayTypeName = Constant.THE_OLD_RENEWAL_JOIN_CLASS;//"老生待续费";
		} else {
			
		}
		//Integer addOrUopdate = 1;//1:添加支付详情表  2：修改支付详情表
        String techIds = (String) clasMap.get("tech_ids");//教师id
        String[] techIdArr = null;
		if (null != insertPayType) {
			if (insertPayType == 1) {//1：立即缴费插班 
				log.info("--------------------------------------------------立即缴费插班 ----------------------------------------------------------");
				StudentClass sc = new StudentClass();
				if(studentClassList != null && studentClassList.size() > 0) {//当前班级存在这个学员 学员状态（报名已作废） 进行插班（更新）将学员状态置为3（报名待缴费）
					StudentClass stuClas = studentClassList.get(0);
					stuClas.setStuStatus(Consts.STUD_STATUS_3SIGNUP_TO_PAY);
					stuClas.setQuota_hold(Constant.IS_QUOTA_HOLD_YES);
					stuClas.setPay_expired_time(payExpiredTime);
					//sc.setStu_class_id(stuclas.getStu_class_id());
					//sc.setStudId(studId);
					//sc.setClasId(clasId);
					//sc.setCamId(camId);
					//sc.setIsPrint(0);
					//sc.setIsDel(Constant.IS_DEL_NO);
					//sc.setInsertTime(new Date());
					//sc.setQuotaHold(Constant.IS_QUOTA_HOLD_YES);
					//sc.setStuStatus(Consts.STUD_STATUS_3SIGNUP_TO_PAY);//报名待缴费
					//sc.setPayExpiredTime(payExpiredTime);
					baseDaoEx.updateOne(stuClas);
				}else {//当前班级存在这个学员 学员状态（退费已完成）或不存在  进行插班（新增）将学员状态置为3（报名待缴费）
					sc.setStudId(studId);
					sc.setClasId(clasId);
					sc.setCamId(camId);
					sc.setIsPrint(0);
					sc.setIsDel(Constant.IS_DEL_NO);
					sc.setInsertTime(new Date());
					sc.setQuotaHold(Constant.IS_QUOTA_HOLD_YES);
					sc.setStuStatus(Consts.STUD_STATUS_3SIGNUP_TO_PAY);//报名待缴费
					sc.setPayExpiredTime(payExpiredTime);
					baseDaoEx.saveOne(sc);
				}
				//给职教班级老师发送插班信息（没有不发送）
		        if (techIds != null && techIds.length() > 0) {
		        	techIdArr = techIds.split(",");
		    		//发送插班成功信息
		        	for (int i = 0; i < techIdArr.length; i++) {
		        		Map<String,Object> msgMap = new HashMap<String, Object>();
		        		String openId = classDao.getWXOpenId(Integer.parseInt(techIdArr[i]));
		        		log.info("---------------------------------------获取老师的openId---------------------------------------------------" + openId);
		        		if (StringUtils.isNotBlank(openId)) {
		            		msgMap.put("orgId", String.valueOf(orgId));
		            		msgMap.put("studName", studName);
		            		msgMap.put("tuitionFee", "");
		            		msgMap.put("clasName", clasName);
		            		msgMap.put("subjectName", subjectName);
		            		msgMap.put("categoryName", categoryName);
		            		msgMap.put("classUnsetTime", classUnsetTime);
		            		msgMap.put("open_id", openId);
		            		//将插班信息发送到q
		            		log.info("---------------------------------------将插班信息发送到q---------------------------------------------------");
		            		mqService.sendMessage("joinClassExchange", "joinClassQueueKey", msgMap);
		        		} else {
		        			log.info("---------------------------------------获取不到老师的openId，不发送插班信息---------------------------------------------------");
		        		}

		        	}
		        }
		        //发送插班信息到家长  调用报名
		        Map<String, Object> parentMap = new HashMap<String, Object>();
		        if (parentId == null) {
		        	List<Map<String, Object>> list = classDao.getStudentToParent(studId);
		        	if (list !=  null && list.size() > 0) {
		        		Map<String, Object> map = list.get(0);
		        		parentId = (Integer) map.get("parent_id");
		        		log.info("---------------------------------------获取parentId---------------------------------------------------" + parentId);
		        		if (parentId == null) {
		        			parentId = -1;
		        		}
		        	}
		        }
		        parentMap.put("orgId", orgId);
		        parentMap.put("camId", camId);
		        parentMap.put("classId", clasId);
		        parentMap.put("studId", studId);
		        parentMap.put("parentId", parentId);
		        parentMap.put("applyResult", 1);
		        parentMap.put("expiredTimes", expiredTime);
		        parentMap.put("openId", parentOpenId);
		        //parentMap.put("tuitionFees", tuitionFee);
		        //调用报名（含创建支付详情表）
				log.info("------------------------------------------------------创建支付详情表  插班缴费金额为--------------------------------------------------------------" + tuitionFee);
		        chargeDetailService.createChargeDetail(orgId, studId, clasId, camId,null,tuitionFee);
		        Map<String,Object> messageMap=new HashMap<>();//发送微信的map 给家长
				messageMap.put("first", "恭喜您，报名成功！");
				messageMap.put("keyword1", tuitionFee+"元");
				messageMap.put("keyword2", subjectName+"（"+classes.getClas_name()+"）" + termName);
				messageMap.put("remark","请务必在"+expiredTime+"小时内完成支付，否则名额将自动作废。");
				log.info("--------------------------------------------------发送插班信息到家长 ----------------------------------------------------------");
				//发送微信
		 		this.sendWX(orgId,studId,1,parentOpenId,messageMap);
		 		/**
		 		 * expiredTime*60*60*1000  名额作废
		 		 */
		 		Map<String,Object> map=new HashMap<>();
				map.put("camId", camId);
				map.put("classId", clasId);
				map.put("studId", studId);
				mqService.sendMessage("applyExchange", "applyCancelQueuelKey", map, expiredTime*60*60*1000);

			} else {//2：老生续费插班
				log.info("--------------------------------------------------老生待续费插班 ----------------------------------------------------------");
				StudentClass sc = new StudentClass();
				if (studentClassList != null && studentClassList.size() > 0) {//已有该学员状态为4 更新
					StudentClass stuClas = studentClassList.get(0);
					stuClas.setStu_status(Consts.STUD_STATUS_1OLD_TO_PAY);
					stuClas.setQuota_hold(Constant.IS_QUOTA_HOLD_NO);
					//sc.setStu_class_id(stuclas.getStu_class_id());
					//sc.setStudId(studId);
					//sc.setClasId(clasId);
					//sc.setCamId(camId);
					//sc.setIsPrint(0);
					//sc.setIsDel(Constant.IS_DEL_NO);
					//sc.setInsertTime(new Date());
					//sc.setQuotaHold(Constant.IS_QUOTA_HOLD_NO);
					//sc.setStuStatus(Consts.STUD_STATUS_1OLD_TO_PAY);//老生待续费
					//sc.setPayExpiredTime(payExpiredTime);
					baseDaoEx.updateOne(sc);
				} else {//已有该学员状态为8 或没有该学员 新增
					sc.setStudId(studId);
					sc.setClasId(clasId);
					sc.setCamId(camId);
					sc.setIsPrint(0);
					sc.setIsDel(Constant.IS_DEL_NO);
					sc.setInsertTime(new Date());
					sc.setQuotaHold(Constant.IS_QUOTA_HOLD_NO);
					sc.setStuStatus(Consts.STUD_STATUS_1OLD_TO_PAY);//老生待续费
					//sc.setPayExpiredTime(payExpiredTime);
					baseDaoEx.saveOne(sc);
				}
				//

			}
		}

		//修改班级的可用名额
		log.info("---------------------------------------------修改班级的可用名额----------------------------------------------------------");
		applyServiceImpl.doUsableNum(clasId);
		//插入支付详情表   班级id 机构id 校区id 学员id 学员名称
		//log.info("------------------------------------插入支付详情表   班级id 机构id 校区id 学员id 学员名称----------------------------------------");
		//chargeDetailService.createChargeDetail(orgId, studId, clasId, camId, null, tuitionFee);
		//插入操作日志表
        LoggerUtil.save(orgId, camId, studId, EnumLog.TARGET_TYPE_CLASS.getKey(), studName, EnumLog.OPERATION_STU_JOIN_CLASS.getKey(), insertPayTypeName + EnumLog.OPERATION_STU_JOIN_CLASS.getValue() + subjectName + "(" + clasName + ")", Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
	}
	/**
	 * 发送微信
	 * @param orgId
	 * @throws Exception
	 */
	public void sendWX(int orgId,int studId,int applyResult,String openId,Map<String,Object> messageMap) {
		try {
			String templateId="";
			templateId=tokenService.getSNGWXTemplateId(String.valueOf(orgId), Constant.TEMPLATE_NAME_ORDER_NOT_PAID_REMINDER);	
			messageMap.put("open_id", openId);
			messageMap.put("templateId", templateId);
			WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(messageMap);
			String access_token = tokenService.getAccessTokenByOrg_Id(orgId);
			SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("报名后微信推送失败"+ex.getMessage());
		}
	}
	@Override
	public void consumptionJoinClass(Map<String, Object> msgMap) {
		String orgId = (String) msgMap.get("orgId");
		String studName = (String) msgMap.get("studName");
		String tuitionFee = (String) msgMap.get("tuitionFee");
		String clasName = (String) msgMap.get("clasName");
		String subjectName = (String) msgMap.get("subjectName");
		//String categoryName = (String) msgMap.get("categoryName");
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
		JSONObject msgJson = new JSONObject();
		msgJson.put("msg", mqMap);
		log.info("-----------------------------------------------插班发送内容----------------------------------------------------");
		log.info(String.valueOf(msgJson));
		try {
			this.sendWX(Integer.parseInt(orgId), mqMap);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
			String templateId = tokenService.getSNGWXTemplateId(String.valueOf(orgId), Constant.TEMPLATE_NAME_JOIN_CLASS_SUCCESS_REMIND);
			mqMap.put("templateId", templateId);
			log.info("---------------------------------------获取少年宫微信模板ID----------------------------------------------" + templateId);
			WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(mqMap);
			String access_token = tokenService.getAccessTokenByOrg_Id(orgId);
			log.info("---------------------------------------获取少年宫微授权token----------------------------------------------" + access_token);
			SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
			log.info("---------------------------------------插班信息发送完成----------------------------------------------" + access_token);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("插班微信推送失败"+ex.getMessage());
		}
	}
	/***
	 * 获取当前班级下的上课时间、班级可容纳人数、起始生日等
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void delClass(Integer orgId, Integer clasId) throws Exception {
		String sql = " UPDATE classes SET is_del = 1 WHERE clas_id = ? ";
		List<Object> params = new ArrayList<Object>();
    	params.add(clasId);
    	baseDaoEx.executeSql(sql, params);
	}
	/***
	 * 查询班级的人数（除 报名已作废  退费已完成）
	 */
	@Override
	@Transactional
	public Integer getClassNum(Integer orgId, Integer clasId) {
		return classDao.getClassNum(orgId, clasId);
	}
	/***
	 * 查询班级的实收学费
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public BigDecimal getClassFees(Integer orgId, Integer clasId) {
		return classDao.getClassFees(orgId, clasId);
	}
	/***
	 * 当前学期下，根据前段传来的班级名称 查询数据库是否有这个名称的班级
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Integer getClassCount(Integer orgId, Integer termId, String clasName, Integer clasId) {
		String sql = " SELECT COUNT(1) FROM newsng.classes c WHERE\r\n" + 
				"c.is_del = 0 AND  c.org_id = ? AND c.term_id = ? AND c.clas_name = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(orgId);
		params.add(termId);
		params.add(clasName);
		if (null != clasId) {
			sql += "  AND clas_id != ? ";//NOT IN (?)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map getClassListByTerm(Integer orgId, Integer termId, Integer clasType, String camIds) {
		//1':'老生班','2':'新生班
		return classDao.getClassListByTerm(orgId, termId, clasType, camIds);
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map getTeacher(Integer orgId) {
		return classDao.getTeacher(orgId);
	}
	/***
	 * 根据 机构获取所有联合机构信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map<String, Integer> getCampus(Integer orgId, String camIds) {
		Map<String, Integer> campusMap = new HashMap();
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(orgId);
		paramObj.setNeedCount(false);
		Paging<Campus> paging = baseSetCampusManageDao.queryCampusListInfo(paramObj);
		List<Campus> camList = paging.getData();
		if (null != camList && camList.size() > 0) {
			if (null != camIds) {
				boolean flag = camIds.contains(",");
				if (camIds.equals(Constant.ALL_CAMPUS)) {//获取全部校区
					for (Campus campus : camList) {
						campusMap.put(campus.getCam_name(), campus.getCam_id());
					}
				} else if (!flag) {//只有一个校区(camId不存在逗号)
					for (Campus campus : camList) {
						if (camIds.equals(String.valueOf(campus.getCam_id()))) {
							campusMap.put(campus.getCam_name(), campus.getCam_id());
						}
					}
				} else {//以逗号分隔的多个校区
					String[] idArr = null;
					idArr = camIds.split(",");
					for (Campus campus : camList) {
						for (int i = 0; i < idArr.length; i++) {
							if (idArr[i].equals(String.valueOf(campus.getCam_id()))) {
								campusMap.put(campus.getCam_name(), campus.getCam_id());
							}
						}
					}
				}
			}
		}
		return campusMap;
	}
	
	
	/***
	 * 批量导入班级
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void saveClass(ImportClass iClass, Map allTechMap, SessionInfo sessionInfo, String userTypeName) throws Exception {
		Classes classes = iClass.getClasses();
		classes.setIs_del(Constant.IS_DEL_NO);
		classes.setInsert_time(new Date());
		classes.setUpdate_time(new Date());
		Integer camId = classes.getCam_id();
		//插入班级表
		Integer classId = (Integer) baseDaoEx.saveOne(classes);
		String techNames = iClass.getTech_names(); //教师名称_手机号码，多个以英文逗号分隔（张三_18811223344,李四_15812378945）
		//插入班级教师关系表
		if (StringUtils.isNotBlank(techNames)) {//教师名称不为空，插入教师班级关系表
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
		chargeService.createChargeForNewClass(classes);
		//操作日志表
		Integer orgId = sessionInfo.getOrgId();
		Integer userType = sessionInfo.getIdentity();
		//String userTypeName = sessionInfo.getLoginName();
		Integer userTypeId = sessionInfo.getUserId();
		techNames = (techNames == null) ? "" : techNames;
		System.out.println("批量导入班级 日志 ： " + "批量" + EnumLog.OPERATION_CLASS_CREATE.getValue() + classes.getClas_name() + techNames);
        LoggerUtil.save(orgId, camId, classId,EnumLog.TARGET_TYPE_CLASS.getKey(), classes.getClas_name(), EnumLog.OPERATION_CLASS_IMPORT.getKey(), "批量" + EnumLog.OPERATION_CLASS_IMPORT.getValue() + classes.getClas_name() + techNames, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
	}
	
	/***
	 * @Description:批量导入班级
	 * @param fin 文件流
	 * @param orgId 机构id
	 * @param termId 学期id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Map importClass(Workbook wb, String [] columnName, String userTypeName, HttpSession session, SessionInfo sessionInfo, InputStream fin, Integer orgId, Integer termId, String suffix, String camIds) throws Exception {
		Map returnMap = new HashMap<>();
/*		LinkedHashMap<String, String> clasMap = new LinkedHashMap<String, String>();
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
		clasMap.put("联合机构", "name");*/
		//校验数据  不合法添加
		List<ClassTeacher> errorList = new ArrayList<ClassTeacher>();
		//获取当前学期下的所有班级(当前角色下的校区)
		Integer allClasType = null;//新生班和老生班
		Map<String,Integer> allClassMap = this.getClassListByTerm(orgId, termId, allClasType, camIds);
		//获取当前机构下的所有校区  获取当前角色下的校区列表
		Map<String,Integer> allCampusMap = this.getCampus(orgId, camIds);
		//获取当前校区的所有教室
		//Map<String,Integer> allClassRoomMap = this.getClassromm(camId);
		//获取当前机构的所有类目
		Map<String,Integer> allCategoryMap = this.getCategory(orgId);
		//获取当前机构的所有科目
		Map<String,Integer> allSubjectMap = this.getSubject(orgId);
		//获取当前机构的所有老师
		Map<String,Integer> allTechMap = this.getTeacher(orgId);
		//获取当前机构的所有合作机构
		Map<String,Integer> cooMap = this.getCooperative(orgId);
		//List<Object> objectList = new ArrayList<Object>();
		//获取列头 List<String>
		String [] field = {"clas_name", "clas_type_name", "category_name", "subject_name", "cam_name", "building", "floor",
				"classroom_name", "size", "age_range", "start_birthday", "end_birthday", "total_hours", "class_start_date",
				"class_week_name", "class_begin_time", "class_over_time", "class_unset_time", "tuition_fees", "tech_names",
				"name"};
		List<String> filedNames = new ArrayList<String>();
		for (int i = 0; i < field.length; i++ ) {
			filedNames.add(field[i]);
		}
		//读取excel数据转成list
		List<ClassTeacher> ctList = new ArrayList<ClassTeacher>();
		if ("xls".equals(suffix)) {
			try {
				//objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLS);
				ctList = ExcelUtil.readExcelXLSContent(wb, ".xls", ClassTeacher.class, filedNames, true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", "-1");
				return returnMap;
			}
		} else {
			try {
				//objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLS);
				ctList = ExcelUtil.readExcelXLSContent(wb, ".xlsx", ClassTeacher.class, filedNames, true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", "-1");
				return returnMap;
			}
		}
		//List<ClassTeacher> ctList = ExcelImport.excelToList(fin, "", ClassTeacher.class, clasMap);
		Integer errCount = 0;
		//判断list中的数据是否合法
		if (null != ctList && ctList.size() > 0) {
			log.info("--------------------------------开始校验导入班级数据-----------------------------------");
			for (ClassTeacher ct : ctList) {
				
				StringBuffer errorBuffer = new StringBuffer("");
				//校验excel中的每一行数据是否为合法数据，默认合法
				boolean validateFlag = true;
				//校验班级名称
				String clasName = ct.getClas_name();
				if (StringUtils.isNotBlank(clasName)) {
					boolean clasNameFlag = allClassMap.containsKey(clasName);
					if (clasNameFlag == true) {
						errorBuffer.append("当前学期下的班级名称不可重复;");
						validateFlag = false;
					}
				} else {
					errorBuffer.append("班级名称不能为空;");
					validateFlag = false;
				}
				//校区名称校验  并获取该校区的id
				Integer camId = null;
				//根据校区id获取教室列表，用来验证教室名称是否合法
				Map<String,Integer> allClassRoomMap = null;
				String campusName = ct.getCam_name();
				if (StringUtils.isNotBlank(campusName)) {
					boolean campusFlag = allCampusMap.containsKey(campusName);
					if (campusFlag == false) {
						errorBuffer.append("校区名称不存在基础校区数据中;");
						errorBuffer.append("所在教室、所在教学楼、所在楼层不存在基础教室数据中;");
						validateFlag = false;
						
					} else {
						//
						camId = allCampusMap.get(campusName);
						//获取当前校区下的教室列表
						allClassRoomMap = this.getClassromm(camId);
						//校验教室名称
						String building = ct.getBuilding();
						String floor = ct.getFloor();
						String classroomName = ct.getClassroom_name();
						if (StringUtils.isNotBlank(building) && StringUtils.isNotBlank(floor) && StringUtils.isNotBlank(classroomName)) {
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
					}
				} else {
					errorBuffer.append("校区名称不能为空;");
					validateFlag = false;
				}
				//校验类目名称
				String categoryName = ct.getCategory_name();
				if (StringUtils.isNotBlank(categoryName)) {
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

				//校验科目名称
				String subjectName = ct.getSubject_name();
				if (StringUtils.isNotBlank(subjectName)) {
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
				System.out.println("jiaoshi 名称 ：" + techNames);
				if (StringUtils.isNotBlank(techNames)) {//
					String[] techArray = techNames.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
			        if (0 != techArray.length) {
			        	boolean techFlag = true;
			        	for (int i = 0; i < techArray.length; i++) {
				        	//String[] nameAndPhone = techArray[i].split(",");
				        	//String techName = nameAndPhone[0];
				        	//String phone = nameAndPhone[1];
				        	//String valueStr = techName + "_" + phone; 
				        	techFlag = allTechMap.containsKey(techArray[i]);
				        	int a = 0;
				        	if (techFlag == false) {
				        		if (a == 0) {
				        			errorBuffer.append("教师名称不存在基础教师数据中;");
				        			validateFlag = false;
				        		}
				        	}
				        }
			        }

				}
				//联合机构如果不为空，必须在合作机构中名字一致。
				String cooName = ct.getName();
				if (StringUtils.isNotBlank(cooName)) {
					cooName = cooName.trim();
					boolean cooFlag = cooMap.containsKey(cooName);
					if (cooFlag == false) {
						errorBuffer.append("联合机构的名称不存在合作结构数据中;");
						validateFlag = false;
					}
				}
				log.info("--------------------------------校验导入班级数据完成-----------------------------------");
				if (validateFlag == false) {// 当前条校验不通过
					log.info("--------------------------------校验数据不通过  添加到errorList中-----------------------------------");
					//将错误信息的当前数据添加到错误列表中
					ct.setError_msg(errorBuffer.toString());
					errorList.add(ct);
					errCount++;
				} else {// 当前条数据校验合法
					//将合法数据添加到正确列表中
					log.info("--------------------------------校验数据通过 进行添加入库-----------------------------------");
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
					/*Integer campusId = (Integer) allCampusMap.get(ct.getCam_name());
					clas.setCam_id(campusId);//校区id*/
					clas.setSize(Integer.parseInt(ct.getSize()));//班级容量
					clas.setUsable_num(Integer.parseInt(ct.getSize()));//创建班级时，初始化可用名额  默认与班级容量相等
					clas.setAge_range(ct.getAge_range());//年龄要求
					try {
						clas.setStart_birthday(CommonUtils.stringToDate(ct.getStart_birthday(), "yyyy-MM-dd"));
						//开始生日
						clas.setEnd_birthday(CommonUtils.stringToDate(ct.getEnd_birthday(), "yyyy-MM-dd"));//结束生日
						clas.setClass_start_date(CommonUtils.stringToDate(ct.getClass_start_date(), "yyyy-MM-dd"));//开课日期
					} catch (ParseException e1) {
						log.info("CommonUtils.stringToDate Start_birthday ParseException :" + e1);
						e1.printStackTrace();
					}

					clas.setTotal_hours(Integer.parseInt(ct.getTotal_hours()));//总课时数
					Integer classWeek = 0;
					if (StringUtils.isNotBlank(ct.getClass_unset_time())) {//不固定时间 不为空
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
							log.info("CommonUtils.stringToDate classBeginDate ParseException");
							e.printStackTrace();
						}
						clas.setClass_week(ValidateObject.getClassWeek(classWeek, ct.getClass_week_name()));
						clas.setClass_begin_time(classBeginDate);
						clas.setClass_over_time(classOverDate);
						clas.setClass_unset_time(null);
					}
					clas.setTuition_fees(ct.getTuition_fees());//学费
					if (StringUtils.isNotBlank(ct.getName())) {
						clas.setCooperation_id(cooMap.get(ct.getName()));//合作机构
					} else {
						clas.setCooperation_id(null);//合作机构
					}
					iClass.setTech_names(techNames);
					iClass.setClasses(clas);
					// 手动控制事务
					HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
					TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
					//保存
					try {
						this.saveClass(iClass, allTechMap, sessionInfo, userTypeName);
						//导入时保存操作日志，格式为操作动作导入班级，操作对象是科目 （班级名）教师名 上课时间，操作内容是批量导入班级 班级名。
				        //LoggerUtil.save(orgId, camId, clas.getClas_id(), 2, clas.getClas_name(), EnumLog.OPERATION_CLASS_IMPORT.getKey(), "批量" + EnumLog.OPERATION_CLASS_IMPORT.getValue()+ " " + clas.getClas_name(), userType, userTypeId, userTypeName, sessionInfo);
						// 所有处理完成后，提交事务
						txManager.commit(txStatus);
					} catch (Exception e) {
						log.info("--------------------------------校验数据通过 添加入库异常 加入errorList中-----------------------------------");
						e.printStackTrace();
						log.info("saveClass  Exception :" + e);
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
			//将错误的list转成json串
			String clasJson = JsonUtil.toJson(errorList, Include.ALWAYS);
			session.setAttribute("classExcel", clasJson);
			/***
			 * 将导入数据进行记录
			 */
			//导入记录表暂时
			/*ImportRecord ir = new ImportRecord();
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
			*/
			returnMap.put("total", ctList.size());
			returnMap.put("errNum", errCount);
			returnMap.put("sucNum", ctList.size()-errCount);
			returnMap.put("code", "0");
			JSONObject json = new JSONObject();
			json.put("key", session.getAttribute("classExcel"));
			log.info("--------------------------------session.getAttribute(classExcel)-----------------------------------");
			log.info("pint classExcel :" + json);
		} else {
			returnMap.put("total", 0);
			returnMap.put("errNum", 0);
			returnMap.put("sucNum", 0);
			returnMap.put("code", "0");
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map importStudent(Workbook wb, String userTypeName, HttpSession session, SessionInfo sessionInfo, InputStream fin, Integer orgId, Integer termId, String suffix, String camIds) throws Exception {
		Map returnMap = new HashMap();
		//班级名称、学员名称、证件号码、学员状态
		LinkedHashMap<String, String> stuMap = new LinkedHashMap<String, String>();
		stuMap.put("班级名称", "clas_name");
		stuMap.put("学员名称", "stud_name");
		stuMap.put("证件号码", "user_idnumber");
		stuMap.put("学员状态", "stu_status");
		Integer userType = sessionInfo.getIdentity();
		Integer userTypeId = sessionInfo.getUserId();
		//获取当前学期下的所有老班级  校区为当前用户下所有的角色校区下的
		Integer clasType = 1;//老生班
		Map<String,Integer> allClassMap = this.getClassListByTerm(orgId, termId, clasType, camIds);
		//获取当机构下所有的学员（已认证）
		Map<String,Integer> allStuMap = this.getStudent(orgId);
		
		//校验数据  不合法添加
		List<ImportStudent> errorList = new ArrayList<ImportStudent>();
		Integer errCount = 0;
		//读取excel数据转成list
		//List<ImportStudent> ctList = ExcelImport.excelToList(fin, "", ImportStudent.class, stuMap);
		String [] field = {"clas_name", "stud_name", "user_idnumber", "stu_status"};
		List<String> filedNames = new ArrayList<String>();
		for (int i = 0; i < field.length; i++ ) {
			filedNames.add(field[i]);
		}
		
		//List<Object> objectList = new ArrayList<Object>();
		List<ImportStudent> ctList = new ArrayList<ImportStudent>();
		//读取excel数据转成list
		//List<ClassTeacher> ctList = new ArrayList<ClassTeacher>();
		if ("xls".equals(suffix)) {
			try {
				//objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLS);
				ctList = ExcelUtil.readExcelXLSContent(wb, ".xls", ImportStudent.class, filedNames, true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", "-1");
				return returnMap;
			}
		} else {
			try {
				//objectList = EasyExcelUtil.readExcelWithModel(fin, ClassTeacher.class, ExcelTypeEnum.XLS);
				ctList = ExcelUtil.readExcelXLSContent(wb, ".xlsx", ImportStudent.class, filedNames, true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", "-1");
				return returnMap;
			}
		}

		if (null != ctList && ctList.size() > 0) {
			log.info("--------------------------------开始校验学员导入数据-----------------------------------");
			for (ImportStudent stu : ctList) {
				//校验excel中的每一行数据是否为合法数据，默认合法
				boolean validateFlag = true;
				String clasName = stu.getClas_name();
				String stuName = stu.getStud_name();
				String idNumber = stu.getUser_idnumber();
				String stuStatus = stu.getStu_status();
				StringBuffer errorBuffer = new StringBuffer("");
				//为空校验：班级名称、学员名称、证件号码，必须都是必填项
				//校验班级名称  并获取班级对应的校区id
				Integer camId = null;
				if (StringUtils.isNotBlank(clasName)) {
					clasName = clasName.trim();
					boolean clasNameFlag = allClassMap.containsKey(clasName);
					if (clasNameFlag == false) {
						errorBuffer.append("当前班级名称应为老生班,不支持新生班导入学员或班级名称不存在基础数据库中;");
						validateFlag = false;
					} else {
						//根据班级名称和学期id 获取当前校区id  与前端传来的权限校区列表对比  
						camId = classDao.getCampusIdByClassNameAndTerm(orgId, clasName, termId);
					}
				} else {
					errorBuffer.append("班级名称不能为空;");
					validateFlag = false;
				}
				//校验 学员名称和证件号码  并且 根据学员名称和证件号码确认在基础数据中存在该学员并且已认证
				if (StringUtils.isNotBlank(stuName) && StringUtils.isNotBlank(idNumber)) {
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
				//根据班级名称和学期获取班级id
				Integer curClasId = allClassMap.get(clasName);
				//根据（学员名称+身份证号）获取学员id
				Integer curStudId = allStuMap.get(stuName + "_" + idNumber);
				//校验导入的学员和班级，是否在该班级已存在(根据班级id和学员id来确定该班级是否存在  并且学员状态为（不占坑）)
				Integer count = classDao.getStudentCountByClasIdAndStudId(curClasId, curStudId);
				if (count > 0) {
					errorBuffer.append("当前学员已存在该班级;");
					validateFlag = false;
				}
				log.info("--------------------------------校验数据完成-----------------------------------");
				if (validateFlag == true) { //数据校验通过 
					log.info("--------------------------------数据校验通过 进行添加-----------------------------------");
					StudentClass sc = new StudentClass();
					Integer clasId = allClassMap.get(clasName);//班级id
					Integer studId = allStuMap.get(stuName + "_" + idNumber);
					/**
					 * 根据班级id和学员id查询学员班级关系表，如果存在学员状态为4（报名已作废） 更新学员状态
					 * 如果 学员状态为8 （退费已完成） 新增
					 */
					List<Map<String, Object>> fourList = classDao.getStudentListByClasIdAndStudId(clasId, studId, Consts.STUD_STATUS_4SIGNUP_VOIDED);
					//List<Map<String, Object>> eightList = classDao.getStudentListByClasIdAndStudId(clasId, studId, Consts.STUD_STATUS_8REFUND_FINISHED);
					
					// 手动控制事务
					HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
					TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
					try {
						if (fourList != null && fourList.size() > 0) {
							Map<String, Object> map = fourList.get(0);
							sc.setStu_class_id((Integer) map.get("stu_class_id"));
							sc.setClasId(clasId);
							sc.setStudId(studId);
							sc.setCamId(camId);
							sc.setStuStatus(Consts.STUD_STATUS_1OLD_TO_PAY);
							sc.setIsPrint(0);
							sc.setIsDel(Constant.IS_DEL_NO);
							sc.setInsertTime(new Date());
							sc.setUpdateTime(new Date());
							sc.setQuotaHold(Constant.IS_QUOTA_HOLD_NO);
							baseDaoEx.updateOne(sc);
						} else {
							sc.setClasId(clasId);
							sc.setStudId(studId);
							sc.setCamId(camId);
							sc.setStuStatus(Consts.STUD_STATUS_1OLD_TO_PAY);
							sc.setIsPrint(0);
							sc.setIsDel(Constant.IS_DEL_NO);
							sc.setInsertTime(new Date());
							sc.setUpdateTime(new Date());
							sc.setQuotaHold(Constant.IS_QUOTA_HOLD_NO);
							baseDaoEx.saveOne(sc);
						}
						//创建支付详情表
						//chargeDetailService.createChargeDetail(orgId, studId, clasId, camId,null,null);
						//导入学员成功，更改班级的可用名额
						applyServiceImpl.doUsableNum(clasId);
						//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
				        LoggerUtil.save(orgId, camId, studId, EnumLog.TARGET_TYPE_STU.getKey(), stuName, EnumLog.OPERATION_STU_IMPORT_STU.getKey(), "批量" + EnumLog.OPERATION_STU_IMPORT_STU.getValue() + " " + stuName + " " + idNumber, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
				        // 所有处理完成后，提交事务
				        txManager.commit(txStatus);
					} catch (Exception e) {
						log.info("--------------------------------数据校验通过 添加出现服务异常-----------------------------------");
						log.info("saveOne or chargeDetailService or applyServiceImpl or LoggerUtil.save Exception :" +e);
						e.printStackTrace();
						//异常回滚
						txManager.rollback(txStatus);
						stu.setErr_msg("数据库服务异常;");
						errorList.add(stu);
						errCount++;
					}
				} else { //校验不通过
					log.info("--------------------------------数据校验不通过 放入errorList中-----------------------------------");
					stu.setErr_msg(errorBuffer.toString());
					errorList.add(stu);
					errCount++;
				}
			}

			session.setAttribute("errNum", errCount);
			session.setAttribute("sucNum", ctList.size()-errCount);
			session.setAttribute("total", ctList.size());
			//将学员错误列表转json
			String studentJson = JsonUtil.toJson(errorList, Include.ALWAYS);
			session.setAttribute("studentExcel", studentJson);
			
			JSONObject json = new JSONObject();
			json.put("key", errorList);
			log.info("--------------------------------session.getAttribute(studentExcel)-----------------------------------");
			log.info("pint classExcel :" + json);
			/***
			 * 将导入数据进行记录
			 */
			//校区id 还没获取
			/*ImportRecord ir = new ImportRecord();
			ir.setCamId(camId);//校区
			ir.setCorrectCount(ctList.size()-errCount);
			ir.setErrorCount(errCount);
			ir.setInsertTime(new Date());
			ir.setOrgId(orgId);
			ir.setTotalCount(ctList.size());//导入总数
			ir.setIsDel(Constant.IS_DEL_NO);
			ir.setResult(1);//导入状态 1成功 0失败
			ir.setCreater(userTypeName);
			baseDaoEx.saveOne(ir);*/
			returnMap.put("code", "0");
			returnMap.put("total", ctList.size());
			returnMap.put("errNum", errCount);
			returnMap.put("sucNum", ctList.size()-errCount);
		} else {
			returnMap.put("code", "0");
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void saveStudentClass(StudentClass sc) {
		baseDaoEx.saveOne(sc);
	}
	/***
 	 *  @Description: 下载导入错误的excel（班级或学员）
	 *  @param type 1:班级 2:学生
	 *  @return
	 * @throws IOException 
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public void downloadErrorExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response, Integer type) throws Exception {
		
		List<String[]> valueList = new ArrayList<String[]>();
		Workbook wb;
		if (type == 1) {//班级
			//、、String clasJson = JsonUtil.toJson(errorList, Include.ALWAYS);
			//、、session.setAttribute("classExcel", clasJson);
			String clasJson = session.getAttribute("classExcel") == null ? "" : (String) session.getAttribute("classExcel");
			System.out.println("downloadErrorExcel   clasJson :" + clasJson + ".... length :" + clasJson.length());
			List<ClassTeacher> errorClass = new ArrayList<ClassTeacher>();
			if (clasJson != null && clasJson.length() > 0) {
				errorClass = JsonUtil.getObjectFromJson(clasJson, new TypeReference<List<ClassTeacher>>() {});
			}
			log.info("--------------------------------session.getAttribute(classExcel) or session.getAttribute(studentExcel)-----------------------------------");
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
			String studentJson = session.getAttribute("studentExcel") == null ? "" : (String) session.getAttribute("studentExcel");
			List<ImportStudent> errStudent = JsonUtil.getObjectFromJson(studentJson, new TypeReference<List<ImportStudent>>() {});
			//List<ImportStudent> errStudent = (List<ImportStudent>) session.getAttribute("studentExcel");
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
				"c.is_del = 0  \r\n" + 
				"AND c.org_id = ?  ");

		params.add(orgId);
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
		String order1 = "ORDER BY cam.cam_name , c.clas_name, cr.classroom_name, c.clas_id ASC";
		sql.append(group);
		sql.append(order1);
		String finalSql = "";
		String order2 = " ORDER BY tt.cam_name , tt.clas_name, tt.classroom_name, tt.clas_id ASC ";

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
		List<ClassToTeacher> list = baseDaoEx.queryListBySql(finalSql, ClassToTeacher.class, params);
		return list;
	}
	
	/***
	 * 统计班级列表：班级学员数量和学费
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map getStatisticsInfo(List<Integer> ids) throws Exception {
		return classDao.getStatisticsInfo(ids);
	}
	
	/***
	 * 查询班级列表
	 */

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Paging<Map<String, Object>> getStudentListByClasId_bak(Integer orgId, Integer clasId,  String status, Paging<Map> page) {
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer(" SELECT c.clas_id, c.clas_name, s.stud_id, s.stud_name, o.user_idnumber, sc.stu_status, sub.subject_name,\r\n" + 
				"CASE WHEN IFNULL(sc.is_print, 0) = 0 THEN '未打印' ELSE '已打印' END is_print,   \r\n" + 
				"sc.voucher_no, IFNULL(ch.money, '--') money, IFNULL(ch.txnAmt, '--') txnAmt,   \r\n" + 
				"IFNULL((ch.money - ch.txnAmt), '--') tf_money, IFNULL(ch.online_pay, 0) online_pay, IFNULL(ch.offline_pay, 0) offline_pay, ch.pay_method pay_method_id,\r\n" + 
				"CASE WHEN ch.pay_method = 1 THEN '现金' WHEN  ch.pay_method = 2 THEN '银行卡' WHEN ch.pay_method = 3 THEN '手机' ELSE '' END pay_method \r\n" + 
				"FROM newsng.classes c  \r\n" + 
				"LEFT JOIN newsng.subject sub ON sub.subject_id = c.subject_id AND sub.is_del = 0\r\n" + 
				"INNER JOIN newsng.student_class sc ON c.clas_id = sc.clas_id  AND sc.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.student s ON sc.stud_id = s.stud_id  AND s.is_del = 0  \r\n" + 
				"INNER JOIN edugate_base.org_user o ON o.user_id = s.user_id  AND o.is_del = 0  \r\n" + 
				"LEFT JOIN newsng.charge_detail ch ON ch.stud_id = s.stud_id AND ch.is_del = 0  \r\n" + 
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
					m.put("stu_status_name", statusName);
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<ClassToTeacher> getTechClassInfoList(Integer orgId, Integer techId, Integer termId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT c.clas_id, c.clas_name,c.class_week, DATE_FORMAT(c.class_begin_time,'%H:%i') class_begin_time,\r\n" + 
				"DATE_FORMAT(c.class_over_time,'%H:%i') class_over_time, c.class_unset_time,\r\n" + 
				"t.tech_name, (IFNULL(c.ss_num, 0) - IFNULL(c.st_num, 0)) num,\r\n" + 
				"IFNULL(ch.ss_money, 0) ss_money, IFNULL(ch.st_money, 0) st_money\r\n" + 
				"FROM edugate_base.teacher t\r\n" + 
				"INNER JOIN newsng.teacher_class tc ON tc.tech_id = t.tech_id AND tc.is_del = 0\r\n" + 
				"INNER JOIN newsng.classes c ON c.clas_id = tc.clas_id AND c.is_del = 0\r\n" + 
				"LEFT JOIN newsng.charge ch ON ch.clas_id = c.clas_id AND ch.is_del = 0\r\n" + 
				"WHERE t.is_del = 0\r\n"; 
				//"AND t.org_id = ?\r\n" + 
				//"AND c.term_id = ?\r\n" + 
				//"AND t.tech_id = ? ";
		sql.append(ssql);
		List<Object> params = new ArrayList<>();
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
		if(null != techId ) {
			sql.append(" AND t.tech_id = ? ");
			params.add(techId);
		}
		List<ClassToTeacher> list = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);
		return list;
	}
	/***
	 * 开课统计表 ：统计当前班级的老师所有开课的班级开课时间、学员人数、学费等信息, 以教师分组统计班级上课及学费总额
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unused"})
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public void downloadPaymentRecords(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, String camId, Integer classWeek,
			String techNames, Integer isAll, String ids) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<Object> params = new ArrayList<>();
		if (isAll != null && isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
			List<ClassToTeacher> list = null;//按照c.clas_id升序
			list = this.getClassList(orgId, termId, clasType, categoryId, subjectId, camId, classWeek, techNames);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					List<String> clasInfo = new ArrayList<String>();
					String clasName = list.get(i).getClas_name();//班级名称
					String ageRange = list.get(i).getAge_range();//年龄范围
					String size = list.get(i).getSize().toString();//容量
					String classroom = list.get(i).getClassroom_name();//教室名称
					String techName = list.get(i).getTech_name();//教师名称
					String classStartDate = CommonUtils.dateFormat(list.get(i).getClass_start_date(), "yyyy-MM-dd");//开课时间
					String unset = list.get(i).getClass_unset_time();//不固定时间
					if (unset == null) {//如不固定时间为空， 将上课星期+开课时间+结束时间赋值给不固定时间  
						unset =	Consts.WEEK_MAP.get(String.valueOf(list.get(i).getClass_week())) + " (" + list.get(i).getClass_begin_time().toString() + "-" + list.get(i).getClass_over_time().toString() + ")";
					}
					String tuitionFees = list.get(i).getTuition_fees();//学费标准
					//根据班级的实退学费和实收学费之差  班级的总缴费
					BigDecimal totalMoney = new BigDecimal("0");
					String stMoney = list.get(i).getSt_money();
					String ssMoney = list.get(i).getSs_money();
					BigDecimal ss = new BigDecimal(ssMoney);
					BigDecimal st = new BigDecimal(stMoney);
					totalMoney = ss.subtract(st);
					clasInfo.add(clasName);
					clasInfo.add(ageRange);
					clasInfo.add(size);
					clasInfo.add(classroom);
					clasInfo.add(techName);
					clasInfo.add(classStartDate);
					clasInfo.add(unset);
					clasInfo.add(tuitionFees);
					//clasInfoList.add(clasInfo);
					//根据班级id获取学员缴费记录列表
					Integer clasId = list.get(i).getClas_id();
					List<ChargeDetail> studentPaymentRecordsList = chargeDetailService.getByClasId(clasId);
					//List<Map<String, Object>> studentPaymentRecordsList = classDao.getStudentPaymentRecordsList(orgId, clasId);
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					//BigDecimal totalMoney = new BigDecimal("0"); //初始化班级学费总金额
					List<String> totalStuInfo = new ArrayList<String>();
					BigDecimal zsjs = new BigDecimal(0);
					int n = 1;
					for (int m = 0; m < studentPaymentRecordsList.size(); m++) {
						List<String> stuInfo = new ArrayList<String>();
						stuInfo.add(String.valueOf(n));//序号
						stuInfo.add((String) studentPaymentRecordsList.get(m).getStud_name());//学员名称
						stuInfo.add((String) studentPaymentRecordsList.get(m).getUser_idnumber());//学员身份证号码
						stuInfo.add((String) studentPaymentRecordsList.get(m).getUser_mobile());//家长手机号码
						stuInfo.add((String) studentPaymentRecordsList.get(m).getTxnType());//缴费或退费
						//缴费时间   将查询的缴费时间转为指定日期格式（yyyy-mm-dd）
						Date txnTime = studentPaymentRecordsList.get(m).getTxnTime();
						/*String txnTimeStr = CommonUtils.dateFormat(txnTime, null);
						Date txnDate = null;
						try {
							txnDate = CommonUtils.stringToDate(txnTimeStr, null);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						stuInfo.add(CommonUtils.dateFormat(txnDate, "yyyy-MM-dd").toString());//缴费时间
						*/
						stuInfo.add(CommonUtils.dateFormat(txnTime, "yyyy-MM-dd").toString());//缴费时间
						stuInfo.add(MoneyUtil.fromFENtoYUAN((String) studentPaymentRecordsList.get(m).getTxnAmt()));//金额
						stuInfo.add("");//备注
						
						/*BigDecimal fees = new BigDecimal((String) studentPaymentRecordsList.get(m).get("txnAmt"));
						totalMoney = totalMoney.add(fees);*/
						stuInfoList.add(stuInfo);
						n++;
					}
					//计算当前班级下的学员学费数的总计
					totalStuInfo.add(String.valueOf(String.valueOf(n)));
					totalStuInfo.add("总计");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add(MoneyUtil.fromFENtoYUAN(totalMoney.toString()));
					totalStuInfo.add("");
					stuInfoList.add(totalStuInfo);
					try {
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportStudentPaymentRecordExcel(workbook, i, sheetName, Constant.IMPORT_EXCEL_PAYMENT_RECORDS, stuInfoList, clasInfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//将excel下载
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员缴费记录表.xls");
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
					"IFNULL(c.ss_money, 0) ss_money, IFNULL(c.st_money, 0) st_money,\r\n" +
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
						System.out.println("strArray :" +strArray[i]);
					} else {
						sql.append(strArray[i]);
						sql.append(",");
						System.out.println("strArray :" +strArray[i]);
					}
				}
				sql.append(" )");
			}
			sql.append(" GROUP BY c.clas_id ");
			sql.append(" ORDER BY c.clas_id ASC ");
			params.add(orgId);
			int i = 0;
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
						unset =	Consts.WEEK_MAP.get(String.valueOf(ct.getClass_week())) + " (" + ct.getClass_begin_time().toString() + "-" + ct.getClass_over_time().toString() + ")";
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
					//根据班级的实退学费和实收学费之差  班级的总缴费
					BigDecimal totalMoney = new BigDecimal("0");
					String stMoney = ct.getSt_money();
					String ssMoney = ct.getSs_money();
					BigDecimal ss = new BigDecimal(ssMoney);
					BigDecimal st = new BigDecimal(stMoney);
					totalMoney = ss.subtract(st);
					//根据班级id获取学员缴费记录列表
					Integer clasId = ct.getClas_id();
					//List<Map<String, Object>> studentPaymentRecordsList = classDao.getStudentPaymentRecordsList(orgId, clasId);
					List<ChargeDetail> studentPaymentRecordsList = chargeDetailService.getByClasId(clasId);
					List<List<String>> stuInfoList = new ArrayList<List<String>>();//学员信息列表
					
					List<String> totalStuInfo = new ArrayList<String>();//统计班级学费信息
					BigDecimal zsjs = new BigDecimal(0);
					int n = 1;
					for (int m = 0; m < studentPaymentRecordsList.size(); m++) {
						List<String> stuInfo = new ArrayList<String>();
						stuInfo.add(String.valueOf(n));//序号
						stuInfo.add((String) studentPaymentRecordsList.get(m).getStud_name());//学员名称
						stuInfo.add((String) studentPaymentRecordsList.get(m).getUser_idnumber());//学员身份证号码
						stuInfo.add((String) studentPaymentRecordsList.get(m).getUser_mobile());//家长手机号码
						stuInfo.add(studentPaymentRecordsList.get(m).getTxnType());//缴费或退费
						//缴费时间   将查询的缴费时间转为指定日期格式（yyyy-mm-dd）
						Date txnTime = studentPaymentRecordsList.get(m).getTxnTime();
						/*String txnTimeStr = CommonUtils.dateFormat(txnTime, null);
						Date txnDate = null;
						try {
							txnDate = CommonUtils.stringToDate(txnTimeStr, null);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						stuInfo.add(CommonUtils.dateFormat(txnDate, "yyyy-MM-dd").toString());//缴费时间
						*/
						stuInfo.add(CommonUtils.dateFormat(txnTime, null).toString());//缴费时间
						stuInfo.add(MoneyUtil.fromFENtoYUAN((String) studentPaymentRecordsList.get(m).getTxnAmt()));//金额
						stuInfo.add("");//备注
						stuInfoList.add(stuInfo);
						n++;
					}
					//计算当前班级下的学员学费数的总计
					totalStuInfo.add(String.valueOf(String.valueOf(n)));
					totalStuInfo.add("总计");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add("");
					totalStuInfo.add(MoneyUtil.fromFENtoYUAN(totalMoney.toString()));
					totalStuInfo.add("");
					stuInfoList.add(totalStuInfo);
					try {
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportStudentPaymentRecordExcel(workbook, i, sheetName, Constant.IMPORT_EXCEL_PAYMENT_RECORDS, stuInfoList, clasInfo);
						i++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//将excel下载
				try {
					ExcelUtil.makeAndOutHSSFExcelFile(request, response, workbook, "学员缴费记录表.xls");
				} catch (Exception e) {
					log.info("makeAndOutHSSFExcelFile Ex :" + e);
					e.printStackTrace();
				}
			}
		}
	}
	
	/***
	 * 学员考勤表
	 * 1 查询班级列表，循环班级，获取每个班级的信息，根据班级id获取班级下的学员  写入excel
	 * 
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public void downloadStudentsAttendance(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, String camId, Integer classWeek,
			String techNames, Integer isAll, String ids) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		if (isAll != null && isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
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
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportExcelStuCheck(workbook, i, sheetName, Constant.IMPORT_EXCEL_STU_CHECK, stuInfoList, clasInfo);
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
			/*//班级ids
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
			List<ClassToTeacher> ctList = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);*/
			List<ClassToTeacher> ctList = classDao.getListByClasId(orgId, ids);	
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
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportExcelStuCheck(workbook, i, sheetName, Constant.IMPORT_EXCEL_STU_CHECK, stuInfoList, clasInfo);
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
	 * 根据班级id获取班级中的学员信息，(学员状态为缴费已完成)
	 * 学员名称
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
	 * 获取的学员列表（学员状态为：缴费已完成）
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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
				"AND sc.stu_status = 5\r\n" +
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public void downloadStudentRoster(HttpServletRequest request, HttpServletResponse response, Integer orgId,
			Integer termId, Integer clasType, Integer categoryId, Integer subjectId, String camId, Integer classWeek,
			String techNames, Integer isAll, String ids, String status) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		if (isAll != null && isAll == 1) {//判断 是否选择全部分页数据  1：是   其他：否
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
					//List<StudentRoster> srList = this.getStudentRosterList(orgId, list.get(i).getClas_id());
					List<StudentRoster> srList = classDao.getStudentRosterList(orgId, list.get(i).getClas_id(), status);
					JSONObject json = new JSONObject();
					json.put("key", srList);
					log.info("json --------------- :" + json);
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
							stuInfoList.add(stuInfo);//将每条学员信息加入到学员列表
						}
					}
					//ExcelExport.exportStudentRosterExcel(workbook, i, sheetTitle, headers, result, clasInfo);
					try {
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportStudentRosterExcel(workbook, i, sheetName, Constant.IMPORT_EXCEL_STU_ROSTER, stuInfoList, clasInfo);
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
			/*List<Object> params = new ArrayList<>();
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
			List<ClassToTeacher> ctList = baseDaoEx.queryListBySql(sql.toString(), ClassToTeacher.class, params);*/
			List<ClassToTeacher> ctList = classDao.getListByClasId(orgId, ids);
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
					//List<StudentRoster> srList = this.getStudentRosterList(orgId, ctList.get(i).getClas_id());
					List<StudentRoster> srList = classDao.getStudentRosterList(orgId, ctList.get(i).getClas_id(), status);
					JSONObject json = new JSONObject();
					json.put("key", srList);
					log.info("json --------------- :" + json);
					
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
						String sheetName = clasInfo.get(0);
						sheetName = sheetName.replaceAll("/", " ");
						ExcelExport.exportStudentRosterExcel(workbook, i, sheetName, Constant.IMPORT_EXCEL_STU_ROSTER, stuInfoList, clasInfo);
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
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
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

	@Override
	@Transactional (propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateClasssAndTeacher(String userTypeName, Map<String, Object> reqMap, SessionInfo sessionInfo,
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
		if (null != reqMap.get("cooperationId")) {
			Integer cooperationId = Integer.parseInt((String) reqMap.get("cooperationId"));//联合机构id(教师的所属机构)
			curClasses.setCooperation_id(cooperationId);
		} else {
			curClasses.setCooperation_id(null);
		}
		//修改班级表
		baseDaoEx.updateOne(curClasses);
		applyServiceImpl.doUsableNum(clasId);
		String techIds = (String) reqMap.get("techIds"); //教师id，多个以英文逗号分隔（121,145）
		//  ？  支付项目表
  		// TODO Auto-generated method stub
		//班级教师关系表
		String sql = " UPDATE teacher_class SET is_del = 1 WHERE clas_id = ? ";
		if (StringUtils.isBlank(techIds)) {
			//前段返回的班级教师为空，更新该班级与之前老师的关系
			//if (null != techList && techList.size() > 0) {
				//for (Map map : techList) {
					List<Object> params = new ArrayList<Object>();
					//Integer techId = (Integer) map.get("tech_id");
		        	params.add(clasId);
		        	baseDaoEx.executeSql(sql, params);
		        //}
			//}
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
		//根据班级id查询 班级的科目、类目、班级名称、上课星期及上课时间
        Map<String, Object> clasMap = classDao.getTechListByClasId(clasId);
        String subjectName = (String) clasMap.get("subject_name");
        //String categoryName = (String) clasMap.get("category_name");
        String unsetAndFix = (String) clasMap.get("class_unset_time");
        String techNames = clasMap.get("tech_names") == null ? "教师待定" : (String) clasMap.get("tech_names");
        
		//操作日志表
		Integer userTypeId = sessionInfo.getUserId();
		//导入时保存操作日志，格式为操作动作导入学员，操作对象是学员名，操作内容是批量导入学员 学员名 证件号码
        LoggerUtil.save(orgId, camId, clasId, EnumLog.TARGET_TYPE_CLASS.getKey(), clasName, EnumLog.OPERATION_CLASS_EDIT.getKey(), EnumLog.OPERATION_CLASS_EDIT.getValue() + subjectName + "(" +clasName + ")" + " " + techNames + unsetAndFix, Constant.IDENTITY_TEACHER, userTypeId, userTypeName, sessionInfo);
	
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
	
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Classes getBySCId(String stu_class_id) {
		return classDao.getBySCId(stu_class_id);
	}

	/***
	 * 获取学员在班级的学员状态
	 */
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Integer getStudStatusByClasId(Integer clasId, Integer studId) {
		Map<String, Object> stuMap = classDao.getStudStatusByClasId(clasId, studId);
		//获取学员的状态
		Integer stuStatus = (Integer) stuMap.get("stu_status");
		return stuStatus;
	}
	
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<Map<String, Object>> getStudentInfoByClasIdAndStudId(Integer clasId, Integer studId, String status) {
		return classDao.getStudentInfoByClasIdAndStudId(clasId, studId, status);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Map<String, Object> getStatisticsStuList(Integer orgId, Integer clasId) throws Exception {
		List<Map<String, Object>> stuList = classDao.getStuList(orgId, clasId);
		StringBuffer stuIds = new StringBuffer("");
		if (null != stuList && stuList.size() > 0) {
			for (int i = 0; i < stuList.size(); i++ ) {
				Integer stuId = (Integer) stuList.get(i).get("stud_id");
				if (i == stuList.size()-1) {
					stuIds.append(String.valueOf(stuId));
				} else {
					stuIds.append(String.valueOf(stuId));
					stuIds.append(",");
				}
			}
		}
		Map<String, Object> stuMap = classDao.getClasInfoById(orgId, clasId);
		Integer stNum = stuMap.get("st_num") == null ? 0 : (Integer) stuMap.get("st_num");
		Integer ssNum = stuMap.get("ss_num") == null ? 0 : (Integer) stuMap.get("ss_num");
		String stMoney = stuMap.get("st_money") == null ? "0" : (String) stuMap.get("st_money");
		String ssMoney = stuMap.get("ss_money") == null ? "0" : (String) stuMap.get("ss_money");
		BigDecimal ss = new BigDecimal(ssMoney);
	    BigDecimal st = new BigDecimal(stMoney);
		String sjsMoney = ss.subtract(st).toString();
		Integer sjsNum = ssNum - stNum;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stuIds", String.valueOf(stuIds));
		map.put("st_num", stNum);
		map.put("ss_num", ssNum);
		map.put("st_money", stMoney);
		map.put("ss_money", ssMoney);
		map.put("sjsMoney", MoneyUtil.fromFENtoYUAN(sjsMoney));
		map.put("sjsNum", sjsNum);
		return map;
	}
	
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public List<Map<String, Object>> getStuList(Integer orgId, Integer clasId) {
		return classDao.getStuList(orgId, clasId);
	}
	
	@Override
	public Map<String, Object> getStatisticsInfoByTermAndCampus(Integer orgId, Integer termId, String camId, Integer clasType) throws Exception {
		//当前机构下的注册人数
		Integer registerCount = 0;
		registerCount = classDao.getRegisterCount(orgId);
		//当前机构某学期某校区学员总人数
		//当前机构某学期某校区班级总数
		Map<String, Object> stuAndclas = classDao.getStudentCountAndClasCountByTermAndCampus(orgId, termId, camId, null);
		Integer stuCount = stuAndclas.get("stuCount") == null ? 0 : (Integer) stuAndclas.get("stuCount");
		Integer clasCount = stuAndclas.get("clasCount") == null ? 0 : (Integer) stuAndclas.get("clasCount");
		String zsjsMoney = stuAndclas.get("zsjsMoney") == null ? "0" : (String) stuAndclas.get("zsjsMoney");
		
		//当前机构某学期某校区学员总学费
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("registerCount", registerCount);
		map.put("stuCount", stuCount);
		map.put("clasCount", clasCount);
		map.put("zsjsMoney", MoneyUtil.fromFENtoYUAN(zsjsMoney));
		return map;
	}
	
	@Override
	public Map<String, Object> termCampusStatisticalComparison(Integer orgId, Integer termId1, String camId1,
			Integer termId2, String camId2, Integer clasType) throws Exception {//1 老生班 2 新生班
		
		//22.本期老生数：老生班 clasType = 1（状态为“缴费已完成”学员的人数）
		Map<String, Object> curStuCount = classDao.getStudentCountAndClasCountByTermAndCampus(orgId, termId1, camId1, clasType);
		Integer curCount = curStuCount.get("stuCount") == null ? 0 : (Integer) curStuCount.get("stuCount");
		//23.上期学员数：新生班+老生班 clasType = null 对比学期的状态为“缴费已完成”学员的人数
		Map<String, Object> frontStuCount = classDao.getStudentCountAndClasCountByTermAndCampus(orgId, termId2, camId2, null);
		Integer frontCount = frontStuCount.get("stuCount") == null ? 0 : (Integer) frontStuCount.get("stuCount");
		//24.缴费总人数：本学期的缴费总人数
		Map<String, Object> curPayCountMap = classDao.getStudentCountAndClasCountByTermAndCampus(orgId, termId1, camId1, null);
		Integer curPayCount = curPayCountMap.get("stuCount") == null ? 0 : (Integer) curPayCountMap.get("stuCount");
		//25.退费总人数：上学期的退费总人数
		Integer zstCount = frontStuCount.get("zstCount") == null ? 0 : (Integer) frontStuCount.get("zstCount");
		//21.总注册率：（注册率=所有教师本期缴费人数（仅老生班）/参考学期新老生总人数（新生班人数➕老生班人数）*100%）
		double p = 0;
		if (frontCount == 0) {

		} else {
			p = (double)curCount/frontCount*100;
		}
		java.math.BigDecimal big = new java.math.BigDecimal(p);  
		String  payPercent = big.setScale(0,java.math.BigDecimal.ROUND_HALF_UP).doubleValue() +"%";
		//26.流失率：退费人数（包含全费和半费）除交费总人数（有缴费记录的总人数）×100%
		double p1 = 0;
		if (curPayCount == 0) {

		} else {
			p1 = (double)zstCount/curPayCount*100;
		}
		java.math.BigDecimal big1 = new java.math.BigDecimal(p1); 
		String  leavePercent = big1.setScale(0,java.math.BigDecimal.ROUND_HALF_UP).doubleValue() +"%";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("curCount", curCount);//本期老生数：老生班 clasType = 1（状态为“缴费已完成”学员的人数）
		map.put("frontCount", frontCount);//上期学员数：新生班+老生班 clasType = null 对比学期的状态为“缴费已完成”学员的人数
		map.put("curPayCount", curPayCount);//缴费总人数：本学期的缴费总人数
		map.put("fronZstCount", zstCount);//退费总人数：上学期的退费总人数
		map.put("payPercent", payPercent);//总注册率：（注册率=所有教师本期缴费人数（仅老生班）/参考学期新老生总人数（新生班人数➕老生班人数）*100%）
		map.put("leavePercent", leavePercent);//流失率：退费人数（包含全费和半费）除交费总人数（有缴费记录的总人数）×100%
		return map;
	}
	@Override
	public Integer getCampusIdByClasId(Integer orgId, Integer clasId) {
		// TODO Auto-generated method stub
		return classDao.getCampusIdByClasId(orgId, clasId);
	}
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional (propagation = Propagation.NOT_SUPPORTED,rollbackFor=Exception.class)
	public Paging<Map<String, Object>> getStudentListByClasId(Integer orgId, Integer clasId, String status,
			Paging<Map> page) throws Exception {
		return classDao.getStudentListByClasId(orgId, clasId, status, page);
	}
	@Override
	public List<ClassToTeacher> getListByClasId(Integer orgId, String ids) {
		List<ClassToTeacher> list = classDao.getListByClasId(orgId, ids);	
		return list;
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public Map<String, Object> getMyCoucher(Integer orgId, Integer studId, String userIdnumber, String techName, Integer clasId) throws Exception {
		Map<String, Object> myCoucher = new HashMap<String, Object>();
		//获取学员凭证信息
		List<Map<String, Object>> myCoucherList = classDao.getMyCoucher(orgId, studId, userIdnumber, techName, clasId);
		if (myCoucherList != null && myCoucherList.size() >0) {
			myCoucher = myCoucherList.get(0);
		} else {
			//tishi  
		}
		List<Vouchermodel>  vouList = null;
		Vouchermodel vou = null;
		vouList = voucherModelService.getVouModel(null, orgId);
		if (vouList != null && vouList.size() >0) {
			vou = vouList.get(0);
		}
		Integer coucherId = vou.getVoucher_model_id();
		String voucherContent = vou.getVoucher_content();
		JSONObject json = new JSONObject();
		JSONObject jsonContent = (JSONObject) json.parse(voucherContent);
		System.out.println(jsonContent);
		String content1 = null;  String left1 = null;  String top1 = null;
		String content2 = null;  String left2 = null;  String top2 = null;
		String content3 = null;  String left3 = null;  String top3 = null;
		String content4 = null;  String left4 = null;  String top4 = null;
		String content5 = null;  String left5 = null;  String top5 = null;
		String content6 = null;  String left6 = null;  String top6 = null;
		String content7 = null;  String left7 = null;  String top7 = null;
		String content8 = null;  String left8 = null;  String top8 = null;
		String content9 = null;  String left9 = null;  String top9 = null;
		String content10 = null;  String left10 = null;  String top10 = null;
		String content11 = null;  String left11 = null;  String top11 = null;
		String content12 = null;  String left12 = null;  String top12 = null;
		String content13 = null;  String left13 = null;  String top13 = null;
		String content14 = null;  String left14 = null;  String top14 = null;
		String content15 = null;  String left15 = null;  String top15 = null;
		String content16 = null;  String left16 = null;  String top16 = null;
		String content17 = null;  String left17 = null;  String top17 = null;
		String content18 = null;  String left18 = null;  String top18 = null;
		String content19 = null;  String left19 = null;  String top19 = null;
		String content20 = null;  String left20 = null;  String top20 = null;
		String content21 = null;  String left21 = null;  String top21 = null;
		String content22 = null;  String left22 = null;  String top22 = null;
		String content23 = null;  String left23 = null;  String top23 = null;
		String content24 = null;  String left24 = null;  String top24 = null;
		String content25 = null;  String left25 = null;  String top25 = null;
		String content26 = null;  String left26 = null;  String top26 = null;
		String content27 = null;  String left27 = null;  String top27 = null;
		String content28 = null;  String left28 = null;  String top28 = null;
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		JSONObject json4 = new JSONObject();
		JSONObject json5 = new JSONObject();
		JSONObject json6 = new JSONObject();
		JSONObject json7 = new JSONObject();
		JSONObject json8 = new JSONObject();
		JSONObject json9 = new JSONObject();
		JSONObject json10 = new JSONObject();
		JSONObject json11 = new JSONObject();
		JSONObject json12 = new JSONObject();
		JSONObject json13 = new JSONObject();
		JSONObject json14 = new JSONObject();
		JSONObject json15 = new JSONObject();
		JSONObject json16 = new JSONObject();
		JSONObject json17 = new JSONObject();
		JSONObject json18 = new JSONObject();
		JSONObject json19 = new JSONObject();
		JSONObject json20 = new JSONObject();
		JSONObject json21 = new JSONObject();
		JSONObject json22 = new JSONObject();
		JSONObject json23 = new JSONObject();
		JSONObject json24 = new JSONObject();
		JSONObject json25 = new JSONObject();
		JSONObject json26 = new JSONObject();
		JSONObject json27 = new JSONObject();
		JSONObject json28 = new JSONObject();
		JSONObject returnJson = new JSONObject();
		if (jsonContent.get("type_1") != null) {//主标题
			JSONObject type1Json = (JSONObject) jsonContent.get("type_1");
			System.out.println("type1Json :" + type1Json);
			content1 = String.valueOf(type1Json.get("content"));
			left1 = String.valueOf(type1Json.get("left"));
			top1 = String.valueOf(type1Json.get("top"));
			System.out.println("content1 :" + content1+ "left :" + left1 + "top1 :" + top1);
			json1.put("content", content1);
			json1.put("left", left1);
			json1.put("top", top1);
			returnJson.put("type_1", json1);
		}
		if (jsonContent.get("type_2") != null) {//副标题
			JSONObject type2Json = (JSONObject) jsonContent.get("type_2");
			System.out.println("type2Json :" + type2Json);
			content2 = String.valueOf(type2Json.get("content"));
			left2 = String.valueOf(type2Json.get("left"));
			top2 = String.valueOf(type2Json.get("top"));
			System.out.println("content2 :" + content2+ "left2 :" + left2 + "top2 :" + top2);
			json2.put("content", content2);
			json2.put("left", left2);
			json2.put("top", top2);
			returnJson.put("type_2", json2);
		}
		if (jsonContent.get("type_3") != null) {//学期
			JSONObject type3Json = (JSONObject) jsonContent.get("type_3");
			System.out.println("type3Json :" + type3Json);
			content3 = String.valueOf(type3Json.get("content"));
			left3 = String.valueOf(type3Json.get("left"));
			top3 = String.valueOf(type3Json.get("top"));
			System.out.println("content3 :" + content3+ "left3 :" + left3 + "top3 :" + top3);
			String termName = (String) myCoucher.get("term_name");
			json3.put("content", termName);
			json3.put("left", left3);
			json3.put("top", top3);
			returnJson.put("type_3", json3);
		}
		if (jsonContent.get("type_4") != null) {//学员姓名  
			JSONObject type4Json = (JSONObject) jsonContent.get("type_4");
			System.out.println("type4Json :" + type4Json);
			//content4 = String.valueOf(type4Json.get("content"));
			left4 = String.valueOf(type4Json.get("left"));
			top4 = String.valueOf(type4Json.get("top"));
			String studName = (String) myCoucher.get("stud_name");
			json4.put("content", studName);
			json4.put("left", left4);
			json4.put("top", top4);
			returnJson.put("type_4", json4);
		}
		if (jsonContent.get("type_5") != null) {//证件号
			JSONObject type5Json = (JSONObject) jsonContent.get("type_5");
			System.out.println("type5Json :" + type5Json);
			//content5 = String.valueOf(type5Json.get("content"));
			left5 = String.valueOf(type5Json.get("left"));
			top5 = String.valueOf(type5Json.get("top"));
			json5.put("content", userIdnumber);
			json5.put("left", left5);
			json5.put("top", top5);
			returnJson.put("type_5", json5);
		}
		if (jsonContent.get("type_6") != null) {//教师姓名
			JSONObject type6Json = (JSONObject) jsonContent.get("type_6");
			System.out.println("type6Json :" + type6Json);
			//content6 = String.valueOf(type6Json.get("content"));
			left6 = String.valueOf(type6Json.get("left"));
			top6 = String.valueOf(type6Json.get("top"));
			json6.put("content", techName);
			json6.put("left", left6);
			json6.put("top", top6);
			returnJson.put("type_6", json6);
		}
		if (jsonContent.get("type_7") != null) {//科目班级
		    JSONObject type7Json = (JSONObject) jsonContent.get("type_7");
		    System.out.println("type7Json :" + type7Json);
		    //content7 = String.valueOf(type7Json.get("content"));
		    left7 = String.valueOf(type7Json.get("left"));
		    top7 = String.valueOf(type7Json.get("top"));
		    String clasName = (String) myCoucher.get("clas_name");
		    json7.put("content", clasName);
		    json7.put("left", left7);
		    json7.put("top", top7);
		    returnJson.put("type_7", json7);
		}
		if (jsonContent.get("type_8") != null) {//班级类型
		    JSONObject type8Json = (JSONObject) jsonContent.get("type_8");
		    System.out.println("type8Json :" + type8Json);
		    //content8 = String.valueOf(type8Json.get("content"));
		    left8 = String.valueOf(type8Json.get("left"));
		    top8 = String.valueOf(type8Json.get("top"));
		    String clasType = (String) myCoucher.get("clas_type");
		    json8.put("content", clasType);
		    json8.put("left", left8);
		    json8.put("top", top8);
		    returnJson.put("type_8", json8);
		}
		if (jsonContent.get("type_9") != null) {//课时数
		    JSONObject type9Json = (JSONObject) jsonContent.get("type_9");
		    System.out.println("type9Json :" + type9Json);
		    //content9 = String.valueOf(type9Json.get("content"));
		    left9 = String.valueOf(type9Json.get("left"));
		    top9 = String.valueOf(type9Json.get("top"));
		    Integer totalHours = (Integer) myCoucher.get("total_hours");
		    json9.put("content", totalHours);
		    json9.put("left", left9);
		    json9.put("top", top9);
		    returnJson.put("type_9", json9);
		}
		if (jsonContent.get("type_10") != null) {//开课日期
		    JSONObject type10Json = (JSONObject) jsonContent.get("type_10");
		    System.out.println("type10Json :" + type10Json);
		    //content10 = String.valueOf(type10Json.get("content"));
		    left10 = String.valueOf(type10Json.get("left"));
		    top10 = String.valueOf(type10Json.get("top"));
		    String classStartDate = (String) myCoucher.get("class_start_date");
		    json10.put("content", classStartDate);
		    json10.put("left", left10);
		    json10.put("top", top10);
		    returnJson.put("type_10", json10);
		}
		if (jsonContent.get("type_11") != null) {//上课时间
		    JSONObject type11Json = (JSONObject) jsonContent.get("type_11");
		    System.out.println("type11Json :" + type11Json);
		    //content11 = String.valueOf(type11Json.get("content"));
		    left11 = String.valueOf(type11Json.get("left"));
		    top11 = String.valueOf(type11Json.get("top"));
		    String classBeginTime = (String) myCoucher.get("class_begin_time");
		    json11.put("content", classBeginTime);
		    json11.put("left", left11);
		    json11.put("top", top11);
		    returnJson.put("type_11", json11);
		}
		if (jsonContent.get("type_12") != null) {//学费标准
		    JSONObject type12Json = (JSONObject) jsonContent.get("type_12");
		    System.out.println("type12Json :" + type12Json);
		    //content12 = String.valueOf(type12Json.get("content"));
		    left12 = String.valueOf(type12Json.get("left"));
		    top12 = String.valueOf(type12Json.get("top"));
		    String tuition = (String) myCoucher.get("tuition_fees");
		    json12.put("content", tuition);
		    json12.put("left", left12);
		    json12.put("top", top12);
		    returnJson.put("type_12", json12);
		}
		if (jsonContent.get("type_13") != null) {//校区
		    JSONObject type13Json = (JSONObject) jsonContent.get("type_13");
		    System.out.println("type13Json :" + type13Json);
		    //content13 = String.valueOf(type13Json.get("content"));
		    left13 = String.valueOf(type13Json.get("left"));
		    top13 = String.valueOf(type13Json.get("top"));
		    String camName = (String) myCoucher.get("cam_name");
		    json13.put("content", camName);
		    json13.put("left", left13);
		    json13.put("top", top13);
		    returnJson.put("type_13", json13);
		}
		if (jsonContent.get("type_14") != null) {//教室
		    JSONObject type14Json = (JSONObject) jsonContent.get("type_14");
		    System.out.println("type14Json :" + type14Json);
		    //content14 = String.valueOf(type14Json.get("content"));
		    left14 = String.valueOf(type14Json.get("left"));
		    top14 = String.valueOf(type14Json.get("top"));
		    String classroomName = (String) myCoucher.get("classroom_name");
		    json14.put("content", classroomName);
		    json14.put("left", left14);
		    json14.put("top", top14);
		    returnJson.put("type_14", json14);
		}
		if (jsonContent.get("type_15") != null) {//班容量
		    JSONObject type15Json = (JSONObject) jsonContent.get("type_15");
		    System.out.println("type15Json :" + type15Json);
		    //content15 = String.valueOf(type15Json.get("content"));
		    left15 = String.valueOf(type15Json.get("left"));
		    top15 = String.valueOf(type15Json.get("top"));
		    Integer size = (Integer) myCoucher.get("size");
		    json15.put("content", size);
		    json15.put("left", left15);
		    json15.put("top", top15);
		    returnJson.put("type_15", json15);
		}
		if (jsonContent.get("type_16") != null) {//缴费方式
		    JSONObject type16Json = (JSONObject) jsonContent.get("type_16");
		    System.out.println("type16Json :" + type16Json);
		    //content16 = String.valueOf(type16Json.get("content"));
		    left16 = String.valueOf(type16Json.get("left"));
		    top16 = String.valueOf(type16Json.get("top"));
		    String payMethod = (String) myCoucher.get("pay_method");
		    json16.put("content", payMethod);
		    json16.put("left", left16);
		    json16.put("top", top16);
		    returnJson.put("type_16", json16);
		}
		if (jsonContent.get("type_17") != null) {//缴费金额
		    JSONObject type17Json = (JSONObject) jsonContent.get("type_17");
		    System.out.println("type17Json :" + type17Json);
		    //content17 = String.valueOf(type17Json.get("content"));
		    left17 = String.valueOf(type17Json.get("left"));
		    top17 = String.valueOf(type17Json.get("top"));
		    String txnAmt = (String) myCoucher.get("txnAmt");
		    json17.put("content", MoneyUtil.fromFENtoYUAN(txnAmt));
		    json17.put("left", left17);
		    json17.put("top", top17);
		    returnJson.put("type_17", json17);
		}
		if (jsonContent.get("type_18") != null) {//缴费金额大写
		    JSONObject type18Json = (JSONObject) jsonContent.get("type_18");
		    System.out.println("type18Json :" + type18Json);
		    //content18 = String.valueOf(type18Json.get("content"));
		    left18 = String.valueOf(type18Json.get("left"));
		    top18 = String.valueOf(type18Json.get("top"));
		    String txnAmt = (String) myCoucher.get("txnAmt");
		    //将金额转为大写金额
		    json18.put("content", MoneyUtil.fromFENtoYUAN(txnAmt));
		    json18.put("left", left18);
		    json18.put("top", top18);
		    returnJson.put("type_18", json18);
		}
		if (jsonContent.get("type_19") != null) {//缴费时间
		    JSONObject type19Json = (JSONObject) jsonContent.get("type_19");
		    System.out.println("type19Json :" + type19Json);
		    //content19 = String.valueOf(type19Json.get("content"));
		    left19 = String.valueOf(type19Json.get("left"));
		    top19 = String.valueOf(type19Json.get("top"));
		    String txnTime = (String) myCoucher.get("txnTime");
		    json19.put("content", txnTime);
		    json19.put("left", left19);
		    json19.put("top", top19);
		    returnJson.put("type_19", json19);
		}
		if (jsonContent.get("type_20") != null) {//凭证编号
		    JSONObject type20Json = (JSONObject) jsonContent.get("type_20");
		    System.out.println("type20Json :" + type20Json);
		    //content20 = String.valueOf(type20Json.get("content"));
		    left20 = String.valueOf(type20Json.get("left"));
		    top20 = String.valueOf(type20Json.get("top"));
		    String voucherNo = (String) myCoucher.get("voucher_no"); 
		    json20.put("content", voucherNo);
		    json20.put("left", left20);
		    json20.put("top", top20);
		    returnJson.put("type_20", json20);
		}
		if (jsonContent.get("type_21") != null) {//凭证日期
		    JSONObject type21Json = (JSONObject) jsonContent.get("type_21");
		    //content21 = String.valueOf(type21Json.get("content"));
		    left21 = String.valueOf(type21Json.get("left"));
		    top21 = String.valueOf(type21Json.get("top"));
		    String voucherTime = CommonUtils.dateFormat(new Date(), null);
		    json21.put("content", voucherTime);
		    json21.put("left", left21);
		    json21.put("top", top21);
		    returnJson.put("type_21", json21);
		}
		if (jsonContent.get("type_22") != null) {//操作人
		    JSONObject type22Json = (JSONObject) jsonContent.get("type_22");
		    System.out.println("type22Json :" + type22Json);
		    content22 = String.valueOf(type22Json.get("content"));
		    left22 = String.valueOf(type22Json.get("left"));
		    top22 = String.valueOf(type22Json.get("top"));
		    json22.put("content", content22);
		    json22.put("left", left22);
		    json22.put("top", top22);
		    returnJson.put("type_22", json22);
		}
		if (jsonContent.get("type_23") != null) {//自定义1
		    JSONObject type23Json = (JSONObject) jsonContent.get("type_23");
		    System.out.println("type23Json :" + type23Json);
		    content23 = String.valueOf(type23Json.get("content"));
		    left23 = String.valueOf(type23Json.get("left"));
		    top23 = String.valueOf(type23Json.get("top"));
		    json23.put("content", content23);
		    json23.put("left", left23);
		    json23.put("top", top23);
		    returnJson.put("type_23", json23);
		}
		if (jsonContent.get("type_24") != null) {//自定义2
		    JSONObject type24Json = (JSONObject) jsonContent.get("type_24");
		    System.out.println("type24Json :" + type24Json);
		    content24 = String.valueOf(type24Json.get("content"));
		    left24 = String.valueOf(type24Json.get("left"));
		    top24 = String.valueOf(type24Json.get("top"));
		    json24.put("content", content24);
		    json24.put("left", left24);
		    json24.put("top", top24);
		    returnJson.put("type_24", json24);
		}
		if (jsonContent.get("type_25") != null) {//自定义3
		    JSONObject type25Json = (JSONObject) jsonContent.get("type_25");
		    System.out.println("type25Json :" + type25Json);
		    content25 = String.valueOf(type25Json.get("content"));
		    left25 = String.valueOf(type25Json.get("left"));
		    top25 = String.valueOf(type25Json.get("top"));
		    json25.put("content", content25);
		    json25.put("left", left25);
		    json25.put("top", top25);
		    returnJson.put("type_25", json25);
		}
		if (jsonContent.get("type_26") != null) {//自定义4
		    JSONObject type26Json = (JSONObject) jsonContent.get("type_26");
		    System.out.println("type26Json :" + type26Json);
		    content26 = String.valueOf(type26Json.get("content"));
		    left26 = String.valueOf(type26Json.get("left"));
		    top26 = String.valueOf(type26Json.get("top"));
		    json26.put("content", content26);
		    json26.put("left", left26);
		    json26.put("top", top26);
		    returnJson.put("type_26", json26);
		}
		if (jsonContent.get("type_27") != null) {//自定义5
		    JSONObject type27Json = (JSONObject) jsonContent.get("type_27");
		    System.out.println("type27Json :" + type27Json);
		    content27 = String.valueOf(type27Json.get("content"));
		    left27 = String.valueOf(type27Json.get("left"));
		    top27 = String.valueOf(type27Json.get("top"));
		    json27.put("content", content27);
		    json27.put("left", left27);
		    json27.put("top", top27);
		    returnJson.put("type_27", json27);
		}
		if (jsonContent.get("type_28") != null) {//自定义6
		    JSONObject type28Json = (JSONObject) jsonContent.get("type_28");
		    System.out.println("type28Json :" + type28Json);
		    content28 = String.valueOf(type28Json.get("content"));
		    left28 = String.valueOf(type28Json.get("left"));
		    top28 = String.valueOf(type28Json.get("top"));
		    json28.put("content", content28);
		    json28.put("left", left28);
		    json28.put("top", top28);
		    returnJson.put("type_28", json28);
		}
		myCoucher.put("voucher_content", returnJson);
		System.out.println("--+####--:" +returnJson.toString());
		return myCoucher;
	}
	@Override
	public List<Map<String, Object>> getUserRegisterList(Integer orgId, String userIdnumber) {
		return classDao.getUserRegisterList(orgId, userIdnumber);
	}
	/***
	 * 批量 我的凭证
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	@Override
	public List<Map<String, Object>> getMyCoucherList(Integer orgId, String studClasIds) throws Exception {
		//获取凭证模板
		List<Vouchermodel>  vouList = voucherModelService.getVouModel(null, orgId);
		Vouchermodel vou = null;
		if (vouList != null && vouList.size() >0) {
			vou = vouList.get(0);
		} else {
			log.info("---------------------------------打印学员凭证    无凭证模板    请先设置凭证模板-----------------------------------------");
			return null;
		}
		//获取凭证模板内容
		String voucherContent = vou.getVoucher_content();
		Integer bgLength = vou.getBackground_length();
		Integer bgWidth = vou.getBackground_width();
		JSONObject json = new JSONObject();
		JSONObject jsonContent = (JSONObject) json.parse(voucherContent);
		System.out.println(jsonContent);
		String left1 = null;  String top1 = null;
		String left2 = null;  String top2 = null;
		String left3 = null;  String top3 = null;
		String left4 = null;  String top4 = null;
		String left5 = null;  String top5 = null;
		String left6 = null;  String top6 = null;
		String left7 = null;  String top7 = null;
		String left8 = null;  String top8 = null;
		String left9 = null;  String top9 = null;
		String left10 = null;  String top10 = null;
		String left11 = null;  String top11 = null;
		String left12 = null;  String top12 = null;
		String left13 = null;  String top13 = null;
		String left14 = null;  String top14 = null;
		String left15 = null;  String top15 = null;
		String left16 = null;  String top16 = null;
		String left17 = null;  String top17 = null;
		String left18 = null;  String top18 = null;
		String left19 = null;  String top19 = null;
		String left20 = null;  String top20 = null;
		String left21 = null;  String top21 = null;
		String left22 = null;  String top22 = null;
		String left23 = null;  String top23 = null;
		String left24 = null;  String top24 = null;
		String left25 = null;  String top25 = null;
		String left26 = null;  String top26 = null;
		String left27 = null;  String top27 = null;
		String left28 = null;  String top28 = null;
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		JSONObject json4 = new JSONObject();
		JSONObject json5 = new JSONObject();
		JSONObject json6 = new JSONObject();
		JSONObject json7 = new JSONObject();
		JSONObject json8 = new JSONObject();
		JSONObject json9 = new JSONObject();
		JSONObject json10 = new JSONObject();
		JSONObject json11 = new JSONObject();
		JSONObject json12 = new JSONObject();
		JSONObject json13 = new JSONObject();
		JSONObject json14 = new JSONObject();
		JSONObject json15 = new JSONObject();
		JSONObject json16 = new JSONObject();
		JSONObject json17 = new JSONObject();
		JSONObject json18 = new JSONObject();
		JSONObject json19 = new JSONObject();
		JSONObject json20 = new JSONObject();
		JSONObject json21 = new JSONObject();
		JSONObject json22 = new JSONObject();
		JSONObject json23 = new JSONObject();
		JSONObject json24 = new JSONObject();
		JSONObject json25 = new JSONObject();
		JSONObject json26 = new JSONObject();
		JSONObject json27 = new JSONObject();
		JSONObject json28 = new JSONObject();
		JSONObject returnJson = new JSONObject();
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		//根据学员班级id 获取班级id和学员id
		List<Map<String, Object>> studClaslist = classDao.getClassIdAndStudId(studClasIds);
		if (studClaslist != null && studClaslist.size() > 0) {
			//获取班级和学员ID
			for (Map<String, Object> sc : studClaslist) {
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.put("bgLength", bgLength);
				returnMap.put("bgWidth", bgWidth);
				Integer clasId = (Integer) sc.get("clas_id");
				Integer studId = (Integer) sc.get("stud_id");
				//String key = String.valueOf(clasId) + "_" + String.valueOf(studId);
				List<Map<String, Object>> myCoucherList = classDao.getBatchMyCoucher(orgId, studId, clasId);
				if (myCoucherList != null && myCoucherList.size() >0) {
					Map<String, Object> myCoucher = myCoucherList.get(0);
					if (jsonContent.get("type_1") != null) {//主标题
						JSONObject type1Json = (JSONObject) jsonContent.get("type_1");
						System.out.println("type1Json :" + type1Json);
						String content1 = String.valueOf(type1Json.get("content"));
						left1 = String.valueOf(type1Json.get("left"));
						top1 = String.valueOf(type1Json.get("top"));
						System.out.println("content1 :" + content1+ "left :" + left1 + "top1 :" + top1);
						json1.put("content", content1);
						json1.put("left", left1);
						json1.put("top", top1);
						returnJson.put("type_1", json1);
					}
					if (jsonContent.get("type_2") != null) {//副标题
						JSONObject type2Json = (JSONObject) jsonContent.get("type_2");
						System.out.println("type2Json :" + type2Json);
						String content2 = String.valueOf(type2Json.get("content"));
						left2 = String.valueOf(type2Json.get("left"));
						top2 = String.valueOf(type2Json.get("top"));
						System.out.println("content2 :" + content2+ "left2 :" + left2 + "top2 :" + top2);
						json2.put("content", content2);
						json2.put("left", left2);
						json2.put("top", top2);
						returnJson.put("type_2", json2);
					}
					if (jsonContent.get("type_3") != null) {//学期
						JSONObject type3Json = (JSONObject) jsonContent.get("type_3");
						System.out.println("type3Json :" + type3Json);
						String content3 = String.valueOf(type3Json.get("content"));
						left3 = String.valueOf(type3Json.get("left"));
						top3 = String.valueOf(type3Json.get("top"));
						System.out.println("content3 :" + content3+ "left3 :" + left3 + "top3 :" + top3);
						String termName = (String) myCoucher.get("term_name");
						json3.put("content", termName);
						json3.put("left", left3);
						json3.put("top", top3);
						returnJson.put("type_3", json3);
					}
					if (jsonContent.get("type_4") != null) {//学员姓名  
						JSONObject type4Json = (JSONObject) jsonContent.get("type_4");
						System.out.println("type4Json :" + type4Json);
						//content4 = String.valueOf(type4Json.get("content"));
						left4 = String.valueOf(type4Json.get("left"));
						top4 = String.valueOf(type4Json.get("top"));
						String studName = (String) myCoucher.get("stud_name");
						json4.put("content", studName);
						json4.put("left", left4);
						json4.put("top", top4);
						returnJson.put("type_4", json4);
					}
					if (jsonContent.get("type_5") != null) {//证件号   
						JSONObject type5Json = (JSONObject) jsonContent.get("type_5");
						System.out.println("type5Json :" + type5Json);
						//content5 = String.valueOf(type5Json.get("content"));
						left5 = String.valueOf(type5Json.get("left"));
						top5 = String.valueOf(type5Json.get("top"));
						String userIdnumber = (String) myCoucher.get("user_idnumber");
						json5.put("content", userIdnumber);
						json5.put("left", left5);
						json5.put("top", top5);
						returnJson.put("type_5", json5);
					}
					if (jsonContent.get("type_6") != null) {//教师姓名
						JSONObject type6Json = (JSONObject) jsonContent.get("type_6");
						System.out.println("type6Json :" + type6Json);
						//content6 = String.valueOf(type6Json.get("content"));
						left6 = String.valueOf(type6Json.get("left"));
						top6 = String.valueOf(type6Json.get("top"));
						String techName = (String) myCoucher.get("teacher_name");
						json6.put("content", techName);
						json6.put("left", left6);
						json6.put("top", top6);
						returnJson.put("type_6", json6);
					}
					if (jsonContent.get("type_7") != null) {//科目班级
					    JSONObject type7Json = (JSONObject) jsonContent.get("type_7");
					    System.out.println("type7Json :" + type7Json);
					    //content7 = String.valueOf(type7Json.get("content"));
					    left7 = String.valueOf(type7Json.get("left"));
					    top7 = String.valueOf(type7Json.get("top"));
					    String clasName = (String) myCoucher.get("clas_name");
					    json7.put("content", clasName);
					    json7.put("left", left7);
					    json7.put("top", top7);
					    returnJson.put("type_7", json7);
					}
					if (jsonContent.get("type_8") != null) {//班级类型
					    JSONObject type8Json = (JSONObject) jsonContent.get("type_8");
					    System.out.println("type8Json :" + type8Json);
					    //content8 = String.valueOf(type8Json.get("content"));
					    left8 = String.valueOf(type8Json.get("left"));
					    top8 = String.valueOf(type8Json.get("top"));
					    String clasType = (String) myCoucher.get("clas_type");
					    json8.put("content", clasType);
					    json8.put("left", left8);
					    json8.put("top", top8);
					    returnJson.put("type_8", json8);
					}
					if (jsonContent.get("type_9") != null) {//课时数
					    JSONObject type9Json = (JSONObject) jsonContent.get("type_9");
					    System.out.println("type9Json :" + type9Json);
					    //content9 = String.valueOf(type9Json.get("content"));
					    left9 = String.valueOf(type9Json.get("left"));
					    top9 = String.valueOf(type9Json.get("top"));
					    Integer totalHours = (Integer) myCoucher.get("total_hours");
					    json9.put("content", totalHours);
					    json9.put("left", left9);
					    json9.put("top", top9);
					    returnJson.put("type_9", json9);
					}
					if (jsonContent.get("type_10") != null) {//开课日期
					    JSONObject type10Json = (JSONObject) jsonContent.get("type_10");
					    System.out.println("type10Json :" + type10Json);
					    //content10 = String.valueOf(type10Json.get("content"));
					    left10 = String.valueOf(type10Json.get("left"));
					    top10 = String.valueOf(type10Json.get("top"));
					    String classStartDate = (String) myCoucher.get("class_start_date");
					    json10.put("content", classStartDate);
					    json10.put("left", left10);
					    json10.put("top", top10);
					    returnJson.put("type_10", json10);
					}
					if (jsonContent.get("type_11") != null) {//上课时间
					    JSONObject type11Json = (JSONObject) jsonContent.get("type_11");
					    System.out.println("type11Json :" + type11Json);
					    //content11 = String.valueOf(type11Json.get("content"));
					    left11 = String.valueOf(type11Json.get("left"));
					    top11 = String.valueOf(type11Json.get("top"));
					    String classBeginTime = (String) myCoucher.get("class_begin_time");
					    json11.put("content", classBeginTime);
					    json11.put("left", left11);
					    json11.put("top", top11);
					    returnJson.put("type_11", json11);
					}
					if (jsonContent.get("type_12") != null) {//学费标准
					    JSONObject type12Json = (JSONObject) jsonContent.get("type_12");
					    System.out.println("type12Json :" + type12Json);
					    //content12 = String.valueOf(type12Json.get("content"));
					    left12 = String.valueOf(type12Json.get("left"));
					    top12 = String.valueOf(type12Json.get("top"));
					    String tuition = (String) myCoucher.get("tuition_fees");
					    json12.put("content", tuition);
					    json12.put("left", left12);
					    json12.put("top", top12);
					    returnJson.put("type_12", json12);
					}
					if (jsonContent.get("type_13") != null) {//校区
					    JSONObject type13Json = (JSONObject) jsonContent.get("type_13");
					    System.out.println("type13Json :" + type13Json);
					    //content13 = String.valueOf(type13Json.get("content"));
					    left13 = String.valueOf(type13Json.get("left"));
					    top13 = String.valueOf(type13Json.get("top"));
					    String camName = (String) myCoucher.get("cam_name");
					    json13.put("content", camName);
					    json13.put("left", left13);
					    json13.put("top", top13);
					    returnJson.put("type_13", json13);
					}
					if (jsonContent.get("type_14") != null) {//教室
					    JSONObject type14Json = (JSONObject) jsonContent.get("type_14");
					    System.out.println("type14Json :" + type14Json);
					    //content14 = String.valueOf(type14Json.get("content"));
					    left14 = String.valueOf(type14Json.get("left"));
					    top14 = String.valueOf(type14Json.get("top"));
					    String classroomName = (String) myCoucher.get("classroom_name");
					    json14.put("content", classroomName);
					    json14.put("left", left14);
					    json14.put("top", top14);
					    returnJson.put("type_14", json14);
					}
					if (jsonContent.get("type_15") != null) {//班容量
					    JSONObject type15Json = (JSONObject) jsonContent.get("type_15");
					    System.out.println("type15Json :" + type15Json);
					    //content15 = String.valueOf(type15Json.get("content"));
					    left15 = String.valueOf(type15Json.get("left"));
					    top15 = String.valueOf(type15Json.get("top"));
					    Integer size = (Integer) myCoucher.get("size");
					    json15.put("content", size);
					    json15.put("left", left15);
					    json15.put("top", top15);
					    returnJson.put("type_15", json15);
					}
					if (jsonContent.get("type_16") != null) {//缴费方式
					    JSONObject type16Json = (JSONObject) jsonContent.get("type_16");
					    System.out.println("type16Json :" + type16Json);
					    //content16 = String.valueOf(type16Json.get("content"));
					    left16 = String.valueOf(type16Json.get("left"));
					    top16 = String.valueOf(type16Json.get("top"));
					    String payMethod = (String) myCoucher.get("pay_method");
					    json16.put("content", payMethod);
					    json16.put("left", left16);
					    json16.put("top", top16);
					    returnJson.put("type_16", json16);
					}
					if (jsonContent.get("type_17") != null) {//缴费金额
					    JSONObject type17Json = (JSONObject) jsonContent.get("type_17");
					    System.out.println("type17Json :" + type17Json);
					    //content17 = String.valueOf(type17Json.get("content"));
					    left17 = String.valueOf(type17Json.get("left"));
					    top17 = String.valueOf(type17Json.get("top"));
					    String txnAmt = (String) myCoucher.get("txnAmt");
					    json17.put("content", MoneyUtil.fromFENtoYUAN(txnAmt));
					    json17.put("left", left17);
					    json17.put("top", top17);
					    returnJson.put("type_17", json17);
					}
					if (jsonContent.get("type_18") != null) {//缴费金额大写
					    JSONObject type18Json = (JSONObject) jsonContent.get("type_18");
					    System.out.println("type18Json :" + type18Json);
					    //content18 = String.valueOf(type18Json.get("content"));
					    left18 = String.valueOf(type18Json.get("left"));
					    top18 = String.valueOf(type18Json.get("top"));
					    String txnAmt = myCoucher.get("txnAmt") == null ? "0" : (String) myCoucher.get("txnAmt");
					    txnAmt = MoneyUtil.fromFENtoYUAN(txnAmt);
					    //将金额转为大写金额
					    txnAmt = MoneyConvertUtil.digitUppercase(Double.valueOf(txnAmt));
					    json18.put("content", txnAmt);
					    json18.put("left", left18);
					    json18.put("top", top18);
					    returnJson.put("type_18", json18);
					}
					if (jsonContent.get("type_19") != null) {//缴费时间
					    JSONObject type19Json = (JSONObject) jsonContent.get("type_19");
					    System.out.println("type19Json :" + type19Json);
					    //content19 = String.valueOf(type19Json.get("content"));
					    left19 = String.valueOf(type19Json.get("left"));
					    top19 = String.valueOf(type19Json.get("top"));
					    String txnTime = (String) myCoucher.get("txnTime");
					    json19.put("content", txnTime);
					    json19.put("left", left19);
					    json19.put("top", top19);
					    returnJson.put("type_19", json19);
					}
					if (jsonContent.get("type_20") != null) {//凭证编号
					    JSONObject type20Json = (JSONObject) jsonContent.get("type_20");
					    System.out.println("type20Json :" + type20Json);
					    //content20 = String.valueOf(type20Json.get("content"));
					    left20 = String.valueOf(type20Json.get("left"));
					    top20 = String.valueOf(type20Json.get("top"));
					    String voucherNo = (String) myCoucher.get("voucher_no"); 
					    json20.put("content", voucherNo);
					    json20.put("left", left20);
					    json20.put("top", top20);
					    returnJson.put("type_20", json20);
					}
					if (jsonContent.get("type_21") != null) {//凭证日期
					    JSONObject type21Json = (JSONObject) jsonContent.get("type_21");
					    //content21 = String.valueOf(type21Json.get("content"));
					    left21 = String.valueOf(type21Json.get("left"));
					    top21 = String.valueOf(type21Json.get("top"));
					    String voucherTime = CommonUtils.dateFormat(new Date(), null);
					    json21.put("content", voucherTime);
					    json21.put("left", left21);
					    json21.put("top", top21);
					    returnJson.put("type_21", json21);
					}
					if (jsonContent.get("type_22") != null) {//操作人
					    JSONObject type22Json = (JSONObject) jsonContent.get("type_22");
					    System.out.println("type22Json :" + type22Json);
					    String content22 = String.valueOf(type22Json.get("content"));
					    left22 = String.valueOf(type22Json.get("left"));
					    top22 = String.valueOf(type22Json.get("top"));
					    json22.put("content", content22);
					    json22.put("left", left22);
					    json22.put("top", top22);
					    returnJson.put("type_22", json22);
					}
					if (jsonContent.get("type_23") != null) {//自定义1
					    JSONObject type23Json = (JSONObject) jsonContent.get("type_23");
					    System.out.println("type23Json :" + type23Json);
					    String content23 = String.valueOf(type23Json.get("content"));
					    left23 = String.valueOf(type23Json.get("left"));
					    top23 = String.valueOf(type23Json.get("top"));
					    json23.put("content", content23);
					    json23.put("left", left23);
					    json23.put("top", top23);
					    returnJson.put("type_23", json23);
					}
					if (jsonContent.get("type_24") != null) {//自定义2
					    JSONObject type24Json = (JSONObject) jsonContent.get("type_24");
					    System.out.println("type24Json :" + type24Json);
					    String content24 = String.valueOf(type24Json.get("content"));
					    left24 = String.valueOf(type24Json.get("left"));
					    top24 = String.valueOf(type24Json.get("top"));
					    json24.put("content", content24);
					    json24.put("left", left24);
					    json24.put("top", top24);
					    returnJson.put("type_24", json24);
					}
					if (jsonContent.get("type_25") != null) {//自定义3
					    JSONObject type25Json = (JSONObject) jsonContent.get("type_25");
					    System.out.println("type25Json :" + type25Json);
					    String content25 = String.valueOf(type25Json.get("content"));
					    left25 = String.valueOf(type25Json.get("left"));
					    top25 = String.valueOf(type25Json.get("top"));
					    json25.put("content", content25);
					    json25.put("left", left25);
					    json25.put("top", top25);
					    returnJson.put("type_25", json25);
					}
					if (jsonContent.get("type_26") != null) {//自定义4
					    JSONObject type26Json = (JSONObject) jsonContent.get("type_26");
					    System.out.println("type26Json :" + type26Json);
					    String content26 = String.valueOf(type26Json.get("content"));
					    left26 = String.valueOf(type26Json.get("left"));
					    top26 = String.valueOf(type26Json.get("top"));
					    json26.put("content", content26);
					    json26.put("left", left26);
					    json26.put("top", top26);
					    returnJson.put("type_26", json26);
					}
					if (jsonContent.get("type_27") != null) {//自定义5
					    JSONObject type27Json = (JSONObject) jsonContent.get("type_27");
					    System.out.println("type27Json :" + type27Json);
					    String content27 = String.valueOf(type27Json.get("content"));
					    left27 = String.valueOf(type27Json.get("left"));
					    top27 = String.valueOf(type27Json.get("top"));
					    json27.put("content", content27);
					    json27.put("left", left27);
					    json27.put("top", top27);
					    returnJson.put("type_27", json27);
					}
					if (jsonContent.get("type_28") != null) {//自定义6
					    JSONObject type28Json = (JSONObject) jsonContent.get("type_28");
					    System.out.println("type28Json :" + type28Json);
					    String content28 = String.valueOf(type28Json.get("content"));
					    left28 = String.valueOf(type28Json.get("left"));
					    top28 = String.valueOf(type28Json.get("top"));
					    json28.put("content", content28);
					    json28.put("left", left28);
					    json28.put("top", top28);
					    returnJson.put("type_28", json28);
					}
					returnMap.put("content", String.valueOf(returnJson));
					returnList.add(returnMap);
				}
			}
		}
		return returnList;
	}
	@Override
	public Map<String, Object> getStudentInfoByUserIdnumber(Integer orgId, String userIdnumber) {
		return classDao.getStudentInfoByUserIdnumber(orgId, userIdnumber);
	}
	@Override
	public List<Map<String, Object>> getStudentToParent(Integer studId) {
		return classDao.getStudentToParent(studId);
	}
	@Override
	public List<Map<String, Object>> getTermInfo(Integer orgId, Integer termId) {
		return classDao.getTermInfo(orgId, termId);
	}
	@Override
	public List<Map<String, Object>> getPlanSwitch(Integer orgId, Integer clasId) {
		return classDao.getPlanSwitch(orgId, clasId);
	}

}
