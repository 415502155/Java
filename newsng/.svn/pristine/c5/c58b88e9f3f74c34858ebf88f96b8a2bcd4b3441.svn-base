package sng.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sng.constant.Consts;
import sng.dao.BaseDaoEx;
import sng.dao.BaseSetTermManageDao;
import sng.dao.PlanDao;
import sng.entity.Plan;
import sng.entity.Term;
import sng.pojo.PlanClassVo;
import sng.pojo.SessionInfo;
import sng.util.CommonUtils;
import sng.util.Paging;

/**
 * 招生计划
 * @author s
 *
 */
@Service
@Transactional
public class PlanServiceImpl {
	@Resource
	private BaseDaoEx baseDaoEx;
	@Resource
	private PlanDao planDao;
	@Resource
	private BaseSetTermManageDao baseSetTermManageDao;
	
	/**
	 * 获取年份列表
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<String> getYearList(int orgId) {
		List<String> yearList=new ArrayList<>();
		List<Object> params=new ArrayList<>();
		String sql="select distinct b.cur_year from plan a,term b where a.is_del=0 and a.term_id=b.term_id and a.org_id=? order by b.cur_year";
		params.add(orgId);
		List<Map<String, Object>> list=baseDaoEx.queryListBySql(sql, params);
		for(Map<String, Object> map : list) {
			String year=(String)map.get("cur_year");
			yearList.add(year);
		}
		return yearList;
	}
	
	/**
	 * 招生计划列表
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Paging getPagePlan(int orgId,String year,int pageNo,int limit) {
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.plan_id as planId, title,a.begin_time as beginTime,a.end_time as endTime,a.class_type as classType,a.plan_switch as planSwitch from plan a,term b where a.is_del=0 and b.is_del=0 and a.term_id=b.term_id ");
		sb.append(" and a.org_id=? ");
		params.add(orgId);
		if(!StringUtils.isBlank(year)) {
			sb.append(" and b.cur_year=? ");
			params.add(year);
		}
		String countSql="select count(*) from ("+sb.toString()+") t";
		sb.append(" order by b.cur_year desc ");
		Paging pageModel=baseDaoEx.queryPageBySql(sb.toString(),countSql, pageNo, limit,  Plan.class, params);
		
		List<Plan> planVoList=new ArrayList<>();
		List<Plan> planList=pageModel.getData();
		for(Plan plan : planList) {
			int planId=plan.getPlanId();
			int classType=plan.getClassType();
			
			//招生班级数
			params=new ArrayList<>();
			sb.setLength(0);
			sb.append(" select count(*) from classes where is_del=0 and plan_id=? ");
			params.add(planId);
			int classCount=baseDaoEx.countBySql(sb.toString(), params);
			
			//招生进度
			String progress="";
			sb.setLength(0);
			sb.append("select ifnull(sum(ss_num-st_num),0) from classes where is_del=0 and plan_id=?");
			int fenzi=((BigDecimal)baseDaoEx.queryObjectBySql(sb.toString(), params)).intValue();
			progress=fenzi+"";
			if(classType==1) {//如果是老生班 则查询分母
				sb.setLength(0);
				sb.append(" select count(distinct c.stu_status) from plan a,classes b,student_class c ");
				sb.append(" where a.plan_id=b.plan_id and b.clas_id=c.clas_id and a.is_del=0 and b.is_del=0 and c.is_del=0 ");
				sb.append(" and a.plan_id=? ");
				int fenmu=baseDaoEx.countBySql(sb.toString(), params);
				progress=fenzi+"/"+fenmu;
			}
			
			//已缴学费
			sb.setLength(0);
			sb.append("select ifnull(sum(b.ss_money-b.st_money),0) from classes a,charge b where a.is_del=0 and b.is_del=0 and a.clas_id=b.clas_id and a.plan_id=?");
			double tuition=(Double)baseDaoEx.queryObjectBySql(sb.toString(), params);
			
			
			plan=CommonUtils.generateBean(plan, "classCount","progress","tuition","beginTimeStr","endTimeStr");
			BeanMap beanMap=BeanMap.create(plan);
			beanMap.put("beginTimeStr", CommonUtils.dateFormat(plan.getBeginTime(), "yyyy-MM-dd HH:mm"));
			beanMap.put("endTimeStr", CommonUtils.dateFormat(plan.getEndTime(), "yyyy-MM-dd HH:mm"));
			beanMap.put("classCount", classCount+"");
			beanMap.put("progress", progress);
			beanMap.put("tuition", CommonUtils.numberFormat(tuition/100, 4));
			plan=(Plan)beanMap.getBean();
			planVoList.add(plan);
		}
		pageModel.setData(planVoList);
		
		return pageModel;
	}
	
	/**
	 * 查询招生计划详情
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Plan getPlanInfo(int planId) {
		List<Object> params=new ArrayList<>();
		params.add(planId);
		StringBuffer sb=new StringBuffer();
		sb.append("select clas_id from classes where is_del=0 and plan_id=?");
		List<Map<String, Object>> classIdList=baseDaoEx.queryListBySql(sb.toString(), params);
		Integer[] classIds=new Integer[classIdList.size()];
		for(int i=0;i<classIdList.size();i++) {
			classIds[i]=(Integer)((Map)classIdList.get(i)).get("clas_id");
		}
		
		Plan plan=planDao.get(Plan.class, planId);
		int termId=plan.getTermId();
		Term term=baseSetTermManageDao.get(Term.class, termId);
		String termName=term.getTerm_name();
		plan=CommonUtils.generateBean(plan, "beginTimeStr","endTimeStr","classIds","serverTime","termName");
		BeanMap beanMap=BeanMap.create(plan);
		beanMap.put("beginTimeStr", CommonUtils.dateFormat(plan.getBeginTime(), "yyyy-MM-dd HH:mm"));
		beanMap.put("endTimeStr", CommonUtils.dateFormat(plan.getEndTime(), "yyyy-MM-dd HH:mm"));
		beanMap.put("classIds", StringUtils.join(classIds, ","));//当前招生计划下选择的班级id
		beanMap.put("serverTime", CommonUtils.dateFormat(new Date(), null));
		beanMap.put("termName", termName);
		plan=(Plan)beanMap.getBean();
		return plan;
	}
	
	/**
	 * 新增或修改计划
	 */
	public void savePlan(SessionInfo sessionInfo,Plan plan,String classIds,Map<String,Object> paramMap) throws ParseException {
		List<Object> params=null;
		StringBuffer sb=new StringBuffer();
		Integer planId=plan.getPlanId();
		
		String loginName=sessionInfo.getLoginName();
		int orgId=sessionInfo.getOrgId();
		int planType=1;//培训
		int isDel=0;
		String creater="";
		
		if(planId==null) {//新增
			plan.setOrgId(orgId);
			plan.setPlanType(planType);
			plan.setIsDel(isDel);
			plan.setInsertTime(new Date());
			plan.setUpdateTime(new Date());
			plan.setCreater(creater);
			plan.setBeginTime(CommonUtils.stringToDate((String)paramMap.get("beginTimeStr"), null));
			plan.setEndTime(CommonUtils.stringToDate((String)paramMap.get("endTimeStr"), null));
			plan.setCreater(loginName);
			planId=(Integer)baseDaoEx.saveOne(plan);
			
			params=new ArrayList<>();
			params.add(planId);
			params.add(orgId);
		}else {//更新
			Plan oldPlan=baseDaoEx.findById(Plan.class, planId);
			oldPlan.setTermId(plan.getTermId());
			oldPlan.setClassType(plan.getClassType());
			oldPlan.setBeginTime(CommonUtils.stringToDate((String)paramMap.get("beginTimeStr"), null));
			oldPlan.setEndTime(CommonUtils.stringToDate((String)paramMap.get("endTimeStr"), null));
			oldPlan.setTitle(plan.getTitle());
			oldPlan.setContent(plan.getContent());
			oldPlan.setPlanSwitch(plan.getPlanSwitch());
			oldPlan.setUpdateTime(new Date());
			planDao.update(oldPlan);
			
			//先把老班级下的planId清空
			params=new ArrayList<>();
			params.add(planId);
			params.add(orgId);
			sb.setLength(0);
			sb.append("update classes set plan_id=null where plan_id=? and org_id=?");
			baseDaoEx.executeSql(sb.toString(), params);
			
		}
		//将新的班级回填planId
		sb.setLength(0);
		sb.append("update classes set plan_id=? where org_id=? and clas_id in("+classIds+")");
		baseDaoEx.executeSql(sb.toString(), params);
		
		
	}
	
