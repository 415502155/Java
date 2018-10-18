/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.redis.terminal;

import com.bestinfo.bean.terminal.TmnCmdDetail;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

/**
 * 终端命令
 *
 * @author YangRong
 */
@Repository
public class TerminalCommandRedis {

    private static final Logger logger = Logger.getLogger(TerminalCommandRedis.class);
    @Resource
    private RedisDaoImpl redisDaoImpl;

    public synchronized TmnCmdDetail listCurTmnCmd(int terminalId) {
        return getTmnCmd(terminalId);
    }

    public synchronized TmnCmdDetail listTmnCmd(int terminalId, String cmdId) {

        TmnCmdDetail tmnCmdDetail = getTmnCmd(terminalId);
        if (cmdId == null || "".equals(cmdId)) {
            return tmnCmdDetail;
        }
        if (tmnCmdDetail != null && cmdId.equals(tmnCmdDetail.getCommandId())) {
            return tmnCmdDetail;
        }
        return getTmnHistCmd(terminalId, cmdId);
    }

    public synchronized int cancelCurTmnCmd(int terminalId) {
        TmnCmdDetail tmnCmdDetail = getTmnCmd(terminalId);
        if (tmnCmdDetail == null) {
            logger.error("stop fail,tmdCmdDetail is null,terminalId=" + terminalId);
            return -1;
        }

        if (tmnCmdDetail.getWorkingStaus() == 0) {
            logger.error("stop fail,tmn working status is already 0");
            return -2;
        }
        tmnCmdDetail.setWorkingStaus(0);
        Date stopTime = new Date();
        tmnCmdDetail.setStopTime(stopTime);
        int retn = setTmnCmd(tmnCmdDetail);
        if (retn != 0) {
            logger.error("redis set fail");
            return -3;
        }
        retn = setTmnHistCmd(tmnCmdDetail);
        if (retn != 0) {
            logger.error("history redis set fail");
            return  -4;
        }
        return 0;
    }

    public synchronized int cancelTmnCmd(int terminalId, String cmdId, String responseMsg) {
        TmnCmdDetail tmnCmdDetail = getTmnCmd(terminalId);
        if (tmnCmdDetail == null) {
            logger.error("stop fail,tmdCmdDetail is null,terminalId=" + terminalId + ",cmdId=" + cmdId);
            return -1;
        }
        if (tmnCmdDetail.getTerminalId() != terminalId || !tmnCmdDetail.getCommandId().equals(cmdId)) {
            logger.error("parameter is not equil,terminalId=" + terminalId + ",cmdId=" + cmdId + ",tmnCmdDetail=" + tmnCmdDetail);
            return -2;
        }
        if (tmnCmdDetail.getWorkingStaus() == 0) {
            logger.error("cmd working status is 0,stop fail ");
            return -3;
        }
        tmnCmdDetail.setWorkingStaus(0);
        Date stopTime = new Date();
        tmnCmdDetail.setStopTime(stopTime);
        if (responseMsg != null || !"".equals(responseMsg)) {
            tmnCmdDetail.setResponseMsg(responseMsg);
        }
        int retn = setTmnCmd(tmnCmdDetail);
        if (retn != 0) {
            return -4;
        }
        retn = setTmnHistCmd(tmnCmdDetail);
        if (retn != 0) {
            return -5;
        }
        return 0;
    }

    public synchronized String execTmnCmd(int terminalId, String command) {
        TmnCmdDetail tmnCurCmdDetail = getTmnCmd(terminalId);
        if (tmnCurCmdDetail != null && tmnCurCmdDetail.getWorkingStaus() != 0) {
            logger.error("start terminal command fail,command is running,termianlId=" + terminalId + ",tmnCmdDetail=" + tmnCurCmdDetail);
            return "-1";
        }
        if (command == null || "".equals(command)) {
            logger.error("command is null");
            return "-2";
        }
        TmnCmdDetail tmnCmdDetail = new TmnCmdDetail();
        tmnCmdDetail.setCommand(command);
        Date startTime = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String cmdId = df.format(startTime);
        Random rand = new Random();
        cmdId = cmdId + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10)) + String.valueOf(rand.nextInt(10));
        tmnCmdDetail.setCommandId(cmdId);
        tmnCmdDetail.setStartTime(startTime);
        tmnCmdDetail.setTerminalId(terminalId);
        tmnCmdDetail.setValidTime(0);
        tmnCmdDetail.setWorkingStaus(1);
        int retn = setTmnCmd(tmnCmdDetail);
        if (retn != 0) {
            return "-3";
        }
        return cmdId;

    }

    public TmnCmdDetail getTmnCmd(int terminalId) {
        try {
            String tmnCmdKey = RedisKeysUtil.getTmnCmdKey(terminalId);
            String tmnCmd = redisDaoImpl.redisLoad(tmnCmdKey);
            if (tmnCmd == null || "".equals(tmnCmd) || tmnCmd.isEmpty()) {
                logger.info("there is no key " + tmnCmdKey + " in redis");
                return null;
            }
            TmnCmdDetail tmnCmdDetail = new ObjectMapper().readValue(tmnCmd, TmnCmdDetail.class);
            return tmnCmdDetail;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    public TmnCmdDetail getTmnHistCmd(int terminalId, String cmsId) {
        try {
            String tmnHistCmdKey = RedisKeysUtil.getTmnHistoryCmdKey(terminalId, cmsId);
            String tmnHistCmd = redisDaoImpl.redisLoad(tmnHistCmdKey);
            if (tmnHistCmd == null || "".equals(tmnHistCmd) || tmnHistCmd.isEmpty()) {
                logger.error("there is no key " + tmnHistCmdKey + " in redis");
                return null;
            }
            TmnCmdDetail tmnCmdDetail = new ObjectMapper().readValue(tmnHistCmd, TmnCmdDetail.class);
            return tmnCmdDetail;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    private int setTmnCmd(TmnCmdDetail tmnCmdDetail) {
        String tmnCmdkey = RedisKeysUtil.getTmnCmdKey(tmnCmdDetail.getTerminalId());
        try {
            boolean re = redisDaoImpl.redisSet(tmnCmdkey, new ObjectMapper().writeValueAsString(tmnCmdDetail));
            if (!re) {
                logger.error("insert OpenprizeInfo into redis error,key:" + tmnCmdkey
                        + ",jsonObject:" + new ObjectMapper().writeValueAsString(tmnCmdDetail));
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    private int setTmnHistCmd(TmnCmdDetail tmnCmdDetail) {
        String tmnHistCmdkey = RedisKeysUtil.getTmnHistoryCmdKey(tmnCmdDetail.getTerminalId(), tmnCmdDetail.getCommandId());
        try {
            boolean re = redisDaoImpl.redisSet(tmnHistCmdkey, new ObjectMapper().writeValueAsString(tmnCmdDetail));
            if (!re) {
                logger.error("insert OpenprizeInfo into redis error,key:" + tmnHistCmdkey
                        + ",jsonObject:" + new ObjectMapper().writeValueAsString(tmnCmdDetail));
                return -3;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -4;
        }

    }

}
