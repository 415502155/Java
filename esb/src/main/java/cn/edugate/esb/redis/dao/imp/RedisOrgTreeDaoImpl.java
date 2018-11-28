package cn.edugate.esb.redis.dao.imp;
 
import java.util.ArrayList;
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
 


import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisOrgTreeDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
@Repository
public class RedisOrgTreeDaoImpl extends RedisGeneratorDao<String, String> implements RedisOrgTreeDao {
    
    private Util util;
    @Autowired
    public void setUtil(Util util) {
        this.util = util;
    }
    
    private RedisOrganizationDao redisOrganizationDao;
    @Autowired
    public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
        this.redisOrganizationDao = redisOrganizationDao;
    }
 
    @Override
    public boolean addOrgTree(final OrgTree orgTree) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                if(orgTree.getIs_del()==Constant.IS_DEL_NO){
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    String json="";
                    json = util.getJSONFromPOJO(orgTree);
                    byte[] value = serializer.serialize(json);               
                    // 机构树主键：机构树对象
                    byte[] key1  = serializer.serialize("orgTreeId:"+orgTree.getOtree_id());                      
                    connection.set(key1, value);  
                    // 父级机构主键:子级机构信息
                    byte[] key2  = serializer.serialize("orgParentId:"+orgTree.getOrg_id());                      
                    byte[] field2  = serializer.serialize(orgTree.getLower_id().toString()); 
                    Organization childOrg = redisOrganizationDao.getByOrgId(orgTree.getLower_id().toString(),null);
                    String orgJson = util.getJSONFromPOJO(childOrg);
                    byte[] orgValue = serializer.serialize(orgJson);  
                    connection.hSet(key2,field2, orgValue);
                }
                return true;  
            }
        }, false, true);  
        return result;  
    }
 
    @Override
    public boolean addOrgTrees(final List<OrgTree> orgTrees) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                 for (OrgTree orgTree : orgTrees) {                      
                     //if(orgTree.getIs_del()==Constant.IS_DEL_NO){
                         String json="";
                         json = util.getJSONFromPOJO(orgTree);
                         byte[] value = serializer.serialize(json);               
                         // 机构树主键：机构树对象
                         byte[] key1  = serializer.serialize("orgTreeId:"+orgTree.getOtree_id());                      
                         connection.set(key1, value);  
                         // 父级机构主键:子级机构信息
                         byte[] key2  = serializer.serialize("orgParentId:"+orgTree.getOrg_id());                      
                         byte[] field2  = serializer.serialize(orgTree.getLower_id().toString()); 
                         Organization childOrg = redisOrganizationDao.getByOrgId(orgTree.getLower_id().toString(),null);
                         String orgJson = util.getJSONFromPOJO(childOrg);
                         byte[] orgValue = serializer.serialize(orgJson);  
                         connection.hSet(key2,field2, orgValue);
                     //}
                 }
                return true;  
            }
        }, false, true);  
        return result;  
    }
 
    @Override
    public boolean deleteOrgTree(final OrgTree orgTree) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key1  = serializer.serialize("orgTreeId:"+orgTree.getOrg_id()); 
                connection.del(key1);
                byte[] key2  = serializer.serialize("orgParentId:"+orgTree.getOrg_id());                      
                 byte[] field2  = serializer.serialize(orgTree.getLower_id().toString()); 
                connection.hDel(key2, field2);
                return true;  
            }
        }, false, true);  
        return result; 
    }
 
    @Override
    public OrgTree getOrgTreeById(final Integer orgTreeId) {
        OrgTree result = redisTemplate.execute(new RedisCallback<OrgTree>() {  
            public OrgTree doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgTreeId:"+orgTreeId);  
                OrgTree orgTree=null;
                try {
                    byte[] value = connection.get(key); 
                    String evalue = serializer.deserialize(value); 
                    if(StringUtils.isNotEmpty(evalue))
                        orgTree = util.getPojoFromJSON(evalue, OrgTree.class);
                } catch (Exception e) {
                    return null;
                }                
                return orgTree;
            }  
        });  
        return result;
    }
 
    @Override
    public boolean deleteAllOrgTrees() {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgTreeId:"+"*");  
                byte[] key1 = serializer.serialize("orgParentId:"+"*");  
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
                    
                    Set<byte[]> value1 = connection.keys(key1);                    
                    byte[][] keys1=new byte[value1.size()][];
                    int i1=0;
                    for (byte[] bs : value1) {
                        keys1[i1] = bs;
                        i1++;
                    }
                    if(keys1.length>0){
                        connection.del(keys1);
                    }
                } catch (Exception e) {
                    return false;
                }                
                return true;
            }  
        });  
        return result;   
    }
    
    
    
    @Override
	public List<Organization> getOrgsByTreeId(final Integer OrgTreeId) {
		// TODO Auto-generated method stub
		List<Organization> result = redisTemplate.execute(new RedisCallback<List<Organization>>() {  
            public List<Organization> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgParentId:"+OrgTreeId);  
                List<Organization> list = new ArrayList<Organization>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Organization evalue = util.getPojoFromJSON(evaluestr, Organization.class);
							list.add(evalue);
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
 
}