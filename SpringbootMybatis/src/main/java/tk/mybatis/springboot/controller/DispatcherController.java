package tk.mybatis.springboot.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import tk.mybatis.springboot.conf.InitializeBean;
import tk.mybatis.springboot.service.IComponentsService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@RestController
@RequestMapping("/app")
public class DispatcherController {
	Logger logger = Logger.getLogger(DispatcherController.class);
	/**
     * 分发请求
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/server")//, method = {RequestMethod.POST}
    public JSONObject dispatcher(HttpServletRequest request, HttpServletResponse response) {
//    	response.setContentType("text/xml;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");//'Access-Control-Allow-Origin:*'
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        JSONObject backJson = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            String pkt = IOUtils.toString(inputStream);
            //String pkt = IOUtils.toString(inputStream,"UTF-8");
            inputStream.close();
            if (pkt == null) {
                return null;
            }
            JSONObject jsonDataObj = null; 
            try {
            	jsonDataObj = JSON.parseObject(pkt); 
			} catch (Exception e) {
				System.out.println(e);
			}             
            JSONArray reqJsonArray = (JSONArray) jsonDataObj.get("REQ");
            JSONArray resJsonArray = new JSONArray();
            for (int i = 0; i < reqJsonArray.size(); i++) {
                JSONObject reqJson = reqJsonArray.getJSONObject(i);
                System.out.println("rreq:"+reqJson);
                String cmd = String.valueOf(reqJson.get("CMD"));
                System.out.println(cmd);
                String beanName = "";
                try {
                	beanName = getServiceBeanByCmd(cmd);
                    System.out.println(beanName);
    			} catch (Exception e) {
					// TODO: handle exception
					System.out.println("ex:"+e);
				} 
                IComponentsService serviceBean = (IComponentsService) InitializeBean.getBean(beanName);
                backJson = serviceBean.execute(reqJson, null);
                //resJsonArray.add(backJson);
                System.out.println("BACKJSON:"+backJson);
	               	
            }
            response.setHeader("content-type", "text/html;charset=UTF-8");
        } catch (Exception e) {
        }
		return backJson;
    }
    /**
     * 根据指令id获取业务处理service类
     *
     * @param cmd
     * @return
     */
    public static String getServiceBeanByCmd(String cmd) {
        String beanName = "";
        switch (Integer.parseInt(cmd)) {
            case 101://计划指令
                beanName = "TaskPlanService";
                break;
            case 102://公共指令
                beanName = "MonitorService";
                break;
            case 103://对比指令
                beanName = "ComparisonService";
                break;
            case 104://测试往redis中添加终端机信息
                beanName = "AddTmnInRedisService";
                break;
            default:
                break;
        }
        return beanName;
    }
}
