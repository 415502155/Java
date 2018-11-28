package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.service.CampusService;

@Cache(name="校区缓存",entities={Campus.class})
public class CampusCacheProvider  implements CacheProvider<Campus>,java.io.Serializable{
	private static final long serialVersionUID = -8931067259320047018L;

	static Logger logger=LoggerFactory.getLogger(CampusCacheProvider.class);
	
	private RedisCampusDao redisCampusDao;
	@Autowired
	public void setRedisCampusDao(RedisCampusDao redisCampusDao) {
		this.redisCampusDao = redisCampusDao;
	}
	private CampusService CampusService;
	@Autowired
	public void setCampusService(CampusService CampusService) {
		this.CampusService = CampusService;
	}
	
	@Override
	public void add(Campus campus) {
		logger.info("CampusRedis===add====",campus);
		this.redisCampusDao.add(campus);
	}

	@Override
	public void update(Campus campus) {
		logger.info("CampusRedis===update====",campus);
		Campus oldCampus = redisCampusDao.getById(campus.getCam_id());
		if(oldCampus!=null)
			this.redisCampusDao.delete(oldCampus);
		this.redisCampusDao.add(campus);
	}

	@Override
	public void delete(Campus campus) {
		logger.info("CampusRedis===delete====",campus);
		this.redisCampusDao.delete(campus);
	} 

	@Override 
	public void refreshAll() {
		this.redisCampusDao.deleteAll();
		//List<Campus> Campus = this.CampusService.getAll();
		List<Campus> Campus = this.CampusService.getAll();
		this.redisCampusDao.add(Campus);	
	}

	@Override
	public void refreshOrg(String org_id) {
		try {
			List<Campus> list = redisCampusDao.getCampusByOrgId(org_id);
			for (Campus campus : list) {
				redisCampusDao.delete(campus);
			}
		} catch (Exception e) {
		}
		List<Campus> Campus = this.CampusService.getByOrgId(Integer.parseInt(org_id));
		this.redisCampusDao.add(Campus);
	}

}
