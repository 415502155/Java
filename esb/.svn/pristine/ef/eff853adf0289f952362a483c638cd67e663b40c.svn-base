package cn.edugate.esb.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.comparator.TechToSortComparator;
import cn.edugate.esb.dao.DepartmentDao;
import cn.edugate.esb.dao.IClassesDao;
import cn.edugate.esb.dao.IGradeDao;
import cn.edugate.esb.dao.IGroupDao;
import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.dao.IOrgUserDao;
import cn.edugate.esb.dao.IRoleDao;
import cn.edugate.esb.dao.ITeacherDao;
import cn.edugate.esb.dao.ITeacherRoleDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisOrgTreeDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.redis.dao.imp.RedisTeacherRoleDaoImp;
import cn.edugate.esb.service.GroupMemberService;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.ImportExcelUtil;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

@Component
@Transactional("transactionManager")
public class TeacherServiceImpl implements TeacherService {

	private static Logger logger = Logger.getLogger(TeacherServiceImpl.class);
	
	private static String[] ColumnName4ImportTeacher = { "教师组名称", "教师姓名*", "教师卡号", "教师性别", "教师生日", "民族", "手机号码*", "号码类型*", "教师住址", "备注", "用户角色", "所在部门", "证件类型", "证件号码" };

	@Autowired
	private IOrgUserDao orgUserDao;

	@Autowired
	private ITeacherRoleDao teacherRoleDao;

	@Autowired
	private TechRangeService techRangeService;

	@Autowired
	private UserService userService;

	@Autowired
	private ITechRangeDao techRangeDao;

	@Autowired
	private RedisTechRangeDao redisTechRangeDao;

	@Autowired
	private RedisTeacherDao redisTeacherDao;

	@Autowired
	private GroupMemberService groupMemberService;

	@Autowired
	private ITeacherDao teacherDao;

	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	@Autowired
	private TeacherRoleService teacherRoleService;

	@Autowired
	private IGroupMemberDao groupMemberDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private IGroupDao groupDao;
	
	@Autowired  
    private ApplicationContext ctx;
	
	@Autowired
	private RedisOrgTreeDao redisOrgTreeDao;
	
	@Autowired
	private RedisDepartmentDao redisDepartmentDao;
	
	@Autowired
	private RedisGroupMemberDao redisGroupMemberDao;
	
	private ResourcesService resourcesService;
	
	@Autowired
	private RedisCourseDao courseDao;
	
	@Autowired
	private IGradeDao gradeDao;
	
	@Autowired
	private  ITechRangeDao rangeDao;
	
	@Autowired
	private IClassesDao classesDao;
	
	private EduConfig eduConfig;
	
