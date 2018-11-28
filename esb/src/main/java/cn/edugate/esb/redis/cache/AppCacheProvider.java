package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisAppDao;
import cn.edugate.esb.service.AppService;

/**
 * 班级缓存
 * Title:ClassesCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月25日下午5:45:18
 */
@Cache(name="sso应用缓存",entities={App.class})
public class AppCacheProvider implements CacheProvider<App>,java.io.Serializable {

	private static final long serialVersionUID = -8931067259320047018L;

	static Logger logger=LoggerFactory.getLogger(AppCacheProvider.class);
	
	private RedisAppDao redisAppDao;
	private AppService appService;	
	
	@Autowired
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	@Autowired
	public void setRedisAppDao(RedisAppDao redisAppDao) {
		this.redisAppDao = redisAppDao;
	}
	@Override
	public void add(App app) {
		// TODO Auto-generated method stub
		this.redisAppDao.addApp(app);
	}
	@Override
	public void update(App app) {
		// TODO Auto-generated method stub
		App oldApp = this.redisAppDao.getAppById(app.getA_id().toString());
		if(oldApp!=null){
			this.redisAppDao.deleteApp(oldApp);
		}
		this.redisAppDao.addApp(app);
		
	}
	@Override
	public void delete(App app) {
		// TODO Auto-generated method stub
		this.redisAppDao.deleteApp(app);
	}
	@Override
	public void refreshAll() {
		this.redisAppDao.deleteAllApp();
		List<App> apps = this.appService.getAll();
		this.redisAppDao.addApp(apps);
	}
	
	@Override
	public void refreshOrg(String org_id) {
		try {
			this.redisAppDao.deleteAllApp();
		} catch (Exception e) {
		}
		List<App> apps = this.appService.getAll();
		this.redisAppDao.addApp(apps);
	}
	

}
