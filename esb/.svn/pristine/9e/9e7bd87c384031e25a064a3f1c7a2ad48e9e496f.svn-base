package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.Paging;

/**
 * 模块DAO
 * Title:IModuleDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午3:02:57
 */
public interface IModuleDao extends BaseDAO<Module>{

	/**
	 * 获得模块列表
	 * @param module
	 * @return
	 */
	List<Module> getList(Module module);
	 /**
	  * 获得模块列表(带分页)
	  * @param paging
	  * @param module
	  * @return
	  */
	Paging<Module> getListWithPaging(Paging<Module> paging, Module module);
	/**
	 * 获得模块列表(带分页,带跨表的模糊查询条件)
	 * @param paging
	 * @param module (带模块或功能共有的模糊查询条件)
	 * @return
	 */
	Paging<Module> getQueryListWithPaging(Paging<Module> paging, Module module);
	/**
	 * 校验模块名称是否存在
	 * @param name
	 * @return
	 */
	boolean checkName(String name);
	/**
	 * 更新模块顺序
	 * @param module
	 * @return 
	 */
	void updateOrder();
	/**
	 * 根据功能主键查询模块中尚未根据此功能创建菜单的模块(并附带机构信息)
	 * @param fun_id
	 * @return
	 */
	List<Module> getModuleForMenu(Integer fun_id);
	
	
}
