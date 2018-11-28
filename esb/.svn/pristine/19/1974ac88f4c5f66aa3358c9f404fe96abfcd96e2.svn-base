package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.service.GradeService;

/**
 * 年级缓存
 * Title:GradeCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:06:14
 */
@Cache(name="年级缓存",entities={Grade.class})
public class GradeCacheProvider implements CacheProvider<Grade>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;

	static Logger logger=LoggerFactory.getLogger(GradeCacheProvider.class);
	
	private RedisGradeDao redisGradeDao;
	@Autowired
	public void setRedisGradeDao(RedisGradeDao redisGradeDao) {
		this.redisGradeDao = redisGradeDao;
	}
	private GradeService gradeService;
	@Autowired
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
	@Override
	public void add(Grade grade) {
		logger.info("gradeRedis===add====",grade);
		this.redisGradeDao.addGrade(grade);
	}

	@Override
	public void update(Grade grade) {
		logger.info("gradeRedis===update====",grade);
		Grade oldGrade = this.redisGradeDao.getGradeById(grade.getGrade_id(),null);
		if(oldGrade!=null)
			this.redisGradeDao.deleteGrade(oldGrade);
		this.redisGradeDao.addGrade(grade);
	}

	@Override
	public void delete(Grade grade) {
		logger.info("gradeRedis===delete====",grade);
		this.redisGradeDao.deleteGrade(grade);
	}

	@Override
	public void refreshAll() {
		this.redisGradeDao.deleteAllGrades();
		List<Grade> grades = this.gradeService.getAll();
		this.redisGradeDao.addGrades(grades);	
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Grade> list = redisGradeDao.getGrades(Integer.parseInt(org_id));
		for (Grade g : list) {
			redisGradeDao.deleteGrade(g);
		}
		Grade grade = new Grade();
		grade.setOrg_id(Integer.parseInt(org_id));
		List<Grade> grades = gradeService.getAllList(grade);
		this.redisGradeDao.addGrades(grades);
	}

}
