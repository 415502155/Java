package sng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

import sng.constant.Consts;
import sng.dao.ApplyDao;
import sng.dao.BaseDaoEx;
import sng.entity.Apply;
import sng.entity.Classes;
import sng.entity.Plan;
import sng.entity.StudentClass;
import sng.exception.EsbException;
import sng.pojo.PlanClassVo;
import sng.pojo.Result;
import sng.pojo.SessionInfo;
import sng.pojo.WXTemplateMessage;
import sng.pojo.base.Parent;
import sng.service.ChargeDetailService;
import sng.service.TokenService;
import sng.util.CommonUtils;
import sng.util.Constant;
import sng.util.ESBPropertyReader;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.Paging;
import sng.util.RabbitmqUtils;
import sng.util.SendMessageUtil;

/**
 * 报名
 * @author s
 *
 */
@Service
@Transactional
public class ApplyServiceImpl {
	private static String ESB_REQUEST_URL = ESBPropertyReader.getProperty("esbRequestUrl");
	
	private Logger logger=Logger.getLogger(ApplyServiceImpl.class);
	
	
	@Resource
	private BaseDaoEx baseDaoEx;
	@Resource
	private ApplyDao applyDao;
	@Resource
	private PlanServiceImpl planServiceImpl;
	@Resource
	private RabbitmqUtils rabbitmqUtils;
	@Resource
	private ChargeDetailService chargeDetailService;
	@Resource
	private TokenService tokenService;
	
	/**
	 * 过期时间 默认4小时
	 */
	public static long EXPIRED_TIMES=4;
	
