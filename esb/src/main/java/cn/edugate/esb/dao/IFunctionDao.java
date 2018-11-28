package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.Paging;

/**
 * 功能DAO
 * Title:IFunctionDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午3:03:12
 */
public interface IFunctionDao extends BaseDAO<Function>{

	/**
	 * 获得功能列表
	 * @param function
	 * @return
	 */
	List<Function> getList(Function function);

	/**
	 * 获得功能列表（带分页）
	 * @param paging
	 * @param function
	 * @return
	 */
	Paging<Function> getListWithPaging(Paging<Function> paging,
			Function function);
	
	/**
	 * 获取模块下的全部功能列表
	 * @param module
	 * @return
	 */
	List<Function> getListByModule(Module module);
	
	/**
	 * 校验功能名称是否存在
	 * @param name
	 * @param version
	 * @return
	 */
	boolean checkName(String name,String version);
	
	/**
	 * 更新功能顺序
	 * @param function
	 * @return 
	 */
	void updateOrder(Integer moduleID);

	/**
	 * 根据功能主键查询尚未创建菜单的功能信息(附带机构信息、上级菜单信息等)
	 * @param fun_id
	 * @return
	 */
	List<Function> getFunctionForMenu(Integer fun_id);
	List<Function> getFunctionForOrg(String org_id);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Function getByid(Integer id);
}
