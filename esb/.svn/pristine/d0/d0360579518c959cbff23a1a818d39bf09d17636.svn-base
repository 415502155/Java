package cn.edugate.esb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

public class RoleControllerTest {

	@Test
	public void testGetUserAuth() {
		Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("token", Constant.adminaToken);
        data.put("udid", Constant.adminaUdid);
        data.put("org_id", 1);
        data.put("user_id", 163);
        
        String response = HttpRequest
                .post(Constant.base_url + "/role/getUserAuth")
                .form(data)
                .body();

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	@Test
	public void testGetOrgUserMenus() {
		Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("token", Constant.adminaToken);
        data.put("udid", Constant.adminaUdid);
        data.put("org_id", 1);
        data.put("user_id", 163);
        
        String response = HttpRequest
                .post(Constant.base_url + "/role/getOrgUserMenus")
                .form(data)
                .body();

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

}
