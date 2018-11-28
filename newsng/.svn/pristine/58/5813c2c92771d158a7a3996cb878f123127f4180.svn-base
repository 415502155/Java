package sng.dao;

import java.util.List;

import sng.entity.Classroom;
import sng.pojo.CampusAndClassRoomPojo;
import sng.pojo.ClassRoomAndClassesPojo;
import sng.pojo.ParamObj;
import sng.util.Paging;

/**
 * @desc 基础设置模块-教室管理dao层接口
 * @author magq
 * @data 2018-10-29
 * @version 1.0
 */
public interface BaseSetClassRoomManageDao extends BaseDao<Classroom>{
	
	/**
	 * 根据不同条件查询所对应的教室列表
	 * @param campus
	 * @return
	 */
	//public List<Classroom> queryClassRoomList(ParamObj paramObj);
	
	
	/**
	 * 根据不同条件查询校区与教室信息数据
	 * @param paramObj
	 * @return
	 */
	public Paging queryCampusAndClassRoomInfo(ParamObj paramObj);
	
	/**
	 * 根据不同条件查询教室与班级信息数据
	 * @param paramObj
	 * @return
	 */
	public List<ClassRoomAndClassesPojo> queryClassRoomAndClassesInfo(ParamObj paramObj);
	
	/**
	 * 逻辑删除教室信息
	 * @param paramObj
	 */
	public void deleteClassRoomInfo(ParamObj paramObj);
	
	
	
	

	
	
	
	

}
