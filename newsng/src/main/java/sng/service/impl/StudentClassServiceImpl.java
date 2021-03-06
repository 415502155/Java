package sng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.StudentClassDao;
import sng.entity.StudentClass;
import sng.service.StudentClassService;

/**
 * @类 名： StudentClassServiceImpl
 * @功能描述：学员班级关系实现类 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月21日下午3:01:00
 */
@Component
@Transactional("transactionManager")
public class StudentClassServiceImpl implements StudentClassService {

	@Autowired
	private StudentClassDao studentClassDao;
	
	@Override
	public void update(StudentClass sc) {
		studentClassDao.update(sc);
	}

	@Override
	public StudentClass getInfosById(Integer stu_clas_id) {
		return studentClassDao.getInfosById(stu_clas_id);
	}

	@Override
	public StudentClass getById(Integer stu_clas_id) {
		return studentClassDao.get(StudentClass.class, stu_clas_id);
	}

	@Override
	public void updateStatus(StudentClass sc) {
		studentClassDao.updateStatus(sc);
	}

}
