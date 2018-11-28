package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Field;

/**
 * 场地DAO
 * Title:IFieldDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午10:15:32
 */
public interface IFieldDao extends BaseDAO<Field>{

	/**
	 * 根据查询条件获得场地列表
	 * @param field
	 * @return
	 */
	List<Field> getList(Field field);

}
