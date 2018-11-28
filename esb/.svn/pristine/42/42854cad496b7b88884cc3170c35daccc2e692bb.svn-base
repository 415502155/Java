package cn.edugate.esb.redis.dao.imp;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisAppDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.util.Util;

/**
 * Title:RedisClassesDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:08:50
 */
@Repository
public class RedisAppDaoImpl extends RedisGeneratorDao<String, String> implements RedisAppDao {

	private static Logger logger = Logger.getLogger(RedisAppDaoImpl.class);
	
	@Autowired
	private RedisGradeDao redisGradeDao;
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	@Override
	public boolean addApp(final App app) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
               
            	String json="";
            	json = util.getJSONFromPOJO(app);
            	byte[] value = serializer.serialize(json); 
            	byte[] appkey  = serializer.serialize("app_key:"+app.getKey().toString());
        		connection.set(appkey, value); 
        		
        		byte[] appid  = serializer.serialize("app_id:"+app.getA_id().toString());
        		connection.set(appid, value);
        		
                return true;  
            }
        }, false, true);  
        return result;
	}
	@Override
	public boolean addApp(final List<App> apps) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (App app : apps) {
                	String json="";
                	json = util.getJSONFromPOJO(app);
                	byte[] value = serializer.serialize(json); 
                	byte[] appkey  = serializer.serialize("app_key:"+app.getKey().toString());
            		connection.set(appkey, value);
            		
            		byte[] appid  = serializer.serialize("app_id:"+app.getA_id().toString());
            		connection.set(appid, value);
				}
                return true;
            }
        }, false, true);  
        return result;
	}
	@Override
	public boolean deleteApp(final App app) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                if(app.getKey()!=null){
	        		byte[] key1  = serializer.serialize("app_key:"+app.getKey().toString());  
	        		connection.del(key1);
                }
        		if(app.getA_id()!=null){
	        		byte[] key2  = serializer.serialize("app_id:"+app.getA_id().toString());
	        		connection.del(key2);
        		}
                return true;
            }
        }, false, true);  
        return result;
	}
	@Override
	public boolean deleteAllApp() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"app_key:*");	
					deleteRedis(connection,serializer,"app_id:*");	
				} catch (Exception e) {
					logger.error("=====deleteAllApp error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result;
	}
	
	@Override
	public App getAppById(final String appId) {
		// TODO Auto-generated method stub
		App result = redisTemplate.execute(new RedisCallback<App>() {  
            public App doInRedis(RedisConnection redisConnection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                App app = new App();
				try {
					byte[] key = serializer.serialize("app_id:"+appId);
					byte[] value = redisConnection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	app = util.getPojoFromJSON(evalue, App.class);
				} catch (Exception e) {
					logger.error("=====getAppById error:"+e.getMessage());
					return null;
				}                
                return app;
            }  
        });  
        return result;
	}
	
	@Override
	public App getAppByKey(final String appkey) {
		// TODO Auto-generated method stub
		App result = redisTemplate.execute(new RedisCallback<App>() {  
            public App doInRedis(RedisConnection redisConnection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                App app = new App();
				try {
					byte[] key = serializer.serialize("app_key:"+appkey);
					byte[] value = redisConnection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	app = util.getPojoFromJSON(evalue, App.class);
				} catch (Exception e) {
					logger.error("=====getAppByKey error:"+e.getMessage());
					return null;
				}                
                return app;
            }  
        });  
        return result;
	}

}
