package cn.edugate.esb;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.service.Class2StudentService;
import cn.edugate.esb.service.StudentService;


import cn.edugate.esb.util.HttpRequest;
import junit.framework.TestCase;

public class StudentControllerTest extends TestCase {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private Class2StudentService class2StudentService;

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testGetStudent() {
		Integer studentId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/student/getStudent").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&stud_id=").append(studentId);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	public void testGetStudentsByOrgId() {
		Integer orgId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/student/getStudents").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&org_id=").append(orgId);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	public void testGetStudentsByGradeId() {
		Integer gradeId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/student/getStudents").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&grade_id=").append(gradeId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	public void testGetStudentsByClasId() {
		Integer clasId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/student/getStudents").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&clas_id=").append(clasId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	public void testGetStudentsByNull() {
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/student/getStudents").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	
	public void testgetStudentsByRangeByNull() {
		StringBuffer url = new StringBuffer("http://t.shijiwxy.5tree.cn/esb/api");
		url.append("/student/getStudentsByRange").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid);
		String response = HttpRequest.post(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	

}
