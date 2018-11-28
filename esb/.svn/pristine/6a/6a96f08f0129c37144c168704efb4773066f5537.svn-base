package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.redis.dao.RedisGrade2ClasDao;
import cn.edugate.esb.service.GradeService;
import cn.edugate.esb.service.StudentService;

/**
 * WxConfigCacheProvider
 */
@Cache(name="年级班级缓存",entities={Grade2Clas.class})
public class Grade2ClasCacheProvider implements CacheProvider<Grade2Clas>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;
	static Logger logger=LoggerFactory.getLogger(Grade2ClasCacheProvider.class);
	
	private RedisGrade2ClasDao redisGrade2ClasDao;
	private GradeService gradeService;	
	
	@Autowired
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@Autowired
	public void setRedisGrade2ClasDao(RedisGrade2ClasDao redisGrade2ClasDao) {
		this.redisGrade2ClasDao = redisGrade2ClasDao;
	}

	@Override
	public void add(Grade2Clas gp) {
		logger.info("CardCache===add====",gp);
		this.redisGrade2ClasDao.add(gp);
	}

	@Override
	public void update(Grade2Clas gp) {
		logger.info("CardCache===update====",gp);
		Grade2Clas old = this.redisGrade2ClasDao.getById(gp.getGra2cls_id());
		if(old!=null){
			this.redisGrade2ClasDao.delete(old);
		}
		this.redisGrade2ClasDao.add(gp);	
	}
	

	@Override
	public void delete(Grade2Clas gp) {		
		this.redisGrade2ClasDao.delete(gp);		
	}

	@Override
	public void refreshAll() {
		this.redisGrade2ClasDao.deleteAll();
		List<Grade2Clas> configs = this.gradeService.getAllGrade2Clas();
		this.redisGrade2ClasDao.add(configs);	
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
