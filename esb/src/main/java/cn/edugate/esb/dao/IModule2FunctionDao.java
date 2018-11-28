package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Module2Function;

/**
 * 模块和功能关系DAO
 * Title:IModule2FunctionDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:08:31
 */
public interface IModule2FunctionDao extends BaseDAO<Module2Function>{

	/**
	 * 获取功能模块关系列表
	 * @param module2function
	 * @return
	 */
	List<Module2Function> getList(Module2Function module2function);
	/**
	 * 根据功能主键和模块主键删除已有的关系表
	 * @param moduleID
	 * @param functionID
	 */
	void deleteByIDs(Integer moduleID, Integer functionID);

}