	/**
	 * 查询招生计划要选择的班级列表
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Paging getPageCampusClass(SessionInfo sessionInfo,Map<String,Object> paramMap,int pageNo,int limit) {
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		int orgId=sessionInfo.getOrgId();
		String planId=(String)paramMap.get("planId");//如果是新增 则为空，已有计划的班级不会出现在列表中
		String termId=(String)paramMap.get("termId");//学期必选*
		String classType=(String)paramMap.get("classType");//类型必选*
		String categoryId=(String)paramMap.get("categoryId");//类目
		String subjectId=(String)paramMap.get("subjectId");//科目
		String classWeek=(String)paramMap.get("classWeek");//星期
		String keyword=(String)paramMap.get("keyword");//关键字
		String camId=(String)paramMap.get("camId");
		//左联查询班级表
		sb.append(" select a.plan_id as planId, a.clas_id as classId,a.clas_name as className,a.clas_type as classType,a.class_start_date as classStartDate,a.class_begin_time as classBeginTime,a.class_over_time as classOverTime, ");
		sb.append(" a.class_week as classWeek,a.size as classSize,a.tuition_fees as tuitionFees,(a.ss_num-a.st_num) as appplyCount, ");
		sb.append(" b.cam_name as camName,c.classroom_name as classroomName,c.building,c.floor,d.subject_name as subjectName ");
		sb.append(" from classes a  ");
		sb.append(" left join campus b on a.cam_id=b.cam_id and a.org_id=b.org_id and b.is_del=0 ");
		sb.append(" left join classroom c on a.classroom_id=c.classroom_id and c.is_del=0 ");
		sb.append(" left join subject d on a.subject_id=d.subject_id and a.org_id=d.org_id and d.is_del=0 ");
		sb.append(" where a.is_del=0   and a.org_id=? ");
		params.add(orgId);
		if(StringUtils.isBlank(planId)) {//新增
			sb.append(" and (a.plan_id is null or a.plan_id='') ");
		}else {//修改
			sb.append(" and (a.plan_id is null or a.plan_id='' or a.plan_id=?) ");
			params.add(planId);
		}
		
		if(!StringUtils.isBlank(termId)) {
			sb.append(" and a.term_id=? ");
			params.add(termId);
		}
		if(!StringUtils.isBlank(classType)) {
			sb.append(" and a.clas_type=? ");
			params.add(classType);
		}
		if(!StringUtils.isBlank(categoryId)) {
			sb.append(" and a.category_id=? ");
			params.add(categoryId);
		}
		if(!StringUtils.isBlank(subjectId)) {
			sb.append(" and a.subject_id=? ");
			params.add(subjectId);
		}
		if(!StringUtils.isBlank(classWeek)) {
			sb.append(" and a.clas_week=? ");
			params.add(classWeek);
		}
		if(!StringUtils.isBlank(keyword)) {
			sb.append(" and (a.clas_name like ?) ");
			params.add("%"+keyword+"%");
		}
		if(!StringUtils.isBlank(camId)) {
			sb.append(" and a.cam_id=? ");
			params.add(camId);
		}
		
		String countSql="select count(*) from("+sb.toString()+") t";
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
			
			voList.add(vo);
		}
		paging.setData(voList);
		
		return paging;
	}
	
	/**
	 * 查询招生计划要选择的班级列表-点击选择全部分页数据
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<String> selectAllClass(SessionInfo sessionInfo,Map<String,Object> paramMap) {
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		int orgId=sessionInfo.getOrgId();
		String planId=(String)paramMap.get("planId");
		String termId=(String)paramMap.get("termId");//学期必选*
		String classType=(String)paramMap.get("classType");//类型必选*
		String categoryId=(String)paramMap.get("categoryId");//类目
		String subjectId=(String)paramMap.get("subjectId");//科目
		String classWeek=(String)paramMap.get("classWeek");//星期
		String keyword=(String)paramMap.get("keyword");//关键字
		String camId=(String)paramMap.get("camId");//校区
		//左联查询班级表
		sb.append(" select a.clas_id as classId ");
		sb.append(" from classes a  ");
		sb.append(" left join campus b on a.cam_id=b.cam_id and a.org_id=b.org_id and b.is_del=0 ");
		sb.append(" left join classroom c on a.classroom_id=c.classroom_id and c.is_del=0 ");
		sb.append(" left join subject d on a.subject_id=d.subject_id and a.org_id=d.org_id and d.is_del=0 ");
		sb.append(" where a.is_del=0   and a.org_id=? ");
		params.add(orgId);
		if(StringUtils.isBlank(planId)) {//新增
			sb.append(" and (a.plan_id is null or a.plan_id='') ");
		}else {//修改
			sb.append(" and (a.plan_id is null or a.plan_id='' or a.plan_id=?) ");
			params.add(planId);
		}
		if(!StringUtils.isBlank(termId)) {
			sb.append(" and a.term_id=? ");
			params.add(termId);
		}
		if(!StringUtils.isBlank(classType)) {
			sb.append(" and a.clas_type=? ");
			params.add(classType);
		}
		if(!StringUtils.isBlank(categoryId)) {
			sb.append(" and a.category_id=? ");
			params.add(categoryId);
		}
		if(!StringUtils.isBlank(subjectId)) {
			sb.append(" and a.subject_id=? ");
			params.add(subjectId);
		}
		if(!StringUtils.isBlank(classWeek)) {
			sb.append(" and a.clas_week=? ");
			params.add(classWeek);
		}
		if(!StringUtils.isBlank(keyword)) {
			sb.append(" and (a.clas_name like ?) ");
			params.add("%"+keyword+"%");
		}
		if(!StringUtils.isBlank(camId)) {
			sb.append(" and a.cam_id=? ");
			params.add(camId);
		}
		
		
		List<String> classIdList=new ArrayList<>();
		List<Map<String, Object>> list=baseDaoEx.queryListBySql(sb.toString(), params);
		if(list!=null && list.size()>0) {
			Map<String, Object> map=list.get(0);
			for(Entry<String, Object> entry : map.entrySet()) {
				classIdList.add(String.valueOf((Integer)entry.getValue()));
			}
		}
		return classIdList;
	}
	
	/**
	 * 查询招生计划要选择的班级列表-列表下面的统计
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Map<String,Object> getClassData(SessionInfo sessionInfo,Map<String,Object> paramMap) {
		List<Object> params=new ArrayList<>();
		StringBuffer sb=new StringBuffer();
		int orgId=sessionInfo.getOrgId();
		String planId=(String)paramMap.get("planId");
		String termId=(String)paramMap.get("termId");//学期必选*
		String classType=(String)paramMap.get("classType");//类型必选*
		String categoryId=(String)paramMap.get("categoryId");//类目
		String subjectId=(String)paramMap.get("subjectId");//科目
		String classWeek=(String)paramMap.get("classWeek");//星期
		String keyword=(String)paramMap.get("keyword");//关键字
		String camId=(String)paramMap.get("camId");//校区
		//左联查询班级表
		sb.append(" select ifnull(sum(a.ss_num-a.st_num),0) as studCount ");
		sb.append(" from classes a  ");
		sb.append(" where a.is_del=0 and a.org_id=? ");
		params.add(orgId);
		if(StringUtils.isBlank(planId)) {//新增
			sb.append(" and (a.plan_id is null or a.plan_id='') ");
		}else {//修改
			sb.append(" and (a.plan_id is null or a.plan_id='' or a.plan_id=?) ");
			params.add(planId);
		}
		if(!StringUtils.isBlank(termId)) {
			sb.append(" and a.term_id=? ");
			params.add(termId);
		}
		if(!StringUtils.isBlank(classType)) {
			sb.append(" and a.clas_type=? ");
			params.add(classType);
		}
		if(!StringUtils.isBlank(categoryId)) {
			sb.append(" and a.category_id=? ");
			params.add(categoryId);
		}
		if(!StringUtils.isBlank(subjectId)) {
			sb.append(" and a.subject_id=? ");
			params.add(subjectId);
		}
		if(!StringUtils.isBlank(classWeek)) {
			sb.append(" and a.clas_week=? ");
			params.add(classWeek);
		}
		if(!StringUtils.isBlank(keyword)) {
			sb.append(" and (a.clas_name like ?) ");
			params.add("%"+keyword+"%");
		}
		if(!StringUtils.isBlank(camId)) {
			sb.append(" and a.cam_id=? ");
			params.add(camId);
		}
		
		Map<String,Object> map=new HashMap<>();
		
		List<Map<String, Object>> list=baseDaoEx.queryListBySql(sb.toString(), params);
		if(list!=null && list.size()>0) {
			map=list.get(0);
		}
		
		return map;
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/spring-context.xml");
		PlanServiceImpl s=(PlanServiceImpl)context.getBean("testServiceImpl");

		System.out.println("main执行结束");
	}
}
