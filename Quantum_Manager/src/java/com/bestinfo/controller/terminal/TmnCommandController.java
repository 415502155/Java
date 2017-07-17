/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.controller.terminal;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.terminal.TmnCmdDetail;
import com.bestinfo.redis.business.TmnInfoRedisCache;
import com.bestinfo.redis.terminal.TerminalCommandRedis;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 往指定终端发命令串
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/tmnCmd")
public class TmnCommandController {

    private static final Logger logger = Logger.getLogger(TmnCommandController.class);
    @Resource
    private TmnInfoRedisCache terminalInfoRedis;

    @Resource
    private TerminalCommandRedis terminalCmdRedis;

    public String[] getTokens(String input) {
        int i = 0;
        StringTokenizer st = new StringTokenizer(input);
        int numTokens = st.countTokens();
        String[] tokenList = new String[numTokens];
        while (st.hasMoreTokens()) {
            tokenList[i] = st.nextToken();
            i++;
        }
        return (tokenList);

    }

//    private String upload(String[] args) {
//        try {
//            if (args == null || args.length < 1 || args[0] == null) {
//                return "命令无效";
//            }
//            CommandLineParser parser =  new DefaultParser();
//             Options options = new Options();
//        options.addOption("t", "terminal", true, "Terminal Id");
//        options.addOption("f", "file", true, "Terminal File to Upload");
//        // Parse the program arguments  
//         
//        CommandLine commandLine = parser.parse(options,args );
//        // Set the appropriate variables based on supplied options  
//        if(!commandLine.hasOption("t") || !commandLine.hasOption("f")){
//            return "命令行缺少必要的输入参数";
//        }
//        int terminalId = Integer.parseInt(commandLine.getOptionValue("t"));
//        String fileName = commandLine.getOptionValue('f');
//        return null;
//        } catch (Exception ex) {
//            return "命令解析异常";
//        }
//
//    }
    //终端支持的命令名称
    private static List<String> commandNameArray = new ArrayList<String>(asList("upload"));

    @RequestMapping(value = "/listCmd")
    @ResponseBody
    public String listCmd(HttpServletRequest request, String cmdId, Integer tmnId) {
        try {
            logger.info("list command,cmdId:" + cmdId + ",tmnId:" + tmnId);

            TmnCmdDetail tmnCmd = terminalCmdRedis.listTmnCmd(tmnId, cmdId);
            if (tmnCmd == null) {
                logger.error("command is null");
                return "";
            }
            String cmd = new ObjectMapper().writeValueAsString(tmnCmd);
            logger.info("cmd:" + cmd);
            return cmd;

        } catch (Exception ex) {
            logger.error("", ex);
            return "";
        }
    }

    @RequestMapping(value = "/listCurCmd")
    @ResponseBody
    public String listCmd(HttpServletRequest request, Integer tmnId) {
        try {
            logger.info("list current command,tmnId:" + tmnId);
            TmnCmdDetail tmnCmd = terminalCmdRedis.listCurTmnCmd(tmnId);
            if (tmnCmd == null) {
                logger.error("command is null");
                return "";
            }
            return new ObjectMapper().writeValueAsString(tmnCmd);

        } catch (Exception ex) {
            logger.error("", ex);
            return "";
        }
    }

    @RequestMapping(value = "/stopCmd")
    @ResponseBody
    public Map<String, Object> stopCmd(HttpServletRequest request, String cmdId, Integer tmnId) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        logger.info("stop tmn cmd,termId:" + tmnId + ",cmdId:" + cmdId);

        TmnCmdDetail tmnCmd = terminalCmdRedis.listTmnCmd(tmnId, cmdId);
        if (tmnCmd == null) {
            logger.error("command is null");
            resMap.put("code", -4);
            resMap.put("msg", "无此命令");
            return resMap;
        }
        if(tmnCmd.getWorkingStaus()==0){
            logger.info("command has stopped");
            resMap.put("code", 0);
            resMap.put("msg", cmdId);
            return resMap;
        }
        int retn = terminalCmdRedis.cancelTmnCmd(tmnId, cmdId, null);
        logger.info("redis return code:" + retn);
        if (retn == 0) {
            resMap.put("code", 0);
            resMap.put("msg", cmdId);
        } else if (retn == -2) {
            logger.error("cmdId not correct");
            resMap.put("code", -2);
            resMap.put("msg", "cmdId不匹配");
        } else if (retn == -3) {
            logger.error("cmd has stopped");
            resMap.put("code", 0);
            resMap.put("msg", cmdId);
        } else {
            logger.error("fail stop cmd");
            resMap.put("code", -1);
            resMap.put("msg", "停止命令失败");

        }
        return resMap;
    }

    @RequestMapping(value = "/stopCurCmd")
    @ResponseBody
    public Map<String, Object> stopCurCmd(HttpServletRequest request, Integer tmnId) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        logger.info("stop current cmd,tmnId:" + tmnId);
        int retn = terminalCmdRedis.cancelCurTmnCmd(tmnId);
        if (retn == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "成功停止");
        } else if (retn == -2) {
            resMap.put("code", -2);
            resMap.put("msg", "命令先前已停止");
        } else {
            resMap.put("code", -1);
            resMap.put("msg", "停止命令失败");

        }
        logger.info("return code:" + retn);
        return resMap;
    }

    @RequestMapping(value = "/startCmd")
    @ResponseBody
    public Map<String, Object> startCmd(HttpServletRequest request, String cmd, Integer tmnId) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        logger.info("start tmn cmd,tmnId:" + tmnId + ",cmd:" + cmd);
        try {

            if (cmd == null || cmd.isEmpty()) {
                resMap.put("code", -2);
                resMap.put("msg", "命令为空");
                return resMap;
            }
            String[] cmdArgs = getTokens(cmd);
            if (!commandNameArray.contains(cmdArgs[0])) {
                logger.info("command name is not correct");
                resMap.put("code", -4);
                resMap.put("msg", "系统不支持此命令");
                return resMap;
            }

            TmnInfo tmnInfo = terminalInfoRedis.getTmnInfoById(tmnId);
            if (tmnInfo == null) {
                logger.info("tmn info is null");
                resMap.put("code", -5);
                resMap.put("msg", "redis没有此终端信息");
                return resMap;
            }

            String retn = terminalCmdRedis.execTmnCmd(tmnId, cmd);
            logger.info("redis return code:" + retn);
            if (retn.equals("-1")) {
                logger.error("tmn has current command");
                resMap.put("code", -1);
                resMap.put("msg", "终端当前有命令在执行");
                return resMap;
            }
            if (retn.equals("-2")) {
                logger.error("command is null");
                resMap.put("code", -2);
                resMap.put("msg", "命令为空");
                return resMap;
            }
            if (retn.equals("-3")) {
                logger.error("fail to write redis");
                resMap.put("code", -3);
                resMap.put("msg", "写redis失败");
                return resMap;
            }
            resMap.put("code", 0);
            resMap.put("msg", retn);
            return resMap;

        } catch (Exception ex) {
            logger.error("", ex);
            resMap.put("code", -6);
            resMap.put("msg", "处理异常");
            return resMap;
        }
    }

}
