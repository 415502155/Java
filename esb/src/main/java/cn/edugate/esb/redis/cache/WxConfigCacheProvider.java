package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.WxConfig;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisWxConfigDao;
import cn.edugate.esb.service.WxConfigService;

/**
 * WxConfigCacheProvider
 */
@Cache(name="微信配置缓存",entities={WxConfig.class})
public class WxConfigCacheProvider implements CacheProvider<WxConfig>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;

	static Logger logger=LoggerFactory.getLogger(WxConfigCacheProvider.class);
	
	
	private RedisWxConfigDao redisWxConfigDao;
	private WxConfigService wxConfigService;	
	
	@Autowired
	public void setWxConfigService(WxConfigService wxConfigService) {
		this.wxConfigService = wxConfigService;
	}

	@Autowired
	public void setRedisWxConfigDao(RedisWxConfigDao redisWxConfigDao) {
		this.redisWxConfigDao = redisWxConfigDao;
	}
	

	@Override
	public void add(WxConfig gp) {
		logger.info("groupRedis===add====",gp);
		this.redisWxConfigDao.add(gp);
	}

	@Override
	public void update(WxConfig gp) {
		logger.info("groupRedis===update====",gp);
		this.redisWxConfigDao.add(gp);	
	}
	

	@Override
	public void delete(WxConfig gp) {		
		this.redisWxConfigDao.delete(gp);		
	}

	@Override
	public void refreshAll() {
		this.redisWxConfigDao.deleteAll();
		List<WxConfig> configs = this.wxConfigService.getAll();
		this.redisWxConfigDao.add(configs);	
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
