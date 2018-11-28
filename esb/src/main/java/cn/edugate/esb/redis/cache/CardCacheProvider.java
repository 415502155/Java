package cn.edugate.esb.redis.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.Card;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.service.StudentService;

/**
 * WxConfigCacheProvider
 */
@Cache(name="学生卡缓存",entities={Card.class})
public class CardCacheProvider implements CacheProvider<Card>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;
	static Logger logger=LoggerFactory.getLogger(CardCacheProvider.class);
	
	private RedisCardDao redisCardDao;
	private StudentService studentService;
	
	@Autowired
	public void setRedisCardDao(RedisCardDao redisCardDao) {
		this.redisCardDao = redisCardDao;
	}

	@Autowired
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	@Override
	public void add(Card gp) {
		logger.info("CardCache===add====",gp);
		this.redisCardDao.add(gp);
	}

	@Override
	public void update(Card gp) {
		logger.info("CardCache===update====",gp);
		Integer stud_id = this.redisCardDao.getStudId(gp.getCard_no());
		if(stud_id!=null){
			this.redisCardDao.clearStudId(gp.getCard_no(),stud_id);
		}
		this.redisCardDao.add(gp);	
	}
	

	@Override
	public void delete(Card gp) {		
		this.redisCardDao.delete(gp);		
	}

	@Override
	public void refreshAll() {
		this.redisCardDao.deleteAll();
		List<Card> configs = this.studentService.getAllCard();
		this.redisCardDao.add(configs);	
	}

	@Override
	public void refreshOrg(String org_id) {
		refreshAll();
	}

}
