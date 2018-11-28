package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisMenuDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * 菜单
 * Title:RedisOrganizationDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:01:50
 */
@Repository
public class RedisMenuDaoImpl extends RedisGeneratorDao<String, String> implements RedisMenuDao {

	private static Logger logger = Logger.getLogger(RedisMenuDaoImpl.class);
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public Map<String,Menu> getMenusByOrgId(final Integer orgId) {
		Map<String,Menu> result = redisTemplate.execute(new RedisCallback<Map<String,Menu>>() {  
			public Map<String,Menu> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("menuOrg:"+orgId);  
                Map<String,Menu> menus = new HashMap<String,Menu>();
                Map<String, Menu> sortMenu = new LinkedHashMap<String, Menu>();
				try {
	                Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> menu : values.entrySet()) {
						String ekey = serializer.deserialize(menu.getKey());
						String evaluestr = serializer.deserialize(menu.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Menu evalue = util.getPojoFromJSON(evaluestr, Menu.class);
							if(evalue.getMenu_type()!=Constant.MENU_TYPE_STAYING || evalue.getParent_id()==0){
								menus.put(ekey, evalue);
							}
						}
					}	 
				} catch (Exception e) {
					logger.error("=====getMenusByOrgId error:"+e.getMessage());
					return null;
				}               
				sortMenu = Utils.sortMapByKey(menus);
                return sortMenu;
            }  
        });
		return result;  
	}

	@Override
	public Map<String,Menu> getOrgMenusForCode(final Integer orgId,final boolean isAll) {
		Map<String,Menu> result = redisTemplate.execute(new RedisCallback<Map<String,Menu>>() {  
			public Map<String,Menu> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("menuForCode:"+orgId);  
                Map<String,Menu> menus = new HashMap<String,Menu>();
                Map<String, Menu> sortMenu = new LinkedHashMap<String, Menu>();
				try {
	                Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> menu : values.entrySet()) {
						String ekey = serializer.deserialize(menu.getKey());
						String evaluestr = serializer.deserialize(menu.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Menu evalue = util.getPojoFromJSON(evaluestr, Menu.class);
							if((evalue.getMenu_type()!=Constant.MENU_TYPE_STAYING || isAll ) || evalue.getParent_id()==0){
								menus.put(ekey, evalue);
							}
						}
					}	 
				} catch (Exception e) {
					logger.error("=====getMenusByOrgId error:"+e.getMessage());
					return null;
				}               
				sortMenu = Utils.sortMapByKey(menus);
                return sortMenu;
            }  
        });
		return result;  
	}

	@Override
	public boolean addMenu(final Menu menu) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(menu.getIs_del()==Constant.IS_DEL_NO){
	            	String json="";
	            	json = util.getJSONFromPOJO(menu);
	            	System.out.println(menu.getOrg_id());
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("menu:"+menu.getMenu_id());  
	        		byte[] key2  = serializer.serialize("menuOrg:"+menu.getOrg_id()); 
	        		byte[] key3  = serializer.serialize("menuForCode:"+menu.getOrg_id()); 
	        		byte[] key4  = serializer.serialize("menuAll"); 
	        		byte[] field2  = serializer.serialize(menu.getMenu_id().toString()); 
	        		byte[] field3  = serializer.serialize(menu.getFun_code().toString()); 
	        		connection.set(key, value);    
	        		connection.hSet(key2, field2, value);
        			connection.hSet(key3, field3, value);
        			connection.hSet(key4, field2, value);
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean addMenus(final List<Menu> menus) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Menu menu : menus) { 
                	//if(menu.getIs_del()==Constant.IS_DEL_NO){
                		String json="";
    	            	json = util.getJSONFromPOJO(menu);
    	            	byte[] value = serializer.serialize(json); 
    	        		byte[] key  = serializer.serialize("menu:"+menu.getMenu_id());  
    	        		byte[] key2  = serializer.serialize("menuOrg:"+menu.getOrg_id()); 
    	        		byte[] key3  = serializer.serialize("menuForCode:"+menu.getOrg_id()); 
    	        		byte[] key4  = serializer.serialize("menuAll"); 
    	        		byte[] field2  = serializer.serialize(menu.getMenu_id().toString()); 
    	        		byte[] field3  = serializer.serialize(menu.getFun_code().toString()); 
    	            	connection.set(key, value);    
    	            	connection.hSet(key2, field2, value);
    	            	connection.hSet(key3, field3, value);
            			connection.hSet(key4, field2, value);
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean deleteMenu(final Menu menu) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("menuOrg:"+menu.getOrg_id());  
        		byte[] field1 = serializer.serialize(menu.getMenu_id().toString()); 
        		connection.hDel(key, field1);
        		byte[] key2  = serializer.serialize("menu:"+menu.getMenu_id());             	  
            	connection.del(key2);
            	byte[] key3  = serializer.serialize("menuForCode:"+menu.getOrg_id());  
            	byte[] field3 = serializer.serialize(menu.getFun_code().toString()); 
            	connection.hDel(key3, field3);
            	byte[] key4  = serializer.serialize("menuAll");  
        		connection.hDel(key4,field1);  
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public Menu getMenuById(final Integer MenuId) {
		Menu result = redisTemplate.execute(new RedisCallback<Menu>() {  
            public Menu doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("menu:"+MenuId);  
                Menu menu = new Menu();
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	menu = util.getPojoFromJSON(evalue, Menu.class);
				} catch (Exception e) {
					logger.error("=====getMenuById error:"+e.getMessage());
					return null;
				}                
                return menu;
            }  
        });  
        return result;
	}

	@Override
	public boolean deleteAll() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                
				try {
					
					deleteRedis(connection, serializer, "menu:"+"*");
					deleteRedis(connection, serializer, "menuForCode:"+"*");
					deleteRedis(connection, serializer, "menuOrg:"+"*");
					deleteRedis(connection,serializer,"menuAll");
					
				} catch (Exception e) {
					logger.error("=====deleteAll error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public List<Menu> getAllMenus() {
		List<Menu> result = redisTemplate.execute(new RedisCallback<List<Menu>>() {
			public List<Menu> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("menuAll");
				List<Menu> list = new ArrayList<Menu>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Menu evalue = util.getPojoFromJSON(evaluestr, Menu.class);
							list.add(evalue);
						}
					}
				} catch (Exception e) {
					return null;
				}
				return list;
			}
		});
		return result;
	}

	
	
	@Override
	public Map<String,Menu> getOrgMenusForCode(final Integer orgId,final boolean isAll,final String token,final String udid) {
		Map<String,Menu> result = redisTemplate.execute(new RedisCallback<Map<String,Menu>>() {  
			public Map<String,Menu> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("menuForCode:"+orgId);  
                Map<String,Menu> menus = new HashMap<String,Menu>();
                Map<String, Menu> sortMenu = new LinkedHashMap<String, Menu>();
				try {
	                Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> menu : values.entrySet()) {
						String ekey = serializer.deserialize(menu.getKey());
						String evaluestr = serializer.deserialize(menu.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Menu evalue = util.getPojoFromJSON(evaluestr, Menu.class);
							if((evalue.getMenu_type()!=Constant.MENU_TYPE_STAYING || isAll )){
								String url = "";
								if(StringUtils.isNotEmpty(evalue.getMenu_url())){
									if(evalue.getMenu_url().indexOf("?")==-1)
										url = evalue.getMenu_url()+"?token="+token+"&udid="+udid;
									else
										url = evalue.getMenu_url()+"&token="+token+"&udid="+udid;
									evalue.setMenu_url(url);
								}
								menus.put(ekey, evalue);
							}
						}
					}	 
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("=====getMenusByOrgId error:"+e.getMessage());
					return null;
				}               
				sortMenu = Utils.sortMapByKey(menus);
                return sortMenu;
            }  
        });
		return result;  
	}
	
	@Override
	public Map<String,Menu> getMenusByOrgId(final Integer orgId,final String token,final String udid) {
		Map<String,Menu> result = redisTemplate.execute(new RedisCallback<Map<String,Menu>>() {  
			public Map<String,Menu> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("menuOrg:"+orgId);  
                Map<String,Menu> menus = new HashMap<String,Menu>();
                Map<String, Menu> sortMenu = new LinkedHashMap<String, Menu>();
				try {
	                Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> menu : values.entrySet()) {
						String ekey = serializer.deserialize(menu.getKey());
						String evaluestr = serializer.deserialize(menu.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Menu evalue = util.getPojoFromJSON(evaluestr, Menu.class);
							if(evalue.getMenu_type()!=Constant.MENU_TYPE_STAYING || evalue.getParent_id()==0){
								String url = "";
								if(StringUtils.isNotEmpty(evalue.getMenu_url())){
									if(evalue.getMenu_url().indexOf("?")==-1)
										url = evalue.getMenu_url()+"?token="+token+"&udid="+udid;
									else
										url = evalue.getMenu_url()+"&token="+token+"&udid="+udid;
									evalue.setMenu_url(url);
								}
								menus.put(ekey, evalue);
							}
						}
					}	 
				} catch (Exception e) {
					logger.error("=====getMenusByOrgId error:"+e.getMessage());
					return null;
				}               
				sortMenu = Utils.sortMapByKey(menus);
                return sortMenu;
            }  
        });
		return result;  
	}
}
