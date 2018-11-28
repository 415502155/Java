package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Menu;

public interface RedisMenuDao {
	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	boolean addMenu(Menu menu);
	/**
	 * 批量添加菜单
	 * @param menus
	 * @return
	 */
	boolean addMenus(List<Menu> menus);
	/**
	 * 删除菜单
	 * @param menu
	 * @return
	 */
	boolean deleteMenu(Menu menu);
	/**
	 * 根据主键获取菜单
	 * @param MenuId
	 * @return
	 */
	Menu getMenuById(Integer MenuId);
	/**
	 * 删除全部菜单
	 * @return
	 */
	boolean deleteAll();
	/**
	 * 根据机构主键获取菜单
	 * @param orgId
	 * @return
	 */
	Map<String,Menu> getMenusByOrgId(Integer orgId);
	/**
	 * 根据Code编码查询机构下的菜单
	 * @param orgId
	 * @param isAll 是否不考虑状态查询全部
	 * @return
	 */
	Map<String,Menu> getOrgMenusForCode(Integer orgId,boolean isAll);
	/**
	 * 获取全部菜单
	 * @return
	 */
	List<Menu> getAllMenus();
	
	/**
	 * 根据Code编码查询机构下的菜单
	 * @param orgId
	 * @param isAll 是否不考虑状态查询全部
	 * @return
	 */
	Map<String,Menu> getOrgMenusForCode(Integer orgId,boolean isAll,String token,String udid);
	/**
	 * 根据机构主键获取菜单
	 * @param orgId
	 * @return
	 */
	Map<String,Menu> getMenusByOrgId(Integer orgId,String token,String udid);
}
