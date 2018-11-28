package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.Paging;

/**
 * 模块服务接口
 * Title:ModuleService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:06:05
 */
public interface ModuleService {
	/**
	 * 添加新的模块
	 * @param module
	 */
	public abstract void add(Module module);
	/**
	 * 根据主键删除模块
	 * @param id
	 * @return
	 */
	public abstract Integer delete(Integer id);
	/**
	 * 更新模块
	 * @param module
	 * @return
	 */
	public abstract Module update(Module module);
	/**
	 * 根据主键获取模块
	 * @param id
	 * @return
	 */
	public abstract Module getById(Integer id);
	/**
	 * 获得模块列表
	 * @param module
	 * @return
	 */
	public abstract List<Module> getList(Module module);
	/**
	 * 获取模块列表（带分页）
	 * @param paging
	 * @param module
	 * @return
	 */
	public abstract Paging<Module> getListWithPaging(Paging<Module> paging,Module module);
	/**
	 * 获得模块列表(带分页,带跨表的模糊查询条件)
	 * @param paging
	 * @param module (带模块或功能共有的模糊查询条件)
	 * @return
	 */
	public abstract Paging<Module> getQueryListWithPaging(Paging<Module> paging,Module module);
	/**
	 * 向模块中添加功能列表
	 * @param page
	 * @return
	 */
	public abstract Paging<Module> setFunctionsForModule(Paging<Module> page);
	/**
	 * 校验模块名称是否存在
	 * @param name
	 * @return
	 */
	public abstract boolean checkName(String name);
	/**
	 * 更新模块顺序
	 * @param module
	 * @return 
	 */
	public abstract void updateOrder();
	/**
	 * 根据功能主键查询模块中尚未根据此功能创建菜单的模块(并附带机构信息)
	 * @param fun_id
	 * @return
	 */
	public abstract List<Module> getModuleForMenu(Integer fun_id);
}
