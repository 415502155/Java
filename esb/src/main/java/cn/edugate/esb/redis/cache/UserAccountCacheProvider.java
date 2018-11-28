package cn.edugate.esb.redis.cache;

import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.dao.IUserAccountDao;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisUserAccountDao;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="用户登陆信息缓存",entities={UserAccount.class})
public class UserAccountCacheProvider implements CacheProvider<UserAccount>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(UserAccountCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisUserAccountDao redisUserAccountDao;
	private IUserAccountDao userAccountDao;
	@Autowired
	public void setUserAccountDao(IUserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	@Autowired
	public void setRedisUserAccountDao(RedisUserAccountDao redisUserAccountDao) {
		this.redisUserAccountDao = redisUserAccountDao;
	}
	
	@Override
	public void update(UserAccount user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		this.redisUserAccountDao.delete(user);
		this.redisUserAccountDao.add(user);
	}

	@Override
	public void add(UserAccount user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisUserAccountDao.add(user);
	}

	@Override
	public void delete(UserAccount user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisUserAccountDao.delete(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisUserAccountDao.deleteAll();
		Criterion criterion = Restrictions.conjunction();
		List<UserAccount> users = this.userAccountDao.list(UserAccount.class, criterion, Order.asc("ua_id"));
		this.redisUserAccountDao.add(users);
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
