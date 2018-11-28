package cn.edugate.esb.web.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.ClasToSortComparator;
import cn.edugate.esb.comparator.GradeSorter;
import cn.edugate.esb.comparator.GradeToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.ClassesForCourseFilter;
import cn.edugate.esb.params.filter.ClassesShortFilterBF;
import cn.edugate.esb.params.filter.GradeFilter;
import cn.edugate.esb.params.filter.GradeForCourseFilter;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;

@Controller
@RequestMapping("/api/grade")
public class GradeController {
	@Autowired
	private RedisClassesDao redisClassesDao;
	@Autowired
	private RedisGradeDao redisGradeDao;
	@Autowired
	private RedisStudentDao redisStudentDao;
	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	/**
	 * 查询机构下的年级
	 * @param orgId 【必填】机构主键
	 * @param request  
	 * @return 机构下的年级列表
	 */
	@RequestMapping(value = "/getGrades")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GradeFilter.class, target=Grade.class)})
	public @ResponseBody Result<List<Grade>> getGrades(
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		Result<List<Grade>> result = new Result<List<Grade>>();
		Organization organization = redisOrganizationDao.getByOrgId(orgId.toString(),null);
		if(null==organization){
			result.setSuccess(false);
			result.setMessage(EnumMessage.organization_not_found.getMessage());
			result.setCode(EnumMessage.organization_not_found.getCode());
			return result;
		}
		List<Grade> list = new ArrayList<Grade>();		
		
		list = redisGradeDao.getGrades(orgId);
		//GradeToSortComparator mc = new GradeToSortComparator(); 
		GradeSorter mc = new GradeSorter(); 
        Collections.sort(list,mc);		
		
		result.setData(list);
		
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	
	/**
	 * 查询机构下的年级带班级
	 * @param orgId 【必填】机构主键
	 * @param request  
	 * @return 机构下的年级列表
	 */
	@RequestMapping(value = "/getGradesAndClas")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GradeFilter.class, target=Grade.class),
			@EduJsonFilter(mixin=ClassesShortFilterBF.class, target=Classes.class)})
	public @ResponseBody Result<Map<String, Object>> getGradesAndClas(
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		List<Object> objs = new ArrayList<Object>();		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		List<Grade> gradelist = redisGradeDao.getGrades(orgId);
		//GradeToSortComparator mc = new GradeToSortComparator();  
		GradeSorter mc = new GradeSorter(); 
        Collections.sort(gradelist,mc);
		
		
		
		for (Grade grade : gradelist) {  
			Map<String,Object> objmap = new HashMap<String,Object>();
			List<Classes> clasList = redisClassesDao.getClassesOfGrade(grade.getGrade_id(),null);
			ClasToSortComparator cmc = new ClasToSortComparator();  
	        Collections.sort(clasList,cmc);
			
			objmap.put("grade_id", grade.getGrade_id());
			objmap.put("grade_name", grade.getGrade_name());
			objmap.put("grade_number", grade.getGrade_number());
			objmap.put("grade_note", grade.getGrade_note());
			objmap.put("grade_type", grade.getGrade_type());
			objmap.put("clasList", clasList);
			
			objs.add(objmap);
		}
		Map<String,Object> map = new HashMap<String,Object>();	
		try{		
			map.put("depData", objs);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}		
		return result;
			
	}

	/**
	 * 查询机构下的年级及其全套附属信息(年级组长、下属班级、班主任、任课教师)
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getGradesWithAllInfo")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GradeForCourseFilter.class, target=Grade.class),
			@EduJsonFilter(mixin=ClassesForCourseFilter.class, target=Classes.class)})
	public @ResponseBody Result<Object> getGradesWithAllInfo(
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		Result<Object> result = new Result<Object>();	
		Map<String,Object> map = new HashMap<String,Object>();	
		try{		
			List<Grade> gradelist = redisGradeDao.getGradesWithAllInfo(orgId);
			//GradeToSortComparator mc = new GradeToSortComparator(); 
			GradeSorter mc = new GradeSorter(); 
	        Collections.sort(gradelist,mc);
			result.setData(gradelist);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}		
		return result;
	}
}
