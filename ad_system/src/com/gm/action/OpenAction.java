package com.gm.action;

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
 * 对外提供数据的接口,目前主要调用方是<游戏管理平台></br>
 * @author fyr
 *
 */
@SuppressWarnings("serial") 
@Controller
public class OpenAction extends BaseAction {
	
	Logger logger = LoggerFactory.getLogger(OpenAction.class);
	
	@Autowired
	OpenService openService;
	
	/**
	 * 数据后台操作入口
	 */
	@Action(value="getapi")
	public void openApi(){
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
				logger.info("得到数据为:" + requestStr);
				result=openService.controlService(requestStr);//正确执行返回结果
			} catch (Exception e) {
				e.printStackTrace();
				result=openService.getErrMsg(3, e.getMessage()); //程序错误
			}
		}else{
			result=openService.getErrMsg(4);//json为空
		}
		this.sendText(result);
	}
	
	public static void main(String[] args)  throws Exception {
		
		String aa="18310545658_18310545658-YMWH-1000_82#816_10669967_1_1000_123456";
		System.out.println(MD5Util.MD5(aa).toLowerCase());
		//user_app_package,sys_version,oper,mobile,ip,imei,imsi,iccid,user_app_list
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("user_app_package", "com.aiqiyi");
		jsonObject.put("sys_version", "SAMSUNG i9100");
		jsonObject.put("oper", 2);
		jsonObject.put("mobile", "18310545658");
		jsonObject.put("ip", "43.240.244.65");
		jsonObject.put("imei", "123456789");
		jsonObject.put("imsi", "123456789");
		jsonObject.put("iccid", "987654321");
		jsonObject.put("user_app_list", "com.aiqiyi,com.paobuji,com.youku");
		System.err.println(jsonObject.toString());
	}
}
