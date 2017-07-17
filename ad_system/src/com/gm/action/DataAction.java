package com.gm.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gm.service.OpenService;
import com.gm.utils.HttpUtil;
import com.gm.utils.MD5Util;

/**
 * 控制器  接口控制调用
 * 对外提供数据入库的接口,目前主要调用方是<各种应用></br>
 * @author fyr
 *
 */
@SuppressWarnings("serial") 
@Controller
public class DataAction extends BaseAction {
	
	Logger logger = LoggerFactory.getLogger(DataAction.class);
	
	@Autowired
	OpenService openService;
	
	/**
	 * 用户打开应用数据入库入口 
	 * 例子：
	 * http://localhost:8080/ad_system/user_info.htm
	 * {"user_app_list":"com.aiqiyi,com.paobuji,com.xiaomi","sys_version":"Huawei 123","oper":1,"imei":"987654321","iccid":"456321","user_app_package":"com.paobuji","imsi":"987654321","ip":"43.240.244.65","mobile":"18210545658"}
	 */
	@Action(value="user_info")
	public void userinfo(){
		String result="";
		String json = null;
		json = HttpUtil.getRequestInputStreamStr(this.getRequest());
		logger.info("接受数据为:" + json);
		if (!json.equals("")) {
			try {
				json = json.replaceAll("\n", "");
				json = json.replaceAll("\r", "");
				JSONObject requestStr=null;
				requestStr = JSONObject.fromObject(json);
				requestStr.put("op", "insert_and_update_user_info");
				logger.info("得到数据为:" + requestStr);
				result=openService.controlInsertService(requestStr);//正确执行返回结果
			} catch (Exception e) {
				e.printStackTrace();
				result=openService.getErrMsg(3, e.getMessage()); //程序错误
			}
		}else{
			result=openService.getErrMsg(4);//json为空
		}
		this.sendText2(result);
	}
	
	/**
	 * 用户访问URL数据入库入口 
	 * 例子：
	 * http://localhost:8080/ad_system/user_url.htm
	 * {"imei":"987654321","imsi":"987654321","url":"http://www.baidu.com/970367.html"}
	 */
	@Action(value="user_url")
	public void user_url(){
		String result="";
		String json = null;
		json = HttpUtil.getRequestInputStreamStr(this.getRequest());
		logger.info("接受数据为:" + json);
		if (!json.equals("")) {
			try {
				json = json.replaceAll("\n", "");
				json = json.replaceAll("\r", "");
				JSONObject requestStr=null;
				requestStr = JSONObject.fromObject(json);
				requestStr.put("op", "insert_user_url");
				logger.info("得到数据为:" + requestStr);
				result=openService.controlInsertService(requestStr);//正确执行返回结果
			} catch (Exception e) {
				e.printStackTrace();
				result=openService.getErrMsg(3, e.getMessage()); //程序错误
			}
		}else{
			result=openService.getErrMsg(4);//json为空
		}
		this.sendText2(result);
	}
	/**
	 * 用户搜索关键词数据入库入口 
	 * 例子：
	 * http://localhost:8080/ad_system/user_search.htm
	 * {"imei":"987654321","imsi":"987654321","search":"vr ar mr"}
	 */
	@Action(value="user_search")
	public void user_search(){
		String result="";
		String json = null;
		json = HttpUtil.getRequestInputStreamStr(this.getRequest());
		logger.info("接受数据为:" + json);
		if (!json.equals("")) {
			try {
				json = json.replaceAll("\n", "");
				json = json.replaceAll("\r", "");
				JSONObject requestStr=null;
				requestStr = JSONObject.fromObject(json);
				requestStr.put("op", "insert_user_search");
				logger.info("得到数据为:" + requestStr);
				result=openService.controlInsertService(requestStr);//正确执行返回结果
			} catch (Exception e) {
				e.printStackTrace();
				result=openService.getErrMsg(3, e.getMessage()); //程序错误
			}
		}else{
			result=openService.getErrMsg(4);//json为空
		}
		this.sendText2(result);
	}
	
	
	public static void main(String[] args)  throws Exception {
		
		String aa="18310545658_18310545658-YMWH-1000_82#816_10669967_1_1000_123456";
		System.out.println(MD5Util.MD5(aa).toLowerCase());
		
		String url="http://news.jsinfo.net/newscontent/2016/8/970367.shtml";
		//Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE); 
		Matcher matcher = p.matcher(url);
		matcher.find();
		System.out.println(matcher.group()); 
		System.out.println(matcher.group(1)); 
	}
}
