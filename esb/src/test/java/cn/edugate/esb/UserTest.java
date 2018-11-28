package cn.edugate.esb;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import cn.edugate.esb.util.HttpRequest;


public class UserTest {

	public static String schedule_url=Constant.common_url;

	@Test
	@SuppressWarnings("unused")
    public void sendValidCode() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
//        data.put("token", Constant.token);
//        data.put("udid", Constant.udid);
        data.put("phone", "15822468163");

        String response = HttpRequest
                .post(schedule_url + "/sendValidCode")
                .form(data)
                .body();

        System.out.println(response);
	}
    
    @Test
	@SuppressWarnings("unused")
    public void validCode() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
//        data.put("token", Constant.token);
//        data.put("udid", Constant.udid);
        data.put("phone", "15822468163");
        data.put("code", "429250");

        String response = HttpRequest
                .post(schedule_url + "/validCode")
                .form(data)
                .body();

        System.out.println(response);
	}
    
    @Test
	@SuppressWarnings("unused")
    public void reSetPassword() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
//        data.put("token", Constant.token);
//        data.put("udid", Constant.udid);
        data.put("phone", "15822468163");
        data.put("code", "279318");
        data.put("passwd", "111111");
        

        String response = HttpRequest
                .post(schedule_url + "/reSetPassword")
                .form(data)
                .body();

        System.out.println(response);
	}
    
    @Test
	@SuppressWarnings("unused")
    public void modifyPwd() {
		Map<String, Object> data = new HashMap<String, Object>();
        Map<String,Integer> authorities = new HashMap<String,Integer>();
        
        data.put("token", Constant.token);
        data.put("udid", Constant.udid);
        data.put("oldpwd", "222222");
        data.put("passwd", "111111");
        

        String response = HttpRequest
                .post(schedule_url + "/api/modifyPwd")
                .form(data)
                .body();

        System.out.println(response);
	}
    
	
	
}
