package com.bestinfo.appserver.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bestinfo.appserver.initialize.InitializeBean;
import com.bestinfo.appserver.manage.WorkerConsumer;
import com.bestinfo.appserver.manage.WorkerProducer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public final class WebContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(WebContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent arg) {
//        try {
//            WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
//
//            String path = wac.getServletContext().getRealPath("/WEB-INF");
//            PropertiesUtil pt = new PropertiesUtil();
//            logger.info("filepath.properties init start...");
//            String filename = new File(path + "/classes/config/filepath.properties").getAbsolutePath();
//            if (pt.loadFile(filename, "UTF-8")) {
//                String envirVariableName = pt.getString("envirVariableName");
//                if (envirVariableName != null && !"".equals(envirVariableName.trim())) {
//                    FilePath.rootPath = System.getenv(envirVariableName);
//                }
//
//                FilePath.clpcenter = pt.getString("clpcenter");
//                FilePath.burncd = pt.getString("burncd");
//                FilePath.game = pt.getString("game");
//                FilePath.game2 = pt.getString("game2");
//                FilePath.rule = pt.getString("rule");
//                FilePath.account = pt.getString("account");
//                FilePath.win = pt.getString("win");
//                FilePath.issuec = pt.getString("issuec");
//                FilePath.issuet = pt.getString("issuet");
//                FilePath.issue = pt.getString("issue");
//                FilePath.luckyno = pt.getString("luckyno");
//                FilePath.prize = pt.getString("prize");
//                FilePath.drawinfo = pt.getString("drawinfo");
//                FilePath.soft = pt.getString("soft");
//                FilePath.bank = pt.getString("bank");
//                FilePath.bulletin = pt.getString("bulletin");
//                FilePath.dayfile = pt.getString("dayfile");
//
//                logger.info("envirVariableName: " + envirVariableName);
//                logger.info("rootPath:" + FilePath.rootPath);
//                logger.info("clpcenter:" + FilePath.clpcenter);
//                logger.info("burncd:" + FilePath.burncd);
//                logger.info("game:" + FilePath.game);
//                logger.info("game2:" + FilePath.game2);
//                logger.info("issuec:" + FilePath.issuec);
//                logger.info("issuet:" + FilePath.issuet);
//                logger.info("issue:" + FilePath.issue);
//                logger.info("luckyno:" + FilePath.luckyno);
//                logger.info("prize:" + FilePath.prize);
//                logger.info("drawinfo:" + FilePath.drawinfo);
//                logger.info("soft:" + FilePath.soft);
//                logger.info("bank:" + FilePath.bank);
//                logger.info("bulletin:" + FilePath.bulletin);
//                logger.info("rule:" + FilePath.rule);
//                logger.info("account:" + FilePath.account);
//                logger.info("win:" + FilePath.win);
//                logger.info("dayfile:" + FilePath.dayfile);
//                logger.info("file path init complete...");
//            } else {
//                logger.error("filepath.properties load error");
//            }
//            logger.info("filepath.properties init complete...");
//
//            logger.warn("redis lock init start...");
//            filename = new File(path + "/classes/config/redis.properties").getAbsolutePath();
//            if (pt.loadFile(filename, "UTF-8")) {
//                RedisLock.islock = pt.getBoolean("islock");
//                RedisLock.threadWaitTimeout = pt.getInteger("redisWaitLockTimeout");
//                RedisLock.intervalTime = pt.getInteger("intervalTime");
//                RedisLock.lockTimeout = pt.getString("lockTimeout");
//            } else {
//                logger.error("file redis.properties load error");
//            }
//            logger.warn("redis lock init complete...");
//
//            logger.info("context.properties init start...");
//            filename = new File(path + "/classes/config/context.properties").getAbsolutePath();
//            if (pt.loadFile(filename, "UTF-8")) {
//                InitializeData.localDesKey = pt.getString("LocalDesKey");
//                InitializeData.waitResponseTime = pt.getInteger("WaitResponseTime");
//                InitializeData.queueServerAddress = pt.getString("QueueServerAddress");
//                InitializeData.queueTopic = pt.getString("QueueTopic");
//                InitializeData.checkNumSmsFormat = pt.getString("CheckNumSmsFormat");
//                InitializeData.sendCheckNumInterval = pt.getLong("SendCheckNumInterval");
//                InitializeData.checkNumTimeOutInterval = pt.getLong("CheckNumTimeOutInterval");
//                InitializeData.maxTryLoginCount = pt.getInteger("MaxTryLoginCount");
//                InitializeData.maxTryLoginTime = pt.getLong("MaxTryLoginTime");
//                InitializeData.maxResponsePoolSize = pt.getInteger("MaxResponsePoolSize");
//                InitializeData.maxResponsePoolSize = pt.getInteger("MaxResponsePoolSize");
//                InitializeData.sessionTimeOutInterval = pt.getInteger("SessionTimeOutInterval");
//            } else {
//                logger.error("context.properties load error");
//            }
//            logger.info("context.properties init complete...");
//
//            ResponsePool responsePool = ResponsePool.getInstance();
//            responsePool.setMaxPoolSize(InitializeData.maxResponsePoolSize);
//
////            logger.info("start queue");
////            WorkerProducer workerProducer = WorkerProducer.getInstance();
////            if (workerProducer.startQueue() == false) {
////                logger.error("Start queue error!");
////                return;
////            }
////            WorkerConsumer workerConsumer = WorkerConsumer.getInstance();
////            if (workerConsumer.startDeal() == false) {
////                logger.error("Start deal error!");
////                return;
////            }
////            logger.info("start queue success");
//
//            logger.info("app server start success");
//        } catch (Exception e) {
//            logger.error("Initialize app server error!", e);
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            ApplicationContext ctx = InitializeBean.getApplicationContext();
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ctx.getBean("taskExecutor");
            taskExecutor.shutdown();
            WorkerConsumer.getInstance().stopDeal();
            WorkerProducer.getInstance().stopQueue();
        } catch (Exception e) {
        }
    }
}
