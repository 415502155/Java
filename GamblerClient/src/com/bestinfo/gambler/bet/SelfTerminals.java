package com.bestinfo.gambler.bet;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.bean.gambler.TGamblerInfo;
import com.bestinfo.define.system.SendType;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.all.BetNo;
import com.bestinfo.gambler.all.HttpSend;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.entity.AgentSerialReqRes;
import com.bestinfo.gambler.entity.BetNumber;
import com.bestinfo.gambler.protocols.ActionID;
//import com.bestinfo.gambler.user.CreateUserLoginXmlAndSend;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.bet.PBetSchemeResponse;
import com.bestinfo.protocols.client.bet.PBetSchemeClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.log4j.Logger;

/**
 * 自助终端投注
 *
 * @author chenliping
 */
public class SelfTerminals {

    private static final Logger logger = Logger.getLogger(SelfTerminals.class);

    private ArrayList<String> getMuchMoreBetXml(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
//        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
//        if (serial == null) {
//            return null;
//        }
//        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
//        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
//        if (endserial < startserial) {
//            logger.error("用户未注册，流水号获取失败！");
//            return null;
//        }
        head.setAction(ActionID.SCHEMES_BET);
        ArrayList<String> xml = new ArrayList<String>();//存放xml报文
        for (int i = 0; i < ticketnum; i++) {
            try {
                if (tbr.getGamblerName() == null || tbr.getGamblerName().length() <= 0) {
                    int a[] = CommTool.getRandInt(0, 9, 4);
                    String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
                    bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
                    tbr.setGamblerName(bankno);//卡号
                }
                                //tbr.setGambler_serial_no((new Date().getTime()+"").substring(8,(new Date().getTime()+"").length() ) + Long.toString(startserial)+head.getTerminal_id());

                tbr.setGambler_serial_no((new Date().getTime()+"").substring(8,(new Date().getTime()+"").length() ) + head.getTerminal_id());
               // logger.info("流水号："+tbr.getGambler_serial_no());
                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
                // String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);

                PBetSchemeClient betScheme = new PBetSchemeClient();
                String requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
              //  logger.info("缓存中所有信息："+CacheManager.getCacheAllkey());
                String key = String.valueOf(head.getTerminal_id());
               // logger.info("**key** == "+key);
                String sessionKey = (String) CacheManager.getCacheInfo(key).getValue();
             //   logger.info("sessionKey is: " + sessionKey);
                if (sessionKey == null) {
                    logger.info("sessionKey is null");
                }
                long stime=System.currentTimeMillis();
                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
                 long ptime=System.currentTimeMillis();
                 logger.info("签名生成时间"+(ptime-stime)/1000F);
                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
               // System.out.println("签名是： "+ret_key);
                head.setBody_sign(ret_key);
                requestxml = betScheme.generateXML(head, tbr);
                long gtime=System.currentTimeMillis();
                logger.info("组装报文时间"+(gtime-stime)/1000F);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
//            Resultxml = StaticVariable.ACTION + ActionID.SCHEMES_BET + "&" + Resultxml;
                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
                xml.add(requestxml);
//                startserial += 1;
//                if (startserial > endserial) {
//                    break;
//                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xml;
    }
    
    private ArrayList<String> getMuchMoreBetXml1(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
        if (serial == null) {
            return null;
        }
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
                if (tbr.getGamblerName() == null || tbr.getGamblerName().length() <= 0) {
                    int a[] = CommTool.getRandInt(0, 9, 4);
                    String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
                    bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
                    tbr.setGamblerName(bankno);//卡号
                }
                tbr.setGambler_serial_no(new Date().getTime()+ Long.toString(startserial));
                logger.info("流水号："+tbr.getGambler_serial_no());
                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
                // String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);

                PBetSchemeClient betScheme = new PBetSchemeClient();
                String requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
              //  logger.info("缓存中所有信息："+CacheManager.getCacheAllkey());
                //String key = String.valueOf(head.getTerminal_id());
                //logger.info("**key** == "+key);
//                String sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
//                //logger.info("sessionKey is: " + sessionKey);
//                if (sessionKey == null) {
//                    logger.info("sessionKey is null");
//                }
//                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
                byte[] ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
                System.out.println("签名是： "+ret_key);
                head.setBody_sign(ret_key);
                requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
//            Resultxml = StaticVariable.ACTION + ActionID.SCHEMES_BET + "&" + Resultxml;
                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
                xml.add(requestxml);
                startserial += 1;
                if (startserial > endserial) {
                    break;
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xml;
    }

    /**
     * 创建投注发送的xml报文，取流水号,从文件获取号码,随机号码
     *
     * @param head
     * @param tbr
     * @param controlfilename
     * @param size
     * @return
     */
    private ArrayList<String> getMuchMoreBetXml(AppHeader head, PBetSchemeRequst tbr, String controlfilename, int size, String serNO, String NO) {
        int gameid = tbr.getGame_id();
        int playid = tbr.getPlay_id();
        int betmod = tbr.getBet_mode();
        ArrayList<BetNumber> abetnum = new BetNo().getBetNumber2(gameid, playid, betmod, controlfilename);
        if (abetnum.isEmpty()) {
            return null;
        }
        int ticketnum = abetnum.size();//得到票个数
        if (size == 0) {
            size = ticketnum;
        }
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, size);
        if (serial == null) {
            return null;
        }
        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return null;
        }
        head.setAction(ActionID.SCHEMES_BET);
        ArrayList<String> xml = new ArrayList<String>();
        Bet be = new Bet();
        for (int i = 0; i < size; i++) {
            if (i % ticketnum == 0) {
                for (int j = 0; j < ticketnum; j++) {
                    int a[] = CommTool.getRandInt(0, 9, 4);
                    String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
                    bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
                    tbr.setGamblerName(bankno);//卡号

                    BetNumber betNumber = abetnum.get(j);
                    tbr.setGambler_serial_no(NO + serNO + Long.toString(startserial));
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
     * 将中奖或者错误用例封装成xml,从数据库获取用例
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
        head.setAction(ActionID.SCHEMES_BET);
        for (BetNumber tbetschemebetingreq : atsbr) {
            int a[] = CommTool.getRandInt(0, 9, 4);
            String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
            bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
            tbr.setGamblerName(bankno);//卡号

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

    private AppHeader copyHead(AppHeader head, ArrayList<String> ait, int index) {
        AppHeader header = new AppHeader();
        header.setAction(head.getAction());
        header.setDealer_id(head.getDealer_id());
        header.setMobile(head.getMobile());
        header.setPhone(head.getPhone());
        header.setSent_time(head.getSent_time());
        header.setType(head.getType());
        header.setTerminal_id(head.getTerminal_id());
        header.setBody_sign(head.getBody_sign());
        if (head.getType() == SendType.HELPUS) {//顺序使用物理终端 
//            int a[] = CommTool.getRandInt(0, ait.size(), 1);
//            logger.info("get terminal rand no:"+a[0]);
            header.setTerminal_id(Integer.parseInt(ait.get(index)));
        }
        return header;
    }

    /**
     * 串行发送多张报文
     *
     * @param als
     * @param c
     * @param cookieStore
     */
    private void SendBetingXml(ArrayList<String> als, Count c, CookieStore cookieStore) {
        int ticketnum = als.size();
//        String filename1 = gameblername + String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
//        String filenameReq = StaticVariable.NUMBERREQXML + filename1;
//        String filenameRes = StaticVariable.NUMBERRESXML + filename1;
        BetNo bn = new BetNo();
        for (int i = 0; i < ticketnum; i++) {
//            int recode = send(als.get(i), filenameReq, filenameRes);
            int recode = bn.send(als.get(i), cookieStore);
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

    /**
     * 一终端一线程，一终端多用户，自助终端, 用户名是随机生成的银行卡号，投注前，先运营商管理员登录
     *
     * @param client_num 终端用户数
     * @param head
     * @param tbr
     * @param eachnum 每终端用户数
     * @param controlfilename
     * @param serNO
     */
    public void SendRandXml(int client_num, AppHeader head, PBetSchemeRequst tbr, final int eachnum, final String controlfilename, final String serNO) {
        ArrayList<String> ait = UserSelect1.checkandgetTmnPhy(head.getDealer_id());
        if (ait == null) {
            return;
        }
        if (ait.isEmpty()) {
            logger.error("未有证书或者无终端");
            return;
        }
        final List<TGamblerInfo> userlist = UserSelect1.getUser(1, 1, head.getDealer_id());//取运营商管理员
        if (userlist == null) {
            logger.error("get user failed");
            return;
        }
        final Count c = new Count();
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.  
        if (client_num > ait.size()) {
            client_num = ait.size();
            logger.warn("仅有" + ait.size() + "个证书");
        }
        final Semaphore semp = new Semaphore(client_num);// n个线程可以同时访问 
        long beforeTime = System.currentTimeMillis();
        for (int index = 0; index < client_num; index++) { //模拟多个客户端
            final int NO = index;
            final AppHeader header = copyHead(head, ait, index);
            final PBetSchemeRequst tsbr = Bet.copyPBetSchemeRequst(tbr);
            tsbr.setIsSettled(0);
//            UserLoginReq ul = new UserLoginReq();
//            ul.setGamblerName(userlist.get(0).getGambler_name());
//            String key = HttpUtil.encryptPwd("123456", header.getTerminal_id());
//            if (key == null) {
//                continue;
//            }
//            ul.setGamblerPwd(key);
//            final String userloginxml = new CreateUserLoginXmlAndSend().getUserLoginXml(header, ul);
//            if (userloginxml == null) {
//                logger.error("用户" + ul.getGamblerName() + "登录报文生成异常");
//                continue;
//            }
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可   
                        CookieStore cookieStore = new BasicCookieStore();
//                        if (new CreateUserLoginXmlAndSend().send(userloginxml, cookieStore) != 0) {//登录
//                            logger.error("登录失败");
//                            return;
//                        }
                        //分配流水号,创建xml投注报文
                        ArrayList<String> als = getMuchMoreBetXml(header, tsbr, controlfilename, eachnum, serNO, Integer.toString(NO));
                        if (als == null) {
                            return;
                        }
                        SendBetingXml(als, c, cookieStore);//请求流水号并发报文
                        semp.release();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }
                }
            });
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
    }

    /**
     * 获取运营商管理员登录报文,并进行运营商管理员登录（运营商管理员密码使用终端机证书私钥加密）
     *
     * @param head
     * @param tbr
     * @return
     */
    private int getLoginUserXml(AppHeader head, PBetSchemeRequst tbr, CookieStore cookieStore) {
//        ArrayList<String> ait = UserSelect.checkandgetTmnPhy(head.getDealer_id());
//        if (ait == null) {
//            return -1;
//        }
//        if (ait.isEmpty()) {
//            logger.error("未有证书或者无终端");
//            return -2;
//        }
//        head.setTerminal_id(ait.get(0));
        if (head.getTerminal_id() == 0) {
            logger.error("请填写终端");
            return -1;
        }
        final List<TGamblerInfo> userlist = UserSelect.getUser(1, 1, head.getDealer_id());//取运营商管理员
        if (userlist == null) {
            logger.error("get user failed");
            return -3;
        }
        tbr.setIsSettled(0);
//        UserLoginReq ul = new UserLoginReq();
//        ul.setGamblerName(userlist.get(0).getGambler_name());
//        String key = HttpUtil.encryptPwd("123456", head.getTerminal_id());
//        if (key == null) {
//            return -4;
//        }
//        ul.setGamblerPwd(key);
//        String userloginxml = new CreateUserLoginXmlAndSend().getUserLoginXml(head, ul);
//        if (userloginxml == null) {
//            logger.error("用户" + ul.getGamblerName() + "登录报文生成异常");
//            return -5;
//        }
//        if (new CreateUserLoginXmlAndSend().send(userloginxml, cookieStore) != 0) {//运营商管理员登录，使用终端密码对密码加密
//            logger.error("登录失败");
//            return -6;
//        }
        return 0;
    }

    /**
     * 中奖号码与错误号码共用部分，串行发送报文，一张一张报文生成
     *
     * @param atsbr
     * @param head
     * @param tbr
     * @param cookieStore
     * @param serNo 流水号标识符
     */
    private void LuckyWrong(ArrayList<BetNumber> atsbr, AppHeader head, PBetSchemeRequst tbr, CookieStore cookieStore, String serNo) {
        int ticketnum = atsbr.size();
        //取流水号
        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
        if (endserial < startserial) {
            logger.error("用户未注册，流水号获取失败！");
            return;
        }
        Bet be = new Bet();
        BetNo bn = new BetNo();
        head.setAction(ActionID.SCHEMES_BET);
        int successticketnum = 0;
        int failticketnum = 0;
        long beforeTime = System.currentTimeMillis();
        for (BetNumber bt : atsbr) {
            int a[] = CommTool.getRandInt(0, 9, 4);
            String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
            bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
            tbr.setGamblerName(bankno);//卡号

            tbr.setGambler_serial_no(serNo + Long.toString(startserial));
            String Resultxml = be.getBetXmlLW(head, tbr, bt);
            if (Resultxml == null) {
                break;
            }
            if (bn.send(Resultxml, cookieStore) == 0) {
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
    }

    /**
     * 销售中奖票
     *
     * @param head
     * @param tbr
     * @param caseid 用例编号
     * @param serNo 流水号标识
     */
    public void SaleLuckyNO(AppHeader head, PBetSchemeRequst tbr, int caseid, String serNo) {
        CookieStore cookieStore = new BasicCookieStore();
        int re = getLoginUserXml(head, tbr, cookieStore);
        if (re != 0) {
            logger.error(re);
            return;
        }

        ArrayList<BetNumber> atsbr = new BetNo().getGameLuckyNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode(), caseid);
        if (atsbr == null) {
            return;
        }
        LuckyWrong(atsbr, head, tbr, cookieStore, serNo);

//        ArrayList<String> betxml = getMuchMoreBetXml(head, tbr, serNo);
//        if (betxml == null) {
//            return;
//        }
//        int successticketnum = 0;
//        int failticketnum = 0;
//        BetNo bn = new BetNo();
//        long beforeTime = System.currentTimeMillis();
//        for (String bex : betxml) {
//            if (bn.send(bex, cookieStore) == 0) {
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
     * 销售错误票
     *
     * @param head
     * @param tbr
     * @param serNo 流水号标识符
     */
    public void SendErrorXml(AppHeader head, PBetSchemeRequst tbr, String serNo) {
        CookieStore cookieStore = new BasicCookieStore();
        int re = getLoginUserXml(head, tbr, cookieStore);
        if (re != 0) {
            logger.error(re);
            return;
        }
        ArrayList<BetNumber> atsbr = new BetNo().getGameErrorNumber(tbr.getGame_id(), tbr.getPlay_id(), tbr.getBet_mode());
        if (atsbr == null) {
            return;
        }
        LuckyWrong(atsbr, head, tbr, cookieStore, serNo);

//        ArrayList<String> betxml = getMuchMoreBetXml(head, tbr, 0, serNo, false);
//        if (betxml == null) {
//            return;
//        }
//        int successticketnum = 0;
//        int failticketnum = 0;
//        BetNo bn = new BetNo();
//        long beforeTime = System.currentTimeMillis();
//        for (String bex : betxml) {
//            if (bn.send(bex, cookieStore) == 0) {
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
     * 发送投注的xml报文,单张报文，可以批量发送，用户定制号码
     *
     * @param head
     * @param tbr
     * @param isuser
     * @param ticketnum
     */
    public void SendAppointBetXml(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser,Count c) {
        CookieStore cookieStore = new BasicCookieStore();
//        if (!isuser) {
//            int re = getLoginUserXml(head, tbr, cookieStore);
//            if (re != 0) {
//                logger.error(re);
//                return;
//            }
//        }
        //logger.info( "======head ========="+head.toString());
        long Time1 = System.currentTimeMillis();
        ArrayList<String> als = getMuchMoreBetXml(head, tbr, ticketnum);
        if (als == null) {
            return;
        }
        long Time2 = System.currentTimeMillis();
        
        int successticketnum = 0;
        int failticketnum = 0;
        BetNo bn = new BetNo();
        long beforeTime = System.currentTimeMillis();
//        String filename1 = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
//        String filenameReq = StaticVariable.NUMBERREQXML + filename1;
//        String filenameRes = StaticVariable.NUMBERRESXML + filename1;

        for (String string : als) {
            if (bn.send(string, cookieStore) == 0) {//发送数据
                successticketnum++;
                c.addCount();
            } else {
                failticketnum++;
                c.addfailcount();
            }
        }
//        for (String string : als) {
//            String responsexml = HttpSend.httpSend(StaticVariable.SERVERURL, string);
//            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).parseXML(responsexml);
//             PBetSchemeResponse tbrr = (PBetSchemeResponse) ap.getContent();
//        int resultcode = tbrr.getResult().getResultCode();
//        if (resultcode != 0) {
//            logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultDes() + "\txml:" + responsexml);
//        c.addfailcount();
//           continue;
//        }else{
//            c.addCount();
//        }
////            if (bn.send(string, cookieStore) == 0) {//发送数据
////                successticketnum++;
////                c.addCount();
////            } else {
////                failticketnum++;
////                c.addfailcount();
////            }
//        }

        long endTime = System.currentTimeMillis();
        logger.info("endTime 时间是： "+endTime/1000F);
        long difference = endTime - Time1;
       // logger.error("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + difference/1000F);
       // System.out.println("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + CommTool.getTime(difference));
        //JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功 :" + successticketnum + " \t失败: " + failticketnum + " \t共用时间: " + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
        logger.info( "投注 --> 用户名：" + tbr.getGamblerName() + " \t成功 :" + successticketnum + " \t失败: " + failticketnum + " \t共用时间: " + CommTool.getTime(difference));
    }
    
      /**
     * 发送投注的xml报文,单张报文，可以批量发送，用户定制号码
     *
     * @param head
     * @param tbr
     * @param isuser
     * @param ticketnum
     */
    public void SendAppointBetXml1(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser) {
        CookieStore cookieStore = new BasicCookieStore();
//        if (!isuser) {
//            int re = getLoginUserXml(head, tbr, cookieStore);
//            if (re != 0) {
//                logger.error(re);
//                return;
//            }
//        }
        ArrayList<String> als = getMuchMoreBetXml1(head, tbr, ticketnum);
        if (als == null) {
            return;
        }
        int successticketnum = 0;
        int failticketnum = 0;
        BetNo bn = new BetNo();
        long beforeTime = System.currentTimeMillis();
//        String filename1 = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
//        String filenameReq = StaticVariable.NUMBERREQXML + filename1;
//        String filenameRes = StaticVariable.NUMBERRESXML + filename1;

        for (String string : als) {
            if (bn.send(string, cookieStore) == 0) {//发送数据
                successticketnum++;
            } else {
                failticketnum++;
            }
        }

        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
//        logger.error("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + CommTool.getTime(difference));
//        System.out.println("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功 :" + successticketnum + " \t失败: " + failticketnum + " \t共用时间: " + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
//        logger.info( "投注 --> 用户名：" + tbr.getGamblerName() + " \t成功 :" + successticketnum + " \t失败: " + failticketnum + " \t共用时间: " + CommTool.getTime(difference));
    }
    
   
    
}
