/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import com.bestinfo.service.h5.inter.IComponentsService;
import com.bestinfo.util.TimeUtil;
import static com.bestinfo.util.h5.monitor.getFourRandom.getFourRandom;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
/***
 * 添加终端登录信息到redis
 * @author Administrator
 */
@Service("AddTmnInRedisService")
public class AddTmnInRedisService implements IComponentsService {

    @Resource
    private RedisDaoImpl redisDaoImpl;
    private static final Logger logger = Logger.getLogger(AddTmnInRedisService.class);

    @Override
    public JSONObject execute(JSONObject json, String ip) {
        String terminalId = "";
        Date date = new Date();
        JSONObject backJson = new JSONObject();
        String[] city = {"00", "01", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
        for (int i = 0; i < city.length; i++) {
            for (int j = 0; j < 300; j++) {
                String randomFour = getFourRandom();
                terminalId = "44" + city[i] + randomFour;
                try {
                    String day = TimeUtil.formatDate_YMD8(date);
                    String tmnKey = RedisKeysUtil.getTerminalLoginKey(day);
                    String value = TimeUtil.formatDate_time(date);
                    logger.info("save terminal login info to redis,key---" + tmnKey + ",terminalId---" + terminalId + ",time---" + value);
                    boolean re = redisDaoImpl.redisHashSet(tmnKey, terminalId, value);
                    if (!re) {
                        logger.error("save terminal login info to redis error");
                    }

                    String cityTmnKey = RedisKeysUtil.getCityTerminalLoginKey(day, terminalId.substring(2, 4));
                    logger.info("save city terminal login info to redis,key---" + cityTmnKey + ",terminalId---" + terminalId + ",time---" + value);
                    re = redisDaoImpl.redisHashSet(cityTmnKey, terminalId, value);
                    if (!re) {
                        logger.error("save city terminal login info to redis error");
                    }
                } catch (Exception ex) {
                    logger.error("", ex);
                }
            }
        }
        backJson.put("CODE", 0);
        backJson.put("MSG", "SUCCESS");
        logger.info("backJson:" + backJson);
        return backJson;
    }
}
