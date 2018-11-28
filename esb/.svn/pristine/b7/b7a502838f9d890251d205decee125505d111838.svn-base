package cn.edugate.esb.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Icon;

/**
 * Title: IIconDao
 * Description:图标 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月14日下午1:49:40
 */
public interface IIconDao extends BaseDAO<Icon> {

	/**
	 * 查询机构当前使用的图标
	 * @param org_id
	 * @return
	 */
	List<Icon> getOnUsing(Integer org_id,Integer identity);

	/**
	 * 查询全部机构图标
	 * @return
	 */
	Map<Integer, List<Icon>> getIconList();

	/**
	 * 获取全部图标列表
	 * @return
	 */
	List<Icon> getAllList();

	
}
