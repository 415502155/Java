package sng.dao;

import java.util.List;

import sng.entity.Category;
import sng.pojo.ParamObj;

public interface BaseSetCategoryManageDao extends BaseDao<Category> {

	
	/**
	 * 传不同的参数可以支持以下功能：
	 * 1:查询类目名字是否有重复
	 * 2：支持不同机构下的类目，和获取所有机构下的类目
	 * @param category
	 * @return
	 */
	public List<Category> queryCategoryListInfo(ParamObj paramObj);
	
	
	/**
	 * 过滤类目信息，判断在其他业务数据中是否有关联
	 * @param paramObj
	 * @return
	 */
	public int filterCategoryInfo(ParamObj paramObj);
	
	
	
	
}
