package cn.edugate.esb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.edugate.esb.util.HttpRequest;
import cn.edugate.esb.util.HttpRequest.HttpRequestException;

import com.google.gson.Gson;


public class SmsTest {

	public static String schedule_url=Constant.base_url;

    @Test
    public void addRole() throws HttpRequestException, UnsupportedEncodingException {
    	String s = HttpRequest.get("http://211.147.239.62:9050/cgi-bin/sendsms" + 
    			String.format("?username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4","sjwy","sjwy-123","15822468163",URLEncoder.encode("我的测试","gb2312"),"")).body();
	}
	
    
}
