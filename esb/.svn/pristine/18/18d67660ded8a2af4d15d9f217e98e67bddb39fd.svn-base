package cn.edugate.esb.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.CourseDao;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.service.CourseService;

@Component
@Transactional("transactionManager")
public class CourseServiceImpl  implements CourseService{
 
	
	@Autowired
	private CourseDao courseDao;
	
	@Override
	public List<Course> getAll() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		return courseDao.getCourseAll();
	}

	@Override
	public int updateCourseName(int orgID, int courseID, String courseName) {
		if (StringUtils.isNotBlank(courseName)) {
			// 先获取更新之前的课程名称
			Course c = courseDao.get(Course.class, courseID);
			if (!c.getCour_name().trim().equals(courseName.trim())) {
				// 检查要修改成的课程名称在当前机构下是否
				int count = this.checkName(c.getOrg_id(), courseName.trim());
				if (count == 0) {
					// 进行更新
					c.setOrg_id(orgID);
					c.setCour_name(courseName.trim());
					courseDao.update(c);
					return 1;
				} else {
					// 名称已存在，不进行更新
					return 0;
				}
			} else {
				// 名字相同视为成功
				return 1;
			}
		} else {
			return 0;
		}
	}

	@Override
	public int checkName(int orgID, String courseName) {
		return courseDao.checkName(orgID, courseName);
	}

	@Override
	public int saveCourse(int orgID, String courseName) {
		if (StringUtils.isNotBlank(courseName)) {
			int count = this.checkName(orgID, courseName.trim());
			if (count == 0) {
				Course c = new Course();
				c.setOrg_id(orgID);
				c.setCour_name(courseName.trim());
				c.setInsert_time(new Date());
				c.setIs_del(0);
				courseDao.save(c);
				return c.getCour_id().intValue();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public List<Course> getByOrgId(String org_id) {
		return courseDao.getCourseList(Integer.parseInt(org_id));
	}

}
