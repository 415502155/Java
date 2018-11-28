package sng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.BaseDaoEx;
import sng.dao.StudentDao;
import sng.pojo.base.Student;
import sng.util.Paging;

@Service
@Transactional
public class TestServiceImpl {
	@Resource
	private BaseDaoEx baseDaoEx;
	@Resource
	private StudentDao studentDao;
	
	public void test1() {
		Student student=new Student();
//		student.setStudId(1);
//		student.setName("学ee1111");
//		student.setOrgId(2);
//		student.setIsDel(0);
//		student.setInsertTime(new Date());
//		Integer id=(int)baseDaoEx.saveOne(student);
		System.out.println(1);
	}
	
	public Paging test2() {
		String sql="select stud_id,stud_name,insert_time as insertTime from edugate_base.student";
		String countSql="select count(*) from ("+sql+") t";
		
		List<Object> params=new ArrayList<>();
//		params.add(1);
		Paging page=baseDaoEx.queryPageBySql(sql, countSql, 1, 3, params);
		return page;
	}
	
	public static void main(String[] args) {
//		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"spring-context.xml"});
		
		ApplicationContext context=new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring/spring-context.xml");
		TestServiceImpl s=(TestServiceImpl)context.getBean("testServiceImpl");
		s.test1();
		
		System.out.println("main执行结束");
	}
}
