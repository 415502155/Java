package sng.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.pojo.base.Teacher;

/**
 * @类 名： TeacherService
 * @功能描述： 教师Service接口
 * @作者信息： LiuYang
 * @创建时间： 2018年12月16日下午4:07:16
 */
@Service
@Transactional
public interface TeacherService {
	
	/**
	 * 注册率详情
	 */
	public abstract List<Teacher> regRateDetail(Integer cur_term_id,String cur_cam_id,Integer pre_term_id);

	/**
	 * 流失率详情
	 */
	public abstract List<Teacher> wastageRateDetail(Integer term_id,String cam_id);
	/**
	 * 教师班级表
	 */
	List<Map<String, Object>> getTecherInfoList(Integer techerId);
}
