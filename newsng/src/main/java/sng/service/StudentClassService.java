package sng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.StudentClass;

/**
 * @类 名： StudentClassService
 * @功能描述： 班级学员服务接口
 * @作者信息： LiuYang
 * @创建时间： 2018年11月21日下午2:57:48
 */
@Service
@Transactional
public interface StudentClassService {

	/**
	 * @Title : getById 
	 * @功能描述: 根据主键查询关系附带各种信息
	 * @返回类型：StudentClass 
	 * @throws ：
	 */
	public StudentClass getInfosById(Integer stu_clas_id);

	/**
	 * @Title : getById 
	 * @功能描述: 根据主键查询关系
	 * @返回类型：StudentClass 
	 * @throws ：
	 */
	public StudentClass getById(Integer stu_clas_id);

	/**
	 * @Title : update 
	 * @功能描述: 更新学员班级关系表
	 * @返回类型：void 
	 * @throws ：
	 */
	public void update(StudentClass sc);

	/**
	 * 更新学员状态
	 */
	public void updateStatus(StudentClass sc);

}
