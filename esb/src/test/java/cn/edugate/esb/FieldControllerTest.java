package cn.edugate.esb;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

public class FieldControllerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testAddField() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteField() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetField() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFields() {
		Integer orgId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/field/getFields").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&org_id=").append(orgId);
		String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

}
