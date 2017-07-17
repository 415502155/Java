package com.bestinfo.controller.fsMon;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.fs.ETLServerInfo;
import com.bestinfo.bean.fs.MonServerInfo;
import com.bestinfo.bean.fs.MonServerStatusInPara;
import com.bestinfo.bean.fs.MonServerStatusOutPara;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.riskControl.AllRiskNoWithPlayId;
import com.bestinfo.bean.session.SessionInfo;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.dao.terminal.ITerminalRegisterDao;
import com.bestinfo.redis.login.TerminalLoginRedis;
import com.bestinfo.service.fsMonitor.IFsMonitorService;
import com.bestinfo.util.TimeUtil;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import org.apache.commons.beanutils.BeanComparator;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 文件系统监控
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/fsMon")
public class FsMonController {

    private static final Logger logger = Logger.getLogger(FsMonController.class);

    @Resource
    private IFsMonitorService fsMonitorService;
    @Resource
    private TerminalLoginRedis terminalLoginRedis;
    @Resource
    private ITerminalRegisterDao termDao;//终端注册DAO
    @Resource
    private JdbcTemplate metaJdbcTemplate;
    @Resource
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private IGameInfoDao gameInfoDao;
    @Resource
    private IGameDrawInfoDao drawInfoDao;

    public class ListSorter {

        public <V> void sort(List<V> list, final String... properties) {
            Collections.sort(list, new Comparator<V>() {
                public int compare(V o1, V o2) {
                    if (o1 == null && o2 == null) {
                        return 0;
                    }
                    if (o1 == null) {
                        return -1;
                    }
                    if (o2 == null) {
                        return 1;
                    }

                    for (String property : properties) {
                        Comparator c = new BeanComparator(property);
                        int result = c.compare(o1, o2);
                        if (result != 0) {
                            return result;
                        }
                    }
                    return 0;
                }
            });
        }
    }

    public class JsonDateValueProcessor implements JsonValueProcessor {

        private String format = "yyyy-MM-dd HH:mm:ss";

        public Object processArrayValue(Object value, JsonConfig config) {
            return process(value);
        }

        public Object processObjectValue(String key, Object value, JsonConfig config) {
            return process(value);
        }

        private Object process(Object value) {
            if (value instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(value);
            }
            return "";
        }
    }

//    @RequestMapping(value = "/getRiskNoList")
//    public @ResponseBody
//    String getRiskNoList(HttpServletRequest request, int OrderRule, int gameId, int NumBegin, int NumEnd, int drawId, int kdrawId, String serverIp, int monitorPort) {
//        if (OrderRule < 0 || NumBegin < 0 || NumEnd < 0 || NumBegin > NumEnd || gameId < 0 || drawId < 0 || kdrawId < 0 || serverIp == null || serverIp.isEmpty() || monitorPort < 0) {
//            logger.error("get risk no list input parameter is not correcct");
//            return "";
//        }
//
//        List<AllRiskNoWithPlayId> lesi = fsMonitorService.getRiskNoList(gameId, drawId, kdrawId, serverIp, monitorPort);
//        if (lesi == null || lesi.isEmpty()) {
//            logger.error("get risk no list fail");
//            return "";
//        } else {
//
//            if (OrderRule == 0) {
//                new ListSorter().sort(lesi,"playId","riskNo" );
//            } else if (OrderRule == 1) {
//                new ListSorter().sort(lesi, "playId","soldStake", "riskNo");
//                Collections.reverse(lesi);
//            }
//
//            if (NumBegin < lesi.size()) {
//                logger.info("get risk no list status success");
//
//                JSONArray jsa = JSONArray.fromObject(lesi.subList(NumBegin, Math.min(NumEnd, lesi.size())));
//                return jsa.toString();
//            } else {
//                logger.error("list size :" + lesi.size() + " NumBegin:" + NumBegin);
//                return "";
//            }
//
//        }
//    }
    @RequestMapping(value = "/getRiskNoList")
    public @ResponseBody
    String getRiskNoList(HttpServletRequest request, int OrderRule, int playId, int gameId, int NumBegin, int NumEnd, int drawId, int kdrawId, String serverIp, int monitorPort) {
        if (playId < 0 || OrderRule < 0 || NumBegin < 0 || NumEnd < 0 || NumBegin > NumEnd || gameId < 0 || drawId < 0 || kdrawId < 0 || serverIp == null || serverIp.isEmpty() || monitorPort < 0) {
            logger.error("get risk no list input parameter is not correcct");
            return "";
        }

        List<AllRiskNoWithPlayId> lesi = fsMonitorService.getRiskNoListByPlayId(playId, gameId, drawId, kdrawId, serverIp, monitorPort);
        if (lesi == null || lesi.isEmpty()) {
            logger.error("get risk no list fail");
            return "";
        } else {

            if (OrderRule == 0) {
                new ListSorter().sort(lesi, "riskNo");
            } else if (OrderRule == 1) {
                new ListSorter().sort(lesi, "soldStake");
                Collections.reverse(lesi);
            }

            if (NumBegin < lesi.size()) {
                logger.info("get risk no list status success");

                JSONArray jsa = JSONArray.fromObject(lesi.subList(NumBegin, Math.min(NumEnd + 1, lesi.size())));
                return jsa.toString();
            } else {
                logger.error("list size :" + lesi.size() + " NumBegin:" + NumBegin);
                return "";
            }

        }
    }

