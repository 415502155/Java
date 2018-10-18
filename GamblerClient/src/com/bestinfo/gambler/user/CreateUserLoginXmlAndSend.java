package com.bestinfo.gambler.user;

import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.BaseJDBCDao;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.bet.UserSelect;
import com.bestinfo.gambler.bet.UserSelect1;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserLoginReq;
import com.bestinfo.protocols.users.UserLoginReqRes;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

/**
 *
 * @author chenliping
 */
public class CreateUserLoginXmlAndSend {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CreateUserLoginXmlAndSend.class);

    /**
     * 发送数据并处理返回数据
     *
     * @param xml
     * @return
     * @throws Exception
     */
    private int send(String xml, UserLoginReq ulr) throws Exception {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, true);
        if (responsexml == null) {
            return -2;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_LOGIN).parseXML(responsexml);

        UserLoginReqRes tbrr = (UserLoginReqRes) ap.getContent();//得到返回内容
        int resultcode = tbrr.getAppResResult().getResultCode();

        if (resultcode == 0) {
            if (StaticVariable.remeberMe) {//登录成功，将用户记住
                StaticVariable.us = ulr;
            }
            System.out.println("send successfull");
        } else {
            logger.error("登录失败：" + resultcode + "\t" + tbrr.getAppResResult().getResultDes());
            System.out.println(resultcode + "\t" + tbrr.getAppResResult().getResultDes());
            return -1;
        }
        return 0;
    }

    /**
     * 发送数据并处理返回数据
     *
     * @param xml
     * @param cookieStore 返回值
     * @return
     */
    public int send(String xml, CookieStore cookieStore) {
        try {
            String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, cookieStore);
            if (responsexml == null) {
                return -2;
            } else if (responsexml.equals("sendError")) {
                return -5;
            }
            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_LOGIN).parseXML(responsexml);

            UserLoginReqRes tbrr = (UserLoginReqRes) ap.getContent();//得到返回内容
            int resultcode = tbrr.getAppResResult().getResultCode();
            if (resultcode == 0) {
                return 0;
            } else {
                logger.error("登录失败：" + resultcode + "\t" + tbrr.getAppResResult().getResultDes());
                return -1;
            }
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    private void morethread(final List<UserLoginReq> lur, final List<String> lar, final int userno, final int eachno) {
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(userno);// n个线程可以同时访问  
        final Count c = new Count();
        long beforeTime = System.currentTimeMillis();
        for (int index = 0; index < userno; index++) {
            final int NO = index;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可
                        for (int i = 0; i < eachno; i++) {
                            int re = send(lar.get(NO), lur.get(NO));
                            if (re == 0) {
                                c.addCount();
                            } else {
                                c.addfailcount();
                            }
                        }
                        semp.release();
                    } catch (Exception e) {
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
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * *
     * 用户多线程登录 ，记录cookie
     *
     * @param lur 用户对象
     * @param lar 用户报文
     * @param userno 用户个数
     */
    private void morethread(final List<UserLoginReq> lur, final List<String> lar, final int userno, final Count c) {
        long beforeTime = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(userno);// n个线程可以同时访问  
        for (int index = 0; index < userno; index++) {
            final int NO = index;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可
                        CookieStore cookieStore = new BasicCookieStore();
                        int re = send(lar.get(NO), cookieStore);
                        if (re == 0) {
                            c.addCount();
                            c.addMcookiestore(lur.get(NO).getGamblerName(), cookieStore);
                        } else {
                            c.addfailcount();
                        }
                        semp.release();
                    } catch (Exception e) {
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
        logger.info("投注成功票数：" + c.getCount() + "\t\t失败票数：" + c.getFailcount() + "\t总共用时：" + CommTool.getTime(difference));
        System.out.println("投注成功票数：" + c.getCount() + "\t\t失败票数：" + c.getFailcount() + "\t总共用时：" + CommTool.getTime(difference));
    }

    /**
     * 组装xml数据
     *
     * @param head
     * @param ur
     * @return
     */
    public String getUserLoginXml(AppHeader head, UserLoginReq ur) {
        head.setAction(ActionID.GAMBLER_LOGIN);
        String userxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_LOGIN).generateXML(head, ur);
        if (userxml == null) {
            logger.error("组装登录报文出错！");
            return null;
        }
        return SerialNo.getxml(userxml, ActionID.GAMBLER_LOGIN);
    }

    /**
     * *
     * 组装xml数据 ,批量
     *
     * @param head
     * @param alluser
     * @return
     */
    public List<String> getUserLoginXml(AppHeader head, List<UserLoginReq> alluser) {
//        ArrayList<String> ait = null;
//        if (head.getType() == SerialNo.SENDTYPEFOUR) {
//            ait = UserSelect.checkandgetTmnPhy(head.getDealer_id());
//            if (ait == null) {
//                return null;
//            }
//        }
        List<String> alluserxml = new ArrayList<String>();//存放所有的xml数据
        for (UserLoginReq userLoginReq : alluser) {
//            if (ait != null) {
//                int b[] = CommTool.getRandInt(0, ait.size(), 1);
//                head.setTerminal_id(Integer.parseInt(ait.get(b[0])));
//            }else{
//                //String newp = HttpUtil.encryptPwd("123456", null);
//                String newp = "123456";
//                userLoginReq.setGamblerPwd(newp);
//            }
            String userxml = getUserLoginXml(head, userLoginReq);
            if (userxml == null) {
                continue;
            }
            alluserxml.add(userxml);
        }
        return alluserxml;
    }

    /**
     * 组装xml数据并发送,针对批量用户登录
     *
     * @param head 报文头
     * @param userno 用户个数
     * @param eachno 每用户登录次数
     */
    public void SendUserLoginXml(AppHeader head, int userno, int eachno) {
         List<UserLoginReq> alluser = UserSelect1.getUserobj(head, userno);
        //List<UserLoginReq> alluser = getUser();
        if (alluser == null || alluser.isEmpty()) {
            return;
        }
        List<String> alluserxml = getUserLoginXml(head, alluser);
        if (alluserxml.isEmpty()) {
            return;
        }
        morethread(alluser, alluserxml, userno, eachno);
    }

    /**
     * 获取用户，组装xml数据并发送,针对批量用户登录，每用户登录一次，多用户登录 ,记录cookie
     *
     * @param head
     * @param userno 用户个数
     * @param c 记录cookie
     */
    public void SendUserLoginXml(AppHeader head, int userno, Count c) {
        List<UserLoginReq> alluser = UserSelect.getUserobj(head, userno);
        if (alluser == null || alluser.isEmpty()) {
            return;
        }
        List<String> alluserxml = getUserLoginXml(head, alluser);
        if (alluserxml.isEmpty()) {
            return;
        }
        morethread(alluser, alluserxml, userno, c);
    }

    /**
     * 组装xml数据并发送,针对单用户登录
     *
     * @throws Exception
     */
    public int SendUserLoginXml(AppHeader head, UserLoginReq ur) {
        try {
            return send(getUserLoginXml(head, ur), ur);
        } catch (Exception e) {
            logger.error("", e);
            return 0;
        }
    }

    /**
     * ***********************************************************************************
     */
    /**
     * 查询数据库得到方案流水号的人
     *
     * @return
     */
    private List<UserLoginReq> getBetPeople() {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, "virtual");
        List<UserLoginReq> tbetL = new ArrayList<UserLoginReq>();
        String sql = "select DISTINCT GAMBLER_NAME from T_BETRES";
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                UserLoginReq tbet = new UserLoginReq();
                tbet.setGamblerName(rs.getString("GAMBLER_NAME"));
                tbet.setGamblerPwd("123456");
                tbetL.add(tbet);
            }
            rs.close();
            s.close();
            conn.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        return tbetL;
    }

    /**
     * 从方案回复表获取用户，组装xml数据并发送,针对批量用户登录，每用户登录一次，多用户登录 ,记录cookie
     *
     * @param head
     * @param c 记录cookie
     * @return 用户个数
     */
    public int SendUserLoginXml(AppHeader head, Count c) {
        List<UserLoginReq> alluser = getBetPeople();
        if (alluser == null || alluser.isEmpty()) {
            return 0;
        }
        List<String> alluserxml = getUserLoginXml(head, alluser);
        if (alluserxml.isEmpty()) {
            return 0;
        }
        morethread(alluser, alluserxml, alluser.size(), c);
        return alluser.size();
    }

    private List<UserLoginReq> getUser() {
        List<UserLoginReq> alluser = new ArrayList<UserLoginReq>();
        UserLoginReq u = new UserLoginReq();
        u.setGamblerName("a10yiHLV0");
        u.setGamblerPwd("123");
        alluser.add(u);
        UserLoginReq u1 = new UserLoginReq();
        u1.setGamblerName("a1pIRaz31");
        u1.setGamblerPwd("123");
        alluser.add(u1);
        UserLoginReq u2 = new UserLoginReq();
            u2.setGamblerName("a0Lf4n5F0");
        u2.setGamblerPwd("123");
        alluser.add(u2);
        return alluser;
    }

    public void SendUserLoginXml2(AppHeader head, int userno, int eachno, List<UserLoginReq> alluser) {
        List<UserLoginReq> userlist = alluser;
        if (userlist == null || userlist.isEmpty()) {
            return;
        }
        List<String> alluserxml = getUserLoginXml(head, userlist);
        if (alluserxml.isEmpty()) {
            return;
        }
        morethread(alluser, alluserxml, userno, eachno);
    }
}
