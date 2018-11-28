package cn.edugate.esb;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

public class TeacherControllerTest {

	public static String schedule_url=Constant.base_url;

	@Test
	public void getRoles() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        String param = "?token="+Constant.token+""
        		+ "&udid="+Constant.udid+""
        		+ "&tech_id=2";

        String response = HttpRequest
                .get(schedule_url + "/tech/getRoles"+param)
                .body();

        System.out.println(response);
	}

	@Test
	public void testGetTeacherInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTechsOfDepId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIdentityById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTechSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkClasTech() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkGradeCourse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkGradeTech() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDepinfoManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddRange() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupStudManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupTechManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkClasBZR() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkGradeCourClas() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSkTechClas() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeacherList() {
		fail("Not yet implemented");
	}

	@Test
	public void testTechImportExcel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeaching() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTechofDepsWithStatus() {
		Long start = System.currentTimeMillis();
		String dep_id = "1";
		String org_id = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/tech/getTechofDepsWithStatus").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&dep_id=").append(dep_id).
		append("&org_id=").append(org_id);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}

	@Test
	public void testReplaceManager() {
		Long start = System.currentTimeMillis();
		String dep_id = "1";
		String org_id = "1";
		String tech_ids = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/tech/replaceTeachers").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&tech_ids=").append(tech_ids).
		append("&dep_id=").append(dep_id).
		append("&org_id=").append(org_id);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}

}
