package com.bestinfo.controller.city;

import com.bestinfo.bean.encoding.CityInfo;
import com.bestinfo.service.city.ICityInfoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 二级城市编号
 * @author zyk
 */
@Controller
@RequestMapping(value = "/ebCityInfo")
public class EBCityInfoController {
    private final Logger logger = Logger.getLogger(EBCityInfoController.class);
    
    @Resource
    private ICityInfoService cityInfoService;
    
    /**
     * 根据区县id修改 中心地址、联系电话、终端维护口令
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/updateCity", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyTmnPrivilege(HttpServletRequest request){
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            String cityInfoStr = request.getParameter("CityInfoStr");
            logger.info("CityInfoStr: " + cityInfoStr + "\n");
            JSONArray cityInfoJson = JSONArray.fromObject(cityInfoStr);
            List<CityInfo> ciList = new ArrayList<CityInfo>();
            int cityInfoJsonSize = cityInfoJson.size();
            for (int i = 0; i < cityInfoJsonSize; i++) {
                JSONObject obj = cityInfoJson.getJSONObject(i);
                CityInfo ci = new CityInfo();
                ci.setCity_id((Integer)obj.get("city_id")); 				//Integer.valueOf(obj.getInt("city_id"))
                ci.setCity_address(obj.get("city_address").toString());		//obj.getString("city_address")
                ci.setCity_phone(obj.get("city_phone").toString());			//obj.getString("city_phone")
                String pwd = obj.get("terminal_password").toString().trim();//obj.getString("terminal_password")
                if(pwd.length() != 6){
                    logger.error("the terminal_password length not's 6");
                    resMap.put("code", "-4");
                    resMap.put("msg", "操作 失败！");
                    return resMap;
                }
                ci.setTerminal_password(pwd);
                ciList.add(ci);
            }
            if (ciList.isEmpty()) {
                logger.error("the CityInfoList is null");
                resMap.put("code", "-1");
                resMap.put("msg", "操作 失败！");
                return resMap;
            }
            int tag = cityInfoService.modifyCityInfo(ciList);
            logger.info("update city info list from DB and cache re:" + tag);
            if (tag < 0) {
                resMap.put("code", "-2");
                resMap.put("msg", "操作 失败！");
            } else {
                resMap.put("code", "0");
                resMap.put("msg", "操作 成功！");
            }
        }catch(Exception e){
            logger.error("ebCityInfo updateCity error: ",e);
            resMap.put("code", "-3");
            resMap.put("msg", "操作 失败！");
        }
        return resMap;
    }
}
