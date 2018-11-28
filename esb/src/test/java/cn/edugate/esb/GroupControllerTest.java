package cn.edugate.esb;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;

public class GroupControllerTest {

	@Test
	public void testAddGroup() {
		Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("token", Constant.adminaToken);
        data.put("udid", Constant.adminaUdid);

        data.put("group_name", "测试组名称");
        data.put("org_id", 1);
        data.put("group_type", 3);
        data.put("type", 2);
        data.put("tech_id", 1);
        data.put("note", "测试组备注说明");
        data.put("mem_ids", "1,2,3,5,6,7,9,10,11,12,13");
        
        String response = HttpRequest
                .post(Constant.base_url + "/group/addGroup")
                .form(data)
                .body();

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	@Test
	public void testDeleteGroup() {
		Integer groupId = 55;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/deleteGroup").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&group_id=").append(groupId);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	@Test
	public void testUpdateGroup() {
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", Constant.adminaToken);
        data.put("udid", Constant.adminaUdid);
        
        data.put("group_id", 53);
        data.put("group_name", "修改后的测试组名称2017-06-10");
        data.put("note", "修改后的测试组备注说明");
        data.put("mem_ids", "1,2");
        data.put("mem_type", 2);
        
        String response = HttpRequest
                .post(Constant.base_url + "/group/updateGroup")
                .form(data)
                .body();

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	@Test
	public void testGetGroup() {
		Integer groupId = 53;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/getGroup").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&group_id=").append(groupId);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

	@Test
	public void testGetGroupsByOrgId() {
		Integer orgId = 23;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/getGroups").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&org_id=").append(orgId);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	@Test
	public void testGetGroupsByOrgIdWithoutMember() {
		Integer orgId = 1;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/getGroups").
		append("?token=").append(Constant.adminaToken).
		append("&udid=").append(Constant.adminaUdid).
		append("&org_id=").append(orgId).
		append("&mem_info=").append("no");
		String response = HttpRequest.get(url.toString()).body();
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		System.out.println(response);
		System.out.println("");
	}
	
	@Test
	public void testGetGroupsByMem() {
		Integer memId = 6;
		Integer memType = 2;
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/getGroups").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid).
			append("&mem_id=").append(memId).
			append("&mem_type=").append(memType);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	@Test
	public void testGetGroupsByNull() {
		StringBuffer url = new StringBuffer(Constant.base_url);
		url.append("/group/getGroups").
			append("?token=").append(Constant.adminaToken).
			append("&udid=").append(Constant.adminaUdid);
        String response = HttpRequest.get(url.toString()).body();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}
	
	

	public static void main(String[] ss) {
//		Map<String, Object> data = new HashMap<String, Object>();
//        
//        data.put("token", "8fad0f2fb1cf80ffff06ca16b044d1a4_1516876503960_119438_0_33c1fbb2");
//        data.put("udid", "3c0c8489-4a5b-4344-8220-4b1a33c1fbb2");
//
//        data.put("param", "{\"c\":[78839,78836],\"s\":[2299,2302],\"g\":[1027,1028,1034,1035,1036,908]}");
//
//        
//        String response = HttpRequest
//                .post("http://127.0.0.1:8080/esb/api/student/getStudentsByRange")
//                .form(data)
//                .body();
//
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
//        System.out.println(response);
//        System.out.println("");
		try{
		String add_url = "http://127.0.0.1:8080/esb/api/student/getStudentsByRange?token=8fad0f2fb1cf80ffff06ca16b044d1a4_1516876503960_119438_0_33c1fbb2&udid=3c0c8489-4a5b-4344-8220-4b1a33c1fbb2";  
		   URL url = new URL(add_url);  
		   HttpURLConnection connection = (HttpURLConnection)url.openConnection();  
		   connection.setDoInput(true);  
		   connection.setDoOutput(true);  
		   connection.setRequestMethod("POST");  
		   connection.setUseCaches(false);  
		   connection.setInstanceFollowRedirects(true);  
		   connection.setRequestProperty("Content-Type","application/json");  
		   connection.connect();  
		   DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
		   JSONObject obj = new JSONObject();  
		      
		   obj.put("g", "[1027,1028,1034,1035,1036,908]");
		   out.writeBytes(obj.toString());  
		   out.flush();  
		   out.close();
		}catch(Exception e){
			
		}
		
		
		
		
	}
	
	@Test
	public void testgetStudentsByRange() {
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("token", Constant.adminaToken);
        data.put("udid", Constant.adminaUdid);
        
        
        
        String response = HttpRequest
                .post("http://127.0.0.1:8080/esb/api/student/getStudentsByRange")
                .form(data)
                .body();

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(response);
        System.out.println("");
	}

}
