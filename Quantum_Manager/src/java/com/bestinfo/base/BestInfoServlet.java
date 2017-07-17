package com.bestinfo.base;

import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.define.terminal.AutoDeduct;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.define.fs.SocketConfig;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.info.ManagerConfig;
import com.bestinfo.service.task.IExecTaskDrawInfoFileService;
import com.bestinfo.service.task.IExecTaskService;
import com.bestinfo.service.task.IExecTaskTmnGameFileService;
import com.bestinfo.service.task.IExecTaskTmnStartService;
import com.bestinfo.sync.quartz.SyncInfo;
import com.bestinfo.util.PropertiesUtil;
import java.io.File;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * 初始化数据servlet
 */
public class BestInfoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BestInfoServlet.class);

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
        try {
            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();

            //Ehcache 初始化
            logger.info("ehcache init start...");
            Map<String, Object> ehcacheBeans = wac.getBeansWithAnnotation(EhcacheInit.class);
            Set<String> beanNames = ehcacheBeans.keySet();
            for (String beanName : beanNames) {
                GetCacheCommon ehcacheBean = (GetCacheCommon) ehcacheBeans.get(beanName);
                //引入了电话投注后
                if (!beanName.equalsIgnoreCase("GamblerKey")) {
                    if (!beanName.equalsIgnoreCase("DealerInfoCache")
                            && !beanName.equalsIgnoreCase("DealerPrivilegeCache")) {
                        ehcacheBean.init((JdbcTemplate) wac.getBean("metaJdbcTemplate"));
                    } else {
                        ehcacheBean.init((JdbcTemplate) wac.getBean("gamblerTemplate"));
                    }
                }
            }
            logger.info("ehcache init complete...");

            logger.info("file path init start...");
            String path = wac.getServletContext().getRealPath("/WEB-INF");
            PropertiesUtil pt = new PropertiesUtil();
            String filename = new File(path + "/classes/config/filepath.properties").getAbsolutePath();
            if (pt.loadFile(filename, "UTF-8")) {
                String envirVariableName = pt.getString("envirVariableName");
                logger.info("read from filepath.properties envirVariableName: " + envirVariableName);
                if (envirVariableName != null && !"".equals(envirVariableName.trim())) {
                    FilePath.rootPath = System.getenv(envirVariableName);
                }

                FilePath.clpcenter = pt.getString("clpcenter");
                FilePath.game = pt.getString("game");
                FilePath.game2 = pt.getString("game2");
                FilePath.gambler = pt.getString("gambler");
                FilePath.rule = pt.getString("rule");
                FilePath.account = pt.getString("account");
                FilePath.win = pt.getString("win");
                FilePath.issuec = pt.getString("issuec");
                FilePath.issuet = pt.getString("issuet");
                FilePath.issue = pt.getString("issue");
                FilePath.luckyno = pt.getString("luckyno");
                FilePath.prize = pt.getString("prize");
                FilePath.drawinfo = pt.getString("drawinfo");
                FilePath.saleStat = pt.getString("saleStat");
                FilePath.prizeStat = pt.getString("prizeStat");
                FilePath.tmnclpkey = pt.getString("tmnclpkey");
                FilePath.kenoFile = pt.getString("kenoFile");
                try {
                    FilePath.TICKET_DATA = pt.getString("ticketData");//added by yfyang 20160704，存放新冠恒朋票数据
                } catch (Exception e) {
                    logger.error("get ticketData value from filepath error", e);
                }
                FilePath.soft = pt.getString("soft");
                FilePath.bank = pt.getString("bank");
                FilePath.bulletin = pt.getString("bulletin");
                FilePath.natureTmnDay = pt.getString("natureTmnDay");
                logger.info("rootPath:" + FilePath.rootPath);
                logger.info("clpcenter:" + FilePath.clpcenter);
                logger.info("game:" + FilePath.game);
                logger.info("game2:" + FilePath.game2);
                logger.info("gambler:" + FilePath.gambler);
                logger.info("rule:" + FilePath.rule);
                logger.info("account:" + FilePath.account);
                logger.info("win:" + FilePath.win);
                logger.info("issuec:" + FilePath.issuec);
                logger.info("issuet:" + FilePath.issuet);
                logger.info("issue:" + FilePath.issue);
                logger.info("luckyno:" + FilePath.luckyno);
                logger.info("prize:" + FilePath.prize);
                logger.info("drawinfo:" + FilePath.drawinfo);
                logger.info("saleStat:" + FilePath.saleStat);
                logger.info("ticketData:" + FilePath.TICKET_DATA);
                logger.info("prizeStat:" + FilePath.prizeStat);
                logger.info("soft:" + FilePath.soft);
                logger.info("bank:" + FilePath.bank);
                logger.info("bulletin:" + FilePath.bulletin);
                logger.info("natureTmnDay:" + FilePath.natureTmnDay);
                logger.info("kenoFile:" + FilePath.kenoFile);
                logger.info("file path init complete...");

                AutoDeduct.usemoney = Integer.parseInt(pt.getString("deviceMoney"));
                AutoDeduct.netmoney = Integer.parseInt(pt.getString("netMoney"));

                //查询文件系统etl服务是否结束的查询次数和间隔时间
                ManagerConfig.ETL_LOOP_COUNT = pt.getInteger("etlLoopCount");
                ManagerConfig.ETL_INTERVAL_TIME = pt.getInteger("etlIntervalTime");

                //下载文件系统文件的线程数和每次下载大小
                ManagerConfig.DOWNLOAD_THREAD_NUM = pt.getInteger("downloadThreadNum");
                ManagerConfig.DOWNLOAD_BLOCK_SIZE = pt.getInteger("downloadBlockSize");
            } else {
                logger.error("file load error");
            }

            logger.info("Socket server info init start...");
            filename = new File(path + "/classes/config/socket.properties").getAbsolutePath();
            if (pt.loadFile(filename, "UTF-8")) {
                /**
                 * 监控服务
                 */
                SocketConfig.MONITOR_SERVICE = pt.getString("monitor.pool.name");
                SocketConfig.MONITOR_HOST = pt.getString("monitor.pool.host");
                SocketConfig.MONITOR_PORT = pt.getString("monitor.pool.port");
                /**
                 * 普通游戏管理服务
                 */
                SocketConfig.MANAGER_SERVICE = pt.getString("manager.pool.name");
                SocketConfig.MANAGER_HOST = pt.getString("manager.pool.host");
                SocketConfig.MANAGER_PORT = pt.getString("manager.pool.port");
                /**
                 * k3管理服务 add by lvchangrong 2015-03-11 用于新期检查里的快三开新期协议
                 */
                SocketConfig.K3MANAGER_SERVICE = pt.getString("k3manager.pool.name");
                SocketConfig.K3MANAGER_HOST = pt.getString("k3manager.pool.host");
                SocketConfig.K3MANAGER_PORT = pt.getString("k3manager.pool.port");
                /**
                 * S10时时乐管理服务 add by lvchangrong 2015-03-12
                 */
                SocketConfig.S10MANAGER_SERVICE = pt.getString("s10manager.pool.name");
                SocketConfig.S10MANAGER_HOST = pt.getString("s10manager.pool.host");
                SocketConfig.S10MANAGER_PORT = pt.getString("s10manager.pool.port");

                /**
                 * 普通游戏抽奖服务
                 */
                SocketConfig.RAFFLE_SERVICE = pt.getString("raffle.pool.name");
                SocketConfig.RAFFLE_HOST = pt.getString("raffle.pool.host");
                SocketConfig.RAFFLE_PORT = pt.getString("raffle.pool.port");
            } else {
                logger.error("file socket.properties load error");
            }
            logger.info("Socket server info init complete...");

            logger.info("sync file init start...");
            filename = new File(path + "/classes/config/sync.properties").getAbsolutePath();
            if (pt.loadFile(filename, "UTF-8")) {
                SyncInfo.MAX_QUERY_COUNT = pt.getInteger("sync.maxQueryCount");
                SyncInfo.SLEEP_TIME = pt.getInteger("sync.sleepTime");
            } else {
                logger.error("file sync.properties load error");
            }
            logger.info("sync file init complete...");

            //定时检查符合解封条件的终端并执行解封操作  add by lvchangrong 2014-11-09
            IExecTaskTmnStartService execTaskTmnStartService = (IExecTaskTmnStartService) wac.getBean("execTaskTmnStart");
            execTaskTmnStartService.execTask();
            //定时扣款任务
            IExecTaskService execTaskService = (IExecTaskService) wac.getBean("execTask");
            execTaskService.run();
            //定时生成站点游戏销售统计文件 add by LH 2015-04-16
            IExecTaskTmnGameFileService execTaskTmnGameFileService = (IExecTaskTmnGameFileService) wac.getBean("execTaskTmnGameFile");
            execTaskTmnGameFileService.execTask();

            //定时生成无纸化快开游戏新期文件 add by YR 2015-09-28
            IExecTaskDrawInfoFileService execTaskDrawInfoFileService = (IExecTaskDrawInfoFileService) wac.getBean("execTaskDrawInfoFile");
            execTaskDrawInfoFileService.execTask();

            //项目启动时启动定时任务
            startThreads(wac);

            logger.info("start manager success......");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 开启任务线程
     *
     * @param wac
     */
    private void startThreads(WebApplicationContext wac) {
        logger.warn("thread init start...");
//        //开始快3同步线程
//        new StartK3SyncThread(wac).start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            logger.error("", ex);
//        }
//        //开始时时乐同步线程
//        new StartS10SyncThread(wac).start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            logger.error("", ex);
//        }
//        //开始keno同步线程
//        new StartK80SyncThread(wac).start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            logger.error("", ex);
//        }

        //引入电话投注之后
        //开始gambler自动兑奖线程
//        new StartAuroCashThread(wac).start();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ex) {
//            logger.error("", ex);
//        }
        logger.warn("thread init complete...");
    }
}
