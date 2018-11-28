package cn.edugate.esb.web.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edugate.esb.Result;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentCard;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.imp.AppImpl;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.CampusFilter;
import cn.edugate.esb.params.filter.ClassesFilter;
import cn.edugate.esb.params.filter.GradeFilter;
import cn.edugate.esb.params.filter.ParentFilter;
import cn.edugate.esb.params.filter.StudentFilter;
import cn.edugate.esb.params.filter.StudentShortFilter;
import cn.edugate.esb.pojo.ExcelCardPojo;
import cn.edugate.esb.pojo.ExcelStudentPojo;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGrade2ClasDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.service.CampusService;
import cn.edugate.esb.service.Class2StudentService;
import cn.edugate.esb.service.ClassesService;
import cn.edugate.esb.service.GradeService;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Base64;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.ExcelUtil;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Controller
@RequestMapping("/api/student")
public class StudentController {
	
	private static Logger logger = Logger.getLogger(StudentController.class);

	private RedisStudentDao redisStudentDao;

	private StudentService studentService;

	private Class2StudentService class2StudentService;

	private RedisCampusDao redisCampusDao;

	private RedisGradeDao redisGradeDao;

	private UserService userService;

	private ParentService parentService;

	private ResourcesService resourcesService;

	private RedisStudentParentDao redisStudentParentDao;
	
	private RedisParentDao redisParentDao;

	private RedisOrgUserDao redisOrgUserDao;

	private Util util;

	private GradeService gradeService;

	private RedisOrganizationDao redisOrganizationDao;

	private ClassesService classesService;

	private CampusService campusService;
	
	private RedisCardDao redisCardDao;	
	
	private RedisGrade2ClasDao redisGrade2ClasDao;

	@Autowired  
    private ApplicationContext ctx;
	
	@Autowired
	public void setRedisGrade2ClasDao(RedisGrade2ClasDao redisGrade2ClasDao) {
		this.redisGrade2ClasDao = redisGrade2ClasDao;
	}

	@Autowired
	public void setRedisCardDao(RedisCardDao redisCardDao) {
		this.redisCardDao = redisCardDao;
	}

	@Autowired
	private RedisClassesDao redisClassesDao;

	@Autowired
	private RedisClassStudentDao redisClassStudentDao;

