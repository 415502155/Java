package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="机构用户缓存",entities={OrgUser.class})
public class OrgUserCacheProvider implements CacheProvider<OrgUser>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(OrgUserCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisOrgUserDao redisOrgUserDao;
	@Autowired
	public void setRedisOrgUserDao(RedisOrgUserDao redisOrgUserDao) {
		this.redisOrgUserDao = redisOrgUserDao;
	}
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(OrgUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		OrgUser olduser = this.redisOrgUserDao.getUserById(user.getUser_id().toString());
		if(olduser!=null){
			this.redisOrgUserDao.deleteUsers(olduser);
		}
		this.redisOrgUserDao.addUsers(user);
	}

	@Override
	public void add(OrgUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisOrgUserDao.addUsers(user);
	}

	@Override
	public void delete(OrgUser user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisOrgUserDao.deleteUsers(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisOrgUserDao.deleteAll();
		List<OrgUser> users = this.userService.getOrgUserList();
		this.redisOrgUserDao.addUsers(users);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<OrgUser> list = redisOrgUserDao.getUserByOrgId(Integer.parseInt(org_id));
		for (OrgUser user : list) {  
			redisOrgUserDao.deleteUsers(user);
		}
		List<OrgUser> orgUsers = userService.getOrguserByOrgId(Integer.parseInt(org_id));
		this.redisOrgUserDao.addUsers(orgUsers);
	}

}
