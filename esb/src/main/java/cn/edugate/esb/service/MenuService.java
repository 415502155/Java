package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Module;

/**
 * 菜单服务接口
 * Title:MenuService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:48:49
 */
public interface MenuService {
	/**
	 * 添加新的菜单
	 * @param menu
	 */
	public abstract void add(Menu menu);
	/**
	 * 根据主键删除菜单
	 * @param id
	 * @return
	 */
	public abstract Integer delete(Integer id);
	/**
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	public abstract Menu update(Menu menu);
	/**
	 * 根据主键获取菜单
	 * @param id
	 * @return
	 */
	public abstract Menu getById(Integer id);
	/**
	 * 根据查询条件获取菜单
	 * @param orgId 【必填】机构主键
	 * @param type 菜单类型(null全部0使用中1未使用2试用中)
	 * @param status 菜单状态(null全部0正常1维护2过期)
	 * @return
	 */
	public abstract List<Menu> getMenus(Integer orgId,Integer type,Integer status);
	/**
	 * 获得无条件的全部菜单列表
	 * @return
	 */
	public abstract List<Menu> getAll();
	/**
	 * 为机构创建菜单
	 * @param orgId 机构主键
	 * @param orgType 机构类型(0学校2培训机构3教育局)
	 * @param schoolType 学校类型(1幼儿园2小学3初中4高中5特殊)
	 * @param gradeNumber 年级编码(幼儿园11-15小学21-26初中31-33高中41-43)   
	 * @return 添加成功则返回机构主键
	 */
	public abstract Integer createMenuForOrg(Integer orgId,Integer orgType,Integer schoolType,String gradeNumber);
	public abstract Integer createMenuForOrg(Integer orgId,Integer orgType,Integer schoolType);
	public abstract Integer createMenuForOrg(Integer orgId,Integer orgType);
	/**
	 * 根据机构主键查询菜单列表(可带分级信息)
	 * @param id 机构主键
	 * @param isTree 是否带树形分级
	 * @return
	 */
	public abstract List<Menu> getMenusForOrg(Integer orgId, boolean isTree);
	/**
	 * 查询所有父级菜单列表
	 * @return
	 */
	public abstract List<Menu> getAllParent(Integer org_id);
	
	/******************************模块功能连带操作*********************************/
	/**
	 * 根据模块创建一级菜单
	 * @param function
	 */
	public abstract void createModuleMenus(List<Module> orgModuleList);
	/**
	 * 根据功能创建二级菜单
	 * @param function
	 */
	public abstract void createFunctionMenus(List<Function> orgFunctionList);
	/**
	 * 根据模块删除菜单
	 * @param function
	 */
	public abstract void deleteModuleMenu(Module module);
	/**
	 * 根据功能删除菜单
	 * @param function
	 */
	public abstract void deleteFunctionMenu(Function function);
	/**
	 * 根据模块更新菜单
	 * @param module
	 */
	public abstract void updateModuleMenu(Module module,String oldName);
	/**
	 * 根据功能更新菜单
	 * @param module
	 */
	public abstract void updateFunctionMenu(Function function,String oldName);
	/**
	 * 补录一级菜单
	 * @param orgModuleList
	 * @param orgids
	 */
	public abstract void additionalModuleMenus(List<Module> orgModuleList, List<String> orgids);
	/**
	 * 补录二级菜单
	 * @param orgFunctionList
	 * @param orgids
	 */
	public abstract void additionalFunctionMenus(List<Function> orgFunctionList, List<String> orgids);
	/**
	 * 查询机构下菜单
	 * @param parseInt
	 * @return
	 */
	public abstract List<Menu> getMenusByOgrId(int parseInt);
}
