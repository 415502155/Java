package sng.service;

import org.springframework.stereotype.Service;

import sng.entity.Campus;
import sng.entity.Category;
import sng.entity.Classroom;
import sng.entity.Cooperative;
import sng.entity.Rulelist;
import sng.entity.Subject;
import sng.entity.Term;
import sng.pojo.ParamObj;
import sng.pojo.Result;

/**
 * @desc 基础设置模块Service层接口(校区管理、学期管理、科目管理、教师管理、合作机构、规则设置)
 * @author magq
 * @data 2018-10-26
 * @version 1.0
 */
@Service
public interface BaseSetService {

	/**
	 * 创建/编辑校区
	 * 
	 * @return 结果集
	 */
	public Result createAndUpdateCampus(Campus campus);

	/**
	 * 校区管理列表
	 * 
	 * @param campus
	 * @return
	 */
	public Result queryCampusListInfo(ParamObj paramObj);

	/**
	 * 删除校区
	 * 
	 * @param campus
	 * @return
	 */
	public Result deleteCampus(ParamObj paramObj);

	/**
	 * 创建和跟新学期信息
	 * 
	 * @param term
	 * @return
	 */
	public Result createAndUpdateTerm(Term term);

	/**
	 * 查询学期信息列表
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryTermListInfo(ParamObj paramObj);

	/**
	 * 删除学期信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result deleteTermByTerm(ParamObj paramObj);

	/**
	 * 创建/编辑类目 单表操作
	 * 
	 * @param category
	 * @return
	 */
	public Result createAndUpdateCategoryInfo(Category category);

	/**
	 * 此接口支持获取当前机构下的类目，也是支持获取所有的类目
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryCategoryListInfo(ParamObj paramObj);

	/**
	 * 创建科目信息
	 * 
	 * @param subject
	 * @return
	 */
	public Result createAndUpdateSubjectInfo(Subject subject);

	/**
	 * 科目管理列表
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result querySubjectListInfo(ParamObj paramObj);

	/**
	 * 删除科目
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result deleteSubjectInfo(ParamObj paramObj);

	/**
	 * 创建/编辑教室
	 * 
	 * @param classroom
	 * @return
	 */
	public Result createAndUpdateClassRoomInfo(ParamObj paramObj);

	/**
	 * 获取教室信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryClassRoomListInfo(ParamObj paramObj);

	/**
	 * 删除教室
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result deleteClassRoomInfo(ParamObj paramObj);

	/**
	 * 创建/编辑机构保存
	 * 
	 * @param cooperative
	 * @return
	 */
	public Result createAndUpdateCooperativeInfo(Cooperative cooperative);

	/**
	 * 获取合作机构信息列表
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryCooperativeListInfo(ParamObj paramObj);

	/**
	 * 删除合作机构信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result deleteCooperativeInfo(ParamObj paramObj);

	/**
	 * 缴费新建/编辑保存接口
	 * 
	 * @param rulelist
	 * @return
	 */
	public Result createAndUpdatePaySetInfo(Rulelist rulelist);

	/**
	 * 获取缴费设置信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryPaySetInfo(ParamObj paramObj);

	/**
	 * 认证设置/编辑保存
	 * 
	 * @param rulelist
	 * @return
	 */
	public Result createAndUpdateAuthSetInfo(Rulelist rulelist);

	/**
	 * 获取认证设置信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryAuthSetInfo(ParamObj paramObj);

}
