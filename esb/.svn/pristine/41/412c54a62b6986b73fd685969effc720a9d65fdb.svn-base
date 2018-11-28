package cn.edugate.esb.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.ClasToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.ClassesShortFilterBF;
import cn.edugate.esb.params.filter.ClassesShortFilterGN;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.Class2StudentService;
import cn.edugate.esb.service.ClassesService;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Util;

@Controller
@RequestMapping("/api/class")
public class ClassesController {

	@Autowired
	private RedisGradeDao redisGradeDao;

	private RedisClassesDao redisClassesDao;

	private ClassesService classesService;

	private Util util;

	private RedisTechRangeDao redisTechRangeDao;

	private RedisStudentDao redisStudentDao;

	private RedisParentDao redisParentDao;

	private RedisStudentParentDao redisStudentParentDao;

	private StudentService studentService;

	private Class2StudentService class2StudentService;

	@Autowired
	public void setClass2StudentService(Class2StudentService class2StudentService) {
		this.class2StudentService = class2StudentService;
	}

	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Autowired
	public void setRedisStudentParentDao(RedisStudentParentDao redisStudentParentDao) {
		this.redisStudentParentDao = redisStudentParentDao;
	}

	@Autowired
	public void setRedisParentDao(RedisParentDao redisParentDao) {
		this.redisParentDao = redisParentDao;
	}

	@Autowired
	public void setRedisStudentDao(RedisStudentDao redisStudentDao) {
		this.redisStudentDao = redisStudentDao;
	}

	@Autowired
	public void setRedisTechRangeDao(RedisTechRangeDao redisTechRangeDao) {
		this.redisTechRangeDao = redisTechRangeDao;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}

	@Autowired
	public void setRedisClassesDao(RedisClassesDao redisClassesDao) {
		this.redisClassesDao = redisClassesDao;
	}

