package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Menu;

/**
 * 菜单DAO接口
 * Title:IMenuDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:34:38
 */
public interface IMenuDao extends BaseDAO<Menu>{
	
	/**
	 * 根据查询条件获取菜单
	 * @param orgId
	 * @param type
	 * @param status
	 * @return
	 */
	public abstract List<Menu> getMenus(Integer orgId, Integer type, Integer status);
	/**
	 * 为机构创建菜单
	 * @param orgId 机构主键
	 * @param orgType 机构类型(0学校2培训机构3教育局)
	 * @param schoolType 学校类型(1幼儿园2小学3初中4高中5特殊)
	 * @param gradeNumber 年级编码(幼儿园11-15小学21-26初中31-33高中41-43)   
	 */
	public abstract void createMenuForOrg(Integer orgId, Integer orgType, Integer schoolType, String gradeNumber);
	/**
	 * 根据机构主键查询菜单列表(可带分级信息)
	 * @param id 机构主键
	 * @param isTree 是否带树形分级(默认true)
	 * @return
	 */
	public abstract List<Menu> getMenusForOrg(Integer orgId, boolean isTree);
	/**
	 * 无条件的查询全部菜单(For Redis)
	 * @return
	 */
	public abstract List<Menu> getAll();
	/**
	 * 获取机构菜单
	 * @param org_id
	 * @return
	 */
	public abstract List<Menu> getMenusByOgrId(int org_id);

}
