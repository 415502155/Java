package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Field;

/**
 * 场地DAO接口
 * Title:RedisFieldDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisFieldDao {
	/**
	 * 添加场地
	 * @param field
	 * @return
	 */
	boolean addField(Field field);
	/**
	 * 批量添加场地(For Redis)
	 * @param fields
	 * @return 
	 */
	boolean addFields(List<Field> fields);
	/**
	 * 删除场地
	 * @param field
	 * @return
	 */
	boolean deleteField(Field field);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAllFields();
	/**
	 * 根据主键获取场地
	 * @param FieldId
	 * @return
	 */
	Field getFieldById(Integer FieldId);
	/**
	 * 根据查询条件获取场地
	 * @param orgId
	 * @return
	 */
	Map<String,Field> getFields(Field field);
	
	/**
	 * 根据组织ID和场地类型获取场地集合
	 * @param orgID
	 * @param fieldType
	 * @return
	 */
	public List<Field> getFieldList(Integer orgID, Integer fieldType);
}
