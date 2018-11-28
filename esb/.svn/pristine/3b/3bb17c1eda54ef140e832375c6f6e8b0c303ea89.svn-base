package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Campus;

public interface CampusService {
	/**
	 * 添加新的校区
	 * @param function
	 */
	public abstract void add(Campus campus);
	/**
	 * 根据主键删除校区
	 * @param id
	 * @return
	 */
	public abstract Integer delete(int id);
	/**
	 * 更新校区
	 * @param function
	 * @return
	 */
	public abstract Campus update(Campus campus);
	/**
	 * 根据主键获取校区
	 * @param id
	 * @return
	 */
	public abstract Campus getTechById(int id);
	/**
	 * 获得所有校区列表
	 * @param function
	 * @return
	 */
	public abstract List<Campus> getAll();
	/**
	 * 获取学校所有校区
	 * @param org_id
	 * @return
	 */
	public abstract List<Campus> getByOrgId(Integer org_id);
	
	/**
	 * 获取学校主校区
	 * @param org_id
	 * @return
	 */
	public abstract Campus getCampusByType(int org_id);
}
