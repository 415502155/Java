package cn.edugate.esb.web.manage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.util.FilterApply;
import cn.edugate.esb.util.Order;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.PropertyFilter;
import cn.edugate.esb.util.Selector;
import cn.edugate.esb.util.Util;

/**
 * 
 * @Name: 权限管理controller
 * @Description: 教师范围(添加和删除权限)。
 * @date  2017-5-27
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/range")
public class MrangeController {
	static Logger logger=LoggerFactory.getLogger(MrangeController.class);
	private TechRangeService techRangeService;
	private TeacherService teacherService;
	private DepartmentService departmentService;
	private OrganizationService organizationService;
	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Autowired
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Autowired
	public void setTechRangeService(TechRangeService techRangeService) {
		this.techRangeService = techRangeService;
	}


	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}


//*******************************设置管理员开始*************************************	
	@RequestMapping(value = "/setadmin")
	public ModelAndView setadmin(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="0") Integer org_id,@RequestParam(defaultValue="") String name,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setadmin");
		List<Teacher> teachers = this.teacherService.getTeacherSearch(org_id, name);		
		List<Department> deps = this.departmentService.getListByOrgId(org_id);
		for (Department dep : deps) {
			Selector<Teacher> selector = Selector.from(teachers);
			FilterApply filter = PropertyFilter.eq("dep_id", dep.getDep_id());
			selector.where(filter).orderBy(Order.asc("tech_id"));
			List<Teacher> techers = selector.select();
			dep.setTeachers(techers);
			teachers.removeAll(techers);
		}	
		if(teachers!=null&&teachers.size()>0){
			Department dep = new Department();
			dep.setDep_id(999999);
			dep.setDep_name("未分配用户");
			dep.setTeachers(teachers);
			deps.add(dep);
		}		
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("deps", deps);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/getAdminOrgs",method = RequestMethod.GET)
	public ModelAndView getAdminOrgs(@RequestParam Integer tech_id,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/getAdminOrgs");
		String orgids = this.techRangeService.getAdminOrgsByTechId(tech_id);
		String[] ids = orgids.split("\\,");
		Set<Integer> idss = new HashSet<Integer>();
		for (String id : ids) {
			try {
				idss.add(Integer.parseInt(id));	
			} catch (Exception e) {
				// TODO: handle exception
			}					
		}		
		List<Organization> orgs = this.organizationService.getOrgList();
		for (Organization org : orgs) {
			if(idss.contains(org.getOrg_id())){
				org.setChecked(true);
			}
		}
		mav.addObject("tech_id", tech_id);
		mav.addObject("orgs", orgs);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setAdminOrgs",method = RequestMethod.POST)
	public ModelAndView setAdminOrgs(@RequestParam Integer tech_id,@RequestParam String orgids,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/getAdminOrgs");
		String[] ids = orgids.split("\\,");
		Set<Integer> idss = new HashSet<Integer>();
		for (String id : ids) {
			try {
				idss.add(Integer.parseInt(id));	
			} catch (Exception e) {
				// TODO: handle exception
			}					
		}		
		List<Organization> orgs = this.organizationService.getOrgList();
		for (Organization org : orgs) {
			if(idss.contains(org.getOrg_id())){
				org.setChecked(true);
			}
		}		
		mav.addObject("tech_id", tech_id);
		mav.addObject("orgs", orgs);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}

//**********************************设置管理员结束**********************************
	

//**********************************设置校长开始**********************************
	@RequestMapping(value = "/setheadmaster")
	public ModelAndView setheadmaster(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="0") Integer org_id,@RequestParam(defaultValue="") String name,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setheadmaster");
		List<Teacher> teachers = this.teacherService.getTeacherSearch(org_id, name);		
		List<Department> deps = this.departmentService.getListByOrgId(org_id);
		for (Department dep : deps) {
			Selector<Teacher> selector = Selector.from(teachers);
			FilterApply filter = PropertyFilter.eq("dep_id", dep.getDep_id());
			selector.where(filter).orderBy(Order.asc("tech_id"));
			List<Teacher> techers = selector.select();
			dep.setTeachers(techers);
			teachers.removeAll(techers);
		}	
		if(teachers!=null&&teachers.size()>0){
			Department dep = new Department();
			dep.setDep_id(999999);
			dep.setDep_name("未分配用户");
			dep.setTeachers(teachers);
			deps.add(dep);
		}		
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("deps", deps);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/getHeadmasterOrgs",method = RequestMethod.GET)
	public ModelAndView getHeadmasterOrgs(@RequestParam Integer tech_id,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/getHeadmasterOrgs");
		String orgids = this.techRangeService.getHeadmasterOrgsByTechId(tech_id);
		String[] ids = orgids.split("\\,");
		Set<Integer> idss = new HashSet<Integer>();
		for (String id : ids) {
			try {
				idss.add(Integer.parseInt(id));	
			} catch (Exception e) {
				// TODO: handle exception
			}					
		}		
		List<Organization> orgs = this.organizationService.getOrgList();
		for (Organization org : orgs) {
			if(idss.contains(org.getOrg_id())){
				org.setChecked(true);
			}
		}
		mav.addObject("tech_id", tech_id);
		mav.addObject("orgs", orgs);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
//**********************************设置校长开始**********************************
	
	@RequestMapping(value = "/setteaching")
	public ModelAndView setteaching(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setteaching");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setcharge")
	public ModelAndView setcharge(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setcharge");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setgradegroup")
	public ModelAndView setgradegroup(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setgradegroup");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setstudentgroup")
	public ModelAndView setstudentgroup(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setstudentgroup");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setdepartadmin")
	public ModelAndView setdepartadmin(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setdepartadmin");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setteachermanager")
	public ModelAndView setteachermanager(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setteachermanager");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/setfunctionmanager")
	public ModelAndView setfunctionmanager(@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/range/setfunctionmanager");
		Paging<Role> paging = new Paging<Role>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging.setStart(0);
		String roleName = request.getParameter("searchName");
		
		mav.addObject("roles", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	
	@RequestMapping(value = "/saveEdit",method = RequestMethod.POST)
	public @ResponseBody ResultDwz saveEdit(@RequestParam Integer rid,@RequestParam String right_name,@RequestParam String right_code,@RequestParam String right_note){
		ResultDwz result = new ResultDwz();
		
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("right");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	
}
