package cn.edugate.esb.redis.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="教师范围缓存信息",entities={TechRange.class})
public class TechRangeCacheProvider implements CacheProvider<TechRange>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(TechRangeCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisTechRangeDao redisTechRangeDao;
	
	@Autowired
	public void setRedisTechRangeDao(RedisTechRangeDao redisTechRangeDao) {
		this.redisTechRangeDao = redisTechRangeDao;
	}
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	private TechRangeService techRangeService;
	

	@Override
	public void update(TechRange tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",tr);
		TechRange oldtr = this.redisTechRangeDao.getByTechRange(tr.getTech_id(),tr.getRl_id(),tr.getTr_id());
		if(oldtr!=null){
			this.redisTechRangeDao.delete(oldtr);
		}
		this.redisTechRangeDao.add(tr);
	}

	@Override
	public void add(TechRange tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",tr);
		this.redisTechRangeDao.add(tr);
	}

	@Override
	public void delete(TechRange tr) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",tr);
		this.redisTechRangeDao.delete(tr);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisTechRangeDao.deleteAll();
		List<TechRange> trs = this.userService.getTechRanges();
		this.redisTechRangeDao.add(trs);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Teacher> listTech = redisTeacherDao.getTeacherListByOrgId(org_id, null);
		for (Teacher t : listTech) {  
			List<TechRange> list = this.redisTechRangeDao.getAllOfTechId(t.getTech_id());
			
			if(null!=list&&!list.isEmpty()){
				for (int i=0;i<list.size();i++) {  
					this.redisTechRangeDao.delete(list.get(i));
				}
			}
		}

		List<TechRange> trs = techRangeService.getTechRangesByOrg(Integer.parseInt(org_id));

		this.redisTechRangeDao.add(trs);
	}

}
