package cn.edugate.esb.redis.dao.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.authentication.AC;
import cn.edugate.esb.authentication.FP;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisRightDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisRightDaoImp extends RedisGeneratorDao<String, String> implements RedisRightDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final Right tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException { 
            	if(!tr.getIs_del()){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("right:"+tr.getRight_id());  
	            	connection.set(key, value);  
	            	
	            	byte[] keya = serializer.serialize("rightall");
	            	byte[] field  = serializer.serialize(tr.getRight_id().toString()); 
	            	connection.hSet(keya,field, value);
	            	
	            	FP fp=new FP(tr.getRight_code(), tr.getRight_name(),tr.getFun_id()!=null?tr.getFun_id().toString():"", AC.read,AC.create,AC.write,AC.delete,AC.approve,AC.admin);
	            	String jsonFp = util.getJSONFromPOJO(fp);
	            	byte[] valueFp = serializer.serialize(jsonFp); 
	        		byte[] keyFp  = serializer.serialize("fp:"+tr.getRight_id());  
	            	connection.set(keyFp, valueFp);
	            	byte[] keyaFp = serializer.serialize("fpall");
	            	byte[] fieldFp  = serializer.serialize(tr.getRight_id().toString()); 
	            	connection.hSet(keyaFp,fieldFp, valueFp);
            	}
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<Right> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Right tr : trs) {  
                	//if(!tr.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(tr);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key  = serializer.serialize("right:"+tr.getRight_id());  
	                	connection.set(key, value);  
	                	
	                	byte[] keya = serializer.serialize("rightall");
	                	byte[] field  = serializer.serialize(tr.getRight_id().toString()); 
	                	connection.hSet(keya,field, value);  
	                	
	                	FP fp=new FP(tr.getRight_code(), tr.getRight_name(),tr.getFun_id()!=null?tr.getFun_id().toString():"", AC.read,AC.create,AC.write,AC.delete,AC.approve,AC.admin);
	                	String jsonFp = util.getJSONFromPOJO(fp);
	                	byte[] valueFp = serializer.serialize(jsonFp); 
	            		byte[] keyFp  = serializer.serialize("fp:"+tr.getRight_id());  
	                	connection.set(keyFp, valueFp);
	                	byte[] keyaFp = serializer.serialize("fpall");
	                	byte[] fieldFp  = serializer.serialize(tr.getRight_id().toString()); 
	                	connection.hSet(keyaFp,fieldFp, valueFp);
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final Right tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("right:"+tr.getRight_id());  
        		connection.del(key);
        		
        		byte[] keya = serializer.serialize("rightall");
            	byte[] field  = serializer.serialize(tr.getRight_id().toString()); 
            	connection.hDel(keya, field);
            	
            	byte[] keyFp  = serializer.serialize("fp:"+tr.getRight_id());  
        		connection.del(keyFp);
        		
        		byte[] keyaFp = serializer.serialize("fpall");
            	byte[] fieldFp  = serializer.serialize(tr.getRight_id().toString()); 
            	connection.hDel(keyaFp, fieldFp);
            	
                return true;  
            }
        }, false, true);  
        return result;
	}
	
	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("right:"+"*");  
                byte[] key1 = serializer.serialize("rightall");
                
                byte[] keyFp = serializer.serialize("fp:"+"*");  
                byte[] key1Fp = serializer.serialize("fpall");
				try {
					Set<byte[]> value = connection.keys(key);					
					byte[][] keys=new byte[value.size()][];
					int i=0;
					for (byte[] bs : value) {
						keys[i] = bs;
						i++;
					}
					if(keys.length>0){
						connection.del(keys);
					}
					connection.del(key1);
					
					Set<byte[]> valueFp = connection.keys(keyFp);					
					byte[][] keysFp=new byte[valueFp.size()][];
					int j=0;
					for (byte[] bs : value) {
						keysFp[j] = bs;
						j++;
					}
					if(keysFp.length>0){
						connection.del(keysFp);
					}
					connection.del(key1Fp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return false;
				}                
                return true;
            }  
        });  
        return result;   
	}

	@Override
	public Right getByRightId(final String right_id) {
		// TODO Auto-generated method stub
		Right result = redisTemplate.execute(new RedisCallback<Right>() {  
            public Right doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("right:"+right_id);  
                Right user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	user = util.getPojoFromJSON(evalue, Right.class);
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
	public Map<String, FP> getAllFp() {
		// TODO Auto-generated method stub
		Map<String,FP> result = redisTemplate.execute(new RedisCallback<Map<String,FP>>() {  
            public Map<String,FP> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("fpall");  
                Map<String,FP> teachers = new HashMap<String,FP>();
                Map<String, FP> tempMap = new LinkedHashMap<String, FP>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							FP evalue = util.getPojoFromJSON(evaluestr, FP.class);
							teachers.put(ekey, evalue);
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}   
				tempMap = Utils.sortMapByKey(teachers);
                return tempMap;
            }  
        });  
        return result;
	}

}
