package com.bestinfo.gambler.batch;

import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.protocols.agents.AgentAbstractUserInfo;
import com.bestinfo.protocols.agents.AgentAbstractUserReqList;
import com.bestinfo.protocols.agents.AgentRechargeUserInfo;
import com.bestinfo.protocols.agents.AgentRechargeUserReqList;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserQueryReq;
import com.bestinfo.protocols.users.UserSummaryInfoReq;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 *
 */
public class BetTicketPac {

    private static final Logger logger = Logger.getLogger(BetTicketPac.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private List<Future<Object>> tasks = new ArrayList<Future<Object>>();

    /**
     * 多终端指定号码投注
     *
     * @param start_tmn 开始终端编号
     * @param end_tmn 结束终端编号
     * @param doNu 每个终端循环执行次数
     * @param req 投注请求报文数据结构
     * @param kk_drawId 快开大期号
     * @param c 投注成功和失败数目记录
     * @return
     */
    public int execTicketBet4Batch(int fromInt, int toInt, final int doNu, final PBetSchemeRequst req, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            long startTime = System.currentTimeMillis();
            list = TmnSelectDao.getTerminalAndCity(Integer.valueOf(head.getDealer_id()), fromInt, toInt);
            //开始时间
            long eTime = System.currentTimeMillis();
            logger.info("终端机号生成时间：" + (eTime - startTime) / 1000F);
            for (Object terminalid : list) {
                AppHeader newHead = null;
                PBetSchemeRequst newReq = null;
                newHead = (AppHeader) head.clone();
                newReq = (PBetSchemeRequst) req.clone();
                newHead.setTerminal_id(((Terminal) terminalid).getTerminal_id());
                newReq.setCity_id(((Terminal) terminalid).getCity_id());
                TicketBetCallable task = new TicketBetCallable(newReq, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

    public int execTicketBetBatch(final int doNu, final int from, final int end, final PBetSchemeRequst req, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            List list1 = null;
            long startTime = System.currentTimeMillis();
            list = GamblerInfoDao.getGamblerInfo(head.getDealer_id(), from, end);
            //开始时间
            long eTime = System.currentTimeMillis();
            logger.info("终端机号生成时间：" + (eTime - startTime) / 1000F);
            for (Object gamblerInfo : list) {
                AppHeader newHead = null;
                PBetSchemeRequst newReq = null;
                newHead = (AppHeader) head.clone();
                newReq = (PBetSchemeRequst) req.clone();
                newReq.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
                BetTicket task = new BetTicket(newReq, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

    public int execRechargeBatch(final int doNu, final int end, final AgentRechargeUserReqList req, final AgentRechargeUserInfo ar, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            long startTime = System.currentTimeMillis();
            list = GamblerInfoDao.getGamblerInfo(head.getDealer_id(), doNu, end);
            logger.info("list: " + list.size());
            //开始时间
            long eTime = System.currentTimeMillis();
            for (Object gamblerInfo : list) {
                AppHeader newHead = null;
                newHead = (AppHeader) head.clone();
                AgentRechargeUserReqList newreq = null;
                AgentRechargeUserInfo newuser = new AgentRechargeUserInfo();
                newreq = (AgentRechargeUserReqList) req.clone();
                for (int i = 0; i < newreq.getAgentUserList().size(); i++) {
                    // newuser.setRechargeMoney(newreq.getAgentUserList().get(i).getRechargeMoney());
                    newuser.setRechargeMoney(new BigDecimal(new Random().nextInt(1000)));
                    logger.info("彩民: " + ((GamblerInfo) gamblerInfo).getGambler_name() + " : 充值： " + newuser.getRechargeMoney());
                    // newuser.setRechargeMoney(BigDecimal.ZERO);
                    newuser.setRechargeTime(newreq.getAgentUserList().get(i).getRechargeTime());
                    newuser.setRechargeType(newreq.getAgentUserList().get(i).getRechargeType());
                }

                List<AgentRechargeUserInfo> newagentUserList = new ArrayList<AgentRechargeUserInfo>();
                // newuser=(AgentRechargeUserInfo)ar.clone();
                newuser.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
                String time = new Date().getTime() + "";
                newuser.setDealerSerial("0@12@1@" + time.substring(3) + newuser.getGamblerName());
                logger.info("流水:" + newuser.getDealerSerial());
                newagentUserList.add(newuser);
                newreq.setAgentUserList(newagentUserList);
//                String name = req.getDealerManager();
//                logger.info("req.getDealerManager()" + req.getDealerManager() + " name: " + name);
//                logger.info("req.getPwd()" + req.getPwd());
//                logger.info("req.getOwnerName()" + req.getOwnerName());
//                logger.info("req.getIdNo()" + req.getIdNo());
//                newreq.setPwd(req.getPwd());
//                logger.info(" newreq.setDealerManager33333:" + req.getPwd() + "++++++" + newreq.getPwd());
//                newreq.setDealerManager(name);
//                logger.info(" newreq.setDealerManager :" + newreq.getDealerManager());
//
//                newreq.setOwnerName(req.getOwnerName());
//                newreq.setIdNo(req.getIdNo());
//                AgentRechargeUserInfo newar = null;
//                newar = (AgentRechargeUserInfo) ar.clone();
//                // newreq = (AgentRechargeUserReqList) req.clone();
//                List<AgentRechargeUserInfo> agentUserList = new ArrayList<AgentRechargeUserInfo>();
//
//                newar.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
//                logger.info(" newar.setGamblerName: " + newar.getGamblerName());
//                String time = new Date().getTime() + "";
//                newar.setDealerSerial("0@12@1@" + time.substring(3) + newar.getGamblerName());
//                agentUserList.add(newar);
//                req.setAgentUserList(agentUserList);
//               // logger.info("newreq.setAgentUserList: " + newreq.getAgentUserList());
//
////                newagentUserList = (AgentRechargeUserInfo) ar.;
////                ar.setGamblerName((GamblerInfo) gamblerInfo).getGambler_name());
                RechargeSale task = new RechargeSale(newreq, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

    public int execAbstractBatch(final int doNu, final int end, final AgentAbstractUserReqList req, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            long startTime = System.currentTimeMillis();
            list = GamblerInfoDao.getGamblerInfo(head.getDealer_id(), doNu, end);
            logger.info("list: " + list.size());
            //开始时间
            long eTime = System.currentTimeMillis();
            for (Object gamblerInfo : list) {
                AppHeader newHead = null;
                newHead = (AppHeader) head.clone();
                AgentAbstractUserReqList newreq1 = null;
                AgentAbstractUserInfo newuser = new AgentAbstractUserInfo();
                newreq1 = (AgentAbstractUserReqList) req.clone();
                for (int i = 0; i < newreq1.getAgentAbstractUsers().size(); i++) {
                    //newuser.setAbstractMoney(newreq1.getAgentAbstractUsers().get(i).getAbstractMoney());
                    newuser.setAbstractMoney(new BigDecimal(new Random().nextInt(1000)));
                    logger.info("彩民: " + ((GamblerInfo) gamblerInfo).getGambler_name() + " : 提款： " + newuser.getAbstractMoney());
                    // newuser.setRechargeMoney(BigDecimal.ZERO);
                    newuser.setAbstractTime(newreq1.getAgentAbstractUsers().get(i).getAbstractTime());
                    newuser.setAbstractType(newreq1.getAgentAbstractUsers().get(i).getAbstractType());
                }
                List<AgentAbstractUserInfo> newagentUserList = new ArrayList<AgentAbstractUserInfo>();
                // newuser=(AgentRechargeUserInfo)ar.clone();
                newuser.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
                String time = new Date().getTime() + "";
                newuser.setDealerSerial("0@2@1@" + time.substring(3) + newuser.getGamblerName());
                newagentUserList.add(newuser);
                newreq1.setAgentAbstractUsers(newagentUserList);

//                String name = req.getDealerManager();
//                logger.info("req.getDealerManager()" + req.getDealerManager() + " name: " + name);
//                logger.info("req.getPwd()" + req.getPwd());
//                logger.info("req.getOwnerName()" + req.getOwnerName());
//                logger.info("req.getIdNo()" + req.getIdNo());
//                newreq.setPwd(req.getPwd());
//                logger.info(" newreq.setDealerManager33333:" + req.getPwd() + "++++++" + newreq.getPwd());
//                newreq.setDealerManager(name);
//                logger.info(" newreq.setDealerManager :" + newreq.getDealerManager());
//
//                newreq.setOwnerName(req.getOwnerName());
//                newreq.setIdNo(req.getIdNo());
//                AgentRechargeUserInfo newar = null;
//                newar = (AgentRechargeUserInfo) ar.clone();
//                // newreq = (AgentRechargeUserReqList) req.clone();
//                List<AgentRechargeUserInfo> agentUserList = new ArrayList<AgentRechargeUserInfo>();
//
//                newar.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
//                logger.info(" newar.setGamblerName: " + newar.getGamblerName());
//                String time = new Date().getTime() + "";
//                newar.setDealerSerial("0@12@1@" + time.substring(3) + newar.getGamblerName());
//                agentUserList.add(newar);
//                req.setAgentUserList(agentUserList);
//               // logger.info("newreq.setAgentUserList: " + newreq.getAgentUserList());
//
////                newagentUserList = (AgentRechargeUserInfo) ar.;
////                ar.setGamblerName((GamblerInfo) gamblerInfo).getGambler_name());
                AbstractSale task = new AbstractSale(newreq1, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

    public int execUserSumBatch(final int doNu, final int end, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            long startTime = System.currentTimeMillis();
            list = GamblerInfoDao.getGamblerInfo(head.getDealer_id(), doNu, end);
            logger.info("list: " + list.size());
            //开始时间
            long eTime = System.currentTimeMillis();
            for (Object gamblerInfo : list) {
                AppHeader newHead = null;
                newHead = (AppHeader) head.clone();
                UserSummaryInfoReq req = new UserSummaryInfoReq();
                req.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
                req.setGamblerPwd(((GamblerInfo) gamblerInfo).getPwd());

                UserSum task = new UserSum(req, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

    public int execUserQueryBatch(final int doNu, final int end, final Count c, final AppHeader head) {
        try {
            //获取终端
            List list = null;
            long startTime = System.currentTimeMillis();
            list = GamblerInfoDao.getGamblerInfo(head.getDealer_id(), doNu, end);
            logger.info("list: " + list.size());
            //开始时间
            long eTime = System.currentTimeMillis();
            for (Object gamblerInfo : list) {
                AppHeader newHead = null;
                newHead = (AppHeader) head.clone();
                UserQueryReq req = new UserQueryReq();
                req.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
                req.setGamblerPwd(((GamblerInfo) gamblerInfo).getPwd());
                req.setQueryTime(new Date());
                UserQuery task = new UserQuery(req, newHead, c, doNu);
                Future<Object> future = executorService.submit(task);
                tasks.add(future);
            }
            for (Future<Object> future : tasks) {
                try {
//                    logger.info("================" + future.get().toString()); // 打印各个线程（任务）执行的结果  
                    while (true) {
                        if (future.isDone() && !future.isCancelled()) {
                            break;
                        } else {
                            Thread.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    logger.error("[execute reg future]\n", e);
                }
            }
            long endTime = System.currentTimeMillis();
            float takeTime = (endTime - startTime) / 1000F;
            StaticVariable.TASTE_TIME = takeTime;
            logger.error("successful:" + c.getCount() + "\t failed:" + c.getFailcount() + "\t sendfailed:" + c.getSendFail() + "\t take time: " + takeTime + "ms ");
            return 0;

        } catch (Exception ex) {
            logger.error("ex :" + ex);
            return -1;
        }
    }

}
