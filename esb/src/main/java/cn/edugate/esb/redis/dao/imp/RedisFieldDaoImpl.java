package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
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

import cn.edugate.esb.comparator.FieldComparator;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Field;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisFieldDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * Title:RedisFieldDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:08:50
 */
@Repository
public class RedisFieldDaoImpl extends RedisGeneratorDao<String, String> implements RedisFieldDao {

	private static Logger logger = Logger.getLogger(RedisFieldDaoImpl.class);
	
	private Util util;
	
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	
	@Autowired
	private RedisCampusDao redisCampusDao;
	
	@Override
	public boolean addField(final Field field) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				if (field.getIs_del() == Constant.IS_DEL_NO) {
					String json = "";
					json = util.getJSONFromPOJO(field);
					byte[] value = serializer.serialize(json);
					byte[] param = serializer.serialize(field.getField_id().toString());

					// 场地id ：对应的场地信息
					byte[] key1 = serializer.serialize("field:" + field.getField_id());
					connection.set(key1, value);
					
					// 机构id ： 机构下的全部场地信息
					byte[] key2 = serializer.serialize("fieldOrg:" + field.getOrg_id());
					connection.hSet(key2, param, value);
					
					// 场地名称 ： 对应的场地信息
					byte[] key3 = serializer.serialize("fieldName:" + field.getField_name() + ":fieldOrg:" + field.getOrg_id());
					connection.hSet(key3, param, value);
					
					// 场地类型 ： 该类型的场地信息
					byte[] key4 = serializer.serialize("fieldType:" + field.getField_type() + ":fieldOrg:" + field.getOrg_id());
					connection.hSet(key4, param, value);
					
					// 场地编号 : 对应的场地信息
					byte[] key5 = serializer.serialize("fieldNumber:" + field.getField_number() + ":fieldOrg:"
							+ field.getOrg_id());
					connection.hSet(key5, param, value);

					// 场地所属机构+场地类型
					byte[] key6 = serializer.serialize("fieldOrg:" + field.getOrg_id() + ":fieldType:" + field.getField_type());
					connection.hSet(key6, param, value);
				}
				return true;
			}
		}, false, true);
		return result;
	}
	
	@Override
	public boolean addFields(final List<Field> fields) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				for (Field field : fields) {
					//if (field.getIs_del() == Constant.IS_DEL_NO) {
						String json = "";
						json = util.getJSONFromPOJO(field);
						byte[] value = serializer.serialize(json);
						byte[] param = serializer.serialize(field.getField_id().toString());

						// 场地id ：对应的场地信息
						byte[] key1 = serializer.serialize("field:" + field.getField_id());
						connection.set(key1, value);
						// 机构id ： 机构下的全部场地信息
						byte[] key2 = serializer.serialize("fieldOrg:" + field.getOrg_id());
						connection.hSet(key2, param, value);
						// 场地名称 ： 对应的场地信息
						byte[] key3 = serializer.serialize("fieldName:" + field.getField_name() + ":fieldOrg:"
								+ field.getOrg_id());
						connection.hSet(key3, param, value);
						// 场地类型 ： 该类型的场地信息
						byte[] key4 = serializer.serialize("fieldType:" + field.getField_type() + ":fieldOrg:"
								+ field.getOrg_id());
						connection.hSet(key4, param, value);
						// 场地编号 : 对应的场地信息
						byte[] key5 = serializer.serialize("fieldNumber:" + field.getField_number() + ":fieldOrg:"
								+ field.getOrg_id());
						connection.hSet(key5, param, value);
						
						// 场地所属机构+场地类型
						byte[] key6 = serializer.serialize("fieldOrg:" + field.getOrg_id() + ":fieldType:" + field.getField_type());
						connection.hSet(key6, param, value);
					//}
				}
				return true;
			}
		}, false, true);
		return result;
	}

	@Override
	public boolean deleteField(final Field field) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] param = serializer.serialize(field.getField_id().toString());

				// 场地id ：对应的场地信息
				byte[] key1 = serializer.serialize("field:" + field.getField_id());
				connection.del(key1);
				// 机构id ： 机构下的全部场地信息
				byte[] key2 = serializer.serialize("fieldOrg:" + field.getOrg_id());
				connection.hDel(key2, param);
				// 场地名称 ： 对应的场地信息
				byte[] key3 = serializer.serialize("fieldName:" + field.getField_name() + ":fieldOrg:" + field.getOrg_id());
				connection.hDel(key3, param);
				// 场地类型 ： 该类型的场地信息
				byte[] key4 = serializer.serialize("fieldType:" + field.getField_type() + ":fieldOrg:" + field.getOrg_id());
				connection.hDel(key4, param);
				// 场地编号 : 对应的场地信息
				byte[] key5 = serializer.serialize("fieldNumber:" + field.getField_number() + ":fieldOrg:" + field.getOrg_id());
				connection.hDel(key5, param);
				
				// 场地所属机构+场地类型
				byte[] key6 = serializer.serialize("fieldOrg:" + field.getOrg_id() + ":fieldType:" + field.getField_type());
				connection.hDel(key6, param);
				
				return true;
			}
		}, false, true);
		return result;
	}


	
	@Override
	public boolean deleteAllFields() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"field:*");
					deleteRedis(connection,serializer,"fieldOrg:*");
					deleteRedis(connection,serializer,"fieldName:*:fieldOrg:*");
					deleteRedis(connection,serializer,"fieldType:*:fieldOrg:*");
					deleteRedis(connection,serializer,"fieldNumber:*:fieldOrg:*");
					deleteRedis(connection,serializer,"fieldOrg:*:fieldType:*");
				} catch (Exception e) {
					logger.error("=====deleteAllFields error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public Field getFieldById(final Integer fieldId) {
		Field result = redisTemplate.execute(new RedisCallback<Field>() {  
            public Field doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Field field = new Field();
				try {
					byte[] key = serializer.serialize("field:"+fieldId);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	field = util.getPojoFromJSON(evalue, Field.class);
				} catch (Exception e) {
					logger.error("=====getFieldById error:"+e.getMessage());
					return null;
				}                
                return field;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Field> getFields(final Field field) {
		Map<String, Field> result = redisTemplate.execute(new RedisCallback<Map<String, Field>>() {

			public Map<String, Field> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = null;
				if (null != field.getField_id()) {
					key = serializer.serialize("field:" + field.getField_id());
				} else if (null != field.getOrg_id() && null != field.getField_name()) {
					key = serializer.serialize("fieldName:" + field.getField_name() + ":fieldOrg:" + field.getOrg_id());
				} else if (null != field.getOrg_id() && null != field.getField_type()) {
					key = serializer.serialize("fieldType:" + field.getField_type() + ":fieldOrg:" + field.getOrg_id());
				} else if (null != field.getOrg_id() && null != field.getField_number()) {
					key = serializer.serialize("fieldNumber:" + field.getField_number() + ":fieldOrg:" + field.getOrg_id());
				} else if (null != field.getOrg_id()) {
					key = serializer.serialize("fieldOrg:" + field.getOrg_id());
				} else {
					return null;
				}
				Map<String, Field> fieldsMap = new HashMap<String, Field>();
				Map<String, Field> tempMap = new LinkedHashMap<String, Field>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> field : values.entrySet()) {
						String ekey = serializer.deserialize(field.getKey());
						String evaluestr = serializer.deserialize(field.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Field evalue = util.getPojoFromJSON(evaluestr, Field.class);
							tempMap.put(ekey, evalue);
						}
					}
				} catch (Exception e) {
					logger.error("=====getFields error:" + e.getMessage());
					return null;
				}
				fieldsMap = Utils.sortMapByKey(tempMap);
				return fieldsMap;
			}
		});
		return result;
	}

	@Override
	public List<Field> getFieldList(final Integer orgID, final Integer fieldType) {
		List<Field> result = redisTemplate.execute(new RedisCallback<List<Field>>() {

			public List<Field> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = null;
				// 如果场地类型为空，则返回机构下的所有场地
				if (fieldType == null || fieldType.intValue() == -1) {
					key = serializer.serialize("fieldOrg:" + orgID);
				} else {
					key = serializer.serialize("fieldOrg:" + orgID + ":fieldType:" + fieldType);
				}

				List<Field> fieldList = new ArrayList<Field>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> field : values.entrySet()) {
						String ekey = serializer.deserialize(field.getKey());
						String evaluestr = serializer.deserialize(field.getValue());
						Field evalue = util.getPojoFromJSON(evaluestr, Field.class);
						
						if (evalue.getCampus_id() != null) {
							Campus campus = redisCampusDao.getById(evalue.getCampus_id());
							evalue.setCampusName(campus.getCam_name());
						}
						fieldList.add(evalue);
					}
					
					// 安装ID排序
					Collections.sort(fieldList, new FieldComparator());

				} catch (Exception e) {
					logger.error("=====getFields error:" + e.getMessage());
					return null;
				}
				return fieldList;
			}
		});
		return result;
	}

}
