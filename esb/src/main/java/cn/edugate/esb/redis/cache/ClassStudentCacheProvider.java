package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.service.Class2StudentService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="班级学生关系缓存",entities={Clas2Student.class})
public class ClassStudentCacheProvider implements CacheProvider<Clas2Student>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(ClassStudentCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisClassStudentDao redisClassStudentDao;
	private Class2StudentService class2StudentService;
	@Autowired
	public void setClass2StudentService(Class2StudentService class2StudentService) {
		this.class2StudentService = class2StudentService;
	}

	@Autowired
	public void setRedisClassStudentDao(RedisClassStudentDao redisClassStudentDao) {
		this.redisClassStudentDao = redisClassStudentDao;
	}
	
	@Override
	public void update(Clas2Student cs) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",cs);
		Clas2Student olduser = this.redisClassStudentDao.getById(cs.getClas2stud_id().toString());
		if(olduser!=null)
			this.redisClassStudentDao.delete(olduser);
		this.redisClassStudentDao.add(cs);
	}

	@Override
	public void add(Clas2Student cs) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",cs);
		this.redisClassStudentDao.add(cs);
	}

	@Override
	public void delete(Clas2Student cs) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",cs);
		this.redisClassStudentDao.delete(cs);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisClassStudentDao.deleteAll();
		//List<Clas2Student> users = this.class2StudentService.getAllList();
		List<Clas2Student> users = this.class2StudentService.getClas2StudentList();
		this.redisClassStudentDao.adds(users);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Clas2Student> list = redisClassStudentDao.getByOrgId(org_id);
		for (Clas2Student cs : list) {  
			redisClassStudentDao.delete(cs);
		}
		List<Clas2Student> clas2Students = class2StudentService.getClas2StudentByOrgId(Integer.parseInt(org_id));
		this.redisClassStudentDao.adds(clas2Students);
	}

}
