package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.service.StudentService;

@Cache(name="学生家长关系缓存",entities={StudentParent.class})
public class StudentParentCacheProvider  implements CacheProvider<StudentParent>,java.io.Serializable{

	private static final long serialVersionUID = -9172041061763013137L;

	static Logger logger=LoggerFactory.getLogger(StudentParentCacheProvider.class);

	private RedisStudentParentDao redisStudentParentDao;
	private StudentService studentService;
	
	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Autowired
	public void setRedisStudentParentDao(RedisStudentParentDao redisStudentParentDao) {
		this.redisStudentParentDao = redisStudentParentDao;
	}

	@SuppressWarnings("unused")
	private ParentService parentService;	
	
	@Autowired
	public void setParentService(ParentService parentService) {
		this.parentService = parentService;
	}

	@Override
	public void add(StudentParent sp) {
		this.redisStudentParentDao.add(sp);
	}

	@Override
	public void update(StudentParent sp) {
		StudentParent old_sp = this.redisStudentParentDao.getByStud2parent_id(sp.getStud2parent_id().toString());
		if(null!=old_sp.getStud2parent_id()){
			this.redisStudentParentDao.delete(old_sp);
		}
		this.redisStudentParentDao.add(sp);
	}

	@Override
	public void delete(StudentParent sp) {
		this.redisStudentParentDao.delete(sp);
	}

	@Override
	public void refreshAll() {
		this.redisStudentParentDao.deleteAll();
		List<StudentParent> list = this.studentService.getAllStudentParent();
		this.redisStudentParentDao.add(list);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<StudentParent> list = redisStudentParentDao.getStudParentByOrgId(org_id);
		for (StudentParent sp : list) {  
			redisStudentParentDao.delete(sp);
		}
		List<StudentParent> studentParents = studentService.getStudentParentByOrgId(Integer.parseInt(org_id));
		this.redisStudentParentDao.add(studentParents);
	}

}
