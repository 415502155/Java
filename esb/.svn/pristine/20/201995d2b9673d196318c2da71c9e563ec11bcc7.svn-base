package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="中心用户缓存",entities={UcUser.class})
public class UcUserCacheProvider implements CacheProvider<UcUser>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(UcUserCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisUcUserDao redisUcUserDao;
	private UserService userService;
	@Autowired
	public void setRedisUcUserDao(RedisUcUserDao redisUcUserDao) {
		this.redisUcUserDao = redisUcUserDao;
	}	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(UcUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		UcUser olduser = this.redisUcUserDao.getUserById(user.getUc_id().toString());
		if(olduser!=null)
			this.redisUcUserDao.deleteUsers(olduser);
		this.redisUcUserDao.addUsers(user);
	}

	@Override
	public void add(UcUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisUcUserDao.addUsers(user);
	}

	@Override
	public void delete(UcUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisUcUserDao.deleteUsers(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisUcUserDao.deleteAll();
		List<UcUser> users = this.userService.getUcUserList();
		this.redisUcUserDao.addUsers(users);
	}
	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
