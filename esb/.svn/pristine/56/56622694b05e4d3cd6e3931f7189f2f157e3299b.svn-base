package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Function;
import cn.edugate.esb.util.Paging;

/**
 * 功能服务接口
 * Title:FunctionService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:03:35
 */
public interface FunctionService {
	/**
	 * 添加新的功能
	 * @param function
	 */
	public abstract Integer add(Function function);
	/**
	 * 根据主键删除功能
	 * @param id
	 * @return
	 */
	public abstract Integer delete(Integer id);
	/**
	 * 更新功能
	 * @param function
	 * @return
	 */
	public abstract Function update(Function function);
	/**
	 * 根据主键获取功能
	 * @param id
	 * @return
	 */
	public abstract Function getById(Integer id);
	/**
	 * 获得功能列表
	 * @param function
	 * @return
	 */
	public abstract List<Function> getList(Function function);
	/**
	 * 获取功能列表（带分页）
	 * @param paging
	 * @param function
	 * @return
	 */
	public abstract Paging<Function> getListWithPaging(Paging<Function> paging,Function function);
	/**
	 * 校验功能名和版本号是否重复
	 * @param name
	 * @param version
	 * @return
	 */
	public abstract boolean checkName(String name, String version);
	/**
	 * 根据功能主键查询尚未创建菜单的功能信息(附带机构信息、上级菜单信息等)
	 * @param fun_id
	 * @return
	 */
	public abstract List<Function> getFunctionForMenu(Integer fun_id);
	
	
	/**
	 * 根据机构id获得功能列表
	 * @param function
	 * @return
	 */
	public abstract List<Function> getFunctionForOrg(String org_id);
	/**
	 * 更新功能顺序
	 * @param function
	 * @return 
	 */
	public abstract void updateOrder(Integer moduleId);
	
	
}
