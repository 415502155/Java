package cn.edugate.esb.imp;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument.Include;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.AttendanceConfDao;
import cn.edugate.esb.dao.AttendanceMachineDao;
import cn.edugate.esb.dao.CampusDAO;
import cn.edugate.esb.dao.CourseDao;
import cn.edugate.esb.dao.DepartmentDao;
import cn.edugate.esb.dao.IClass2StudentDao;
import cn.edugate.esb.dao.IClassesDao;
import cn.edugate.esb.dao.IGrade2ClassDao;
import cn.edugate.esb.dao.IGradeDao;
import cn.edugate.esb.dao.IGroupDao;
import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.dao.IOrgTreeDao;
import cn.edugate.esb.dao.IOrgUserDao;
import cn.edugate.esb.dao.IOrganizationDao;
import cn.edugate.esb.dao.IParentDao;
import cn.edugate.esb.dao.IRoleDao;
import cn.edugate.esb.dao.IRoleLabelDao;
import cn.edugate.esb.dao.IStudentDao;
import cn.edugate.esb.dao.IStudentParentDao;
import cn.edugate.esb.dao.ITeacherDao;
import cn.edugate.esb.dao.ITeacherRoleDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.AttendanceConf;
import cn.edugate.esb.entity.AttendanceMachine;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.service.MenuService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.JsonUtil;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.RedisUtil;
import cn.edugate.esb.util.Utils;

/**
 * Description: Company:SJWY
 * 
 * @author:wg
 * @Date:2017年4月24日下午1:39:51
 */
@Component
@Transactional("transactionManager")
public class OrganizationServiceImpl implements OrganizationService {
	

	@Autowired
	private TeacherRoleService teacherRoleService;
	
	@Autowired
	private IOrganizationDao orgDao;
	
	@Autowired
	private IGradeDao gradeDAO;
	
	@Autowired
	private CampusDAO campusDAO;
	
	@Autowired
	private IOrgTreeDao orgTreeDAO;
	
	@Autowired
	private DepartmentDao departmentDAO;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IRoleLabelDao roleLabelDao;
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private IStudentDao studentDao;
	
	@Autowired
	private IParentDao parentDao;
	
	@Autowired
	private IStudentParentDao s2pDao;
	
	@Autowired
	private IClassesDao classDao;
	
	@Autowired
	private IClass2StudentDao c2sDao;
	
	@Autowired
	private IGroupDao groupDao;
	
	@Autowired
	private IGroupMemberDao gmDao;
	
	@Autowired
	private ITechRangeDao teachRangeDao;
	
//	@Autowired
//	private IGradeDao gradeDao;
	
	@Autowired
	private ITeacherDao teacherDao;
	
	@Autowired
	private IOrgUserDao orgUserDao;
	
	@Autowired
	private ITeacherRoleDao t2rDao;
	
	@Autowired
	private IGrade2ClassDao g2cDao;
	
	@Autowired
	private AttendanceMachineDao attnMachineDao;
	
	@Autowired
	private AttendanceConfDao attnConfDao;
	
	@Autowired
	private RedisUtil redisUtil;
	
	

	
	@Override
	public void add(Organization organization) {
		// TODO Auto-generated method stub

	}

	@Override
	public Organization update(Organization organization) {
		orgDao.update(organization);
		return organization;
	}

	@Override
	public Organization getOrgById(int id) {
		return orgDao.get(Organization.class, id);
	}

	@Override
	public List<Organization> getOrgList() {
		return orgDao.getOrgList();
		//Criterion criterion = Restrictions.conjunction();
		//return orgDao.list(Organization.class, criterion);
	}

	@Override
	public Paging<Organization> getOrgListWithPaging(Paging<Organization> paging, Organization organization) {
		return orgDao.getOrgListWithPaging(paging, organization);
	}

	@Override
	public List<Organization> getOrgByType(int type) {
		// TODO Auto-generated method stub
		return orgDao.getOrgByType(type);
	}

