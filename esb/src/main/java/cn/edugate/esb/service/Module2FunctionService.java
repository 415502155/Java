package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Module;
import cn.edugate.esb.entity.Module2Function;

/**
 * 模块功能关系服务接口
 * Title:Module2FunctionService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:16:51
 */
public interface Module2FunctionService {
	/**
	 * 添加新的模块功能关系
	 * @param module2function
	 */
	public abstract void add(Module2Function module2function);
	/**
	 * 根据条件查询所有模块功能关系列表
	 * @param module2function
	 * @return
	 */
	public abstract List<Module2Function> getList(Module2Function module2function);
	/**
	 * 新增模块功能关系表，同时删除旧的关系表
	 * @param Module2Function module2function
	 */
	public abstract void update(Module2Function module2function);
	/**
	 * 根据功能模块主键删除模块功能关系
	 * @param moduleID
	 * @param functionID
	 */
	public abstract void deleteByIDs(Integer moduleID,Integer functionID);
	/**
	 * 根据功能查询模块
	 * @param functionId
	 * @return
	 */
	public abstract Module getModuleByFunction(Integer functionId);
	
}