    @RequestMapping(value = "/getAliveTerminalNum")
    public @ResponseBody
    int getAliveTerminalNum(HttpServletRequest request) {
        List<TmnInfo> termList = termDao.getRegTmnIdList(termJdbcTemplate);
//        logger.info("terminal size:"+termList.size());
        int aliveNum = 0;
        Date now = new Date();
        for (TmnInfo ti : termList) {
            SessionInfo si = terminalLoginRedis.getSessionInfo(ti.getTerminal_id().toString());
            if (si != null && si.getLoginTime() != null && si.getLoginOutTime() == null && si.getTransTime() != null) {
//                logger.info("session info:"+si.getLogin_terminal()+" login time:"+si.getLoginTime()+" login out time:"+si.getLoginOutTime());
                if (TimeUtil.theSameDay(now, si.getTransTime())) {
                    aliveNum++;
                }
            }
        }
        return aliveNum;

    }

    @RequestMapping(value = "/getSessionInfo")
    public @ResponseBody
    String getSessionInfoList(HttpServletRequest request, int beginTerminalId, int endTerminalId) {
        if (beginTerminalId <= 0 || endTerminalId <= 0) {
            return "";
        }
        List<SessionInfo> silist = terminalLoginRedis.getSessionInfoListById(beginTerminalId, endTerminalId);
        if (silist == null || silist.isEmpty()) {
            logger.error("get session info list fail ,beginTerminalId:" + beginTerminalId + " endTerminalId:" + endTerminalId);
            return "";
        } else {
            logger.info("get session info list success");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jsonConfig.registerJsonValueProcessor(byte[].class, new JsonDateValueProcessor());
            JSONArray jsa = JSONArray.fromObject(silist, jsonConfig);
            return jsa.toString();
        }
    }

