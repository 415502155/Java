package sng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.TeacherDao;
import sng.pojo.base.Teacher;
import sng.service.TeacherService;

/**
 * @类 名： TeacherServiceImpl
 * @功能描述：教师接口实现类 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月16日下午4:17:10
 */
@Component
@Transactional("transactionManager")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public List<Teacher> regRateDetail(Integer cur_term_id,String cur_cam_id, Integer pre_term_id) {
		return teacherDao.regRateDetail(cur_term_id, cur_cam_id, pre_term_id);
	}

	@Override
	public List<Teacher> wastageRateDetail(Integer term_id, String cam_id) {
		return teacherDao.wastageRateDetail(term_id, cam_id);
	}

	@Override
	public List<Map<String, Object>> getTecherInfoList(Integer techerId) {
		return teacherDao.getTecherInfoList(techerId);
	}

}
