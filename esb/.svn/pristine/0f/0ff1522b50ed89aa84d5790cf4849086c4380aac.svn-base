package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Icon;

public interface RedisIconDao {

	/**
	 * 删除全部图标
	 * @return
	 */
	boolean deleteAll();
	/**
	 * 批量添加图标
	 * @return
	 */
	boolean add(Map<Integer,List<Icon>> map);
	/**
	 * 获取机构下指定身份的图标显示信息
	 * @param org_id
	 * @param identity
	 * @return
	 */
	String getOnUsing(Integer org_id,Integer identity);
	/**
	 * 获取机构下指定身份的图标禁用信息
	 * @param org_id
	 * @param identity
	 * @return
	 */
	String getOnClosing(Integer org_id, Integer identity);
	
}
