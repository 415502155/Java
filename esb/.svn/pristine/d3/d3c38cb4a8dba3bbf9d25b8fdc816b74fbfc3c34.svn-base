package cn.edugate.esb.service;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Icon;

/**
 * Title: IconService
 * Description:图标接口 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月14日下午1:41:31
 */
public interface IconService {
	
	/**
	 * 查询机构下所有的图标
	 * @param org_id
	 * @return
	 */
	public abstract List<Icon> getOnUsing(Integer org_id,Integer identity);

	/**
	 * 查询全部图标（FOR REDIS）
	 * @return
	 */
	public abstract Map<Integer,List<Icon>> getIconList();

	/**
	 * 为org_icon表填充数据
	 */
	public abstract void refreshDB();
	
}
