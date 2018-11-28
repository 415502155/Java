package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.service.ClassesService;

/**
 * 班级缓存
 * Title:ClassesCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月25日下午5:45:18
 */
@Cache(name="班级缓存",entities={Classes.class})
public class ClassesCacheProvider implements CacheProvider<Classes>,java.io.Serializable {

	private static final long serialVersionUID = -8931067259320047018L;

	static Logger logger=LoggerFactory.getLogger(ClassesCacheProvider.class);
	
	private RedisClassesDao redisClassesDao;
	@Autowired
	public void setRedisClassesDao(RedisClassesDao redisClassesDao) {
		this.redisClassesDao = redisClassesDao;
	}
	private ClassesService classesService;
	@Autowired
	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}
	
	@Override
	public void add(Classes classes) {
		logger.info("classesRedis===add====",classes);
		this.redisClassesDao.addClasses(classes);
	}

	@Override
	public void update(Classes classes) {
		logger.info("classesRedis===update====",classes);
		Classes oldClasses = this.redisClassesDao.getClassesById(classes.getClas_id().toString(),null);
		if(oldClasses!=null)
			this.redisClassesDao.deleteClasses(oldClasses);
		this.redisClassesDao.addClasses(classes);
	}

	@Override
	public void delete(Classes classes) {
		logger.info("classesRedis===delete====",classes);
		this.redisClassesDao.deleteClasses(classes);
	} 

	@Override 
	public void refreshAll() {
		this.redisClassesDao.deleteAllClassess();
		//List<Classes> classes = this.classesService.getAll();
		List<Classes> classes = this.classesService.getAllList(0,0,0,0);
		this.redisClassesDao.addClassess(classes);	
	}

	@Override
	public void refreshOrg(String org_id) {
		try {
			List<Classes> list = redisClassesDao.getClassesOfOrg(Integer.parseInt(org_id));
			for (Classes classes : list) {
				redisClassesDao.deleteClasses(classes);
			}
		} catch (Exception e) {
		}
		List<Classes> classes = this.classesService.getAllList(Integer.parseInt(org_id),0,0,0);
		this.redisClassesDao.addClassess(classes);
	}

}
