package com.bestinfo.controller.sync.d2code;

import com.bestinfo.redis.d2code.GameD2codeRedis;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 二维码同步
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/synehcache")
public class D2codeSyncController {
    private static final Logger logger = Logger.getLogger(D2codeSyncController.class);
    
    @Resource
    private GameD2codeRedis gameD2codeRedis;
    
    /**
     * 同步游戏外加信息
     * @return 
     */
    @RequestMapping(value = "/initGameAddInfo")
    @ResponseBody
    public Map<String, Object> syncGameAddInfo(){
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = gameD2codeRedis.initGameAddInfo();
        if(retn < 0){
            logger.error("sync gameAddInfo error");
            resMap.put("code", -1);
            resMap.put("msg", "同步游戏外加信息失败！");
        }else{
            logger.info("sync gameAddInfo success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步游戏外加信息成功！");
        }
        return resMap;
    }
    
    /**
     * 同步二维码编码信息
     * @return 
     */
    @RequestMapping(value = "/initD2codeInfo")
    @ResponseBody
    public Map<String,Object> syncD2codeInfo(){
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = gameD2codeRedis.initD2codeInfo();
        if(retn < 0){
            logger.error("sync d2codeInfo error");
            resMap.put("code", -1);
            resMap.put("msg", "同步二维码编码信息失败！");
        }else{
            logger.info("sync d2codeInfo success");
            resMap.put("code", 0);
            resMap.put("msg", "同步二维码编码信息成功！");
        }
        return resMap;
    }
}
