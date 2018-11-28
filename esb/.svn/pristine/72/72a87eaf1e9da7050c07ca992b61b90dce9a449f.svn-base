package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.service.OrganizationService;

/**
 * 
 * 
 * @Name: 机构缓存
 * @Description:  
 * @date  2017-5-17
 * @version V1.0
 */
@Cache(name="机构缓存",entities={Organization.class})
public class OrganizationCacheProvider implements CacheProvider<Organization>,java.io.Serializable {

	static Logger logger=LoggerFactory.getLogger(OrganizationCacheProvider.class);
	private static final long serialVersionUID = 1L;	
	
	private RedisOrganizationDao redisOrganizationDao;
	
	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}

	
	private OrganizationService organizationService;
	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Override
	public void add(Organization en) {
		// TODO Auto-generated method stub
		this.redisOrganizationDao.add(en);
	}

	@Override
	public void update(Organization en) {
		// TODO Auto-generated method stub
		Organization oldOrganization = redisOrganizationDao.getByOrgId(en.getOrg_id().toString(), null);
		if(oldOrganization!=null)
			this.redisOrganizationDao.delete(oldOrganization);
		this.redisOrganizationDao.add(en);
	}

	@Override
	public void delete(Organization en) {
		// TODO Auto-generated method stub
		this.redisOrganizationDao.delete(en);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisOrganizationDao.deleteAll();
		List<Organization> list = organizationService.getOrgList();
		this.redisOrganizationDao.add(list);
	}

	@Override
	public void refreshOrg(String org_id) {
		Organization o = redisOrganizationDao.getByOrgId(org_id, null);
		redisOrganizationDao.delete(o);
		Organization org = organizationService.getOrgByIdForRedis(Integer.parseInt(org_id));
		this.redisOrganizationDao.add(org);
	}

	


}
