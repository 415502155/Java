package sng.dao;

import java.util.List;
import java.util.Map;

import sng.pojo.base.Teacher;

/**
 * @类 名： TeacherDao
 * @功能描述：教师Dao接口 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月16日下午4:18:22
 */
public interface TeacherDao extends BaseDao<Teacher> {

	/**
	 * 获取教师注册率详情
	 */
	List<Teacher> regRateDetail(Integer cur_term_id, String cur_cam_id,Integer pre_term_id);

	/**
	 * 获取教师流失率详情
	 */
	List<Teacher> wastageRateDetail(Integer term_id, String cam_id);
	
	/**
	 * 教师排名前10统计
	 */
	List<Map<String, Object>> reportTecherTop10(int orgId,int termId,String camId) throws Exception;
	
	/**
	 * 所有教师统计列表
	 */
	List<Map<String, Object>> reportTecherAll(int orgId,int termId,String camId,String techName) throws Exception;
	/***
	 * 查询教师的业务数据
	 */
	List<Map<String, Object>> getTecherInfoList(Integer techerId);

}