    @RequestMapping(value = "/startService", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> startService(HttpServletRequest request, String processName, int serverType, int gameId, int drawId, int kdrawId,
            int monitorPort, String serverIp, int serverPort) {
        logger.info("start server    servertype=" + serverType + " gameId=" + gameId + " drawId=" + drawId + " kdrawId=" + kdrawId
                + " monitorPort=" + monitorPort + " serverIp=" + serverIp + " serverPort=" + serverPort + " processName:" + processName);
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (gameId < 0 || drawId < 0 || kdrawId < 0 || monitorPort < 0 || serverPort < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int retn = fsMonitorService.startServer(processName, serverType, gameId, drawId, kdrawId, serverPort, serverIp, monitorPort);
        if (retn == 0) {
            logger.info("start fs server success");
            resMap.put("code", 0);
            resMap.put("msg", "启动服务成功");
            return resMap;
        } else {
            logger.error("start fs server fail");
            resMap.put("code", retn);
            resMap.put("msg", "启动服务失败");
            return resMap;
        }

    }

    @RequestMapping(value = "/restartETLService", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> restartETLService(HttpServletRequest request, String processName, int serverType, int gameId, int drawId, int kdrawId,
            int monitorPort, String serverIp, int serverPort) {
        logger.info("restart ETL server    servertype=" + serverType + " gameId=" + gameId + " drawId=" + drawId + " kdrawId=" + kdrawId
                + " monitorPort=" + monitorPort + " serverIp=" + serverIp + " serverPort=" + serverPort + " processName:" + processName);
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (gameId < 0 || drawId < 0 || kdrawId < 0 || monitorPort < 0 || serverPort < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int retn = fsMonitorService.restartETLService(processName, serverType, gameId, drawId, kdrawId, serverPort, serverIp, monitorPort);
        if (retn == 0) {
            logger.info("restart etl server success");
            resMap.put("code", 0);
            resMap.put("msg", "启动ETL服务成功");
            return resMap;
        } else {
            logger.error("restart etl server fail");
            resMap.put("code", retn);
            resMap.put("msg", "启动ETL服务失败");
            return resMap;
        }

    }

    @RequestMapping(value = "/stopService", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> stopService(HttpServletRequest request, String processName, int serverType, int gameId, int drawId, int kdrawId,
            int monitorPort, String serverIp, int serverPort) {
        logger.info("stop server    servertype=" + serverType + " gameId=" + gameId + " drawId=" + drawId + " kdrawId=" + kdrawId
                + " monitorPort=" + monitorPort + " serverIp=" + serverIp + " serverPort=" + serverPort + " processName:" + processName);
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (gameId < 0 || drawId < 0 || kdrawId < 0 || monitorPort < 0 || serverPort < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int retn = fsMonitorService.stopServer(processName, serverType, gameId, drawId, kdrawId, serverPort, serverIp, monitorPort);
        if (retn == 0) {
            logger.info("stop fs server success");
            resMap.put("code", 0);
            resMap.put("msg", "停止服务成功");
            return resMap;
        } else {
            logger.error("stop fs server fail,server type:" + serverType);
            resMap.put("code", retn);
            resMap.put("msg", "停止服务失败");
            return resMap;
        }

    }

    @RequestMapping(value = "/getMonServiceConfig", method = RequestMethod.POST)
    public @ResponseBody
    String getMonServiceConfig(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<MonServerInfo> lmsi = fsMonitorService.getMonServerInfo();
        if (lmsi == null) {
            logger.error("get fs server config list fail");
            return "";
        } else {
            logger.info("get fs server config list success");
            JSONArray jsa = JSONArray.fromObject(lmsi);
            return jsa.toString();
        }
    }

    @RequestMapping(value = "/getETLServerStatusList")
    public @ResponseBody
    String getETLServerStatusList(HttpServletRequest request, String processName, String serverIp, int monitorPort) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (processName == null || processName.isEmpty() || serverIp == null || serverIp.isEmpty() || monitorPort < 0) {
            logger.error("get etl status list input parameter is not correcct");
            return "";
        }
        List<ETLServerInfo> lesi = fsMonitorService.getETLServerList(processName, serverIp, monitorPort);
        if (lesi == null || lesi.isEmpty()) {
            logger.error("get ETL server status fail");
            return "";
        } else {
            logger.info("get ETL server status success");
            JSONArray jsa = JSONArray.fromObject(lesi);
            return jsa.toString();
        }
    }

    /**
     * added by yfyang 20161109，电话投注改造<br>
     * 抽取服务监控，查询etl进程是否正常以及etl抽取票数量<br>
     * 分快开游戏和普通游戏<br>
     *
     * @param request
     * @param processName
     * @param serverIp
     * @param monitorPort
     * @param isKeno
     * @param gameCode
     * @return
     */
    @RequestMapping(value = "/getETLServerStatusListNew")
    @ResponseBody
    public String getETLServerStatusList(HttpServletRequest request, String processName, String serverIp, int monitorPort,
            String isKeno, String gameCode) {
        logger.info("get etl server status list,isKeno:" + isKeno + ",gameCode:" + gameCode);
        if (processName == null || processName.isEmpty() || serverIp == null || serverIp.isEmpty() || monitorPort < 0) {
            logger.error("eb data error");
            return "";
        }
        int drawId = 0;
        int gameId = 0;
        if ("1".equals(isKeno)) {//快开游戏
            GameInfo gameInfo = gameInfoDao.getGameInfoByGameCode(metaJdbcTemplate, gameCode);
            if (gameInfo == null) {
                logger.error("GameInfo is null,game_code:" + gameCode);
                return "";
            }
            gameId = gameInfo.getGame_id();

            GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, TimeUtil.formatDate_YMD8(new Date()));
            if (drawInfo == null) {
                logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_id:" + drawId);
                return "";
            }
            drawId = drawInfo.getDraw_id();
        }

        List<ETLServerInfo> lesi = fsMonitorService.getETLServerList(processName, serverIp, monitorPort, gameId, drawId);
        if (lesi == null || lesi.isEmpty()) {
            logger.error("get ETL server status fail");
            return "";
        } else {
            logger.info("get ETL server status success");
            JSONArray jsa = JSONArray.fromObject(lesi);
            return jsa.toString();
        }
    }

    @RequestMapping(value = "/getServerStatus", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getServerStatus(HttpServletRequest request, String processName, int serverType, int gameId, int drawId, int kdrawId,
            int monitorPort, String serverIp, int serverPort) {
        logger.info("query server sataus  servertype=" + serverType + " gameId=" + gameId + " drawId=" + drawId + " kdrawId=" + kdrawId
                + " monitorPort=" + monitorPort + " serverIp=" + serverIp + " serverPort=" + serverPort + " processName:" + processName);
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (gameId < 0 || drawId < 0 || kdrawId < 0 || monitorPort < 0 || serverPort < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int retn = fsMonitorService.getServerStatus(processName, serverType, gameId, drawId, kdrawId, serverPort, serverIp, monitorPort);
        if (retn == 0) {
            logger.info("get fs status success");
            resMap.put("code", 0);
            resMap.put("msg", "服务正常");
            return resMap;
        } else {
            logger.error("get fs status fail");
            resMap.put("code", retn);
            resMap.put("msg", "服务异常");
            return resMap;
        }

    }

    @RequestMapping(value = "/getServerStatusList")
    public @ResponseBody
    String getServerStatusList(HttpServletRequest request, String jsonItems) {
//        jsonItems="[{\"serverType\":2,\"serverId\":1,\"serverIp\":\"192.168.0.204\",\"serverPort\":8821,\"monitorPort\":8800,\"processName\":\"ltsraff\"},{\"serverType\":4,\"serverId\":2,\"serverIp\":\"192.168.0.204\",\"serverPort\":8823,\"monitorPort\":8800,\"processName\":\"ltsraff\"}]";
        JSONArray jsa = JSONArray.fromObject(jsonItems);
        List<MonServerStatusInPara> lmssip = (List<MonServerStatusInPara>) JSONArray.toCollection(jsa, MonServerStatusInPara.class);
        if (lmssip.isEmpty()) {
            logger.error("input para is null");
            return "";
        }
        List<MonServerStatusOutPara> lmssop = new ArrayList<MonServerStatusOutPara>();
        for (MonServerStatusInPara mssip : lmssip) {
            int retn = fsMonitorService.getServerStatus(mssip.getProcessName(), mssip.getServerType(), 0, 0, 0, mssip.getServerPort(), mssip.getServerIp(), mssip.getMonitorPort());
            MonServerStatusOutPara mssop = new MonServerStatusOutPara();
            mssop.setServerId(mssip.getServerId());
            mssop.setStatus(retn);
            lmssop.add(mssop);
        }
        if (lmssop.size() > 0) {
            logger.info("get  server status list success");
            JSONArray ja = JSONArray.fromObject(lmssop);
            return ja.toString();
        } else {
            return "";
        }

    }

}