	/**
	 * 招生计划列表-家长报名
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Plan> getPlanList(SessionInfo sessionInfo) {
		int orgId=sessionInfo.getOrgId();
		int isAuth=sessionInfo.getIsAuth();//是否认证
		Integer userId=sessionInfo.getUserId();//当前登录者
		Map<String,Object> params=new HashMap<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select plan_id as planId, title,begin_time as beginTime,end_time as endTime,content,class_type as classType from plan where is_del=0 and plan_switch=1 and org_id=:orgId ");
		sb.append(" order by end_time desc ");
		params.put("orgId", orgId);
		
		List<Plan> planList=baseDaoEx.queryListBySql(sb.toString(),Plan.class, params);
		List<Plan> list=new ArrayList<>();
		Integer studId=null;
		if(userId!=null && isAuth==1) {//不为空时 则是登录用户，并且已认证时才有学员信息
			Map<String,Object> studMap=this.queryStudent(userId);
			if(studMap!=null) {
				studId=(Integer)studMap.get("stud_id");				
			}

		}
		for(Plan p : planList) {
			int planId=p.getPlanId();
			int classType=p.getClassType();
			p=CommonUtils.generateBean(p, "beginTimeStr","endTimeStr","applyClassCount");
			BeanMap beanMap=BeanMap.create(p);
			beanMap.put("beginTimeStr", CommonUtils.dateFormat(p.getBeginTime(), null));
			beanMap.put("endTimeStr", CommonUtils.dateFormat(p.getEndTime(), null));
			
			if(userId!=null && isAuth==1) {//登录用户，并且是已认证用户
				//如果是老生班，则判断当前学生在不在此班，如果在则显示此班所对应的计划
				if(classType==1) {//查询当前学生在此计划下的班级列表
					List<Map<String,Object>> classList=this.getClass(planId, studId, "");
					if(classList!=null && classList.size()>0) {
						classList=this.getClass(planId, studId, "2,3,5");
						beanMap.put("applyClassCount", classList.size());
						p=(Plan)beanMap.getBean();
						list.add(p);
					}
				}else {
					List<Map<String,Object>> classList=this.getClass(planId, studId, "2,3,5");
					beanMap.put("applyClassCount", String.valueOf(classList.size()));
					p=(Plan)beanMap.getBean();
					list.add(p);
				}
			}else {
				beanMap.put("applyClassCount", 0);
				p=(Plan)beanMap.getBean();
				list.add(p);
			}
		}
		
		return list;
	}
	
	/**
	 * 通过家长userId查询学生信息
	 */
	private Map<String,Object> queryStudent(int userId) {
		Map<String,Object> params=new HashMap<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select b.stud_id,b.parent_id,c.stud_name,c.birthday from edugate_base.parent a,edugate_base.student2parent b,edugate_base.student c ");
		sb.append(" where a.parent_id=b.parent_id and b.stud_id=c.stud_id and b.is_main=1 ");
		sb.append(" and a.is_del=0 and b.is_del=0 and c.is_del=0 and a.user_id=:userId  ");
		params.put("userId", userId);
		List<Map<String,Object>> list=(List)baseDaoEx.queryListBySql(sb.toString(), params);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询当前学生在此计划下的班级列表
	 */
	private List<Map<String,Object>> getClass(int planId,int studId,String stuStatus) {
		Map<String,Object> params=new HashMap<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.clas_id,a.clas_name from classes a,student_class b where a.is_del=0 and b.is_del=0 and a.clas_id=b.clas_id ");
		if(!StringUtils.isBlank(stuStatus)) {
			sb.append(" and b.stu_status in("+stuStatus+") ");
		}
		sb.append(" and a.plan_id=:planId and b.stud_id=:studId");
		params.put("planId", planId);
		params.put("studId", studId);
		List<Map<String,Object>> classList=baseDaoEx.queryListBySql(sb.toString(), params);
		return classList;
	}
	
	/**
	 * 按计划获取班级-用于报名
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Paging getPageClass(SessionInfo sessionInfo,Integer pageNo,Integer limit,Map<String,Object> paramMap) {
		int orgId=sessionInfo.getOrgId();
		int userId=sessionInfo.getUserId();
		int isAuth=sessionInfo.getIsAuth();
		String planId=(String)paramMap.get("planId");
		Integer categoryId=(Integer)paramMap.get("categoryId");
		Integer subjectId=(Integer)paramMap.get("subjectId");
		String classWeek=(String)paramMap.get("classWeek");
		
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.plan_id as planId, a.clas_id as classId,a.clas_name as className,a.clas_type as classType,a.class_start_date as classStartDate,a.class_begin_time as classBeginTime,a.class_over_time as classOverTime, ");
		sb.append(" a.age_range as ageRange,class_unset_time as classUnsetTime, ");
		sb.append(" a.class_week as classWeek,a.size as classSize,a.tuition_fees as tuitionFees,(a.ss_num-a.st_num) as appplyCount, ");
		sb.append(" b.cam_name as camName,c.classroom_name as classroomName,c.building,c.floor, ");
		sb.append(" d.subject_id as subjectId,d.subject_name as subjectName, ");
		sb.append(" e.category_id as categoryId,e.category_name as categoryName ");
		sb.append(" from classes a  ");
		sb.append(" left join campus b on a.cam_id=b.cam_id and a.org_id=b.org_id and b.is_del=0 ");
		sb.append(" left join classroom c on a.classroom_id=c.classroom_id and c.is_del=0 ");
		sb.append(" left join subject d on a.subject_id=d.subject_id and a.org_id=d.org_id and d.is_del=0 ");
		sb.append(" left join category e on a.category_id=e.category_id and a.org_id=e.org_id and e.is_del=0 ");
		sb.append(" where a.is_del=0 and a.org_id=? and a.plan_id=? ");
		params.add(orgId);
		params.add(planId);
		Integer studId=null;
		if(isAuth==1) {//只有认证的学生才可以取到学生信息
			Map<String,Object> studentMap=this.queryStudent(userId);
			Date birthday=(Date)studentMap.get("birthday");
			sb.append(" and a.start_birthday<=? and a.end_birthday>=? ");
			params.add(birthday);
			params.add(birthday);
			studId=(Integer)studentMap.get("stud_id");
		}
		if(categoryId!=null) {
			sb.append(" and a.category_id=? ");
			params.add(categoryId);
		}
		if(subjectId!=null) {
			sb.append(" and a.subject_id=? ");
			params.add(subjectId);
		}
		if(!StringUtils.isBlank(classWeek)) {
			sb.append(" and a.class_week=? ");
			params.add(classWeek);
		}
		String countSql="select count(*) from ("+sb.toString()+") t";
		sb.append(" order by a.clas_name ");
		
		Paging paging=baseDaoEx.queryPageBySql(sb.toString(), countSql, pageNo, limit, PlanClassVo.class, params);
		List<PlanClassVo> planClassVoList=paging.getData();
		List<PlanClassVo> voList=new ArrayList<>();
		for(PlanClassVo vo : planClassVoList) {
			Integer classId=vo.getClassId();
			
			Integer _classType=vo.getClassType();
			String classTypeStr=Consts.CLASS_TYPE_MAP.get(String.valueOf(_classType));
			vo.setClassTypeStr(classTypeStr);
			
			Integer _classWeek=vo.getClassWeek();
			String classWeekStr=Consts.WEEK_MAP.get(String.valueOf(_classWeek));
			vo.setClassWeekStr(classWeekStr);
			
			String classStartDateStr=CommonUtils.dateFormat(vo.getClassStartDate(), "yyyy-MM-dd");
			vo.setClassStartDateStr(classStartDateStr);
			
			String classBeginTimeStr=CommonUtils.dateFormat(vo.getClassBeginTime(),"HH:mm");
			vo.setClassBeginTimeStr(classBeginTimeStr);
			
			String classOverTimeStr=CommonUtils.dateFormat(vo.getClassOverTime(),"HH:mm");
			vo.setClassOverTimeStr(classOverTimeStr);
			
			//查询教师
			params=new ArrayList<>();
			sb.setLength(0);
			sb.append(" select a.tech_name from edugate_base.teacher a,teacher_class b where a.is_del=0 and b.is_del=0 and a.tech_id=b.tech_id ");
			sb.append(" and a.org_id=? and b.clas_id=? ");
			params.add(orgId);
			params.add(classId);
			List<Map<String,Object>> techNameList=baseDaoEx.queryListBySql(sb.toString(), params);
			String teachers="";
			for(Map<String,Object> map : techNameList) {
				String techName=(String)map.get("tech_name");
				if(!StringUtils.isBlank(techName)) {
					teachers+=techName+",";
				}
			}
			if(!StringUtils.isBlank(teachers)) {
				teachers=teachers.substring(0,teachers.length()-1);
			}
			vo.setTeachers(teachers);
			
			//查询学员班级关系表
			if(isAuth==1) {
				String isApply="0";
				params=new ArrayList<>();
				sb.setLength(0);
				sb.append("select stu_status as stuStatus from student_class where is_del=0 and stu_status in(2,3,5,6,7) and clas_id=? and stud_id=? ");
				params.add(classId);
				params.add(studId);
				List<StudentClass> studentClassList=baseDaoEx.queryListBySql(sb.toString(), StudentClass.class, params);
				if(studentClassList!=null && studentClassList.size()>0) {
					isApply="1";//已报名
				}
				vo.setIsApply(isApply);
			}
			
			
			voList.add(vo);
		}
		paging.setData(voList);
		return paging;
	}
	
	/**
	 * 根据计划id获取当前计划下的所有班级所属的科目列表
	 */
	@Deprecated
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Map<String,Object>> getPlanSubject(SessionInfo sessionInfo,Map<String,Object> paramMap) {
		int orgId=sessionInfo.getOrgId();
		int userId=sessionInfo.getUserId();
		int isAuth=sessionInfo.getIsAuth();
		String planId=(String)paramMap.get("planId");
		
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select d.subject_id as subjectId,d.subject_name as subjectName ");
		sb.append(" from classes a  ");
		sb.append(" left join campus b on a.cam_id=b.cam_id and a.org_id=b.org_id and b.is_del=0 ");
		sb.append(" left join classroom c on a.classroom_id=c.classroom_id and c.is_del=0 ");
		sb.append(" left join subject d on a.subject_id=d.subject_id and a.org_id=d.org_id and d.is_del=0 ");
		sb.append(" where a.is_del=0 and a.org_id=? and a.plan_id=? ");
		params.add(orgId);
		params.add(planId);
		if(isAuth==1) {//只有认证的学生才可以取到学生信息
			Map<String,Object> studentMap=this.queryStudent(userId);
			Date birthday=(Date)studentMap.get("birthday");
			sb.append(" and a.start_birthday<=? and a.end_birthday>=? ");
			params.add(birthday);
			params.add(birthday);
		}
		List<Map<String,Object>> subjectList=baseDaoEx.queryListBySql(sb.toString(), params);
		return subjectList;
	}
	

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Plan getPlanInfo(int planId) {
		Plan plan=planServiceImpl.getPlanInfo(planId);
		BeanMap beanMap=BeanMap.create(plan);
		beanMap.put("beginTimeStr", CommonUtils.dateFormat(plan.getBeginTime(),null));
		beanMap.put("endTimeStr", CommonUtils.dateFormat(plan.getEndTime(), null));
		return plan;
	}
	
	/**
	 * 报名
	 */
	public List<Map<String,Object>> doApply(SessionInfo sessionInfo,Map<String,Object> paramMap) throws EsbException {
		int orgId=sessionInfo.getOrgId();
		int userId=sessionInfo.getUserId();
		int isAuth=sessionInfo.getIsAuth();
		if(isAuth==0) {//未认证
			throw new EsbException("未认证");
		}
		StringBuffer sb=new StringBuffer();
		List<Object> params=new ArrayList<>();
		String classIds=(String)paramMap.get("classIds");
		String[] classIdArr=classIds.split(",");
		
		Map<String,Object> studentMap=this.queryStudent(userId);
		if(studentMap==null) {
			throw new EsbException("未认证");
		}
		Integer studId=(Integer)studentMap.get("stud_id");
		Integer parentId=(Integer)studentMap.get("parent_id");
		
		Date now=new Date();
		
		List<Map<String,Object>> rsList=new ArrayList<>();
		for(String classId : classIdArr) {
			sb.setLength(0);
			params=new ArrayList<>();
			sb.append("select a.clas_id,a.clas_name as className,b.subject_name as subjectName,a.cam_id as camId,a.clas_type ");
			sb.append(" from classes a ");
			sb.append(" left join `subject` b on a.subject_id=b.subject_id and b.is_del=0 ");
			sb.append(" where a.is_del=0 and a.org_id=? and a.clas_id=?");
			params.add(orgId);
			params.add(classId);
			
			String message="";//报名失败原因
			List<Map<String, Object>> classesList=baseDaoEx.queryListBySql(sb.toString(), params);
			Map<String,Object> classMap=classesList.get(0);
			String className=(String)classMap.get("className");
			String subjectName=(String)classMap.get("subjectName");
			Integer camId=(Integer)classMap.get("camId");
			Integer classType=(Integer)classMap.get("clas_type");
			
			int applyResult=0;//报名结果
			long expiredTimes=EXPIRED_TIMES*60*60*1000;//过期时间默认4小时
			Date payExpiredTime=new Date(now.getTime()+expiredTimes);
			if(classType==2) {//新生班
				//调用存储过程
				String sql="{Call proc_apply(?,?,?,?)}";
				params=new ArrayList<>();
				params.add(orgId);
				params.add(camId);
				params.add(classId);
				params.add(studId);
				List<Map<String, Object>> list=baseDaoEx.queryListBySql(sql, params);
				if(list!=null && list.size()>0) {
					Map<String,Object> map=list.get(0);
					applyResult=(Integer)map.get("out_result");
					if(applyResult==0) {
						message="已满";
					}
				}
				
			}else {//老生班
				String hql="from StudentClass where studId=? and clasId=? and camId=? and stuStatus!=8 ";
				params=new ArrayList<>();
				params.add(studId);
				params.add(Integer.parseInt(classId));
				params.add(camId);
				List<StudentClass> studentClassList=baseDaoEx.queryListByHql(hql, params);
				if(studentClassList!=null && studentClassList.size()>0) {
					StudentClass studentClassNew=studentClassList.get(0);
					studentClassNew.setStuStatus(3);
					studentClassNew.setUpdateTime(now);
					studentClassNew.setPayExpiredTime(payExpiredTime);
					baseDaoEx.updateOne(studentClassNew);
				}
				applyResult=1;
			}

			
			Map<String,Object> rsMap=new HashMap<>();
			rsMap.put("className", className);
			rsMap.put("subjectName", subjectName);
			rsMap.put("message", message);
			rsMap.put("applyResult", applyResult);
			rsList.add(rsMap);
			
			//报名后加入mq
			Map<String,Object> mqMap=new HashMap<>();
			mqMap.put("orgId", orgId);
			mqMap.put("camId", camId);
			mqMap.put("classId", Integer.parseInt(classId));
			mqMap.put("studId", studId);
			mqMap.put("parentId", parentId);
			mqMap.put("applyResult", applyResult);
			//插入相关表的mq
			rabbitmqUtils.sendMessage("applyExchange", "applyQueueKey", mqMap);
			//计算缴费过期的mq
			if(applyResult==1) {
				rabbitmqUtils.sendMessage("applyExchange", "applyCancelQueuelKey", mqMap, (int)expiredTimes);
			}
		}
		return rsList;
	}
	
	/**
	 * 报名后插入相关表-用于mq任务
	 * @throws Exception 
	 */
	public void apply(Map<String,Object> map) throws Exception {
		StringBuffer sb=new StringBuffer();
		List<Object> params=new ArrayList<>();
		Map<String,Object> messageMap=new HashMap<>();//发送微信的map
		
		int orgId=(Integer)map.get("orgId");
		int camId=(Integer)map.get("camId");
		int classId=(Integer)map.get("classId");
		int studId=(Integer)map.get("studId");
		int parentId=(Integer)map.get("parentId");
		int applyResult=(Integer)map.get("applyResult");
		Date now=new Date();
		
		sb.append(" select a.clas_id,a.clas_name,a.tuition_fees ");
		sb.append(" ,b.subject_name,c.term_name ");
		sb.append(" from classes a ");
		sb.append(" left join `subject` b on a.subject_id=b.subject_id and b.is_del=0 ");
		sb.append(" left join `term` c on a.org_id=c.org_id and a.term_id=c.term_id and c.is_del=0 ");
		sb.append(" where a.is_del=0 and a.org_id=? and a.clas_id=?");
		params.add(orgId);
		params.add(classId);
		List<Map<String, Object>> list=baseDaoEx.queryListBySql(sb.toString(), params);
		String className="";
		String termName="";
		String subjectName="";
		String tuitionFees="";
		if(list!=null && list.size()>0) {
			Map<String,Object> classMap=list.get(0);
			className=(String)classMap.get("clas_name");
			termName=(String)classMap.get("term_name");
			subjectName=(String)classMap.get("subject_name");
			tuitionFees=(String)classMap.get("tuition_fees");//学费标准
		}
		
		
		
		if(applyResult==1) {//报名成功
			//插入支付详情表
			chargeDetailService.createChargeDetail(orgId, studId, classId, camId);
			messageMap.put("first", "恭喜您，报名成功！");
			messageMap.put("keyword1", tuitionFees+"元");
			messageMap.put("keyword2", subjectName+"（"+className+"）"+termName);
			messageMap.put("remark","请务必在"+EXPIRED_TIMES+"小时内完成支付，否则名额将自动作废。");
		}else {
			messageMap.put("first", "很遗憾，报名失败");
			messageMap.put("keyword1", subjectName+"（"+className+"）"+termName);
			messageMap.put("keyword2", "报名失败（名额已满）");
			messageMap.put("remark", "不要灰心，其他家长退费时，名额会自动释放，请持续关注报名系统。");
		}
		//插入报名表
		Apply apply=new Apply();
		apply.setOrgId(orgId);
		apply.setCamId(camId);
		apply.setClasId(classId);
		apply.setStudId(studId);
		apply.setParentId(parentId);
		apply.setResult(applyResult);
		apply.setType(1);
		apply.setIsDel(0);
		apply.setInsertTime(now);
		apply.setUpdateTime(now);
		baseDaoEx.saveOne(apply);
		
		//计算可用名额
		params=new ArrayList<>();
		params.add(classId);
		String sql="select count(*) from student_class where quota_hold=1 and clas_id = ?";
		int studCount=baseDaoEx.countBySql(sql, params);//占坑名额
		Classes classes=baseDaoEx.findById(Classes.class, classId);
		int classSize=classes.getSize();
		int usableNum=classSize-studCount;
		if(usableNum<0) {
			usableNum=0;
		}
		classes.setUsable_num(usableNum);
		classes.setUpdate_time(now);
		baseDaoEx.updateOne(classes);
		
		//发送微信
 		this.sendWX(orgId,studId,applyResult,messageMap);
		
	}
	
	/**
	 * 4小时后报名作废-用于mq任务
	 */
	public void applyCancel(Map<String,Object> map) {
		int camId=(Integer)map.get("camId");
		int classId=(Integer)map.get("classId");
		int studId=(Integer)map.get("studId");
		List<Object> params=new ArrayList<>();
		params.add(studId);
		params.add(classId);
		params.add(camId);
		//学员班级关系表置为作废
		String sql="update student_class set stu_status=4,update_time=NOW() where stud_id=? and clas_id=? and cam_id=? and stu_status!=8";
		baseDaoEx.executeSql(sql, params);
		//报名作废后需要等待15分钟才能置为不占坑，这里只需加入mq即可（doQuotaHold）
		int delay=CommonUtils.getRandomInteger(15, 120)*60*1000;
		rabbitmqUtils.sendMessage("applyExchange", "quotaHoldQueueKey", map, delay);
	}
	
	/**
	 * 报名作废15分钟后 置为不占坑
	 * 缴费完成后申请退费15分钟后 置为不占坑
	 * 用于mq任务
	 */
	public void doQuotaHold(Map<String,Object> map) {
		int camId=(Integer)map.get("camId");
		int classId=(Integer)map.get("classId");
		int studId=(Integer)map.get("studId");
		List<Object> params=new ArrayList<>();
		params.add(studId);
		params.add(classId);
		params.add(camId);
		//置为不占坑(判断4，6，7，8)，只把这四种状态的学员班级关系表置为不占坑
		String sql="update student_class set quota_hold=0,update_time=NOW() where stud_id=? and clas_id=? and cam_id=? and stu_status in(4,6,7,8)";
		baseDaoEx.executeSql(sql, params);
	}
	
	/**
	 * 发送微信
	 * @param orgId
	 * @throws Exception
	 */
	public void sendWX(int orgId,int studId,int applyResult,Map<String,Object> messageMap) throws Exception {
		try {
			String templateId="";
			if(applyResult==1) {//报名成功的模板
				templateId=tokenService.getWxTemplateId(String.valueOf(orgId), "报名成功");// TODO
			}else {//失败的模板
				templateId=tokenService.getWxTemplateId(String.valueOf(orgId), "报名失败");// TODO
			}
			
			Result<List<Parent>> pResult = new Result<List<Parent>>();
			String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+studId, "application/x-www-form-urlencoded");
			pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
			List<Parent> parents = pResult.getData();
			if(parents!=null && parents.size()>0) {
				for(Parent p : parents) {
					String openId=p.getOpenid();
					if(!StringUtils.isBlank(openId)) {
						messageMap.put("open_id", openId);
						messageMap.put("templateId", templateId);
						WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(messageMap);
						String access_token = tokenService.getAccessTokenByOrg_Id(orgId);
						SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("报名后微信推送失败"+ex.getMessage());
		}
	}
	
	public String test() {
		System.out.println(new Date());
		Map<String,Object> mqMap=new HashMap<>();
		mqMap.put("orgId", 1);
		mqMap.put("camId", 2);
		mqMap.put("classId", 3);
		rabbitmqUtils.sendMessage("applyExchange", "applyQueueKey", mqMap);
		return "success";
	}
}
