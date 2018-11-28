package sng.dao;

import sng.entity.Term;
import sng.pojo.ParamObj;
import sng.util.Paging;
/**
 * @desc 基础设置模块-学期管理dao层接口
 * @author magq
 * @data 2018-10-29
 * @version 1.0
 */
public interface BaseSetTermManageDao extends BaseDao<Term>{
	
	/**
	 * 创建编辑学期名称是否有重名
	 * @param paramObj
	 * @return
	 */
	public int filterTermName(ParamObj  paramObj);
	/**
	 * 查询学期信息列表
	 * @param paramObj
	 * @return
	 */
	public Paging queryTermListInfo(ParamObj  paramObj);
	/**
	 * 删除学期信息
	 * @param paramObj
	 */
	public void deleteTermByTerm(ParamObj  paramObj);
	
	/**
	 * 判断是否可以删除和编辑数据
	 * @param paramObj
	 * @return
	 */
	public int isDelOrUpdateTerminfo(ParamObj  paramObj);
	

}
