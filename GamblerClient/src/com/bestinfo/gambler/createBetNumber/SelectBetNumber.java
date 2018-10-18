package com.bestinfo.gambler.createBetNumber;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.BaseJDBCDao;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.OracleJDBCDao;
import com.bestinfo.gambler.bet.SelfTerminals;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.MoreBetInfoReq;
import com.bestinfo.protocols.bet.MoreBetInfoReqRes;
import com.bestinfo.protocols.bet.PBetSchemeQueryReq;
import com.bestinfo.protocols.bet.PBetSchemeQueryRes;
import com.bestinfo.protocols.bet.TBetSchemeSingleQueryReqRes;
import com.bestinfo.protocols.client.bet.PBetSchemeQueryClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;

/**
 * 投注查询
 *
 * @author chenliping
 */
public class SelectBetNumber {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SelectBetNumber.class);

    /**
     * 查询数据库得到方案流水号
     *
     * @return
     */
    private List<PBetSchemeQueryReq> getBetNum(String dealerId) {
        List<PBetSchemeQueryReq> tbetL = new ArrayList<PBetSchemeQueryReq>();
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        try {
            Statement s = conn.createStatement();
            String sql = "select b.GAMBLER_SERIAL_NO,a.GAMBLER_NAME from t_bet_scheme b , t_gambler_info a where b.gambler_id=a.gambler_id and a.dealer_id=b.dealer_id and a.dealer_id='" + dealerId + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                PBetSchemeQueryReq tbet = new PBetSchemeQueryReq();
                tbet.setDealerSerial(rs.getString("GAMBLER_SERIAL_NO"));
                tbet.setGamblerName(rs.getString("GAMBLER_NAME"));
                tbetL.add(tbet);
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }
        return tbetL;
    }

    /**
     * 查询数据库得到方案编号
     *
     * @return
     */
    private List<PBetSchemeQueryReq> getBetNumScheme() {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, "virtual");
        List<PBetSchemeQueryReq> tbetL = new ArrayList<PBetSchemeQueryReq>();
        String sql = "select SCHEME_ID,GAMBLER_NAME from T_BETRES";
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                PBetSchemeQueryReq tbet = new PBetSchemeQueryReq();
                tbet.setDealerSerial(rs.getString("SCHEME_ID"));
                tbet.setGamblerName(rs.getString("GAMBLER_NAME"));
                tbetL.add(tbet);
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }
        return tbetL;
    }

    /**
     * 单方案查询
     *
     * @param head
     * @param reg
     * @param c
     */
    public void SelectBet(AppHeader head, PBetSchemeQueryReq reg, Count c) {
        head.setAction(ActionID.SCHEMES_QUERY);
        String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_QUERY).generateXML(head, reg);
        if (Resultxml == null || Resultxml.isEmpty()) {
            logger.error("组装xml数据出错！");
            return;
        }
        Resultxml = SerialNo.getxml(Resultxml, ActionID.SCHEMES_QUERY);
        String responsexml = HttpUtil.httpSend(Resultxml, StaticVariable.SERVERURL, false);
        if (responsexml == null) {
            c.addfailcount();
            return;
        } else if (responsexml.equals("sendError")) {
            c.addSendFail();
            return;
        }

        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_QUERY).parseXML(responsexml);
        if (ap == null) {
            return;
        }
        PBetSchemeQueryRes grres = (PBetSchemeQueryRes) ap.getContent();
        if (grres.getResult().getResultCode() != 0) {
            logger.error(grres.getResult().getResultCode() + "\t" + grres.getResult().getResultDes());
            c.addfailcount();
        }
        c.addCount();
    }

    /**
     * 单方案查询
     *
     * @param head
     * @param reg
     */
    public void SelectBet(AppHeader head, PBetSchemeQueryReq reg) {
//        Count c = new Count();
        SelectBet_send(SelectBet_CreateXml(head, reg));
    }

    /**
     * 单方案查询----创建报文
     *
     * @param head
     * @param reg
     * @return
     */
    private String SelectBet_CreateXml(AppHeader head, PBetSchemeQueryReq reg) {
        String Resultxml = null;
        try {
            head.setAction(ActionID.SCHEMES_QUERY);
            PBetSchemeQueryClient betSchemeQuery = new PBetSchemeQueryClient();
            Resultxml = betSchemeQuery.generateXML(head, reg);
            //String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_QUERY).generateXML(head, reg);
            if (Resultxml == null || Resultxml.isEmpty()) {
                logger.error("组装xml数据出错！");
                return null;
            }
//            String sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
//            logger.info("sessionKey is: " + sessionKey);
//            if (sessionKey == null) {
//                logger.info("sessionKey is null");
//            }
//
//            byte[] ret_key = NewSign.GetSign(Resultxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
            //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
            //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
            byte[] ret_key = NewSign.GetSign(Resultxml, StaticVariable.privateKey, StaticVariable.SESSION);
            head.setBody_sign(ret_key);
            Resultxml = betSchemeQuery.generateXML(head, reg);
            if (Resultxml == null || Resultxml.isEmpty()) {
                logger.error("组装xml数据出错！");
                return null;
            }
            return SerialNo.getxml(Resultxml, ActionID.SCHEMES_QUERY);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Resultxml;
    }

    /**
     * 单方案查询----创建报文
     *
     * @param head
     * @param reg
     * @return
     */
    private List<String> SelectBet_CreateXml(AppHeader head, List<PBetSchemeQueryReq> reg) {
        List<String> allxml = new ArrayList<String>();
        for (PBetSchemeQueryReq tq : reg) {
            String xml = SelectBet_CreateXml(head, tq);
            if (xml == null) {
                continue;
            }
            allxml.add(xml);
        }
        return allxml;
    }

    /**
     * 单方案查询----发送 0为成功，负数为失败
     *
     * @param xml
     * @return
     */
    public int SelectBet_send(String xml) {
        try {
            String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
            if (responsexml == null) {
                return -1;
            }
            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_QUERY).parseXML(responsexml);
            if (ap == null) {
                return 0;
            }
            PBetSchemeQueryRes grres = (PBetSchemeQueryRes) ap.getContent();
            if (grres.getResult().getResultCode() != 0) {
                logger.error(grres.getResult().getResultDes());
                return -2;
            }
            JOptionPane.showMessageDialog(null, "返回码:" + grres.getResult().getResultCode() + "结果:" + grres.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
        return 0;
    }

    /**
     * 单方案查询----发送 0为成功，负数为失败
     *
     * @param xml
     * @param cookieStore
     * @return
     */
    public int SelectBet_send(String xml, CookieStore cookieStore) {
        try {
            String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, cookieStore);
            if (responsexml == null) {
                return -1;
            }
            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET_SINGLE_QUERY).parseXML(responsexml);
            if (ap == null) {
                return 0;
            }
            TBetSchemeSingleQueryReqRes grres = (TBetSchemeSingleQueryReqRes) ap.getContent();
            if (grres.getResult().getResultCode() != 0) {
                logger.error(grres.getResult().getResultDes());
                return -2;
            }
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
        return 0;
    }

    /**
     * 多线程投注查询---手机登录
     *
     * @param head
     * @param size 线程个数
     * @param count 每个线程的票数
     * @throws Exception
     */
    private void moreluckyNOthread_phone(final AppHeader head, int size, final int eachsize, final Count usercount) throws Exception {
        final List<PBetSchemeQueryReq> ltbet = getBetNumScheme();//得到所有的投注方案 
        final int len = ltbet.size();
        if (len == 0) {
            logger.error("查不到流水号");
            return;
        }
        final List<String> allxml = SelectBet_CreateXml(head, ltbet);
        long beforeTime = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(size);// n个线程可以同时访问 
        final Count c = new Count();
        for (int index = 0; index < size; index++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可   
                        int f = 0;
                        for (int i = 0; i < len; i++) {
                            f++;
                            int code = SelectBet_send(allxml.get(i), usercount.getMcookiestore().get(ltbet.get(i).getGamblerName()));
                            if (code == 0) {
                                c.addCount();
                            } else {
                                c.addfailcount();
                            }
                            if (f == eachsize) {
                                break;
                            }
                            if (eachsize > f) {
                                i = 0;
                            }
                        }
                        semp.release();
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            });
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
    }

    /**
     * 多线程投注查询---手机登录
     *
     * @param head
     * @param size 线程个数
     * @param eachsize
     * @throws Exception
     */
