package com.bestinfo.gambler.bet;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.bean.encoding.TChargeType;
import com.bestinfo.bean.gambler.TGamblerInfo;
import com.bestinfo.define.gambler.GamblerChargeType;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.all.BetNo;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.entity.AgentSerialReqRes;
import com.bestinfo.gambler.entity.BetNumber;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.client.bet.PBetSchemeClient;
import com.bestinfo.protocols.message.AppHeader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * 电话投注--华祥方式投注
 *
 * @author chenliping
 */
public class Telephone {

    private static final Logger logger = Logger.getLogger(Telephone.class);

    /**
     * 设置用户信息
     *
     * @param userlist
     * @param tsbr
     * @param index
     * @param altch
     */
    private void setuserinfo(List<TGamblerInfo> userlist, PBetSchemeRequst tsbr, int index, ArrayList<TChargeType> altch) {
        tsbr.setGamblerName(userlist.get(index).getGambler_name());
        if (tsbr.getCharge_type_id() == GamblerChargeType.NOCHARGE) {//后期结算
            tsbr.setIsSettled(1);
            if (altch.isEmpty()) {
                logger.error("业务数据为空");
                return;
            }
            int min = 0;
            int max = altch.size();
            int a = new Random().nextInt(max) % (max - min + 1) + min;
//            int a[] = CommTool.getRandInt(0, altch.size() - 1, 1);
            tsbr.setBusiness_id(Integer.toString(altch.get(a).getChargeTypeId()));
//            tsbr.setAccountType(userlist.get(index).getAccountTypeId());
            tsbr.setAccountType(new Random().nextInt(3) % (3) + 1);//帐户类型1，2，3 随机
        } else {
            tsbr.setIsSettled(0);
        }
//        int b[] = CommTool.getRandInt(0, 22, 1);//随机地市
        tsbr.setCity_id(userlist.get(index).getCity_id());
    }
 /**
     * 创建投注发送的xml报文，获取流水号,用户定制号码
     * 140
     * @param head
     * @param tbr
     * @return
     * @throws Exception
     */
    private ArrayList<String> getMuchMoreBetXml(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
        //取流水号        
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return null;
        }
        head.setAction(ActionID.SCHEMES_BET);
        ArrayList<String> xml = new ArrayList<String>();//存放xml报文
        for (int i = 0; i < ticketnum; i++) {
            try {
                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
                tbr.setGambler_serial_no((new Date().getTime()+"").substring(3,(new Date().getTime()+"").length()) + Long.toString(startserial)+tbr.getGamblerName().substring(6));
                //String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
                PBetSchemeClient betScheme = new PBetSchemeClient();
                String requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
                String sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
                logger.info("sessionKey is: " + sessionKey);
                if (sessionKey == null) {
                    logger.info("sessionKey is null");
                }
                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
                head.setBody_sign(ret_key);
                requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
                xml.add(requestxml);
                startserial += 1;
                if (startserial > endserial) {
                    break;
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Telephone.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xml;
    }
    /**
     * 创建投注发送的xml报文，获取流水号,用户定制号码
     * 140
     * @param head
     * @param tbr
     * @return
     * @throws Exception
     */
    private ArrayList<String> getMuchMoreBetXml1(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
        //取流水号        
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return null;
        }
        head.setAction(ActionID.SCHEMES_BET_SH);
        ArrayList<String> xml = new ArrayList<String>();//存放xml报文
        for (int i = 0; i < ticketnum; i++) {
            try {
                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
                tbr.setGambler_serial_no(new Date().getTime() + Long.toString(startserial));
//                tbr.setGamblerIdNo("232103199812275889");
                tbr.setGamblerIdNo("沪消字第4112号");
                //String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
                PBetSchemeClient betScheme = new PBetSchemeClient();
                String requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
//                String sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
//                logger.info("sessionKey is: " + sessionKey);
//                if (sessionKey == null) {
//                    logger.info("sessionKey is null");
//                }
//                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
                byte[] ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
                head.setBody_sign(ret_key);
                requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET_SH);
                xml.add(requestxml);
                startserial += 1;
                if (startserial > endserial) {
                    break;
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Telephone.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xml;
    }

    /**
     * 创建投注发送的xml报文，获取了流水号,从文件获取号码
     *
     * @param head
     * @param tbr
     * @param controlfilename 存放号码的文件名
     * @param size
     * @param serno 投注流水号标识符
     * @return
     */
    private ArrayList<String> getMuchMoreBetXml(AppHeader head, PBetSchemeRequst tbr, String controlfilename, int size, String serno, String threadNO) {
        int gameid = tbr.getGame_id();
        int playid = tbr.getPlay_id();
        int betmod = tbr.getBet_mode();
        ArrayList<BetNumber> abetnum = new BetNo().getBetNumber2(gameid, playid, betmod, controlfilename);
        if (abetnum.isEmpty()) {
            logger.error("get bet no error");
            return null;
        }
        int ticketnum = abetnum.size();//得到票个数
        if (size == 0) {
            size = ticketnum;
        }
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, size);
        if (serial == null) {
            logger.error("get serial error");
            return null;
        }
        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return null;
        }
        ArrayList<String> xml = new ArrayList<String>();
        Bet be = new Bet();
        for (int i = 0; i < size; i++) {
            if (i % ticketnum == 0) {
                for (int j = 0; j < ticketnum; j++) {
                    BetNumber betNumber = abetnum.get(j);
                    tbr.setGambler_serial_no(threadNO + serno + Long.toString(startserial));
                    String Resultxml = be.getBetXml(head, tbr, betNumber);
                    if (Resultxml == null) {
                        return null;
                    }
                    xml.add(Resultxml);
                    startserial += 1;
                    if (startserial > endserial) {
                        break;
                    }
                }
            }
        }
        return xml;
    }

    /**
     * 将中奖用例封装成xml,从数据库获取用例
     *
     * @param head
     * @param tbr
     * @param id 示例编号
     * @param serNo 投注流水号标识符
     * @return
     */
    private ArrayList<String> getMuchMoreBetXml(AppHeader head, PBetSchemeRequst tbr, int id, String serNo, boolean lucky) {
        ArrayList<BetNumber> atsbr = null;
        if (lucky) {
            atsbr = new BetNo().getGameLuckyNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode(), id);
        } else {
            atsbr = new BetNo().getGameErrorNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode());
        }
        if (atsbr == null) {
            return null;
        }
        int ticketnum = atsbr.size();
        //取流水号
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);

        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return null;
        }
        ArrayList<String> xml = new ArrayList<String>();
        Bet be = new Bet();
        for (BetNumber tbetschemebetingreq : atsbr) {
            tbr.setGambler_serial_no(serNo + Long.toString(startserial));
            String Resultxml = be.getBetXmlLW(head, tbr, tbetschemebetingreq);
            if (Resultxml == null) {
                return null;
            }
            xml.add(Resultxml);
            startserial += 1;
            if (startserial > endserial) {
                break;
            }
        }
        return xml;
    }

    /**
     * 串行发送多张报文
     *
     * @param als
     * @param c
     */
    private void SendBetingXml(ArrayList<String> als, Count c) {
        int ticketnum = als.size();
//        String filename1 = gameblername + String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
//        String filenameReq = StaticVariable.NUMBERREQXML + filename1;
//        String filenameRes = StaticVariable.NUMBERRESXML + filename1;
        BetNo bn = new BetNo();
        for (int i = 0; i < ticketnum; i++) {
//            int recode = send(als.get(i), filenameReq, filenameRes);
            int recode = bn.send(als.get(i));
            if (recode == 0) {
                c.addCount();
            } else if (recode == -5) {
                c.addSendFail();
            } else {
                c.addfailcount();
                if (recode == -4) {
                    break;
                }
            }
        }
    }

    private List<TGamblerInfo> getUser(int client_num, AppHeader head, String username) {
        List<TGamblerInfo> userlist = null;
        if (client_num > 1) {
            int role = SerialNo.getuserrole(head);
            if (role < 0) {
                return null;
            }
            userlist = UserSelect.getUser(client_num, role, head.getDealer_id());
            if (userlist == null) {
                logger.error("get user failed");
                return null;
            }

        } else if (client_num == 1) {
            TGamblerInfo tb = UserSelect.getUser(username, head.getDealer_id());
            if (tb == null) {
                return null;
            }
            userlist = new ArrayList<TGamblerInfo>();
            userlist.add(tb);
        }
        return userlist;
    }

    /**
     * 随机号码一用户一线程投注
     *
     * @param client_num
     * @param head
     * @param tbr
     * @param eachnum
     * @param controlfilename
     * @param serno
     */
    public void SendRandXml(int client_num, final AppHeader head, PBetSchemeRequst tbr, final int eachnum, final String controlfilename, final String serno) {
//        List<TGamblerInfo> userlist = getUser(client_num, head, tbr.getGamblerName());
//        if (userlist == null) {
//            logger.error("no user");
//            return;
//        }
        List<List<TGamblerInfo>> userlists = getUser_new(client_num, head, tbr.getGamblerName());
        if (userlists == null) {
            logger.error("no user");
            return;
        }

        ArrayList<TChargeType> altch = UserSelect.getChargeType(head.getDealer_id());//获取业务编号   
        if (altch.isEmpty()) {
            logger.error("获取业务编号为空");
            return;
        }
        final Count c = new Count();
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(client_num);// n个线程可以同时访问 
        long beforeTime = System.currentTimeMillis();
        for (int index = 0; index < client_num; index++) { //模拟多个客户端   
            final String threadNO = Integer.toString(index);
            //分配用户
            final PBetSchemeRequst tsbr = Bet.copyPBetSchemeRequst(tbr);
//            setuserinfo(userlist, tsbr, index, altch);
            setuserinfo_new(client_num, userlists, tsbr, index, altch);
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可   
                        //分配流水号,创建xml投注报文
                        ArrayList<String> als = getMuchMoreBetXml(head, tsbr, controlfilename, eachnum, serno, threadNO);
                        if (als == null) {
                            logger.error("get bet xml error");
                            return;
                        }
                        SendBetingXml(als, c);//请求流水号并发报文
                        semp.release();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }
                }
            });
        }
        // 退出线程池
        exec.shutdown();

        try {
            while (!exec.isTerminated()) {
                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("", e);
            return;
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("成功:" + c.getCount() + " \t失败:" + c.getFailcount() + " \t发送失败:" + c.getSendFail() + " \t共花费时间:" + CommTool.getTime(difference));
        System.out.println("成功:" + c.getCount() + "\t失败:" + c.getFailcount() + "\t发送失败:" + c.getSendFail() + "\t共花费时间:" + CommTool.getTime(difference));
    }

    private int getUserInfo(AppHeader head, PBetSchemeRequst tbr) {

        if (tbr.getCharge_type_id() == GamblerChargeType.NOCHARGE) {//后期结算
            tbr.setIsSettled(1);
           //tbr.setBusiness_id(Integer.toString(14010293));

//            tsbr.setAccountType(userlist.get(index).getAccountTypeId());
            //tbr.setAccountType(1);//帐户类型1，2，3 随机
        } else {

            tbr.setIsSettled(0);
            tbr.setBusiness_id(Integer.toString(14010293));
        }
//        int b[] = CommTool.getRandInt(0, 22, 1);//随机地市
        //tbr.setCity_id(12);

//        List<TGamblerInfo> userlist = getUser(1, head, tbr.getGamblerName());
//        if (userlist == null) {
//            logger.error("no user");
//            return -1;
//        }
//        ArrayList<TChargeType> altch = UserSelect.getChargeType(head.getDealer_id());//获取业务编号   
//        if (altch.isEmpty()) {
//            logger.error("获取业务编号为空");
//            return -2;
//        }
//        setuserinfo(userlist, tbr, 0, altch);
        return 0;
    }

    private void LuckyWrong(ArrayList<BetNumber> atsbr, AppHeader head, PBetSchemeRequst tbr, String serNo) {
        int ticketnum = atsbr.size();
        //取流水号
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);

        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return;
        }
        int successticketnum = 0;
        int failticketnum = 0;
        BetNo bn = new BetNo();
        long beforeTime = System.currentTimeMillis();
        Bet be = new Bet();
        for (BetNumber tbetschemebetingreq : atsbr) {
            tbr.setGambler_serial_no(serNo + Long.toString(startserial));
            String Resultxml = be.getBetXmlLW(head, tbr, tbetschemebetingreq);
            if (Resultxml == null) {
                return;
            }
            if (bn.send(Resultxml) == 0) {
                successticketnum++;
            } else {
                failticketnum++;
            }
            startserial += 1;
            if (startserial > endserial) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.info("username:" + tbr.getGamblerName() + "\tsuccessfull:" + successticketnum + "\tfailed:" + failticketnum + "\ttime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * 电话投注中奖号码
     *
     * @param head
     * @param tbr
     * @param caseid 用例编号
     * @param serNo 流水号标识
     */
    public void SendLuckyXml(AppHeader head, PBetSchemeRequst tbr, int caseid, String serNo) {
        head.setAction(ActionID.SCHEMES_BET);
        int re = getUserInfo(head, tbr);
        if (re != 0) {
            logger.error(re);
            return;
        }

        ArrayList<BetNumber> atsbr = new BetNo().getGameLuckyNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode(), caseid);
        if (atsbr == null) {
            return;
        }
        LuckyWrong(atsbr, head, tbr, serNo);

//        ArrayList<String> als = getMuchMoreBetXml(head, tbr, caseid, serNo, true);
//        if (als == null) {
//            return;
//        }
//        int successticketnum = 0;
//        int failticketnum = 0;
//        BetNo bn = new BetNo();
//        long beforeTime = System.currentTimeMillis();
//        for (String string : als) {
//            if (bn.send(string) == 0) {
//                successticketnum++;
//            } else {
//                failticketnum++;
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        long difference = endTime - beforeTime;
//        logger.info("username:" + tbr.getGamblerName() + "\tsuccessfull:" + successticketnum + "\tfailed:" + failticketnum + "\ttime:" + CommTool.getTime(difference));
    }

    /**
     * 错误号码销售
     *
     * @param head
     * @param tbr
     * @param serno
     */
    public void SendErrorXml(AppHeader head, PBetSchemeRequst tbr, String serno) {
        head.setAction(ActionID.SCHEMES_BET);
        int re = getUserInfo(head, tbr);
        if (re != 0) {
            logger.error(re);
            return;
        }
        ArrayList<BetNumber> atsbr = new BetNo().getGameErrorNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode());
        if (atsbr == null) {
            return;
        }
        LuckyWrong(atsbr, head, tbr, serno);
//        ArrayList<String> als = getMuchMoreBetXml(head, tbr, 0, serno, false);
//        if (als == null) {
//            return;
//        }
//        int successticketnum = 0;
//        int failticketnum = 0;
//        BetNo bn = new BetNo();
//        long beforeTime = System.currentTimeMillis();
//        for (String string : als) {
//            if (bn.send(string) == 0) {
//                successticketnum++;
//            } else {
//                failticketnum++;
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        long difference = endTime - beforeTime;
//        logger.info("username:" + tbr.getGamblerName() + "\tsuccessfull:" + successticketnum + "\tfailed:" + failticketnum + "\ttime:" + CommTool.getTime(difference));
    }

    /**
     * 发送投注的xml报文,单张报文，可以批量发送，用户定制号码，无物理终端
     *
     * @param head
     * @param tbr
     * @param isuser
     * @param ticketnum
     */
    public void SendAppointBetXml(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser,Count c) {
        try {
            if (!isuser) {
                int re = getUserInfo(head, tbr);
                if (re != 0) {
                    logger.error(re);
                    return;
                }
            }
            ArrayList<String> als = getMuchMoreBetXml(head, tbr, ticketnum);
            if (als == null) {
                return;
            }
            int successticketnum = 0;
            int failticketnum = 0;
            BetNo bn = new BetNo();
            long beforeTime = System.currentTimeMillis();
            for (String string : als) {
                if (bn.send(string) == 0) {//发送数据
                    c.addCount();
                    successticketnum++;
                } else {
                    failticketnum++;
                    c.addfailcount();
                }
            }
            long endTime = System.currentTimeMillis();
            long difference = endTime - beforeTime;
            logger.info("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
            //System.out.println("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
            //JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
            //logger.info("用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference));
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    /**
     * 发送投注的xml报文,单张报文，可以批量发送，用户定制号码，无物理终端
     *
     * @param head
     * @param tbr
     * @param isuser
     * @param ticketnum
     */
    public void SendAppointBetXml1(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser) {
        try {
            if (!isuser) {
                int re = getUserInfo(head, tbr);
                if (re != 0) {
                    logger.error(re);
                    return;
                }
            }
            ArrayList<String> als = getMuchMoreBetXml1(head, tbr, ticketnum);
            if (als == null) {
                return;
            }
            int successticketnum = 0;
            int failticketnum = 0;
            BetNo bn = new BetNo();
            long beforeTime = System.currentTimeMillis();
            for (String string : als) {
                if (bn.send(string) == 0) {//发送数据
                    successticketnum++;
                } else {
                    failticketnum++;
                }
            }
            long endTime = System.currentTimeMillis();
            long difference = endTime - beforeTime;
            logger.info("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
            //System.out.println("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
            //JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
            //logger.info("用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference));
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    

    //用户分组
    private List<List<TGamblerInfo>> getUser_new(int client_num, AppHeader head, String username) {
        List<List<TGamblerInfo>> userlists = null;
        ArrayList<TGamblerInfo> userlist = null;
        if (client_num > 1) {
            int role = SerialNo.getuserrole(head);
            if (role < 0) {
                return null;
            }
            userlists = UserSelect.getUserGroup(client_num, role, head.getDealer_id());
            if (userlists == null) {
                logger.error("get user failed");
                return null;
            }
        } else if (client_num == 1) {
            TGamblerInfo tb = UserSelect.getUser(username, head.getDealer_id());
            if (tb == null) {
                return null;
            }
            userlist = new ArrayList<TGamblerInfo>();
            userlist.add(tb);
            userlists.add(userlist);
        }
        return userlists;
    }

    private void setuserinfo_new(int client_num, List<List<TGamblerInfo>> userlists, PBetSchemeRequst tsbr, int index, ArrayList<TChargeType> altch) {

        int city_max_user_num = Telephone.get_max_users_num_from_city(userlists);
        List<TGamblerInfo> userl = new ArrayList<TGamblerInfo>();
        List<TGamblerInfo> userlist = null;
        for (int i = 0; i < city_max_user_num; i++) {
            userlist = Telephone.get_layer_users_from_city(userlists, userl, i);
        }
        tsbr.setGamblerName(userlist.get(index).getGambler_name());
        if (tsbr.getCharge_type_id() == GamblerChargeType.NOCHARGE) {//后期结算
            tsbr.setIsSettled(1);
//            if (altch.isEmpty()) {
//                logger.error("业务数据为空");
//                return;
//            }
            int min = 0;
            int max = altch.size();
            int a = new Random().nextInt(max) % (max - min + 1) + min;
//            int a[] = CommTool.getRandInt(0, altch.size() - 1, 1);
            tsbr.setBusiness_id(Integer.toString(altch.get(a).getChargeTypeId()));
//            tsbr.setAccountType(userlist.get(index).getAccountTypeId());
            tsbr.setAccountType(new Random().nextInt(3) % (3) + 1);//帐户类型1，2，3 随机
        } else {
            tsbr.setIsSettled(0);
        }
//        int b[] = CommTool.getRandInt(0, 22, 1);//随机地市
        tsbr.setCity_id(userlist.get(index).getCity_id());

    }

    public static int get_max_users_num_from_city(List<List<TGamblerInfo>> userlists) {
        int city_max_user_num = 0;
        for (int i = 0; i < userlists.size(); i++) {
            List<TGamblerInfo> gamlist = userlists.get(i);
            if (city_max_user_num < gamlist.size()) {
                city_max_user_num = gamlist.size();
            }
        }
        return city_max_user_num;
    }

    public static List<TGamblerInfo> get_layer_users_from_city(List<List<TGamblerInfo>> userlists, List<TGamblerInfo> userlist, int n) {
        //List<TGamblerInfo> userlist = new ArrayList<TGamblerInfo>();
        for (int i = 0; i < userlists.size(); i++) {
            List<TGamblerInfo> gamlist = userlists.get(i);
            if (n > (gamlist.size() - 1)) {
                continue;
            }

            TGamblerInfo gam = gamlist.get(n);
            if (gam != null) {
//                System.out.println("***:  " + gam.getGamblerName());
                userlist.add(gam);
            } else {
                continue;
            }
        }
        return userlist;
    }
    
    
    

//    public static void main(String[] args) {
//        List<List<TGamblerInfo>> userlists = new ArrayList<List<TGamblerInfo>>();
//        List<TGamblerInfo> list1 = new ArrayList<TGamblerInfo>();
//        List<TGamblerInfo> list2 = new ArrayList<TGamblerInfo>();
//        TGamblerInfo g = new TGamblerInfo();
//
//        g.setGamblerName("3");
//        TGamblerInfo g1 = new TGamblerInfo();
//
//        g1.setGamblerName("2");
//        TGamblerInfo g2 = new TGamblerInfo();
//
//        g2.setGamblerName("1");
//        TGamblerInfo g3 = new TGamblerInfo();
//
//        g3.setGamblerName("0");
//        list1.add(g1);
//        list1.add(g2);
//
//        list2.add(g);
//        list2.add(g3);
//        userlists.add(list1);
//        userlists.add(list2);
//
//        List<TGamblerInfo> userlist = new ArrayList<TGamblerInfo>();
//        int alluserlen = userlists.size();
//        for (int i = 0; i < alluserlen; i++) {
//            List<TGamblerInfo> gamlist = userlists.get(i);
//            int gamlen = gamlist.size();
//            for (int j = i; j < gamlen; j++) {
//                TGamblerInfo gam = gamlist.get(j);
//                userlist.add(gam);
//                if (gam != null) {
//                    break;
//                }
//            }
//        }
//
//        for (int i = 0; i < userlist.size(); i++) {
//            System.out.println("*****  " + userlist.get(i).getGamblerName());
//        }
//    }
}
