package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.service.ParentService;

@Cache(name="家长缓存",entities={Parent.class})
public class ParentCacheProvider  implements CacheProvider<Parent>,java.io.Serializable{

	private static final long serialVersionUID = -9172041061763013137L;

	static Logger logger=LoggerFactory.getLogger(ParentCacheProvider.class);

	private RedisParentDao redisParentDao;	
	
	@Autowired
	public void setRedisParentDao(RedisParentDao redisParentDao) {
		this.redisParentDao = redisParentDao;
	}

	private ParentService parentService;	
	
	@Autowired
	public void setParentService(ParentService parentService) {
		this.parentService = parentService;
	}

	@Override
	public void add(Parent parent) {
		redisParentDao.add(parent);
	}

	@Override
	public void update(Parent parent) {
//		this.redisParentDao.delete(parent);
//		this.redisParentDao.add(parent);
		
		Parent old = this.redisParentDao.getByParentId(parent.getParent_id().toString());
		if(old!=null){
			this.redisParentDao.delete(old);
		}
		this.redisParentDao.add(parent);
	}

	@Override
	public void delete(Parent parent) {
		this.redisParentDao.delete(parent);
	}

	@Override
	public void refreshAll() {
		this.redisParentDao.deleteAll();
		List<Parent> list = this.parentService.getAll();
		this.redisParentDao.add(list);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Parent> list = redisParentDao.getParentsByOrgId(Integer.parseInt(org_id));
		for (Parent p : list) {  
			redisParentDao.delete(p); 
		}
		List<Parent> parents = parentService.getParentByOrgId(Integer.parseInt(org_id));
		this.redisParentDao.add(parents);
	}

}
