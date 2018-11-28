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

import cn.edugate.esb.entity.Card;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisCardDaoImpl  extends RedisGeneratorDao<String, String> implements RedisCardDao{
	private static Logger logger = Logger.getLogger(RedisCardDaoImpl.class);
	@Autowired
	private Util util;
	
	@Override
	public boolean add(final Card tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
        		RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		if(tr.getStud_id()!=null){
	            	byte[] value = serializer.serialize(tr.getStud_id().toString()); 
	        		byte[] keyt  = serializer.serialize("card:"+tr.getCard_no());                	  
	            	connection.set(keyt, value);
	            	byte[] keyc  = serializer.serialize("studentCard:"+tr.getStud_id());  
	            	byte[] valuec  = serializer.serialize(tr.getCard_no());	        		
	        		connection.sAdd(keyc, valuec);
        		}
            	return true; 
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Card> trs) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	        	for (Card tr : trs) { 
	        		if(tr.getStud_id()!=null){
		        		byte[] value = serializer.serialize(tr.getStud_id().toString()); 
		         		byte[] keyt  = serializer.serialize("card:"+tr.getCard_no());                	  
		             	connection.set(keyt, value);
		             	byte[] keyc  = serializer.serialize("studentCard:"+tr.getStud_id());  
		             	byte[] valuec  = serializer.serialize(tr.getCard_no());	        		
		         		connection.sAdd(keyc, valuec);	
	        		}
	        	}
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Card tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();                
        		byte[] key  = serializer.serialize("card:"+tr.getCard_no());  
        		connection.del(key);
        		
        		byte[] key1  = serializer.serialize("studentCard:"+tr.getStud_id());  
        		byte[] field1 = serializer.serialize(tr.getCard_no()); 
        		connection.sRem(key1, field1);
        		
                return true;  
            }
        }, false, true);
        return result;
	}

	@Override
	public boolean deleteAll() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"card:*");	
					deleteRedis(connection,serializer,"studentCard:*");
				} catch (Exception e) {
					logger.error("=====deleteAllGroups error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public Integer getStudId(final String card_no) {
		// TODO Auto-generated method stub
		Integer result = redisTemplate.execute(new RedisCallback<Integer>() {  
            public Integer doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("card:"+card_no);  
                Integer user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
	                	user = Integer.parseInt(evalue);
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result;
	}

	@Override
	public boolean clearStudId(final String card_no,final Integer stud_id) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                
                byte[] key  = serializer.serialize("card:"+card_no);  
        		connection.del(key);
        		
        		byte[] key1  = serializer.serialize("studentCard:"+stud_id);  
        		byte[] field1 = serializer.serialize(card_no); 
        		connection.sRem(key1, field1);
        		
                return true;  
            }
        }, false, true);
        return result;
	}

}