	@Override
	public List<Organization> getOrgByFunID(int funId) {
		// TODO Auto-generated method stub
		return orgDao.getOrgByFunID(funId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String saveOrganization(Organization orgEntity) {
		String result = "";
		int count = orgDao.checkName(orgEntity.getOrg_name_cn());
		if (count == 0) {
			// 没有重名的机构
			// 1.先保存organzation主体，获得机构ID，再保存机构下的年级和校区
			int orgID = orgDao.saveOrganization(orgEntity);

			orgEntity.setOrg_id(orgID);

			// 新建一个默认部门
			Department dp = new Department();
			dp.setOrg_id(orgID);
			dp.setParent_id(0);
			dp.setDep_name("默认部门");
			dp.setType(1);
			departmentDAO.saveDefaultDP(dp);

			// 根据拿到的机构ID为年级和校区设值
			List<Grade> gradeList = orgEntity.getGradeList();
			Set<String> gradeTypeSet = null;
			if (gradeList != null && gradeList.size() > 0) {
				gradeTypeSet = new HashSet<String>();
				String gradeNumStr = "";
				for (Grade g : gradeList) {
					g.setOrg_id(orgID);
					gradeDAO.saveGrade(g);
					gradeNumStr += (g.getGrade_number().toString() + ",");
					gradeTypeSet.add(String.valueOf(g.getGrade_type().intValue()));
				}

				if (gradeNumStr.length() > 0) {
					gradeNumStr = gradeNumStr.substring(0, gradeNumStr.length() - 1);
				}
				// 存在年级的情况下新建默认菜单
				menuService.createMenuForOrg(orgID, orgEntity.getType(), null, gradeNumStr);
			} else {
				// 不存在年级的情况下新建默认菜单
				menuService.createMenuForOrg(orgID, orgEntity.getType());
			}

			List<Campus> campusList = orgEntity.getCampusList();
			if (campusList != null && campusList.size() > 0) {
				for (Campus c : campusList) {
					c.setOrg_id(orgID);
					c.setInsert_time(new Date());
					c.setUpdate_time(new Date());
					c.setIs_del(Constant.IS_DEL_NO);
					// 新增机构应该没有已存在的主校区，所以也直接进行保存
					campusDAO.save(c);
				}
			}
			//为机构创建默认管理员OrgUser信息
			OrgUser user = userService.createManagerForOrg(orgEntity);
			//根据OrgUser信息创建对应教师信息
			Teacher teacher = new Teacher();
			teacher.setDep_id(dp.getDep_id());
			teacher.setOrg_id(orgID);
			teacher.setTech_name(Constant.MANAGER_DEFAULT_LOGINNAME + orgID);
			teacher.setTech_note("云平台系统最高级管理员");
			teacher.setIs_del(Constant.IS_DEL_NO);
			teacher.setInsert_time(new Date());
			teacher.setUpdate_time(new Date());
			teacher.setUser(user);
			teacherService.add(teacher);
			//补充Teacher信息
			//user.setTeacher(teacher);
			//userService.updateOrgUser(user);
			//为教师建立权限关系
			String roleId = roleService.createManagerRole(orgID,orgEntity.getType());
			teacherRoleService.saveOrUpdateUser2Role(user.getUser_id(),new String[]{roleId});
			
			// 新建学校类型机构时增加班主任和任课教师两个角色，并根据年级类型初始化对应的一些科目 20180328 Begin
			if (orgEntity.getType().intValue() == 0) {
				// 1.查询班主任和任课教师的角色标签
				RoleLabel roleLabel = roleLabelDao.getRoleLabel(0, "班主任");
				if (roleLabel != null && roleLabel.getRl_id() != null && roleLabel.getRl_id().intValue() > 0) {
					// 2.根据角色标签创建对应的一个角色
					int rl_id = roleLabel.getRl_id().intValue();
					Role role = new Role();
					role.setRole_name("班主任");
					role.setRole_note("班主任");
					role.setOrg_id(orgID);
					role.setRl_id(rl_id);
					
					String authorities = null;
					if(rl_id==EnumRoleLabel.管理员.getCode() ||rl_id==EnumRoleLabel.校长.getCode()||rl_id==EnumRoleLabel.分校校长.getCode()||rl_id==EnumRoleLabel.局管理员.getCode()||rl_id==EnumRoleLabel.局长.getCode()){
						authorities = "{\""+Constant.ALL_AUTH+"\":262143}";
					}
					role.setAuthorities(authorities);
					role.setIs_del(false);
					role.setInsert_time(new Date());
					roleService.add(role);
				}
				
				roleLabel = roleLabelDao.getRoleLabel(0, "任课教师");
				if (roleLabel != null && roleLabel.getRl_id() != null && roleLabel.getRl_id().intValue() > 0) {
					int rl_id = roleLabel.getRl_id().intValue();
					Role role = new Role();
					role.setRole_name("任课教师");
					role.setRole_note("任课教师");
					role.setOrg_id(orgID);
					role.setRl_id(rl_id);
					
					String authorities = null;
					if(rl_id==EnumRoleLabel.管理员.getCode() ||rl_id==EnumRoleLabel.校长.getCode()||rl_id==EnumRoleLabel.分校校长.getCode()||rl_id==EnumRoleLabel.局管理员.getCode()||rl_id==EnumRoleLabel.局长.getCode()){
						authorities = "{\""+Constant.ALL_AUTH+"\":262143}";
					}
					role.setAuthorities(authorities);
					role.setIs_del(false);
					role.setInsert_time(new Date());
					roleService.add(role);
				}
				
				// 根据年级类型创建不同的课程
				if (gradeTypeSet != null && gradeTypeSet.size() > 0) {
					Set<String> courseNameSet = new HashSet<String>();
					Date currentDate = new Date();
					for (String gradeType : gradeTypeSet) {
						// 查询此机构下的当前年级类型已存在新增的三种年级类型，不存在进行创建
						Grade g = gradeDAO.getGrade(orgID, Integer.valueOf(gradeType).intValue(), Integer.valueOf(gradeType).intValue() * 10);
						if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
							// 不存在，进行创建
							g = Utils.getGrade4NewType(orgID, Integer.valueOf(gradeType).intValue(), Integer.valueOf(gradeType).intValue() * 10);
							if (g != null) {
								g.setInsert_time(currentDate);
								g.setIs_del(0);
								
								gradeDAO.save(g);
							}
						}

						g = gradeDAO.getGrade(orgID, Integer.valueOf(gradeType).intValue(),
								Integer.valueOf(gradeType).intValue() * 10 + 8);
						if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
							// 不存在，进行创建
							g = Utils.getGrade4NewType(orgID, Integer.valueOf(gradeType).intValue(), Integer.valueOf(gradeType).intValue() * 10 + 8);
							if (g != null) {
								g.setInsert_time(currentDate);
								g.setIs_del(0);
								
								gradeDAO.save(g);
							}
						}

						g = gradeDAO.getGrade(orgID, Integer.valueOf(gradeType).intValue(),
								Integer.valueOf(gradeType).intValue() * 10 + 9);
						if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
							// 不存在，进行创建
							g = Utils.getGrade4NewType(orgID, Integer.valueOf(gradeType).intValue(), Integer.valueOf(gradeType).intValue() * 10 + 9);
							if (g != null) {
								g.setInsert_time(currentDate);
								g.setIs_del(0);
								
								gradeDAO.save(g);
							}
						}
						
						if ("1".equals(gradeType)) {
							for (String courseName : Constant.CourseName4YouErYuan) {
								courseNameSet.add(courseName);
							}
						} else if ("2".equals(gradeType)) {
							for (String courseName : Constant.CourseName4XiaoXue) {
								courseNameSet.add(courseName);
							}
						} else if ("3".equals(gradeType)) {
							for (String courseName : Constant.CourseName4ChuZhong) {
								courseNameSet.add(courseName);
							}
						} else if ("4".equals(gradeType)) {
							for (String courseName : Constant.CourseName4GaoZhong) {
								courseNameSet.add(courseName);
							}
						}
					}
					
					// 创建课程
					if (courseNameSet.size() > 0) {
						for (String courseName : courseNameSet) {
							Course c = new Course();
							c.setOrg_id(orgID);
							c.setCour_name(courseName);
							c.setInsert_time(currentDate);
							c.setIs_del(0);
							courseDao.save(c);
						}
					}
				}
			}
			// 新建机构时增加班主任和任课教师两个角色 20180328 End
			
			
			result = "success_"+String.valueOf(orgID);
		} else {
			result = "名称为【" + orgEntity.getOrg_name_cn() + "】的机构已经存在！";
		}
		