//    public void SelectBetInfo_Phone(final AppHeader head, int size, final int eachsize) throws Exception {
//        final Count usercount = new Count();
//        int userno = new CreateUserLoginXmlAndSend().SendUserLoginXml(head, usercount);//所有用户登录
//        moreluckyNOthread_phone(head, size, eachsize, usercount);
//    }
    /**
     * 多线程投注查询---手机不登录
     *
     * @param head
     * @param size 线程个数
     * @param count 每个线程的票数
     * @throws Exception
     */
    public void moreluckyNOthread_phone(final AppHeader head, int size, final int count) throws Exception {
        final List<PBetSchemeQueryReq> ltbet = getBetNumScheme();//得到所有的投注方案 
        final int len = ltbet.size();
        if (len == 0) {
            logger.error("查不到流水号");
            return;
        }
        final List<String> allxml = SelectBet_CreateXml(head, ltbet);
        long beforeTime = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(size);// n个线程可以同时访问 
        final Count co = new Count();
        for (int index = 1; index <= size; index++) {
            final int a = index;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可
                        int c = 1;
                        int i = 0;
                        int b = (a * count);
                        if (b > len) {
                            i = len % b - 1;
                        }
                        for (; i < count; i++) {
                            if (c > count) {
                                break;
                            }
                            if (i >= len) {
                                i = len % i;
                            }
                            SelectBet_send(allxml.get(i));
                            c++;
                        }
                        semp.release();
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            });
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("success:" + co.getCount() + "\tfailed:" + co.getFailcount() + "\tsendfailed:" + co.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("success:" + co.getCount() + "\tfailed:" + co.getFailcount() + "\tsendfailed:" + co.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
    }

    /**
     * 多线程投注查询
     *
     * @param head
     * @param size 线程个数
     * @param count 每个线程的票数
     * @throws Exception
     */
    public void moreluckyNOthread(final AppHeader head, int size, final int count) throws Exception {
        List<PBetSchemeQueryReq> ltbet = getBetNum(head.getDealer_id());//得到所有的投注流水号       
        final int len = ltbet.size();
        if (len == 0) {
            logger.error("查不到流水号");
            return;
        }
        long beforeTime = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(size);
        final Count co = new Count();
        for (int index = 1; index <= size; index++) {
            final PBetSchemeQueryReq pbsq = ltbet.get(index);
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < count; i++) {
                            SelectBet(head, pbsq, co);
                        }
                        semp.release();
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            });
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("success:" + co.getCount() + "\tfailed:" + co.getFailcount() + "\tsendfailed:" + co.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("success:" + co.getCount() + "\tfailed:" + co.getFailcount() + "\tsendfailed:" + co.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "成功: " + co.getCount() + " \t失败: " + co.getFailcount() + " \t发送失败: " + co.getSendFail() + " \t共用时间:" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * ********************************************彩民投注明细查询*************************************************
     */
    private String GamblerbetDetail_CreateXml(AppHeader head, MoreBetInfoReq reg) {
        head.setAction(ActionID.SCHEMES_BET_MORE_QUERY);
        String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET_MORE_QUERY).generateXML(head, reg);
        if (Resultxml == null || Resultxml.isEmpty()) {
            logger.error("组装xml数据出错！");
            return null;
        }
        return SerialNo.getxml(Resultxml, ActionID.SCHEMES_BET_MORE_QUERY);
    }

    private int GamblerbetDetail_send(String xml) throws Exception {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        if (responsexml == null) {
            return -1;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET_MORE_QUERY).parseXML(responsexml);
        if (ap == null) {
            return 0;
        }
        MoreBetInfoReqRes grres = (MoreBetInfoReqRes) ap.getContent();
        if (grres.getResult().getResultCode() != 0) {
            logger.error(grres.getResult().getResultDes());
            return -2;
        } else {
            return 0;
        }
    }

    private int GamblerbetDetail_send(String xml, CookieStore cookieStore) throws Exception {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, cookieStore);
        if (responsexml == null) {
            return -1;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET_MORE_QUERY).parseXML(responsexml);
        if (ap == null) {
            return 0;
        }
        MoreBetInfoReqRes grres = (MoreBetInfoReqRes) ap.getContent();
        if (grres.getResult().getResultCode() != 0) {
            logger.error(grres.getResult().getResultDes());
            return -2;
        } else {
            return 0;
        }
    }

    /**
     * 彩民投注明细查询----单线程
     *
     * @param head
     * @param reg
     */
    public void GamblerbetDetailSelect(AppHeader head, MoreBetInfoReq reg) {
        try {
            GamblerbetDetail_send(GamblerbetDetail_CreateXml(head, reg));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 彩民投注明细查询----多线程(不登录)
     *
     * @param head
     * @param gameid
     * @param threadnum
     * @param eachnum
     */
//    public void GamblerbetDetailSelect(AppHeader head, int gameid, int threadnum, final int eachnum) {
//        List<UserLoginReq> lul = UserSelect.getUserobj(head, threadnum);
//        final List<String> ls = new ArrayList<String>();
//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        for (UserLoginReq ul : lul) {
//            MoreBetInfoReq mi = new MoreBetInfoReq();
//            mi.setGambler_name(ul.getGamblerName());
//            int ra = (int) (Math.random() * 10);
//            cal.set(Calendar.DATE, -ra);
//            mi.setBeginTime(sim.format(cal.getTime()));
//            mi.setEndTime(sim.format(new Date()));
//            mi.setGameId(gameid);
//            ls.add(GamblerbetDetail_CreateXml(head, mi));
//        }
//        long beforeTime = System.currentTimeMillis();
//        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
//        final Semaphore semp = new Semaphore(threadnum);// n个线程可以同时访问 
//        final Count c = new Count();
//        for (int index = 0; index < threadnum; index++) {
//            final int a = index;
//            exec.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        semp.acquire();// 获取许可
//                        for (int i = 0; i < eachnum; i++) {
//                            if (GamblerbetDetail_send(ls.get(a)) < 0) {
//                                c.addfailcount();
//                            } else {
//                                c.addCount();
//                            }
//                        }
//                        semp.release();
//                    } catch (Exception e) {
//                        logger.error(e);
//                    }
//                }
//            });
//        }
//        // 退出线程池
//        exec.shutdown();
//        while (!exec.isTerminated()) {
//            try {
//                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
//            } catch (Exception e) {
//                logger.error("", e);
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        long difference = endTime - beforeTime;
//        logger.error("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
//        System.out.println("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
//    }
    /**
     * 彩民投注明细查询----多线程(登录)
     *
     * @param head
     * @param gameid
     * @param threadnum
     * @param eachnum
     */
//    public void GamblerbetDetailSelect_login(AppHeader head, int gameid, int threadnum, final int eachnum) {
//        List<UserLoginReq> alluser = UserSelect.getUserobj(head, threadnum);
//        if (alluser == null || alluser.isEmpty()) {
//            return;
//        }
//        final List<String> userloginxml = new CreateUserLoginXmlAndSend().getUserLoginXml(head, alluser);
//        if (userloginxml == null) {
//            return;
//        }
//        final List<String> ls = new ArrayList<String>();
//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        for (UserLoginReq ul : alluser) {
//            MoreBetInfoReq mi = new MoreBetInfoReq();
//            mi.setGambler_name(ul.getGamblerName());
//            int ra = (int) (Math.random() * 10);
//            cal.set(Calendar.DATE, -ra);
//            mi.setBeginTime(sim.format(cal.getTime()));
//            mi.setEndTime(sim.format(new Date()));
//            mi.setGameId(gameid);
//            ls.add(GamblerbetDetail_CreateXml(head, mi));
//        }
//        long beforeTime = System.currentTimeMillis();
//        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
//        final Semaphore semp = new Semaphore(threadnum);// n个线程可以同时访问 
//        final Count c = new Count();
//        for (int index = 0; index < threadnum; index++) {
//            final int a = index;
//            exec.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        semp.acquire();// 获取许可
//                        CookieStore cookieStore = new BasicCookieStore();
//                        if (new CreateUserLoginXmlAndSend().send(userloginxml.get(a), cookieStore) != 0) {//登录
//                            return;
//                        }
//                        for (int i = 0; i < eachnum; i++) {
//                            if (GamblerbetDetail_send(ls.get(a), cookieStore) < 0) {
//                                c.addfailcount();
//                            } else {
//                                c.addCount();
//                            }
//                        }
//                        semp.release();
//                    } catch (Exception e) {
//                        logger.error(e);
//                    }
//                }
//            });
//        }
//        // 退出线程池
//        exec.shutdown();
//        while (!exec.isTerminated()) {
//            try {
//                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
//            } catch (Exception e) {
//                logger.error("", e);
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        long difference = endTime - beforeTime;
//        logger.error("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
//        System.out.println("success:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
//    }
}
