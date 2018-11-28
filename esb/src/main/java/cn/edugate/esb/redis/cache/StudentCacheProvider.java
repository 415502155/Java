package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Student;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.service.StudentService;

@Cache(name="学生缓存",entities={Student.class})
public class StudentCacheProvider  implements CacheProvider<Student>,java.io.Serializable{

	private static final long serialVersionUID = -9172041061763013137L;

	static Logger logger=LoggerFactory.getLogger(StudentCacheProvider.class);

	private RedisStudentDao redisStudentDao;	
	@Autowired
	public void setRedisStudentDao(RedisStudentDao redisStudentDao) {
		this.redisStudentDao = redisStudentDao;
	}

	private StudentService studentService;
	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	
	@Override
	public void add(Student student) {
		redisStudentDao.add(student);
	}

	@Override
	public void update(Student student) {
		Student old = redisStudentDao.getByStudentId(student.getStud_id());
		if(old!=null)
			this.redisStudentDao.delete(old);
		this.redisStudentDao.add(student);
	}

	@Override
	public void delete(Student student) {
		this.redisStudentDao.delete(student);
	}

	@Override
	public void refreshAll() {
		this.redisStudentDao.deleteAll();
		List<Student> list = studentService.getAll();
		this.redisStudentDao.add(list);
	}


	@Override
	public void refreshOrg(String org_id) {
		Map<String,Student> list = redisStudentDao.getStudentsByOrgId(Integer.parseInt(org_id));
		if(null!=list&&!list.isEmpty()){
			for (Student s : list.values()) {  
				redisStudentDao.delete(s);
			}
		}
		Student student = new Student();
		student.setOrg_id(Integer.parseInt(org_id));
		List<Student> students = studentService.getStudents(student);
		this.redisStudentDao.add(students);
	}

}
