package cn.edugate.esb.redis.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.Icon;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisIconDao;
import cn.edugate.esb.util.Constant;

@Repository
public class RedisIconDaoImpl extends RedisGeneratorDao<String, String> implements  RedisIconDao{
	
	@Override
	public boolean add(final Map<Integer,List<Icon>> map) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	for (Map.Entry<Integer,List<Icon>> entry : map.entrySet()) {
            		StringBuffer on_ps = new StringBuffer();
            		StringBuffer on_ss = new StringBuffer();
            		StringBuffer on_ts = new StringBuffer();
            		StringBuffer on_ws = new StringBuffer();
            		StringBuffer off_ps = new StringBuffer();
            		StringBuffer off_ss = new StringBuffer();
            		StringBuffer off_ts = new StringBuffer();
            		StringBuffer off_ws = new StringBuffer();
            		Integer org_id = entry.getKey();
            		List<Icon> list = entry.getValue();
            		for (Icon icon : list) {
						if(Constant.YES==icon.getS_show()){
							on_ss.append(icon.getS_key()+",");
						}else{
							off_ss.append(icon.getS_key()+",");
						}
						if(Constant.YES==icon.getT_show()){
							on_ts.append(icon.getT_key()+",");
						}else{
							off_ts.append(icon.getT_key()+",");
						}
						if(Constant.YES==icon.getP_show()){
							on_ps.append(icon.getP_key()+",");
						}else{
							off_ps.append(icon.getP_key()+",");
						}
						if(Constant.YES==icon.getW_show()){
							on_ws.append(icon.getW_key()+",");
						}else{
							off_ws.append(icon.getW_key()+",");
						}
					}
            		byte[] on_p = serializer.serialize(on_ps.toString().indexOf(",")>0?on_ps.toString().substring(0, on_ps.toString().length()-1):"");   
            		byte[] on_s = serializer.serialize(on_ss.toString().indexOf(",")>0?on_ss.toString().substring(0, on_ss.toString().length()-1):"");   
            		byte[] on_t = serializer.serialize(on_ts.toString().indexOf(",")>0?on_ts.toString().substring(0, on_ts.toString().length()-1):"");   
            		byte[] on_w = serializer.serialize(on_ws.toString().indexOf(",")>0?on_ws.toString().substring(0, on_ws.toString().length()-1):"");   
            		byte[] off_p = serializer.serialize(off_ps.toString().indexOf(",")>0?off_ps.toString().substring(0, off_ps.toString().length()-1):"");   
            		byte[] off_s = serializer.serialize(off_ss.toString().indexOf(",")>0?off_ss.toString().substring(0, off_ss.toString().length()-1):"");   
            		byte[] off_t = serializer.serialize(off_ts.toString().indexOf(",")>0?off_ts.toString().substring(0, off_ts.toString().length()-1):"");   
            		byte[] off_w = serializer.serialize(off_ws.toString().indexOf(",")>0?off_ws.toString().substring(0, off_ws.toString().length()-1):"");   
            		
            		byte[] key_on  = serializer.serialize("icon_on:"+org_id);                	  
            		byte[] key_off  = serializer.serialize("icon_off:"+org_id);                	  
            		byte[] field_p  = serializer.serialize(Constant.IDENTITY_PARENT.toString()); 
            		byte[] field_t  = serializer.serialize(Constant.IDENTITY_TEACHER.toString()); 
            		byte[] field_s  = serializer.serialize(Constant.IDENTITY_STUDENT.toString()); 
            		byte[] field_w  = serializer.serialize(Constant.IDENTITY_WEB.toString()); 
            		connection.hSet(key_on,field_p, on_p); 
            		connection.hSet(key_on,field_s, on_s); 
            		connection.hSet(key_on,field_t, on_t); 
            		connection.hSet(key_on,field_w, on_w); 
            		connection.hSet(key_off,field_p, off_p); 
            		connection.hSet(key_off,field_s, off_s); 
            		connection.hSet(key_off,field_t, off_t); 
            		connection.hSet(key_off,field_w, off_w); 
        	    }
                return true;  
            }
        }, false, true);  
        return result;
	}
	
	@Override
	public boolean deleteAll() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				deleteRedis(connection, serializer, "icon_on:"+"*");       
				deleteRedis(connection, serializer, "icon_off:"+"*");       
                return true;
            }  
        });  
        return result;  
	}
	
	@Override
	public String getOnUsing(final Integer org_id, final Integer identity) {
		String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("icon_on:"+org_id);  
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					return new String(values.get(serializer.serialize(identity+"")));
				} catch (Exception e) {
					return "";
				}   
            }  
        });  
        return result;
		/*String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("icon_on:"+org_id+":"+identity);  
				return new String(redisConnection.get(key));
            }  
        });  
        return result;*/
	}

	@Override
	public String getOnClosing(final Integer org_id, final Integer identity) {
		String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("icon_off:"+org_id);  
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					return new String(values.get(serializer.serialize(identity+"")));
				} catch (Exception e) {
					return "";
				}   
            }  
        });  
        return result;
	}

}