	/**
	 * 根据年级id 查询班级
	 * 
	 * @param request
	 * @return 符合条件的组的集合
	 */
	@RequestMapping(value = "/getClassesOfGrade")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ClassesShortFilterBF.class, target = Classes.class) })
	public @ResponseBody
	Result<Object> getClassesOfGrade(@RequestParam(value = "grade_id", defaultValue = "-1") Integer grade_id,
			HttpServletRequest request) {
		Result<Object> result = new Result<Object>();

		List<Classes> clasList = redisClassesDao.getClassesOfGrade(grade_id, null);
		ClasToSortComparator cmc = new ClasToSortComparator();
		Collections.sort(clasList, cmc);
		// Map<String,Object> clasMap = new HashMap<String,Object>();
		// Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		// for (Classes classes : clasList) {
		//
		// clasMap.put(classes.getClas_name(), classes);
		// }
		// tempMap = Utils.sortMapByKey(clasMap);

		result.setData(clasList);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 根据机构id 查询班级
	 * 
	 * @param request
	 * @return 符合条件的组的集合
	 */
	@RequestMapping(value = "/getClassesOfOrg")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ClassesShortFilterBF.class, target = Classes.class) })
	public @ResponseBody
	Result<Object> getClassesOfOrg(@RequestParam(value = "org_id", defaultValue = "-1") Integer org_id, HttpServletRequest request) {
		Result<Object> result = new Result<Object>();

		List<Classes> clasList = redisClassesDao.getClassesOfOrg(org_id);
		ClasToSortComparator cmc = new ClasToSortComparator();
		Collections.sort(clasList, cmc);

		// Map<String,Object> clasMap = new HashMap<String,Object>();
		// Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		// for (Classes classes : clasList) {
		//
		// clasMap.put(classes.getClas_name(), classes);
		// }
		// tempMap = Utils.sortMapByKey(clasMap);

		result.setData(clasList);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/getClassesOfOrgByPage")
	public @ResponseBody
	Result<Paging<Classes>> getClassesOfOrgByPage(@RequestParam(value = "org_id", defaultValue = "-1") Integer org_id,
			@RequestParam(value = "is_graduated", defaultValue = "0") Integer is_graduated,
			@RequestParam(value = "cam_id", defaultValue = "0") Integer cam_id,
			@RequestParam(value = "grade_id", defaultValue = "0") Integer grade_id,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer numPerPage, HttpServletRequest request) {
		Result<Paging<Classes>> result = new Result<Paging<Classes>>();
		Paging<Classes> pages = new Paging<Classes>();
		if (pageNum == null) {
			pageNum = 1;
		}

		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);

		this.classesService.getPaging(org_id, is_graduated, cam_id, grade_id, pages);

		result.setData(pages);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 根据机构id 查询班级
	 * 
	 * @param request
	 * @return 符合条件的组的集合
	 */
	@RequestMapping(value = "/getClassesOfCampus")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ClassesShortFilterBF.class, target = Classes.class) })
	public @ResponseBody
	Result<Object> getClassesOfCampus(@RequestParam(value = "cam_id", defaultValue = "-1") Integer cam_id,
			HttpServletRequest request) {
		Result<Object> result = new Result<Object>();

		List<Classes> clasList = redisClassesDao.getClassesOfCampus(cam_id);
		ClasToSortComparator cmc = new ClasToSortComparator();
		Collections.sort(clasList, cmc);

		// Map<String,Object> clasMap = new HashMap<String,Object>();
		// Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
		// for (Classes classes : clasList) {
		//
		// clasMap.put(classes.getClas_name(), classes);
		// }
		// tempMap = Utils.sortMapByKey(clasMap);

		result.setData(clasList);

		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/getClassInfo")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ClassesShortFilterGN.class, target = Classes.class) })
	public @ResponseBody
	Result<Classes> getClassInfo(@RequestParam Integer clas_id, HttpServletRequest request) {
		Result<Classes> result = new Result<Classes>();
		// Classes classes = this.classesService.getById(clas_id);
		Classes classes = redisClassesDao.getClassesById(clas_id + "", null);
		if (StringUtils.isEmpty(classes.getClas_note()))
			classes.setClas_note("");
		Grade grade = redisGradeDao.getGradeById(classes.getGrade_id(), null);
		classes.setGrade_id(grade.getGrade_id());
		classes.setGrade_name(grade.getGrade_name());
		result.setData(classes);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/addClassSave")
	public @ResponseBody
	Result<Classes> addClassSave(@RequestParam Integer grade_id, @RequestParam String data, HttpServletRequest request) {
		Result<Classes> result = new Result<Classes>();

		Classes classes = this.util.getPojoFromJSON(data, Classes.class);
		classes.setGrade_id(grade_id);
		classes.setInsert_time(new Date());
		this.classesService.add(classes);
		Grade2Clas grade2Clas = new Grade2Clas();
		grade2Clas.setClas_id(classes.getClas_id());
		grade2Clas.setGrade_id(grade_id);
		grade2Clas.setInsert_time(new Date());
		grade2Clas.setIs_del(false);
		this.classesService.addGrade2Class(grade2Clas);
		result.setData(classes);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 
	 * @param grade_id 传入的年级id为修改后的值
	 * @param clas_id
	 * @param data
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editClassSave")
	@ResponseBody
	public Result<Classes> editClassSave(@RequestParam Integer grade_id, @RequestParam Integer clas_id, @RequestParam String data,
			HttpServletRequest request) {
		Result<Classes> result = new Result<Classes>();

		Classes classes = this.util.getPojoFromJSON(data, Classes.class);
		
		classes = classesService.modifyClassInfo(classes, grade_id);
		
		result.setData(classes);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/deleteClass")
	public @ResponseBody
	Result<Classes> deleteClass(@RequestParam Integer clas_id, HttpServletRequest request) {
		Result<Classes> result = new Result<Classes>();
		this.classesService.deleteById(clas_id);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/viewClassInfo")
	public @ResponseBody
	Result<Classes> viewClassInfo(@RequestParam Integer clas_id, HttpServletRequest request) {
		Result<Classes> result = new Result<Classes>();
		Classes classes = this.classesService.getById(clas_id);

		Map<String, Object> classMaster = this.redisTechRangeDao.getSkClasBZR(classes.getClas_id().toString(), null);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Teacher> ls = new ArrayList(classMaster.values());
		classes.setMasters(ls);

		List<String> classTeacherIds = this.redisTechRangeDao.getSkClasTechId(classes.getClas_id().toString());

		classes.setTeacher_num(classTeacherIds.size());

		// Map<String, Student> classStudentIds = this.redisStudentDao.getStudentsByClassId(classes.getClas_id(),null);
		// List<Student> students = classStudentIds!=null?new ArrayList<Student>(classStudentIds.values()):new
		// ArrayList<Student>();
		List<Student> students = this.studentService.getStudentsByClassId(classes.getClas_id());
		for (Student student : students) {
			Map<String, StudentParent> sps = this.redisStudentParentDao.getByStudentId(student.getStud_id().toString());
			if (sps != null) {
				List<String> parentids = new ArrayList<String>(sps.keySet());
				String parentName = "";
				for (String parentid : parentids) {
					Parent parent = this.redisParentDao.getByParentId(parentid);
					parentName += !"".equals(parentName) ? "," + parent.getParent_name() : parent.getParent_name();
				}
				student.setParent_name(parentName);
			}
		}

		classes.setStudent_num(students.size());
		classes.setStudents(students);

		result.setData(classes);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/getClass")
	public @ResponseBody
	Result<List<Classes>> getClass(@RequestParam Integer org_id, @RequestParam Integer cam_id, @RequestParam Integer grade_id,
			HttpServletRequest request) {
		Result<List<Classes>> result = new Result<List<Classes>>();
		List<Classes> clas = this.classesService.getClassByOrgIdCamIdGradeId(org_id, cam_id, grade_id);
		result.setData(clas);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteStudent")
	public Result<String> deleteStudent(@RequestParam Integer clas2stud_id, HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		this.class2StudentService.deleteClassStudent(clas2stud_id);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 批量调整学生所在班级，studentID可以包含多个学生ID，以_分隔
	 * @param originalClassID
	 * @param targetClassID
	 * @param studentID
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/batchMoveStudent")
	public Result<String> batchMoveStudent(@RequestParam String originalClassID, @RequestParam String targetClassID,
			@RequestParam String studentID) throws IOException {
		Result<String> result = new Result<String>();
		System.out.println(studentID);
		if (StringUtils.isNotBlank(originalClassID) && StringUtils.isNotBlank(targetClassID) && StringUtils.isNotBlank(studentID)) {
			String[] idArray = studentID.split("_");
			
			class2StudentService.batchMoveStudent(originalClassID, targetClassID, idArray);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
			return result;
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}

	}
	
	
	/**
	 * 根据机构id 查询班级
	 * 
	 * @param request
	 * @return 符合条件的组的集合
	 */
	@RequestMapping(value = "/getClassesOfOrgSQL")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ClassesShortFilterBF.class, target = Classes.class) })
	public @ResponseBody
	Result<Object> getClassesOfOrgSQL(@RequestParam(value = "org_id", defaultValue = "-1") Integer org_id, HttpServletRequest request) {
		Result<Object> result = new Result<Object>();

		List<Classes> clasList =classesService.getClassesOfOrg(org_id);
		ClasToSortComparator cmc = new ClasToSortComparator();
		Collections.sort(clasList, cmc);

		result.setData(clasList);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	

}
