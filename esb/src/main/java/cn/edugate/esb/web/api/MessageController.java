package cn.edugate.esb.web.api;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edugate.esb.Result;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.util.FileProperties;
import cn.edugate.esb.util.HttpRequestUtil;
import cn.edugate.esb.util.Util;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("/api/message")
public class MessageController {
	private static Logger logger = Logger.getLogger(MessageController.class);
	@Autowired
	private Util util;
	
	@ResponseBody
	@RequestMapping(value = "/sendSmsYZ")
	public Result<Object> sendSmsYZ(HttpServletRequest request, HttpServletResponse response) throws Exception  {		
		Result<Object> result = new Result<Object>();	
		try{
			String phones = request.getParameter("phone");
			String[] es = null;
			if(phones.indexOf(",")==-1){
				es = new String[1];
				es[0] = phones;
			}
			else
				es = phones.split(",");
			String content = request.getParameter("content");
			String ss = "success";
		
//			String sms_name = FileProperties.getProperty("qxt_sms_name");
//			String sms_pass = FileProperties.getProperty("qxt_sms_pass");
//			String sms_url = FileProperties.getProperty("qxt_sms_url");
//			
//			for(int i=0;i<es.length;i++){
//				HttpRequestUtil.sendGet(sms_url, String.format("username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4",sms_name,sms_pass,es[i],URLEncoder.encode(content,"gb2312"),""));
//			}
			this.util.schoolsms(es, content, "校园云办公");
			result.setData(ss);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		}catch(Exception e){
			e.printStackTrace();	
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	

	
//	@ResponseBody
//	@RequestMapping(value = "/sendSms")
//	public Result<Object> sendSms(HttpServletRequest request, HttpServletResponse response) {		
//		Result<Object> result = new Result<Object>();	
//		int schoolid = Integer.parseInt(request.getParameter("org_id"));
//		String tels = request.getParameter("tels");
//		String text = request.getParameter("content");
//		
//		int type = Integer.parseInt(request.getParameter("type"));
//		String ss = "success";
//		
//		try{
//			text = URLEncoder.encode(text,"gb2312");
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("schoolid", schoolid);
//			map.put("tels", tels.split(","));
//			map.put("type", type);		
//			String sdata = util.getJSONFromPOJO(map);		
//			String sms_url = FileProperties.getProperty("sms_url");		
//			String url = sms_url+"?sdata=["+sdata+"]&text="+text;
//			
//			System.out.println("url:"+url);
//			HttpRequestUtil.sendGet(url);
//		}catch(Exception e){
//			e.printStackTrace();	
//			ss="error";
//		}
//		result.setData(ss);
//		result.setSuccess(true);
//		result.setMessage(EnumMessage.success.getMessage());
//		result.setCode(EnumMessage.success.getCode());
//		return result;
//	}
	

}
