package sng.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import sng.pojo.Result;
import sng.pojo.WXTemplateMessage;
import sng.pojo.WXTemplateMessageData;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @classname SendMessageUtil
 * @description 发送微信和手机短信工具类
 * @author sunwei
 * @time 2018年2月2日
 */
public class SendMessageUtil {
	
	/**
	 * 发送微信模板消息
	 * @param access_token
	 * @param templateMessage
	 * @return
	 * @throws Exception
	 */
	public static String sendTemplateMessageToWeiXin(String access_token, WXTemplateMessage templateMessage) throws Exception {
		String result = "";
		try {
			Thread.sleep(50);
			
			String requestUrl = String.format(Constant.SEND_TEMPLATE_MESSAGE_URL, access_token);

			String jsonTemplateMessage = JsonUtil.toJson(templateMessage);

			result = HttpClientUtil.post(requestUrl, jsonTemplateMessage, "application/json");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return result;
		//return "{ \"errcode\":0, \"errmsg\":\"ok\", \"msgid\":200228332 }";
	}
	
	/**
	 * 发送手机短信
	 * @param mobileStr（多手机号用,分隔）
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public static String sendMobileMessage(String mobileStr, String content) throws Exception {
		// sdata=%1$s&text=%2$s&title=%3$s
		if (StringUtils.isNotBlank(mobileStr)) {
			String result = "fail";
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			List<Map<String,List<String>>> ls = new ArrayList<Map<String,List<String>>>();
			Map<String,List<String>> data = new HashMap<String, List<String>>();
			List<String> tels = new ArrayList<String>();
			String[] mobileArray = mobileStr.split(",");
			for (String tel : mobileArray) {
				tels.add(tel);
			}
			data.put("tels", tels);
			ls.add(data);
			
			paramMap.put("sdata", JsonUtil.toJson(ls));
			paramMap.put("text", content);
			paramMap.put("title", "微校云");

			String resultStr = HttpClientUtil.post(Constant.SEND_MOBILE_MESSAGE_URL, paramMap);

			Result<Object> resultEntity = JsonUtil.getObjectFromJson(resultStr, new TypeReference<Result<Object>>() {});
			if (resultEntity.getCode() == 200 && resultEntity.isSuccess()) {
				result = "success";
			}
			
			return result;
		} else {
			return "success";
		}
		
		
		//return "success";
	}
	

	/**
	 * 组装消息模板
	 * @param messageMap.open_id
	 * @param messageMap.templateId
	 * @param messageMap.url
	 * @param messageMap.name
	 * @param messageMap.subject
	 * @param messageMap.content
	 * @param messageMap.first
	 * @param messageMap.keyword1
	 * @param messageMap.keyword2
	 * @param messageMap.keyword3
	 * @param messageMap.keyword4
	 * @param messageMap.remark
	 * @return
	 */
	public static WXTemplateMessage makeTemplateMessage(Map<String, Object> messageMap) {
		WXTemplateMessage templateMessage = new WXTemplateMessage();
		templateMessage.setTouser((String) messageMap.get("open_id"));
		templateMessage.setTemplate_id((String) messageMap.get("templateId"));
		if(messageMap.containsKey("url")&&StringUtils.isNotEmpty((String) messageMap.get("url"))){
			templateMessage.setUrl((String) messageMap.get("url"));
		}
		Map<String, WXTemplateMessageData> data = new HashMap<String, WXTemplateMessageData>();
		if(messageMap.containsKey("name")&&StringUtils.isNotEmpty((String) messageMap.get("name"))){
			data.put("name", new WXTemplateMessageData((String) messageMap.get("name")));
		}
		if(messageMap.containsKey("subject")&&StringUtils.isNotEmpty((String) messageMap.get("subject"))){
			data.put("subject", new WXTemplateMessageData((String) messageMap.get("subject")));
		}
		if(messageMap.containsKey("content")&&StringUtils.isNotEmpty((String) messageMap.get("content"))){
			data.put("content", new WXTemplateMessageData((String) messageMap.get("content")));
		}
		if(messageMap.containsKey("first")&&StringUtils.isNotEmpty((String) messageMap.get("first"))){
			data.put("first", new WXTemplateMessageData((String) messageMap.get("first")));
		}
		if(messageMap.containsKey("keyword1")&&StringUtils.isNotEmpty((String) messageMap.get("keyword1"))){
			data.put("keyword1", new WXTemplateMessageData((String) messageMap.get("keyword1")));
		}
		if(messageMap.containsKey("keyword2")&&StringUtils.isNotEmpty((String) messageMap.get("keyword2"))){
			data.put("keyword2", new WXTemplateMessageData((String) messageMap.get("keyword2")));
		}
		if(messageMap.containsKey("keyword3")&&StringUtils.isNotEmpty((String) messageMap.get("keyword3"))){
			data.put("keyword3", new WXTemplateMessageData((String) messageMap.get("keyword3")));
		}
		if(messageMap.containsKey("keyword4")&&StringUtils.isNotEmpty((String) messageMap.get("keyword4"))){
			data.put("keyword4", new WXTemplateMessageData((String) messageMap.get("keyword4")));
		}
		if(messageMap.containsKey("remark")&&StringUtils.isNotEmpty((String) messageMap.get("remark"))){
			data.put("remark", new WXTemplateMessageData((String) messageMap.get("remark")));
		}
		templateMessage.setData(data);
		return templateMessage;
	}
	
	
	public static void main(String[] args) {
		try {
			//System.out.println(NumberUtils.isParsable("2.323.34"));
			
			//System.out.println(Integer.valueOf("05").intValue());
			
			/*BigDecimal min = new BigDecimal("253513784000000000000000.000000000000000000000300000000000");
			
			System.out.println(min.toPlainString());
			System.out.println(min.toString());
			System.out.println(min.toEngineeringString());*/
			
			/*String a = "151022,252";
			String[] mobileArray = a.split(",");
			Map<String, Object> mobileMap = new HashMap<String, Object>();
			mobileMap.put("tels", mobileArray);
			System.out.println(JsonUtil.toJson(mobileMap));*/
			
			//String tel = "13821223106,13821215220,15822021845,13034345999,15822619904,18920687336,13011191119,15866660002,18652098248,13011326106,13299915277,18202666737,15822131144,18622462236,13820655748,13132083516,15002258113,18920860919,15902227873,15822468163,13207699527,18502255746,15822682786,13114901450,18649130140,15300010004,13602176441,15602096026,15222484220,15922059452,13920371130,13820688815,18611642210,15822755019,13512959854,13312345678,15866660001,13820073131,18622966409,13821773024,15300010002,18234132104,18102163885,15102263071,13820672276,13920788639,15300010003,13163113085,13012201831,13820067094,13132565708,15899990001,13569981245,13642187496,18202544654,13920898396,13512906845,15802242973,13299969869,15300010006,13752417815,18722313105,15620929982,15122213260,13902172533,15899990002,13200000000,13200000001,13200000002,13200000003,18613844404,13132563758,18526543691,18810010002,18810010003,18810010006,18810010001,15002264014,15922116685,18810010004,18622093558,15122505949,18102163891,13902101841,18102163901,13512493189,13682147295,18920219888,15222440095,13820868470,15922062714,15122056765,13312126591,13752309673,15022454272,13302166775,13821185749,15300010001,15300010005,13502123339,13034349864,13821863653,15899990003,18733829367,15022736753";
			String tel = "15102263071,15822131144,18613844404,17725304297";
			String content = "测试用post方式调用短信2";
			SendMessageUtil.sendMobileMessage(tel, content);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
