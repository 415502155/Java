package com.shy.springboot.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.service.ICommonService;
import com.shy.springboot.thread.InitializeBean;
import com.shy.springboot.utils.ReturnResult;

import lombok.extern.slf4j.Slf4j;
/***
 * @deprecated 根據參數分發請求
 * @author sjwy-0001
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/server")
public class DispatcherController {
	
	@RequestMapping(value= "/api")
	@ResponseBody
	public ReturnResult dispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream inputStream = request.getInputStream();
        String pkt = IOUtils.toString(inputStream);
        log.info("請求參數為 》》》》》》》》》》》》》 ：" + pkt);
        inputStream.close();
        if (pkt == null) {
            return ReturnResult.error("400", "請求參數不能為空；");
        }
        JSONObject reqParam = null; 
        try {
        	reqParam = JSON.parseObject(pkt); 
        	boolean checkParams = checkParams(reqParam);
        	if (!checkParams) {
        		return ReturnResult.error("400", "請求參數不合法；");
        	} 
        	Integer code = (Integer) reqParam.get("cmd");
        	String serviceName = getServiceNameByCmd(code);
        	log.info("根據cmd獲取serviceName 》》》》》》》》》》》》》 ：" + serviceName);
        	if (StringUtils.isBlank(serviceName)) {
        		return ReturnResult.error("400", "無法根據code獲取指定的service；");
        	}
        	ICommonService ICommonService = (com.shy.springboot.service.ICommonService) InitializeBean.getBean(serviceName);
        	ReturnResult result = ICommonService.execute(reqParam);
        	return result;
        } catch (Exception e) {
			log.info("JSON 轉換異常 ：" + e);
			e.printStackTrace();
			return ReturnResult.error("400", "數據服務異常；");
		}  

	}
	/***
	 * @deprecated 校驗請求參數是否合法
	 * @param params
	 * @return
	 */
	public boolean checkParams(JSONObject params) {
		boolean flag = true;
		try {
			Integer cmd = params.getInteger("cmd");
			Integer type = params.getInteger("type");
			if (cmd == null || type == null) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("checkParams ex 校驗請求參數是否合法 :" + e);
			flag = false;
			return flag;
		}
		return flag;
	} 
	
	/***
	 * @deprecated 根據cmd獲取serviceBean
	 * @param cmd
	 * @return
	 */
	public String getServiceNameByCmd(int cmd) {
		String serviceName = "";
		switch (cmd) {
		case 101:
			serviceName = "userService";
			break;
		case 102:
			serviceName = "accountService";
			break;
		case 103:
			serviceName = "loggerService";
			break;
		default:
			break;
		}
		return serviceName;
	}
}
