package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.KeyValue;

/**
 * 键值DAO接口
 * Title:IKeyValueDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年7月17日上午9:58:35
 */
public interface IKeyValueDao extends BaseDAO<KeyValue>{

	/**
	 * 根据类型获取全部键值对集合
	 * @param type
	 * @return
	 */
	public abstract List<KeyValue> getListByType(Integer type);
	
} 
