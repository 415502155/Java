package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Right;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisRightDao;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="权限配置缓存",entities={Right.class})
public class RightCacheProvider implements CacheProvider<Right>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(RightCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisRightDao redisRightDao;
//	private IRightDao rightDao;
	
	@Autowired
	public void setRedisRightDao(RedisRightDao redisRightDao) {
		this.redisRightDao = redisRightDao;
	}
//	@Autowired
//	public void setRightDao(IRightDao rightDao) {
//		this.rightDao = rightDao;
//	}

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(Right rt) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",rt);
		Right olduser = this.redisRightDao.getByRightId(rt.getRight_id().toString());
		if(olduser!=null)
			this.redisRightDao.delete(olduser);
		this.redisRightDao.add(rt);
	}

	@Override
	public void add(Right rt) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",rt);
		this.redisRightDao.add(rt);
	}

	@Override
	public void delete(Right rt) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",rt);
		this.redisRightDao.delete(rt);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisRightDao.deleteAll();
		List<Right> rts = this.userService.getRights();
		this.redisRightDao.add(rts);
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
