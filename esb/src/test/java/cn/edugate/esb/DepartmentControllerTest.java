package cn.edugate.esb;

import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

public class DepartmentControllerTest {

	@Test
	public void testGetDepsDetailList() {
		Long start = System.currentTimeMillis();
		String orgId = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/dep/getDepsDetailList").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&org_id=").append(orgId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}


	@Test
	public void testgetDepsAndTechOfOrgId() {
		Long start = System.currentTimeMillis();
		String orgId = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/dep/getDepsAndTechOfOrgId").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&org_id=").append(orgId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}

	@Test
	public void testGetMenusByOrgId() {
		Long start = System.currentTimeMillis();
		String orgId = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/org/getMenus").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&org_id=").append(orgId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}


	@Test
	public void testReplaceTeachers() {
		Long start = System.currentTimeMillis();
		String dep_name = "1";
		String dep_id = "1";
		String org_id = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/tech/replaceTeachers").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&dep_name=").append(dep_name).
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
	public void testCheckDepName() {
		Long start = System.currentTimeMillis();
		String dep_name = "1";
		String dep_id = "1";
		String org_id = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/dep/checkDepName").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&dep_name=").append(dep_name).
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
	public void testRemoveMember() {
		Long start = System.currentTimeMillis();
		String tech_ids = "1";
		String from_dep_id = "1";
		String to_dep_id = "1";
		String org_id = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/dep/removeMember").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&tech_ids=").append(tech_ids).
		append("&from_dep_id=").append(from_dep_id).
		append("&to_dep_id=").append(to_dep_id).
		append("&org_id=").append(org_id);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}

	@Test
	public void testAddTeachers() {
		Long start = System.currentTimeMillis();
		String tech_ids = "1";
		String dep_id = "1";
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/dep/addTeachers").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&tech_ids=").append(tech_ids).
		append("&dep_id=").append(dep_id);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
        Long end = System.currentTimeMillis();
        System.out.println("lllllllllllllllllll---"+(end-start)+"---yyyyyyyyyyyyyyyyyyyyy");
	}

}
