package cn.edugate.esb.redis.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.service.CourseService;

@Cache(name="课程缓存",entities={Course.class})
public class CourseCacheProvider   implements CacheProvider<Course>,java.io.Serializable{
	static Logger logger=LoggerFactory.getLogger(CourseCacheProvider.class);
	private static final long serialVersionUID = 1L;	
	@Autowired
	private RedisCourseDao redisCourseDao;	
	
	@Autowired
	private CourseService CourseService;
	
	
	
	@Override
	public void add(Course en) {
		// TODO Auto-generated method stub
		redisCourseDao.add(en);
	}

	@Override
	public void update(Course en) {
		// TODO Auto-generated method stub
		Course oldCourse = redisCourseDao.getCourseById(en.getCour_id().toString(), null);
		if(oldCourse!=null)
			this.redisCourseDao.delete(oldCourse);
		this.redisCourseDao.add(en);
	}

	@Override
	public void delete(Course en) {
		// TODO Auto-generated method stub
		this.redisCourseDao.delete(en);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisCourseDao.deleteAll();
		List<Course> list = CourseService.getAll();
		this.redisCourseDao.add(list);
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Course> list = redisCourseDao.getCoursByOrgId(org_id);
		for (Course c : list) {
			redisCourseDao.delete(c);
		}
		List<Course> courses = CourseService.getByOrgId(org_id);
		this.redisCourseDao.add(courses);
	}
}
