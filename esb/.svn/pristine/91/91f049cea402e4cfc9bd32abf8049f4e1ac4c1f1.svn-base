package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Role;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="角色权限缓存",entities={Role.class})
public class RoleCacheProvider implements CacheProvider<Role>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(RoleCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisRoleDao redisRoleDao;
	
	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	@Override
	public void update(Role user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		Role olduser = this.redisRoleDao.getByRoleId(user.getRole_id().toString());
		if(olduser!=null)
			this.redisRoleDao.delete(olduser);
		this.redisRoleDao.add(user);
	}

	@Override
	public void add(Role user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisRoleDao.add(user);
	}

	@Override
	public void delete(Role user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisRoleDao.delete(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisRoleDao.deleteAll();
		List<Role> trs = this.userService.getRoles();
		this.redisRoleDao.add(trs);
	}

	@Override
	public void refreshOrg(String org_id) {
		Map<String,Role> list = redisRoleDao.getByOrgId(Integer.parseInt(org_id));
		if(null!=list&&!list.isEmpty()){
			for (Role r : list.values()) {  
				redisRoleDao.delete(r);
			}
		}
		List<Role> roles = roleService.getRoleListByOrgID(Integer.parseInt(org_id));
		this.redisRoleDao.add(roles);
	}

}
