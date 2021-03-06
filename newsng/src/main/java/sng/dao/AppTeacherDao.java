package sng.dao;

import java.util.List;

import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.base.Parent;


/**
 * @desc 移动端---教师dao接口
 * @author magq
 * @data 2018-11-2
 * @version 1.0
 */
public interface AppTeacherDao extends BaseDao<Parent>{

	/**
	 * 根据学员信息查询家长信息
	 * @param paramObj
	 * @return
	 */
	public List queryParentInfoByStu(ParamObj paramObj);
	
	/**
	 * 
	 * @param paramObj
	 * @return
	 */
	public List echartInfo(ParamObj paramObj);
	
}
