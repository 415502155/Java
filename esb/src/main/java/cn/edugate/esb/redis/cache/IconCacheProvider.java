package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Icon;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisIconDao;
import cn.edugate.esb.service.IconService;

/**
 * Title: IconCacheProvider
 * Description:图标缓存 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月15日上午10:58:07
 */
@Cache(name="图标缓存",entities={Icon.class})
public class IconCacheProvider implements CacheProvider<Icon>,java.io.Serializable {

	private static final long serialVersionUID = -6894606317707223119L;
	
	@Autowired
	private RedisIconDao redisIconDao;
	
	@Autowired
	private IconService iconService;

	@Override
	public void refreshAll() {
		this.redisIconDao.deleteAll();
		Map<Integer,List<Icon>> map = iconService.getIconList();
		this.redisIconDao.add(map);
	}

	@Override
	public void add(Icon en) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Icon en) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Icon en) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshOrg(String org_id) {
		iconService.refreshDB();
	}

}
