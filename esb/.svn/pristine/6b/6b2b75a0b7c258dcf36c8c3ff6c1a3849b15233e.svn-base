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
import cn.edugate.esb.dao.IStudentDao;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.service.Class2StudentService;
/**
 * 班级服务实现类
 * Title:ClassesImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:57:49
 */
@Component
@Transactional("transactionManager")
public class Class2StudentImpl implements Class2StudentService {

	private static Logger logger = Logger.getLogger(Class2StudentImpl.class);
	
	private IClass2StudentDao class2StudentDao;
	private RedisClassStudentDao redisClassStudentDao;
	private IClassesDao classesDao;
	
	@Autowired
	public void setClassesDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	@Autowired
	public void setRedisClassStudentDao(RedisClassStudentDao redisClassStudentDao) {
		this.redisClassStudentDao = redisClassStudentDao;
	}

	@Autowired
	public void setClass2StudentDao(IClass2StudentDao class2StudentDao) {
		this.class2StudentDao = class2StudentDao;
	}
	
	private IStudentDao studentDao;
	@Autowired
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	private RedisClassesDao redisClassesDao;
	@Autowired
	public void setRedisClassesDao(RedisClassesDao redisClassesDao) {
		this.redisClassesDao = redisClassesDao;
	}

	@Override
	public void add(Clas2Student clas2student) {
		this.class2StudentDao.saveOrUpdate(clas2student);
	}

	@Override
	public List<Clas2Student> getAllList() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		List<Clas2Student> ls = this.class2StudentDao.list(Clas2Student.class, criterion, Order.desc("clas2stud_id"));
		return ls;
	}

	@Override
	public List<Classes> getClassesByStudId(String stud_id) {
		// TODO Auto-generated method stub
		List<Classes> ls = new ArrayList<Classes>();
		Map<String, Clas2Student> ccmap = this.redisClassStudentDao.getByStud_id(stud_id);
		if(ccmap!=null){
			List<String> ids = new ArrayList<String>(ccmap.keySet());
			for (String id : ids) {
				Classes cls = this.redisClassesDao.getClassesById(id, null);
				ls.add(cls);
			}
		}
		return ls;
	}

	@Override
	public List<Classes> queryClassesByStudId(Integer stud_id, Integer org_id) {
		// TODO Auto-generated method stub
		return this.classesDao.queryClassesByStudId(stud_id,org_id);
	}

	@Override
	public Clas2Student getByClas2stud_id(Integer clas2stud_id) {
		// TODO Auto-generated method stub
		return this.class2StudentDao.get(Clas2Student.class, clas2stud_id);
	}

	@Override
	public void deleteClassStudent(Integer clas2stud_id) {
		// TODO Auto-generated method stub
		this.class2StudentDao.deleteById(Clas2Student.class, clas2stud_id);
	}


	@Override
	public int batchMoveStudent(String originalClassID, String targetClassID, String[] studentIDArray) {
		if (studentIDArray != null && studentIDArray.length > 0) {
			for (String studentID : studentIDArray) {
				List<Clas2Student> c2sList = class2StudentDao.getClas2StudentList(Integer.valueOf(studentID),
						Integer.valueOf(originalClassID));
				Student student = studentDao.get(Student.class, Integer.valueOf(studentID));
				if (c2sList != null && c2sList.size() > 0) {
					for (Clas2Student c2s : c2sList) {
						c2s.setIs_del(true);
						c2s.setDel_time(new Date());
						c2s.setOrg_id(student.getOrg_id());
						class2StudentDao.update(c2s);
					}
				}
				// 把学生和新班级的关系保存
				Clas2Student c2s = new Clas2Student();
				c2s.setClas_id(Integer.valueOf(targetClassID));
				c2s.setStud_id(Integer.valueOf(studentID));
				c2s.setInsert_time(new Date());
				c2s.setIs_del(false);
				c2s.setStud_name(student.getStud_name());
				c2s.setUser_id(student.getUser_id());
				
				Classes classes = redisClassesDao.getClassesById(targetClassID, null);
				
				c2s.setClas_name(classes.getClas_name());
				c2s.setOrg_id(student.getOrg_id());
				class2StudentDao.save(c2s);
			}
		}

		return 0;
	}

	@Override
	public Clas2Student getClas2Student(Integer stud_id, Integer clas_id) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false))
				.add(Restrictions.eq("stud_id", stud_id))
				.add(Restrictions.eq("clas_id", clas_id));
		List<Clas2Student> ls = this.class2StudentDao.list(Clas2Student.class, criterion, Order.desc("clas2stud_id"));
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public List<Clas2Student> getClas2StudentList() {
		// TODO Auto-generated method stub
		return this.class2StudentDao.getClas2StudentList();
	}

	@Override
	public void deleteOld(Integer stud_id, Integer org_id) {
		List<Clas2Student> list = class2StudentDao.getOldForDelete(stud_id,org_id);
		for (Clas2Student clas2Student : list) {
			clas2Student.setIs_del(true);
			class2StudentDao.saveOrUpdate(clas2Student);
		}
	}

	@Override
	public List<Clas2Student> getClas2StudentByOrgId(Integer org_id) {
		return class2StudentDao.getClass2StudentListIncludeDeleted(org_id);
	}

	@Override
	public List<Clas2Student> getClas2StudentByClassId(Integer clas_id) {
		return class2StudentDao.getClas2StudentByClassId(clas_id);
	}


}
