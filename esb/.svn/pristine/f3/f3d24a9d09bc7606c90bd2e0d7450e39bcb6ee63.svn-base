package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IClass2StudentDao;
import cn.edugate.esb.dao.IClassesDao;
import cn.edugate.esb.dao.IGrade2ClassDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.ClassesService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

/**
 * 班级服务实现类 Title:ClassesImpl Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月19日上午9:57:49
 */
@Component
@Transactional("transactionManager")
public class ClassesImpl implements ClassesService {

	private static Logger logger = Logger.getLogger(ClassesImpl.class);

	private IClassesDao classesDao;

	@SuppressWarnings("unused")
	private RedisTeacherDao redisTeacherDao;

	private RedisTechRangeDao redisTechRangeDao;

	private IGrade2ClassDao grade2ClassDao;
	
	private RedisGradeDao redisGradeDao;
	
	@Autowired
	public void setRedisGradeDao(RedisGradeDao redisGradeDao) {
		this.redisGradeDao = redisGradeDao;
	}

	@Autowired
	public void setGrade2ClassDao(IGrade2ClassDao grade2ClassDao) {
		this.grade2ClassDao = grade2ClassDao;
	}

	@Autowired
	public void setRedisTechRangeDao(RedisTechRangeDao redisTechRangeDao) {
		this.redisTechRangeDao = redisTechRangeDao;
	}

	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}

	@Autowired
	public void setClassesDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}
	
	@Autowired
	private IClass2StudentDao class2StudentDao;
	
	@Autowired
	private ITechRangeDao teacherRangeDao;
	
	

	@Override
	public void add(Classes classes) {
		try {
			classesDao.saveOrUpdate(classes);
		} catch (Exception e) {
			logger.error("Classes add error");
		}
	}

	@Override
	public Integer delete(Classes classes) {
		Classes oldClasses = new Classes();
		try {
			oldClasses = classesDao.get(Classes.class, classes.getClas_id());
			oldClasses.setIs_del(Constant.IS_DEL_YES);
			oldClasses.setDel_time(new Date());
			classesDao.update(oldClasses);
		} catch (Exception e) {
			logger.error("Classes delete error");
			return null;
		}
		return oldClasses.getClas_id();
	}

	@Override
	public Classes update(Classes classes) {
		try {
			classes.setUpdate_time(new Date());
			classesDao.update(classes);
		} catch (Exception e) {
			logger.error("Classes update error");
			return null;
		}
		return classes;
	}

	@Override
	public Classes getById(Integer id) {
		Classes classes = new Classes();
		try {
			classes = classesDao.get(Classes.class, id);
		} catch (Exception e) {
			logger.error("Classes getById error");
			return null;
		}
		return classes;
	}

	@Override
	public List<Classes> getAll() {
		Criterion criterion = Restrictions.conjunction();
		try {
			return classesDao.list(Classes.class, criterion);
		} catch (Exception e) {
			logger.error("Classes getAll error:" + e.getMessage());
			return null;
		}
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Classes> getAllList(int orgId, int camId, int isGraduated, int gradeNumber) {
		List<Classes> list = new ArrayList<Classes>();
		try {
			list = classesDao.getClasses(orgId, camId, isGraduated, gradeNumber);
		} catch (Exception e) {
			logger.error("Classes getClassess error");
			return null;
		}
		return list;
	}

	@Override
	public void getPaging(Integer org_id, Integer is_graduated, Integer cam_id, Integer grade_id, Paging<Classes> pages) {
		// TODO Auto-generated method stub
		Long total = this.classesDao.getTotalCount(org_id, is_graduated, cam_id, grade_id);
		pages.setTotal(total);
		this.classesDao.getPaging(org_id, is_graduated, cam_id, grade_id, pages);
		for (Classes classitem : pages.getData()) {
			Map<String, Object> classMaster = this.redisTechRangeDao.getSkClasBZR(classitem.getClas_id().toString(), null);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<Teacher> ls = new ArrayList(classMaster.values());
			classitem.setMasters(ls);
		}
	}

	@Override
	public void addGrade2Class(Grade2Clas grade2Clas) {
		// TODO Auto-generated method stub
		// Criterion criterion = Restrictions.and(Restrictions.eq("grade_id", grade2Clas.getGrade_id()))
		// .add(Restrictions.eq("clas_id", grade2Clas.getClas_id()));
		Criterion criterion = Restrictions.and(Restrictions.eq("clas_id", grade2Clas.getClas_id()));
		List<Grade2Clas> grade2ClasList = this.grade2ClassDao.list(Grade2Clas.class, criterion, Order.desc("gra2cls_id"));
		if (grade2ClasList.size() > 0) {
			grade2ClasList.get(0).setIs_del(false);
			Grade2Clas grade2Clas1 = grade2ClasList.get(0);
			grade2Clas1.setGrade_id(grade2Clas.getGrade_id());
			this.grade2ClassDao.update(grade2Clas1);
		} else {
			this.grade2ClassDao.save(grade2Clas);
		}
	}

	@Override
	public void deleteById(Integer clas_id) {
		// TODO Auto-generated method stub
		Date currentDate = new Date();
		Classes cls = this.classesDao.get(Classes.class, clas_id);
		cls.setIs_del(1);
		cls.setDel_time(currentDate);
		this.classesDao.saveOrUpdate(cls);
		
		// 查询班级和学生的关联
		List<Clas2Student> class2StudentList = class2StudentDao.getClass2StudentListByClassId(clas_id.intValue());
		if (class2StudentList != null && class2StudentList.size() > 0) {
			for (Clas2Student c2s : class2StudentList) {
				c2s.setIs_del(true);
				c2s.setDel_time(currentDate);
				class2StudentDao.update(c2s);
			}
		}
		
		// 查询年级和班级的关联
		List<Grade2Clas> grade2ClassList = grade2ClassDao.getGrade2ClassListByClassId(clas_id.intValue());
		if (grade2ClassList != null && grade2ClassList.size() > 0) {
			for (Grade2Clas g2c : grade2ClassList) {
				g2c.setIs_del(true);
				g2c.setDel_time(currentDate);
				grade2ClassDao.update(g2c);
			}
		}
		
		// 查询班主任和授课教师
		List<TechRange> techRangeList = teacherRangeDao.getTechRangeListByClassId(clas_id.intValue());
		if (techRangeList != null && techRangeList.size() > 0) {
			for (TechRange tr : techRangeList) {
				tr.setIs_del(1);
				tr.setDel_time(currentDate);
				teacherRangeDao.update(tr);
			}
		}
	}

	@Override
	public List<Classes> getClassByOrgIdCamIdGradeId(Integer org_id, Integer cam_id, Integer grade_id) {
		// TODO Auto-generated method stub
		List<Classes> ls = this.classesDao.getClassByOrgIdCamIdGradeId(org_id, cam_id, grade_id);
		return ls;
	}

	@Override
	public Classes getClassByName(Integer grade_id, String clas_name) {
		// TODO Auto-generated method stub
		return this.classesDao.getClassByName(grade_id, clas_name);
	}

	@Override
	public List<Classes> getClassesOfOrg(Integer org_id) {
		return this.classesDao.getClassesOfOrg(org_id);
	}

	@Override
	public Classes modifyClassInfo(Classes classEntity, int gradeId) {
		int originalGradeId = classEntity.getGrade_id();
		classEntity.setUpdate_time(new Date());
		classEntity.setGrade_id(gradeId);
		classesDao.update(classEntity);
		
		if (originalGradeId != gradeId) {
			// 班级所属的年级发生变化
			List<Grade2Clas> g2cList = grade2ClassDao.getGrade2ClassListByClassId(classEntity.getClas_id());
			if (g2cList != null && g2cList.size() > 0) {
				Date deleteDate = new Date();
				for (Grade2Clas g2c : g2cList) {
					g2c.setIs_del(true);
					g2c.setDel_time(deleteDate);
					grade2ClassDao.update(g2c);
				}
			}
			
			Grade2Clas grade2Clas = new Grade2Clas();
			grade2Clas.setClas_id(classEntity.getClas_id());
			grade2Clas.setGrade_id(gradeId);
			grade2Clas.setInsert_time(new Date());
			grade2Clas.setIs_del(false);
			grade2ClassDao.save(grade2Clas);
		} else {
			// 查询记录是否真实存在
			Grade2Clas g2c = grade2ClassDao.getGrade2Clas(gradeId, classEntity.getClas_id());
			if (g2c == null || g2c.getGra2cls_id() == null || g2c.getGra2cls_id().intValue() <= 0) {
				g2c = new Grade2Clas();
				g2c.setClas_id(classEntity.getClas_id());
				g2c.setGrade_id(gradeId);
				g2c.setInsert_time(new Date());
				g2c.setIs_del(false);
				grade2ClassDao.save(g2c);
			}
			
			// 查询是否班级是否还和别的年级有关联，如果有即为脏数据，进行删除处理
			List<Grade2Clas> dirtyRecords = grade2ClassDao.getDirtyRecords(gradeId, classEntity.getClas_id());
			if (dirtyRecords != null && dirtyRecords.size() > 0) {
				Date deleteDate = new Date();
				for (Grade2Clas dirtyRecord : dirtyRecords) {
					dirtyRecord.setIs_del(true);
					dirtyRecord.setDel_time(deleteDate);
					grade2ClassDao.update(dirtyRecord);
				}
			}
		}
		
		return classEntity;
	}

	@Override
	public Grade getGradeByClassId(Integer clas_id) {
		// TODO Auto-generated method stub
		Grade rdata = null;
		List<Grade2Clas> ls = this.grade2ClassDao.getGrade2ClassListByClassId(clas_id);
		if(ls!=null&&ls.size()>0){
			rdata = this.redisGradeDao.getGradeById(ls.get(0).getGrade_id(), null);			
		}
		return rdata;
	}

	@Override
	public Classes getClassByNameOrg(Integer org_id,String clas_name) {
		// TODO Auto-generated method stub
		return this.classesDao.getClassByNameOrg(org_id,clas_name);
	}

}
