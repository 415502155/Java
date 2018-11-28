package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisRoleLabelDao;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="角色标签缓存信息",entities={RoleLabel.class})
public class RoleLabelCacheProvider implements CacheProvider<RoleLabel>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(RoleLabelCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisRoleLabelDao redisRoleLabelDao;
	
	@Autowired
	public void setRedisRoleLabelDao(RedisRoleLabelDao redisRoleLabelDao) {
		this.redisRoleLabelDao = redisRoleLabelDao;
	}

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(RoleLabel tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",tr);
		RoleLabel oldtr = this.redisRoleLabelDao.getById(tr.getRl_id().toString());
		if(oldtr!=null)
			this.redisRoleLabelDao.delete(oldtr);
		this.redisRoleLabelDao.add(tr);
	}

	@Override
	public void add(RoleLabel tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",tr);
		this.redisRoleLabelDao.add(tr);
	}

	@Override
	public void delete(RoleLabel tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",tr);
		this.redisRoleLabelDao.delete(tr);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisRoleLabelDao.deleteAll();
		List<RoleLabel> trs = this.userService.getRoleLabels();
		this.redisRoleLabelDao.add(trs);
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
