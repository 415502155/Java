package cn.edugate.esb.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import cn.edugate.esb.comparator.DepartmentToSortComparator;
import cn.edugate.esb.comparator.TechToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.DepartmentDetailFilter;
import cn.edugate.esb.params.filter.DepartmentFilter;
import cn.edugate.esb.params.filter.TeacherShortFilter;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.util.Constant;

@Controller
@RequestMapping("/api/dep")
public class DepartmentController {
	
	@Autowired
	private RedisDepartmentDao depDao;
	
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RedisDepartmentDao redisDepartmentDao;
	
	@Autowired
	private TechRangeService techRangeService;
	
	@Autowired
	private RedisTechRangeDao redisTechRangeDao;
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * 根据部门ID 获取部门相信信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentInfo")
	public Result<Department> getDepartmentInfo(@RequestParam String dep_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Department> result = new Result<Department>();	
		Department department = depDao.getByDepId(dep_id,null);
		try{
			result.setData(department);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}

	/**
	 * 根据机构ID 获取部门集合
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepsOfOrgId")
	@EduJsonFilters(value={@EduJsonFilter(mixin=DepartmentFilter.class, target=Department.class)})
	public Result<Map<String, Object>> getDepsOfOrgId(@RequestParam String org_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		List<Department> list = depDao.getDepsByOrgId(org_id);
		Map<String,Object> map = new HashMap<String,Object>();	
		try{		
			map.put("deps", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}
	
	/**
	 * 根据机构ID 获取部门集合 带员工,带管理员
	 * dep_id不为空时，返回的员工可根据字段is_select判断是否是dep_id部门下的管理员
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepsAndTechOfOrgId")
	@EduJsonFilters(value={@EduJsonFilter(mixin=TeacherShortFilter.class, target=Teacher.class)})
	public Result<Map<String, Object>> getDepsAndTechOfOrgId(@RequestParam String org_id,
			@RequestParam(value="dep_id",defaultValue="") String dep_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		List<Department> list = depDao.getDepsByOrgId(org_id);
		DepartmentToSortComparator mcg = new DepartmentToSortComparator();  
        Collections.sort(list,mcg);
		
		List<Object> deps = new ArrayList<Object>();
		Map<String,Object> manager = new HashMap<String,Object>();
		if(StringUtils.isEmpty(dep_id)){
			manager = redisTechRangeDao.getDepinfoManager(dep_id,null);
		}
		for (Department dep : list) {  
			Map<String,Object> objmap = new HashMap<String,Object>();
			List<Teacher> techList = redisTeacherDao.getTechsByDepId(dep.getDep_id()+"",null);
			TechToSortComparator mc = new TechToSortComparator();  
	        Collections.sort(techList,mc);
			if(StringUtils.isEmpty(dep_id)){
				for (Teacher teacher : techList) {
					if(manager.get(teacher.getTech_id().toString()) instanceof Teacher){
						teacher.setIs_selected(Constant.YES);
					}else{
						teacher.setIs_selected(Constant.NO);						
					}
				}
			}
			objmap.put("dep_id", dep.getDep_id());
			objmap.put("dep_name", dep.getDep_name());
			objmap.put("hx_groupid", dep.getHx_groupid());
			objmap.put("techData", techList);
			deps.add(objmap);
		}
		Map<String,Object> map = new HashMap<String,Object>();	
		try{		
			map.put("depData", deps);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}

	/**
	 * 根据机构ID 获取部门详情集合
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepsDetailList")
	@EduJsonFilters(value={@EduJsonFilter(mixin=DepartmentDetailFilter.class, target=Department.class)})
	public Result<Map<String, Object>> getDepsDetailList(@RequestParam String org_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		List<Department> list = departmentService.getDepsDetailList(org_id);
		Map<String,Object> map = new HashMap<String,Object>();	
		try{		
			map.put("deps", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}

	/**
	 * 创建部门
	 * @param orgId       【必填】机构主键
	 * @param name        【必填】部门名称
	 * @param officeplace [选填]办公地点
	 * @param officephone [选填]办公电话
	 * @param parent_id   [选填]父级主键
	 * @param note        [选填]备注说明
	 * @param request
	 * @return 创建完成的部门
	 */
	@RequestMapping(value = "/addDepartment")
	@EduJsonFilters(value={@EduJsonFilter(mixin=DepartmentDetailFilter.class, target=Department.class)})
	public @ResponseBody Result<Map<String, Department>> addDepartment(
			@RequestParam(value="org_id") Integer orgId,
			@RequestParam(value="dep_name") String name,
			@RequestParam(value="dep_officeplace",defaultValue="") String officeplace,
			@RequestParam(value="dep_officephone",defaultValue="") String officephone,
			@RequestParam(value="dep_note",defaultValue="") String note,
			@RequestParam(value="parent_id",defaultValue="0") Integer parent_id,
			HttpServletRequest request){
		Result<Map<String, Department>> result = new Result<Map<String, Department>>();
		Map<String, Department> data = new HashMap<String, Department>();
		//创建部门
		Department department = new Department();
		department.setDep_name(name);
		department.setDep_officephone(officephone);
		department.setDep_officeplace(officeplace);
		department.setDep_note(note);
		department.setParent_id(parent_id);
		department.setOrg_id(orgId);
		// 接口创建的部门必然不能为默认部门
		department.setType(0);
		departmentService.add(department);
		//返回结果
		data.put("department", department);
		result.setData(data);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 更新部门
	 * @param departmentId   【必填】部门主键
	 * @param departmentName [选填] 部门名称
	 * @param orgId     [选填] 机构主键
	 * @param departmentType [选填] 部门类型
	 * @param note      [选填] 备注
	 * @param request
	 * @return 更新后的部门
	 */
	@RequestMapping(value = "/updateDepartment")
	@EduJsonFilters(value={@EduJsonFilter(mixin=DepartmentFilter.class, target=Department.class)})
	public @ResponseBody Result<Map<String, Department>> updateDepartment(
			@RequestParam(value="dep_id") Integer depId,
			@RequestParam(value="dep_name",defaultValue="") String name,
			@RequestParam(value="dep_officeplace",defaultValue="") String officeplace,
			@RequestParam(value="dep_officephone",defaultValue="") String officephone,
			@RequestParam(value="dep_note",defaultValue="sjwy==Department NullPoint==sjwy") String note,
			HttpServletRequest request){
		Result<Map<String, Department>> result = new Result<Map<String, Department>>();
		Department department = redisDepartmentDao.getByDepId(depId.toString(),null);
		if(null==department){
			result.setSuccess(false);
			result.setMessage(EnumMessage.department_not_found.getMessage());
			result.setCode(EnumMessage.department_not_found.getCode());
			return result;
		}
		if(StringUtils.isNotEmpty(name)){
			department.setDep_name(name);
		}
		if(StringUtils.isNotEmpty(officeplace)){
			department.setDep_officeplace(officeplace);
		}
		if(StringUtils.isNotEmpty(officephone)){
			department.setDep_officephone(officephone);
		}
		if(!"sjwy==Department NullPoint==sjwy".equals(note)){
			department.setDep_note(note);
		}
		departmentService.update(department);
		Map<String, Department> data = new HashMap<String, Department>();
		data.put("department", department);
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 删除部门
	 * @param depId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteDepartment")
	public @ResponseBody Result<Map<String, Integer>> deleteDepartment(
			@RequestParam(value="dep_id") Integer depId,
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		Result<Map<String, Integer>> result = new Result<Map<String, Integer>>();
		//解除部门管理员职务
		Map<String,Object> manager = redisTechRangeDao.getDepinfoManager(depId.toString(),null);
		for (Object teacher : manager.values()) {
			Teacher t = (Teacher)teacher;
			TechRange techRange = new TechRange();
			techRange.setTech_id(t.getTech_id());
			techRange.setRl_id(EnumRoleLabel.部门管理员.getCode());
			techRange.setOrg_id(orgId);
			techRange.setDep_id(depId.toString());
			techRangeService.deleteRange(techRange);
		}
		//替换当前部门到默认部门
		List<Teacher> list = redisTeacherDao.getTechsByDepId(depId.toString(), null);
		Integer defaultId = departmentService.getDefaultId(depId);
		for (Teacher teacher : list) {
			teacher.setDep_id(defaultId);
		}
		teacherService.update(list);
		//删除部门
		departmentService.delete(depId);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 校验部门名称
	 * @param depId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkDepName")
	public @ResponseBody Result<Integer> checkDepName(
			@RequestParam(value="dep_name") String dep_name,
			@RequestParam(value="dep_id",defaultValue="") String dep_id,
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		Result<Integer> result = new Result<Integer>();
		List<Department> list = redisDepartmentDao.getDepsByOrgId(orgId.toString());
		for (Department department : list) {
			if(dep_name.equals(department.getDep_name())){
				if(!dep_id.equals(department.getDep_id().toString())){
					result.setSuccess(false);
					result.setMessage(EnumMessage.department_name_illegal.getMessage());
					result.setCode(EnumMessage.department_name_illegal.getCode());
					return result;
				}
			}
		}
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 移除部门成员
	 */
	@RequestMapping(value = "/removeMember")
	public @ResponseBody Result<Integer> removeMember(
			@RequestParam(value="tech_ids") String tech_ids,
			@RequestParam(value="from_dep_id") String from_dep_id,
			@RequestParam(value="to_dep_id",defaultValue="") String to_dep_id,
			@RequestParam(value="org_id") Integer org_id,
			HttpServletRequest request){
		Result<Integer> result = new Result<Integer>();
		List<String> idList = Arrays.asList(tech_ids.split(","));
		if(StringUtils.isEmpty(to_dep_id)){
			to_dep_id = departmentService.getDefaultId(Integer.parseInt(from_dep_id)).toString();
		}
		try {
			for (String id : idList) {
				Teacher t = redisTeacherDao.getByTechId(id, null);
				t.setDep_id(Integer.parseInt(to_dep_id));
				teacherService.update(t);
			}
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}

	/**
	 * 添加成员
	 */
	@RequestMapping(value = "/addTeachers")
	public @ResponseBody Result<Integer> addTeachers(
			@RequestParam(value="tech_ids") String tech_ids,
			@RequestParam(value="dep_id") String dep_id,
			HttpServletRequest request){
		Result<Integer> result = new Result<Integer>();
		List<String> idList = Arrays.asList(tech_ids.split(","));
		try {
			for (String id : idList) {
				Teacher t = redisTeacherDao.getByTechId(id, null);
				t.setDep_id(Integer.parseInt(dep_id));
				teacherService.update(t);
			}
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}
	
}