		return result;
	}

	@Override
	public int updateOrganizationLogo(Organization orgEntity) {
		return orgDao.updateOrganizationLogo(orgEntity);
	}

	@Override
	public Organization getOrgAllInfo(int orgID) {
		Organization orgEntity = orgDao.get(Organization.class, orgID);
		if (orgEntity != null) {
			// 按机构不同的类型，分别查询年级和校区
			if (orgEntity.getType() == 0) {
				// 查询校区和年级
				List<Grade> gl = gradeDAO.getGradeList(orgID);
				if (gl != null && gl.size() > 0) {
					orgEntity.setGradeList(gl);
				}
				
				List<Campus> cl = campusDAO.getCampusList(orgID);
				if (cl != null && cl.size() > 0) {
					orgEntity.setCampusList(cl);
				}
 			} else if (orgEntity.getType() == 2) {
				// 培训机构只查询校区
 				List<Campus> cl = campusDAO.getCampusList(orgID);
				if (cl != null && cl.size() > 0) {
					orgEntity.setCampusList(cl);
				}
			}
		}
		return orgEntity;
	}

	@Override
	public Paging<Organization> getOrgListByUsingFunctionWithPaging(
			Paging<Organization> paging, String orgName, Integer functionId) {
		paging = orgDao.getListWithPaging(paging,orgName,functionId);
		return paging;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String updateOrganization(Organization orgEntity) {
		String result = "";
		// 获取修改前的机构对象
		Organization orgEntityBeforeEdit = this.getOrgAllInfo(orgEntity.getOrg_id());
		if (orgEntityBeforeEdit != null) {

			String orgNameBeforeEdit = orgEntityBeforeEdit.getOrg_name_cn();
			String orgNameAfterEdit = orgEntity.getOrg_name_cn().trim();

			// 比较是否更改了机构名称
			if (!orgNameBeforeEdit.equals(orgNameAfterEdit)) {
				// 改变了机构名称需要重新查重
				int count = orgDao.checkName(orgNameAfterEdit);
				if (count != 0) {
					result = "名称为【" + orgNameAfterEdit + "】的机构已经存在！";
				}
			}

			if (StringUtils.isBlank(result)) {
				// 将页面中可变更项赋值给修改前的机构实体
				orgEntityBeforeEdit.setOrg_name_cn(orgNameAfterEdit);
				orgEntityBeforeEdit.setOrg_name_s_cn(Utils.getValue(orgEntity.getOrg_name_s_cn()));
				orgEntityBeforeEdit.setOrg_name_en(Utils.getValue(orgEntity.getOrg_name_en()));
				orgEntityBeforeEdit.setOrg_name_s_en(Utils.getValue(orgEntity.getOrg_name_s_en()));
				orgEntityBeforeEdit.setArea(orgEntity.getArea());
				orgEntityBeforeEdit.setAddress(Utils.getValue(orgEntity.getAddress()));
				orgEntityBeforeEdit.setWebsite(Utils.getValue(orgEntity.getWebsite()));
				orgEntityBeforeEdit.setPostcode(Utils.getValue(orgEntity.getPostcode()));
				orgEntityBeforeEdit.setContact(Utils.getValue(orgEntity.getContact()));
				orgEntityBeforeEdit.setContact_mobile(Utils.getValue(orgEntity.getContact_mobile()));
				orgEntityBeforeEdit.setRemark(Utils.getValue(orgEntity.getRemark()));
				orgEntityBeforeEdit.setCss(Utils.getValue(orgEntity.getCss()));
				//orgEntityBeforeEdit.setWelcome(Utils.getValue(orgEntity.getWelcome()));
				orgEntityBeforeEdit.setLayout(orgEntity.getLayout());
				orgEntityBeforeEdit.setCopyright_info(Utils.getValue(orgEntity.getCopyright_info()));
				orgEntityBeforeEdit.setPolice_record(Utils.getValue(orgEntity.getPolice_record()));
				orgEntityBeforeEdit.setPolice_record_url(Utils.getValue(orgEntity.getPolice_record_url()));
				orgEntityBeforeEdit.setICP_record(Utils.getValue(orgEntity.getICP_record()));
				orgEntityBeforeEdit.setICP_record_url(Utils.getValue(orgEntity.getICP_record_url()));
				orgEntityBeforeEdit.setInfo(Utils.getValue(orgEntity.getInfo()));
				orgEntityBeforeEdit.setSupport(Utils.getValue(orgEntity.getSupport()));

				// 进行更新
				orgDao.update(orgEntityBeforeEdit);

				if (orgEntityBeforeEdit.getType() == 0) {
					// 更新年级和校区
					List<Grade> addGrade = orgEntity.getGradeList();
					Set<Integer> gradeTypeSet = new HashSet<Integer>();
					
					// 编辑机构时针对新增的年级要根据是否当前机构有考勤机来判断是否新增年级考勤设置记录
					// 查询当前机构是否有考勤机
					boolean hasAttnMachine = false;
					int attnType = 0;
					List<AttendanceMachine> attnMachineList = attnMachineDao.getAttendanceMachineList(orgEntityBeforeEdit.getOrg_id());
					if (attnMachineList != null && attnMachineList.size() > 0) {
						// 存在考勤机
						hasAttnMachine = true;
						
						List<AttendanceConf> attnConfList = attnConfDao.getAttnConfList(orgEntityBeforeEdit.getOrg_id());
						if (attnConfList != null && attnConfList.size() > 0) {
							attnType = attnConfList.get(0).getType();
						}
					} else {
						// 不存在考勤机，默认软考勤
					}
					if (addGrade != null && addGrade.size() > 0) {
						List<AttendanceConf> newAttnConfList = new ArrayList<AttendanceConf>();
						for (Grade g : addGrade) {
							g.setOrg_id(orgEntity.getOrg_id());
							gradeDAO.saveGrade(g);
							gradeTypeSet.add(g.getGrade_type());
							
							if (hasAttnMachine) {
								AttendanceConf ac = new AttendanceConf();
								ac.setOrg_id(orgEntityBeforeEdit.getOrg_id());
								ac.setAttnd_num(2);
								ac.setType(attnType);
								ac.setGrade_id(g.getGrade_id());
								ac.setGrade_name(g.getGrade_name());
								
								ac.setAM_in_scope_begin(new Time(6, 0, 0));
								ac.setAM_in_scope_end(new Time(10, 0, 0));
								ac.setAM_in_time(new Time(8, 0, 0));
								
								ac.setPM_out_scope_begin(new Time(15, 0, 0));
								ac.setPM_out_scope_end(new Time(19, 0, 0));
								ac.setPM_out_time(new Time(17, 0, 0));
								ac.setInsert_time(new Date());
								
								attnConfDao.insert(ac);
								
								newAttnConfList.add(ac);
							}
						}
						
						if (newAttnConfList.size() > 0) {
							setAttendanceConfToRedis(newAttnConfList);
						}
					}
					
					// 增加处理新勾选年级是否已经存在新增的三种年级类型 20180627 Begin
					if (gradeTypeSet.size() > 0) {
						Date currentDate = new Date();
						int orgId = orgEntity.getOrg_id();
						for (Integer gradeType : gradeTypeSet) {
							// 查询此机构下的当前年级类型已存在新增的三种年级类型，不存在进行创建
							Grade g = gradeDAO.getGrade(orgId, gradeType.intValue(), gradeType.intValue() * 10);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(orgId, gradeType.intValue(), gradeType.intValue() * 10);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									
									gradeDAO.save(g);
								}
							}

							g = gradeDAO.getGrade(orgId, gradeType.intValue(), gradeType.intValue() * 10 + 8);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(orgId, gradeType.intValue(), gradeType.intValue() * 10 + 8);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									
									gradeDAO.save(g);
								}
							}

							g = gradeDAO.getGrade(orgId, gradeType.intValue(), gradeType.intValue() * 10 + 9);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(orgId, gradeType.intValue(), gradeType.intValue() * 10 + 9);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									
									gradeDAO.save(g);
								}
							}
						}
					}
					// 增加处理新勾选年级是否已经存在新增的三种年级类型 20180627 End
					

					List<Campus> campusList = orgEntity.getCampusList();
					if (campusList != null && campusList.size() > 0) {
						for (Campus c : campusList) {
							c.setOrg_id(orgEntity.getOrg_id());
							c.setUpdate_time(new Date());

							if (c.getCam_id() != null && c.getCam_id().intValue() > 0) {
								// 先把原对象查询出来
								Campus originalCampus = campusDAO.get(Campus.class, c.getCam_id());
								originalCampus.setCam_name(Utils.getValue(c.getCam_name()));
								originalCampus.setCam_note(Utils.getValue(c.getCam_note()));
								originalCampus.setCam_type(c.getCam_type());
								originalCampus.setUpdate_time(new Date());
								campusDAO.update(originalCampus);
							} else {
								c.setInsert_time(new Date());
								c.setUpdate_time(new Date());
								c.setIs_del(0);
								campusDAO.save(c);
							}
						}
					}

				} else if (orgEntityBeforeEdit.getType() == 2) {
					// 更新校区
					List<Campus> campusList = orgEntity.getCampusList();
					if (campusList != null && campusList.size() > 0) {
						for (Campus c : campusList) {
							c.setOrg_id(orgEntity.getOrg_id());
							c.setUpdate_time(new Date());

							if (c.getCam_id() != null && c.getCam_id().intValue() > 0) {
								// 先把原对象查询出来
								Campus originalCampus = campusDAO.get(Campus.class, c.getCam_id());
								originalCampus.setCam_name(Utils.getValue(c.getCam_name()));
								originalCampus.setCam_note(Utils.getValue(c.getCam_note()));
								originalCampus.setCam_type(c.getCam_type());
								originalCampus.setUpdate_time(new Date());
								campusDAO.update(originalCampus);
							} else {
								c.setInsert_time(new Date());
								c.setUpdate_time(new Date());
								c.setIs_del(0);
								campusDAO.save(c);
							}
						}
					}
				}

				result = "success";
			}
		} else {
			result = "保存编辑操作的机构不存在！";
		}

		return result;
	}

	@Override
	public List<Organization> getLowerOrgList(int orgID) {
		return orgDao.getLowerOrgList(orgID);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void saveLowerOrg(int orgID, List<OrgTree> lowerOrgList) {
		//String result = "";
		
		if (lowerOrgList != null && lowerOrgList.size() > 0) {
			// 保存的下级机构集合不为空，则计算出哪些需要增加，哪些需要删除
			Map<Integer, OrgTree> lowerOrgList_backup = new HashMap<Integer, OrgTree>(lowerOrgList.size());
			
			for (OrgTree ot : lowerOrgList) {
				lowerOrgList_backup.put(ot.getLower_id(), ot);
			}
			
			// 获取原有的下级机构集合
			List<OrgTree> existedOrgTree = orgTreeDAO.getAllLowerOrg(orgID);
			if (existedOrgTree != null && existedOrgTree.size() > 0) {
				// 在新传入的下级机构集合中把已存在的下级机构剔除，剩下的是要进行添加的机构
				for (OrgTree ot : existedOrgTree) {
					if (lowerOrgList_backup.containsKey(ot.getLower_id())) {
						lowerOrgList_backup.remove(ot.getLower_id());
					}
				}
				
				Iterator iter = lowerOrgList_backup.keySet().iterator();
				while(iter.hasNext()) {
					Integer id = (Integer) iter.next();
					OrgTree otEntity = lowerOrgList_backup.get(id);
					orgTreeDAO.saveOrgTree(otEntity);
				}
				
				// 在已存在的下级机构集合中把新传入的下级机构剔除，剩下的是要进行删除的机构
				iter = existedOrgTree.iterator();
				while(iter.hasNext()) {
					OrgTree ot = (OrgTree) iter.next();
					for (OrgTree orgTree : lowerOrgList) {
						if (orgTree.getLower_id().intValue() == ot.getLower_id().intValue()) {
							iter.remove();
							break;
						}
					}
				}
				
				if (existedOrgTree.size() > 0) {
					for (OrgTree ot : existedOrgTree) {
						orgTreeDAO.deleteOrgTree(ot);
					}
				}
			} else {
				// 原有下级机构集合为空，则直接添加新保存的下级机构集合
				for (OrgTree ot : lowerOrgList) {
					orgTreeDAO.saveOrgTree(ot);
				}
			}
		} else {
			// 如果将要保存的下级机构为空集合，则说明将之前的所有下级机构都删除即可
			// 用hibernate的方式只能查询出全部，然后逐个删除
			List<OrgTree> deleteList =  orgTreeDAO.getAllLowerOrg(orgID);
			if (deleteList != null && deleteList.size() > 0) {
				for (OrgTree ot : deleteList) {
					orgTreeDAO.deleteOrgTree(ot);
				}
			}
			//orgTreeDAO.deleteAllLowerOrg(orgID);
		}
		
	}

	@Override
	public int updateOrganizationBackground(Organization orgEntity) {
		return orgDao.updateOrganizationBackground(orgEntity);
	}

	@Override
	public int updateOrganizationWelCome(Organization orgEntity) {
		// TODO Auto-generated method stub
		return orgDao.updateOrganizationWelCome(orgEntity);
	}

	@Override
	public void executeBatchImport(Organization orgEntity) {
		orgDao.executeBatchImport(orgEntity);
	}

	@Override
	public List<Organization> getSchool(String district) {
		return orgDao.getSchool(district);
	}

	@Override
	public List<String> getOrgInFunctionByFunId(Integer funId) {
		return orgDao.getOrgInFunctionByFunId(funId);
	}

	@Override
	public List<String> getOrgInModuleByFunId(Integer funId) {
		return orgDao.getOrgInModuleByFunId(funId);
	}

	@Override
	public void deleteOrgData(int org_id) {
		
		System.out.println(org_id);
		// 找出教师，学生，家长的集合
		List<Teacher> teacherList = teacherDao.getTeacherListIncludeDeleted(org_id);

		List<Student> studentList = studentDao.getStudentListIncludeDeleted(org_id);

		List<Parent> parentList = parentDao.getParentListIncludeDeleted(org_id);

		// 只删学生家长时还需更改查询机构用户的sql语句
		List<OrgUser> orgUserList = orgUserDao.getOrgUserListIncludeDeleted(org_id);

		List<TechRange> trList = teachRangeDao.getTechRangeListIncludeDeleted(org_id);

		List<Group> groupList = groupDao.getGroupListIncludeDeleted(org_id);

		List<GroupMember> gmList = gmDao.getGroupMemberListIncludeDeleted(org_id);

		List<StudentParent> s2pList = s2pDao.getStudentParentListIncludeDeleted(org_id);

		List<Clas2Student> c2sList = c2sDao.getClass2StudentListIncludeDeleted(org_id);

		List<Classes> classList = classDao.getClassListIncludeDeleted(org_id);

		List<Grade> gradeList = gradeDAO.getGradeListIncludeDeleted(org_id);
		
		List<Grade2Clas> g2cList = g2cDao.getGrade2ClassListIncludeDeleted(org_id);
		
		List<TeacherRole> t2rList = t2rDao.getTeacherRoleListIncludeDeleted(org_id);
		
		List<Course> courseList = courseDao.getCourseListIncludeDeleted(org_id);
		
		Date currentDate = new Date();

		if (c2sList != null && c2sList.size() > 0) {
			for (Clas2Student c2s : c2sList) {
				c2s.setIs_del(true);
				c2s.setDel_time(currentDate);
				c2sDao.update(c2s);
			}
		}

		if (s2pList != null && s2pList.size() > 0) {
			for (StudentParent s2p : s2pList) {
				s2p.setIs_del(true);
				s2p.setDel_time(currentDate);
				s2pDao.update(s2p);
			}
		}
		
		if (classList != null && classList.size() > 0) {
			for (Classes c : classList) {
				c.setIs_del(1);
				c.setDel_time(currentDate);
				classDao.update(c);
			}
		}

		if (gradeList != null && gradeList.size() > 0) {
			for (Grade g : gradeList) {
				g.setIs_del(1);
				g.setDel_time(currentDate);
				gradeDAO.update(g);
			}
		}
		
		if (g2cList != null && g2cList.size() > 0) {
			for (Grade2Clas g2c : g2cList) {
				g2c.setIs_del(true);
				g2c.setDel_time(currentDate);
				g2cDao.update(g2c);
			}
		}

		// 只删学生家长时注掉 Begin=============================
		/*if (courseList != null && courseList.size() > 0) {
			for (Course c : courseList) {
				courseDao.delete(c);
			}
		}
		
		if (gmList != null && gmList.size() > 0) {
			for (GroupMember gm : gmList) {
				gmDao.delete(gm);
			}
		}
		if (groupList != null && groupList.size() > 0) {
			for (Group g : groupList) {
				groupDao.delete(g);
			}
		}

		if (trList != null && trList.size() > 0) {
			for (TechRange tr : trList) {
				teachRangeDao.delete(tr);
			}
		}
		
		if (t2rList != null && t2rList.size() > 0) {
			for (TeacherRole t2r : t2rList) {
				t2rDao.delete(t2r);
			}
		}

		if (teacherList != null && teacherList.size() > 0) {
			for (Teacher t : teacherList) {
				teacherDao.delete(t);
			}
		}*/
		// 只删学生家长时注掉 End=============================

		if (studentList != null && studentList.size() > 0) {
			for (Student s : studentList) {
				s.setIs_del(1);
				s.setDel_time(currentDate);
				studentDao.update(s);
			}
		}

		if (parentList != null && parentList.size() > 0) {
			for (Parent p : parentList) {
				p.setIs_del(true);
				p.setDel_time(currentDate);
				parentDao.update(p);
			}
		}

		if (orgUserList != null && orgUserList.size() > 0) {
			for (OrgUser ou : orgUserList) {
				ou.setIs_del(true);
				ou.setDel_time(currentDate);
				orgUserDao.update(ou);
			}
		}

		System.out.println("删除学校数据完成！");
	}

	@Override
	public Organization getOrgByIdForRedis(int org_id) {
		return orgDao.getOrgByIdForRedis(org_id);
	}

	@Override
	public void initGradeType4Schools(String org_id) {
		if ("allOrg".equals(org_id)) {
			// 获取所有类型是学校的机构
			List<Organization> orgList = orgDao.getOrgByType(0);
			if (orgList != null && orgList.size() > 0) {
				Date currentDate = new Date();
				for (Organization org : orgList) {
					List<Grade> gradeList = gradeDAO.getGradeList(org.getOrg_id());
					if (gradeList != null && gradeList.size() > 0) {
						Set<Integer> gradeTypeSet = new HashSet<Integer>();
						for (Grade g : gradeList) {
							gradeTypeSet.add(g.getGrade_type());
						}
						
						if (gradeTypeSet.size() > 0) {
							for (Integer gradeType : gradeTypeSet) {
								// 查询此机构下的当前年级类型已存在新增的三种年级类型，不存在进行创建
								Grade g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10);
								if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
									// 不存在，进行创建
									g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10);
									if (g != null) {
										g.setInsert_time(currentDate);
										g.setIs_del(0);
										gradeDAO.save(g);
									}
								}

								g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10 + 8);
								if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
									// 不存在，进行创建
									g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10 + 8);
									if (g != null) {
										g.setInsert_time(currentDate);
										g.setIs_del(0);
										gradeDAO.save(g);
									}
								}

								g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10 + 9);
								if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
									// 不存在，进行创建
									g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10 + 9);
									if (g != null) {
										g.setInsert_time(currentDate);
										g.setIs_del(0);
										gradeDAO.save(g);
									}
								}
							}
						}
					}
				}
			}
		} else {
			// 单个机构
			Organization org = orgDao.get(Organization.class, Integer.valueOf(org_id));
			if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0 && org.getType().intValue() == 0 && !org.getIs_del()) {
				List<Grade> gradeList = gradeDAO.getGradeList(org.getOrg_id());
				if (gradeList != null && gradeList.size() > 0) {
					Set<Integer> gradeTypeSet = new HashSet<Integer>();
					for (Grade g : gradeList) {
						gradeTypeSet.add(g.getGrade_type());
					}
					
					if (gradeTypeSet.size() > 0) {
						Date currentDate = new Date();
						for (Integer gradeType : gradeTypeSet) {
							// 查询此机构下的当前年级类型已存在新增的三种年级类型，不存在进行创建
							Grade g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									gradeDAO.save(g);
								}
							}

							g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10 + 8);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10 + 8);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									gradeDAO.save(g);
								}
							}

							g = gradeDAO.getGrade(org.getOrg_id(), gradeType, gradeType * 10 + 9);
							if (g == null || g.getGrade_id() == null || g.getGrade_id().intValue() <= 0) {
								// 不存在，进行创建
								g = Utils.getGrade4NewType(org.getOrg_id(), gradeType, gradeType * 10 + 9);
								if (g != null) {
									g.setInsert_time(currentDate);
									g.setIs_del(0);
									gradeDAO.save(g);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 设置考勤设置到redis缓存
	 * @param confList
	 */
	private void setAttendanceConfToRedis(List<AttendanceConf> confList) {
		if (confList != null && confList.size() > 0) {
			for (AttendanceConf ac : confList) {
				try {
					redisUtil.set("AttendanceConf:org=" + ac.getOrg_id() + ",gradeId=" + ac.getGrade_id(),
							JsonUtil.toJson(ac, Inclusion.ALWAYS));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
