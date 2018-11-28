package cn.edugate.esb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

import com.google.gson.Gson;


public class RoleTest {

	public static String schedule_url=Constant.base_url;

    @Test
    public void addRole() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        data.put("token", Constant.token);
        data.put("udid", Constant.udid);

        data.put("roleName", "测试角色");
        data.put("roleNote", "测试角色备注");
        data.put("org_id", 1);
        data.put("rl_id", 1);
        
        
        authorities.put("oagw", 5);
        authorities.put("oamsg", 3);
        authorities.put("oanotice", 9);
        authorities.put("oafile", 262143);
        data.put("authorities", new Gson().toJson(authorities));

        String response = HttpRequest
                .post(schedule_url + "/role/add")
                .form(data)
                .body();

        System.out.println(response);
	}
	
    @Test
    public void updateRole() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        data.put("token", Constant.token);
        data.put("udid", Constant.udid);

        data.put("roleId", 42);
        data.put("roleName", "测试角色abb");
        data.put("roleNote", "测试角色备注abb");
        data.put("org_id", 1);
        data.put("rl_id", 1);
        
        
        authorities.put("oagw", 5);
        authorities.put("oamsg", 3);
        authorities.put("oanotice", 262143);
        authorities.put("oafile", 262143);
        data.put("authorities", new Gson().toJson(authorities));

        String response = HttpRequest
                .post(schedule_url + "/role/update")
                .form(data)
                .body();

        System.out.println(response);
	}
	
    @Test
    public void deleteRole() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        data.put("token", Constant.token);
        data.put("udid", Constant.udid);

        data.put("roleId", 42); 

        String response = HttpRequest
                .post(schedule_url + "/role/delete")
                .form(data)
                .body();

        System.out.println(response);
	}
    
    @Test
	public void getRoleLabels() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        String param = "?token="+Constant.token+""
        		+ "&udid="+Constant.udid+""
        		+ "&rl_type=0";

        String response = HttpRequest
                .get(schedule_url + "/role/getRoleLabels"+param)
                .body();

        System.out.println(response);
	}
	
    @Test
    public void setTeacherRoles() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        data.put("token", Constant.token);
        data.put("udid", Constant.udid);

        data.put("tech_id", 2); 
        data.put("roleIds", "40,41,42");

        String response = HttpRequest
                .post(schedule_url + "/role/setTeacherRoles")
                .form(data)
                .body();

        System.out.println(response);
	}
}
