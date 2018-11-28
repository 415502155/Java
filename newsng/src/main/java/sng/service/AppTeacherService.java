package sng.service;

import org.springframework.stereotype.Service;

import sng.pojo.ParamObj;
import sng.pojo.Result;

/**
 * @desc 移动端---教师
 * @author magq
 * @data 2018-11-1
 * @version 1.0
 */
@Service
public interface AppTeacherService {

	/**
	 * 获取职教班级
	 * @param paramObj
	 * @return
	 */
	public Result queryClassByTeacher(ParamObj paramObj);

	/**
	 * 按班级查询学员列表
	 * @param paramObj
	 * @return
	 */
	public Result queryStudentByClass(ParamObj paramObj);

	/**
	 * 根据学生获取家长信息
	 * 
	 * @param paramObj
	 * @return
	 */
	public Result queryParentInfoByStu(ParamObj paramObj);

}
