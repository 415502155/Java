package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="认证用户关系缓存",entities={UcuserOrguser.class})
public class UcuserOrguserCacheProvider implements CacheProvider<UcuserOrguser>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(UcuserOrguserCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private RedisUcUserOrguserDao redisUcUserOrguserDao;
	@Autowired
	public void setRedisUcUserOrguserDao(RedisUcUserOrguserDao redisUcUserOrguserDao) {
		this.redisUcUserOrguserDao = redisUcUserOrguserDao;
	}	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(UcuserOrguser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		UcuserOrguser olduser = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
		if(olduser!=null)
			this.redisUcUserOrguserDao.delete(olduser);
		this.redisUcUserOrguserDao.add(user);
	}

	@Override
	public void add(UcuserOrguser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisUcUserOrguserDao.add(user);
	}

	@Override
	public void delete(UcuserOrguser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisUcUserOrguserDao.delete(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisUcUserOrguserDao.deleteAll();
		List<UcuserOrguser> users = this.userService.getUcuserOrguserList();
		this.redisUcUserOrguserDao.adds(users);
	}
	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
