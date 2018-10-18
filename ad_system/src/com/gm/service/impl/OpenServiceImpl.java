package com.gm.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm.service.AdAppUserInterfaceService;
import com.gm.service.OpenService;
import com.gm.service.UserDataInterfaceService;

@Service
public class OpenServiceImpl implements OpenService {

	Logger logger = LoggerFactory.getLogger(OpenServiceImpl.class);	
	
	@Autowired
	AdAppUserInterfaceService adAppUserInterfaceService ;
	
	@Autowired
	UserDataInterfaceService userDataInterfaceService;
	
	/**
	 * 前端数据接口方法调用分配
	 */
	public String controlService(JSONObject jsonObject) throws Exception{
		String result="";
		Class clazz=adAppUserInterfaceService.getClass();
		try {
			String op=jsonObject.getString("op"); //获取要访问的接口op
			String[] opStrings=op.split("_");
			String methodname="get";
			for(String opString:opStrings){
				opString=opString.replaceFirst(opString.substring(0,1), opString.substring(0,1).toUpperCase());
				methodname+=opString;
			}
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodname, JSONObject.class);
				result=method.invoke(adAppUserInterfaceService,jsonObject).toString();
			} catch (NoSuchMethodException e) {
				result=getErrMsg(-1,op);
				return result;
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (SecurityException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage());
			return result;
		}
		result=getErrMsg(0,"ok", result);
		return result;
	}
	
	/**
	 * 数据入库接口分配
	 */
	@Override
	public String controlInsertService(JSONObject jsonObject) throws Exception {
		String result="";
		Class clazz=userDataInterfaceService.getClass();
		try {
			String op=jsonObject.getString("op"); //获取要访问的接口op
			String[] opStrings=op.split("_");
			String methodname="";
			for(String opString:opStrings){
				opString=opString.replaceFirst(opString.substring(0,1), opString.substring(0,1).toUpperCase());
				methodname+=opString;
			}
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodname, JSONObject.class);
				result=method.invoke(userDataInterfaceService,jsonObject).toString();
			} catch (NoSuchMethodException e) {
				result=getErrMsg(-1,op);
				return result;
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		} catch (SecurityException e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage()); 
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			result=getErrMsg(5, e.getMessage());
			return result;
		}
		result=getErrMsg(0,"ok", result);
		return result;
	}
	
	@Override
	public String getErrMsg(int errno, String ...errmsg) {
		//errmsg[0]  返回信息 errmsg    errmsg[1]：返回值，默认为 {}
		String errmsgstr="ok";
		if(errno==0){
			logger.info("ok");
			errmsgstr=errmsg[0];
		}else if(errno==1){
			logger.info("没有提供《"+errmsg[0]+"》表");
			errmsgstr="没有提供《"+errmsg[0]+"》表";
		}else if(errno==2){
			logger.info("数字签名错误");
			errmsgstr="数字签名错误";
		}else if(errno==3){//错误的接口操作
			logger.info("执行失败,程序内部错误："+errmsg[0]);
			errmsgstr="执行失败,程序内部错误："+errmsg[0];
		}else if(errno==4){//错误的接口操作
			logger.info("json串不能为空");
			errmsgstr="json串不能为空";
		}else if(errno==5){//错误的接口操作
			logger.info("参数错误，请核对所传参数");
			errmsgstr="参数错误，请核对所传参数";
		}else if(errno==6){//错误的接口操作
			logger.info("没有提供《"+errmsg[0]+"》实体");
			errmsgstr="没有提供《"+errmsg[0]+"》实体";
		}else{
			logger.info("其他错误，可自己定义错误");
			errmsgstr="其他错误，可自己定义错误";
		}
		
		//拼接要返回的json串
		StringBuilder result = new StringBuilder();
		result.append("{\"errno\":"+errno+",\"data\":");
		//如果errmsg长度是 0 或者是 1 ，拼接 data的值为{}
		if(errmsg.length==1 || errmsg.length==0 ){
			result.append("{}");
		}
		if(errmsg.length==2){
			if(!errmsg[1].equals("")){
				result.append(errmsg[1]);
			}else{
				result.append("{}");
			}
		}
		result.append(",\"errmsg\":\""+errmsgstr+"\"}");
		return result.toString();
	}

}
