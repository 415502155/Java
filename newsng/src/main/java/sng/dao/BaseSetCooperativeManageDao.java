package sng.dao;

import java.util.List;
import java.util.Map;

import sng.entity.Cooperative;
import sng.pojo.ParamObj;
import sng.util.Paging;
/**
 * @desc 基础设置模块-合作机构dao接口
 * @author magq
 * @data 2018-10-31
 * @version 1.0
 */
public interface BaseSetCooperativeManageDao extends BaseDao<Cooperative>{
	
	
	
	/**
	 * 根据机构ID,或者合作机构ID获取合作机构信息
	 * @param paramObj
	 * @return
	 */
	public Paging<Cooperative> queryCooperativeListInfo(ParamObj paramObj);
	
	/**
	 * 更新合作机构信息
	 * @param cooperative
	 * @return
	 */
	public int upateCooperativeInfo(Cooperative cooperative);
	
	
	/**
	 * 逻辑删除合作机构信息
	 * @param paramObj
	 * @return
	 */
	public int deleteCooperativeInfo(ParamObj paramObj);

	
	/**
	 * 根据不同条件查询合作机构与班级信息数据
	 * @param paramObj
	 * @return
	 */
	public List<Map<String,Object>> queryCooperativeAndClassesInfo(ParamObj paramObj);

}
