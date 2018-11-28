package cn.edugate.esb.redis.cache;
 
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Field;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisFieldDao;
import cn.edugate.esb.service.FieldService;
 
/**
 * 场地缓存
 * Title:FieldCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午11:48:25
 */
@Cache(name="场地缓存",entities={Field.class})
public class FieldCacheProvider implements CacheProvider<Field>,java.io.Serializable {
 
    private static final long serialVersionUID = -4847327728695359228L;
 
    static Logger logger=LoggerFactory.getLogger(FieldCacheProvider.class);
    
    private RedisFieldDao redisFieldDao;
    @Autowired
    public void setRedisFieldDao(RedisFieldDao redisFieldDao) {
        this.redisFieldDao = redisFieldDao;
    }
    private FieldService fieldService;
    @Autowired
    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }
    
    @Override
    public void add(Field field) {
        logger.info("fieldRedis===add====",field);
        this.redisFieldDao.addField(field);
    }
 
    @Override
    public void update(Field field) {
        logger.info("fieldRedis===update====",field);
        Field oldField = this.redisFieldDao.getFieldById(field.getField_id());
        if(oldField!=null)
        	this.redisFieldDao.deleteField(oldField);
        this.redisFieldDao.addField(field);
    }
 
    @Override
    public void delete(Field field) {
        logger.info("fieldRedis===delete====",field);
        this.redisFieldDao.deleteField(field);
    }
 
    @Override
    public void refreshAll() {
        this.redisFieldDao.deleteAllFields();
        List<Field> fields = this.fieldService.getAll();
        this.redisFieldDao.addFields(fields);    
    }

	@Override
	public void refreshOrg(String org_id) {
		List<Field> list = redisFieldDao.getFieldList(Integer.parseInt(org_id),null);
		for (Field f : list) {
			redisFieldDao.deleteField(f);
		}
		Field field = new Field();
		field.setOrg_id(Integer.parseInt(org_id));
		List<Field> fields = fieldService.getList(field);
		this.redisFieldDao.addFields(fields);
	}
 
}