	@Autowired
	private RedisTeacherRoleDaoImp redisTeacherRole;
	
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	
	@Autowired
	private RedisTeacherRoleDao redisTeacherRoleDao;
	
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}
	
	
	@Autowired
	private IRoleDao roleDao;
	
	

	@Override
	public int add(Teacher teacher) {
		teacher.setIs_del(0);
		teacher.setInsert_time(new Date());
		try {
			if (null != teacher.getOrg_id()) {
				Organization org = redisOrganizationDao.getByOrgId(teacher.getOrg_id().toString(), null);
				if(null!=org){
					teacher.setOrg_name(org.getOrg_name_cn());
				}
			}

			// OrgUser user = new OrgUser();
			// user.setUser_loginname(teacher.getUser_mobile());
			// user.setOrg_id(teacher.getOrg_id());
			// String salt = Utils.makecode();
			// user.setUser_salt(salt);
			// user.setUser_loginpass(Utils.MD5(salt+":"+"111111"));
			// //user.setUser_id(user_idnumber);
			// user.setUser_mobile(teacher.getUser_mobile());
			// user.setInsert_time(new Date());
			// user.setIs_del(false);
			// user.setUser_mobile_type((byte)teacher.getUser_mobile_type().intValue());
			// user.setIdentity(Constant.IDENTITY_TEACHER);
			// user.setIs_current(Constant.CURRENTYES);
			OrgUser user = teacher.getUser();

			if(this.userService.getUserIsExist(user.getUser_loginname(), user.getUser_mobile(), Constant.IDENTITY_TEACHER, user.getOrg_id())){			
				this.userService.SaveOrgUser(user);	
				teacher.setUser_id(user.getUser_id());
				teacherDao.save(teacher);
				
			}else{
				return -1;
			}
		} catch (Exception e) {
			logger.error("create Teacher error");
		}
		return 0;
	}

	@Override
	public Integer delete(int id) {
		Teacher teacher = getTechById(id);
		teacher.setIs_del(1);
		teacher.setDel_time(new Date());
		try {
			// Teacher teacher = redisTeacherDao.getByTechId(tech_id, null);
			// 删除教师数据范围
			List<TechRange> trList = redisTechRangeDao.getAllOfTechId(id);
			for (TechRange techRange : trList) {
				techRange.setIs_del(Constant.IS_DEL_YES);
				techRange.setDel_time(new Date());
				// techRangeService.deleteRange(techRange);
				techRangeDao.deleteRange(techRange);
			}
			// 删除组成员
			// groupMemberService.deleteByMemberId(id+"",Constant.GROUPMEMBER_TYPE_TEACHER);
			groupMemberDao.deleteByMemberId(id + "", Constant.GROUPMEMBER_TYPE_TEACHER);
			// 删除教师角色
			// teacherRoleService.deleteByUserId(teacher.getUser_id());
			Criterion criterion = Restrictions.eq("tech_id", teacher.getUser_id());
			this.teacherRoleDao.delete(TeacherRole.class, criterion);

			// 修改教师
			teacherDao.update(teacher);

			// userService.deleteOrgUser(teacher.getUser_id());
			OrgUser orgUser = orgUserDao.get(OrgUser.class, teacher.getUser_id());
			orgUser.setIs_del(true);
			orgUser.setDel_time(new Date());
			orgUserDao.update(orgUser);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete Teacher error");
		}
		return null;
	}

	@Override
	public void update(Teacher teacher) {
//		teacher.setUpdate_time(new Date());
//		OrgUser user = teacher.getUser();		
//		this.userService.updateOrgUser(user);				
		teacherDao.update(teacher);
	}

	@Override
	public Teacher update(List<Teacher> list) {
		for (Teacher teacher : list) {
			teacher.setUpdate_time(new Date());
		}
		try {
			teacherDao.update((Teacher[]) list.toArray(new Teacher[list.size()]));
		} catch (Exception e) {
			logger.error("update Teacher error");
		}
		return null;
	}

	@Override
	public Teacher getTechById(int id) {
		return teacherDao.get(Teacher.class, id);
	}

	@Override
	public List<Teacher> getAll() {
		return teacherDao.getAll();
	}

	@Override
	public Paging<Teacher> getTechListWithPaging(Paging<Teacher> paging, int org_id, int type, String seach_name) {
		int total = this.teacherDao.getTotalCount(org_id, type, seach_name);
		paging.setTotal(total);
		this.teacherDao.getPaging(org_id, type, seach_name, paging);
		return null;
	}

	@Override
	public List<Teacher> getTeacherSearch(int org_id, String sname) {
		return teacherDao.getTeacherSearch(org_id, sname);
	}

	@Override
	public Teacher getTechAndUserId(int id) {
		return teacherDao.getTechAndUserId(id);
	}

	@Override
	public void getPaging(String org_id, String role_id, String tech_name, String user_mobile, Paging<Teacher> pages) {
		Long total = this.teacherDao.getTotalCount(org_id, role_id, tech_name, user_mobile);
		pages.setTotal(total);
		this.teacherDao.getPaging(org_id, role_id, tech_name, user_mobile, pages);
	}

	@Override
	public List<Object> getOrgTreeTeacherMembers(String org_id, String group_id) {
		try {
			String ids = "";
			LinkedHashMap<String, Teacher> maps = redisGroupMemberDao.getTeacherMembers(group_id);
			if(maps!=null){
				for (String key : maps.keySet()) {
					Teacher teacher = maps.get(key);
					ids = ids+teacher.getTech_id()+",";
				}
			}
			
			List<Organization> orgs = redisOrgTreeDao.getOrgsByTreeId(Integer.parseInt(org_id));
			List<Object> list = new ArrayList<Object>();
			for(int i=0;i<orgs.size();i++){
				Organization organization = orgs.get(i);
				List<Department> deplist = redisDepartmentDao.getDepsByOrgId(organization.getOrg_id().toString());			
				for (Department dep : deplist) {  					
					List<Teacher> techList = redisTeacherDao.getTechsByDepId(dep.getDep_id()+"",null);
					TechToSortComparator mc = new TechToSortComparator();  
			        Collections.sort(techList,mc);
					if(dep.getDep_id()!=null){
						for (Teacher teacher : techList) {
							List<Object> list1 = new ArrayList<Object>();
							list1.add(teacher.getTech_id());
							list1.add(teacher.getTech_name());
							list1.add(organization.getOrg_id());
							list1.add(organization.getOrg_name_cn());
							list1.add(dep.getDep_id());
							list1.add(dep.getDep_name());
							if(ids.indexOf(teacher.getTech_id().toString())!=-1)
								list1.add(group_id);
							else
								list1.add("");
							list.add(list1);
						} 
					}
				}
			}
			return list;
				
			//return teacherDao.getOrgTreeTeacherMembers(org_id, group_id);
		} catch (Exception e) {
			logger.error("getOrgTreeTeacherMembers Teacher error");
		}
		return null;
	}

	@Override
	public void batchAdd(List<Teacher> teacherList) {
		if (teacherList != null && teacherList.size() > 0) {
			for (Teacher t : teacherList) {
				this.add(t);
			}
		}
	}

	@Override
	public List<String[]> validateAndSaveTeacher(Integer orgID, List<String[]> excelRowArray) {
		List<String[]> resultArray = new ArrayList<String[]>();

		for (int i = 0; i < excelRowArray.size(); i++) {

			String[] t = excelRowArray.get(i);
			// 用于保存错误信息的数组
			String[] excelRow = new String[t.length + 1];
			int errorIdx = t.length;
			// 第一行标题默认为:0"教师组名称", 1"教师姓名*", 2"教师卡号", 3"教师性别", 4"教师生日", 5"民族", 6"手机号码*", 7"号码类型*", 8"教师住址", 9"备注", 10"用户角色",
			// 11"所在部门", 12"证件类型", 13"证件号码"
			excelRow[0] = t[0];
			excelRow[1] = t[1];
			excelRow[2] = t[2];
			excelRow[3] = t[3];
			excelRow[4] = t[4];
			excelRow[5] = t[5];
			excelRow[6] = t[6];
			excelRow[7] = t[7];
			excelRow[8] = t[8];
			excelRow[9] = t[9];
			excelRow[10] = t[10];
			excelRow[11] = t[11];
			excelRow[12] = t[12];
			excelRow[13] = t[13];
			excelRow[14] = ""; // 错误信息

			try {
				if (StringUtils.isNotBlank(excelRow[0])) {
					if (excelRow[0].length() > 20) {
						throw new Exception("教师组名称字数应在20字以内！");
					} else {
						// 检查填写的组名称是否存在
						int count = groupDao.checkName(orgID, excelRow[0]);
						if (count > 1) {
							throw new Exception("名称为【" + excelRow[0] + "】的组存在多个，请联系技术人员！");
						}
					}
				}

				if (!(StringUtils.isNotBlank(excelRow[1]) && excelRow[1].length() <= 10)) {
					throw new Exception("教师名称字数应在10字以内！");
				}

				if (StringUtils.isNotBlank(excelRow[3])) {
					if (!("男".equals(excelRow[3]) || "女".equals(excelRow[3]))) {
						throw new Exception("教师性别为选填，若填写则填写男或女");
					}
				}

				if (StringUtils.isNotBlank(excelRow[4])) {
					// 年月日表达式对象
					Pattern datePattern = Pattern
							.compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$");
					if (!datePattern.matcher(excelRow[4]).matches()) {
						throw new Exception("教师生日为选填，若填写请按照 1985-02-01格式填写！");
					}
				}

				Pattern mobilePattern = Pattern.compile("^1[0-9][0-9]\\d{8}$");
				if (!(StringUtils.isNotBlank(excelRow[6]) && mobilePattern.matcher(excelRow[6]).matches())) {
					throw new Exception("手机号码为必填项，并请输入正确格式的手机号码！");
				}

				if (!(StringUtils.isNotBlank(excelRow[7]) && ("移动".equals(excelRow[7]) || "联通".equals(excelRow[7])
						|| "电信".equals(excelRow[7]) || "非津号码".equals(excelRow[7])))) {
					throw new Exception("号码类型为必填项，且输入内容应为：移动、联通、电信、非津号码！");
				}

				if (StringUtils.isNotBlank(excelRow[10])) {
					// 填写了角色，将其中中文逗号替换成英文
					excelRow[10] = excelRow[10].replaceAll("，", ",");
				}

				if (StringUtils.isNotBlank(excelRow[11])) {
					// 部门不为空，检查部门是否存在，默认部门理论上每个机构都有并且只有一个，所以不用检查
					if (!"默认".equals(excelRow[11])) {
						// 在非默认部门中查询是否存在这个部门
						int count = departmentDao.checkName(orgID, excelRow[11]);
						if (count == 0) {
							throw new Exception("名称为【" + excelRow[11] + "】的部门未找到！");
						} else if (count > 1) {
							throw new Exception("名称为【" + excelRow[11] + "】的部门存在多个，请联系技术人员！");
						}
					} else {
						Department dp = departmentDao.getDefaultDeptEntity(orgID);
						if (dp == null || StringUtils.isBlank(dp.getDep_name())) {
							throw new Exception("机构下的默认部门未找到！");
						}
					}
				}

				if (StringUtils.isNotBlank(excelRow[12])) {
					if (!("居民身份证".equals(excelRow[12]) || "军官证".equals(excelRow[12]) || "士兵证".equals(excelRow[12])
							|| "文职干部证".equals(excelRow[12]) || "部队离退休证".equals(excelRow[12])
							|| "香港特区护照/身份证明".equals(excelRow[12]) || "澳门特区护照/身份证明".equals(excelRow[12])
							|| "台湾居民来往大陆通行证".equals(excelRow[12]) || "境外永久居住证".equals(excelRow[12]) || "护照".equals(excelRow[12]))) {
						throw new Exception(
								"如果填写证件，请选择：居民身份证、军官证、士兵证、文职干部证、部队离退休证、香港特区护照/身份证明、澳门特区护照/身份证明、台湾居民来往大陆通行证、境外永久居住证、护照中的一种！");
					}
				}

			} catch (Exception ex) {
				excelRow[errorIdx] = ex.getMessage();
			}
			
			
			// 不存在错误则进行数据库保存
			if (StringUtils.isBlank(excelRow[errorIdx])) {
				// 手动控制事务
				HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
				TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
				int saveOrUpdateCount = 0;
				
				try {
					// 通过基本验证
					//OrgUser ou = orgUserDao.getOrgUser(orgID, 1, excelRow[6]);
					OrgUser ou = redisOrgUserDao.getUserByLoginNameWX(excelRow[6],new Integer(orgID),Constant.IDENTITY_TEACHER.toString());
					if (ou == null || ou.getUser_id().intValue() == 0) {
						// 保存组
						int groupID = 0;
						if (StringUtils.isNotBlank(excelRow[0])) {
							// 写了分组名称时才进行处理
							int count = groupDao.checkName(orgID, excelRow[0]);
							if (count > 1) {
								throw new Exception("名称为【" + excelRow[0] + "】的组存在多个，请联系技术人员！");
							} else if (count == 0) {
								// 新建组
								Group g = new Group();
								g.setGroup_name(Utils.getValue(excelRow[0]));
								g.setOrg_id(orgID);
								g.setGroup_type(2);
								g.setIs_del(0);
								g.setInsert_time(new Date());
								g.setUpdate_time(new Date());
								groupDao.save(g);
								saveOrUpdateCount++;
								groupID = g.getGroup_id();
							} else if (count == 1) {
								Group g = groupDao.getGroupEntity(orgID, Utils.getValue(excelRow[0]));
								groupID = g.getGroup_id();
							}
						}
						
						// 不存在这个人，进行新建
						ou = new OrgUser();
						ou.setOrg_id(orgID);
						ou.setIdentity(1);
						ou.setIs_current(0);
						ou.setUser_idnumber(Utils.getValue(excelRow[13]));
						ou.setUser_loginname(Utils.getValue(excelRow[6]));
						String user_salt = Utils.makecode();// ((Math.random()*(9999-1000+1))+1000)+"";
						ou.setUser_salt(user_salt);
						ou.setUser_loginpass(Utils.MD5(user_salt + ":" + "111111"));
						// ou.setUser_mail(teacher.getUser_mail());
						ou.setUser_mobile(Utils.getValue(excelRow[6]));

						switch (Utils.getValue(excelRow[7])) {
							case "移动":
								ou.setUser_mobile_type((byte) 0);
								break;
							case "联通":
								ou.setUser_mobile_type((byte) 1);
								break;
							case "电信":
								ou.setUser_mobile_type((byte) 2);
								break;
							case "非津号码":
								ou.setUser_mobile_type((byte) 3);
								break;
							default:
								ou.setUser_mobile_type((byte) -1);
								break;
						}
						// ou.setUser_type(1);
						ou.setIs_del(false);
						ou.setInsert_time(new Date());
						ou.setUpdate_time(new Date());

						orgUserDao.save(ou);
						saveOrUpdateCount++;
						// 拿到userID
						Integer userID = ou.getUser_id();
						Teacher teacher = new Teacher();
						teacher.setOrg_id(orgID);
						teacher.setUser_id(userID);
						teacher.setTech_name(Utils.getValue(excelRow[1]));

						Department dp = null;
						if ("默认".equals(excelRow[11])) {
							// 获取默认部门ID
							dp = departmentDao.getDefaultDeptEntity(orgID);
						} else {
							// 根据名称在非默认部门中查询
							dp = departmentDao.getNonDefaultDeptEntity(orgID, Utils.getValue(excelRow[11]));
						}
						teacher.setDep_id(dp.getDep_id());
						teacher.setTech_card(Utils.getValue(excelRow[2]));
						// teacher.setJson(json);
						
						if (StringUtils.isNotBlank(Utils.getValue(excelRow[4]))) {
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							teacher.setBirthday(df.parse(Utils.getValue(excelRow[4])));
						}
						
						if (excelRow[3].equals("男")) {
							teacher.setSex((byte) 0);
						} else if (excelRow[3].equals("女")) {
							teacher.setSex((byte) 1);
						} else {
							teacher.setSex(null);
						}
						teacher.setIs_del(0);
						teacher.setInsert_time(new Date());
						teacher.setUpdate_time(new Date());

						teacherDao.save(teacher);
						saveOrUpdateCount++;

						if (StringUtils.isNotBlank(excelRow[0])) {
							GroupMember gm = new GroupMember();
							gm.setGroup_id(groupID);
							gm.setType(2);
							gm.setMem_id(teacher.getTech_id());
							gm.setInsert_time(new Date());
							gm.setIs_del(0);
							groupMemberDao.save(gm);
							saveOrUpdateCount++;
						}
						
						// 20180323新增处理角色
						if (StringUtils.isNotBlank(excelRow[10])) {
							// 角色填写时,进行绑定关联
							String[] roleNameArray = excelRow[10].split(",");
							// 1.先查询是否有对应的角色，没有不进行处理
							if (roleNameArray != null && roleNameArray.length > 0) {
								for (String roleName : roleNameArray) {
									if (StringUtils.isNotBlank(roleName)) {
										Role role = roleDao.getRole(orgID, roleName);
										if (role != null && role.getRole_id() != null && role.getRole_id().intValue() > 0) {
											// 新建的人员记录所以不用查询是否已存在
											//teacherRoleDao.getTeachRole(role_id, tech_id);
											TeacherRole tr = new TeacherRole();
											tr.setRole_id(role.getRole_id().intValue());
											tr.setTech_id(userID);
											tr.setInsert_time(new Date());
											tr.setIs_del(false);
											
											teacherRoleDao.save(tr);
											saveOrUpdateCount++;
										}
									}
								}
							}
						}
					} else {
						// 验证已存在的人的姓名和excel中是否一致
						// 已存在，则根据userID查询teacher
						//Teacher teacher = teacherDao.getByUserId(ou.getUser_id());
						Teacher teacher = redisTeacherDao.getByUserId(ou.getUser_id().toString());

						if (teacher != null && StringUtils.isNotBlank(teacher.getTech_name())) {
							if (teacher.getTech_name().trim().equals(Utils.getValue(excelRow[1]))) {
								// 名称相同
								int groupID = 0;
								if (StringUtils.isNotBlank(excelRow[0])) {
									int count = groupDao.checkName(orgID, excelRow[0]);
									if (count > 1) {
										throw new Exception("名称为【" + excelRow[0] + "】的组存在多个，请联系技术人员！");
									} else if (count == 0) {
										// 新建组
										Group g = new Group();
										g.setGroup_name(Utils.getValue(excelRow[0]));
										g.setOrg_id(orgID);
										g.setGroup_type(2);
										g.setIs_del(0);
										g.setInsert_time(new Date());
										g.setUpdate_time(new Date());
										groupDao.save(g);
										saveOrUpdateCount++;
										groupID = g.getGroup_id();
										// 新建的组肯定没有当前教师，所以进行保存

										GroupMember gm = new GroupMember();
										gm.setGroup_id(groupID);
										gm.setType(2);
										gm.setMem_id(teacher.getTech_id());
										gm.setInsert_time(new Date());
										gm.setIs_del(0);
										groupMemberDao.save(gm);
										saveOrUpdateCount++;
									} else if (count == 1) {
										Group g = groupDao.getGroupEntity(orgID, Utils.getValue(excelRow[0]));
										groupID = g.getGroup_id();
										// 根据已存在的组ID和教师ID，查询教师是否已在这个组存在

										int gmCount = groupMemberDao.getGroupMemberCount(groupID, 2, teacher.getTech_id());
										if (gmCount > 2) {
											throw new Exception("名称为【" + teacher.getTech_name() + "】的教师在【" + excelRow[0]
													+ "】组中存在多条记录，请联系技术人员！");
										} else if (gmCount == 0) {
											// 无关联进行添加
											GroupMember gm = new GroupMember();
											gm.setGroup_id(groupID);
											gm.setType(2);
											gm.setMem_id(teacher.getTech_id());
											gm.setInsert_time(new Date());
											gm.setIs_del(0);
											groupMemberDao.save(gm);
											saveOrUpdateCount++;
										}
									}
								}
								
								
								// 根据部门名称查询
								Department dp = null;
								if ("默认".equals(excelRow[11])) {
									// 获取默认部门ID
									dp = departmentDao.getDefaultDeptEntity(orgID);
								} else {
									// 根据名称在非默认部门中查询
									dp = departmentDao.getNonDefaultDeptEntity(orgID, Utils.getValue(excelRow[11]));
								}
								
								if (dp.getDep_id() != null && dp.getDep_id().intValue() > 0) {
									if (teacher.getDep_id() == null || teacher.getDep_id().intValue() == 0
											|| teacher.getDep_id().intValue() != dp.getDep_id().intValue()) {
										// 不同的话，对teacher进行更新
										teacher.setDep_id(dp.getDep_id().intValue());
										teacher.setUpdate_time(new Date());
										teacher.setIs_del(0);

										teacherDao.update(teacher);
										saveOrUpdateCount++;
									}
								}
								
								// 20180323新增处理角色
								if (StringUtils.isNotBlank(excelRow[10])) {
									// 角色填写时,进行绑定关联
									String[] roleNameArray = excelRow[10].split(",");
									// 1.先查询是否有对应的角色，没有不进行处理
									if (roleNameArray != null && roleNameArray.length > 0) {
										for (String roleName : roleNameArray) {
											if (StringUtils.isNotBlank(roleName)) {
												Role role = roleDao.getRole(orgID, roleName);
												if (role != null && role.getRole_id() != null && role.getRole_id().intValue() > 0) {
													// 已存在的人员先查询是否和角色进行了关联
													TeacherRole tr = teacherRoleDao.getTeachRole(role.getRole_id().intValue(), ou.getUser_id());
													if (tr == null || tr.getTech2role_id() == null || tr.getTech2role_id().intValue() <= 0) {
														tr = new TeacherRole();
														tr.setRole_id(role.getRole_id().intValue());
														tr.setTech_id(ou.getUser_id());
														tr.setInsert_time(new Date());
														tr.setIs_del(false);
														
														teacherRoleDao.save(tr);
														saveOrUpdateCount++;
													}
												}
											}
										}
									}
								}
							} else {
								// 名称不同，进行提示
								throw new Exception("数据库中手机号码为【" + ou.getUser_mobile() + "】的人员姓名与excel中填写的【" + excelRow[1] + "】不一致！");
							}
						} else {
							// 存在这个号码的人员，但不在教师表中(暂无此种情况，不处理)
							throw new Exception("手机号码为【" + ou.getUser_mobile() + "】的人员在org_user表中存在，但在teacher表中未找到！");
						}
					}
					
					// 所有处理完成后，提交事务
					txManager.commit(txStatus);
				} catch (Exception ex) {
					ex.printStackTrace();
					if (ex.getMessage() != null && StringUtils.isNotBlank(ex.getMessage())) {
						excelRow[errorIdx] = ex.getMessage();
					} else {
						excelRow[errorIdx] = "validateAndSaveTeacher方法执行中出现异常！";
					}
					
					
					// 根据saveOrUpdateCount变量的值来确定是否需要进行回滚操作
					if (saveOrUpdateCount > 0) {
						txManager.rollback(txStatus);
					}
				}
			}
			
			// 将数据放入返回结果List中
			if (StringUtils.isNotBlank(excelRow[errorIdx])) {
				resultArray.add(excelRow);
			}
		}

		return resultArray;
	}

	@Override
	public List<Object> getTeacherMembers(String orgId, String groupId) {
		// TODO Auto-generated method stub
		try {
			return teacherDao.getTeacherMembers(orgId, groupId);
		} catch (Exception e) {
			logger.error("getOrgTreeTeacherMembers Teacher error");
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> getTechsOfRlId(String org_id,Integer code) {
		// TODO Auto-generated method stub
		return teacherDao.getTechsOfRlId(org_id,code);
	}

	@Override
	public void batchAddTeacher(String fileID) {
		// TODO Auto-generated method stub		
		String errorMessage = "";
		try {
			if (StringUtils.isNotBlank(fileID)) {
				// 根据fileID查询res_excel表中记录
				ExcelRes excelResEntity = resourcesService.getExcelRes(Integer.valueOf(fileID));
				if (excelResEntity != null) {
					// 根据文件ID拿到Excel文件，机构ID等信息

					String basePath = this.eduConfig.getPath();
					String excelPath = basePath + File.separator + "res" + File.separator + "excel" + File.separator;
					String tempPath = basePath + File.separator + "res" + File.separator + "temp" + File.separator;
					String localFilePath = excelPath + Utils.getPathById(Integer.valueOf(fileID));

					// 从远程地址获取文件到本地
					File dir = new File(localFilePath);
					if (!dir.isDirectory()) {
						dir.mkdirs();
					}
					String filepathA = resourcesService.getRomoteFile(excelResEntity.getGroup_name(), excelResEntity.getPath(),
							localFilePath + fileID + "." + excelResEntity.getExt());
					//System.out.println(filepathA);

					File file = new File(localFilePath + fileID + "." + excelResEntity.getExt());
					FileInputStream in = new FileInputStream(file);
					MultipartFile multiFile = new MockMultipartFile("转换文件", in);

					int orgID = excelResEntity.getOrg_id();

					if (file != null) {
						Workbook wb = new HSSFWorkbook(multiFile.getInputStream());
						if (wb != null) {
							String headLineCheckResult = ImportExcelUtil.verificationImportExcelHeadLine(wb,
									ColumnName4ImportTeacher);
							if (StringUtils.isBlank(headLineCheckResult)) {
								List<String[]> excelRowArray = ImportExcelUtil.getImportTeachersFromExcel(wb);
								wb = null;
								if (!excelRowArray.isEmpty()) {
									List<String[]> errorList = this.validateAndSaveTeacher(orgID, excelRowArray);
									if (!errorList.isEmpty()) {
										String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity
												.getMessage().substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息",
												"批量导入教师", ColumnName4ImportTeacher, errorList, tempPath);
										// 将保存在temp文件夹下的excel保存到数据库
										file = new File(tempPath + completeFileName);
										in.close();
										in = new FileInputStream(file);
										multiFile = new MockMultipartFile(completeFileName, in);

										String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
										Long length = multiFile.getSize();

										String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
												length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
										String result_id = fileName.substring(0, fileName.indexOf("."));

										excelResEntity.setFinished(true);
										excelResEntity.setResult_id(Integer.valueOf(result_id));
										excelResEntity.setUpdated_time(new Date());
										resourcesService.updateExcelResEntity(excelResEntity);

										in.close();
									} else {
										// 没有错误信息说明，此次导入的excel内容已经全部成功
										excelResEntity.setFinished(true);
										excelResEntity.setUpdated_time(new Date());
										resourcesService.updateExcelResEntity(excelResEntity);
									}
								} else {
									errorMessage = "未在导入文件中发现数据！";

									// 将错误信息写入excel
									List<String[]> errorList = new ArrayList<String[]>();
									String[] errorInfo = new String[ColumnName4ImportTeacher.length + 1];
									errorInfo[0] = errorMessage;
									errorList.add(errorInfo);

									String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity
											.getMessage().substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息",
											"批量导入教师", ColumnName4ImportTeacher, errorList, tempPath);
									// 将保存在temp文件夹下的excel保存到数据库
									file = new File(tempPath + completeFileName);
									in.close();
									in = new FileInputStream(file);
									multiFile = new MockMultipartFile(completeFileName, in);

									String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
									Long length = multiFile.getSize();

									String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
											length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
									String result_id = fileName.substring(0, fileName.indexOf("."));

									excelResEntity.setFinished(true);
									excelResEntity.setResult_id(Integer.valueOf(result_id));
									excelResEntity.setUpdated_time(new Date());
									resourcesService.updateExcelResEntity(excelResEntity);

									in.close();
								}
							} else {
								errorMessage = headLineCheckResult;
								// 将错误信息写入excel
								List<String[]> errorList = new ArrayList<String[]>();
								String[] errorInfo = new String[ColumnName4ImportTeacher.length + 1];
								errorInfo[0] = errorMessage;
								errorList.add(errorInfo);

								String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo(excelResEntity.getMessage()
										.substring(0, excelResEntity.getMessage().indexOf(".")) + "错误信息", "批量导入教师",
										ColumnName4ImportTeacher, errorList, tempPath);
								// 将保存在temp文件夹下的excel保存到数据库
								file = new File(tempPath + completeFileName);
								in.close();
								in = new FileInputStream(file);
								multiFile = new MockMultipartFile(completeFileName, in);

								String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
								Long length = multiFile.getSize();

								String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
										length.intValue(), ext, orgID, completeFileName, (byte) 0,false);
								String result_id = fileName.substring(0, fileName.indexOf("."));

								excelResEntity.setFinished(true);
								excelResEntity.setResult_id(Integer.valueOf(result_id));
								excelResEntity.setUpdated_time(new Date());
								resourcesService.updateExcelResEntity(excelResEntity);

								in.close();
							}
						}
					} else {
						// logger.info(" PAYLOAD ERROR ：文件未找到！上传增加教师文件ID=" + msg.getPayload());
						errorMessage = "文件未找到！上传增加教师文件ID=" + fileID;
					}
				} else {
					errorMessage = "查询上传记录时出现错误！";
				}
			}
		} catch (Exception ex) {
			errorMessage = ex.getMessage();
		}
	}

	@Override
	public List<Teacher> getPhoneBook(Integer org_id) {
		return teacherDao.getPhoneBook(org_id);
	}

	@Override
	public List<Teacher> getTeachersByOrgId(Integer org_id) {
		return teacherDao.getTeachersByOrgId(org_id);
	}

	@Override
	public List<TechRange> getTechRanges(int org_id) {
		// TODO Auto-generated method stub
		return teacherDao.getTechRanges(org_id);
	}

	@Override
	public List<Map<String, String>> getTechsOforg(int org_id) {
		// TODO Auto-generated method stub
		return teacherDao.getTechsOforg(org_id);
	}

	@Override
	public Map<String,Object> batchTechRange(int org_id,MultipartFile multiFile,Map<String,String> courMap) {

		String errorMessage = "";
        Map<String,Object> map = new HashMap<String,Object>();
		try {		
			String basePath = this.eduConfig.getPath();
			String tempPath = basePath + File.separator + "res" + File.separator + "temp" + File.separator;
			map.put("tempPath", tempPath);
			Workbook wb = new HSSFWorkbook(multiFile.getInputStream());
			if (wb != null) {
				//获取单元格表头 列名
				String[] columnName4ImportTechRange = ImportExcelUtil.getTechRangeExcelHeadLine(wb,courMap);
				map.put("columnName", columnName4ImportTechRange);
				
				//验证表头
				String[] verification = ImportExcelUtil.verificationTechRangeExcelHeadLine(wb,courMap);

				if (verification!=null) {//Excel文件标题正确
					//从Excel中读取导入的信息
					List<String[]> excelRowArray = ImportExcelUtil.getImportTechRangeFromExcel(wb);
					wb = null;
					if (!excelRowArray.isEmpty()) {//Excel不为空
						//保存DB
						List<String[]> errorList = this.validateBatchTechRange(org_id, excelRowArray,courMap);
						map.put("errorList", errorList);
						/*if (!errorList.isEmpty()) {
							//将错误信息保存到Excel 并存入DB
							String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo( "错误信息","批量导入教师授课", columnName4ImportTechRange, errorList, tempPath);
							// 将保存在temp文件夹下的excel保存到数据库
							this.saveErrorExcel(tempPath, completeFileName, org_id);
						}*/ 
					} else {//Excel为空
						errorMessage = "未在导入文件中发现数据！";
						// 将错误信息写入excel
						List<String[]> errorList = new ArrayList<String[]>();
						String[] errorInfo = new String[columnName4ImportTechRange.length + 1];
						errorInfo[0] = errorMessage;
						errorList.add(errorInfo);
						map.put("errorList", errorList);
						/*//将错误信息保存到Excel 并存入DB
						String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo( "错误信息","批量导入教师授课", columnName4ImportTechRange, errorList, tempPath);
						// 将保存在temp文件夹下的excel保存到数据库
						this.saveErrorExcel(tempPath, completeFileName, org_id);*/
					}
				} else {//Excel文件标题 错误
					errorMessage = "excel标题行错误！";
					// 将错误信息写入excel
					List<String[]> errorList = new ArrayList<String[]>();
					String[] errorInfo = new String[columnName4ImportTechRange.length +1];
					errorInfo[columnName4ImportTechRange.length] = errorMessage;
					errorList.add(errorInfo);
					map.put("errorList", errorList);
					/*//将错误信息保存到Excel 并存入DB
					String completeFileName = ImportExcelUtil.makeExcelFileWithErrorInfo( "错误信息","批量导入教师授课", columnName4ImportTechRange, errorList, tempPath);
					// 将保存在temp文件夹下的excel保存到数据库
					this.saveErrorExcel(tempPath, completeFileName, org_id);*/
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage = ex.getMessage();
		}
		return map;
	}

	@Override
	public List<String[]> validateBatchTechRange(Integer orgID,List<String[]> excelRowArray,Map<String,String> courMap) {
		//存放年级信息 key:年级名称 val：年级id
		Map<String,String> njMap = new HashMap<String,String>();
		//存放班级信息 key:班级名称 val：班级id
		Map<String,String> bjMap = new HashMap<String,String>();
		//存放角色信息 key:角色名称 val：角色id
		Map<String,String> roleMap = new HashMap<String,String>();
		//存放表头信息
		String[] title = excelRowArray.get(0);
		List<String[]> resultArray = new ArrayList<String[]>();
		//第0行是表头  从第一行开始读
		for (int i = 1; i < excelRowArray.size(); i++) {
			String[] t = excelRowArray.get(i);
			// 用于保存错误信息的数组
			String[] excelRow = new String[t.length + 1];
			int errorIdx = t.length;

			excelRow[0] = t[0];//教师姓名
			excelRow[1] = t[1];//教师手机号
			excelRow[2] = t[2];//角色
			excelRow[3] = t[3];//年级组长
			excelRow[4] = t[4];//班主任
			for(int j=5;j<t.length;j++){//课程集合
				excelRow[j] = t[j];
			}
			//excelRow[t.length+1] = ""; // 错误信息
            /***
             * 逐行验证excel里 信息 有错误放入 错误信息数组  
             * ****/
			try {
				if (!(StringUtils.isNotBlank(excelRow[0]) && excelRow[0].length() <= 10)) {
					throw new Exception("教师名称字数应在10字以内！");
				}
				Pattern mobilePattern = Pattern.compile("^1[0-9][0-9]\\d{8}$");
				if (!(StringUtils.isNotBlank(excelRow[1]) && mobilePattern.matcher(excelRow[1]).matches())) {
					throw new Exception("手机号码为必填项，并请输入正确格式的手机号码！");
				}
			} catch (Exception ex) {
				excelRow[errorIdx] = ex.getMessage();
			}
			
			
			// 不存在错误则进行数据库保存
			if (StringUtils.isBlank(excelRow[errorIdx])) {
				// 手动控制事务
				HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
				TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
				int saveOrUpdateCount = 0;
				
				try {
					// 通过基本验证
					//OrgUser ou = orgUserDao.getOrgUser(orgID, 1, excelRow[1]);
					OrgUser ou = redisOrgUserDao.getUserByLoginNameWX(excelRow[1],new Integer(orgID),Constant.IDENTITY_TEACHER.toString());
					if (ou == null || ou.getUser_id().intValue() == 0) {
						throw new Exception("手机号码为【" + excelRow[1] + "】的人员在org_user表中不存在！");
					} else {
						// 验证已存在的人的姓名和excel中是否一致
						// 已存在，则根据userID查询teacher
						//Teacher teacher = teacherDao.getByUserId(ou.getUser_id());
						Teacher teacher = redisTeacherDao.getByUserId(ou.getUser_id().toString());
						if (teacher != null && StringUtils.isNotBlank(teacher.getTech_name())) {
							if (teacher.getTech_name().trim().equals(Utils.getValue(excelRow[0]))) {// 名称相同														
								//删除角色
								List<TeacherRole> techRoles = teacherRoleDao.getTeachRoles(ou.getUser_id());
								for(int y=0;y<techRoles.size();y++){
									TeacherRole teacherRole = techRoles.get(y);
									teacherRole.setDel_time(new Date());
									teacherRole.setIs_del(true);
									teacherRoleDao.delete(teacherRole);
									saveOrUpdateCount++;
								}
								
//								Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(ou.getUser_id().toString());
//								if (maps != null) {
//									for (Map.Entry<String, TeacherRole> entry : maps.entrySet()){		
//										TeacherRole teacherRole = entry.getValue();
//										teacherRole.setDel_time(new Date());
//										teacherRole.setIs_del(true);
//										//teacherRoleDao.delete(teacherRole);
//										teacherRoleDao.saveOrUpdate(teacherRole);
//										saveOrUpdateCount++;
//									}
//								}
								//处理角色
								if (StringUtils.isNotBlank(excelRow[2])) {
									// 角色填写时,进行绑定关联
									String[] roleNameArray = excelRow[2].split(",");
									// 1.先查询是否有对应的角色，没有不进行处理
									if (roleNameArray != null && roleNameArray.length > 0) {
										for (String roleName : roleNameArray) {
											if (StringUtils.isNotBlank(roleName)) {
												//角色信息存入 roleMap
												Role role  =null;
												if(roleMap.get(roleName)!=null){
													role = new Role();
													int role_id = Integer.parseInt(roleMap.get(roleName));
													role.setRole_id(role_id);
												}else{
													role = roleDao.getRole(orgID, roleName);
													if (role != null && role.getRole_id() != null && role.getRole_id().intValue() > 0)
														roleMap.put(roleName, role.getRole_id().toString());
													else
														throw new Exception("角色名称为【" + roleName + "】未找到！");
												}
												//Role role = roleDao.getRole(orgID, roleName);
												if (role != null && role.getRole_id() != null && role.getRole_id().intValue() > 0) {
													// 已存在的人员先查询是否和角色进行了关联
													TeacherRole tr = new TeacherRole();
													tr.setRole_id(role.getRole_id().intValue());
													tr.setTech_id(ou.getUser_id());
													tr.setInsert_time(new Date());
													tr.setIs_del(false);
													
													teacherRoleDao.save(tr);
													saveOrUpdateCount++;
												}else{
													throw new Exception("角色名称为【" + roleName + "】未找到！");
												}
											}
										}
									}
								}
								// 删除教师数据范围
								//List<TechRange> trList = redisTechRangeDao.getAllOfTechId(teacher.getTech_id());
								List<TechRange> trList = techRangeDao.getTechSkRangeListById(teacher.getTech_id());
								for (TechRange techRange : trList) {
									techRange.setIs_del(Constant.IS_DEL_YES);
									techRange.setDel_time(new Date());
									rangeDao.deleteRange(techRange);
									saveOrUpdateCount++;
								}
								//处理 年级组长
								if (StringUtils.isNotBlank(excelRow[3])) {
									String[] njArray = excelRow[3].split(",");
									if (njArray != null && njArray.length > 0) {
										for (String njName : njArray) {
											if (StringUtils.isNotBlank(njName)) {
												//讲 年级信息存入 njMap
												Grade grade =null;
												if(njMap.get(njName)!=null){
													grade = new Grade();
													int grade_id = Integer.parseInt(njMap.get(njName));
													grade.setGrade_id(grade_id);
												}else{
													grade = gradeDao.getGrade(orgID, njName);
													if (grade != null && grade.getGrade_id() != null && grade.getGrade_id().intValue() > 0)
														njMap.put(njName, grade.getGrade_id().toString());
													else
														throw new Exception("年级名称为【" + njName + "】未找到！");
												}
												//保存DB
												if (grade != null && grade.getGrade_id() != null && grade.getGrade_id().intValue() > 0) {
													TechRange tr = new TechRange();
													tr.setOrg_id(teacher.getOrg_id());
													tr.setTech_id(teacher.getTech_id());
													tr.setRl_id(EnumRoleLabel.年级组长.getCode());
													tr.setGrade_id(grade.getGrade_id());
													tr.setInsert_time(new Date());
													tr.setIs_del(0);
													tr.setHistory(0);
													rangeDao.save(tr);
													saveOrUpdateCount++;
												}
											}
										}
									}
								}
								//处理 班主任
								if (StringUtils.isNotBlank(excelRow[4])) {
									String[] clasArray = excelRow[4].split(",");
									if (clasArray != null && clasArray.length > 0) {
										for (String clasName : clasArray) {
											if (StringUtils.isNotBlank(clasName)) {
												//讲 班级信息存入 bjMap
												Classes clas =null;
												if(bjMap.get(clasName)!=null){
													clas = new Classes();
													int clas_id = Integer.parseInt(bjMap.get(clasName));
													clas.setClas_id(clas_id);
												}else{
													clas = classesDao.getClass(orgID, clasName);
													if (clas != null && clas.getClas_id() != null && clas.getClas_id().intValue() > 0){ 
														bjMap.put(clasName, clas.getClas_id().toString());
														bjMap.put(clas.getClas_id().toString(), clas.getGrade_id().toString());
														njMap.put(clas.getGrade_name(), clas.getGrade_id().toString());
													}else{
														throw new Exception("班级名称为【" + clasName + "】未找到！");
													}
												}
												//保存DB
												if (clas != null && clas.getClas_id() != null && clas.getClas_id().intValue() > 0) {
													TechRange tr = new TechRange();
													tr.setOrg_id(teacher.getOrg_id());
													tr.setTech_id(teacher.getTech_id());
													tr.setRl_id(EnumRoleLabel.班主任.getCode());
													tr.setClas_id(clas.getClas_id());
													tr.setInsert_time(new Date());
													tr.setIs_del(0);
													tr.setHistory(0);
													rangeDao.save(tr);
													saveOrUpdateCount++;
												}
											}
										}
									}
								}
								//处理课程
								for(int x=5;x<title.length;x++){
									if (StringUtils.isNotBlank(excelRow[x])) {
										String[] clasArray = excelRow[x].split(",");
										if (clasArray != null && clasArray.length > 0) {
											for (String clasName : clasArray) {
												if (StringUtils.isNotBlank(clasName)) {
													//讲 班级信息存入 bjMap
													Classes clas =null;
													if(bjMap.get(clasName)!=null){
														clas = new Classes();
														int clas_id = Integer.parseInt(bjMap.get(clasName));
														int grade_id = Integer.parseInt(bjMap.get(clas_id+""));
														
														clas.setClas_id(clas_id);
														clas.setGrade_id(grade_id);
													}else{
														clas = classesDao.getClass(orgID, clasName);
														if (clas != null && clas.getClas_id() != null && clas.getClas_id().intValue() > 0){ 
															bjMap.put(clasName, clas.getClas_id().toString());
															bjMap.put(clas.getClas_id().toString(), clas.getGrade_id().toString());
															njMap.put(clas.getGrade_name(), clas.getGrade_id().toString());
														}else{
															throw new Exception("班级名称为【" + clasName + "】未找到！");
														}
													}
													//保存DB
													if (clas != null && clas.getClas_id() != null && clas.getClas_id().intValue() > 0) {
														TechRange tr = new TechRange();
														tr.setOrg_id(teacher.getOrg_id());
														tr.setTech_id(teacher.getTech_id());
														tr.setRl_id(EnumRoleLabel.任课教师.getCode());
														tr.setClas_id(clas.getClas_id());
														tr.setGrade_id(clas.getGrade_id());
														tr.setInsert_time(new Date());
														tr.setIs_del(0);
														tr.setHistory(0);
														
														if(courMap.get(title[x])!=null){
															String cour_id = courMap.get(title[x]);
															tr.setCour_id(Integer.parseInt(cour_id));
														}else{
															throw new Exception("课程名称为【" + title[x] + "】未找到！");
														}
														
														rangeDao.save(tr);
														saveOrUpdateCount++;
													}
												}
											}
										}
									}
								}
							} else {
								// 名称不同，进行提示
								throw new Exception("数据库中手机号码为【" + ou.getUser_mobile() + "】的人员姓名与excel中填写的【" + excelRow[1] + "】不一致！");
							}
						} else {
							// 存在这个号码的人员，但不在教师表中(暂无此种情况，不处理)
							throw new Exception("手机号码为【" + ou.getUser_mobile() + "】的人员在org_user表中存在，但在teacher表中未找到！");
						}
					}
					// 所有处理完成后，提交事务
					txManager.commit(txStatus);
				} catch (Exception ex) {
					ex.printStackTrace();
					if (ex.getMessage() != null && StringUtils.isNotBlank(ex.getMessage())) {
						excelRow[errorIdx] = ex.getMessage();
					} else {
						ex.printStackTrace();
						excelRow[errorIdx] = "validateBatchTechRange方法执行中出现异常！";
					}
					// 根据saveOrUpdateCount变量的值来确定是否需要进行回滚操作
					if (saveOrUpdateCount > 0) {
						txManager.rollback(txStatus);
					}
				}
			}
			// 将数据放入返回结果List中
			if (StringUtils.isNotBlank(excelRow[errorIdx])) {
				resultArray.add(excelRow);
			}
		}
		return resultArray;
	}
	
	
	@Override
	public String saveErrorExcel(String tempPath,String completeFileName,int org_id) {
		// 将保存在temp文件夹下的excel保存到数据库
		File file = new File(tempPath + completeFileName);
		FileInputStream in;
		String result_id = "";
		try {
			in = new FileInputStream(file);
		
			MultipartFile multiFile = new MockMultipartFile(completeFileName, in);
			String ext = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
			Long length = multiFile.getSize();
			String fileName = this.resourcesService.saveExcel(1, 0, multiFile.getInputStream(),
					length.intValue(), ext, org_id, completeFileName, (byte) 0,false);
		    result_id = fileName.substring(0, fileName.indexOf("."));
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result_id;
	}
	
	
}