	@Autowired
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	@Autowired
	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}

	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}

	@Autowired
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setRedisOrgUserDao(RedisOrgUserDao redisOrgUserDao) {
		this.redisOrgUserDao = redisOrgUserDao;
	}

	@Autowired
	public void setRedisParentDao(RedisParentDao redisParentDao) {
		this.redisParentDao = redisParentDao;
	}

	@Autowired
	public void setRedisStudentParentDao(RedisStudentParentDao redisStudentParentDao) {
		this.redisStudentParentDao = redisStudentParentDao;
	}

	@Autowired
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}

	@Autowired
	public void setParentService(ParentService parentService) {
		this.parentService = parentService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRedisGradeDao(RedisGradeDao redisGradeDao) {
		this.redisGradeDao = redisGradeDao;
	}

	@Autowired
	public void setRedisCampusDao(RedisCampusDao redisCampusDao) {
		this.redisCampusDao = redisCampusDao;
	}

	@Autowired
	public void setClass2StudentService(Class2StudentService class2StudentService) {
		this.class2StudentService = class2StudentService;
	}

	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Autowired
	public void setRedisStudentDao(RedisStudentDao redisStudentDao) {
		this.redisStudentDao = redisStudentDao;
	}

	/**
	 * 根据学生主键或机构用户主键查询学生信息
	 * 
	 * @param user_id [选填] 机构用户主键
	 * @param stud_id [选填] 学生主键
	 * @param response
	 * @return 单条学生信息
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudent", method = RequestMethod.GET)
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentFilter.class, target = Student.class),
			@EduJsonFilter(mixin = ClassesFilter.class, target = Classes.class),
			@EduJsonFilter(mixin = GradeFilter.class, target = Grade.class),
			@EduJsonFilter(mixin = CampusFilter.class, target = Campus.class) })
	public Result<Student> getStudent(@RequestParam(value = "stud_id") Integer stud_id, HttpServletResponse response)
			throws IOException {
		Result<Student> result = new Result<Student>();
		Student student = new Student();
		try {
			student = redisStudentDao.getByStudentId(stud_id);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}
		result.setData(student);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getStudentInfo")
	public Result<Student> getStudentInfo(@RequestParam Integer stud_id, @RequestParam Integer org_id,
			HttpServletResponse response) throws IOException {
		Result<Student> result = new Result<Student>();
		Student student = redisStudentDao.getByStudentId(stud_id);
		LSHelper.processInjection(student);

		List<Classes> classList = this.class2StudentService.queryClassesByStudId(stud_id, org_id);
		student.setClassList(classList);

		List<Parent> parents = this.studentService.getParents(stud_id, org_id);
		student.setParents(parents);
		
		List<Card> cards = this.studentService.getCardsByStudentId(student.getStud_id());
		student.setCards(cards);

		result.setData(student);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 根据条件查询学生信息
	 * 
	 * @param org_id [选填]机构主键
	 * @param grade_id [选填]年级主键
	 * @param clas_id [选填]班级主键
	 * @param response
	 * @return 符合条件的学生信息
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudents", method = RequestMethod.GET)
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class),
			@EduJsonFilter(mixin = ClassesFilter.class, target = Classes.class),
			@EduJsonFilter(mixin = GradeFilter.class, target = Grade.class),
			@EduJsonFilter(mixin = CampusFilter.class, target = Campus.class) })
	public Result<List<Student>> getStudents(@RequestParam(value = "org_id", defaultValue = "-1") Integer org_id,
			@RequestParam(value = "grade_id", defaultValue = "-1") Integer grade_id,
			@RequestParam(value = "clas_id", defaultValue = "-1") Integer clas_id, HttpServletResponse response)
			throws IOException {
		Result<List<Student>> result = new Result<List<Student>>();
		Map<String, Student> student = new HashMap<String, Student>();
		List<Student> ls = null;
		try {
			if (-1 != clas_id) {
				// student = redisClassStudentDao.getStudsByCid(clas_id.toString());
				ls = this.studentService.getStudentsByClassId(clas_id);
			} else if (-1 != grade_id) {
				// student = redisStudentDao.getStudentsByGradeId(grade_id,null);
				ls = this.studentService.getStudentsByGradeId(grade_id);
			} else if (-1 != org_id) {
				student = redisStudentDao.getStudentsByOrgId(org_id);
			} else {
				result.setSuccess(false);
				result.setMessage(EnumException.common_params_error.getMsg());
				result.setCode(EnumException.common_params_error.getValue());
				return result;
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}
		if (student != null && student.size() > 0) {
			ls = new ArrayList<Student>(student.values());
		}
		result.setData(ls);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStudentsList")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class)})
	public List<StudentCard> getStudentsList(@RequestParam(value = "org_id") Integer org_id,HttpServletResponse response)
			throws IOException {
		List<StudentCard> ls = this.studentService.getStudentListByOrgId(org_id);		
		return ls;
	}

	@ResponseBody
	@RequestMapping(value = "/getStudentsOfOrgByPage")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class),
			@EduJsonFilter(mixin = ClassesFilter.class, target = Classes.class),
			@EduJsonFilter(mixin = GradeFilter.class, target = Grade.class),
			@EduJsonFilter(mixin = CampusFilter.class, target = Campus.class) })
	public Result<Paging<Student>> getStudentsOfOrgByPage(@RequestParam Integer org_id,
			@RequestParam(defaultValue = "") String search_type, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer numPerPage, HttpServletResponse response)
			throws IOException {
		Result<Paging<Student>> result = new Result<Paging<Student>>();
		Paging<Student> pages = new Paging<Student>();
		if (pageNum == null) {
			pageNum = 1;
		}
		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);

		String stud_name = "";
		String parent_name = "";
		String user_mobile = "";
		switch (search_type) {
			case "0":
				stud_name = keyword;
				break;
			case "1":
				parent_name = keyword;
				break;
			case "2":
				user_mobile = keyword;
				break;
			default:
				break;
		}

		this.studentService.getPaging(org_id, stud_name, parent_name, user_mobile, pages);
		Map<Integer,Campus> campidMap = new HashMap<Integer,Campus>();
		Map<Integer,Grade> gradeMap = new HashMap<Integer,Grade>();
		Map<Integer,Classes> classMap = new HashMap<Integer,Classes>();
		for (Student Student_element : pages.getData()) {
			List<Classes> classList = this.class2StudentService.getClassesByStudId(Student_element.getStud_id().toString());	
			List<Campus> campusList = new ArrayList<Campus>();
			List<Grade> gradeList = new ArrayList<Grade>();
			for (Classes classes : classList) {			
				if(classes!=null){
					if(!campidMap.containsKey(classes.getClas_id())){
						Campus campus = this.redisCampusDao.getById(classes.getCam_id());
						if(campus!=null){
							campidMap.put(classes.getClas_id(), campus);
						}
					}
					campusList.add(campidMap.get(classes.getClas_id()));
					classMap.put(classes.getClas_id(), classes);
					
					if(!gradeMap.containsKey(classes.getClas_id())){
						Integer grade_id = this.redisGrade2ClasDao.getGradeIdByClassId(classes.getClas_id());
						Grade g = this.redisGradeDao.getGradeById(grade_id, null);
						if(g!=null)
							gradeMap.put(classes.getClas_id(), g);
					}
					gradeList.add(gradeMap.get(classes.getClas_id()));
				}
			}
			Student_element.setCampusList(campusList);
			Student_element.setGradeList(gradeList);
			Student_element.setClassList(classList);
		}

		result.setData(pages);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/addNewStudent")
	public Result<String> addNewStudent(@RequestParam Integer org_id, @RequestParam String stud_name, @RequestParam Integer sex,
			@RequestParam Integer cam_id, @RequestParam Integer grade_id, @RequestParam Integer clas_id,
			@RequestParam String parent_name, @RequestParam String relation, @RequestParam String psex,
			@RequestParam String user_mobile, @RequestParam String user_mobile_type, @RequestParam String head,
			@RequestParam String ext, @RequestParam Integer width, @RequestParam Integer height,
			@RequestParam(defaultValue="") String card_no,
			HttpServletRequest request,	HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		String filePathName = "";
		if (!"".equals(head)) {
			byte[] buf = Base64.decode(head);
			InputStream sbs = new ByteArrayInputStream(buf);
			filePathName = this.resourcesService.savePicture(Integer.parseInt(type), Integer.parseInt(user_id), sbs, buf.length,
					ext, width, height);
		}
		
		//添加学生卡
		String[] nos = card_no.split("\\,");
		String message = this.checkCards(nos,null);
		if(message!=null){
			result.setSuccess(false);
			result.setMessage(message);
			result.setCode(204);
			return result;
		}

		// 添加学生机构用户
		OrgUser sorguser = new OrgUser();
		sorguser.setIdentity(Constant.IDENTITY_STUDENT);
		sorguser.setOrg_id(org_id);
		sorguser.setIs_current(1);
		// student.setUser_mobile("");
		sorguser.setUser_loginname(makeLoginname(Constant.IDENTITY_STUDENT, org_id));
		String salt = Utils.makecode();
		sorguser.setUser_salt(salt);
		sorguser.setUser_loginpass(Utils.MD5(salt + ":" + "111111"));
		sorguser.setLoginnum(0);
		sorguser.setStatus((byte) 0);
		sorguser.setInsert_time(new Date());
		sorguser.setIs_del(false);
		this.userService.SaveOrgUser(sorguser);
		// 添加学生
		Student student = new Student();
		student.setOrg_id(org_id);
		student.setUser_id(sorguser.getUser_id());
		student.setStud_name(stud_name);
		student.setSex((byte) sex.intValue());
		student.setHead(filePathName);
		student.setIs_del(0);
		student.setInsert_time(new Date());
		this.studentService.add(student);		
		
		Set<String> noids = new HashSet<String>(Arrays.asList(nos));
		for (String card_noid : noids) {
			if(!StringUtils.isBlank(card_noid)){
				Card card = this.studentService.getCardById(card_noid);
				if(card==null){
					card = new Card();
					card.setCard_no(card_noid);
					card.setInsert_time(new Date());
				}
				card.setStud_id(student.getStud_id());
				card.setUpdate_time(new Date());
				this.studentService.saveCard(card);
			}
		}

		// 添加班级

		Classes classes = redisClassesDao.getClassesById(clas_id.toString(), null);

		Clas2Student cs = new Clas2Student();
		cs.setStud_id(student.getStud_id());
		cs.setClas_name(classes.getClas_name());
		cs.setClas_id(clas_id);
		cs.setInsert_time(new Date());
		cs.setIs_del(false);
		cs.setStud_name(student.getStud_name());
		cs.setUser_id(sorguser.getUser_id());
		cs.setOrg_id(org_id);
		this.class2StudentService.add(cs);

		// 添加家长
		String[] pname = parent_name.split("\\,");
		String[] prelation = relation.split("\\,");
		String[] ppsex = psex.split("\\,");
		String[] puser_mobile = user_mobile.split("\\,");
		String[] puser_mobile_type = user_mobile_type.split("\\,");
		for (int i = 0; i < pname.length; i++) {
			String iname = pname[i];
			String irelation = prelation[i];
			String isex = ppsex[i];
			String imobile = puser_mobile[i];
			String imobile_type = puser_mobile_type[i];
			// 家长机构用户
			// OrgUser porguser = this.userService.getOrgUserByLoginName(imobile, org_id,Constant.IDENTITY_PARENT);
			OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(imobile, org_id, Constant.IDENTITY_PARENT.toString());
			if (porguser == null) {
				porguser = new OrgUser();
			}
			porguser.setIdentity(Constant.IDENTITY_PARENT);
			porguser.setOrg_id(org_id);
			porguser.setIs_current(1);
			porguser.setUser_mobile(imobile);
			porguser.setUser_mobile_type((byte) Integer.parseInt(imobile_type));
			porguser.setUser_loginname(imobile);
			String psalt = Utils.makecode();
			porguser.setUser_salt(psalt);
			porguser.setUser_loginpass(Utils.MD5(psalt + ":" + "111111"));
			porguser.setLoginnum(0);
			porguser.setStatus((byte) 0);
			porguser.setInsert_time(new Date());
			porguser.setIs_del(false);
			this.userService.SaveOrgUser(porguser);
			// 家长用户
			Parent parent = this.parentService.getByMobile(imobile, org_id);
			if (parent == null) {
				parent = new Parent();
			}
			parent.setOrg_id(org_id);
			parent.setUser_id(porguser.getUser_id());
			parent.setParent_name(iname);
			parent.setSex((byte) Integer.parseInt(isex));
			parent.setInsert_time(new Date());
			parent.setIs_del(false);
			this.parentService.addParent(parent);
			// 学生家长关系
			StudentParent sp = new StudentParent();
			sp.setStud_id(student.getStud_id());
			sp.setParent_id(parent.getParent_id());
			sp.setRelation(Integer.parseInt(irelation));
			sp.setInsert_time(new Date());
			sp.setIs_del(false);
			sp.setOrg_id(org_id);
			this.parentService.addStudentParent(sp);
		}
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/editNewStudent")	
	public Result<String> editNewStudent(@RequestParam Integer stud_id,
			@RequestParam Integer org_id,
			@RequestParam String stud_name,
			@RequestParam Integer sex,
			@RequestParam String cam_id,
			@RequestParam String grade_id,
			@RequestParam String clas_id,
			@RequestParam String clas2stud_id,
			@RequestParam String parent_name,
			@RequestParam String relation,
			@RequestParam String psex,
			@RequestParam String user_mobile,
			@RequestParam String user_mobile_type,
			@RequestParam String stud2parent_ids,
			@RequestParam String head,@RequestParam String ext,	
			@RequestParam Integer width,@RequestParam Integer height,	
			@RequestParam String remove_spids,
			@RequestParam(defaultValue="") String card_no,
			HttpServletRequest request,HttpServletResponse response) throws IOException  {		
		Result<String> result = new Result<String>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		String filePathName ="";
		if(!"".equals(head)){
			byte[] buf = Base64.decode(head);
			InputStream sbs = new ByteArrayInputStream(buf); 
			filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),sbs,buf.length,ext,width,height);
		}
		//添加学生
		Student student = this.studentService.getById(stud_id);
		if(stud_id==null){
			result.setMessage("找不到该学生");
			return result;
		}
		student.setStud_name(stud_name);
		student.setSex((byte)sex.intValue());
		if(!"".equals(filePathName)){
			student.setHead(filePathName);
		}
		this.studentService.add(student);
		
		List<Card> cards = this.studentService.getCardsByStudentId(student.getStud_id());
		
		List<Card> removes = new ArrayList<Card>();
		String[] nos = card_no.split("\\,");
		
		String message = this.checkCards(nos,stud_id);
		if(message!=null){
			result.setSuccess(false);
			result.setMessage(message);
			result.setCode(204);
			return result;
		}
		 
		Set<String> noids = new HashSet<String>(Arrays.asList(nos));
		Set<String> noadds = new HashSet<String>();
		if(nos.length>0){
			if(cards!=null){
				for (Card card : cards) {
					if(noids.contains(card.getCard_no())){
						noadds.add(card.getCard_no());
					}else{
						removes.add(card);
					}
				}
			}
		}else{
			removes = cards;
		}
		if(noadds.size()>0){
			noids.removeAll(noadds);
		}
		for (String card_noid : noids) {
			if(!StringUtils.isBlank(card_noid)){
				Card card = this.studentService.getCardById(card_noid);
				if(card==null){
					card = new Card();
					card.setCard_no(card_noid);
					card.setInsert_time(new Date());
				}
				card.setStud_id(stud_id);
				card.setUpdate_time(new Date());
				this.studentService.saveCard(card);
			}
		}
		for (Card card : removes) {
			card.setStud_id(null);
			this.studentService.saveCard(card);
		}
		
		//添加班级
		String[] clas2stud_ids = clas2stud_id.split("\\,");
		String[] cids = clas_id.split("\\,");
		for (int i = 0; i < clas2stud_ids.length; i++) {
			if(!"".equals(cids[i])){
				/*Clas2Student cs = this.class2StudentService.getByClas2stud_id(Integer.parseInt(clas2stud_ids[i]));
				if(cs==null){					
					cs = new Clas2Student();
					cs.setStud_id(student.getStud_id());
					cs.setClas_id(Integer.parseInt(cids[i]));
					cs.setInsert_time(new Date());
					cs.setIs_del(false);
				}else{
					cs.setClas_id(Integer.parseInt(cids[i]));							
				}
				Classes classes = redisClassesDao.getClassesById(clas_id.toString(), null);
				cs.setStud_name(student.getStud_name());
				cs.setUser_id(student.getUser_id());
				cs.setClas_name(classes.getClas_name());
				this.class2StudentService.add(cs);*/
				
				Clas2Student cs = class2StudentService.getClas2Student(student.getStud_id(), Integer.parseInt(cids[i].toString()));
				if (cs == null || null == cs.getClas2stud_id()) {
					class2StudentService.deleteOld(student.getStud_id(), org_id);
					cs = new Clas2Student();
					cs.setStud_id(student.getStud_id());
					cs.setClas_id(Integer.parseInt(cids[i]));
					cs.setInsert_time(new Date());
					cs.setIs_del(false);
					Classes cla = redisClassesDao.getClassesById(cids[i].toString(), null);
					cs.setStud_name(student.getStud_name());
					cs.setUser_id(student.getUser_id());
					cs.setClas_name(cla.getClas_name());
					cs.setOrg_id(org_id);
					this.class2StudentService.add(cs);
				} else {
					cs.setStud_name(stud_name);
					Classes cla = redisClassesDao.getClassesById(cids[i].toString(), null);
					cs.setClas_name(cla.getClas_name());
					this.class2StudentService.add(cs);
				}
			}
		}		
			
		//添加家长
		String[] s2p_ids = stud2parent_ids.split("\\,");
		String[] pname = parent_name.split("\\,");
		String[] prelation = relation.split("\\,");
		String[] ppsex = psex.split("\\,");
		String[] puser_mobile = user_mobile.split("\\,");
		String[] puser_mobile_type = user_mobile_type.split("\\,");
		for (int i = 0; i < puser_mobile.length; i++) {
			String s2p_id = s2p_ids[i];
			String iname = pname[i];
			String irelation = prelation[i];
			String isex = ppsex[i];
			String imobile = puser_mobile[i];
			String imobile_type = puser_mobile_type[i];
			if("0".equals(s2p_id)){
				//家长机构用户
				if(!"".equals(imobile)){
					//OrgUser porguser = this.userService.getOrgUserByLoginName(imobile, org_id,Constant.IDENTITY_PARENT);
					//OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(imobile,org_id,Constant.IDENTITY_PARENT.toString());
					OrgUser porguser = userService.getOrgUser(org_id, Constant.IDENTITY_PARENT, imobile);
					if(porguser==null){
						porguser = new OrgUser();
					}
					porguser.setIdentity(Constant.IDENTITY_PARENT);
					porguser.setOrg_id(org_id);
					porguser.setIs_current(1);
					porguser.setUser_mobile(imobile);
					porguser.setUser_mobile_type((byte)Integer.parseInt(imobile_type));
					porguser.setUser_loginname(imobile);
					String psalt = Utils.makecode();
					porguser.setUser_salt(psalt);
					porguser.setUser_loginpass(Utils.MD5(psalt+":"+"111111"));
					porguser.setLoginnum(0);
					porguser.setStatus((byte)0);
					porguser.setInsert_time(new Date());
					porguser.setIs_del(false);		
					this.userService.SaveOrgUser(porguser);
					//家长用户
					Parent parent = this.parentService.getByMobile(imobile,org_id);
					if(parent==null){
						parent = new Parent();
					}
					parent.setOrg_id(org_id);
					parent.setUser_id(porguser.getUser_id());
					parent.setParent_name(iname);
					parent.setSex((byte)Integer.parseInt(isex));
					parent.setInsert_time(new Date());
					parent.setIs_del(false);
					this.parentService.addParent(parent);
					//学生家长关系
					int count = this.parentService.getCountStudentParent(student.getStud_id(), parent.getParent_id());
					if (count == 0) {
						StudentParent sp = new StudentParent();
						sp.setStud_id(student.getStud_id());
						sp.setParent_id(parent.getParent_id());
						sp.setRelation(Integer.parseInt(irelation));
						sp.setInsert_time(new Date());
						sp.setIs_del(false);
						sp.setOrg_id(org_id);
						this.parentService.addStudentParent(sp);
					}
						
					//this.parentService.updateStudentParent(sp);
				}
			}else{
				/*StudentParent studentParent = this.redisStudentParentDao.getByStud2parent_id(s2p_id);
				studentParent.setRelation(Integer.parseInt(irelation));
				studentParent.setIs_del(false);
				this.studentService.saveStudentParent(studentParent);
				Parent parent = this.redisParentDao.getByParentId(studentParent.getParent_id().toString()); 
				parent.setParent_name(iname);
				parent.setSex((byte)Integer.parseInt(isex));
				parent.setIs_del(false);
				this.parentService.addParent(parent);
				OrgUser orguser = this.redisOrgUserDao.getUserById(parent.getUser_id().toString());
				orguser.setUser_mobile(imobile);
				orguser.setUser_mobile_type((byte)Integer.parseInt(imobile_type));
				this.userService.SaveOrgUser(orguser);	*/
				
				// 已经存在的家长和学生关联
				// 1先删除关联，
				if(!StringUtils.isBlank(s2p_id)){
					studentService.deleteStudent2Parent(Integer.valueOf(s2p_id).intValue());
				}
				// 2然后根据家长手机号码、机构id和身份查询org_user，得到parentid
				if(!StringUtils.isBlank(imobile)){
					OrgUser orgUser = userService.getOrgUser(org_id, Constant.IDENTITY_PARENT, imobile);
					Parent parent = null;
					if (orgUser != null && orgUser.getUser_id().intValue() > 0) {
						orgUser.setUser_mobile_type((byte) Integer.parseInt(imobile_type));
						userService.SaveOrgUser(orgUser);
	
						parent = parentService.getParentByUserID(org_id, orgUser.getUser_id().intValue());
						if (parent != null && parent.getParent_id().intValue() > 0) {
							parent.setParent_name(iname);
							parent.setSex((byte) Integer.parseInt(isex));
							parent.setUpdate_time(new Date());
							parentService.updateParent(parent);
						} else {
							// 新建家长记录
							parent = new Parent();
							parent.setOrg_id(org_id);
							parent.setUser_id(orgUser.getUser_id().intValue());
							parent.setParent_name(iname);
							parent.setSex((byte) Integer.parseInt(isex));
							parent.setInsert_time(new Date());
							parent.setIs_del(false);
							parentService.addParent(parent);
						}
					} else {
						// 人员不存在，进行新建
						orgUser = new OrgUser();
	
						orgUser.setIdentity(Constant.IDENTITY_PARENT);
						orgUser.setOrg_id(org_id);
						orgUser.setIs_current(1);
						orgUser.setUser_mobile(imobile);
						orgUser.setUser_mobile_type((byte) Integer.parseInt(imobile_type));
						orgUser.setUser_loginname(imobile);
						String psalt = Utils.makecode();
						orgUser.setUser_salt(psalt);
						orgUser.setUser_loginpass(Utils.MD5(psalt + ":" + "111111"));
						orgUser.setLoginnum(0);
						orgUser.setStatus((byte) 0);
						orgUser.setInsert_time(new Date());
						orgUser.setIs_del(false);
						userService.SaveOrgUser(orgUser);
	
						// 新建家长记录
						parent = new Parent();
						parent.setOrg_id(org_id);
						parent.setUser_id(orgUser.getUser_id().intValue());
						parent.setParent_name(iname);
						parent.setSex((byte) Integer.parseInt(isex));
						parent.setInsert_time(new Date());
						parent.setIs_del(false);
						parentService.addParent(parent);
	
					}
					// 最后建立家长与学生的关联
					int count = this.parentService.getCountStudentParent(student.getStud_id(), parent.getParent_id());
					if (count == 0) {
						StudentParent sp = new StudentParent();
						sp.setStud_id(student.getStud_id());
						sp.setParent_id(parent.getParent_id());
						sp.setRelation(Integer.parseInt(irelation));
						sp.setInsert_time(new Date());
						sp.setIs_del(false);
						this.parentService.addStudentParent(sp);
					}
				}
			}
		}
		
		String[] rspids = remove_spids.split("\\,");
		for (String rspid : rspids) {
			if(!"".equals(rspid)){
				studentService.deleteStudent2Parent(Integer.valueOf(rspid).intValue());
			}
		}
		
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	private String checkCards(String[] nos, Integer studId) {
		// TODO Auto-generated method stub
		String message = null;
		for (String noid : nos) {			
			Integer stud_id = this.redisCardDao.getStudId(noid);
			if(stud_id!=null&&!stud_id.equals(studId)){
				if(stud_id!=null){
					Student student = this.redisStudentDao.getByStudentId(stud_id);					
					if(student!=null&&student.getStud_id()!=null){
						Map<String, Clas2Student> csmap = this.redisClassStudentDao.getByStud_id(stud_id.toString());
						boolean is_graduated = true;
						for (String clsid : csmap.keySet()) {
							Classes cls = this.redisClassesDao.getClassesById(clsid, null);
							if(cls.getIs_graduated().equals(0)){
								is_graduated = false;
								break;
							}
						}
						if(!is_graduated){
							Organization org = this.redisOrganizationDao.getByOrgId(student.getOrg_id().toString(), null);
							if(message==null){
								message = "";
							}
							message += "卡号:"+noid+"已经绑定在"+org.getOrg_name_cn()+" "+student.getStud_name() +" 名下;\r";
						}
					}
				}
			}			
		}
		return message;
	}

	private String makeLoginname(Integer identity, Integer org_id) {
		// String str = "abcdefghijkmnopqrstuvwsyzABCDEFGHIJKMNOPQRSTUVWSYZ0123456789";
		String str = "0123456789";
		String code = "";
		for (int i = 0; i < 10; i++) {
			Random rand = new Random();
			int j = rand.nextInt(str.length());
			code += str.substring(j, j + 1);
		}

		if (this.userService.isExist(code, identity, org_id)) {
			return makeLoginname(identity, org_id);
		} else {
			return code;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteStudent")
	public Result<String> deleteStudent(@RequestParam Integer stud_id, HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		this.studentService.delete(stud_id);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteStudents")
	public Result<String> deleteStudents(@RequestParam String studIds, HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		this.studentService.deleteStuds(studIds);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/getStudentCount")
	public Result<String> getStudentCount(@RequestParam Integer org_id, HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		long num = this.studentService.getStudentCount(org_id);
		result.setData(String.valueOf(num));
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 校验教师手机号
	 * 
	 * @param depId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkParentPhone")
	public @ResponseBody
	Result<Integer> checkParentPhone(@RequestParam(value = "phone") String phone, @RequestParam(value = "org_id") String orgId,
			@RequestParam(value = "parent_id", defaultValue = "") String parent_id, HttpServletRequest request) {
		Result<Integer> result = new Result<Integer>();
		// List<Department> list = redisDepartmentDao.getDepsByOrgId(orgId.toString());
		int num = parentService.checkParentPhone(phone, orgId, parent_id);
		if (num > 0) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.phone_illegal.getMessage());
			result.setCode(EnumMessage.phone_illegal.getCode());
			return result;
		}
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@RequestMapping(value = "/importCards")
	public @ResponseBody
	Result<String> importCards(@RequestParam Integer org_id, @RequestParam String cards, @RequestParam String filename,
			@RequestParam String ext, HttpServletRequest request) throws Exception {
		Result<String> result = new Result<String>();
		
		byte[] buff = Base64.decode(cards);
		LinkedHashMap<String,String> rmap = new LinkedHashMap<String,String>();
		rmap.put("学校名称*","org_name_cn");
		rmap.put("年级名称*","grade_name");
		rmap.put("班级名称*","clas_name");
		rmap.put("学生姓名*","stud_name");
		rmap.put("学生卡号*","cards");
		InputStream sbs = new ByteArrayInputStream(buff); 
		
		List<ExcelCardPojo> ls = ExcelUtil.excelToList(sbs,"卡一",ExcelCardPojo.class,rmap);
		
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("org_name_cn", "学校名称*");
		map.put("grade_name", "年级名称*");
		map.put("clas_name", "班级名称*");
		map.put("stud_name", "学生姓名*");
		map.put("cards","学生卡号*");
		map.put("message","操作结果*");

//		List<ExcelCardPojo> ls = this.util.getPojosFromJSON(cards, ExcelCardPojo.class);
		List<ExcelCardPojo> templs = ls;
		int total = ls.size();
		if(ls.size()>0){
			Map<String, Organization> orgMap = new HashMap<String, Organization>();
			Map<String, Campus> campusMap = new HashMap<String, Campus>();
			Map<String, Grade> gradeMap = new HashMap<String, Grade>();
			Map<String, Classes> classesMap = new HashMap<String, Classes>();
			//检查学校 年级 班级
			this.checkExcelCards(ls,map,baos,ext,org_id,filename,type,user_id,orgMap,campusMap,gradeMap,classesMap);
			
			List<ExcelCardPojo> errorStudentList = new ArrayList<ExcelCardPojo>();
			List<ExcelCardPojo> exitStudentCardList = new ArrayList<ExcelCardPojo>();
			for (ExcelCardPojo excelCardPojo : ls) {
	//			Organization org = orgMap.get(excelCardPojo.getOrg_name_cn().trim());
	//			Grade grade = gradeMap.get(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name());		
				Classes classitem = classesMap.get(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name()+"_"+excelCardPojo.getClas_name());
				List<Student> students = this.studentService.getStudentByClassIdStudentName(classitem.getClas_id(),excelCardPojo.getStud_name());
	//			Student student = ;
				if(students!=null){
					if(students.size()==1){	
						Student student = students.get(0);
						List<Card> cardls = this.studentService.getCardsByStudentId(student.getStud_id());
						
						List<Card> removes = new ArrayList<Card>();
						String[] nos = excelCardPojo.getCards().split("\\,");
						
						//添加学生卡
						String message = this.checkCards(nos,student.getStud_id());
						if(message!=null){
							excelCardPojo.setMessage(message);
							exitStudentCardList.add(excelCardPojo);
							continue;
						}
						
						Set<String> noids = new HashSet<String>(Arrays.asList(nos));
						Set<String> noadds = new HashSet<String>();
						if(nos.length>0){
							if(cards!=null){
								for (Card card : cardls) {
									if(card.getCard_no().trim().length()!=10){
										excelCardPojo.setMessage("学生卡号要求10位的数字，请检查卡号");
										errorStudentList.add(excelCardPojo);
										continue;
									}
									
									if(noids.contains(card.getCard_no())){
										noadds.add(card.getCard_no());
									}else{
										removes.add(card);
									}
								}
							}
						}else{
							removes = cardls;
						}
						if(noadds.size()>0){
							noids.removeAll(noadds);
						}
						for (String card_noid : noids) {
							if(!StringUtils.isBlank(card_noid)){
								Card card = this.studentService.getCardById(card_noid);							
								if(card==null){
									card = new Card();
									card.setCard_no(card_noid);
									card.setInsert_time(new Date());
								}
								if(card.getCard_no().trim().length()!=10){
									excelCardPojo.setMessage("学生卡号要求10位的数字，请检查卡号");
									errorStudentList.add(excelCardPojo);
									continue;
								}
								card.setStud_id(student.getStud_id());
								card.setUpdate_time(new Date());
								this.studentService.saveCard(card);
							}
						}
						for (Card card : removes) {
							card.setStud_id(null);
							this.studentService.saveCard(card);
						}
					}else if(students.size()==0){
						excelCardPojo.setMessage("该班级中找不到该学生");
						errorStudentList.add(excelCardPojo);
					}else{
						excelCardPojo.setMessage("该班级中具有多个姓名相同的学生，请与老师确认");
						errorStudentList.add(excelCardPojo);
					}
				}else{	
					excelCardPojo.setMessage("该班级中找不到该学生");
					errorStudentList.add(excelCardPojo);
					continue;
				}
			}	
			
			boolean success = true;
			if(errorStudentList.size()>0){
				ExcelUtil.listToExcel(errorStudentList, map, "卡一", baos, "未导入，请核对学生姓名");
				ls.removeAll(errorStudentList);
				success = false;
			}
			if(exitStudentCardList.size()>0){
				ExcelUtil.listToExcel(exitStudentCardList, map, "卡一", baos, "未导入，请核对学生卡号");
				ls.removeAll(exitStudentCardList);
				success = false;
			}
			
			int resttotal = ls.size();
			
			if(success){
				ExcelUtil.listToExcel(templs, map, "卡一", baos, "导入成功");
			}
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,total>resttotal?false:true);
			result.setMessage(rfilePathName);
			result.setSuccess(!(total>resttotal));
			result.setMessage(total>resttotal?"导入失败，请检查信息":EnumMessage.success.getMessage());
			result.setCode(total>resttotal?400:EnumMessage.success.getCode());
		}else{
			ExcelUtil.listToExcel(templs, map, "卡一", baos, "导入失败");
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,false);
			result.setMessage(rfilePathName);
			result.setSuccess(false);
			result.setMessage("导入失败:表头信息错误或者excel中没有数据");
			result.setCode(400);
		}
		
		
		return result;
	}

	private Boolean checkExcelCards(List<ExcelCardPojo> ls,
			LinkedHashMap<String, String> map, ByteArrayOutputStream baos,
			String ext, Integer org_id, String filename, String type,
			String user_id, Map<String, Organization> orgMap,
			Map<String, Campus> campusMap, Map<String, Grade> gradeMap,
			Map<String, Classes> classesMap) {
		// TODO Auto-generated method stub
		Boolean is_checked = true;
		List<ExcelCardPojo> errorOrgList = new ArrayList<ExcelCardPojo>();
		List<ExcelCardPojo> errorGradeList = new ArrayList<ExcelCardPojo>();
		List<ExcelCardPojo> errorClassesList = new ArrayList<ExcelCardPojo>();
		for (ExcelCardPojo excelCardPojo : ls) {
			if(!orgMap.containsKey(excelCardPojo.getOrg_name_cn().trim())){
				Organization org = this.redisOrganizationDao.getByOrgName(excelCardPojo.getOrg_name_cn().trim());
				if(org!=null&&org.getOrg_id().equals(org_id)){
					orgMap.put(excelCardPojo.getOrg_name_cn().trim(), org);
				}else{
					excelCardPojo.setMessage("未导入，请核对学校名称");
					errorOrgList.add(excelCardPojo);
					continue;
				}
			}
			if(!gradeMap.containsKey(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name())){					
				Grade grade = this.gradeService.getByName(org_id,excelCardPojo.getGrade_name().trim());
				if(grade!=null){
					gradeMap.put(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name(), grade);
				}else{
					excelCardPojo.setMessage("未导入，请核对年级名称");
					errorGradeList.add(excelCardPojo);
					continue;
				}
			}
			Grade grade = gradeMap.get(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name());
			if(!classesMap.containsKey(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name()+"_"+excelCardPojo.getClas_name())){
				Classes classitem = this.classesService.getClassByName(grade.getGrade_id(),excelCardPojo.getClas_name().trim());
				if(classitem!=null){
					classesMap.put(excelCardPojo.getOrg_name_cn().trim()+"_"+excelCardPojo.getGrade_name()+"_"+excelCardPojo.getClas_name(), classitem);
				}else{
					excelCardPojo.setMessage("未导入，请核对班级名称");
					errorClassesList.add(excelCardPojo);
					continue;
				}				
			}	
		}
		
		
		try {	
			if(errorOrgList.size()>0){
				is_checked = false;
				ExcelUtil.listToExcel(errorOrgList, map, "卡一", baos, "未导入，请核对学校名称");
				ls.removeAll(errorOrgList);
			}
			if(errorGradeList.size()>0){
				is_checked = false;
				ExcelUtil.listToExcel(errorGradeList, map, "卡一", baos, "未导入，请核对年级名称");
				ls.removeAll(errorGradeList);
			}
			if(errorClassesList.size()>0){
				is_checked = false;
				ExcelUtil.listToExcel(errorClassesList, map, "卡一", baos, "未导入，请核对班级名称");
				ls.removeAll(errorClassesList);
			}
		} catch (Exception e) {
			logger.error("生成excel失败:"+e.getMessage());
		}		
		
		return is_checked;
	}	
	
	@RequestMapping(value = "/importGradStudents")
	public @ResponseBody
	Result<String> importGradStudents(@RequestParam Integer org_id, @RequestParam String students, @RequestParam String filename,
			@RequestParam String ext, HttpServletRequest request) throws Exception {
		Result<String> result = new Result<String>();
		
		byte[] buff = Base64.decode(students);
		LinkedHashMap<String,String> rmap = new LinkedHashMap<String,String>();
		rmap.put("学校名称(*)","org_name_cn");
		rmap.put("年级名称(*)","grade_name");
		rmap.put("班级名称(*)","clas_name");
		rmap.put("学生姓名(*)","stud_name");
		rmap.put("学生性别","ssex");
		
		rmap.put("家长1姓名(*)","parent_name_0");
		rmap.put("家长1性别(*)","psex_0");
		rmap.put("家长1亲子关系(*)","relation_0");
		rmap.put("家长1手机号(*)","user_mobile_0");
		rmap.put("家长1号码类型(*)","user_mobile_type_0");
		
		rmap.put("家长2姓名(*)","parent_name_1");
		rmap.put("家长2性别(*)","psex_1");
		rmap.put("家长2亲子关系(*)","relation_1");
		rmap.put("家长2手机号(*)","user_mobile_1");
		rmap.put("家长2号码类型(*)","user_mobile_type_1");
		
		rmap.put("是否已验证","verifyed");
		InputStream sbs = new ByteArrayInputStream(buff); 
		
		List<ExcelStudentPojo> ls = ExcelUtil.excelToList(sbs,"卡一",ExcelStudentPojo.class,rmap);
		
		
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("org_name_cn", "学校名称(*)");
		map.put("grade_name", "年级名称(*)");
		map.put("clas_name", "班级名称(*)");
		map.put("stud_name", "学生姓名(*)");
		map.put("ssex", "学生性别");

		map.put("parent_name_0", "家长1姓名(*)");
		map.put("relation_0", "家长1亲子关系(*)");
		map.put("psex_0", "家长1性别(*)");
		map.put("user_mobile_0", "家长1手机号(*)");
		map.put("user_mobile_type_0", "家长1号码类型(*)");

		map.put("parent_name_1", "家长2姓名(*)");
		map.put("relation_1", "家长2亲子关系(*)");
		map.put("psex_1", "家长2性别(*)");
		map.put("user_mobile_1", "家长2手机号(*)");
		map.put("user_mobile_type_1", "家长2号码类型(*)");
		map.put("verifyed", "是否已验证");
		map.put("message", "操作结果");

//		List<ExcelStudentPojo> ls = this.util.getPojosFromJSON(students, ExcelStudentPojo.class);
		
		if(ls.size()>0){
		
			Map<String, Organization> orgMap = new HashMap<String, Organization>();
			Map<String, Campus> campusMap = new HashMap<String, Campus>();
			Map<String, Grade> gradeMap = new HashMap<String, Grade>();
			Map<String, Classes> classesMap = new HashMap<String, Classes>();
			//检查学校 年级 班级
			String filePathName = this.checkExcelStudents(ls,map,baos,ext,org_id,filename,type,user_id,orgMap,campusMap,gradeMap,classesMap);
			if(filePathName!=null){
				result.setSuccess(false);
				result.setMessage("导入失败，请查看上传记录");
				result.setCode(400);
				return result;
			}
	
			Map<String, Student> studentMap = new HashMap<String, Student>();
			List<ExcelStudentPojo> sameStudentNameList = new ArrayList<ExcelStudentPojo>();
			List<ExcelStudentPojo> errorGradeClassList = new ArrayList<ExcelStudentPojo>();
			for (ExcelStudentPojo excelStudentPojo : ls) {
				String message = "";
				Grade grade = gradeMap.get(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name());			
				Classes classitem = classesMap.get(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name()+"_"+excelStudentPojo.getClas_name());			
				Grade2Clas gc = this.gradeService.getGrade2Clas(grade.getGrade_id(), classitem.getClas_id());
				if (gc == null) {
	//				gc = new Grade2Clas();
	//				gc.setGrade_id(grade.getGrade_id());
	//				gc.setClas_id(classitem.getClas_id());
	//				gc.setInsert_time(new Date());
	//				gc.setIs_del(false);
	//				this.gradeService.addGrade2Clas(gc);
	//				message += "系统中没有这个年级和班级的对应，已自动对应;";
					excelStudentPojo.setMessage("系统中没有这个年级和班级的对应");
					errorGradeClassList.add(excelStudentPojo);
					continue;	
				}
	
				// 添加学生
				Set<String> mobiles = new HashSet<String>();
				if (!"".equals(excelStudentPojo.getUser_mobile_0())) {
					mobiles.add(excelStudentPojo.getUser_mobile_0());
				}
				if (!"".equals(excelStudentPojo.getUser_mobile_1())) {
					mobiles.add(excelStudentPojo.getUser_mobile_1());
				}
				Student student = null;
				
				if (!studentMap.containsKey(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0())) {
					List<Student> sameNameStudents = this.studentService.getByName(org_id, excelStudentPojo.getStud_name());
					student = sameNameStudents!=null&&sameNameStudents.size()>0?sameNameStudents.get(0):null;
					if (student == null||"是".equals(excelStudentPojo.getVerifyed())) {
						// 添加学生机构用户
						OrgUser sorguser = new OrgUser();
						sorguser.setIdentity(Constant.IDENTITY_STUDENT);
						sorguser.setOrg_id(org_id);
						sorguser.setIs_current(1);
						// student.setUser_mobile("");
						sorguser.setUser_loginname(makeLoginname(Constant.IDENTITY_STUDENT, org_id));
						String salt = Utils.makecode();
						sorguser.setUser_salt(salt);
						sorguser.setUser_loginpass(Utils.MD5(salt + ":" + "111111"));
						sorguser.setLoginnum(0);
						sorguser.setStatus((byte) 0);
						sorguser.setInsert_time(new Date());
						sorguser.setIs_del(false);
						this.userService.SaveOrgUser(sorguser);
						student = new Student();
						student.setUser_id(sorguser.getUser_id());
						student.setInsert_time(new Date());
						student.setIs_del(0);
						student.setOrg_id(org_id);
						message += "学生添加成功;";
					}else{
						List<Student> gstudents = this.studentService.getGradStudentsByName(org_id, excelStudentPojo.getStud_name());
						if(gstudents!=null){			
							boolean isGradStudent = false;
							for (Student gst : gstudents) {
								List<Parent> parents = this.studentService.getParents(gst.getStud_id(), gst.getOrg_id());							
								for (Parent parent : parents) {
									if(excelStudentPojo.getUser_mobile_0()!=null){
										if(excelStudentPojo.getUser_mobile_0().equals(parent.getMobile())){
											student = gst;
											Clas2Student cs = this.class2StudentService.getClas2Student(student.getStud_id(), student.getClas_id());
											if(cs!=null){
												cs.setDel_time(new Date());
												cs.setIs_del(true);												
												this.class2StudentService.add(cs);
											}
											isGradStudent = true;
											break;
										}
									}
									if(excelStudentPojo.getUser_mobile_1()!=null){
										if(excelStudentPojo.getUser_mobile_1().equals(parent.getMobile())){
											student = gst;
											Clas2Student cs = this.class2StudentService.getClas2Student(student.getStud_id(), student.getClas_id());
											if(cs!=null){
												cs.setDel_time(new Date());
												cs.setIs_del(true);
												this.class2StudentService.add(cs);
											}
											isGradStudent = true;
											break;
										}
									}
								}
								if(isGradStudent){
									break;
								}
							}
							if(!isGradStudent){
								String esmessage = "姓名已存在:";
								for (Student stu : sameNameStudents) {
									Map<String, Clas2Student> clsMap = this.redisClassStudentDao.getByStud_id(stu.getStud_id().toString());
									for (String clas_id : clsMap.keySet()) {
										Classes cls = this.redisClassesDao.getClassesById(clas_id, null);
										Grade grd = this.classesService.getGradeByClassId(cls.getClas_id());
										esmessage += grd.getGrade_name()+" ## "+cls.getClas_name()+";";
									}
								}
								excelStudentPojo.setMessage(esmessage);
								sameStudentNameList.add(excelStudentPojo);
								continue;
							}
						}else{
							String esmessage = "";
							for (Student stu : sameNameStudents) {
								Map<String, Clas2Student> clsMap = this.redisClassStudentDao.getByStud_id(stu.getStud_id().toString());
								for (String clas_id : clsMap.keySet()) {
									Classes cls = this.redisClassesDao.getClassesById(clas_id, null);
									Grade grd = this.classesService.getGradeByClassId(cls.getClas_id());
									esmessage += grd.getGrade_name()+"-"+cls.getClas_name()+";";
								}
							}
							excelStudentPojo.setMessage(esmessage);
							sameStudentNameList.add(excelStudentPojo);
							continue;
						}
					}
					student.setStud_name(excelStudentPojo.getStud_name());
					student.setSex(Utils.getSexByStr(excelStudentPojo.getSsex()));
					this.studentService.add(student);
	
					studentMap.put(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0(), student);
				} else {
					student = studentMap.get(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0());
				}
	
				Clas2Student cst = this.class2StudentService.getClas2Student(student.getStud_id(), classitem.getClas_id());
				if (cst == null) {
					cst = new Clas2Student();
					cst.setClas_id(classitem.getClas_id());
					cst.setClas_name(classitem.getClas_name());
					cst.setStud_id(student.getStud_id());
					cst.setInsert_time(new Date());
					cst.setIs_del(false);
	
					cst.setStud_name(student.getStud_name());
					cst.setUser_id(student.getUser_id());
					cst.setOrg_id(org_id);
					this.class2StudentService.add(cst);
					message += "学生加入班级成功;";
				}
				// 添加家长
				Class<ExcelStudentPojo> clazz = ExcelStudentPojo.class;
				for (Integer i = 0; i < 2; i++) {
					Method mgetParent_name = clazz.getDeclaredMethod("getParent_name_" + i.toString());
					Method mgetRelation = clazz.getDeclaredMethod("getRelation_" + i.toString());
					Method mgetPsex = clazz.getDeclaredMethod("getPsex_" + i.toString());
					Method mgetUser_mobile = clazz.getDeclaredMethod("getUser_mobile_" + i.toString());
					Method mgetUser_mobile_type = clazz.getDeclaredMethod("getUser_mobile_type_" + i.toString());
					String iname = (String) mgetParent_name.invoke(excelStudentPojo);
					String irelation = (String) mgetRelation.invoke(excelStudentPojo);
					String isex = (String) mgetPsex.invoke(excelStudentPojo);
					String imobile = (String) mgetUser_mobile.invoke(excelStudentPojo);
					String imobile_type = (String) mgetUser_mobile_type.invoke(excelStudentPojo);
					// 家长机构用户
					if (!"".equals(imobile)) {
						// OrgUser porguser = this.userService.getOrgUserByLoginName(imobile, org_id,Constant.IDENTITY_PARENT);
						OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(imobile, org_id, Constant.IDENTITY_PARENT.toString());
						if (porguser == null) {
							porguser = new OrgUser();
						}
						porguser.setIdentity(Constant.IDENTITY_PARENT);
						porguser.setOrg_id(org_id);
						porguser.setIs_current(1);
						porguser.setUser_mobile(imobile);
						porguser.setUser_mobile_type(Utils.getSexByStr(imobile_type));
						porguser.setUser_loginname(imobile);
						String psalt = Utils.makecode();
						porguser.setUser_salt(psalt);
						porguser.setUser_loginpass(Utils.MD5(psalt + ":" + "111111"));
						porguser.setLoginnum(0);
						porguser.setStatus((byte) 0);
						porguser.setInsert_time(new Date());
						porguser.setIs_del(false);
						this.userService.SaveOrgUser(porguser);
						// 家长用户
						Parent parent = this.parentService.getByMobile(imobile, org_id);
						if (parent == null) {
							parent = new Parent();
							message += "家长添加成功;";
						}
						parent.setOrg_id(org_id);
						parent.setUser_id(porguser.getUser_id());
						parent.setParent_name(iname);
						parent.setSex(Utils.getSexByStr(isex));
						parent.setInsert_time(new Date());
						parent.setIs_del(false);
						this.parentService.addParent(parent);
						// 学生家长关系
						StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), parent.getParent_id());
						if (sp == null) {
							sp = new StudentParent();
							message += "学生家长关系对应成功;";
						}
						sp.setOrg_id(org_id);
						sp.setStud_id(student.getStud_id());
						sp.setParent_id(parent.getParent_id());
						sp.setRelation(Utils.getRelationByStr(irelation));
						sp.setInsert_time(new Date());
						sp.setIs_del(false);
						this.parentService.addStudentParent(sp);
					}
	
				}
				if ("".equals(message)) {
					message = "成功";
				}
				excelStudentPojo.setMessage(message);
			}
			boolean success = true;
			String message = EnumMessage.success.getMessage();
			if(errorGradeClassList.size()>0){
				ExcelUtil.listToExcel(errorGradeClassList, map, "机构用户", baos, "年级班级对应错误，未导入，请联系老师确认");
				success = false;
				message = "年级班级对应错误;";
			}
			if(sameStudentNameList.size()>0){
				ExcelUtil.listToExcel(sameStudentNameList, map, "机构用户", baos, "该学校存在姓名相同的学生，未导入，请联系老师确认");
				success = false;
				if(EnumMessage.success.getMessage().equals(message)){
					message = "有重复的姓名;";
				}else{
					message += "有重复的姓名;";
				}
			}
			if(success){
				ExcelUtil.listToExcel(ls, map, "机构用户", baos, "处理成功");
			}
			
			
	//		if(sameStudentNameList.size()>0){
	//			ExcelUtil.listToExcel(sameStudentNameList, map, "机构用户", baos, "该学校存在姓名相同的学生，未导入，请联系老师确认");
	//		}else{
	//			ExcelUtil.listToExcel(ls, map, "机构用户", baos, "处理成功");
	//		}
			
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1, success);
			result.setMessage(rfilePathName);
			result.setSuccess(success);
			result.setMessage(message);
			result.setCode(success?EnumMessage.success.getCode():400);
		}else{
			ExcelUtil.listToExcel(ls, map, "卡一", baos, "导入失败");
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,false);
			result.setMessage(rfilePathName);
			result.setSuccess(false);
			result.setMessage("导入失败:表头信息错误或者excel中没有数据");
			result.setCode(400);
		}
		return result;
	}

	@RequestMapping(value = "/importStudents")
	public @ResponseBody
	Result<String> importStudents(@RequestParam Integer org_id, @RequestParam String students, @RequestParam String filename,
			@RequestParam String ext, HttpServletRequest request) throws Exception {
		Result<String> result = new Result<String>();
		
		byte[] buff = Base64.decode(students);
		LinkedHashMap<String,String> rmap = new LinkedHashMap<String,String>();
		rmap.put("学校名称(*)","org_name_cn");
		rmap.put("年级名称(*)","grade_name");
		rmap.put("班级名称(*)","clas_name");
		rmap.put("学生姓名(*)","stud_name");
		rmap.put("学生性别","ssex");
		
		rmap.put("家长1姓名(*)","parent_name_0");
		rmap.put("家长1性别(*)","psex_0");
		rmap.put("家长1亲子关系(*)","relation_0");
		rmap.put("家长1手机号(*)","user_mobile_0");
		rmap.put("家长1号码类型(*)","user_mobile_type_0");
		
		rmap.put("家长2姓名(*)","parent_name_1");
		rmap.put("家长2性别(*)","psex_1");
		rmap.put("家长2亲子关系(*)","relation_1");
		rmap.put("家长2手机号(*)","user_mobile_1");
		rmap.put("家长2号码类型(*)","user_mobile_type_1");
		
		rmap.put("是否已验证","verifyed");
		InputStream sbs = new ByteArrayInputStream(buff); 
		
		List<ExcelStudentPojo> ls = ExcelUtil.excelToList(sbs,"卡一",ExcelStudentPojo.class,rmap);
		
		
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("org_name_cn", "学校名称(*)");
		map.put("grade_name", "年级名称(*)");
		map.put("clas_name", "班级名称(*)");
		map.put("stud_name", "学生姓名(*)");
		map.put("ssex", "学生性别");

		map.put("parent_name_0", "家长1姓名(*)");
		map.put("relation_0", "家长1亲子关系(*)");
		map.put("psex_0", "家长1性别(*)");
		map.put("user_mobile_0", "家长1手机号(*)");
		map.put("user_mobile_type_0", "家长1号码类型(*)");

		map.put("parent_name_1", "家长2姓名(*)");
		map.put("relation_1", "家长2亲子关系(*)");
		map.put("psex_1", "家长2性别(*)");
		map.put("user_mobile_1", "家长2手机号(*)");
		map.put("user_mobile_type_1", "家长2号码类型(*)");
		map.put("verifyed", "是否已验证");
		map.put("message", "操作结果");

//		List<ExcelStudentPojo> ls = this.util.getPojosFromJSON(students, ExcelStudentPojo.class);
		if(ls.size()>0){
			Map<String, Organization> orgMap = new HashMap<String, Organization>();
			Map<String, Campus> campusMap = new HashMap<String, Campus>();
			Map<String, Grade> gradeMap = new HashMap<String, Grade>();
			Map<String, Classes> classesMap = new HashMap<String, Classes>();
			//检查学校 年级 班级
			String filePathName = this.checkExcelStudents(ls,map,baos,ext,org_id,filename,type,user_id,orgMap,campusMap,gradeMap,classesMap);
			if(filePathName!=null){
				result.setSuccess(false);
				result.setMessage("导入失败，请查看上传记录");
				result.setCode(400);
				return result;
			}
	
			Map<String, Student> studentMap = new HashMap<String, Student>();
			List<ExcelStudentPojo> sameStudentNameList = new ArrayList<ExcelStudentPojo>();
			List<ExcelStudentPojo> errorGradeClassList = new ArrayList<ExcelStudentPojo>();				
			for (ExcelStudentPojo excelStudentPojo : ls) {
				String message = "";
				Grade grade = gradeMap.get(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name());			
				Classes classitem = classesMap.get(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name()+"_"+excelStudentPojo.getClas_name());			
				Grade2Clas gc = this.gradeService.getGrade2Clas(grade.getGrade_id(), classitem.getClas_id());
				if (gc == null) {
					excelStudentPojo.setMessage("系统中没有这个年级和班级的对应");
					errorGradeClassList.add(excelStudentPojo);
					continue;				
				}
	
				// 添加学生
				Set<String> mobiles = new HashSet<String>();
				if (!"".equals(excelStudentPojo.getUser_mobile_0())) {
					mobiles.add(excelStudentPojo.getUser_mobile_0());
				}
				if (!"".equals(excelStudentPojo.getUser_mobile_1())) {
					mobiles.add(excelStudentPojo.getUser_mobile_1());
				}
				Student student = null;
				if (!studentMap.containsKey(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0())) {
					List<Student> sameNameStudents = this.studentService.getByName(org_id, excelStudentPojo.getStud_name());
					student = sameNameStudents!=null&&sameNameStudents.size()>0?sameNameStudents.get(0):null;
					if (student == null||"是".equals(excelStudentPojo.getVerifyed())) {
						// 添加学生机构用户
						OrgUser sorguser = new OrgUser();
						sorguser.setIdentity(Constant.IDENTITY_STUDENT);
						sorguser.setOrg_id(org_id);
						sorguser.setIs_current(1);
						// student.setUser_mobile("");
						sorguser.setUser_loginname(makeLoginname(Constant.IDENTITY_STUDENT, org_id));
						String salt = Utils.makecode();
						sorguser.setUser_salt(salt);
						sorguser.setUser_loginpass(Utils.MD5(salt + ":" + "111111"));
						sorguser.setLoginnum(0);
						sorguser.setStatus((byte) 0);
						sorguser.setInsert_time(new Date());
						sorguser.setIs_del(false);
						this.userService.SaveOrgUser(sorguser);
						student = new Student();
						student.setUser_id(sorguser.getUser_id());
						student.setInsert_time(new Date());
						student.setIs_del(0);
						student.setOrg_id(org_id);
						message += "学生添加成功;";
					}else{
						String esmessage = "姓名已存在:";
						for (Student stu : sameNameStudents) {
							Map<String, Clas2Student> clsMap = this.redisClassStudentDao.getByStud_id(stu.getStud_id().toString());
							for (String clas_id : clsMap.keySet()) {
								Classes cls = this.redisClassesDao.getClassesById(clas_id, null);
								Grade grd = this.classesService.getGradeByClassId(cls.getClas_id());
								esmessage += grd.getGrade_name()+" ## "+cls.getClas_name()+";";
							}
						}
						excelStudentPojo.setMessage(esmessage);
						sameStudentNameList.add(excelStudentPojo);
						continue;
					}
					student.setStud_name(excelStudentPojo.getStud_name());
					student.setSex(Utils.getSexByStr(excelStudentPojo.getSsex()));
					this.studentService.add(student);
	
					studentMap.put(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0(), student);
				} else {
					student = studentMap.get(excelStudentPojo.getStud_name() + "_" + excelStudentPojo.getUser_mobile_0());
				}
	
				Clas2Student cst = this.class2StudentService.getClas2Student(student.getStud_id(), classitem.getClas_id());
				if (cst == null) {
					cst = new Clas2Student();
					cst.setClas_id(classitem.getClas_id());
					cst.setStud_id(student.getStud_id());
					cst.setClas_name(classitem.getClas_name());					
					cst.setInsert_time(new Date());
					cst.setIs_del(false);	
					cst.setStud_name(student.getStud_name());
					cst.setUser_id(student.getUser_id());
					cst.setOrg_id(org_id);
					this.class2StudentService.add(cst);
					
					message += "学生加入班级成功;";
				}
				// 添加家长
				Class<ExcelStudentPojo> clazz = ExcelStudentPojo.class;
				for (Integer i = 0; i < 2; i++) {
					Method mgetParent_name = clazz.getDeclaredMethod("getParent_name_" + i.toString());
					Method mgetRelation = clazz.getDeclaredMethod("getRelation_" + i.toString());
					Method mgetPsex = clazz.getDeclaredMethod("getPsex_" + i.toString());
					Method mgetUser_mobile = clazz.getDeclaredMethod("getUser_mobile_" + i.toString());
					Method mgetUser_mobile_type = clazz.getDeclaredMethod("getUser_mobile_type_" + i.toString());
					String iname = (String) mgetParent_name.invoke(excelStudentPojo);
					String irelation = (String) mgetRelation.invoke(excelStudentPojo);
					String isex = (String) mgetPsex.invoke(excelStudentPojo);
					String imobile = (String) mgetUser_mobile.invoke(excelStudentPojo);
					String imobile_type = (String) mgetUser_mobile_type.invoke(excelStudentPojo);
					// 家长机构用户
					if (!"".equals(imobile)) {
						// OrgUser porguser = this.userService.getOrgUserByLoginName(imobile, org_id,Constant.IDENTITY_PARENT);
						OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(imobile, org_id, Constant.IDENTITY_PARENT.toString());
						if (porguser == null) {
							porguser = new OrgUser();
						}
						porguser.setIdentity(Constant.IDENTITY_PARENT);
						porguser.setOrg_id(org_id);
						porguser.setIs_current(1);
						porguser.setUser_mobile(imobile);
						porguser.setUser_mobile_type(Utils.getSexByStr(imobile_type));
						porguser.setUser_loginname(imobile);
						String psalt = Utils.makecode();
						porguser.setUser_salt(psalt);
						porguser.setUser_loginpass(Utils.MD5(psalt + ":" + "111111"));
						porguser.setLoginnum(0);
						porguser.setStatus((byte) 0);
						porguser.setInsert_time(new Date());
						porguser.setIs_del(false);
						this.userService.SaveOrgUser(porguser);
						// 家长用户
						Parent parent = this.parentService.getByMobile(imobile, org_id);
						if (parent == null) {
							parent = new Parent();
							message += "家长添加成功;";
						}
						parent.setOrg_id(org_id);
						parent.setUser_id(porguser.getUser_id());
						parent.setParent_name(iname);
						parent.setSex(Utils.getSexByStr(isex));
						parent.setInsert_time(new Date());
						parent.setIs_del(false);
						this.parentService.addParent(parent);
						// 学生家长关系
						StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), parent.getParent_id());
						if (sp == null) {
							sp = new StudentParent();
							message += "学生家长关系对应成功;";
						}
						sp.setOrg_id(org_id);
						sp.setStud_id(student.getStud_id());
						sp.setParent_id(parent.getParent_id());
						sp.setRelation(Utils.getRelationByStr(irelation));
						sp.setInsert_time(new Date());
						sp.setIs_del(false);
						this.parentService.addStudentParent(sp);
					}
	
				}
				if ("".equals(message)) {
					message = "成功";
				}
				excelStudentPojo.setMessage(message);
			}
			boolean success = true;
			String message = EnumMessage.success.getMessage();
			if(errorGradeClassList.size()>0){
				ExcelUtil.listToExcel(errorGradeClassList, map, "机构用户", baos, "年级班级对应错误，未导入，请联系老师确认");
				success = false;
				message = "年级班级对应错误;";
			}
			if(sameStudentNameList.size()>0){
				ExcelUtil.listToExcel(sameStudentNameList, map, "机构用户", baos, "该学校存在姓名相同的学生，未导入，请联系老师确认");
				success = false;
				if(EnumMessage.success.getMessage().equals(message)){
					message = "有重复的姓名;";
				}else{
					message += "有重复的姓名;";
				}
			}
			if(success){
				ExcelUtil.listToExcel(ls, map, "机构用户", baos, "处理成功");
			}
			
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1, success);
			result.setMessage(rfilePathName);
			result.setSuccess(success);
			result.setMessage(message);
			result.setCode(success?EnumMessage.success.getCode():400);	
		}else{
			ExcelUtil.listToExcel(ls, map, "卡一", baos, "导入失败");
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,false);
			result.setMessage(rfilePathName);
			result.setSuccess(false);
			result.setMessage("导入失败:表头信息错误或者excel中没有数据");
			result.setCode(400);
		}
		return result;
	}

	private String checkExcelStudents(List<ExcelStudentPojo> ls, LinkedHashMap<String, String> map, ByteArrayOutputStream baos, String ext, Integer org_id, String filename, String type, String user_id, Map<String, Organization> orgMap, Map<String, Campus> campusMap, Map<String, Grade> gradeMap, Map<String, Classes> classesMap) throws Exception {
		// TODO Auto-generated method stub
		String filePathName = null;
		if (ls.size() <= 0) {
			ls.add(new ExcelStudentPojo());
			ExcelUtil.listToExcel(ls, map, "批量导入学生", baos, "数据源中没有任何数据");
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			filePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin,
					b.length, ext, org_id, filename, (byte) 1, false);			
		}else{			
			for (ExcelStudentPojo excelStudentPojo : ls) {
				if(!orgMap.containsKey(excelStudentPojo.getOrg_name_cn().trim())){
					Organization org = this.redisOrganizationDao.getByOrgName(excelStudentPojo.getOrg_name_cn().trim());
					if (org == null || !org_id.equals(org.getOrg_id())) {
						ExcelUtil.listToExcel(ls, map, "批量导入学生", baos, excelStudentPojo.getOrg_name_cn()+" 引发错误,请检查学校名称是否与当前学校一致");
						byte[] b = baos.toByteArray();
						InputStream fin = new ByteArrayInputStream(b);
						filePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin,
								b.length, ext, org_id, filename, (byte) 1, false);
						return filePathName;
					}else{
						orgMap.put(excelStudentPojo.getOrg_name_cn().trim(), org);
						if(!campusMap.containsKey(excelStudentPojo.getOrg_name_cn().trim())){
							List<Campus> campus = this.campusService.getByOrgId(org_id);
							if (campus.size() > 0) {
								campusMap.put(excelStudentPojo.getOrg_name_cn().trim(), campus.get(0));
							} else {
								Campus current = new Campus();
								current.setOrg_id(org_id);
								current.setCam_type(0);
								current.setCam_name("主校区");
								current.setInsert_time(new Date());
								current.setIs_del(0);
								this.campusService.add(current);
								campusMap.put(excelStudentPojo.getOrg_name_cn().trim(), current);
							}
						}
					}
				}
//				Organization org = orgMap.get(excelStudentPojo.getOrg_name_cn().trim());
				Campus campus = campusMap.get(excelStudentPojo.getOrg_name_cn().trim());
				if(!gradeMap.containsKey(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name())){					
					Grade grade = this.gradeService.getByName(org_id,excelStudentPojo.getGrade_name().trim());
					if(grade==null){
						ExcelUtil.listToExcel(ls, map, "批量导入学生", baos, excelStudentPojo.getGrade_name().trim()+" 年级名称引发错误,请检查学校年级是否存在");
						byte[] b = baos.toByteArray();
						InputStream fin = new ByteArrayInputStream(b);
						filePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin,
								b.length, ext, org_id, filename, (byte) 1, false);
						return filePathName;
					}else{
						gradeMap.put(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name(), grade);
					}
				}
				Grade grade = gradeMap.get(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name());
				
				if(!classesMap.containsKey(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name()+"_"+excelStudentPojo.getClas_name())){
					Classes classitem = this.classesService.getClassByName(grade.getGrade_id(),excelStudentPojo.getClas_name().trim());
					if(classitem==null){
						ExcelUtil.listToExcel(ls, map, "批量导入学生", baos, excelStudentPojo.getClas_name().trim()+" 班级名称引发错误,请检查学校班级是否存在");
						byte[] b = baos.toByteArray();
						InputStream fin = new ByteArrayInputStream(b);
						filePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin,
								b.length, ext, org_id, filename, (byte) 1, false);
						return filePathName;
					}
					classesMap.put(excelStudentPojo.getOrg_name_cn().trim()+"_"+excelStudentPojo.getGrade_name()+"_"+excelStudentPojo.getClas_name(), classitem);
				}				
			}			
		}
		return filePathName;
	}

	@RequestMapping(value = "/getUploadRecord")
	@ResponseBody
	public Result<List<ExcelRes>> getUploadRecord(@RequestParam(value = "type") String type,
			@RequestParam(value = "orgID") String orgID, @RequestParam(value = "userID") String userID,
			@RequestParam(value = "proce_type") String proce_type) {

		// 按照类型查询教师或者学生的上传记录
		List<ExcelRes> excelResList = resourcesService.getExcelResList(Integer.valueOf(type), Integer.valueOf(userID),
				Integer.valueOf(orgID), Integer.valueOf(proce_type));

		Result<List<ExcelRes>> result = new Result<List<ExcelRes>>();
		result.setData(excelResList);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());

		return result;
	}

	/**
	 * 根据学生主键获取所有家长的信息
	 * 
	 * @param stud_id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getParentPhone")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ParentFilter.class, target = Parent.class) })
	public Result<List<Parent>> getParentPhone(@RequestParam(value = "stud_id") String stud_id, HttpServletResponse response)
			throws IOException {
		Result<List<Parent>> result = new Result<List<Parent>>();
		try {
			// List<Parent> list = parentService.getByStudId(stud_id);
			List<Parent> list = redisStudentParentDao.getParentsBySId(stud_id);
			result.setData(list);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getStudentsByCid", method = RequestMethod.GET)
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class),
			@EduJsonFilter(mixin = ClassesFilter.class, target = Classes.class),
			@EduJsonFilter(mixin = GradeFilter.class, target = Grade.class),
			@EduJsonFilter(mixin = CampusFilter.class, target = Campus.class) })
	public Result<List<Clas2Student>> getStudentsByCid(@RequestParam(value = "clas_id", defaultValue = "-1") Integer clas_id,
			HttpServletResponse response) throws IOException {
		Result<List<Clas2Student>> result = new Result<List<Clas2Student>>();
		List<Clas2Student> ls = null;
		try {
			if (-1 != clas_id) {
				ls = class2StudentService.getClas2StudentByClassId(clas_id);
				for (Clas2Student cs : ls) {
					if(cs.getIs_bind().indexOf("已关注")>-1){
						cs.setIs_bind("已关注");
					}else{
						cs.setIs_bind("未关注");
					}
				}
//						redisClassStudentDao.getStudsByCid(clas_id.toString());
			} else {
				result.setSuccess(false);
				result.setMessage(EnumException.common_params_error.getMsg());
				result.setCode(EnumException.common_params_error.getValue());
				return result;
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}
		result.setData(ls);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@RequestMapping(value = "/wxupdateStudent", method = RequestMethod.POST)
	public @ResponseBody
	Result<Student> wxupdateStudent(HttpServletRequest request) {

		Result<Student> result = new Result<Student>();
		Student student = new Student();
		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String head = util.upload(multipartRequest, "head");
			if (StringUtils.isNotEmpty(head)) {
				student.setHead(head);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		// 返回结果
		result.setData(student);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getStudentsByRange", method = RequestMethod.POST)
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class),
			@EduJsonFilter(mixin = ClassesFilter.class, target = Classes.class),
			@EduJsonFilter(mixin = GradeFilter.class, target = Grade.class),
			@EduJsonFilter(mixin = CampusFilter.class, target = Campus.class) })
	public Result<List<Clas2Student>> getStudentsByRange(@RequestBody Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Result<List<Clas2Student>> result = new Result<List<Clas2Student>>();

		try {
			// String range = request.getParameter("range");
			// range="{\"c\":[78839,78836],\"s\":[2299,2302],\"g\":[1027,1028,1034,1035,1036,908]}";
			// if (StringUtils.isEmpty(range)) {
			// throw new EsbException(EnumException.common_must_login);
			// }
			Map<String, Object> smap = new HashMap<String, Object>();
			Map<String, Object> cmap = new HashMap<String, Object>();
			Map<String, Object> gmap = new HashMap<String, Object>();
			if (param.get("s") != null) {
				@SuppressWarnings("unchecked")
				List<Object> slist = (List<Object>) param.get("s");
				for (int i = 0; i < slist.size(); i++) {
					String s_id = slist.get(i).toString();
					smap.put(s_id, null);
				}
			}
			if (param.get("c") != null) {
				@SuppressWarnings("unchecked")
				List<Object> clist = (List<Object>) param.get("c");
				for (int i = 0; i < clist.size(); i++) {
					String c_id = clist.get(i).toString();
					cmap.put(c_id, null);
				}
			}
			if (param.get("g") != null) {
				@SuppressWarnings("unchecked")
				List<Object> glist = (List<Object>) param.get("g");
				for (int i = 0; i < glist.size(); i++) {
					String g_id = glist.get(i).toString();
					gmap.put(g_id, null);
				}
			}
			// 查询多年级数据
			Map<String, Grade> mapGrade = redisGradeDao.getGrades(gmap);
			// 获取多年级下班级信息+多班级ids的班级信息
			Map<String, Classes> clasMap = redisClassesDao.getClassesOfGIds(mapGrade, cmap);
			List<Clas2Student> studList = redisClassStudentDao.getStudsByCids(clasMap, smap);

			result.setData(studList);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());

		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		return result;
	}


	@RequestMapping(value = "/updateParent", method = RequestMethod.POST)
	public @ResponseBody
	Result<String> updateParent(HttpServletRequest request,
			@RequestParam(value = "user_id") String user_id,
			@RequestParam(value = "parent_name") String parent_name) {
		Result<String> result = new Result<String>();
		Parent parent = redisParentDao.getParentsByUserId(user_id);
		if(null!=parent&&null!=parent.getParent_id()){
			parent.setParent_name(parent_name);
			parentService.updateParent(parent);
			result.setCode(EnumMessage.success.getCode());
			result.setMessage(EnumMessage.success.getMessage());
			result.setSuccess(true);
		}else{
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
	public @ResponseBody
	Result<String> updateStudent(HttpServletRequest request,
			@RequestParam(value = "stud_id") String stud_id,
			@RequestParam(value = "parent_id", defaultValue="") String parent_id,
			@RequestParam(value = "gender", defaultValue="") String gender,
			@RequestParam(value = "relation", defaultValue="") String relation,
			@RequestParam(value = "head", defaultValue="") String head) {
		Result<String> result = new Result<String>();
		//更新亲子关系
		if(StringUtils.isNotBlank(parent_id)&&StringUtils.isNotBlank(relation)){
			List<StudentParent> list = redisStudentParentDao.getStudParentsByPId(parent_id);
			for (StudentParent sp : list) {
				if(sp.getStud_id().toString().equals(stud_id)){
					sp.setRelation(Integer.parseInt(relation));
					studentService.saveStudentParent(sp);
				}
			}
		}
		Student student = redisStudentDao.getByStudentId(Integer.parseInt(stud_id));
		if(StringUtils.isNotBlank(gender)||StringUtils.isNotBlank(head)){
			if(StringUtils.isNotBlank(gender)){
				student.setSex(Byte.parseByte(gender));
			}
			if(StringUtils.isNotBlank(head)){
				student.setHead(head);
			}
			studentService.update(student);
		}
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/updateCards")
	public @ResponseBody
	Result<String> updateCards(@RequestParam Integer org_id, @RequestParam String cards, @RequestParam String filename,
			@RequestParam String ext, HttpServletRequest request) throws Exception {
		Result<String> result = new Result<String>();
		
		byte[] buff = Base64.decode(cards);
		LinkedHashMap<String,String> rmap = new LinkedHashMap<String,String>();
		rmap.put("学生ID(*)","stud_id");
		rmap.put("学校名称(*)","org_name_cn");
		rmap.put("年级名称(*)","grade_name");
		rmap.put("班级名称(*)","clas_name");
		rmap.put("学生姓名(*)","stud_name");
		rmap.put("学生卡号(*)","card_no");
		InputStream sbs = new ByteArrayInputStream(buff); 
		
		List<StudentCard> ls = ExcelUtil.excelToList(sbs,"卡一",StudentCard.class,rmap);
		
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("stud_id","学生ID(*)");
		map.put("org_name_cn","学校名称(*)");
		map.put("grade_name","年级名称(*)");
		map.put("clas_name","班级名称(*)");
		map.put("stud_name","学生姓名(*)");
		map.put("card_no","学生卡号(*)");
		map.put("message","操作结果(*)");

//		List<Student> ls = this.util.getPojosFromJSON(cards, Student.class);
		
		if(ls.size()>0){
			List<StudentCard> templs = ls;
			int total = ls.size();
			
			List<StudentCard> errorStudentList = new ArrayList<StudentCard>();
			List<StudentCard> exitStudentCardList = new ArrayList<StudentCard>();
			for (StudentCard excelCardPojo : ls) {
				Student student = this.studentService.getById(excelCardPojo.getStud_id());
				if(student!=null&&student.getStud_id()!=null){
					if(org_id.equals(student.getOrg_id())){
						List<Card> cardls = this.studentService.getCardsByStudentId(student.getStud_id());
						
						List<Card> removes = new ArrayList<Card>();
						String[] nos = excelCardPojo.getCard_no().split("\\,");
						if(!StringUtils.isBlank(excelCardPojo.getCard_no())){
							boolean checked = true;
							for (String eno : nos) {
								if(eno.trim().length()!=10){
									excelCardPojo.setMessage("学生卡号要求10位的数字，请检查卡号");
									exitStudentCardList.add(excelCardPojo);
									checked = false;
									break;
								}
							}
							if(!checked){
								continue;
							}
						}
						
						//添加学生卡
						String message = this.checkCards(nos,student.getStud_id());
						if(message!=null){
							excelCardPojo.setMessage(message);
							exitStudentCardList.add(excelCardPojo);
							continue;
						}
						
						Set<String> noids = new HashSet<String>(Arrays.asList(nos));
						Set<String> noadds = new HashSet<String>();
						if(nos.length>0){
							if(cards!=null){
								for (Card card : cardls) {
									if(noids.contains(card.getCard_no())){
										noadds.add(card.getCard_no());
									}else{
										removes.add(card);
									}
								}
							}
						}else{
							removes = cardls;
						}
						if(noadds.size()>0){
							noids.removeAll(noadds);
						}
						for (String card_noid : noids) {
							if(!StringUtils.isBlank(card_noid)){
								Card card = this.studentService.getCardById(card_noid);					
								if(card==null){
									card = new Card();
									card.setCard_no(card_noid);
									card.setInsert_time(new Date());
								}
	//							if(card.getCard_no().trim().length()!=10){
	//								excelCardPojo.setMessage("学生卡号要求10位的数字，请检查卡号");
	//								errorStudentList.add(excelCardPojo);
	//								continue;
	//							}
								card.setStud_id(student.getStud_id());
								card.setUpdate_time(new Date());
								this.studentService.saveCard(card);
							}
						}
						for (Card card : removes) {
							card.setStud_id(null);
							this.studentService.saveCard(card);
						}
					}else{
						excelCardPojo.setMessage("该学生不在当前学校");
						errorStudentList.add(excelCardPojo);
					}
				}else{
					excelCardPojo.setMessage("学生ID错误，请检查学生ID");
					errorStudentList.add(excelCardPojo);
				}
			}		
			boolean success = true;
			if(errorStudentList.size()>0){
				ExcelUtil.listToExcel(errorStudentList, map, "卡一", baos, "未导入，学生ID错误或不在当前学校");
				ls.removeAll(errorStudentList);
				success = false;
			}
			if(exitStudentCardList.size()>0){
				ExcelUtil.listToExcel(exitStudentCardList, map, "卡一", baos, "未导入，请核对学生卡号");
				ls.removeAll(exitStudentCardList);
				success = false;
			}
			
			int resttotal = ls.size();
			
			if(success){
				ExcelUtil.listToExcel(templs, map, "卡一", baos, "更新成功");
			}
			
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,total>resttotal?false:true);
			result.setMessage(rfilePathName);
			result.setSuccess(!(total>resttotal));
			result.setMessage(total>resttotal?"更新失败，请检查信息":EnumMessage.success.getMessage());
			result.setCode(total>resttotal?400:EnumMessage.success.getCode());
		}else{
			ExcelUtil.listToExcel(ls, map, "卡一", baos, "导入失败");
			byte[] b = baos.toByteArray();
			InputStream fin = new ByteArrayInputStream(b);
			String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
					ext, org_id, filename, (byte) 1,false);
			result.setMessage(rfilePathName);
			result.setSuccess(false);
			result.setMessage("导入失败:表头信息错误或者excel中没有数据");
			result.setCode(400);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getStudsAndClasByOrgId")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = StudentShortFilter.class, target = Student.class)})
	public Result<List<StudentCard>> getStudsAndClasByOrgId(@RequestParam(value = "org_id") Integer org_id,HttpServletResponse response)
			throws IOException {
		Result<List<StudentCard>> result = new Result<List<StudentCard>>();
		
		List<StudentCard> ls = this.studentService.getStudsAndClasByOrgId(org_id);	
		result.setData(ls);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}
	
	
	@RequestMapping(value = "/uploadExcelStudsAndClas")
	public @ResponseBody
	Result<String> uploadExcelStudsAndClas(@RequestParam Integer org_id,   @RequestParam String studs,@RequestParam String filename,
			@RequestParam String ext, HttpServletRequest request) throws Exception {
		Result<String> result = new Result<String>();
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("stud_id","学生ID(*)");
		map.put("oldclas_name","原始班级名称");
		map.put("stud_name","学生姓名");
		map.put("clas_name","新班级名称*");
		map.put("message","操作结果(*)");

		List<StudentCard> studList = this.util.getPojosFromJSON(studs, StudentCard.class);
		List<StudentCard> templs = studList;
		int total = studList.size();
		
		List<StudentCard> errorStudentList = new ArrayList<StudentCard>();
		for (StudentCard studPojo : studList) {
			
			if(studPojo.getStud_id()==null){
				studPojo.setMessage("学生ID错误，不能为空");
				errorStudentList.add(studPojo);
				continue;
			}
			if(StringUtils.isBlank(studPojo.getClas_name())){
				studPojo.setMessage("新班级名称错误，不能为空");
				errorStudentList.add(studPojo);
				continue;
			}
			
			//Student student = this.studentService.getById(excelCardPojo.getStud_id());
			Student student = redisStudentDao.getByStudentId(studPojo.getStud_id());
			if(student!=null&&student.getStud_id()!=null){
								
				if(org_id.equals(student.getOrg_id())){
					//新班级名称 获取班级信息
					Classes classes = classesService.getClassByNameOrg(org_id,studPojo.getClas_name());
					//学生所在班级
					int clas_id=0;
					Map<String, Clas2Student> csMap = redisClassStudentDao.getByStud_id(student.getStud_id().toString());
					for (Entry<String, Clas2Student> entry : csMap.entrySet()) {
						 Clas2Student  obj = entry.getValue();
						 clas_id = obj.getClas_id();
					}

					if(classes!=null){
						//学生所在班级 与 新班级不相同
						if(classes.getClas_id().intValue()!=clas_id){
							
							// 手动控制事务
							HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
							DefaultTransactionDefinition def = new DefaultTransactionDefinition();
							def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
							TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
							int saveOrUpdateCount = 0;
							
							try{
								//删除学生与老班级关系
								class2StudentService.deleteOld(student.getStud_id(), org_id);
								saveOrUpdateCount++;
								
								Clas2Student clas2Student = new Clas2Student();
								clas2Student.setClas_id(classes.getClas_id());
								clas2Student.setStud_id(student.getStud_id());
								clas2Student.setInsert_time(new Date());
								clas2Student.setIs_del(false);
								class2StudentService.add(clas2Student);
								saveOrUpdateCount++;
								
								// 所有处理完成后，提交事务
								txManager.commit(txStatus);
							}catch(Exception e){
								e.printStackTrace();
								studPojo.setMessage("方法执行中出现异常！");
								errorStudentList.add(studPojo);
								// 根据saveOrUpdateCount变量的值来确定是否需要进行回滚操作
								if (saveOrUpdateCount > 0) {
									txManager.rollback(txStatus);
								}
							}
						}
					}else{
						studPojo.setMessage("新班级名称错误，没有找到改班级");
						errorStudentList.add(studPojo);
					}
					
				}else{
					studPojo.setMessage("该学生不在当前学校");
					errorStudentList.add(studPojo);
				}
			}else{
				studPojo.setMessage("学生ID错误，请检查学生ID");
				errorStudentList.add(studPojo);
			}
		}		
		boolean success = true;
		//错误集合 里 有数据
		if(errorStudentList.size()>0){
			ExcelUtil.listToExcel(errorStudentList, map, "数据集合", baos, "未导入，请核对信息");
			studList.removeAll(errorStudentList);
			success = false;
		}
		//删除错误数据后 的 总条数
		int resttotal = studList.size();
		
		if(success){
			ExcelUtil.listToExcel(templs, map, "数据集合", baos, "更新成功");
		}
		
		byte[] b = baos.toByteArray();
		InputStream fin = new ByteArrayInputStream(b);
		String rfilePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin, b.length,
				ext, org_id, filename, (byte) 1,total>resttotal?false:true);
		result.setMessage(rfilePathName);
		result.setSuccess(!(total>resttotal));
		result.setMessage(total>resttotal?"更新失败，请检查信息":EnumMessage.success.getMessage());
		result.setCode(total>resttotal?400:EnumMessage.success.getCode());
		return result;
	}
	
	
	
	
}
