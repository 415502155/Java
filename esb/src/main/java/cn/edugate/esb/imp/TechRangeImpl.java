package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.CourseDao;
import cn.edugate.esb.dao.IOrganizationDao;
import cn.edugate.esb.dao.ITeacherDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

/**
 * 权限Service实现 Title:TechRangeImpl Description: Company:SJWY
 * 
 * @author:Huangcf
 * @Date:2017年4月24日下午1:39:51
 */
@Component
@Transactional("transactionManager")
public class TechRangeImpl implements TechRangeService {

	private ITechRangeDao techRangeDao;

	@Autowired
	public void setTechRangeDao(ITechRangeDao techRangeDao) {
		this.techRangeDao = techRangeDao;
	}

	@Autowired
	private ITeacherDao teacherDao;
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private IOrganizationDao orgDao;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TechRangeImpl.class);

	@Override
	public void getAdminPaging(Paging<TechRange> paging, String searchName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAdminOrgsByTechId(Integer tech_id) {
		// TODO Auto-generated method stub
		return this.techRangeDao.getAdminOrgsByTechId(tech_id);
	}

	@Override
	public String getHeadmasterOrgsByTechId(Integer tech_id) {
		// TODO Auto-generated method stub
		return this.techRangeDao.getHeadmasterOrgsByTechId(tech_id);
	}

	@Override
	public List<TechRange> createRange(Integer org_id, Integer tech_id, Integer rl_id, String clas_id, String grade_id,
			String group_id, String dep_id, String cour_id) {
		List<TechRange> list = new ArrayList<TechRange>();
		if (null == org_id || null == tech_id || null == rl_id) {
			return null;
		}
		TechRange techRange = new TechRange();
		techRange.setOrg_id(org_id);
		techRange.setTech_id(tech_id);
		techRange.setRl_id(rl_id);
		techRange.setInsert_time(new Date());
		techRange.setIs_del(Constant.IS_DEL_NO);
		techRange.setHistory(Constant.IS_DEL_NO);
		switch (rl_id) {
			case 3:// 任课教师
				if (StringUtils.isNotEmpty(cour_id)) {
					String[] courceIds = cour_id.split(",");
					for (String id : courceIds) {
						try {
							techRange.setClas_id(Integer.parseInt(clas_id));
							techRange.setGrade_id(Integer.parseInt(grade_id));
							techRange.setCour_id(Integer.parseInt(id));
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			case 4:// 班主任
				if (StringUtils.isNotEmpty(clas_id)) {
					String[] clasIds = clas_id.split(",");
					for (String id : clasIds) {
						try {
							if (null != grade_id) {
								techRange.setGrade_id(Integer.parseInt(grade_id));
							}
							techRange.setClas_id(Integer.parseInt(id));
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			case 5:// 年级组长
				if (StringUtils.isNotEmpty(grade_id)) {
					String[] gradeIds = grade_id.split(",");
					for (String id : gradeIds) {
						try {
							techRange.setGrade_id(Integer.parseInt(id));
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			case 6:// 学生组管理员
				if (StringUtils.isNotEmpty(group_id)) {
					String[] groupIds = group_id.split(",");
					for (String id : groupIds) {
						try {
							techRange.setGroup_id(id);
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			case 7:// 部门管理员
				if (StringUtils.isNotEmpty(dep_id)) {
					String[] depIds = dep_id.split(",");
					for (String id : depIds) {
						try {
							techRange.setDep_id(id);
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			case 8:// 教师组管理员
				if (StringUtils.isNotEmpty(group_id)) {
					String[] gIds = group_id.split(",");
					for (String id : gIds) {
						try {
							techRange.setGroup_id(id);
							techRange.setInsert_time(new Date());
							techRangeDao.save(techRange);
							list.add(techRange);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					return null;
				}
				break;
			default:
				try {
					techRange.setInsert_time(new Date());
					techRangeDao.save(techRange);
					list.add(techRange);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				break;
		}
		return list;
	}

	@Override
	public List<TechRange> createRange(String org_id, String tech_id, String rl_id, String clas_id, String grade_id,
			String group_id, String dep_id, String cour_id) {
		if (StringUtils.isNotEmpty(org_id) && StringUtils.isNotEmpty(tech_id) && StringUtils.isNotEmpty(rl_id)) {
			try {
				return createRange(Integer.parseInt(org_id), Integer.parseInt(tech_id), Integer.parseInt(rl_id), clas_id,
						grade_id, group_id, dep_id, cour_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Map<String, Object>> getTeaching(String org_id, String tech_id, String grade_id, String cour_id, String clas_id) {
		// TODO Auto-generated method stub
		return this.techRangeDao.getTeaching(org_id, tech_id, grade_id, cour_id, clas_id);
	}

	@Override
	public void deleteRange(TechRange techRange) {
		try {
			techRangeDao.deleteRange(techRange);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOldTeachRange(String org_id, String dep_id, String grade_id, String clas_id, String cour_id,
			String group_id, String group_type) {
		try {
			techRangeDao.deleteOldTeachRange(org_id, dep_id, grade_id, clas_id, cour_id, group_id, group_type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void replaceRange(String tech_ids, String org_id, String dep_id, String grade_id, String clas_id, String cour_id,
			String group_id, String group_type) {
		List<String> idList = Arrays.asList(tech_ids.split(","));
		// this.deleteOldTeachRange(org_id,dep_id,grade_id,clas_id,cour_id,group_id,group_type);
		TechRange[] oldArray = new TechRange[] {};
		// 替换部门管理员
		if (StringUtils.isNotEmpty(dep_id)) {
			oldArray = getOldTechRange(org_id, EnumRoleLabel.部门管理员.getCode().toString(), null, null, dep_id, null, null);
			for (TechRange techRange : oldArray) {
				techRange.setIs_del(Constant.IS_DEL_YES);
				techRange.setDel_time(new Date());
			}
			techRangeDao.delete(oldArray);
			for (String id : idList) {
				this.createRange(org_id, id, EnumRoleLabel.部门管理员.getCode().toString(), null, null, null, dep_id, null);
			}
			// 替任课教师
		} else if (StringUtils.isNotEmpty(clas_id) && StringUtils.isNotEmpty(cour_id) && StringUtils.isNotEmpty(grade_id)) {
			oldArray = getOldTechRange(org_id, EnumRoleLabel.任课教师.getCode().toString(), clas_id, null, null, null, cour_id);
			for (TechRange techRange : oldArray) {
				techRange.setIs_del(Constant.IS_DEL_YES);
				techRange.setDel_time(new Date());
			}
			techRangeDao.delete(oldArray);
			for (String id : idList) {
				this.createRange(org_id, id, EnumRoleLabel.任课教师.getCode().toString(), clas_id, grade_id, null, null, cour_id);
			}
			// 替换班主任
		} else if (StringUtils.isNotEmpty(clas_id)) {
			oldArray = getOldTechRange(org_id, EnumRoleLabel.班主任.getCode().toString(), clas_id, null, null, null, null);
			for (TechRange techRange : oldArray) {
				techRange.setIs_del(Constant.IS_DEL_YES);
				techRange.setDel_time(new Date());
			}
			techRangeDao.delete(oldArray);
			for (String id : idList) {
				this.createRange(org_id, id, EnumRoleLabel.班主任.getCode().toString(), clas_id, null, null, null, null);
			}
			// 替换年级组长
		} else if (StringUtils.isNotEmpty(grade_id)) {
			oldArray = getOldTechRange(org_id, EnumRoleLabel.年级组长.getCode().toString(), null, grade_id, null, null, null);
			for (TechRange techRange : oldArray) {
				techRange.setIs_del(Constant.IS_DEL_YES);
				techRange.setDel_time(new Date());
			}
			techRangeDao.delete(oldArray);
			for (String id : idList) {
				this.createRange(org_id, id, EnumRoleLabel.年级组长.getCode().toString(), null, grade_id, null, null, null);
			}
			// 替换组管理员
		} else if (StringUtils.isNotEmpty(group_id)) {
			if (Constant.GROUP_TYPE_STUDENT.toString().equals(group_type)) {
				oldArray = getOldTechRange(org_id, EnumRoleLabel.学生组管理员.getCode().toString(), null, null, null, group_id, null);
				for (TechRange techRange : oldArray) {
					techRange.setIs_del(Constant.IS_DEL_YES);
					techRange.setDel_time(new Date());
				}
				techRangeDao.delete(oldArray);
				for (String id : idList) {
					this.createRange(org_id, id, EnumRoleLabel.学生组管理员.getCode().toString(), null, null, group_id, null, null);
				}
			} else {
				oldArray = getOldTechRange(org_id, EnumRoleLabel.教师组管理员.getCode().toString(), null, null, null, group_id, null);
				for (TechRange techRange : oldArray) {
					techRange.setIs_del(Constant.IS_DEL_YES);
					techRange.setDel_time(new Date());
				}
				techRangeDao.delete(oldArray);
				for (String id : idList) {
					this.createRange(org_id, id, EnumRoleLabel.教师组管理员.getCode().toString(), null, null, group_id, null, null);
				}
			}
		} else {

		}
	}

	@Override
	public TechRange[] getOldTechRange(String org_id, String rl_id, String clas_id, String grade_id, String dep_id,
			String group_id, String cour_id) {
		return techRangeDao.getOldTechRange(org_id, rl_id, clas_id, grade_id, dep_id, group_id, cour_id);
	}

	@Override
	public void replaceRangeSK(String org_id, String tech_id, String cour_ids, List<TechRange> ranges) {
		List<String> idList = Arrays.asList(cour_ids.split(","));
		Map<String, TechRange> map = new HashMap<String, TechRange>();
		for (TechRange techRange : ranges) {
			// techRangeDao.deleteRange(techRange);
			map.put(techRange.getClas_id().toString(), techRange);
			techRangeDao.delete(techRange);

		}

		for (String cour_id : idList) {
			for (Map.Entry<String, TechRange> entry : map.entrySet()) {
				this.createRange(org_id, tech_id, EnumRoleLabel.任课教师.getCode().toString(), entry.getValue().getClas_id()
						.toString(), entry.getValue().getGrade_id().toString(), null, null, cour_id);
			}
		}
	}

	@Override
	public void updateBZRTeacher(String org_id, String grade_id, String clas_id, String tech_ids) {
		TechRange[] oldArray = new TechRange[] {};
		List<String> idList = null;
        if(StringUtils.isNotBlank(tech_ids))
        	idList = Arrays.asList(tech_ids.split(","));
		// 找到旧班主任，删除并增加新的班主任
//		oldArray = getOldTechRange(org_id, EnumRoleLabel.班主任.getCode().toString(), clas_id, null, null, null, null);
//		for (TechRange techRange : oldArray) {
//			techRange.setIs_del(Constant.IS_DEL_YES);
//			techRange.setDel_time(new Date());
//		}
        
        //获取班主任+授课信息
        oldArray = techRangeDao.getOldTechRangeOfClas(org_id,clas_id);
        
        
		techRangeDao.delete(oldArray);
		if (idList != null && idList.size() > 0) {
			for (String id : idList) {
				this.createRange(org_id, id, EnumRoleLabel.班主任.getCode().toString(), clas_id, null, null, null, null);
			}
		}
		// 给班主任设置课程，根据学校机构ID查询所有课程并添加
		Organization org = orgDao.get(Organization.class, Integer.valueOf(org_id).intValue());
		if (org != null && !org.getIs_del() && org.getType().intValue() == 0) {
			List<Course> courseList = courseDao.getCourseList(Integer.valueOf(org_id));
			if (courseList != null && courseList.size() > 0) {
				if (idList != null && idList.size() > 0) {
					Date current = new Date();
					for (String id : idList) {
						for (Course course : courseList) {
							int count = techRangeDao.checkIsExist(org_id, id, EnumRoleLabel.任课教师.getCode(), grade_id, clas_id, course.getCour_id().intValue());
							if (count <= 0) {
								// 当前教师没有这门课程的记录，进行新建
								TechRange tr = new TechRange();
								tr.setOrg_id(Integer.valueOf(org_id));
								tr.setTech_id(Integer.valueOf(id));
								tr.setRl_id(EnumRoleLabel.任课教师.getCode());
								tr.setGrade_id(Integer.valueOf(grade_id));
								tr.setClas_id(Integer.valueOf(clas_id));
								tr.setCour_id(course.getCour_id().intValue());
								tr.setHistory(0);
								tr.setInsert_time(current);
								tr.setIs_del(0);

								techRangeDao.save(tr);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<TechRange> getTechRangesByOrg(int org_id) {
		// TODO Auto-generated method stub
		return this.techRangeDao.getTechRangesByOrg(org_id);
	}

}
